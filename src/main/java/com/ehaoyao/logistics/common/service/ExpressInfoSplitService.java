package com.ehaoyao.logistics.common.service;

import java.util.List;

import com.ehaoyao.logistics.common.vo.OrderExpressVo;

public interface ExpressInfoSplitService {

	/**
	 * 从订单中心根据条件获取已配送的拆单的订单/运单信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
	List<OrderExpressVo> selectExpressInfoSplitList() throws Exception;
}
