package com.ehaoyao.yhdjkg.service;

import com.ehaoyao.yhdjkg.domain.ParamsData;

/**
 * @author wls
 *
 */
public interface IOrderCenterService {
	
	/**
	 * 保存单个订单信息
	 * @return
	 */
	public void doSaveOneCFYOrder(String channel, String operation_secretKey,
			String startTime, String endTime, int pageSize, int pageNo,
			ParamsData paramsData2);

	public Boolean findOrderByOrderId(String tid, String shopAlias);
}
