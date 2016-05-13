package com.ehaoyao.opertioncenter.auditOrder.model;


/**
 * @author xushunxing 
 * @version 创建时间：2015年10月21日 上午10:38:07
 * 平安订单查询参数
 */
public class PAOrderQueryParam {
	
	private Integer pageNo;
	private Integer pageSize;
	//订单创建时间开始
	private String startCreated;
	//订单创建时间结束
	private String endCreated;
	/**
	 * 	0：全部订单；
		1：普通订单：
		2：未审核处方药订单；
		3：审核通过处方药订单；
		4：审核未通过处方药订
	 */
	private String orderType;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartCreated() {
		return startCreated;
	}
	public void setStartCreated(String startCreated) {
		this.startCreated = startCreated;
	}
	public String getEndCreated() {
		return endCreated;
	}
	public void setEndCreated(String endCreated) {
		this.endCreated = endCreated;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}	
