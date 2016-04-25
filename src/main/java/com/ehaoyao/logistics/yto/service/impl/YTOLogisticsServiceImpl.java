package com.ehaoyao.logistics.yto.service.impl;

import java.util.List;
import java.util.ResourceBundle;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;

public class YTOLogisticsServiceImpl implements YTOLogisticsService {
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	
	@Override
	public List<WayBillInfo> selectYTOInitWayBills() throws Exception {
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setWaybillSource("yuantong");
		wayBillInfoVo.set
		return null;
	}

	@Override
	public int updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception {
		
		return 0;
	}

}
