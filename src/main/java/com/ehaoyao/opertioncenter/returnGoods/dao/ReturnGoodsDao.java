package com.ehaoyao.opertioncenter.returnGoods.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsDetailInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.ReturnGoodsHistory;
import com.ehaoyao.opertioncenter.returnGoods.vo.GoodsInfoVO;

public interface ReturnGoodsDao {
	/**
	 * 查询需要退货的订单
	 */
	public List<GoodsInfo> getReturnGoodsInfo(PageModel<GoodsInfo> pm,GoodsInfoVO vo);
	
	/**
	 * 查询退货订单明细
	 */
	public List<GoodsDetailInfo> getGoodsDetails(GoodsInfoVO vo);

	/**
	 * 查询需要退货的订单数量
	 */
	public int getRetuanOrderCount(GoodsInfoVO vo);
	
	/**
	 * 更新退货单状态(订单中心)
	 */
	public void updateStatus(GoodsInfoVO vo);
	/**
	 * 保留退货信息(历史数据)
	 */
	public void insertHistory(GoodsInfoVO vo);
	/**
	 * 更新退货状态(运营中心历史数据)
	 */
	public void updateStatusOperation(GoodsInfoVO vo);
	/**
	 * 记录操作历史
	 */
	public void insertHistory(ReturnGoodsHistory bean);
}
