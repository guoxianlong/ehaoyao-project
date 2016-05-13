package com.ehaoyao.yhdjkg.model.order_center;

/**
 * @author sjfeng
 *
 */
public class ExpressInfo {
	private Long id=0L;
	private String expressId="";
	private String expressComName="";
	private String orderNumber="";
	private Double expressPrice=0.0;
	private String deliveryDateType="";
	private String deliveryType="";
	private String expressStatus="";
	private String deliveryDate="";
	private String deliveryNotice="";
	private String remark="";
	private String distributionCenterName="";
	private String pickingCode="";
	private String distributionStationName="";
	private Integer productsCount=0;
	private String outboundTime="";
	private String orderFlag="";

	private String startTime="";
	private String shuoldPay="";
	private String payType="";

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getShuoldPay() {
		return shuoldPay;
	}

	public void setShuoldPay(String shuoldPay) {
		this.shuoldPay = shuoldPay;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

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
		this.expressId = expressId;
	}

	public String getExpressComName() {
		return expressComName;
	}

	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
		this.deliveryDateType = deliveryDateType;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryNotice() {
		return deliveryNotice;
	}

	public void setDeliveryNotice(String deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDistributionCenterName() {
		return distributionCenterName;
	}

	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}

	public String getPickingCode() {
		return pickingCode;
	}

	public void setPickingCode(String pickingCode) {
		this.pickingCode = pickingCode;
	}

	public String getDistributionStationName() {
		return distributionStationName;
	}

	public void setDistributionStationName(String distributionStationName) {
		this.distributionStationName = distributionStationName;
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
		this.outboundTime = outboundTime;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

}