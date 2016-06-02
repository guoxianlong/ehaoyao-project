package com.ehaoyao.opertioncenter.thirdOrderAudit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.thirdOrderAudit.dao.IThirdOrderAuditDao;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.InvoiceInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderAuditLog;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.service.IThirdOrderAuditService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.galaxy.pop.api.client.DefaultPopApiClient;
import com.galaxy.pop.api.client.PopApiClient;
import com.galaxy.pop.api.client.model.Trade;
import com.galaxy.pop.api.client.request.PrescriptionAuditRequest;
import com.galaxy.pop.api.client.request.TradeFullinfoGetRequest;
import com.galaxy.pop.api.client.response.PrescriptionAuditResponse;
import com.galaxy.pop.api.client.response.TradeFullInfoGetResponse;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.User;
import com.pajk.openapi.codec.client.RequestEncoder;
import com.pajk.openapi.codec.client.RequestEntity;
import com.pajk.openapi.codec.client.ResponseDecoder;

@Service
public class ThirdOrderAuditServiceImpl implements IThirdOrderAuditService {
	
	private static final Logger logger = Logger.getLogger(ThirdOrderAuditServiceImpl.class);
	private Properties thirdPlatCFYConf = PropertiesUtil.getPropertiesFile("thirdPlatCFYConf.properties");
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
	public Object[] updateAuditStatus(ThirdOrderAuditVO vo) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * retObj[0] boolean值   true 表示有问题  false表示没问题 
		 * retObj[1] 返回信息
		 */
		Object[] retObj = new Object[2];
		/**
		 * 备注：审核通过，则审核日志表及发票表，记录客服更改的信息，驳回则为订单默认信息
		 */
		
		String auditStatus = vo.getAuditStatus()!=null&&vo.getAuditStatus().trim().length()>0?vo.getAuditStatus().trim():"";
		String orderNumber = vo.getOrderNumber()!=null&&vo.getOrderNumber().trim().length()>0?vo.getOrderNumber().trim():"";
		String auditDescription = vo.getAuditDescription()!=null&&vo.getAuditDescription().trim().length()>0?vo.getAuditDescription().trim():"";
		String invoiceStatus = vo.getInvoiceStatus()!=null&&vo.getInvoiceStatus().trim().length()>0?vo.getInvoiceStatus().trim():"";
		String rejectType = vo.getRejectType()!=null?vo.getRejectType().trim():"";
		String remark = vo.getRemark()!=null&&vo.getRemark().trim().length()>0?vo.getRemark().trim():"";
		
		String currDate = sdf.format(new Date());
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		InvoiceInfo invoiceInfo = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
		List<InvoiceInfo> invoiceInfoList = new ArrayList<InvoiceInfo>();
			
		User user = getCurrentUser();

			//更新订单表
			orderInfo =  (OrderInfo) iThirdOrderDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
			
