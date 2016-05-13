package com.ehaoyao.opertioncenter.returnGoods.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退货订单明细数据
 * @author zhang
 *
 */
@Entity
@Table(name = "return_goods_detail")
public class GoodsDetailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	/**
	 * 订单号
	 */
	@Column(name = "order_number")
	private String orderNumber;
	/**
	 * 商品编码
	 */
	@Column(name = "product_id")
	private String productId;
	/**
	 * 商品名称
	 */
	@Column(name = "product_name")
	private String productName;
	/**
	 * 数量
	 */
	@Column(name = "count")
	private String count;
	/**
	 * 单价
	 */
	@Column(name = "price")
	private String price;
	/**
	 * 金额
	 */
	@Column(name = "total_price")
	private String totalPrice;
	/**
	 * 订单类型
	 */
	@Column(name = "orderFlag")
	private String orderFlag;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the totalPrice
	 */
	public String getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the orderFlag
	 */
	public String getOrderFlag() {
		return orderFlag;
	}
	/**
	 * @param orderFlag the orderFlag to set
	 */
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
}
