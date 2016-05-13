package com.ehaoyao.opertioncenter.payOrder.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderInfoDetailVO;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderShowInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;


public interface IPayOrderDao {

	
	
	public int getCountOrderInfos(OrderInfoDetailVO vo)  throws Exception;
	public List<OrderShowInfo> getOrderInfos(PageModel<OrderShowInfo> pm, OrderInfoDetailVO vo) throws Exception;
	public List<OrderDetail> getOrderDetails(OrderInfoDetailVO vo)  throws Exception;
	public List<OrderShowInfo> getOrderFlag();
}
