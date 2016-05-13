package com.ehaoyao.opertioncenter.member.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="REQUESTXML")
public class RestParam {
	private String size;
	private List<RestObject> orderList;
	@XmlElement(name="SIZE")
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@XmlElement(name="ORDERLIST")
	public List<RestObject> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<RestObject> orderList) {
		this.orderList = orderList;
	}
	
}
