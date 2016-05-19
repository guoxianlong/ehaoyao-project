package com.ehaoyao.cfy.model.operationcenter;

import java.math.BigDecimal;

public class OrderDetailThirdParty {
    private Long id;

    private String orderNumber;

    private String orderFlag;

    private String groupId;

    private String groupName;

    private Integer count;

    private BigDecimal thirdpartyTotalPrice;

    private BigDecimal thirdpartyGroupPrice;

    private BigDecimal ehaoyaoGroupPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getThirdpartyTotalPrice() {
        return thirdpartyTotalPrice;
    }

    public void setThirdpartyTotalPrice(BigDecimal thirdpartyTotalPrice) {
        this.thirdpartyTotalPrice = thirdpartyTotalPrice;
    }

    public BigDecimal getThirdpartyGroupPrice() {
        return thirdpartyGroupPrice;
    }

    public void setThirdpartyGroupPrice(BigDecimal thirdpartyGroupPrice) {
        this.thirdpartyGroupPrice = thirdpartyGroupPrice;
    }

    public BigDecimal getEhaoyaoGroupPrice() {
        return ehaoyaoGroupPrice;
    }

    public void setEhaoyaoGroupPrice(BigDecimal ehaoyaoGroupPrice) {
        this.ehaoyaoGroupPrice = ehaoyaoGroupPrice;
    }
}