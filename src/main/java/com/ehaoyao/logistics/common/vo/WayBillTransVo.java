package com.ehaoyao.logistics.common.vo;

public class WayBillTransVo {
	private String wayBillSourceNum;
	private String orderFlagNum;
	private OrderExpressVo orderExpress;

	public WayBillTransVo() {
		super();
	}

	public WayBillTransVo(String wayBillSourceNum, String orderFlagNum, OrderExpressVo orderExpress) {
		super();
		this.wayBillSourceNum = wayBillSourceNum;
		this.orderFlagNum = orderFlagNum;
		this.orderExpress = orderExpress;
	}

	public String getWayBillSourceNum() {
		return wayBillSourceNum;
	}

	public void setWayBillSourceNum(String wayBillSourceNum) {
		this.wayBillSourceNum = wayBillSourceNum;
	}

	public String getOrderFlagNum() {
		return orderFlagNum;
	}

	public void setOrderFlagNum(String orderFlagNum) {
		this.orderFlagNum = orderFlagNum;
	}

	public OrderExpressVo getOrderExpress() {
		return orderExpress;
	}

	public void setOrderExpress(OrderExpressVo orderExpress) {
		this.orderExpress = orderExpress;
	}

	@Override
	public int hashCode() {
		return this.wayBillSourceNum.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof WayBillTransVo) {
			if (((WayBillTransVo) obj).getWayBillSourceNum().equals(this.wayBillSourceNum)) {
				return true;
			}
		}
		return false;
	}

}