			//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
			if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(orderInfo.getAuditStatus().trim())){
				retObj[0] = true;
				retObj[1] = "订单："+orderNumber+"，审批失败，已被其他客服审批，请返回查看详情！";
				return retObj;
			}
			
			orderInfo.setAuditStatus(auditStatus);
			orderInfo.setLastTime(currDate);
			//客服可更改订单备注及发票信息
			if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(auditStatus)){
				orderInfo.setInvoiceStatus(vo.getInvoiceStatus());
				orderInfo.setRemark(remark);
			}
			
			
			//订单审核轨迹记录
			orderAuditLog = new OrderAuditLog();
			orderAuditLog.setOrderNumber(orderNumber);
			orderAuditLog.setOrderFlag(vo.getOrderFlag());
			orderAuditLog.setAuditUser(user.getName());
			orderAuditLog.setKfAccount(user.getUserName());
			orderAuditLog.setAuditStatus(auditStatus);
			if(!"-1".equals(rejectType) && rejectType.length()>0){
				orderAuditLog.setRejectType(rejectType);
			}
			orderAuditLog.setAuditDescription(auditDescription);
			orderAuditLog.setCreateTime(currDate);
			if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(auditStatus)){
				orderAuditLog.setRemark(remark);
			}else{
				orderAuditLog.setRemark(orderInfo.getRemark());
			}
			
			/**
			 * 通知并回写三方平台审核状态及审核说明
			 */
			retObj = writeBackThirdPlatAuditInfo(orderAuditLog);
			
			
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
			
			orderInfoList.add(orderInfo);
			orderAuditLogList.add(orderAuditLog);
			
			if(!((boolean) retObj[0])){
				retObj[1] = "订单："+orderNumber+"，审批成功！";
			}else{
				throw new RuntimeException((String) retObj[1]);
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
			
			
		return retObj;
	}
	
	/**
	 * 通知并回写三方平台审核状态及审核说明
	 * @param orderAuditLogList
	 */
	public Object[] writeBackThirdPlatAuditInfo(OrderAuditLog orderAuditLog) throws Exception{
		/**
		 * retObj[0] boolean值   true表示有问题需要提示   false表示没问题可以进行调用360接口回写审核状态
		 * retObj[1] 需要返回的提示信息
		 */
		Object retStr[] = new Object[2];
		String orderFlag  = orderAuditLog.getOrderFlag()!=null?orderAuditLog.getOrderFlag().trim():"";
		String auditStatus = orderAuditLog.getAuditStatus()!=null?orderAuditLog.getAuditStatus().trim():"";
		String orderNumber = orderAuditLog.getOrderNumber()!=null?orderAuditLog.getOrderNumber().trim():"";
		/**
		 * 回写360健康审核信息，同天猫处方药一样，需客服/药师，二级审核。
		 */
		if(OrderInfo.ORDER_ORDER_FLAG_360CFY.equals(orderFlag)){
			Object[] ret = get360LastStatus(orderNumber);
			if(!((boolean) ret[0])){
				retStr[1] = writeBack360CFY(orderAuditLog);
			}else{
				if(OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(auditStatus)){
					retStr[0] = false;
				}else{
					retStr[0] = true;
				}
				retStr[1] = ret[1];
			}
		}
		/**
		 * 平安处方药，业务规定只需二级药师审核
		 * 平安处方药接口限制：审核信息不能为空
		 */
		if(OrderInfo.ORDER_ORDER_FLAG_PA.equals(orderAuditLog.getOrderFlag())){
			retStr[0] = false;
			writeBackPACFY(orderAuditLog);
		}
		return retStr;
	}

	private String writeBackPACFY(OrderAuditLog orderAuditLog) throws Exception {
		String remark = "";//审核说明
		String auditDescription = orderAuditLog.getAuditDescription()!=null?orderAuditLog.getAuditDescription().trim():"";
		String rejectType = orderAuditLog.getRejectType()!=null?orderAuditLog.getRejectType().trim():"";
		String auditStatus = orderAuditLog.getAuditStatus()!=null?orderAuditLog.getAuditStatus().trim():"";
		if(OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(auditStatus)){
			remark = getRejectReason(rejectType);
		}
		if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(auditStatus)){
			if(auditDescription.length()>0){
				remark = auditDescription;
			}else{
				remark = "药师审核通过";
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNumber", orderAuditLog.getOrderNumber());
		map.put("auditStatus", orderAuditLog.getAuditStatus());
		map.put("auditDescription", remark);
		String resultMsg = this.auditPAOrder(map);
		return resultMsg;
	}
	
	/**
	 * 获取360平台最新的订单状态、审核状态
	 * @param orderAuditLog
	 * @return
	 * @throws Exception
	 */
	public Object[] get360LastStatus(String orderNumber) throws Exception {
		/**
		 * retObj[0] boolean值   true表示有问题需要提示不需调用360审核接口   false表示没问题可以进行调用360接口回写审核状态
		 * retObj[1] 需要返回的提示信息
		 */
		Object[] retObj = new Object[2];
		retObj[0] = false;
		/**
		 * 商家id
		 */
		String venderId = thirdPlatCFYConf.getProperty("360jkc.venderId");
		String fields = thirdPlatCFYConf.getProperty("fields");
		TradeFullinfoGetRequest request = new TradeFullinfoGetRequest();
		request.setTid(orderNumber);
		request.setVenderId(venderId);
		request.setFields(fields);
		PopApiClient client = get360ApiClient();
		TradeFullInfoGetResponse response = client.execute(request);
		Trade trade = response.getTrade();
		String status = trade.getStatus()!=null?trade.getStatus().trim():"";
		String pdState = trade.getPdState()!=null?trade.getPdState().trim():"";
		String haveCfy = trade.getHaveCFY()!=null?trade.getHaveCFY().trim():"";
		logger.info("获取订单"+orderNumber+",360平台返回信息"+trade.toString());
		if(status.length()>0 && "TRADE_CLOSED".equals(status) && "1".equals(haveCfy)){
			if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(status) || OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(status)){
				retObj[0] = true;
				retObj[1] = "订单:"+orderNumber+",在卖家平台是已关闭的状态,请进行驳回操作！";
			}
			if(OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(status)){
				retObj[0] = true;
			}
		}

		if(pdState.length()>0 && "1".equals(haveCfy)){
			if("AUDIT_NOT_THROUGH".equals(pdState)){
				retObj[0] = true;
				retObj[1] = "订单:"+orderNumber+",在卖家平台是已驳回的状态！";
			}
			if("APPROVED".equals(pdState)){
				retObj[0] = true;
				retObj[1] = "订单:"+orderNumber+",在卖家平台是已审核通过的状态！";
			}
		}
		/*if((boolean) retObj[0] && retObj[1]!=null){
			throw new RuntimeException((String) retObj[1]);
		}*/
		return retObj;
	}

	/**
	 * 回写360处方药审核状态
	 * @param orderAuditLog
	 * @throws Exception 
	 */
	private String writeBack360CFY(OrderAuditLog orderAuditLog) throws Exception {
		/**
		 * 返回信息
		 */
		String retStr = "";
		
		/**
		 * 如果审核状态为客服审核通过或者药师审核驳回的话，则不调用360健康接口，即不回写审核状态；
		 * 只在客服审核驳回和药师审核通过才进行回写审核状态(药师审核驳回，客服可更改后再次提交药师审核)
		 */
		if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(orderAuditLog.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(orderAuditLog.getAuditStatus().trim())){
			return null;
		}
		
		String auditStatus = orderAuditLog.getAuditStatus().trim();
		/**
		 * 商家id
		 */
		String venderId = thirdPlatCFYConf.getProperty("360jkc.venderId");
		/**
		 * 处方药审核状态：0 审核不通过  1 审核通过（必填）
		 */
		int state = 0;
		ThirdOrderAuditVO vo = new ThirdOrderAuditVO();
		vo.setOrderFlag(orderAuditLog.getOrderFlag());
		vo.setOrderNumber(orderAuditLog.getOrderNumber());
		
		
		PopApiClient client = get360ApiClient();
		PrescriptionAuditRequest request =  new PrescriptionAuditRequest();
		if(OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(auditStatus)){
			state = 0;
			request.setReason(orderAuditLog.getRejectType());
		}
		if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(auditStatus)){
			state = 1;
		}
		request.setVenderId(venderId);
		request.setTid(orderAuditLog.getOrderNumber());
		request.setState(state);
		PrescriptionAuditResponse response = client.execute(request);
		System.out.println("Body is : " + response.getBody());
		System.out.println("Error is : " + response.getErrorCode());
		
		retStr = response.getBody();
		JSONObject json = JSONObject.parseObject(retStr);
		JSONObject rootJson = JSONObject.parseObject(json.getString("root"));
		String errorCode = rootJson.getString("errorCode");
