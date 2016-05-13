package com.ehaoyao.logistics.common.model.logisticscenter;

import java.util.Date;

/**
 * 运单主表数据库映射表
 *
 */
public class WayBillInfo {
	
	/***********************************运单配送状态定义****************************************************/
	
	/**
	 * 运单初始状态 	s00
	 */
	public static final String WAYBILL_INFO_STATUS_INIT = "s00";
	
	/**
	 * 快递拦件状态	s01
	 */
	public static final String WAYBILL_INFO_STATUS_COLLECTPARCEL  = "s01";
	
	/**
	 * 运单配送中	s02
	 */
	public static final String WAYBILL_INFO_STATUS_DISTRIBUTION = "s02";
	
	/**
	 * 运单拒收状态	s03
	 */
	public static final String WAYBILL_INFO_STATUS_REJECTION = "s03";
	
	/**
	 * 运单妥投状态	s04
	 */
	public static final String WAYBILL_INFO_STATUS_DELIVERED = "s04";
	
	/**
	 *	主键id 
	 */
    private Long id;
    
    /**
     * 订单标识
     */
    private String orderFlag;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 运单来源
     */
    private String waybillSource;

    /**
     * 运单号
     */
    private String waybillNumber;

    /**
     * 运单状态  s00 :初始化  s01:揽件  s02:配送中  s04：妥投
     */
    private String waybillStatus;

    /**
     * 运单妥投是否回写到订单中心 0:未回写  1:已回写
     */
    private Integer isWriteback;

    /**
     * 最新平台更新物流信息时间
     */
    private Date lastTime;

    /**
     * 运单创建时间
     */
    private Date createTime;
    
    /**
     * 最新更新时间  数据库已设置为自动更新
     */
    private Date modifyTime;
    
    /**
     * 订单时间(下单时间)
     */
    private Date orderTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
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

    public Integer getIsWriteback() {
        return isWriteback;
    }

    public void setIsWriteback(Integer isWriteback) {
        this.isWriteback = isWriteback;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	
    
}