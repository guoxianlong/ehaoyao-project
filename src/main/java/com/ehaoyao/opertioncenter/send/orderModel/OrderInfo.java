package com.ehaoyao.opertioncenter.send.orderModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Title: OrderInfo.java
 * 
 * Description: 订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:02:09
 */
@Entity
@Table(name = "order_info")
public class OrderInfo implements java.io.Serializable {
	private static final long serialVersionUID = 7304512457245630263L;

	@Id
	@Column(name = "id",nullable = false)
	private Long id;
	@Column(name = "original_id")
	private String originalId;
	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "start_time")
	private String startTime;
	@Column(name = "expire_time")
	private String expireTime;
	@Column(name = "remark")
	private String remark;
	@Column(name = "pay_type")
	private String payType;
	@Column(name = "price")
	private Double price;
	@Column(name = "receiver")
	private String receiver;
	@Column(name = "address_detail")
	private String addressDetail;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "delivery_date")
	private String deliveryDate;
	@Column(name = "province")
	private String province;
	@Column(name = "city")
	private String city;
	@Column(name = "country")
	private String country;
	@Column(name = "order_price")
	private Double orderPrice;
	@Column(name = "discount_amount")
	private Double discountAmount;
	@Column(name = "express_price")
	private Double expressPrice;
	@Column(name = "order_flag")
	private String orderFlag;
	@Column(name = "over_return_free")
	private String overReturnFree;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "address_alias")
	private String addressAlias;
	@Column(name = "app_signature")
	private String appSignature;
	@Column(name = "order_status")
	private String orderStatus;
	@Column(name = "fee_type")
	private String feeType;
	@Column(name = "to_erp")
	private String toErp;
	@Column(name = "payment_time")
	private String paymentTime;
	@Column(name = "to_ordercenter_time")
	private String toOrdercenterTime;
	@Column(name = "to_erp_time")
	private String toErpTime;
	@Column(name = "last_time")
	private String lastTime;
	@Column(name = "kf_account")
	private String kfAccount;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalId() {
		return this.originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddressDetail() {
		return this.addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getExpressPrice() {
		return this.expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	public String getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOverReturnFree() {
		return this.overReturnFree;
	}

	public void setOverReturnFree(String overReturnFree) {
		this.overReturnFree = overReturnFree;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddressAlias() {
		return this.addressAlias;
	}

	public void setAddressAlias(String addressAlias) {
		this.addressAlias = addressAlias;
	}

	public String getAppSignature() {
		return this.appSignature;
	}

	public void setAppSignature(String appSignature) {
		this.appSignature = appSignature;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getToErp() {
		return this.toErp;
	}

	public void setToErp(String toErp) {
		this.toErp = toErp;
	}

	public String getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getToOrdercenterTime() {
		return toOrdercenterTime;
	}

	public void setToOrdercenterTime(String toOrdercenterTime) {
		this.toOrdercenterTime = toOrdercenterTime;
	}

	public String getToErpTime() {
		return this.toErpTime;
	}

	public void setToErpTime(String toErpTime) {
		this.toErpTime = toErpTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getKfAccount() {
		return this.kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

}