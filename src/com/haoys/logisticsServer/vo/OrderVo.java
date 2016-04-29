package com.haoys.logisticsServer.vo;

public class OrderVo {
	private String expressId;
	private String expressComId;
	private String expressComCode;
	private String expressComName;
	private String startTime;
	 //网上订单号
    private String  orderNumber;
    //网上订单来源
    private String orderFlag;
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressComId() {
		return expressComId;
	}
	public void setExpressComId(String expressComId) {
		this.expressComId = expressComId;
	}
	public String getExpressComCode() {
		return expressComCode;
	}
	public void setExpressComCode(String expressComCode) {
		this.expressComCode = expressComCode;
	}
	public String getExpressComName() {
		return expressComName;
	}
	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	public OrderVo(String expressId, String expressComId,
			String expressComCode, String expressComName, String startTime,
			String orderNumber, String orderFlag) {
		super();
		this.expressId = expressId;
		this.expressComId = expressComId;
		this.expressComCode = expressComCode;
		this.expressComName = expressComName;
		this.startTime = startTime;
		this.orderNumber = orderNumber;
		this.orderFlag = orderFlag;
	}
	public OrderVo(String expressId, String expressComId,
			String expressComCode, String expressComName) {
		super();
		this.expressId = expressId;
		this.expressComId = expressComId;
		this.expressComCode = expressComCode;
		this.expressComName = expressComName;
	}
	public OrderVo(String expressId, String expressComId,
			String expressComCode, String expressComName, String startTime,
			String orderFlag) {
		super();
		this.expressId = expressId;
		this.expressComId = expressComId;
		this.expressComCode = expressComCode;
		this.expressComName = expressComName;
		this.startTime = startTime;
		this.orderFlag = orderFlag;
	}
	
	

	
}
