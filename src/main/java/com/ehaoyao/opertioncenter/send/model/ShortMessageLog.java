package com.ehaoyao.opertioncenter.send.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Title: ShortMessageLog.java
 * 
 * Description: 短信记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:00:35
 */
@Entity
@Table(name = "short_message_log")
public class ShortMessageLog implements java.io.Serializable {
	private static final long serialVersionUID = -2991839604470975293L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;
	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_lastname")
	private String customerLastname;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "express_no")
	private String expressNo;
	@Column(name = "express_com_name")
	private String expressComName;
	@Column(name = "refund_money")
	private String refundMoney;
	@Column(name = "content")
	private String content;
	@Column(name = "order_flag")
	private String orderFlag;
	@Column(name = "order_status")
	private String orderStatus;
	@Column(name = "cash_delivery")
	private String cashDelivery;
	@Column(name = "send_flag")
	private String sendFlag;
	@Column(name = "status_time")
	private Date statusTime;
	@Column(name = "last_time")
	private Date lastTime;
	@Column(name = "remark")
	private String remark;

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLastname() {
		return this.customerLastname;
	}

	public void setCustomerLastname(String customerLastname) {
		this.customerLastname = customerLastname;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressComName() {
		return this.expressComName;
	}

	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderFlag() {
		return this.orderFlag;
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

	public String getSendFlag() {
		return this.sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}