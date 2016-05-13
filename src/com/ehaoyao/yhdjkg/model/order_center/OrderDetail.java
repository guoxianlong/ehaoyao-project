package com.ehaoyao.yhdjkg.model.order_center;

/**
 * @author sjfeng
 *
 */
public class OrderDetail {
	private Long id=0L;
	private String orderNumber="";
	private String merchantId="";
	private Double totalPrice=0.0;
	private Double count=0.0;
	private String productId="";
	private String unit="";
	private String productName="";
	private Double price=0.0;
	private String orderFlag="";

	private String djbh_id="";// ERP一个自增字段
	private String eflag="";// ERP一个字段
	private String spId="";

	private Double disTotalPrice=0.0;// 计算优惠价格之后的总价格

	private Double discountAmount;//优惠金额
	
	
	
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDisTotalPrice() {
		return disTotalPrice;
	}

	public void setDisTotalPrice(Double disTotalPrice) {
		this.disTotalPrice = disTotalPrice;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getEflag() {
		return eflag;
	}

	public void setEflag(String eflag) {
		this.eflag = eflag;
	}

	public String getDjbh_id() {
		return djbh_id;
	}

	public void setDjbh_id(String djbhId) {
		djbh_id = djbhId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

}