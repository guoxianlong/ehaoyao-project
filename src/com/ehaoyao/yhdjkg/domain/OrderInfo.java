package com.ehaoyao.yhdjkg.domain;

import java.math.BigDecimal;

/**
 * 订单信息表
 * @author Administrator
 *
 */
public class OrderInfo {
	
	/**
	 * 订单表主键
	 */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单标识(渠道标识) 天猫:TM 京东:JD 掌上糖医:ZSTY  ...
     */
    private String orderFlag;

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
     * 审核状态  WAIT:待审核  SUCC:已审核  RETURN:驳回 VOID:作废  CANCEL:取消
     */
    private String auditStatus;

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
     * 实际支付金额
     */
    private BigDecimal price;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 审核人
     */
    private String auditUser;

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
     * 审核时间
     */
    private String auditTime;

    /**
     * 送货日期
     */
    private String deliveryDate;

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
     * 国家
     */
    private String country;

    /**
     * 订单总金额（商品总价格+邮费）
     */
    private BigDecimal orderPrice;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 使用说明（用药说明）
     */
    private String instructions;

    /**
     * 审核说明
     */
    private String auditDescription;

    /**
     * 备注
     */
    private String remark;

    /**
     * 币种  RMB人民币、USD美元、EUR欧元、HKD港币、GBP英镑、JPY日元、KRW韩元、CAD加元、AUD澳元、CHF瑞郎、SGD新加坡元、MYR马来西亚币、IDR印尼、NZD新西兰、VND越南、THB泰铢、PHP菲律宾
     */
    private String feeType;

    /**
     * 客服工号
     */
    private String kfAccount;

    /**
     * 最后操作时间 
     */
    private String lastTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 快递金额
     */
    private Double expressPrice;
    /**
     * 满反满送金额
     */
    private Double overReturnFree;
    
    /**
     * 平台支付金额
     */
    private BigDecimal platPayPrice;
    /**
     * 支付类型： 货到付款  网上支付...
     */
    private String payTypeName;
    
    /**
     * 商品的总个数
     */
    private Integer productCount;
    
    
    public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public BigDecimal getPlatPayPrice() {
		return platPayPrice;
	}

	public void setPlatPayPrice(BigDecimal platPayPrice) {
		this.platPayPrice = platPayPrice;
	}

	public Double getOverReturnFree() {
		return overReturnFree;
	}

	public void setOverReturnFree(Double overReturnFree) {
		this.overReturnFree = overReturnFree;
	}

	public Double getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType == null ? null : prescriptionType.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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
        this.pregnantFlag = pregnantFlag == null ? null : pregnantFlag.trim();
    }

    public String getDecoctFlag() {
        return decoctFlag;
    }

    public void setDecoctFlag(String decoctFlag) {
        this.decoctFlag = decoctFlag == null ? null : decoctFlag.trim();
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose == null ? null : dose.trim();
    }

    public String getSmallPicLink() {
        return smallPicLink;
    }

    public void setSmallPicLink(String smallPicLink) {
        this.smallPicLink = smallPicLink == null ? null : smallPicLink.trim();
    }

    public String getBigPicLink() {
        return bigPicLink;
    }

    public void setBigPicLink(String bigPicLink) {
        this.bigPicLink = bigPicLink == null ? null : bigPicLink.trim();
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime == null ? null : paymentTime.trim();
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime == null ? null : auditTime.trim();
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions == null ? null : instructions.trim();
    }

    public String getAuditDescription() {
        return auditDescription;
    }

    public void setAuditDescription(String auditDescription) {
        this.auditDescription = auditDescription == null ? null : auditDescription.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount == null ? null : kfAccount.trim();
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
   
}