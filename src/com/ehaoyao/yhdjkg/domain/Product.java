package com.ehaoyao.yhdjkg.domain;

import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = -1L;
	/**商品id*/
	private String spid;
	/**京东的skuid*/
	private String skuid;
	/**天猫skuid*/
	private String tmskuid;
	/**九州通商品id*/
	private String outerid;
	/**库存*/
	private int stock;
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getSkuid() {
		return skuid;
	}
	
	public String getTmskuid() {
		return tmskuid;
	}
	public void setTmskuid(String tmskuid) {
		this.tmskuid = tmskuid;
	}
	public String getOuterid() {
		return outerid;
	}
	public void setOuterid(String outerid) {
		this.outerid = outerid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Product [outerid=" + outerid + ", skuid=" + skuid + ", spid="
				+ spid + ", stock=" + stock + ", tmskuid=" + tmskuid + "]";
	}
	
	
}
