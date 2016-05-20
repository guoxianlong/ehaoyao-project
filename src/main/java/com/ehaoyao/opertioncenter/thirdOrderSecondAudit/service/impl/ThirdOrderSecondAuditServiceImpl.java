package com.ehaoyao.opertioncenter.thirdOrderSecondAudit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.CommonUtils;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderAuditLog;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.service.IThirdOrderAuditService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.ehaoyao.opertioncenter.thirdOrderSecondAudit.dao.IThirdOrderSecondAuditDao;
import com.ehaoyao.opertioncenter.thirdOrderSecondAudit.service.IThirdOrderSecondAuditService;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.User;

@Service
public class ThirdOrderSecondAuditServiceImpl implements IThirdOrderSecondAuditService {

	private static final Logger logger = Logger.getLogger(ThirdOrderSecondAuditServiceImpl.class);
	
	@Autowired
	private IThirdOrderAuditService iThirdOrderAuditService;
	
	@Autowired
	private IThirdOrderSecondAuditDao iThirdOrderSecondAuditDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 三方订单二级医师审核查询
	 * @throws Exception 
	 */
	public PageModel<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm,ThirdOrderAuditVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iThirdOrderSecondAuditDao.getCountOrderInfos(vo);
			pm.setTotalRecords(count);
		}
		List<OrderMainInfo> ls = iThirdOrderSecondAuditDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception{
		return iThirdOrderSecondAuditDao.getOrderDetails(vo);
	}

	@Override
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception {
		Object retnObj = null;
		if(vo!=null){
			retnObj = iThirdOrderSecondAuditDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
		}
		return retnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateAuditStatus(ThirdOrderAuditVO vo) throws Exception {
		String auditStatus = vo.getAuditStatus()!=null?vo.getAuditStatus().trim():"";
		String orderNumber = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
		String auditDescription = vo.getAuditDescription()!=null?vo.getAuditDescription().trim():"";
		String rejectType = vo.getRejectType()!=null?vo.getRejectType().trim():"";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
			if(vo!=null){
				//更新订单表
				orderInfo =  (OrderInfo) iThirdOrderSecondAuditDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
				//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
				if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(orderInfo.getAuditStatus().trim())){
					return "订单："+orderNumber+"，审批失败，已被其他药师审批，请返回查看详情！";
				}
				orderInfo.setAuditStatus(auditStatus);
				orderInfo.setLastTime(currDate);
				User user = getCurrentUser();
				

				//调用平安接口，回写药师审核信息
				/*if(orderFlag.length()>0 && OrderInfo.ORDER_ORDER_FLAG_PA.equals(orderFlag)){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("orderNumber", orderNumber);
					map.put("auditStatus", auditStatus);
					map.put("auditDescription", auditDescription);
					String resultMsg = iThirdOrderAuditService.auditPAOrder(map);
					logger.info("【运营中心-医师二级审核-调用并回写平安订单审核接口，订单号："+orderNumber+"，返回信息：】"+resultMsg);
				}*/
				
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
				
				
				/**
				 * 通知并回写三方平台审核状态及审核说明
				 */
				String retStrMsg = iThirdOrderAuditService.writeBackThirdPlatAuditInfo(orderAuditLog);
				logger.info("【运营中心-回写三方平台审核信息！返回信息："+retStrMsg+"】");
				
				orderAuditLogList.add(orderAuditLog);
				orderInfoList.add(orderInfo);
			}
			
			if(!orderInfoList.isEmpty()){
				orderInfoList = CommonUtils.removeDuplicate(orderInfoList);
				iThirdOrderSecondAuditDao.updateOrderInfoBatch(orderInfoList);
			}
			if(!orderAuditLogList.isEmpty()){
				orderAuditLogList = CommonUtils.removeDuplicate(orderAuditLogList);
				iThirdOrderSecondAuditDao.addOrderAuditLogBatch(orderAuditLogList);
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
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		String hasAuditOrders = "";//如果原订单审核状态为SUCC/RETURN,则记录并返回已审核的提示
		String retStr = "";//返回信息
		String auditErrNum = "";
		for(int i=0;i<orderNumberFlagsParam.length;i++){
			
			//更新订单表
			orderInfo =  (OrderInfo) iThirdOrderSecondAuditDao.getOrderInfosByOrderNumFlags(orderNumberFlagsParam[i]);
			//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
			if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(orderInfo.getAuditStatus().trim())){
				hasAuditOrders += (orderInfo.getOrderNumber()+"、");
				continue;
			}
			orderInfo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_SUCC);
			orderInfo.setLastTime(currDate);
			
			
			//回写平安审核状态
			/*if(orderInfo.getOrderFlag()!=null && orderInfo.getOrderFlag().length()>0 && OrderInfo.ORDER_ORDER_FLAG_PA.equals(orderInfo.getOrderFlag().trim())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderNumber", orderInfo.getOrderNumber());
				map.put("auditStatus", OrderInfo.ORDER_AUDIT_STATUS_SUCC);
				map.put("auditDescription", "医师审核通过");
				try {
					String resultMsg = iThirdOrderAuditService.auditPAOrder(map);
					logger.info("【运营中心-医师二级审核-调用并回写平安订单审核接口，订单号："+orderInfo.getOrderNumber()+"，返回信息：】"+resultMsg);
				} catch (Exception e) {
					continue;
				}
			}*/
			
			User user = getCurrentUser();
			
			//订单审核轨迹记录
			orderAuditLog = new OrderAuditLog();
			orderAuditLog.setOrderNumber(orderInfo.getOrderNumber());
			orderAuditLog.setOrderFlag(orderInfo.getOrderFlag());
			orderAuditLog.setAuditUser(user.getName());
			orderAuditLog.setKfAccount(user.getUserName());
			orderAuditLog.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_SUCC);
			orderAuditLog.setCreateTime(currDate);
			
			/**
			 * 通知并回写三方平台审核状态及审核说明
			 */
			try {
				String retStrMsg = iThirdOrderAuditService.writeBackThirdPlatAuditInfo(orderAuditLog);
				logger.info("【运营中心-回写三方平台审核信息！返回信息："+retStrMsg+"】");
			} catch (Exception e) {
				auditErrNum+=orderAuditLog.getOrderNumber();
				logger.error("【运营中心-回写三方平台审核信息！异常！异常信息："+e.getMessage()+"】");
				continue;
			}
			
			orderAuditLogList.add(orderAuditLog);
			orderInfoList.add(orderInfo);
		}
		
		if(!orderInfoList.isEmpty()){
			orderInfoList = CommonUtils.removeDuplicate(orderInfoList);
			iThirdOrderSecondAuditDao.updateOrderInfoBatch(orderInfoList);
		}
		
		if(!orderAuditLogList.isEmpty()){
			orderAuditLogList = CommonUtils.removeDuplicate(orderAuditLogList);
			iThirdOrderSecondAuditDao.addOrderAuditLogBatch(orderAuditLogList);
		}
		
		retStr = "批量审批订单操作成功！成功审批"+(orderInfoList!=null&&!orderInfoList.isEmpty()?orderInfoList.size():0)+"条订单!";
		if(auditErrNum.length()>0){
			retStr +="调用三方接口失败导致审核失败订单号："+auditErrNum+"、";
		}
		if(hasAuditOrders!=null && hasAuditOrders.trim().length()>0){
			retStr += "已被其他药师审批订单："+hasAuditOrders+"请返回查看订单详情。";
		}
		
		return retStr;
	}
	
	@Override
	public OrderMainInfo getOrderMainInfo(ThirdOrderAuditVO vo) throws Exception {
		OrderMainInfo orderMainInfo = iThirdOrderSecondAuditDao.getOrderMainInfo(vo);
		return orderMainInfo;
	}
}
