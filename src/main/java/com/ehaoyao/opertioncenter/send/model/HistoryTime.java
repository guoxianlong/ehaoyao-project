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
 * Title: HistoryTime.java
 * 
 * Description: 历史时间
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:00:13
 */
@Entity
@Table(name = "history_time")
public class HistoryTime implements java.io.Serializable {
	private static final long serialVersionUID = 1573666195690139797L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;
//	@Column(name = "rule_id")
//	private Long ruleId;
	@Column(name = "order_flag")
	private String orderFlag;
	@Column(name = "order_status")
	private String orderStatus;
	@Column(name = "cash_delivery")
	private String cashDelivery;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "end_time")
	private Date endTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Long getRuleId() {
//		return ruleId;
//	}
//	public void setRuleId(Long ruleId) {
//		this.ruleId = ruleId;
//	}
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}