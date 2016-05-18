package com.ehaoyao.cfy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.cfy.mapper.operationcenter.OrderAuditLogMapper;
import com.ehaoyao.cfy.model.operationcenter.OrderAuditLog;
import com.ehaoyao.cfy.model.operationcenter.OrderInfo;
import com.ehaoyao.cfy.service.OperationCenterService;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

@Transactional(value="transactionManagerOperationCenter")
@Service(value="operationCenterService")
public class OperationCenterServiceImpl implements OperationCenterService {

	@Autowired
	OrderAuditLogMapper orderAuditLogMapper;
	
	@Override
	public List<OrderMainInfo> selectAuditPassList(OrderInfoVo orderInfoVo) throws Exception {
		List<OrderMainInfo> list = orderAuditLogMapper.selectOrderMainInfo(orderInfoVo);
		/**
		 * 设置客服工号
		 */
		if(list==null || list.isEmpty()){
			return null;
		}
		OrderInfoVo vo;
		OrderAuditLog orderAuditLog;
		for(int i=0;i<list.size();i++){
			vo = new OrderInfoVo();
			vo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC);
			vo.setOrderFlag(list.get(i).getOrderFlag());
			vo.setOrderNumber(list.get(i).getOrderNumber());
			orderAuditLog = orderAuditLogMapper.selectByLastAudit(vo);
			list.get(i).getOrderInfo().setKfAccount(orderAuditLog.getKfAccount());
		}
		return list;
	}

}
