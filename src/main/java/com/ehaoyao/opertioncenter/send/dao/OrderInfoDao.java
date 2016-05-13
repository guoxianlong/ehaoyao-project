package com.ehaoyao.opertioncenter.send.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.ordercenter.orderModel.OrderDetail;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderDataVO;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderParamVO;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;

/**
 * Title: OrderInfoDao.java
 * 
 * Description: 订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:57:44
 */
//public interface OrderInfoDao extends BaseDao<OrderInfo, Long>{
public interface OrderInfoDao {

	/**
	 * 按短信规则获取订单信息
	 */
	public List<OrderInfoVO> getOrders(ShortMessageRuleVO<OrderInfoVO> vo);

	/**
	 * 按短信规则获取订单总数
	 */
	public int getOrdersCount(ShortMessageRuleVO<OrderInfoVO> vo);
	
	/**
	 * 按订单信息查询订单
	 */
	public List<OrderInfoVO> getOrder(OrderInfoVO vo);

	/**
	 * 获取拆单物流信息
	 */
	public List<OrderInfoVO> getExpressInfoRemoves(OrderInfoVO vo);

	/**
	 * 获取退款订单总数
	 */
	public int getOrderRefundsCount(ShortMessageRuleVO<OrderInfoVO> vo);

	/**
	 * 获取退款订单
	 */
	public List<OrderInfoVO> getOrderRefunds(ShortMessageRuleVO<OrderInfoVO> vo);
	
	/**
	 * 查询极速达订单
	 */
	public List<OrderDataVO> getOrderData(PageModel<OrderDataVO> pm,OrderParamVO orderVO);
	
	/**
	 * 查询订单总数
	 */
	public int getOrderDataCount(OrderParamVO orderVO);
	
	/**
	 * 查询订单详情
	 */
	public List<OrderDetail> getOrderDetails(OrderParamVO orderVO);
	
}