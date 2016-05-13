package com.ehaoyao.opertioncenter.send.vo;

import java.util.Date;

/**
 * 
 * Title: OrderInfoVO.java
 * 
 * Description: 订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:04:47
 */
public class OrderInfoVO {
	// 订单信息
	private String orderNumber;
	private String receiver;
	private String mobile;
	private String orderFlag;// 订单标志
	private String orderStatus;
	private String toErp;
	private String payType;
	private Date toOrdercenterTime;
	private String toErpTime;
	private Date lastTime;
	// 快递信息
	private String expressId;
	private String expressComId;
	private String expressComCode;
	private String expressComName;
	private String deliveryDate;//发货日期
	
	//退款信息
	private String refundMoney;//退款金额
	private String refundTime;//退款时间
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getToErp() {
		return toErp;
	}

	public void setToErp(String toErp) {
		this.toErp = toErp;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getToOrdercenterTime() {
		return toOrdercenterTime;
	}

	public void setToOrdercenterTime(Date toOrdercenterTime) {
		this.toOrdercenterTime = toOrdercenterTime;
	}

	public String getToErpTime() {
		return toErpTime;
	}

	public void setToErpTime(String toErpTime) {
		this.toErpTime = toErpTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressComId() {
		return expressComId;
	}

	public void setExpressComId(String expressComId) {
		this.expressComId = expressComId;
	}

	public String getExpressComCode() {
		return expressComCode;
	}

	public void setExpressComCode(String expressComCode) {
		this.expressComCode = expressComCode;
	}

	public String getExpressComName() {
		return expressComName;
	}

	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

}
