package com.haoys.logisticsServer.entity;

import java.io.Serializable;

public class LogisticsDetail implements Serializable {
	private static final long serialVersionUID = -5510139060927599378L;
	private Integer id;
	private String receiptTime;
	private String context;
	private String receiptAddress;
	private String trackingNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getReceiptAddress() {
		return receiptAddress;
	}

	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LogisticsDetail() {
		super();
	}

	public LogisticsDetail(String receiptTime, String context,
			String receiptAddress, String trackingNumber) {
		super();
		this.receiptTime = receiptTime;
		this.context = context;
		this.receiptAddress = receiptAddress;
		this.trackingNumber = trackingNumber;
	}

	public LogisticsDetail(Integer id, String receiptTime, String context,
			String receiptAddress, String trackingNumber) {
		super();
		this.id = id;
		this.receiptTime = receiptTime;
		this.context = context;
		this.receiptAddress = receiptAddress;
		this.trackingNumber = trackingNumber;
	}

}
