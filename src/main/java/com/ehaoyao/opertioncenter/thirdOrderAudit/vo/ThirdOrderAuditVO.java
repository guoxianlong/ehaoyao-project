package com.ehaoyao.opertioncenter.thirdOrderAudit.vo;

import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;

public class ThirdOrderAuditVO {

	/**
	 * 序号
	 */
	private int seq;
	/**
	 * 订单号
	 */
	private String orderNumber;
	
	/**
	 * 渠道标识
	 */
	private String orderFlag;
	
	/**
	 * 订单开始日期
	 */
	private String startDate;
	/**
	 * 订单结束日期
	 */
	private String endDate;

	/**
	 * 姓名
	 */
	private String receiver;
	/**
	 * 电话
	 */
	private String custTel;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 订单金额
	 */
	private String price;
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 处方类型
	 */
	private Character prescriptionType;
	/**
	 * 付款状态
	 */
	private String payStatus;
	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 审核状态
	 */
	private String auditStatus;
	
	/**
	 * 审核说明
	 */
	private String auditDescription;
	
	/**
	 * 客服工号
	 */
	private String custServCode;

	/**
	 * 审核状态
	 */
	private String reviewStatus;
	/**
	 * 审核人
	 */
	private String auditor;
	
	/**
	 * 操作状态
	 */
	private String oprStatus;
	
	/**
	 * 根据orderBy字段排序
	 */
	private String orderBy;
	
	/**
	 * sort : asc/desc
	 */
	private String sort;
	
	/**
     * 开票状态 0：否：1:是
     */
    private String invoiceStatus;
    
    /**
     * 发票类型  PLAIN：普通发票 ELECTRONIC：电子发票 VAT：增值税发票
     */
    private String invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票内容
     */
    private String invoiceContent;

    /**
     * 备注
     */
    private String remark;
    
    /**
	 * 驳回类型  F1E(配送不到) F1F(价格贵) F1G(顾客买错) F1H(无货) F1I(无处方单) F1J(价格错误) F1K(文描错误) F1L(电话不通) F1M(付款方式)
	 */
	private String rejectType;
	

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	
	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	
	public Character getPrescriptionType() {
		return prescriptionType;
	}

	public void setPrescriptionType(Character prescriptionType) {
		this.prescriptionType = prescriptionType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getCustServCode() {
		return custServCode;
	}

	public void setCustServCode(String custServCode) {
		this.custServCode = custServCode;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getOprStatus() {
		return oprStatus;
	}

	public void setOprStatus(String oprStatus) {
		this.oprStatus = oprStatus;
	}
	
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRejectType() {
		return rejectType;
	}

	public void setRejectType(String rejectType) {
		this.rejectType = rejectType;
	}


	/**
     * 获取审核状态描述
     * @return
     */
    public String getAuditStatusDesc(String auditStatus){
    	String auditStatusDesc = null;
    	if(auditStatus!=null && auditStatus.length()>0){
    		if(OrderInfo.ORDER_AUDIT_STATUS_WAIT.equals(auditStatus)){
    			auditStatusDesc = "等待客服审核";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_PRESUCC.equals(auditStatus)){
    			auditStatusDesc = "客服审核通过";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_PRERETURN.equals(auditStatus)){
    			auditStatusDesc = "客服审核驳回";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_SUCC.equals(auditStatus)){
    			auditStatusDesc = "医师审核通过";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_RETURN.equals(auditStatus)){
    			auditStatusDesc = "医师审核驳回";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_VOID.equals(auditStatus)){
    			auditStatusDesc = "作废";
    		}
    		if(OrderInfo.ORDER_AUDIT_STATUS_CANCEL.equals(auditStatus)){
    			auditStatusDesc = "取消";
    		}
    	}
    	return auditStatusDesc;
    }

	public String getPrescriptionTypeDesc(String prescriptionType){
    	String prescriptionTypeDesc = null;
    	if(prescriptionType!=null && prescriptionType.length()>0){
    		if("-1".equals(prescriptionType)){
    			prescriptionTypeDesc = "全部";
    		}
    		if("1".equals(prescriptionType)){
    			prescriptionTypeDesc = "处方药";
    		}
    		if("0".equals(prescriptionType)){
    			prescriptionTypeDesc = "非处方药";
    		}
    	}
    	return prescriptionTypeDesc;
    }
	
	public String getRejectTypeDesc(String rejectType){
    	String rejectTypeDesc = null;
    	if(rejectType!=null && rejectType.length()>0){
    		if("F1E".equals(rejectType)){
    			rejectTypeDesc = "配送不到";
    		}
    		if("F1F".equals(rejectType)){
    			rejectTypeDesc = "价格贵";
    		}
    		if("F1G".equals(rejectType)){
    			rejectTypeDesc = "顾客买错";
    		}
    		if("F1H".equals(rejectType)){
    			rejectTypeDesc = "无货";
    		}
    		if("F1I".equals(rejectType)){
    			rejectTypeDesc = "无处方单";
    		}
    		if("F1J".equals(rejectType)){
    			rejectTypeDesc = "价格错误";
    		}
    		if("F1K".equals(rejectType)){
    			rejectTypeDesc = "文描错误";
    		}
    		if("F1L".equals(rejectType)){
    			rejectTypeDesc = "电话不通";
    		}
    		if("F1M".equals(rejectType)){
    			rejectTypeDesc = "付款方式";
    		}
    	}
    	return rejectTypeDesc;
    }

	public String getOrderFlagDesc(String orderFlag) {
		String orderFlagDesc = null;
		if(orderFlag!=null&&orderFlag.length()>0){
			if("-1".equals(orderFlag)){
				orderFlagDesc = "全部";
			}
			if(OrderInfo.ORDER_ORDER_FLAG_TMCFY.equals(orderFlag)){
				orderFlagDesc = "天猫处方药";
			}
			if(OrderInfo.ORDER_ORDER_FLAG_PA.equals(orderFlag)){
				orderFlagDesc = "平安处方药";
			}
			if(OrderInfo.ORDER_ORDER_FLAG_ZSTY.equals(orderFlag)){
				orderFlagDesc = "掌上糖医";
			}
		}
		return orderFlagDesc;
	}

	public String getPayStatusDesc(String payStatus) {
		String payStatusDesc = null;
		if(payStatus!=null&&payStatus.length()>0){
			if(OrderInfo.ORDER_PAY_STATUS_NOPAY.equals(payStatus)){
				payStatusDesc = "未付款";
			}
			if(OrderInfo.ORDER_PAY_STATUS_PAID.equals(payStatus)){
				payStatusDesc = "已付款";
			}
		}
		return payStatusDesc;
	}
}
