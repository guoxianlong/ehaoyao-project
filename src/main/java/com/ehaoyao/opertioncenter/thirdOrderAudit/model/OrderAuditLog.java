package com.ehaoyao.opertioncenter.thirdOrderAudit.model;
// Generated 2016-3-4 15:11:40 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrderAuditLog generated by hbm2java
 */
@Entity
@Table(name = "order_audit_log", catalog = "operationscenter")
public class OrderAuditLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4008999679652592296L;
	
	private Long orderAuditLogId;
	private String orderNumber;
	private String orderFlag;
	private String kfAccount;
	private String auditUser;
	private String auditStatus;
	private String auditDescription;
    private String remark;
	private String createTime;
	/**
	 * 驳回类型  F1E(配送不到) F1F(价格贵) F1G(顾客买错) F1H(无货) F1I(无处方单) F1J(价格错误) F1K(文描错误) F1L(电话不通) F1M(付款方式)
	 */
	private String rejectType;

	public OrderAuditLog() {
	}

	public OrderAuditLog(String orderNumber, String orderFlag) {
		this.orderNumber = orderNumber;
		this.orderFlag = orderFlag;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "order_audit_log_id", unique = true, nullable = false)
	public Long getOrderAuditLogId() {
		return this.orderAuditLogId;
	}

	public void setOrderAuditLogId(Long orderAuditLogId) {
		this.orderAuditLogId = orderAuditLogId;
	}

	@Column(name = "order_number", nullable = false, length = 50)
	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "order_flag", nullable = false, length = 20)
	public String getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	@Column(name = "kf_account", length = 50)
	public String getKfAccount() {
		return this.kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	@Column(name = "audit_user", length = 20)
	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "audit_status", length = 20)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "audit_description", length = 2000)
	public String getAuditDescription() {
		return this.auditDescription;
	}
	
	@Column(name = "remark", length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAuditDescription(String auditDescription) {
		this.auditDescription = auditDescription;
	}

	@Column(name = "create_time", length = 30)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "reject_type", length = 10)
	public String getRejectType() {
		return rejectType;
	}

	public void setRejectType(String rejectType) {
		this.rejectType = rejectType;
	}
	
	

}
