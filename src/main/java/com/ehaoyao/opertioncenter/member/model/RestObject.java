package com.ehaoyao.opertioncenter.member.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="ORDER")
public class RestObject {
	private String orderid;
	@XmlElement(name="ORDERID")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
}
