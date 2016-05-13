package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;

public class CartInfos implements Serializable {
	private static final long serialVersionUID = 6202355187434291406L;
	
	private String assignOtherFaId;//指定更改的其它优惠ID，用户如果没有更改优惠，则可传-1或者不传;如果选中的是不参加优惠，则传0.多个ID以逗号相隔。
	private String assignSingleFaId;//指定更改的单品优惠ID，用户如果没有更改优惠，则可传-1或者不传;如果选中的是不参加优惠，则传0.多个ID以逗号相隔
	private String mealId;//商品套餐ID

    private String quantity;//商品数目

    private String selected; //是否选中，0为不选中，1为选中。

    private String skuId;//商品skuID

	/**
	 * @return the assignOtherFaId
	 */
	public String getAssignOtherFaId() {
		return assignOtherFaId;
	}

	/**
	 * @param assignOtherFaId the assignOtherFaId to set
	 */
	public void setAssignOtherFaId(String assignOtherFaId) {
		this.assignOtherFaId = assignOtherFaId;
	}

	/**
	 * @return the assignSingleFaId
	 */
	public String getAssignSingleFaId() {
		return assignSingleFaId;
	}

	/**
	 * @param assignSingleFaId the assignSingleFaId to set
	 */
	public void setAssignSingleFaId(String assignSingleFaId) {
		this.assignSingleFaId = assignSingleFaId;
	}

	/**
	 * @return the mealId
	 */
	public String getMealId() {
		return mealId;
	}

	/**
	 * @param mealId the mealId to set
	 */
	public void setMealId(String mealId) {
		this.mealId = mealId;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the selected
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
