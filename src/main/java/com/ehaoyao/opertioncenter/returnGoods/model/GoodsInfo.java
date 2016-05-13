package com.ehaoyao.opertioncenter.returnGoods.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退货订单
 * @author zhang
 *
 */
@Entity
@Table(name = "return_goods_info")
public class GoodsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	/**
	 * 订单号
	 */
	@Column(name = "order_number")
	private String orderNumber;
	/**
	 * 订单日期
	 */
	@Column(name = "start_time")
	private String startTime;
	/**
	 * 订单类型
	 */
	@Column(name = "order_flag")
	private String orderFlag;
	/**
	 * 姓名
	 */
	@Column(name = "receiver")
	private String receiver;
	/**
	 * 电话
	 */
	@Column(name = "mobile")
	private String mobile;
	/**
	 * 地址
	 */
	@Column(name = "address")
	private String address;
	/**
	 * 订单金额
	 */
	@Column(name = "price")
	private String price;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 支付方式
	 */
	@Column(name = "pay_type")
	private String payType;
	/**
	 * 订单状态
	 */
	@Column(name = "order_status")
	private String orderStatus;
	/**
	 * 付款状态
	 */
	@Column(name = "pay_status")
	private String payStatus;
	/**
	 * 付款金额
	 */
	@Column(name = "order_price")
	private String orderPrice;
	/**
	 * 审核状态
	 */
	@Column(name = "review_status")
	private String reviewStatus;
	/**
	 * 客服
	 */
	@Column(name = "kfAcount")
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
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
