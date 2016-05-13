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
 * Title: ChannelRule.java
 * 
 * Description: 渠道规则关系对应表
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月28日 上午11:21:26
 */
@Entity
@Table(name = "channel_rule")
public class ChannelRule implements java.io.Serializable {
	private static final long serialVersionUID = 4086055338452816125L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;
	
	/**
	 * 规则ID
	 */
	@Column(name = "rule_id")
	private Long ruleId;
	
	/**
	 * 订单标识（渠道或平台）
	 */
	@Column(name = "order_flag")
	private String orderFlag;
	
	
	@Column(name = "last_time")
	private Date lastTime;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

}