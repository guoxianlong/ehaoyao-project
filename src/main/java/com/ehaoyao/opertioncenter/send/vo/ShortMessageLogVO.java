package com.ehaoyao.opertioncenter.send.vo;

import java.util.Date;

/**
 * 
 * Title: ShortMessageLogVO.java
 * 
 * Description: 短信记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:05:09
 */
public class ShortMessageLogVO implements java.io.Serializable {
	private static final long serialVersionUID = -8532129202360410830L;
	
	private String orderNumber;
	private String orderFlag;
	private String orderStatus;
	private String cashDelivery;
	private Date statusTime;
	private Date lastTime;
	private String expressNo;
	
	private int firstResult;
	private int pageSize;
	
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCashDelivery() {
		return cashDelivery;
	}
	public void setCashDelivery(String cashDelivery) {
		this.cashDelivery = cashDelivery;
	}
	public Date getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}