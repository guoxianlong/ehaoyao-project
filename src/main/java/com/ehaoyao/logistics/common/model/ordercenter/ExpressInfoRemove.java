package com.ehaoyao.logistics.common.model.ordercenter;

/**
 * 订单中心,物流拆单表实体映射表
 */
public class ExpressInfoRemove {
	/**
	 * 物流拆单表id
	 */
    private Long id;

    /**
     * 运单号
     */
    private String expressId;

    /**
     * 运单公司id
     */
    private String expressComId;

    /**
     * 运单公司编码
     */
    private String expressComCode;

    /**
     * 运单公司名称
     */
    private String expressComName;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 配送日期
     */
    private String deliveryDate;

    /**
     * 渠道标识
     */
    private String orderFlag;

    /**
     * 拆单字单编号
     */
    private String subordersn;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 
     */
    private String toGw;

    /**
     * 拆单个数
     */
    private String split;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId == null ? null : expressId.trim();
    }

    public String getExpressComId() {
        return expressComId;
    }

    public void setExpressComId(String expressComId) {
        this.expressComId = expressComId == null ? null : expressComId.trim();
    }

    public String getExpressComCode() {
        return expressComCode;
    }

    public void setExpressComCode(String expressComCode) {
        this.expressComCode = expressComCode == null ? null : expressComCode.trim();
    }

    public String getExpressComName() {
        return expressComName;
    }

    public void setExpressComName(String expressComName) {
        this.expressComName = expressComName == null ? null : expressComName.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getSubordersn() {
        return subordersn;
    }

    public void setSubordersn(String subordersn) {
        this.subordersn = subordersn == null ? null : subordersn.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getToGw() {
        return toGw;
    }

    public void setToGw(String toGw) {
        this.toGw = toGw == null ? null : toGw.trim();
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split == null ? null : split.trim();
    }
}