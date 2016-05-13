package com.ehaoyao.opertioncenter.returnGoods.vo;

import java.io.Serializable;

/**
 * 退货订单
 * @author zhang
 *
 */
public class GoodsInfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private int id;
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
	 * 订单类型
	 */
	private String orderFlag;
	/**
	 * 姓名
	 */
	private String receiver;
	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 订单金额
	 */
	private String price;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 付款状态
	 */
	private String payStatus;
	/**
	 * 付款金额
	 */
	private String orderPrice;
	/**
	 * 审核状态
	 */
	private String reviewStatus;
	/**
	 * 客服
	 */
	private String kfAcount;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the orderNumber
	 */
	public Long getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the orderFlag
	 */
	public String getOrderFlag() {
		return orderFlag;
	}
	/**
	 * @param orderFlag the orderFlag to set
	 */
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}
	/**
	 * @param payStatus the payStatus to set
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * @return the orderPrice
	 */
	public String getOrderPrice() {
		return orderPrice;
	}
	/**
	 * @param orderPrice the orderPrice to set
	 */
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	/**
	 * @return the reviewStatus
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}
	/**
	 * @param reviewStatus the reviewStatus to set
	 */
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	/**
	 * @return the kfAcount
	 */
	public String getKfAcount() {
		return kfAcount;
	}
	/**
	 * @param kfAcount the kfAcount to set
	 */
	public void setKfAcount(String kfAcount) {
		this.kfAcount = kfAcount;
	}
}
