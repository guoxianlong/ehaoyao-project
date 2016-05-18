package com.ehaoyao.cfy.service;

import java.util.List;

import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

public interface OrderCenterService {

	/**
	 * 初始化订单至订单中心
	 * @param subList
	 * @return
	 * @throws Exception
	 */
	public Object insertOrderCenter(List<OrderMainInfo> subList) throws Exception;
}
