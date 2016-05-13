package com.ehaoyao.opertioncenter.ordercenter.orderModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Title: OrderDetail.java
 * 
 * Description: 订单中心订单详情
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月23日 下午2:15:51
 */
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable{
	private static final long serialVersionUID = -6889337359508624804L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition="bigint", length=20, nullable = false)
	private Long id;
	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "merchant_id")
	private String merchantId;
	@Column(name = "total_price")
	private Double totalPrice;
	@Column(name = "count")
	private Double count;
	@Column(name = "product_id")
	private String productId;
	@Column(name = "unit")
	private String unit;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "price")
	private Double price;
	@Column(name = "order_flag")
	private String orderFlag;
	@Column(name = "products_promo_id")
	private String productsPromoId;
	@Column(name = "discount_amount")
	private String discountAmount;
	@Column(name = "djbh_id")
	private String djbhId;
	@Column(name = "eflag")
	private String eflag;
	@Column(name = "spid")
	private String spid;
	
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
	public String getProductsPromoId() {
		return productsPromoId;
	}
	public void setProductsPromoId(String productsPromoId) {
		this.productsPromoId = productsPromoId;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getDjbhId() {
		return djbhId;
	}
	public void setDjbhId(String djbhId) {
		this.djbhId = djbhId;
	}
	public String getEflag() {
		return eflag;
	}
	public void setEflag(String eflag) {
		this.eflag = eflag;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}

}
