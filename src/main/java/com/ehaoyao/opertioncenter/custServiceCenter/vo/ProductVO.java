package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;
/**
 * 
 * @author xcl
 * 电销商品
 */
public class ProductVO implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态
	 * 0:电销，1:主推
	 */
	private String status;
	/**
	 * 副本ID
	 */
	private String secondID;
	/**
	 * 副本名称
	 */
	private String secondName;
	
	/**
	 * 主商品编码
	 */
	private String productCode;
	/**
	 * 主商品名称
	 */
	private String productName;
	/**
	 * 规格
	 */
	private String specifications;
	/**
	 * 市场价
	 */
	private String marketPrice;
	/**
	 * 售价
	 */
	private String price;
	/**
	 * 前端类型
	 */
	private String frontType;
	/**
	 * 处方类型
	 */
	private String isCf;
	
	/**
	 * 主推标志
	 */
	private String goodsSta;
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getGoodsSta() {
		return goodsSta;
	}
	public void setGoodsSta(String goodsSta) {
		this.goodsSta = goodsSta;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the secondID
	 */
	public String getSecondID() {
		return secondID;
	}
	/**
	 * @param secondID the secondID to set
	 */
	public void setSecondID(String secondID) {
		this.secondID = secondID;
	}
	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return secondName;
	}
	/**
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	/**
	 * @return the frontType
	 */
	public String getFrontType() {
		return frontType;
	}
	/**
	 * @param frontType the frontType to set
	 */
	public void setFrontType(String frontType) {
		this.frontType = frontType;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	 * @return the specifications
	 */
	public String getSpecifications() {
		return specifications;
	}
	/**
	 * @param specifications the specifications to set
	 */
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	/**
	 * @return the marketPrice
	 */
	public String getMarketPrice() {
		return marketPrice;
	}
	/**
	 * @param marketPrice the marketPrice to set
	 */
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
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
	 * @return the isCf
	 */
	public String getIsCf() {
		return isCf;
	}
	/**
	 * @param isCf the isCf to set
	 */
	public void setIsCf(String isCf) {
		this.isCf = isCf;
	}
	
}
