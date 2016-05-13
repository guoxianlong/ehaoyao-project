package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;

/**
 * 预约信息
 * @author Administrator
 *
 */
public class ReservationVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	/**
	 * 单据类型
	 */
	private String orderType;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 手机号
	 */
	private String tel;
	/**
	 * 预约回访日期
	 */
	private String reserveTime;
	/**
	 * 说明
	 */
	private String comment;
	/**
	 * 客服工号
	 */
	private String custServiceNo;
	/**
	 * 健康顾问
	 */
	private String custServiceNm;
	/**
	 * 产品关键字
	 */
	private String proKeywords;
	/**
	 * 是否完成
	 */
	private String status;
	/**
	 * 客户来源
	 */
	private String custSource;
	/**
	 * 沟通类型
	 */
	private String acceptResult;
	/**
	 * 上次沟通时间
	 */
	private String lastTime;
	/**
	 * @return the proKeywords
	 */
	public String getProKeywords() {
		return proKeywords;
	}
	/**
	 * @param proKeywords the proKeywords to set
	 */
	public void setProKeywords(String proKeywords) {
		this.proKeywords = proKeywords;
	}
	/**
	 * @return the custSource
	 */
	public String getCustSource() {
		return custSource;
	}
	/**
	 * @param custSource the custSource to set
	 */
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	/**
	 * @return the acceptResult
	 */
	public String getAcceptResult() {
		return acceptResult;
	}
	/**
	 * @param acceptResult the acceptResult to set
	 */
	public void setAcceptResult(String acceptResult) {
		this.acceptResult = acceptResult;
	}
	/**
	 * @return the lastTime
	 */
	public String getLastTime() {
		return lastTime;
	}
	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the reserveTime
	 */
	public String getReserveTime() {
		return reserveTime;
	}
	/**
	 * @param reserveTime the reserveTime to set
	 */
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the custServiceNo
	 */
	public String getCustServiceNo() {
		return custServiceNo;
	}
	/**
	 * @param custServiceNo the custServiceNo to set
	 */
	public void setCustServiceNo(String custServiceNo) {
		this.custServiceNo = custServiceNo;
	}
	/**
	 * @return the custServiceNm
	 */
	public String getCustServiceNm() {
		return custServiceNm;
	}
	/**
	 * @param custServiceNm the custServiceNm to set
	 */
	public void setCustServiceNm(String custServiceNm) {
		this.custServiceNm = custServiceNm;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
