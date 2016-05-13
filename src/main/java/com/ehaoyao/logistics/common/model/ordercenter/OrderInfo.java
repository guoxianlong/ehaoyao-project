package com.ehaoyao.logistics.common.model.ordercenter;

import java.util.Date;
/**
 * 订单中心，主订单表
 * @author longshanw
 *
 */
public class OrderInfo {
	
	/******************************订单中心-订单状态描述****************************************/
	/**
	 * s00	订单初始状态
	 */
	public static final String ORDER_INFO_ORDER_STATUS_INIT = "s00";
	/**
	 * s01	出货成功(已有运单号)
	 */
	public static final String ORDER_INFO_ORDER_STATUS_POST = "s01";
	/**
	 * s02	运单信息已推送
	 */
	public static final String ORDER_INFO_ORDER_STATUS_SEND = "s02";
	/**
	 * s03	交易完成
	 */
	public static final String ORDER_INFO_ORDER_STATUS_END = "s03";
	/**
	 * s04	订单取消
	 */
	public static final String ORDER_INFO_ORDER_STATUS_CANCEL = "s04";
	
	/**
	 * 订单表主键id
	 */
    private Long id;

    /**
     * 平台id
     */
    private String originalId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单时间
     */
    private String startTime;

    /**
     * 订单配送完成时间
     */
    private String expireTime;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 用户支付金额
     */
    private Double price;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货人详细地址
     */
    private String addressDetail;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 配送日期
     */
    private String deliveryDate;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String country;

    /**
     * 订单金额
     */
    private Double orderPrice;

    /**
     * 优惠金额
     */
    private Double discountAmount;

    /**
     * 运费
     */
    private Double expressPrice;

    /**
     * 渠道标识
     */
    private String orderFlag;

    /**
     * 满返满送
     */
    private String overReturnFree;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 地址别名
     */
    private String addressAlias;

    /**
     * 
     */
    private String appSignature;

    /**
     * 订单状态  s00:初始 s01:已发货 s02:物流信息通知平台 s03:交易完成(配送完成) s04:取消退款
     */
    private String orderStatus;

    /**
     * 付款类型
     */
    private String feeType;

    /**
     * 是否推送至erp 0:否 1:是
     */
    private String toErp;

    /**
     * 支付时间
     */
    private String paymentTime;

    private Date toOrdercenterTime;

    /**
     * 至erp时间
     */
    private String toErpTime;

    /**
     * 最后更新时间
     */
    private Date lastTime;

    /**
     * 客服工号
     */
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