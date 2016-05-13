package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 预约信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition="bigint", length=20, nullable = false)
	private int id;
	/**
	 * 单据类型
	 */
	@Column(name = "order_type")
	private String orderType;
	/**
	 * 手机号
	 */
	@Column(name = "tel")
	private String tel;
	/**
	 * 预约回访日期
	 */
	@Column(name = "reserve_time")
	private String reserveTime;
	/**
	 * 说明
	 */
	@Column(name = "comment")
	private String comment;
	/**
	 * 客服工号
	 */
	@Column(name = "cust_service_no")
	private String custServiceNo;
	/**
	 * 是否完成
	 */
	@Column(name = "status")
	private String status;
	/**
	 * 客户来源
	 */
	@Column(name = "cust_source")
	private String custSource;
	/**
	 * 产品关键字
	 */
	@Column(name = "pro_keywords")
	private String proKeywords;
	/**
	 * 沟通类型
	 */
	@Column(name = "accept_result")
	private String acceptResult;
	/**
	 * 上次沟通时间
	 */
	@Column(name = "last_time")
	private String lastTime;
	/**
	 * 完成时间
	 */
	@Column(name = "complete_time")
	private String completeTime;
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
	/**
	 * @return the completeTime
	 */
	public String getCompleteTime() {
		return completeTime;
	}
	/**
	 * @param completeTime the completeTime to set
	 */
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
}
