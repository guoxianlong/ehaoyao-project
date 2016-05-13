package com.ehaoyao.logistics.common.model.logisticscenter;

import java.util.Date;

/**
 * 运单明细数据库映射表
 *
 */
public class WayBillDetail {
	
	/**
	 * 主键id
	 */
    private Long id;

    /*
     * 运单来源
     */
    private String waybillSource;

    /**
     * 运单号
     */
    private String waybillNumber;

    /**
     * 运单状态
     */
    private String waybillStatus;

    /**
     * 运单跟踪信息时间
     */
    private Date waybillTime;

    /**
     * 运单跟踪内容
     */
    private String waybillContent;
    
    /**
     * 运单配送地址
     */
    private String waybillAddress;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWaybillSource() {
        return waybillSource;
    }

    public void setWaybillSource(String waybillSource) {
        this.waybillSource = waybillSource == null ? null : waybillSource.trim();
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber == null ? null : waybillNumber.trim();
    }

    public String getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(String waybillStatus) {
        this.waybillStatus = waybillStatus == null ? null : waybillStatus.trim();
    }

    public Date getWaybillTime() {
        return waybillTime;
    }

    public void setWaybillTime(Date waybillTime) {
        this.waybillTime = waybillTime;
    }

    public String getWaybillContent() {
        return waybillContent;
    }

    public void setWaybillContent(String waybillContent) {
        this.waybillContent = waybillContent == null ? null : waybillContent.trim();
    }
    
    public String getWaybillAddress() {
		return waybillAddress;
	}

	public void setWaybillAddress(String waybillAddress) {
		this.waybillAddress = waybillAddress  == null ? null : waybillAddress.trim();
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
    
    
}