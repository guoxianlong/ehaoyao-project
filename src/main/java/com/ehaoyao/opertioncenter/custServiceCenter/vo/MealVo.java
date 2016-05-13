/**
 * 
 */
package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;

/**
 * @author kxr
 * 订单商品
 */
public class MealVo  implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 订单操作类型（修改，新增）
	 */
	private String type;
	/**
	 * 商品套餐id
	 */
	private String mealId;
	
	/**
	 * 产品id
	 */
	private String productId;
	
	/**
	 * 套餐名称
	 */
	private String mealName;
	
	/**
	 * 商品数量
	 */
	private String buyCount;
	/**
	 * 商品库存
	 */
	private String stockCount;
	/**
	 * 商品价格
	 */
	private String price;
	
	/**
	 * 商品总价
	 */
	private String amount;
	
	/**
	 * 主商品SKU
	 */
	private String mainSku;
	
	/**
	 * 套餐规格
	 */
	private String mealNormName;
	
	/**
	 * 1: 非处方药(甲类)\r\n2: 非处方药(乙类)\r\n3: 处方药\r\n4: 保健食品\r\n
	 */
	private String prescriptionType;
	
	/**
	 * 第几页
	 */
	private String pn;
	
	/**
	 * 每页显示的条数
	 */
	private String ps;

	/**
	 * @return the orderSn
	 */
	public String getOrderSn() {
		return orderSn;
	}

	/**
	 * @param orderSn the orderSn to set
	 */
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	/**
	 * @return the stockCount
	 */
	public String getStockCount() {
		return stockCount;
	}

	/**
	 * @param stockCount the stockCount to set
	 */
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getMealId() {
		return mealId;
	}

	public void setMealId(String mealId) {
		this.mealId = mealId;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(String buyCount) {
		this.buyCount = buyCount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMainSku() {
		return mainSku;
	}

	public void setMainSku(String mainSku) {
		this.mainSku = mainSku;
	}

	public String getMealNormName() {
		return mealNormName;
	}

	public void setMealNormName(String mealNormName) {
		this.mealNormName = mealNormName;
	}

	public String getPrescriptionType() {
		return prescriptionType;
	}

	public void setPrescriptionType(String prescriptionType) {
		this.prescriptionType = prescriptionType;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

}
