package com.ehaoyao.yhdjkg.model.order_center;

/**
 * @author sjfeng
 *
 */
public class PrintDataInfo {
	private String id="";
	private String orderNumber="";
	// 出库时间
	private String outBoundDate="";
	// 送货前是否通知
	private String bfDeliGoodGlag="";
	// 送货时间
	private String godTimeName="";
	// 备注
	private String remark="";
	// 配送中心名称
	private String cky2Name="";
	// 分拣代码
	private String sortingCode="";
	// 订购时间
	private String creatDate="";
	// 支付金额
	private String shuoldPay="";
	// 支付方式
	private String paymentTypeStr="";
	// 配送站点
	private String partner="";
	// 条形码
	private String generade="";
	// 商品总数
	private String itemsCount="";
	//
	private String orderFlag="";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOutBoundDate() {
		return outBoundDate;
	}

	public void setOutBoundDate(String outBoundDate) {
		this.outBoundDate = outBoundDate;
	}

	public String getBfDeliGoodGlag() {
		return bfDeliGoodGlag;
	}

	public void setBfDeliGoodGlag(String bfDeliGoodGlag) {
		this.bfDeliGoodGlag = bfDeliGoodGlag;
	}

	public String getGodTimeName() {
		return godTimeName;
	}

	public void setGodTimeName(String godTimeName) {
		this.godTimeName = godTimeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCky2Name() {
		return cky2Name;
	}

	public void setCky2Name(String cky2Name) {
		this.cky2Name = cky2Name;
	}

	public String getSortingCode() {
		return sortingCode;
	}

	public void setSortingCode(String sortingCode) {
		this.sortingCode = sortingCode;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getShuoldPay() {
		return shuoldPay;
	}

	public void setShuoldPay(String shuoldPay) {
		this.shuoldPay = shuoldPay;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getGenerade() {
		return generade;
	}

	public void setGenerade(String generade) {
		this.generade = generade;
	}

	public String getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

}
