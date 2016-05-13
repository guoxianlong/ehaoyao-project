package com.ehaoyao.opertioncenter.payOrder.vo;

import java.math.BigDecimal;

public class OrderShowInfo {
    
	    /*********************************************订单表 **************************************************/
	    
	    
	    /**
		 * 订单表主键
		 */
	    private Long orderId;

	    /**
	     * 订单编号
	     */
	    private String infoOrderNumber;
	    /**
	     * 订单号
	     */
	    private String orderNumber;
	    /**
	     * 渠道标识
	     */
	    private String orderFlag;

	    /**
	     * 订单标识(渠道标识) 天猫:TMCFY  ...
	     */
	    private String infoOrderFlag;

	    /**
	     * 下单用户id
	     */
	    private String userId;

	    /**
	     * 下单用户编码
	     */
	    private String userCode;

	    /**
	     * 下单用户名
	     */
	    private String nickName;

	    /**
	     * 下单用户姓名
	     */
	    private String userName;

	    /**
	     * 处方类型   0:不是  1:是
	     */
	    private String prescriptionType;

	    /**
	     * 订单状态   UNTREATED:未处理  FINISH:已完成(目前用于中药馆煎药中心人员,订单是否已处理 )
	     */
	    private String orderStatus;
	    
	    /**
	     * 是否进erp
	     */
	    private String toErp;
	    
	    /**
	     * 进入erp的时间
	     */
	    private String toErpTime;
	    
	    /**
	     * 客服编号
	     */
	    private String kfAccount;
	    

	    /**
	     * 付款状态  NOPAY:未付款   PAID:已付款   RETURN:已退款
	     */
	    private String payStatus;

	    /**
	     * 下单类型  CUSORDER:客户下单(客户自选药品)   REPORDER:代下单(客户上传处方单图片)
	     */
	    private String orderType;

	    /**
	     * 支付类型 COD:货到付款  ONLINEPAY:网上支付...
	     */
	    private String payType;

	    /**
	     * 订单时间
	     */
	    private String orderTime;
	   

	    /**
	     * 收货人
	     */
	    private String receiver;


	    /**
	     * 患者姓名
	     */
	    private String patientName;

	    /**
	     * 性别 男：MALE  女：FAMALE
	     */
	    private String sex;

	    /**
	     * 年龄
	     */
	    private Integer age;

	    /**
	     * 是否孕妇  0: 不是  1:是
	     */
	    private String pregnantFlag;

	    /**
	     * 是否代煎  0:否 1:是
	     */
	    private String decoctFlag;

	    /**
	     * 剂量
	     */
	    private String dose;

	    /**
	     * 小图片链接
	     */
	    private String smallPicLink;

	    /**
	     * 大图链接
	     */
	    private String bigPicLink;

	    /**
	     * 付款时间
	     */
	    private String paymentTime;

	    /**
	     * 送货日期
	     */
	    private String deliveryDate;
		
		/**
	     * 医院处方日期
	     */
		private String hospitalPrescDate;

	    /**
	     * 详细地址
	     */
	    private String addressDetail;

	    /**
	     * 手机号码
	     */
	    private String mobile;

	    /**
	     * 联系电话
	     */
	    private String telephone;

	    /**
	     * 省份
	     */
	    private String province;

	    /**
	     * 城市
	     */
	    private String city;
	    
	    /**
	     * 区域
	     */
	    private String area;

	    /**
	     * 国家
	     */
	    private String country;
		
		/**
	     * 实际支付金额（商品总价格+邮费-订单优惠金额）
	     */
	    private BigDecimal price;

	    /**
	     * 订单总金额（商品总价格+邮费）
	     */
	    private BigDecimal orderPrice;

	    /**
	     * 优惠金额
	     */
	    private BigDecimal discountAmount;
		
		/**
	     * 满反满送
	     */
		private BigDecimal overReturnFree;

		/**
	     * 快递费用
	     */
	    private BigDecimal expressPrice;

		/**
	     * 平台支付金额
	     */
	    private BigDecimal platPayPrice;

	    /**
	     * 使用说明（用药说明）
	     */
	    private String instructions;

	    /**
	     * 备注
	     */
	    private String remark;

	    /**
	     * 币种  RMB人民币、USD美元、EUR欧元、HKD港币、GBP英镑、JPY日元、KRW韩元、CAD加元、AUD澳元、CHF瑞郎、SGD新加坡元、MYR马来西亚币、IDR印尼、NZD新西兰、VND越南、THB泰铢、PHP菲律宾
	     */
	    private String feeType;


	    /**
	     * 最后操作时间 
	     */
	    private String lastTime;

