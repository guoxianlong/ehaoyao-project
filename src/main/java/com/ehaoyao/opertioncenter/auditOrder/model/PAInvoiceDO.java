package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 
 * 每笔订单的发票信息
 * @author xushunxing
 *
 */
public class PAInvoiceDO {
	//发票内容
	private int invoiceContentId;
	//开票人标识:公司-个人
	private int invoiceSignal;
	private String invoiceTitle;
	//发票类型
	private int invoiceTypeId;
	
	public int getInvoiceContentId() {
		return invoiceContentId;
	}

	public void setInvoiceContentId(int invoiceContentId) {
		this.invoiceContentId = invoiceContentId;
	}

	public int getInvoiceSignal() {
		return invoiceSignal;
	}

	public void setInvoiceSignal(int invoiceSignal) {
		this.invoiceSignal = invoiceSignal;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public int getInvoiceTypeId() {
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(int invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}

}
