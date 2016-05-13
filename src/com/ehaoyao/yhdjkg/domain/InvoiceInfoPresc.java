package com.ehaoyao.yhdjkg.domain;


/**
 * 天猫处方药发票实体
 * @author longshanw
 *
 */
public class InvoiceInfoPresc {
	

    /**
     * 发票类型枚举值  PLAIN：普通发票 ELECTRONIC：电子发票 VAT：增值税发票
     */
    private String invoiceType;
    
    /**
     * 发票类型描述  PLAIN：普通发票 ELECTRONIC：电子发票 VAT：增值税发票
     */
    private String invoiceTypeDesc;

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



    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }
    

    public String getInvoiceTypeDesc() {
		return invoiceTypeDesc;
	}

	public void setInvoiceTypeDesc(String invoiceTypeDesc) {
		this.invoiceTypeDesc = invoiceTypeDesc;
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
		this.remark = remark;
	}

}