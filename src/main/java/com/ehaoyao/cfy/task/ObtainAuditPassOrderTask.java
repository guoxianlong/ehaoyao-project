package com.ehaoyao.cfy.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.cfy.model.operationcenter.OrderInfo;
import com.ehaoyao.cfy.service.OperationCenterService;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;

@Component(value="obtainAuditPassOrderTask")
public class ObtainAuditPassOrderTask {

	@Autowired
	OperationCenterService operationCenterService;
	
//	@Autowired
//	CfyToOrderCenterService cfyToOrderCenterService;
	
	
	public void dealLogic(){
		try {
			OrderInfoVo orderInfoVo = queryParam();
			operationCenterService.selectAuditPassList(orderInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("..............");
	}
	
	/**
	 * 封装查询条件
	 * @return
	 */
	public OrderInfoVo queryParam(){
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_SUCC);
		orderInfoVo.setAuditTimeStart("2016-05-17 12:00:00");
		orderInfoVo.setAuditTimeEnd("2016-05-17 23:00:00");
		return orderInfoVo;
	}
}
