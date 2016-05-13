package com.ehaoyao.yhdjkg.model.operation_center;

/**
 * 发票表实体
 * @author longshanw
 *
 */
public class InvoiceInfo {
	
	/**
	 * 发票表id
	 */
    private Long invoiceId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 渠道标识
     */
    private String orderFlag;
    
    /**
     * 客服工号
     */
    private String kfAccount;
    
    /**
     * 发票状态  0：不开发票  1：开发票
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
     * 创建时间
     */
    private String createTime;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }
    

    public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
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
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}