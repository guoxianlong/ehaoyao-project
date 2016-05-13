package com.ehaoyao.yhdjkg.domain;

import java.io.Serializable;
import java.util.List;

public class ParamsData implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */   
	private static final long serialVersionUID = 6463796633910211779L;

	private OrderInfo orderInfo;
	private List<OrderDetail> details;
	private InvoiceInfoPresc invoiceInfo;
	
	
	
	public InvoiceInfoPresc getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(InvoiceInfoPresc invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public List<OrderDetail> getDetails() {
		return details;
	}
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}
	
	
}
