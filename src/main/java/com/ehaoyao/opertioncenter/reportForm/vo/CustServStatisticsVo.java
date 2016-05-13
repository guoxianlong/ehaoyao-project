package com.ehaoyao.opertioncenter.reportForm.vo;


/**
 * 
 * <p>Title: CustServStatisticsVo</p>
 * <p>Description: 客服统计</p>
 * @author	zsj
 * @date	2015年1月29日上午11:22:55
 * @version 1.0
 */
public class CustServStatisticsVo {
	private Long id;
	private String consultDate;//查询时间
	private String name;//客服姓名
	private String consultAmount;//咨询量
	private String orderAmount;//成单数量
	private String newUserConsult;//新增会员咨询量
	private String newUserOrder;//新增会员成单量
	private String orderTotalPrice;//订单总金额
	public String getNewUserConsult() {
		return newUserConsult;
	}
	public void setNewUserConsult(String newUserConsult) {
		this.newUserConsult = newUserConsult;
	}
	public String getNewUserOrder() {
		return newUserOrder;
	}
	public void setNewUserOrder(String newUserOrder) {
		this.newUserOrder = newUserOrder;
	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getConsultAmount() {
		return consultAmount;
	}
	public void setConsultAmount(String consultAmount) {
		this.consultAmount = consultAmount;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConsultDate() {
		return consultDate;
	}
	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
