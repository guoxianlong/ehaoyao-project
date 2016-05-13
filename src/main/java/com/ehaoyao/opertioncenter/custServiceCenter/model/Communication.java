package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Title: Communication.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月25日 下午2:08:50
 */
@Entity
@Table(name = "communication")
public class Communication implements Serializable {

	/** 描述 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint", length = 20, nullable = false)
	private Long id;

	/**
	 * 客户电话
	 */
	@Column(name = "tel")
	private String tel;

	/**
	 * 客户姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 沟通类型一级分类 去电弹屏受理结果 1:订购，2：考虑，3：反感 ，4：关机，5：空号，6：无应答
	 */
	@Column(name = "accept_result")
	private String acceptResult;

	/**
	 * 沟通类型二级分类
	 */
	@Column(name = "second_type")
	private String secondType;
	
	/**
	 * 沟通类型三级分类
	 */
	@Column(name = "third_type")
	private String thirdType;
	
	/**
	 * 品类类别
	 */
	@Column(name = "pro_category")
	private String proCategory;
	
	/**
	 * 科组类别
	 */
	@Column(name = "dep_category")
	private String depCategory;
	
	/**
	 * 病种类别
	 */
	@Column(name = "disease_category")
	private String diseaseCategory;
	
	/**
	 * 备注
	 */
	@Column(name = "remark", length = 500)
	private String remark;

	/**
	 * 创建者 ，受理人
	 */
	@Column(name = "create_user")
	private String createUser;

	/**
	 * 创建时间 ，受理时间
	 */
	@Column(name = "create_time")
	private String createTime;

	/**
	 * 客户编号
	 */
	@Column(name = "cust_no")
	private String custNo;

	/**
	 * 客户来源
	 */
	@Column(name = "cust_source")
	private String custSource;
	
	/**
	 * 是否新用户
	 */
	@Column(name = "is_new_user")
	private String isNewUser;

	/**
	 * 是否订购
	 */
	@Column(name = "is_order")
	private String isOrder;

	// 订单信息
	/**
	 * 是否成单
	 */
	@Column(name = "is_place_order")
	private String isPlaceOrder;

	/**
	 * 下单用户电话
	 */
	@Column(name = "order_tel")
	private String orderTel;

	/**
	 * 订单号
	 */
	@Column(name = "order_number")
	private String orderNumber;

	/**
	 * 订单来源
	 */
	@Column(name = "order_source")
	private String orderSource;
	
	/**
	 * 订单来源标志
	 */
	@Column(name = "order_source_flag")
	private String orderSourceFlag;

	/**
	 * 成单日期
	 */
	@Column(name = "place_order_date")
	private String placeOrderDate;

	/**
	 * 订单数量
	 */
	@Column(name = "order_quantity", columnDefinition = "int")
	private Integer orderQuantity;

	/**
	 * 订单金额
	 */
	@Column(name = "order_total_price")
	private String orderTotalPrice;

	/**
	 * 订单套餐ID
	 */
	@Column(name = "order_meal_ids")
	private String orderMealIds;
	/**
	 * 订单商品名称
	 */
	@Column(name = "product_name")
	private String productName;

	/**
	 * 订单商品编码
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 本次沟通时间
	 */
	@Column(name = "consult_date")
	private String consultDate;

	/**
	 * 本次沟通客服工号
	 */
	@Column(name = "cust_serv_code")
	private String custServCode;
	
	/**
	 * 本次沟通客服姓名
	 */
	@Column(name = "cust_serv_name")
	private String custServName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAcceptResult() {
		return acceptResult;
	}

	public void setAcceptResult(String acceptResult) {
		this.acceptResult = acceptResult;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getProCategory() {
		return proCategory;
	}

	public void setProCategory(String proCategory) {
		this.proCategory = proCategory;
	}

	public String getDepCategory() {
		return depCategory;
	}

	public void setDepCategory(String depCategory) {
		this.depCategory = depCategory;
	}

	public String getDiseaseCategory() {
		return diseaseCategory;
	}

	public void setDiseaseCategory(String diseaseCategory) {
		this.diseaseCategory = diseaseCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustSource() {
		return custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getIsPlaceOrder() {
		return isPlaceOrder;
	}

	public void setIsPlaceOrder(String isPlaceOrder) {
		this.isPlaceOrder = isPlaceOrder;
	}

	public String getOrderTel() {
		return orderTel;
	}

	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderSourceFlag() {
		return orderSourceFlag;
	}

	public void setOrderSourceFlag(String orderSourceFlag) {
		this.orderSourceFlag = orderSourceFlag;
	}

	public String getPlaceOrderDate() {
		return placeOrderDate;
	}

	public void setPlaceOrderDate(String placeOrderDate) {
		this.placeOrderDate = placeOrderDate;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getOrderMealIds() {
		return orderMealIds;
	}

	public void setOrderMealIds(String orderMealIds) {
		this.orderMealIds = orderMealIds;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}

	public String getCustServCode() {
		return custServCode;
	}

	public void setCustServCode(String custServCode) {
		this.custServCode = custServCode;
	}

	public String getCustServName() {
		return custServName;
	}

	public void setCustServName(String custServName) {
		this.custServName = custServName;
	}

}
