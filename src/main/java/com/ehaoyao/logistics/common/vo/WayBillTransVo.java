package com.ehaoyao.logistics.common.vo;

/**
 * 主要用于根据运单来源及运单号进行去重
 * @author longshanw
 *
 */
public class WayBillTransVo {
	/**
	 * 运单来源
	 */
	private String wayBillSource;
	/**
	 * 运单来源+运单号
	 */
	private String wayBillSourceNum;
	/**
	 * 订单来源+订单号
	 */
	private String orderFlagNum;
	/**
	 * 封装查询实体
	 */
	private OrderExpressVo orderExpress;

	public WayBillTransVo() {
		super();
	}




	public String getWayBillSource() {
		return wayBillSource;
	}

	public void setWayBillSource(String wayBillSource) {
		this.wayBillSource = wayBillSource;
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
