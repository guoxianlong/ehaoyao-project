package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.math.BigDecimal;

public class OrderInfoVO {
	/**
	 * 促销信息
	 */
	private String cheapInfo = "无";
	/**
	 * 邮费
	 */
	private String postageInfo = "0.00";
	/**
	 * 商品总价（含运费）
	 */
	private String totalAmtInfo = "0.00";
	/**
	 * 商品总价（不含运费）
	 */
	private BigDecimal totalAmount;
	/**
	 * 运费
	 */
	private BigDecimal postage;
	/**
	 * 优惠金额
	 */
	private BigDecimal cheapAmt;
	/**
	 * 订单总价
	 */
	private BigDecimal orderAmt;
	/**
	 * @return the cheapInfo
	 */
	public String getCheapInfo() {
		return cheapInfo;
	}
	/**
	 * @param cheapInfo the cheapInfo to set
	 */
	public void setCheapInfo(String cheapInfo) {
		this.cheapInfo = cheapInfo;
	}
	/**
	 * @return the postageInfo
	 */
	public String getPostageInfo() {
		return postageInfo;
	}
	/**
	 * @param postageInfo the postageInfo to set
	 */
	public void setPostageInfo(String postageInfo) {
		this.postageInfo = postageInfo;
	}
	/**
	 * @return the totalAmtInfo
	 */
	public String getTotalAmtInfo() {
		return totalAmtInfo;
	}
	/**
	 * @param totalAmtInfo the totalAmtInfo to set
	 */
	public void setTotalAmtInfo(String totalAmtInfo) {
		this.totalAmtInfo = totalAmtInfo;
	}
	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the postage
	 */
	public BigDecimal getPostage() {
		return postage;
	}
	/**
	 * @param postage the postage to set
	 */
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	/**
	 * @return the cheapAmt
	 */
	public BigDecimal getCheapAmt() {
		return cheapAmt;
	}
	/**
	 * @param cheapAmt the cheapAmt to set
	 */
	public void setCheapAmt(BigDecimal cheapAmt) {
		this.cheapAmt = cheapAmt;
	}
	/**
	 * @return the orderAmt
	 */
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	/**
	 * @param orderAmt the orderAmt to set
	 */
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
}
