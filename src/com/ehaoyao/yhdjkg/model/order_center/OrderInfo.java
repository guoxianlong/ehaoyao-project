package com.ehaoyao.yhdjkg.model.order_center;

/**
 * @author sjfeng
 * 订单信息表
 *
 */
public class OrderInfo {
	
	private Long id=0L;
	private String originalId="";//原始ID,来源数据库主键
	private String orderNumber="";//订单编号,来源订单编号
	private String startTime="";//订单开始时间
	private String expireTime="";//订单结束时间
	private String remark="";//备注
	private String payType="";//支付类型 货到付款.网上支付...
	private Double price=0.0;//实际支付金额
	private String receiver="";//收货人
	private String addressDetail="";//详细地址
	private String mobile="";// 手机号码
	private String telephone="";//联系电话
	private String deliveryDate="";//送货日期
	private String province="";//省份
	private String city="";//城市
	private String country="";//国家
	private Double orderPrice=0.0;//订单总金额（商品总价格+邮费）
	private Double discountAmount=0.0;//优惠金额
	private Double expressPrice=0.0;//快递费用
	private String orderFlag="";//订单标识 微信:wx 当当:dd
	private String overReturnFree="";//满反满送
	
	private String nickName="";//昵称(仅微信使用)
	private String addressAlias="";//地址别名(仅微信使用)
	private String appSignature="";//地址别名(仅微信使用
	//订单状态 s00:订单初始化 s01:出货成功(已有运单号) s02:运单信息已推送平台(回写平台物流信息：运单号/物流公司) s03:交易完成(客户签收) s04:订单取消
	private String orderStatus="";
	private String feeType="";//币种 人民币：RMB
	private String payMentTime="";//付款时间
	private String tm_buyer_alipay_no="";//天猫买家支付宝账号
	private String tm_buyer_area="";//天猫买家下单的地区
	private String tm_buyer_email="";//天猫买家邮件地址
	private String tm_buyer_nick="";//天猫买家昵称

	private String kfAccount;//客服工号
	
	
	
	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getTm_buyer_alipay_no() {
		return tm_buyer_alipay_no;
	}

	public void setTm_buyer_alipay_no(String tm_buyer_alipay_no) {
		this.tm_buyer_alipay_no = tm_buyer_alipay_no;
	}

	public String getTm_buyer_area() {
		return tm_buyer_area;
	}

	public void setTm_buyer_area(String tm_buyer_area) {
		this.tm_buyer_area = tm_buyer_area;
	}

	public String getTm_buyer_email() {
		return tm_buyer_email;
	}

	public void setTm_buyer_email(String tm_buyer_email) {
		this.tm_buyer_email = tm_buyer_email;
	}

	public String getTm_buyer_nick() {
		return tm_buyer_nick;
	}

	public void setTm_buyer_nick(String tm_buyer_nick) {
		this.tm_buyer_nick = tm_buyer_nick;
	}

	public String getPayMentTime() {
		return payMentTime;
	}

	public void setPayMentTime(String payMentTime) {
		this.payMentTime = payMentTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOverReturnFree() {
		return overReturnFree;
	}

	public void setOverReturnFree(String overReturnFree) {
		this.overReturnFree = overReturnFree;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddressAlias() {
		return addressAlias;
	}

	public void setAddressAlias(String addressAlias) {
		this.addressAlias = addressAlias;
	}

	public String getAppSignature() {
		return appSignature;
	}

	public void setAppSignature(String appSignature) {
		this.appSignature = appSignature;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}