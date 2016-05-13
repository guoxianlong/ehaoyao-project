package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;

public class BuyRecord implements Serializable {
	private static final long serialVersionUID = 6202355187434291406L;
	
	private String orderTime;//订单日期
	private String orderNumber;//订单编号
	private String stock;//发货仓库 
	private String productNo;//商品编码 	
	private String productName;//商品名称 	
	private String specific;//规格
	private String uint;//单位
	private String count;//数量
	private String unitPrice;//单价 
	private String totalPrice;//金额 	
	private String gift;//赠品 	
	private String channel;//渠道类别
	private String thirdMerchant;//三方商家
	
	
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecific() {
		return specific;
	}
	public void setSpecific(String specific) {
		this.specific = specific;
	}
	public String getUint() {
		return uint;
	}
	public void setUint(String uint) {
		this.uint = uint;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getThirdMerchant() {
		return thirdMerchant;
	}
	public void setThirdMerchant(String thirdMerchant) {
		this.thirdMerchant = thirdMerchant;
	}
	
}
