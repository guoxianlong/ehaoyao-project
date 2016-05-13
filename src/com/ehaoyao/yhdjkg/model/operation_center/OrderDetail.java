package com.ehaoyao.yhdjkg.model.operation_center;

import java.math.BigDecimal;

/**
 * 订单明细表
 * @author longshanw
 * @date 2016年2月14日
 * @version 0.0.1
 */
public class OrderDetail {
	
	/**
	 * 订单明细表主键
	 */
    private Long orderDetailId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 渠道标识
     */
    private String orderFlag;

    /**
     * 三方平台商品编号
     */
    private String merchantId;

    /**
     * 九州通商品编号
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 商品规格
     */
    private String productSpec;

    /**
     * 品牌
     */
    private String productBrand;

    /**
     * 包装数量
     */
    private Integer packCount;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否为赠品  0：不是  1：是
     */
    private String giftFlag;

    /**
     * 处方类型   0:不是  1:是
     */
    private String prescriptionType;

    /**
     * 商品许可证号
     */
    private String productLicenseNo;

    /**
     * 制药公司
     */
    private String pharmacyCompany;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后操作时间
     */
    private String lastTime;

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec == null ? null : productSpec.trim();
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand == null ? null : productBrand.trim();
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getGiftFlag() {
        return giftFlag;
    }

    public void setGiftFlag(String giftFlag) {
        this.giftFlag = giftFlag == null ? null : giftFlag.trim();
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType == null ? null : prescriptionType.trim();
    }

    public String getProductLicenseNo() {
        return productLicenseNo;
    }

    public void setProductLicenseNo(String productLicenseNo) {
        this.productLicenseNo = productLicenseNo == null ? null : productLicenseNo.trim();
    }

    public String getPharmacyCompany() {
        return pharmacyCompany;
    }

    public void setPharmacyCompany(String pharmacyCompany) {
        this.pharmacyCompany = pharmacyCompany == null ? null : pharmacyCompany.trim();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }
}