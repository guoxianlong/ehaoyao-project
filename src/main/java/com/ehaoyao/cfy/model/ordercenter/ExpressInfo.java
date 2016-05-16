package com.ehaoyao.cfy.model.ordercenter;

public class ExpressInfo {
    private Long id;

    private String expressId;

    private String expressComId;

    private String expressComCode;

    private String expressComName;

    private String orderNumber;

    private Double expressPrice;

    private String deliveryDateType;

    private String deliveryType;

    private String expressStatus;

    private String deliveryDate;

    private String deliveryNotice;

    private String remark;

    private String distributionCenterName;

    private String pickingCode;

    private String distributionStationName;

    private Integer productsCount;

    private String outboundTime;

    private String orderFlag;

    private String jdTradeNo;

    private String startTime;

    private String shuoldPay;

    private String payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId == null ? null : expressId.trim();
    }

    public String getExpressComId() {
        return expressComId;
    }

    public void setExpressComId(String expressComId) {
        this.expressComId = expressComId == null ? null : expressComId.trim();
    }

    public String getExpressComCode() {
        return expressComCode;
    }

    public void setExpressComCode(String expressComCode) {
        this.expressComCode = expressComCode == null ? null : expressComCode.trim();
    }

    public String getExpressComName() {
        return expressComName;
    }

    public void setExpressComName(String expressComName) {
        this.expressComName = expressComName == null ? null : expressComName.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Double getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(Double expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getDeliveryDateType() {
        return deliveryDateType;
    }

    public void setDeliveryDateType(String deliveryDateType) {
        this.deliveryDateType = deliveryDateType == null ? null : deliveryDateType.trim();
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType == null ? null : deliveryType.trim();
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus == null ? null : expressStatus.trim();
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    public String getDeliveryNotice() {
        return deliveryNotice;
    }

    public void setDeliveryNotice(String deliveryNotice) {
        this.deliveryNotice = deliveryNotice == null ? null : deliveryNotice.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDistributionCenterName() {
        return distributionCenterName;
    }

    public void setDistributionCenterName(String distributionCenterName) {
        this.distributionCenterName = distributionCenterName == null ? null : distributionCenterName.trim();
    }

    public String getPickingCode() {
        return pickingCode;
    }

    public void setPickingCode(String pickingCode) {
        this.pickingCode = pickingCode == null ? null : pickingCode.trim();
    }

    public String getDistributionStationName() {
        return distributionStationName;
    }

    public void setDistributionStationName(String distributionStationName) {
        this.distributionStationName = distributionStationName == null ? null : distributionStationName.trim();
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public String getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(String outboundTime) {
        this.outboundTime = outboundTime == null ? null : outboundTime.trim();
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getJdTradeNo() {
        return jdTradeNo;
    }

    public void setJdTradeNo(String jdTradeNo) {
        this.jdTradeNo = jdTradeNo == null ? null : jdTradeNo.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getShuoldPay() {
        return shuoldPay;
    }

    public void setShuoldPay(String shuoldPay) {
        this.shuoldPay = shuoldPay == null ? null : shuoldPay.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }
}