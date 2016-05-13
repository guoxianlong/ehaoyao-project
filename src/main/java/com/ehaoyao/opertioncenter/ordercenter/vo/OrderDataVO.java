package com.ehaoyao.opertioncenter.ordercenter.vo;

/**
 * 
 * Title: OrderDataVO.java
 * 
 * Description: 极速达订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月30日 下午12:36:08
 */
public class OrderDataVO {

	private String orderNumber;
	private String startTime;
	private String remark;
	private String payType;
	private String receiver;
	private String addressDetail;
	private String mobile;
	private Double orderPrice;
	private String orderFlag;
	private String orderStatus;
	private String toErp;
	private String country;//地区
	//极速达订单状态，0:初始化 ，1:不在配送范围 ，2:不在配送时间内 ，3:已发货 ，4:已驳回
	private String jdsOrderStatus;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getJdsOrderStatus() {
		return jdsOrderStatus;
	}

	public void setJdsOrderStatus(String jdsOrderStatus) {
		this.jdsOrderStatus = jdsOrderStatus;
	}

}
