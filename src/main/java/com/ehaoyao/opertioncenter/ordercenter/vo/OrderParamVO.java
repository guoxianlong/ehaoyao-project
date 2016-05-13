package com.ehaoyao.opertioncenter.ordercenter.vo;

/**
 * 
 * Title: OrderDataVO.java
 * 
 * Description: 订单中心订单
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月23日 下午2:31:38
 */
public class OrderParamVO {
	private String orderNumber;
	private String orderFlag;
	private String startDate;//订单日期-开始时间
	private String endDate;//订单日期-截止时间
	private String jdsOrderStatus;//极速达订单状态
	private String tel;//联系电话

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

	public String getJdsOrderStatus() {
		return jdsOrderStatus;
	}

	public void setJdsOrderStatus(String jdsOrderStatus) {
		this.jdsOrderStatus = jdsOrderStatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
