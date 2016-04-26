package com.ehaoyao.logistics.yto.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;

public interface YTOLogisticsService {
	
	/**
	 * 获取圆通初始运单集合
	 * @return
	 * @throws Exception
	 */
	public List<WayBillInfo> selectYTOInitWayBills() throws Exception ;
	
	/**
	 * 更新圆通运单信息
	 * @param wayBillInfoList
	 * @return
	 * @throws Exception
	 */
	public Integer updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception;
	
	
}
