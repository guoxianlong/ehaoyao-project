package com.ehaoyao.opertioncenter.thirdOrderAudit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.opertioncenter.common.CommonUtils;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.dao.IThirdOrderAuditDao;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.InvoiceInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderAuditLog;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.service.IThirdOrderAuditService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.User;
import com.pajk.openapi.codec.client.RequestEncoder;
import com.pajk.openapi.codec.client.RequestEntity;
import com.pajk.openapi.codec.client.ResponseDecoder;

@Service
public class ThirdOrderAuditServiceImpl implements IThirdOrderAuditService {
	
	private static final Logger logger = Logger.getLogger(ThirdOrderAuditServiceImpl.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private IThirdOrderAuditDao iThirdOrderDao;
	
	@Autowired
	private UserDao userDao;

	
	
	/**
	 * 三方订单客服审核查询
	 * @throws Exception 
	 */
	public PageModel<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm,ThirdOrderAuditVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iThirdOrderDao.getCountOrderInfos(vo);
			pm.setTotalRecords(count);
		}
		List<OrderMainInfo> ls = iThirdOrderDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception{
		return iThirdOrderDao.getOrderDetails(vo);
	}

	@Override
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception {
		Object retnObj = null;
		if(vo!=null){
			retnObj = iThirdOrderDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
		}
		return retnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateAuditStatus(ThirdOrderAuditVO vo) throws Exception {
		/**
		 * 备注：审核通过，则审核日志表及发票表，记录客服更改的信息，驳回则为订单默认信息
		 */
		
		String auditStatus = vo.getAuditStatus()!=null&&vo.getAuditStatus().trim().length()>0?vo.getAuditStatus().trim():"";
		String orderNumber = vo.getOrderNumber()!=null&&vo.getOrderNumber().trim().length()>0?vo.getOrderNumber().trim():"";
		String auditDescription = vo.getAuditDescription()!=null&&vo.getAuditDescription().trim().length()>0?vo.getAuditDescription().trim():"";
		String invoiceStatus = vo.getInvoiceStatus()!=null&&vo.getInvoiceStatus().trim().length()>0?vo.getInvoiceStatus().trim():"";
		String remark = vo.getRemark()!=null&&vo.getRemark().trim().length()>0?vo.getRemark().trim():"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		InvoiceInfo invoiceInfo = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
		List<InvoiceInfo> invoiceInfoList = new ArrayList<InvoiceInfo>();
			if(vo!=null){
					User user = getCurrentUser();

					//更新订单表
					orderInfo =  (OrderInfo) iThirdOrderDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
					
					//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
					if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(orderInfo.getAuditStatus().trim())){
						return "订单："+orderNumber+"，审批失败，已被其他客服审批，请返回查看详情！";
					}
					
					orderInfo.setAuditStatus(auditStatus);
					orderInfo.setLastTime(currDate);
					if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(auditStatus)){
						orderInfo.setInvoiceStatus(vo.getInvoiceStatus());
						orderInfo.setRemark(remark);
					}
					orderInfoList.add(orderInfo);
					
					//订单审核轨迹记录
					orderAuditLog = new OrderAuditLog();
					orderAuditLog.setOrderNumber(orderNumber);
					orderAuditLog.setOrderFlag(vo.getOrderFlag());
					orderAuditLog.setAuditUser(user.getName());
					orderAuditLog.setKfAccount(user.getUserName());
					orderAuditLog.setAuditStatus(auditStatus);
					orderAuditLog.setAuditDescription(auditDescription);
					orderAuditLog.setCreateTime(currDate);
					if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(auditStatus)){
						orderAuditLog.setRemark(remark);
					}else{
						orderAuditLog.setRemark(orderInfo.getRemark());
					}
					orderAuditLogList.add(orderAuditLog);
					