	    /**
	     * 创建时间
	     */
	    private String infoCreateTime;
	    
    
		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public String getInfoOrderNumber() {
			return infoOrderNumber;
		}

		public void setInfoOrderNumber(String infoOrderNumber) {
			this.infoOrderNumber = infoOrderNumber;
		}

		public String getInfoOrderFlag() {
			return infoOrderFlag;
		}

		public void setInfoOrderFlag(String infoOrderFlag) {
			this.infoOrderFlag = infoOrderFlag;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPrescriptionType() {
			return prescriptionType;
		}

		public void setPrescriptionType(String prescriptionType) {
			this.prescriptionType = prescriptionType;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
	
		public String getToErp() {
			return toErp;
		}

		public void setToErp(String toErp) {
			this.toErp = toErp;
		}

		public String getToErpTime() {
			return toErpTime;
		}

		public void setToErpTime(String toErpTime) {
			this.toErpTime = toErpTime;
		}

		public String getPayStatus() {
			return payStatus;
		}

		public void setPayStatus(String payStatus) {
			this.payStatus = payStatus;
		}

		public String getOrderType() {
			return orderType;
		}

		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getOrderTime() {
			return orderTime;
		}

		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}

		public String getReceiver() {
			return receiver;
		}

		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}

		public String getPatientName() {
			return patientName;
		}

		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getPregnantFlag() {
			return pregnantFlag;
		}

		public void setPregnantFlag(String pregnantFlag) {
			this.pregnantFlag = pregnantFlag;
		}

		public String getDecoctFlag() {
			return decoctFlag;
		}

		public void setDecoctFlag(String decoctFlag) {
			this.decoctFlag = decoctFlag;
		}

		public String getDose() {
			return dose;
		}

		public void setDose(String dose) {
			this.dose = dose;
		}

		public String getSmallPicLink() {
			return smallPicLink;
		}

		public void setSmallPicLink(String smallPicLink) {
			this.smallPicLink = smallPicLink;
		}

		public String getBigPicLink() {
			return bigPicLink;
		}

		public void setBigPicLink(String bigPicLink) {
			this.bigPicLink = bigPicLink;
		}

		public String getPaymentTime() {
			return paymentTime;
		}

		public void setPaymentTime(String paymentTime) {
			this.paymentTime = paymentTime;
		}

		public String getDeliveryDate() {
			return deliveryDate;
		}

		public void setDeliveryDate(String deliveryDate) {
			this.deliveryDate = deliveryDate;
		}

		public String getHospitalPrescDate() {
			return hospitalPrescDate;
		}

		public void setHospitalPrescDate(String hospitalPrescDate) {
			this.hospitalPrescDate = hospitalPrescDate;
		}

		public String getAddressDetail() {
			return addressDetail;
		}

		public void setAddressDetail(String addressDetail) {
			this.addressDetail = addressDetail;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}
		

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getOrderPrice() {
			return orderPrice;
		}

		public void setOrderPrice(BigDecimal orderPrice) {
			this.orderPrice = orderPrice;
		}

		public BigDecimal getDiscountAmount() {
			return discountAmount;
		}

		public void setDiscountAmount(BigDecimal discountAmount) {
			this.discountAmount = discountAmount;
		}

		public BigDecimal getOverReturnFree() {
			return overReturnFree;
		}

		public void setOverReturnFree(BigDecimal overReturnFree) {
			this.overReturnFree = overReturnFree;
		}

		public BigDecimal getExpressPrice() {
			return expressPrice;
		}

		public void setExpressPrice(BigDecimal expressPrice) {
			this.expressPrice = expressPrice;
		}

		public BigDecimal getPlatPayPrice() {
			return platPayPrice;
		}

		public void setPlatPayPrice(BigDecimal platPayPrice) {
			this.platPayPrice = platPayPrice;
		}

		public String getInstructions() {
			return instructions;
		}

		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getFeeType() {
			return feeType;
		}

		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}

		public String getLastTime() {
			return lastTime;
		}

		public void setLastTime(String lastTime) {
			this.lastTime = lastTime;
		}

		public String getInfoCreateTime() {
			return infoCreateTime;
		}

		public void setInfoCreateTime(String infoCreateTime) {
			this.infoCreateTime = infoCreateTime;
		}

		public String getOrderNumber() {
			return orderNumber;
		}

		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}

		public String getOrderFlag() {
			return orderFlag;
		}

		public void setOrderFlag(String orderFlag) {
			this.orderFlag = orderFlag;
		}

		public String getKfAccount() {
			return kfAccount;
		}

		public void setKfAccount(String kfAccount) {
			this.kfAccount = kfAccount;
		}

	    

}
