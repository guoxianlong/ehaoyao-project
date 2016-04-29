package com.haoys.logisticsServer.vo;

import com.haoys.logisticsServer.timeTask.config.TaskConfig;

public class WXSendInfoVo {
	private String expressNo;
	private String expressName;
	private String information;
	private String mobile;
	private String orderNumber;
	private String orderFlag;
	private String payType;
	private String parnterCode=TaskConfig.getString("WxParnterCode");
	private String parnterkey=TaskConfig.getString("WxParnterkey");
	private String productName;
	private String receiver;
	private String startTime;
	private String sign;

	public String getParnterCode() {
		return parnterCode;
	}

	public void setParnterCode(String parnterCode) {
		this.parnterCode = parnterCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getParnterkey() {
		return parnterkey;
	}

	public void setParnterkey(String parnterkey) {
		this.parnterkey = parnterkey;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getSimpleJson() {
		String json="{\"parntercode\":\""+parnterCode+"\",\"parnterkey\":\""+parnterkey+"\",\"receiver\":\""+receiver+"\",\"information\":\""+information+"\",\"startTime\":\""+startTime+"\",\"expressNo\":\""+expressNo+"\",\"mobile\":"+mobile+",\"expressName\":\""+expressName+"\",\"payType\":\""+payType+"\",\"orderNumber\":\""+orderNumber+"\",\"productName\":\""+productName+"\",\"orderFlag\":\""+orderFlag+"\"}";		
		return json;
	}

	public String getUltimateJson() {
		String json="{\"parntercode\":\""+parnterCode+"\",\"sign\":\""+sign+"\",\"receiver\":\""+receiver+"\",\"information\":\""+information+"\",\"startTime\":\""+startTime+"\",\"expressNo\":\""+expressNo+"\",\"mobile\":"+mobile+",\"expressName\":\""+expressName+"\",\"payType\":\""+payType+"\",\"orderNumber\":\""+orderNumber+"\",\"productName\":\""+productName+"\",\"orderFlag\":\""+orderFlag+"\"}";
		return json;
	}
}
