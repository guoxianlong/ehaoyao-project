package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Title: Order.java
 * 
 * Description: 购买记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月29日 下午2:55:38
 */
public class Order implements Serializable{
	
	private static final long serialVersionUID = 6551109406900470400L;
	
	/** 订单编号 */
	private String orderNum;
	/**	订单日期 */
	private String orderTime;
	/**	发货仓库 */
	private String goodsArea;
	/**	商品编码 */
	private String goodsNum;
	/** 企业内码 */
	private String goodsSpid;
	/**	商品名称 */
	private String goodsName;
	/**	规格 */
	private String formart;
	/**	单位 */
	private String unit;
	/**	数量 */
	private int count;
	/**	单价 */
	private BigDecimal price;
	/**	金额 */
	private BigDecimal totalPrice;
	/**	赠品 */
	private String ifzp;
	/**	渠道类别 */
	private String chanel;
	/**	三方商家 */
	private String otherSeller;
	
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getGoodsArea() {
		return goodsArea;
	}
	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getGoodsSpid() {
		return goodsSpid;
	}
	public void setGoodsSpid(String goodsSpid) {
		this.goodsSpid = goodsSpid;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getFormart() {
		return formart;
	}
	public void setFormart(String formart) {
		this.formart = formart;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getIfzp() {
		return ifzp;
	}
	public void setIfzp(String ifzp) {
		this.ifzp = ifzp;
	}
	public String getChanel() {
		return chanel;
	}
	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	public String getOtherSeller() {
		return otherSeller;
	}
	public void setOtherSeller(String otherSeller) {
		this.otherSeller = otherSeller;
	}
	
	
	
}
