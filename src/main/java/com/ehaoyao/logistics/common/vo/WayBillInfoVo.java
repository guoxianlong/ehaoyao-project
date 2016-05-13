/**
 * 
 */
package com.ehaoyao.logistics.common.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月13日 上午10:25:44
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public class WayBillInfoVo {
   private Long id;

    private String orderFlag;

    private String orderNumber;

    private String waybillSource;

    private String waybillNumber;

    private String waybillStatus;

    private Integer isWriteback;

    private Date lastTime;

    private Date createTime;
    
    private Date orderTime;
    
    private Date lastTimeStart;
    
    private Date lastTimeEnd;
    
    private Date createTimeStart;
    
    private Date createTimeEnd;
    
	private List<String> waybillStatusList;


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
    
    public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getLastTimeStart() {
		return lastTimeStart;
	}

	public void setLastTimeStart(Date lastTimeStart) {
		this.lastTimeStart = lastTimeStart;
	}

	public Date getLastTimeEnd() {
		return lastTimeEnd;
	}

	public void setLastTimeEnd(Date lastTimeEnd) {
		this.lastTimeEnd = lastTimeEnd;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public List<String> getWaybillStatusList() {
		return waybillStatusList;
	}

	public void setWaybillStatusList(List<String> waybillStatusList) {
		this.waybillStatusList = waybillStatusList;
	}
	
}
