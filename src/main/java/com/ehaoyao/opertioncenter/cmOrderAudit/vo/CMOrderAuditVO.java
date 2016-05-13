package com.ehaoyao.opertioncenter.cmOrderAudit.vo;

public class CMOrderAuditVO {

	/**
	 * 序号
	 */
	private int seq;
	/**
	 * 订单号
	 */
	private Long orderNumber;
	/**
	 * 订单开始日期
	 */
	private String startDate;
	/**
	 * 订单结束日期
	 */
	private String endDate;

	/**
	 * 姓名
	 */
	private String receiver;
	/**
	 * 电话
	 */
	private String custTel;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 订单金额
	 */
	private String price;
	/**
	 * 支付方式
	 */
	private String payMode;
	/**
	 * 付款状态
	 */
	private String payStatus;
	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 客服工号
	 */
	private String custServCode;

	/**
	 * 审核状态
	 */
	private String reviewStatus;
	/**
	 * 审核人
	 */
	private String auditor;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustServCode() {
		return custServCode;
	}

	public void setCustServCode(String custServCode) {
		this.custServCode = custServCode;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

}