//		String SubCode = rootJson.getString("subCode");
		if(errorCode!=null && errorCode.trim().length()>0 && !"null".equals(errorCode.trim())){
//		if(StringUtils.isNotBlank(errorCode) && "SUCCESS".equals(SubCode))
			throw new RuntimeException(retStr);
		}
		
		return retStr;
	}
	
	/**
	 * 封装360请求client
	 * @return
	 * @throws Exception
	 */
	public PopApiClient get360ApiClient() throws Exception{
		/**
		 * 请求地址url
		 */
		String url = thirdPlatCFYConf.getProperty("360cfy_url");
		/**
		 * 商家key
		 */
		String appId = thirdPlatCFYConf.getProperty("360jkc.appId");
		/**
		 * 商家密钥
		 */
		String appkey = thirdPlatCFYConf.getProperty("360jkc.appkey");
		PopApiClient client = new DefaultPopApiClient(url,  appId,appkey);
		return client;
	}
	

	/**
	 * 匹配并获取获取审核驳回描述
	 * @param rejectType
	 * @return
	 */
	private String getRejectReason(String rejectType) {
		if(rejectType==null || rejectType.length()==0){
			return null;
		}
		String reason = rejectType;
		if("F1E".equals(rejectType)){
			reason = "配送不到";
		}
		if("F1F".equals(rejectType)){
			reason = "价格贵";
		}
		if("F1G".equals(rejectType)){
			reason = "顾客买错";
		}
		if("F1H".equals(rejectType)){
			reason = "无货";
		}
		if("F1I".equals(rejectType)){
			reason = "无处方单";
		}
		if("F1J".equals(rejectType)){
			reason = "价格错误";
		}
		if("F1K".equals(rejectType)){
			reason = "文描错误";
		}
		if("F1L".equals(rejectType)){
			reason = "电话不通";
		}
		if("F1M".equals(rejectType)){
			reason = "付款方式";
		}
		return reason;
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
		String partnerId = thirdPlatCFYConf.getProperty("partnerId");
		// 1.1请求响应报文使用 
		String key = thirdPlatCFYConf.getProperty("key"); 
		// apiId，            平安平台还未开发出测试接口
		String apiId = thirdPlatCFYConf.getProperty("apiId"); 
		String apiName = thirdPlatCFYConf.getProperty("apiName"); 
		Long userVenderId = new Long(thirdPlatCFYConf.getProperty("userVenderId"));
		String apiGroup = thirdPlatCFYConf.getProperty("apiGroup");
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
		String url = thirdPlatCFYConf.getProperty("url");
		url += apiGroup+"/"+apiName + "?" + re.getQueryParams();
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
				return json.toJSONString();
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
