package com.ehaoyao.opertioncenter.returnGoods.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsDetailInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.ReturnGoodsHistory;
import com.ehaoyao.opertioncenter.returnGoods.vo.GoodsInfoVO;

/**
 * Title: ReturnGoodsService.java
 * Description: 退货审核流程
 * @author zhang
 */
public interface ReturnGoodsService {
	
	/**
	 * 查询需要退货的订单
	 */
	public PageModel<GoodsInfo> getReturnGoodsInfo(PageModel<GoodsInfo> pm,GoodsInfoVO vo);
	
	/**
	 * 查询退货订单明细
	 */
	public List<GoodsDetailInfo> getGoodsDetails(GoodsInfoVO vo);
	/**
	 * 更新退货单状态
	 */
	public void updateStatus(GoodsInfoVO vo);
	/**
	 * 审核
	 */
	public String insertReview(ReturnGoodsHistory bean);
	/**
	 * 驳回
	 */
	public void insertReject(ReturnGoodsHistory bean);

}
