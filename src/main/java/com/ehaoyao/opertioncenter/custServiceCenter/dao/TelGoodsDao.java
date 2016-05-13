package com.ehaoyao.opertioncenter.custServiceCenter.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
import com.haoyao.goods.dao.BaseDao;

public interface TelGoodsDao extends BaseDao<Product, String> {

	/**
	 * 
	 * 查询电销商品
	 * @param firstResult
	 * @param pageSize
	 * @param pvo
	 * @return 
	 */
	public List<Product> queryProductList(int firstResult, Integer pageSize, ProductVO pvo);

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
}
