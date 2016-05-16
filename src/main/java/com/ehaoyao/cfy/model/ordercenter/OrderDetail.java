package com.ehaoyao.cfy.model.ordercenter;

public class OrderDetail {
    private Long id;

    private String orderNumber;

    private String merchantId;

    private Double totalPrice;

    private Double count;

    private String productId;

    private String unit;

    private String productName;

    private Double price;

    private String orderFlag;

    private String productsPromoId;

    private String discountAmount;

    private String djbhId;

    private String eflag;

    private String spid;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getProductsPromoId() {
        return productsPromoId;
    }

    public void setProductsPromoId(String productsPromoId) {
        this.productsPromoId = productsPromoId == null ? null : productsPromoId.trim();
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount == null ? null : discountAmount.trim();
    }

    public String getDjbhId() {
        return djbhId;
    }

    public void setDjbhId(String djbhId) {
        this.djbhId = djbhId == null ? null : djbhId.trim();
    }

    public String getEflag() {
        return eflag;
    }

    public void setEflag(String eflag) {
        this.eflag = eflag == null ? null : eflag.trim();
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid == null ? null : spid.trim();
    }
}