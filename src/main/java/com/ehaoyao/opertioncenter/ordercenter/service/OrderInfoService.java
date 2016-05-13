package com.ehaoyao.opertioncenter.ordercenter.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.ordercenter.orderModel.OrderDetail;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderDataVO;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderParamVO;

/**
 * 
 * Title: OrderInfoService.java
 * 
 * Description: 订单中心订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月23日 下午1:53:34
 */
public interface OrderInfoService {
	
	/**
	 * 查询订单
	 */
	public PageModel<OrderDataVO> getOrderData(PageModel<OrderDataVO> pm,OrderParamVO orderVO);
	
	/**
	 * 查询订单详情
	 */
	public List<OrderDetail> getOrderDetails(OrderParamVO orderVO);

	/**
	 * 新订单数量
	 */
	public int getNewOrderCount();
}
