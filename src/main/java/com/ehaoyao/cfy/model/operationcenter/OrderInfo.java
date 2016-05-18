package com.ehaoyao.cfy.model.operationcenter;

import java.math.BigDecimal;

public class OrderInfo {
	
	/**------------------------渠道标识定义----------------------------*/
	/**
	 * 天猫处方药
	 */
	public static final String ORDER_ORDER_FLAG_TMCFY = "TMCFY";
	/**
	 * 1号店处方药
	 */
	public static final String ORDER_ORDER_FLAG_yhdcfy = "yhdcfy";
	/**
	 * 平安处方药
	 */
	public static final String ORDER_ORDER_FLAG_PACFY = "PACFY";
	
	
	/**------------------------订单审核状态码----------------------------*/
	/**
	 * 等待客服审核
	 */
	public static final String ORDER_AUDIT_STATUS_WAIT = "WAIT";
	
	/**
	 * 客服审核通过
	 */
	public static final String ORDER_AUDIT_STATUS_PRESUCC = "PRESUCC"; 
	/**
	 * 客服审核驳回
	 */
	public static final String ORDER_AUDIT_STATUS_PRERETURN = "PRERETURN";
	
	/**
	 * 医师审核通过
	 */
	public static final String ORDER_AUDIT_STATUS_SUCC = "SUCC"; 
	/**
	 * 医师审核驳回
	 */
	public static final String ORDER_AUDIT_STATUS_RETURN = "RETURN";
	/**
	 * 作废
	 */
	public static final String ORDER_AUDIT_STATUS_VOID = "VOID";
	/**
	 * 取消审核
	 */
	public static final String ORDER_AUDIT_STATUS_CANCEL = "CANCEL"; 
	
	/**------------------------订单状态码----------------------------*/
	/**
	 * 未处理
	 */
	public static final String ORDER_STATUS_UNTREATED = "UNTREATED";
	/**
	 * 处理完成
	 */
	public static final String ORDER_STATUS_FINISH = "FINISH"; 
	
	/**------------------------付款状态枚举----------------------------*/
	/**
	 * 未付款
	 */
	public static final String ORDER_PAY_STATUS_NOPAY = "NOPAY";
	/**
	 * 已付款
	 */
	public static final String ORDER_PAY_STATUS_PAID = "PAID";
	
	/**------------------------支付方式枚举----------------------------*/
	/**
	 * COD:货到付款  
	 */
	public static final String ORDER_PAY_TYPE_COD = "COD";
	/**
	 * ONLINEPAY:网上支付
	 */
	public static final String ORDER_PAY_TYPE_ONLINEPAY = "ONLINEPAY";
	/**
	 * 账户支付
	 */
	public static final String ORDER_PAY_TYPE_ACCOUNTPAY = "ACCOUNTPAY";
	/**
	 * 邮局汇款
	 */
	public static final String ORDER_PAY_TYPE_POSTREMITTANCE = "POSTREMITTANCE";
	/**
	 * 银行转账
	 */
	public static final String ORDER_PAY_TYPE_BANKTRANSFER = "BANKTRANSFER";
	/**
	 * pos机
	 */
	public static final String ORDER_PAY_TYPE_POSPAY = "POSPAY";
	/**
	 * 万里通
	 */
	public static final String ORDER_PAY_TYPE_WALITO = "WALITO";
	/**
	 * 分期付款
	 */
	public static final String ORDER_PAY_TYPE_INSTALLMENTPAYMENT = "INSTALLMENTPAYMENT";
	/**
	 * 合同账期
	 */
	public static final String ORDER_PAY_TYPE_CONTRACTPERIOD = "CONTRACTPERIOD";
	/**
	 * 货到转账
	 */
	public static final String ORDER_PAY_TYPE_ARRIVALTRANSFER = "ARRIVALTRANSFER";
	/**
	 * 货到付支票
	 */
	public static final String ORDER_PAY_TYPE_ARRIVALPAYCHECK = "ARRIVALPAYCHECK";
	/**
	 * 货到刷支付宝
	 */
	public static final String ORDER_PAY_TYPE_ARRIVALBRUSHALIPAY = "ARRIVALBRUSHALIPAY";
	
	
	
	/**------------------------开票状态枚举----------------------------*/
	/**
	 * 开发票
	 */
	public static final String ORDER_INVOICE_STATUS_YES = "1";
	/**
	 * 不开发票
	 */
	public static final String ORDER_INVOICE_STATUS_NO = "0";

	/**------------------------发票类型枚举----------------------------*/
	/**
	 * 普通发票
	 */
	public static final String ORDER_INVOICE_TYPE_PLAIN = "PLAIN";
	/**
	 * 电子发票
	 */
	public static final String ORDER_INVOICE_TYPE_ELECTRONIC = "ELECTRONIC";
	/**
	 * 增值税发票
	 */
	public static final String ORDER_INVOICE_TYPE_VAT = "VAT";
	
    private Long orderId;

    private String orderNumber;

    private String orderFlag;

    private String userId;

    private String userCode;

    private String nickName;

    private String userName;

    private String prescriptionType;

    private String orderStatus;

    private String auditStatus;

    private String invoiceStatus;

    private String payStatus;

    private String orderType;

    private String payType;

    private String orderTime;

