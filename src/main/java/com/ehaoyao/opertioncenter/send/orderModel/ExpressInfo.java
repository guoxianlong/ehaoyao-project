package com.ehaoyao.opertioncenter.send.orderModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Title: ExpressInfo.java
 * 
 * Description: 快递信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:01:42
 */
@Entity
@Table(name = "express_info")
public class ExpressInfo implements java.io.Serializable {
	private static final long serialVersionUID = 3600200870494922599L;
	
	@Id
	@Column(name = "id",nullable = false)
	private Long id;
	@Column(name = "express_id")
	private String expressId;
	@Column(name = "express_com_id")
	private String expressComId;
	@Column(name = "express_com_code")
	private String expressComCode;
	@Column(name = "express_com_name")
	private String expressComName;
	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "express_price")
	private Double expressPrice;
	@Column(name = "delivery_date_type")
	private String deliveryDateType;
	@Column(name = "delivery_type")
	private String deliveryType;
	@Column(name = "express_status")
	private String expressStatus;
	@Column(name = "delivery_date")
	private String deliveryDate;
	@Column(name = "delivery_notice")
	private String deliveryNotice;
	@Column(name = "remark")
	private String remark;
	@Column(name = "distribution_center_name")
	private String distributionCenterName;
	@Column(name = "picking_code")
	private String pickingCode;
	@Column(name = "distribution_station_name")
	private String distributionStationName;
	@Column(name = "products_count")
	private Integer productsCount;
	@Column(name = "outbound_time")
	private String outboundTime;
	@Column(name = "order_flag")
	private String orderFlag;
	@Column(name = "jd_trade_no")
	private String jdTradeNo;
	@Column(name = "start_time")
	private String startTime;
	@Column(name = "shuold_pay")
	private String shuoldPay;
	@Column(name = "pay_type")
	private String payType;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressId() {
		return this.expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressComId() {
		return this.expressComId;
	}

	public void setExpressComId(String expressComId) {
		this.expressComId = expressComId;
	}

	public String getExpressComCode() {
		return this.expressComCode;
	}

	public void setExpressComCode(String expressComCode) {
		this.expressComCode = expressComCode;
	}

	public String getExpressComName() {
		return this.expressComName;
	}

	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getExpressPrice() {
		return this.expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	public String getDeliveryDateType() {
		return this.deliveryDateType;
	}

	public void setDeliveryDateType(String deliveryDateType) {
		this.deliveryDateType = deliveryDateType;
	}

	public String getDeliveryType() {
		return this.deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getExpressStatus() {
		return this.expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryNotice() {
		return this.deliveryNotice;
	}

	public void setDeliveryNotice(String deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDistributionCenterName() {
		return this.distributionCenterName;
	}

	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}

	public String getPickingCode() {
		return this.pickingCode;
	}

	public void setPickingCode(String pickingCode) {
		this.pickingCode = pickingCode;
	}

	public String getDistributionStationName() {
		return this.distributionStationName;
	}

	public void setDistributionStationName(String distributionStationName) {
		this.distributionStationName = distributionStationName;
	}

	public Integer getProductsCount() {
		return this.productsCount;
	}

	public void setProductsCount(Integer productsCount) {
		this.productsCount = productsCount;
	}

	public String getOutboundTime() {
		return this.outboundTime;
	}

	public void setOutboundTime(String outboundTime) {
		this.outboundTime = outboundTime;
	}

	public String getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getJdTradeNo() {
		return this.jdTradeNo;
	}

	public void setJdTradeNo(String jdTradeNo) {
		this.jdTradeNo = jdTradeNo;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getShuoldPay() {
		return this.shuoldPay;
	}

	public void setShuoldPay(String shuoldPay) {
		this.shuoldPay = shuoldPay;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}