package com.ehaoyao.opertioncenter.cmOrderAudit.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.cmOrderAudit.dao.ICMOrderAuditDao;
import com.ehaoyao.opertioncenter.cmOrderAudit.service.ICMOrderAuditService;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderAuditLog;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.User;

@Service
public class CMOrderAuditServiceImpl implements ICMOrderAuditService {

	@Autowired
	private ICMOrderAuditDao iCMOrderAuditDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 煎药处订单查询
	 * @throws Exception 
	 */
	public PageModel<OrderInfo> getOrderInfos(PageModel<OrderInfo> pm,ThirdOrderAuditVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iCMOrderAuditDao.getCountOrderInfos(vo);
			pm.setTotalRecords(count);
		}
		List<OrderInfo> ls = iCMOrderAuditDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception{
		return iCMOrderAuditDao.getOrderDetails(vo);
	}


	
	@Override
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception {
		Object retnObj = null;
		if(vo!=null){
			retnObj = iCMOrderAuditDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
		}
		return retnObj;
	}


	@Override
	public String updateAuditStatus(ThirdOrderAuditVO vo) throws Exception {
		String auditStatus = vo.getAuditStatus()!=null?vo.getAuditStatus().trim():"";
		String orderNumber = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
		String auditDescription = vo.getAuditDescription()!=null?vo.getAuditDescription().trim():"";
		OrderInfo orderInfo = null;
		OrderAuditLog orderAuditLog = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate = sdf.format(new Date());
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderAuditLog> orderAuditLogList = new ArrayList<OrderAuditLog>();
		if(vo!=null){
				//更新订单表
				orderInfo =  (OrderInfo) iCMOrderAuditDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
				orderInfo.setAuditStatus(auditStatus);
				orderInfo.setLastTime(currDate);
				User user = getCurrentUser();
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
				orderAuditLogList.add(orderAuditLog);
				
				//更新发票表信息
				//TODO:
				
		}
		if(!orderInfoList.isEmpty()){
			iCMOrderAuditDao.updateOrderInfoBatch(orderInfoList);
		}
		if(!orderAuditLogList.isEmpty()){
			iCMOrderAuditDao.addOrderAuditLogBatch(orderAuditLogList);
		}
		return "订单号"+orderNumber+"，审核成功！";
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
}