					//更新发票表信息
					if(invoiceStatus.length()>0 && OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(vo.getAuditStatus())){
						invoiceInfo = new InvoiceInfo();
						invoiceInfo.setOrderNumber(orderNumber);
						invoiceInfo.setOrderFlag(vo.getOrderFlag());
						invoiceInfo.setKfAccount(user.getUserName());
						invoiceInfo.setCreateTime(currDate);
						invoiceInfo.setInvoiceStatus(invoiceStatus);
						if("1".equals(invoiceStatus)){
							invoiceInfo.setInvoiceType(vo.getInvoiceType());
							invoiceInfo.setInvoiceTitle(vo.getInvoiceTitle());
							invoiceInfo.setInvoiceContent(vo.getInvoiceContent());
						}
						invoiceInfoList.add(invoiceInfo);
					}
			}
			
			if(!orderInfoList.isEmpty()){
				orderInfoList = CommonUtils.removeDuplicate(orderInfoList);
				iThirdOrderDao.updateOrderInfoBatch(orderInfoList);
			}
			if(!orderAuditLogList.isEmpty()){
				orderAuditLogList = CommonUtils.removeDuplicate(orderAuditLogList);
				iThirdOrderDao.addOrderAuditLogBatch(orderAuditLogList);
			}
			if(!invoiceInfoList.isEmpty()){
				invoiceInfoList = CommonUtils.removeDuplicate(invoiceInfoList);
				iThirdOrderDao.addInvoiceInfoBatch(invoiceInfoList);
			}
			
		return "订单："+orderNumber+"，审批成功！";
	}
	
	/**
	 * 获取当前用户
	 * @return User 
	 */
	private User getCurrentUser(){
		//用户信息
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				User user = userDao.loadUserByUserName(userName);
				if(user!=null){
					return user;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateAuditStatusBatch(String[] orderNumberFlagsParam) throws Exception {
		/**
		 * 备注：批量审核，则订单备注、发票信息则取默认订单信息
		 */
		
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		InvoiceInfo invoiceInfo = null;
		OrderMainInfo orderMainInfo = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
		List<InvoiceInfo> invoiceInfoList = new ArrayList<InvoiceInfo>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		ThirdOrderAuditVO vo = new ThirdOrderAuditVO();
		String hasAuditOrders = "";//如果原订单审核状态为PRESUCC/PRERETURN,则记录并返回已审核的提示
		String retStr = "";//返回信息
		for(int i=0;i<orderNumberFlagsParam.length;i++){
			
			//更新订单表
			orderInfo =  (OrderInfo) iThirdOrderDao.getOrderInfosByOrderNumFlags(orderNumberFlagsParam[i]);
			
			//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
			if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(orderInfo.getAuditStatus().trim())){
				hasAuditOrders += (orderInfo.getOrderNumber()+"、");
				continue;
			}
			
			vo.setOrderNumber(orderInfo.getOrderNumber());
			vo.setOrderFlag(orderInfo.getOrderFlag());
			orderMainInfo = iThirdOrderDao.getOrderMainInfo(vo);
			orderInfo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC);
			orderInfo.setLastTime(currDate);
			orderInfoList.add(orderInfo);
			
			User user = getCurrentUser();
			
			//订单审核轨迹记录
			orderAuditLog = new OrderAuditLog();
			orderAuditLog.setOrderNumber(orderInfo.getOrderNumber());
			orderAuditLog.setOrderFlag(orderInfo.getOrderFlag());
			orderAuditLog.setAuditUser(user.getName());
			orderAuditLog.setKfAccount(user.getUserName());
			orderAuditLog.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC);
			orderAuditLog.setCreateTime(currDate);
			orderAuditLog.setRemark(orderInfo.getRemark());
			orderAuditLogList.add(orderAuditLog);
			

			//更新发票表信息
				invoiceInfo = new InvoiceInfo();
				invoiceInfo.setOrderNumber(orderInfo.getOrderNumber());
				invoiceInfo.setOrderFlag(orderInfo.getOrderFlag());
				invoiceInfo.setKfAccount(user.getUserName());
				invoiceInfo.setCreateTime(currDate);
				invoiceInfo.setInvoiceStatus(orderMainInfo.getInvoiceStatus());
				invoiceInfo.setInvoiceType(orderMainInfo.getInvoiceType());
				invoiceInfo.setInvoiceTitle(orderMainInfo.getInvoiceTitle());
				invoiceInfo.setInvoiceContent(orderMainInfo.getInvoiceContent());
				invoiceInfoList.add(invoiceInfo);
			
		}
		
		if(!orderInfoList.isEmpty()){
			orderInfoList = CommonUtils.removeDuplicate(orderInfoList);
			iThirdOrderDao.updateOrderInfoBatch(orderInfoList);
		}
		
		if(!orderAuditLogList.isEmpty()){
			orderAuditLogList = CommonUtils.removeDuplicate(orderAuditLogList);
			iThirdOrderDao.addOrderAuditLogBatch(orderAuditLogList);
		}
		if(!invoiceInfoList.isEmpty()){
			invoiceInfoList = CommonUtils.removeDuplicate(invoiceInfoList);
			iThirdOrderDao.addInvoiceInfoBatch(invoiceInfoList);
		}
		
		retStr = "批量审批订单操作成功！成功审批"+(orderInfoList!=null&&!orderInfoList.isEmpty()?orderInfoList.size():0)+"条订单!";
		if(hasAuditOrders!=null && hasAuditOrders.trim().length()>0){
			retStr += "已被其他客服审批订单："+hasAuditOrders+"请返回查看订单详情。";
		}
		
		return retStr;
	}

	@Override
	public OrderMainInfo getOrderMainInfo(ThirdOrderAuditVO vo) throws Exception {
		OrderMainInfo orderMainInfo = iThirdOrderDao.getOrderMainInfo(vo);
		return orderMainInfo;
	}
	
	
	/**
	* @Description:审核平安订单
	* @param	paramMap 参数集合
	* @throws
	*/ 
	@SuppressWarnings("rawtypes")
	@Override
	public String auditPAOrder(Map<String,Object> paramMap) throws Exception {
		String orderNumber = paramMap.get("orderNumber")!=null&&paramMap.get("orderNumber").toString().trim().length()>0?paramMap.get("orderNumber").toString().trim():"";
		String auditStatus = paramMap.get("auditStatus")!=null&&paramMap.get("auditStatus").toString().trim().length()>0?paramMap.get("auditStatus").toString().trim():"";
		String auditDescription = paramMap.get("auditDescription")!=null&&paramMap.get("auditDescription").toString().trim().length()>0?paramMap.get("auditDescription").toString().trim():"";
		
		if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(auditStatus) && auditDescription.length()<=0){
			auditDescription = "医师审核通过";
		}
		
		//转换为平安API要求的审核状态枚举值 1：审核通过 2：审核不通过
		if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(auditStatus) ){
			auditStatus = "1";//最终审核状态为医师审核通过
		}
		if(OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(auditStatus)){
			auditStatus = "2";//最终审核状态为医师审核驳回
		}
		String msg =null;
		//1  配置   调用审核接口的参数
		String partnerId = "haoyaoshi_01";
		// 1.1请求响应报文使用 
		String key = "8aaa9339547fb66351e4a66972cfe359"; 
		// apiId，            平安平台还未开发出测试接口
		String apiId = "51ba608c079c53c3a1f98b9ab2837633#PROD"; 
		String apiName = "B2cNoticePrescribedOrder"; 
		Long userVenderId = new Long(2083950007);
		String apiGroup = "shennongLogistics";
		// 1.3 开始请求报⽂文编码 
		RequestEncoder encoder = new RequestEncoder(partnerId, key, apiId);
		// 1.4  填充API参数 
		encoder.addParameter(userVenderId);
		encoder.addParameter(orderNumber);
		encoder.addParameter(auditStatus);
		encoder.addParameter(auditDescription);
		encoder.addParameter("11");//remark不能为空，所以随便填充，具体参照之前版本--三方平台订单审核(平安处方药)或咨询平安相关人员
		// 1.5  结束请求报⽂文编码,作为HTTP POST BODY 
		RequestEntity re = encoder.encode();
		String url = "http://openapi.jk.cn/api/v1/"+apiGroup+"/"+apiName + "?";
		url += re.getQueryParams();
		url += "&" + re.getFormParams();
		// 2  客户端发送HTTP POST请求，获得数据后，解析成符合要求的json字符串
		// 2.1  客户端发送HTTP POST请求， 获得HTTP相应结果(JSON格式)
		String text = restTemplate.postForObject(url, null, String.class);
		// 2.2从response中提取key=object的value 
		Map obj = JSON.parseObject(text,Map.class);
		// 2.3响应结果解码 
		ResponseDecoder decoder = new ResponseDecoder(key);
		boolean decoded = decoder.decode(obj.get("object").toString()); 
		if(decoded){    
			// 2.4 当解码成功时，获得上层业务数据    
			String data = decoder.getData();
			//2.5  当获得的类似的json数据有值时， 对该数据做一定处理，最终获得标准的json字符串
			if(data != null && !"".equals(data)){
				data = data.substring(1,data.length()-1);
				data = data.replaceAll("\\\\","");
				JSONObject json = JSONObject.parseObject(data);
				logger.info("运营中心-调用平安审核接口，返回数据" + (json!=null?json.toString():""));
				boolean rs = json.getBoolean("success");
				if(rs){
					return msg;
				}else{
					msg = json.getString("resultMsg");						
				}
			}else{
				msg = "获取data的值为" + data;
			}
		}else{
			msg = "审核失败！！接口秘钥配置错误，请联系开发人员！！！";
			logger.info("审核平安订单接口秘钥配置错误，url：" + url+",返回结果：" + text);
			throw new RuntimeException(msg);
		}
		return msg;
	}
}
