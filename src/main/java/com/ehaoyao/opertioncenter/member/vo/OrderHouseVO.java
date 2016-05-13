package com.ehaoyao.opertioncenter.member.vo;

import java.io.Serializable;
/**
 * @author Administrator
 * 订单信息
 */
public class OrderHouseVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private int id;
	/**
	 * 原ID
	 */
	private int beforeId;
	/**
	 * 源数据库标识
	 */
	private int DBtype;
	
	/**
	 * 更新时间
	 */
	private String updateDate;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 是否删除
	 */
	private int ifDel;
	
	/**
	 * 日期
	 */
	private String date;
	
	/**
	 * 订单标识
	 */
	private String orderFlag;
	
	/**
	 * 订单号
	 */
	private String orderNumber;
	
	/**
	 * 商品编号
	 */
	private String spbh;
	/**
	 * 商品ID
	 */
	private String spid;
	/**
	 * 商品名称
	 */
	private String goodName;
	/**
	 * 商品数量
	 */
	private int goodCount;
	/**
	 * 商品单价
	 */
	private String goodPrice;
	/**
	 * 
	 */
	private String sales;
	/**
	 * 
	 */
	private String cannalSales;
	/**
	 * 
	 */
	private String personSales;
	/**
	 * 
	 */
	private String discountAmount;
	/**
	 * 
	 */
	private String expressSales;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String country;
	/**
	 * 一级分类
	 */
	private String oneLevel;
	/**
	 * 二级分类
	 */
	private String twoLevel;
	/**
	 * 三级分类
	 */
	private String threeLevel;
	/**
	 * 开始时间
	 */
	private String startDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 专属客服
	 */
	private String userName;
	/**
	 * 在member表中是否已经存在(是否新会员)
	 */
	private String isExist;
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
	 * @return the dBtype
	 */
	public int getDBtype() {
		return DBtype;
	}
	/**
	 * @param dBtype the dBtype to set
	 */
	public void setDBtype(int dBtype) {
		DBtype = dBtype;
	}
	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the ifDel
	 */
	public int getIfDel() {
		return ifDel;
	}
	/**
	 * @param ifDel the ifDel to set
	 */
	public void setIfDel(int ifDel) {
		this.ifDel = ifDel;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
	 * @return the spbh
	 */
	public String getSpbh() {
		return spbh;
	}
	/**
	 * @param spbh the spbh to set
	 */
	public void setSpbh(String spbh) {
		this.spbh = spbh;
	}
	/**
	 * @return the spid
	 */
	public String getSpid() {
		return spid;
	}
	/**
	 * @param spid the spid to set
	 */
	public void setSpid(String spid) {
		this.spid = spid;
	}
	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}
	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	/**
	 * @return the goodCount
	 */
	public int getGoodCount() {
		return goodCount;
	}
	/**
	 * @param goodCount the goodCount to set
	 */
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	
	/**
	 * @return the beforeId
	 */
	public int getBeforeId() {
		return beforeId;
	}
	/**
	 * @param beforeId the beforeId to set
	 */
	public void setBeforeId(int beforeId) {
		this.beforeId = beforeId;
	}
	/**
	 * @return the goodPrice
	 */
	public String getGoodPrice() {
		return goodPrice;
	}
	/**
	 * @param goodPrice the goodPrice to set
	 */
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	/**
	 * @return the sales
	 */
	public String getSales() {
		return sales;
	}
	/**
	 * @param sales the sales to set
	 */
	public void setSales(String sales) {
		this.sales = sales;
	}
	/**
	 * @return the cannalSales
	 */
	public String getCannalSales() {
		return cannalSales;
	}
	/**
	 * @param cannalSales the cannalSales to set
	 */
	public void setCannalSales(String cannalSales) {
		this.cannalSales = cannalSales;
	}
	/**
	 * @return the personSales
	 */
	public String getPersonSales() {
		return personSales;
	}
	/**
	 * @param personSales the personSales to set
	 */
	public void setPersonSales(String personSales) {
		this.personSales = personSales;
	}
	/**
	 * @return the discountAmount
	 */
	public String getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 * @return the expressSales
	 */
	public String getExpressSales() {
		return expressSales;
	}
	/**
	 * @param expressSales the expressSales to set
	 */
	public void setExpressSales(String expressSales) {
		this.expressSales = expressSales;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the oneLevel
	 */
	public String getOneLevel() {
		return oneLevel;
	}
	/**
	 * @param oneLevel the oneLevel to set
	 */
	public void setOneLevel(String oneLevel) {
		this.oneLevel = oneLevel;
	}
	/**
	 * @return the twoLevel
	 */
	public String getTwoLevel() {
		return twoLevel;
	}
	/**
	 * @param twoLevel the twoLevel to set
	 */
	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}
	/**
	 * @return the threeLevel
	 */
	public String getThreeLevel() {
		return threeLevel;
	}
	/**
	 * @param threeLevel the threeLevel to set
	 */
	public void setThreeLevel(String threeLevel) {
		this.threeLevel = threeLevel;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the isExist
	 */
	public String getIsExist() {
		return isExist;
	}
	/**
	 * @param isExist the isExist to set
	 */
	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
}