package com.ehaoyao.cfy.model.ordercenter;

import java.util.Date;

public class OrderInfo {
	
	public static final String ORDER_INFO_ORDER_STATUS_INIT = "s00";
	
	public static final String ORDER_INFO_ORDER_STATUS_HASSHIP = "s01";
	
	public static final String ORDER_INFO_ORDER_STATUS_NOTICEPLAT = "s02";
	
	public static final String ORDER_INFO_ORDER_STATUS_FINISH = "s03";
	
    private Long id;

    private String originalId;

    private String orderNumber;

    private String startTime;

    private String expireTime;

    private String remark;

    private String payType;

    private Double price;

    private String receiver;

    private String addressDetail;

    private String mobile;

    private String telephone;

    private String deliveryDate;

    private String province;

    private String city;

    private String country;

    private Double orderPrice;

    private Double discountAmount;

    private Double expressPrice;

    private String orderFlag;

    private String overReturnFree;

    private String nickName;

    private String addressAlias;

    private String appSignature;

    private String orderStatus;

    private String feeType;

    private String toErp;

    private String paymentTime;

    private Date toOrdercenterTime;

    private String toErpTime;

    private Date lastTime;

    private String kfAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId == null ? null : originalId.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime == null ? null : expireTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(Double expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getOverReturnFree() {
        return overReturnFree;
    }

    public void setOverReturnFree(String overReturnFree) {
        this.overReturnFree = overReturnFree == null ? null : overReturnFree.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAddressAlias() {
        return addressAlias;
    }

    public void setAddressAlias(String addressAlias) {
        this.addressAlias = addressAlias == null ? null : addressAlias.trim();
    }

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature == null ? null : appSignature.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public String getToErp() {
        return toErp;
    }

    public void setToErp(String toErp) {
        this.toErp = toErp == null ? null : toErp.trim();
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime == null ? null : paymentTime.trim();
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
        this.toErpTime = toErpTime == null ? null : toErpTime.trim();
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount == null ? null : kfAccount.trim();
    }
}