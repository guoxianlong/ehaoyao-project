package com.ehaoyao.opertioncenter.send.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ehaoyao.opertioncenter.common.EntityUtil;

/**
 * 
 * Title: ShortMessageRule.java
 * 
 * Description: 短信规则
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:01:24
 */
@Entity
@Table(name = "short_message_rule")
public class ShortMessageRule implements java.io.Serializable {
	private static final long serialVersionUID = -657776908865843372L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;
	@Column(name = "rule_name")
	private String ruleName;
	@Column(name = "content")
	private String content;
	@Column(name = "order_status")
	private String orderStatus;//订单状态 1:已下单，2：配货中，3：已发货，4：已完成 ，5：拆单
	@Column(name = "cash_delivery")
	private String cashDelivery;//仅针对货到付款 0：否，1：是
	@Column(name = "start_hour")
	private Integer startHour;
	@Column(name = "end_hour")
	private Integer endHour;
	@Column(name = "out_time_flag")
	private String outTimeFlag;//0：时间外自动顺延，1：时间外跳过不发
//	@Column(name = "order_flag")
//	private String orderFlag;
	@Column(name = "enable")
	private String enable;//是否启用短信规则,0：未启用，1：已启用
	@Column(name = "last_time")
	private Date lastTime;
	@Column(name = "word_count")
	private int wordCount;
	
//	private String orderStatus_CN;
//	private String cashDelivery_CN;
//	private String orderFlag_CN;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCashDelivery() {
		return this.cashDelivery;
	}

	public void setCashDelivery(String cashDelivery) {
		this.cashDelivery = cashDelivery;
//		setCashDelivery_CN(EntityUtil.getCashDeliveryChange(cashDelivery));
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public String getOutTimeFlag() {
		return this.outTimeFlag;
	}

	public void setOutTimeFlag(String outTimeFlag) {
		this.outTimeFlag = outTimeFlag;
	}

//	public String getOrderFlag() {
//		return this.orderFlag;
//	}
//
//	public void setOrderFlag(String orderFlag) {
//		this.orderFlag = orderFlag;
////		setOrderFlag_CN(EntityUtil.getOrderFlagChange(orderFlag));
//	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the wordCount
	 */
	public int getWordCount() {
		return wordCount;
	}
	/**
	 * @param wordCount the wordCount to set
	 */
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public String getCashDelivery_CN() {
//		this.cashDelivery_CN = EntityUtil.getCashDeliveryChange(cashDelivery);
//		return cashDelivery_CN;
		return EntityUtil.getCashDeliveryChange(cashDelivery);
	}

//	public void setCashDelivery_CN(String cashDelivery_CN) {
//		this.cashDelivery_CN = cashDelivery_CN;
//	}

//	public String getOrderFlag_CN() {
////		this.orderFlag_CN = EntityUtil.getOrderFlagChange(orderFlag);
////		return orderFlag_CN;
//		return EntityUtil.getOrderFlagChange(orderFlag);
//	}

//	public void setOrderFlag_CN(String orderFlag_CN) {
//		this.orderFlag_CN = orderFlag_CN;
//	}

	/**
	 * @return the orderStatus_CN
	 */
	public String getOrderStatus_CN() {
//		this.orderStatus_CN = EntityUtil.getOrderStatusChange(orderStatus);
//		return orderStatus_CN;
		return EntityUtil.getOrderStatusChange(orderStatus);
	}

	/**
	 * @param orderStatus_CN the orderStatus_CN to set
	 */
//	public void setOrderStatus_CN(String orderStatus_CN) {
//		this.orderStatus_CN = orderStatus_CN;
//	}
	
}