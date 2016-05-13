package com.ehaoyao.opertioncenter.custServiceCenter.service;

import java.util.List;

import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;

/**
 * 
 * Title: TelGoodsService.java
 * 
 * Description: 电销商品Service
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月15日 下午2:38:34
 */
public interface TelGoodsService {
	
	/**
	 * 
	 * 查询电销商品
	 * @param firstResult
	 * @param pageSize
	 * @param pvo
	 * @return 
	 */
	public List<Product> queryProductList(int firstResult, int pageSize, ProductVO pvo);
	
	/**
	 * 
	 * 查询电销商品总数
	 * @param firstResult
	 * @param pageSize
	 * @param pvo
	 * @return
	 */
	public int queryProductCount(ProductVO pvo);

	/**
	 * 
	 * 批量删除商品
	 * @param idList
	 */
	public void delGoods(List<String> idList);

	/**
	 * 设置主推状态
	 * @param secondIDList
	 * @param status
	 */
	public void updateStatus(List<String> secondIDList, String status);

	/**
	 * 保存商品信息
	 */
	public void saveOrUpdateProduct(Product p);
}
