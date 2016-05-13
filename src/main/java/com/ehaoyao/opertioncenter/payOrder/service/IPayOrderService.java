package com.ehaoyao.opertioncenter.payOrder.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderInfoDetailVO;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderShowInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface IPayOrderService {
	public PageModel<OrderShowInfo> getOrderInfos(PageModel<OrderShowInfo> pm,OrderInfoDetailVO vo) throws Exception;
	public List<OrderDetail> getOrderDetails(OrderInfoDetailVO vo) throws Exception;
	public List<OrderShowInfo> getOrderFlag();
}
