package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.util.List;

/**
 * 
 * Title: BuyRecordVO.java
 * 
 * Description: 购买记录 vo
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午3:58:25
 */
public class BuyRecordVO {
	
	//姓名
	private String userName;
	//电话
	private String telephoneNo;
	//时间间隔（三个月、六个月、十二个月、永久）
	private String timeNum;
	//商品编号
	private String productNo;

	//购买记录
	List<Order> orderList;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(String timeNum) {
		this.timeNum = timeNum;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

}
