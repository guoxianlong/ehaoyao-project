package com.ehaoyao.yhdjkg.model.order_center;

/**
 * @author sjfeng
 *
 */
public class InvoiceInfo {
	private Long id=0L;
	private String orderNumber="";
	private String invoiceTitle="";
	private String invoiceContent="";
	private Double orderPrice=0.0;
	private String invoiceType="";
	private String remark="";
	private String orderFlag="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

}