package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 每笔订单价格
 * @author xushunxing
 *
 */
public class PAOrderPriceDO {
	private double actualPostFee;
	private double discountFee;
	private double orderPrice;
	private double postFee;
	public double getActualPostFee() {
		return actualPostFee;
	}
	public void setActualPostFee(double actualPostFee) {
		this.actualPostFee = actualPostFee/100;
	}
	public double getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(double discountFee) {
		this.discountFee = discountFee/100;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice/100;
	}
	public double getPostFee() {
		return postFee;
	}
	public void setPostFee(double postFee) {
		this.postFee = postFee/100;
	}
}
