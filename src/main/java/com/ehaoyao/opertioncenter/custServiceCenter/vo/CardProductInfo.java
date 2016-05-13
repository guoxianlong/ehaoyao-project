package com.ehaoyao.opertioncenter.custServiceCenter.vo;

import java.io.Serializable;
import java.util.List;

public class CardProductInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CartInfos> cartInfos;

	/**
	 * @return the cartInfos
	 */
	public List<CartInfos> getCartInfos() {
		return cartInfos;
	}

	/**
	 * @param cartInfos the cartInfos to set
	 */
	public void setCartInfos(List<CartInfos> cartInfos) {
		this.cartInfos = cartInfos;
	}
}
