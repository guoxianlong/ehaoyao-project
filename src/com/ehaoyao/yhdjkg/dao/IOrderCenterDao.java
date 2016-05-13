package com.ehaoyao.yhdjkg.dao;

import java.util.List;

import com.ehaoyao.yhdjkg.model.order_center.ExpressInfo;
import com.ehaoyao.yhdjkg.model.order_center.InvoiceInfo;
import com.ehaoyao.yhdjkg.model.order_center.OrderDetail;
import com.ehaoyao.yhdjkg.model.order_center.OrderInfo;


public interface IOrderCenterDao {
	

	/**
	 * 根据订单编号查询是否存在
	 * @param orderNumber 	订单编号
	 * @param orderFlag		订单来源标识
	 * @return				true 存在 false 不存在
	 */
	public boolean isExistsByOrderId(String orderNumber,String orderFlag);
	
	
	/**
	 * 保存订单主表
	 * @param orderInfo		订单主信息
	 */
	public void doSaveOrder(OrderInfo orderInfo);
	
	/**保存订单明细表
	 * @param orderDetails
	 */
	public void doSaveOrderDetail(List<OrderDetail> orderDetails);
	
	/**
	 * 保存发票信息表
	 * @param invoiceInfo
	 */
	public void doSaveInvoice(InvoiceInfo invoiceInfo);
	
	/**
	 * 保存快递信息表
	 * @param expressInfo
	 */
	public void doSaveExpress(ExpressInfo expressInfo);
	
	/**
	 * 保存买家相关数据信息
	 * @param orderInfo
	 */
	public void doSaveBuyerInfo(OrderInfo orderInfo);
	/**
	 * 根据订单编号和订单标示查询订单信息
	 * @param tid 订单编号
	 * @param shopAlias 平台标示
	 * @return
	 */
	public boolean findOrderByOrderId(String tid, String shopAlias);
	
}
