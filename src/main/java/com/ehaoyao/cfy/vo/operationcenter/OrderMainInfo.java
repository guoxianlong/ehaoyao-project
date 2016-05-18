package com.ehaoyao.cfy.vo.operationcenter;

import java.util.List;

import com.ehaoyao.cfy.model.operationcenter.InvoiceInfo;
import com.ehaoyao.cfy.model.operationcenter.OrderDetail;
import com.ehaoyao.cfy.model.operationcenter.OrderInfo;

public class OrderMainInfo {

	 private Long orderAuditLogId;

    private String orderNumber;

    private String orderFlag;

    private String kfAccount;

    private String auditUser;

    private String auditStatus;

    private String auditDescription;

    private String remark;

    private String createTime;
	    
	private OrderInfo orderInfo;
	
	private InvoiceInfo invoiceInfo;
	
	private List<OrderDetail> orderDetailList;
	

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public Long getOrderAuditLogId() {
		return orderAuditLogId;
	}

	public void setOrderAuditLogId(Long orderAuditLogId) {
		this.orderAuditLogId = orderAuditLogId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditDescription() {
		return auditDescription;
	}

	public void setAuditDescription(String auditDescription) {
		this.auditDescription = auditDescription;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}


	public InvoiceInfo getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
	
}
