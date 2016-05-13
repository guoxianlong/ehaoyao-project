package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 收货人信息
 * @author xushunxing
 *
 */
public class PALgOrderInfoDO {
	//收货人地址
	private String address;
	//地区名
	private String area;
	//平安订单id
	private long bizOrderId;
	//购买人id
	private long buyerId;
	//市
	private String city;
	private String fullName;
	//手机
	private String mobilePhone;
	//省
	private String prov;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

}
