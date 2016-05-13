package com.ehaoyao.opertioncenter.send.vo;

import java.util.Date;

import com.ehaoyao.opertioncenter.common.PageModel;

/**
 * 
 * Title: ShortMessageRuleVO.java
 * 
 * Description: 短信规则
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:05:34
 */
public class ShortMessageRuleVO<E> {
	PageModel<E> pageModel;
	private Long id;
	private String orderStatus;//订单状态 1:已下单，2：配货中，3：已发货，4：已完成 ，5：拆单
	private String cashDelivery;//仅针对货到付款 0：否，1：是
	private Integer startHour;
	private Integer endHour;
	private String outTimeFlag;//0：时间外自动顺延，1：时间外跳过不发
	private String orderFlag;//订单标志
	private String enable;//是否启用短信规则,0：未启用，1：已启用
	private Date lastTime;
	
	private Date curTime;//当前时间
	private Integer curHour;//当前时间,小时
	
	private Date startTime;
	private Date endTime;
	
	private String sTime;
	
	public PageModel<E> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<E> pageModel) {
		this.pageModel = pageModel;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCurTime() {
		return curTime;
	}

	public void setCurTime(Date curTime) {
		this.curTime = curTime;
	}

	public Integer getCurHour() {
		return curHour;
	}

	public void setCurHour(Integer curHour) {
		this.curHour = curHour;
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

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	
}
