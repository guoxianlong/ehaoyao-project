package com.haoys.logisticsServer.entity;

import java.io.Serializable;

public class LogisticsInfo implements Serializable {

	private static final long serialVersionUID = -8306944645320458473L;
	private Integer id;
	//快递单号
	private String trackingNumber;
	//快递公司来源
	private String source;
	//快递状态
	private String status;
	//订单开始时间
    private String startTime;
    //订单结束时间
    private String endTime;
    //网上订单号
    private String  orderNumber;
    //网上订单来源
    private String orderFlag;
    //订单回写状态
    private Integer isWriteBack;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getIsWriteBack() {
		return isWriteBack;
	}

	public void setIsWriteBack(Integer isWriteBack) {
		this.isWriteBack = isWriteBack;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public LogisticsInfo(Integer id, String trackingNumber, String source,
			String status, String startTime, String endTime,
			String orderNumber, String orderFlag, Integer isWriteBack) {
		super();
		this.id = id;
		this.trackingNumber = trackingNumber;
		this.source = source;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.orderNumber = orderNumber;
		this.orderFlag = orderFlag;
		this.isWriteBack = isWriteBack;
	}

	public LogisticsInfo(String trackingNumber, String source, String status,
			String startTime, String endTime, String orderNumber,
			String orderFlag, Integer isWriteBack) {
		super();
		this.trackingNumber = trackingNumber;
		this.source = source;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
		this.orderNumber = orderNumber;
		this.orderFlag = orderFlag;
		this.isWriteBack = isWriteBack;
	}

	public LogisticsInfo(String trackingNumber, String source, String status,
			String startTime, String orderNumber, String orderFlag,
			Integer isWriteBack) {
		super();
		this.trackingNumber = trackingNumber;
		this.source = source;
		this.status = status;
		this.startTime = startTime;
		this.orderNumber = orderNumber;
		this.orderFlag = orderFlag;
		this.isWriteBack = isWriteBack;
	}

	public LogisticsInfo(String trackingNumber, String source,
			String status, String startTime, String orderFlag,
			Integer isWriteBack) {
		super();
		this.trackingNumber = trackingNumber;
		this.source = source;
		this.status = status;
		this.startTime = startTime;
		this.orderFlag = orderFlag;
		this.isWriteBack = isWriteBack;
	}

	public LogisticsInfo() {
		super();
	}



	


}
