package com.ehaoyao.opertioncenter.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Administrator
 * 任务信息
 */
@Entity
@Table(name = "fc_data_orderhouse_detail")
public class OrderHouse implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@Column(name = "id",columnDefinition="int",length=11,nullable = false)
	private int id;
	/**
	 * 原ID
	 */
	@Column(name = "before_id",columnDefinition="int",length=11)
	private int beforeId;
	/**
	 * 源数据库标识
	 */
	@Column(name = "DBtype",columnDefinition="int",length=2,nullable = false)
	private int DBtype;
	
	/**
	 * 更新时间
	 */
	@Column(name = "updateDate",columnDefinition="date",nullable = false)
	private String updateDate;
	/**
	 * 创建时间
	 */
	@Column(name = "createDate",columnDefinition="date",nullable = false)
	private String createDate;
	/**
	 * 是否删除
	 */
	@Column(name = "ifDel",columnDefinition="int",length=1)
	private int ifDel;
	
	/**
	 * 日期
	 */
	@Column(name = "date",columnDefinition="char",length=10)
	private String date;
	
	/**
	 * 订单标识
	 */
	@Column(name = "order_flag",length=20)
	private String orderFlag;
	
	/**
	 * 订单号
	 */
	@Column(name = "order_number",length=50)
	private String orderNumber;
	
	/**
	 * 商品编号
	 */
	@Column(name = "spbh",length=50)
	private String spbh;
	/**
	 * 商品ID
	 */
	@Column(name = "spid",length=50)
	private String spid;
	/**
	 * 商品名称
	 */
	@Column(name = "good_name",length=200)
	private String goodName;
	/**
	 * 商品数量
	 */
	@Column(name = "good_count",columnDefinition="int",length=11)
	private int goodCount;
	/**
	 * 商品单价
	 */
	@Column(name = "good_price",columnDefinition="decimal")
	private String goodPrice;
	/**
	 * 
	 */
	@Column(name = "sales",columnDefinition="decimal")
	private String sales;
	/**
	 * 
	 */
	@Column(name = "cannal_sales",columnDefinition="decimal")
	private String cannalSales;
	/**
	 * 
	 */
	@Column(name = "person_sales",columnDefinition="decimal")
	private String personSales;
	/**
	 * 
	 */
	@Column(name = "discount_amount",columnDefinition="decimal")
	private String discountAmount;
	/**
	 * 
	 */
	@Column(name = "express_sales",columnDefinition="decimal")
	private String expressSales;
	/**
	 * 电话
	 */
	@Column(name = "tel",length=18)
	private String tel;
	/**
	 * 姓名
	 */
	@Column(name = "name",length=30)
	private String name;
	/**
	 * 省
	 */
	@Column(name = "province",length=50)
	private String province;
	/**
	 * 市
	 */
	@Column(name = "city",length=50)
	private String city;
	/**
	 * 区
	 */
	@Column(name = "country",length=50)
	private String country;
	/**
	 * 一级分类
	 */
	@Column(name = "one_level",length=50)
	private String oneLevel;
	/**
	 * 二级分类
	 */
	@Column(name = "two_level",length=50)
	private String twoLevel;
	/**
	 * 三级分类
	 */
	@Column(name = "three_level",length=50)
	private String threeLevel;
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
}
