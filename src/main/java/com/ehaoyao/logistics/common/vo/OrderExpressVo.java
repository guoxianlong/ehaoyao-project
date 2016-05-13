package com.ehaoyao.logistics.common.vo;
// Generated 2016-4-13 22:30:12 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * 用于返回封装查询实体
 */
public class OrderExpressVo implements java.io.Serializable {

	private static final long serialVersionUID = 1112740078126174810L;
	/**
	 * 物流表主键
	 */
	private Long id;
	/**
	 * 运单号
	 */
	private String expressId;
	/**
	 * 运单公司id
	 */
	private String expressComId;
	/**
	 * 运单公司编码
	 */
	private String expressComCode;
	/**
	 * 运单公司名称
	 */
	private String expressComName;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 运费
	 */
	private Double expressPrice;
	/**
	 * 配送日期类型
	 */
	private String deliveryDateType;
	/**
	 * 配送类型
	 */
	private String deliveryType;
	/**
	 * 物流状态
	 */
	private String expressStatus;
	/**
	 * 配送日期
	 */
	private String deliveryDate;
	/**
	 * 配送提醒
	 */
	private String deliveryNotice;
	/**
	 * 备注
	 */
	private String remark;
	private String distributionCenterName;
	private String pickingCode;
	private String distributionStationName;
	private int productsCount;
	private String outboundTime;
	/**
	 * 渠道标识
	 */
	private String orderFlag;
	private String jdTradeNo;
	/**
	 * 订单时间
	 */
	private String startTime;
	private String shuoldPay;
	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 订单状态 s00:订单初始化  s01:出货成功(已有运单号) s02:运单信息已推送 s03:交易完成 s04:订单取消
	 */
    private String orderStatus;
    /**
     * 拆单子单号
     */
    private String subordersn;
    /**
     * 商品编码
     */
    private String sku;
    private String toGw;
    /**
     * 拆单数量
     */
    private String split;
    /**
     * 订单中心order_info表中最后更新时间
     */
    private Date lastTime;
    

	    
	public OrderExpressVo() {
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


	public String getExpressComId() {
		return expressComId;
	}


	public void setExpressComId(String expressComId) {
		this.expressComId = expressComId;
	}


	public String getExpressComCode() {
		return expressComCode;
	}


	public void setExpressComCode(String expressComCode) {
		this.expressComCode = expressComCode;
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


	public int getProductsCount() {
		return productsCount;
	}


	public void setProductsCount(int productsCount) {
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


	public String getJdTradeNo() {
		return jdTradeNo;
	}


	public void setJdTradeNo(String jdTradeNo) {
		this.jdTradeNo = jdTradeNo;
	}


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


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSubordersn() {
		return subordersn;
	}


	public void setSubordersn(String subordersn) {
		this.subordersn = subordersn;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public String getToGw() {
		return toGw;
	}


	public void setToGw(String toGw) {
		this.toGw = toGw;
	}


	public String getSplit() {
		return split;
	}


	public void setSplit(String split) {
		this.split = split;
	}


	public Date getLastTime() {
		return lastTime;
	}


	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	
}
