package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 每笔订单中每种药品清单
 * @author xushunxing
 *
 */
public class PADrugDO {
	//每种药购买数量
	private int buyAmount;
	
	private long itemId;
	//图片地址
	private String itemImage;
	//药品名称
	private String itemName;
	//药品价格 , 单位分
	private double price;
	//好药师药品id
	private String productCode;
	private String standard;
	private String licence;
	private String factoryName;
	private String isPrescribed;
	//商品编码
	private long referId;
	
	
	public long getReferId() {
		return referId;
	}

	public void setReferId(long referId) {
		this.referId = referId;
	}

	public String getIsPrescribed() {
		return isPrescribed;
	}

	public void setIsPrescribed(String isPrescribed) {
		this.isPrescribed = isPrescribed;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price/100;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