    private String receiver;

    private String patientName;

    private String sex;

    private Integer age;

    private String pregnantFlag;

    private String decoctFlag;

    private String dose;

    private String smallPicLink;

    private String bigPicLink;

    private String paymentTime;

    private String deliveryDate;

    private String hospitalPrescDate;

    private String addressDetail;

    private String mobile;

    private String telephone;

    private String province;

    private String city;

    private String area;

    private String country;

    private BigDecimal price;

    private BigDecimal orderPrice;

    private BigDecimal discountAmount;

    private BigDecimal overReturnFree;

    private BigDecimal expressPrice;

    private BigDecimal platPayPrice;

    private String instructions;

    private String remark;

    private String feeType;

    private String lastTime;

    private String createTime;

    private String shippingType;
    
    private String kfAccount;
    
    /**
     * 商品的总个数
     */
    private Integer productCount;

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

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus == null ? null : invoiceStatus.trim();
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    public String getHospitalPrescDate() {
        return hospitalPrescDate;
    }

    public void setHospitalPrescDate(String hospitalPrescDate) {
        this.hospitalPrescDate = hospitalPrescDate == null ? null : hospitalPrescDate.trim();
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
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
        this.instructions = instructions == null ? null : instructions.trim();
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

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType == null ? null : shippingType.trim();
    }
    
    public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	/**
     * 获取审核状态描述
     * @return
     */
    public String getAuditStatusDesc(){
    	String auditStatusDesc = null;
    	if(auditStatus!=null && auditStatus.length()>0){
    		if(ORDER_AUDIT_STATUS_WAIT.equals(this.auditStatus)){
    			auditStatusDesc = "等待客服审核";
    		}
    		if(ORDER_AUDIT_STATUS_PRESUCC.equals(this.auditStatus)){
    			auditStatusDesc = "客服审核通过";
    		}
    		if(ORDER_AUDIT_STATUS_PRERETURN.equals(this.auditStatus)){
    			auditStatusDesc = "客服审核驳回";
    		}
    		if(ORDER_AUDIT_STATUS_SUCC.equals(this.auditStatus)){
    			auditStatusDesc = "医师审核通过";
    		}
    		if(ORDER_AUDIT_STATUS_RETURN.equals(this.auditStatus)){
    			auditStatusDesc = "医师审核驳回";
    		}
    		if(ORDER_AUDIT_STATUS_VOID.equals(this.auditStatus)){
    			auditStatusDesc = "作废";
    		}
    		if(ORDER_AUDIT_STATUS_CANCEL.equals(this.auditStatus)){
    			auditStatusDesc = "取消";
    		}
    	}
    	return auditStatusDesc;
    }
    
    /**
     * 获取支付方式描述
     * @return
     */
    public static String getPayTypeDesc(String payType){
    	String payTypeDesc = null;
    	if(payType!=null && payType.length()>0){
    		if(ORDER_PAY_TYPE_COD.equals(payType)){
    			payTypeDesc = "货到付款";
    		}
    		if(ORDER_PAY_TYPE_ONLINEPAY.equals(payType)){
    			payTypeDesc = "网上支付";
    		}
    		if(ORDER_PAY_TYPE_ACCOUNTPAY.equals(payType)){
    			payTypeDesc = "账户支付";
    		}
    		if(ORDER_PAY_TYPE_POSTREMITTANCE.equals(payType)){
    			payTypeDesc = "邮局汇款";
    		}
    		if(ORDER_PAY_TYPE_BANKTRANSFER.equals(payType)){
    			payTypeDesc = "银行转账";
    		}
    		if(ORDER_PAY_TYPE_POSPAY.equals(payType)){
    			payTypeDesc = "pos机";
    		}
    		if(ORDER_PAY_TYPE_WALITO.equals(payType)){
    			payTypeDesc = "万里通";
    		}
    		if(ORDER_PAY_TYPE_INSTALLMENTPAYMENT.equals(payType)){
    			payTypeDesc = "分期付款";
    		}
    		if(ORDER_PAY_TYPE_CONTRACTPERIOD.equals(payType)){
    			payTypeDesc = "合同账期";
    		}
    		if(ORDER_PAY_TYPE_ARRIVALTRANSFER.equals(payType)){
    			payTypeDesc = "货到转账";
    		}
    		if(ORDER_PAY_TYPE_ARRIVALPAYCHECK.equals(payType)){
    			payTypeDesc = "货到付支票";
    		}
    		if(ORDER_PAY_TYPE_ARRIVALBRUSHALIPAY.equals(payType)){
    			payTypeDesc = "货到刷支付宝";
    		}
    	}
    	return payTypeDesc;
    }
    
    
    /**
     * 获取发票状态描述
     * @return
     */
    public String getInvoiceStatusDesc(){
    	String invoiceStatusDesc = null;
    	if(this.invoiceStatus!=null && this.invoiceStatus.length()>0){
    		if(ORDER_INVOICE_STATUS_YES.equals(this.invoiceStatus)){
    			invoiceStatusDesc = "开票";
    		}
    		if(ORDER_INVOICE_STATUS_NO.equals(this.invoiceStatus)){
    			invoiceStatusDesc = "不开票";
    		}
    	}
    	return invoiceStatusDesc;
    }
}