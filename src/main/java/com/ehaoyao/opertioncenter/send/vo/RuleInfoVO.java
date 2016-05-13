package com.ehaoyao.opertioncenter.send.vo;

import java.util.Date;

public class RuleInfoVO implements java.io.Serializable {
	private static final long serialVersionUID = -2866756946183179312L;
	
	private Long id;
	private Long ruleId;
	private String orderFlag;//渠道、订单标识
	
	//规则名称
	private String ruleName;
	//规则信息
	private String content;
	/**
	 * 订单状态
	 * 1:已下单，2：配货中，3：已发货，4：已完成 ，5：拆单，6：运单已复核
	 */
	private String orderStatus;
	private String cashDelivery;//货到付款：0：否，1：是
	private Integer startHour;
	private Integer endHour;
	private String outTimeFlag;//0：时间外自动顺延，1：时间外跳过不发
	private String enable;//是否启用短信规则,0：未启用，1：已启用
	private Date lastTime;
	private int wordCount;
	
	public Long getId() {
		return id;
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
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return outTimeFlag;
	}
	public void setOutTimeFlag(String outTimeFlag) {
		this.outTimeFlag = outTimeFlag;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getEnable() {
		return enable;
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
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	
}