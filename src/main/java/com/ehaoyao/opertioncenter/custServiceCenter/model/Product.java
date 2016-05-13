package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author Administrator
 * 主推商品
 */
@Entity
@Table(name = "product")
public class Product implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;
	/**
	 * 副本ID
	 */
	@Id
	@Column(name = "second_id",nullable = false)
	private String secondID;
	/**
	 * 副本名称
	 */
	@Column(name = "second_name")
	private String secondName;
	
	/**
	 * 产品ID
	 */
	@Column(name = "main_product_id")
	private String mainProductId;
	
	/**
	 * 主商品编码
	 */
	@Column(name = "product_code")
	private String productCode;
	/**
	 * 主商品名称
	 */
	@Column(name = "product_name")
	private String productName;
	/**
	 * 主商品规格
	 */
	@Column(name = "specifications")
	private String specifications;
	/**
	 * 市场价
	 */
	@Column(name = "market_price")
	private String marketPrice;
	/**
	 * 售价
	 */
	@Column(name = "price")
	private String price;
	/**
	 * 前端类型
	 */
	@Column(name = "front_type")
	private String frontType;
	/**
	 * 处方类型
	 * 1:非药品、2:非处方药、3:单轨处方药、4:双轨处方药
	 */
	@Column(name = "is_cf")
	private String isCf;
	/**
	 * 创建者
	 */
	@Column(name = "create_user")
	private String createUser;
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private String createTime;
	/**
	 * 更新者
	 */
	@Column(name = "update_user")
	private String updateUser;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private String updateTime;
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
	public String getMainProductId() {
		return mainProductId;
	}
	public void setMainProductId(String mainProductId) {
		this.mainProductId = mainProductId;
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
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
