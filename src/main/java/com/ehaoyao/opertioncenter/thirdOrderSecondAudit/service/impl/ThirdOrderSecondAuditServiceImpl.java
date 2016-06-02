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
	public Object[] updateAuditStatus(ThirdOrderAuditVO vo) throws Exception {
		/**
		 * retObj[0] boolean值   true 表示有问题  false表示没问题 
		 * retObj[1] 返回信息
		 */
		Object[] retObj = new Object[2];
		retObj[0] = false;
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
			//更新订单表
			orderInfo =  (OrderInfo) iThirdOrderSecondAuditDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
			//如果原订单审核状态为PRESUCC,则记录并返回已审核的提示
			if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(orderInfo.getAuditStatus().trim())||OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(orderInfo.getAuditStatus().trim())){
				retObj[0] = true;
				retObj[1] = "订单："+orderNumber+"，审批失败，已被其他药师审批，请返回查看详情！";
				return retObj;
			}
			orderInfo.setAuditStatus(auditStatus);
			orderInfo.setLastTime(currDate);
			User user = getCurrentUser();
			
	
			
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
			retObj = iThirdOrderAuditService.writeBackThirdPlatAuditInfo(orderAuditLog);
			
			orderAuditLogList.add(orderAuditLog);
			orderInfoList.add(orderInfo);
			
			if((boolean) retObj[0]){
				throw new RuntimeException((String) retObj[1]);
			}else{
				retObj[1] = "订单："+orderNumber+"，审批成功！";
			}
			
			if(!orderInfoList.isEmpty()){
				orderInfoList = CommonUtils.removeDuplicate(orderInfoList);
				iThirdOrderSecondAuditDao.updateOrderInfoBatch(orderInfoList);
			}
			if(!orderAuditLogList.isEmpty()){
				orderAuditLogList = CommonUtils.removeDuplicate(orderAuditLogList);
				iThirdOrderSecondAuditDao.addOrderAuditLogBatch(orderAuditLogList);
			}
			
		return retObj;
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
		String noAllowedBatchDealNums = "";//不允许批量审核的订单，需手工数量
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
				/**
				 * 回写三方平台判断，主要验证三方平台客户取消的订单，需客服进行驳回操作，同时不再进行调用三方审核状态回写接口
				 */
				Object[] retObj = iThirdOrderAuditService.writeBackThirdPlatAuditInfo(orderAuditLog);
				if((boolean) retObj[0]){
					noAllowedBatchDealNums +=orderAuditLog.getOrderNumber()+"、";
					continue;
				}
			} catch (Exception e) {
				auditErrNum+=orderAuditLog.getOrderNumber()+"、";
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
		if(hasAuditOrders!=null && hasAuditOrders.trim().length()>0){
			retStr += "已被其他药师审批订单："+hasAuditOrders+"请返回查看订单详情。";
		}
		if(auditErrNum.length()>0){
			retStr +="调用三方接口失败导致审核失败订单号："+auditErrNum.substring(0, auditErrNum.length()-1)+"!";
		}
		if(noAllowedBatchDealNums.trim().length()>0){
			retStr += "需手工处理的订单："+noAllowedBatchDealNums.substring(0, noAllowedBatchDealNums.length()-1)+"!";
		}
		
		return retStr;
	}
	
	@Override
	public OrderMainInfo getOrderMainInfo(ThirdOrderAuditVO vo) throws Exception {
		OrderMainInfo orderMainInfo = iThirdOrderSecondAuditDao.getOrderMainInfo(vo);
		return orderMainInfo;
	}
}
