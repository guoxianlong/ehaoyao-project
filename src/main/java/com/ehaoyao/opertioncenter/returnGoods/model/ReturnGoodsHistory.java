package com.ehaoyao.opertioncenter.returnGoods.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退货订单历史
 * @author zhang
 *
 */
@Entity
@Table(name = "return_goods_history")
public class ReturnGoodsHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	/**
	 * 订单号
	 */
	@Column(name = "order_number")
	private Long orderNumber;
	/**
	 * 退款金额
	 */
	@Column(name = "amount")
	private double amount;
	/**
	 * 原因
	 */
	@Column(name = "reason")
	private String reason;  
	/**
	 * 客服编号
	 */
	@Column(name = "cust_service_no")
	private String custServiceNo;
	/**
	 * 操作日期
	 */
	@Column(name = "operate_time")
	private String operateTime;
	/**
	 * 操作类型
	 */
	@Column(name = "operate_flag")
	private String operateFlag;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
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
	 * @return the orderNumber
	 */
	public Long getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
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
	 * @return the operateTime
	 */
	public String getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the operateFlag
	 */
	public String getOperateFlag() {
		return operateFlag;
	}
	/**
	 * @param operateFlag the operateFlag to set
	 */
	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount * 100;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
