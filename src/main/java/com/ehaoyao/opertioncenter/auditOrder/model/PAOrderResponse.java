package com.ehaoyao.opertioncenter.auditOrder.model;

import java.util.List;
/**
 * 
 * @author xushunxing
 *  调用平安接口返回的数据对象
 */
public class PAOrderResponse {


	//订单详情集合
	private List<PAOrderDetailDO> orderDetailDOList;
	private int resultCode;
	private boolean success;
	//系统时间
	private long systemTime;
	//订单总数
	private int totalCount;
	
	public List<PAOrderDetailDO> getOrderDetailDOList() {
		return orderDetailDOList;
	}

	public void setOrderDetailDOList(List<PAOrderDetailDO> orderDetailDOList) {
		this.orderDetailDOList = orderDetailDOList;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	@Override
	public String toString() {
		return "OrderResponse [orderDetailDOList=" + orderDetailDOList
				+ ", resultCode=" + resultCode + ", success=" + success
				+ ", systemTime=" + systemTime + ", totalCount=" + totalCount
				+ "]";
	}
}
