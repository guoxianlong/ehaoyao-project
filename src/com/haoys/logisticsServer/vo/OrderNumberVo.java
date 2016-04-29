package com.haoys.logisticsServer.vo;

public class OrderNumberVo {
//订单号	
private String orderNumber;
//订单渠道
private String orderFlag;
//订单开始时间
private String startTime;
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
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}

}
