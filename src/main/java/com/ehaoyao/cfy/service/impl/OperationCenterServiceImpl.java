package com.ehaoyao.cfy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.cfy.mapper.operationcenter.OrderAuditLogMapper;
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
		System.out.println(list.get(0));
		return null;
	}

}
