package com.ehaoyao.opertioncenter.payOrder.vo;

public class OrderInfoDetailVO {
	/**
	 * 序号
	 */
	private int seq;
	/**
	 * 订单号
	 */
	private String orderNumber;
	
	/**
	 * 渠道标识
	 */
	private String orderFlag;
	
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
	private String payType;
	
	/**
	 * 处方类型
	 */
	private Character prescriptionType;
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
	private String kf_account;

	/**
	 * 操作状态
	 */
	private String oprStatus;
	
	/**
	 * 根据orderBy字段排序
	 */
	private String orderBy;
	
	/**
	 * sort : asc/desc
	 */
	private String sort;
	
	
    private String remark;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Character getPrescriptionType() {
		return prescriptionType;
	}

	public void setPrescriptionType(Character prescriptionType) {
		this.prescriptionType = prescriptionType;
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


	


	public String getKf_account() {
		return kf_account;
	}

	public void setKf_account(String kf_account) {
		this.kf_account = kf_account;
	}

	public String getOprStatus() {
		return oprStatus;
	}

	public void setOprStatus(String oprStatus) {
		this.oprStatus = oprStatus;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}   
    
}
