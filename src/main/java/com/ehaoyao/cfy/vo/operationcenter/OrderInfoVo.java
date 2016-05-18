package com.ehaoyao.cfy.vo.operationcenter;

public class OrderInfoVo {
	
	private String orderNumber;

    private String orderFlag;

    private String auditStatus;

    private String auditTimeStart;

    private String auditTimeEnd;
    
    /**
     * 开始游标
     */
    private Integer count;
    
    /**
     * 条数
     */
    private Integer pageSize;

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

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditTimeStart() {
		return auditTimeStart;
	}

	public void setAuditTimeStart(String auditTimeStart) {
		this.auditTimeStart = auditTimeStart;
	}

	public String getAuditTimeEnd() {
		return auditTimeEnd;
	}

	public void setAuditTimeEnd(String auditTimeEnd) {
		this.auditTimeEnd = auditTimeEnd;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
    
    
    
}
