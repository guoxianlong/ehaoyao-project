package com.ehaoyao.logistics.yto.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;

/**
 * 主要业务接口类
 */
public interface YTOLogisticsService {
	
	/**
	 * 获取圆通未完成配送运单集合
	 * @param wayBillInfoVo 
	 * @return
	 * @throws Exception
	 */
	public List<WayBillInfo> selectYTOInitWayBills(WayBillInfoVo wayBillInfoVo) throws Exception ;
	
	/**
	 * 更新圆通运单信息
	 * @param wayBillInfoList
	 * @return
	 * @throws Exception
	 */
	public Integer updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception;
	
	
}
