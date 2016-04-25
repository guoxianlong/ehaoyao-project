package com.ehaoyao.logistics.yto.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;

public class YTOLogisticsServiceImpl implements YTOLogisticsService {
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	
	@Autowired
	private WayBillInfoMapper waybillInfoMapper;
	
	@Override
	public List<WayBillInfo> selectYTOInitWayBills() throws Exception {
		String waybillsource = ytoConfig.getString("waybillsource");
		int orderIntervalTime = Integer.parseInt(ytoConfig.getString("normal_updLogistics_minute"));
		Date startTime=DateUtil.getPreMinute(orderIntervalTime);//当前时间向前推迟xxx分钟
		Date endTime=new Date();
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1 运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add("s00");
		waybillStatusList.add("s01");
		waybillStatusList.add("s02");
		
		/*2、将查询条件封装*/
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setWaybillSource(waybillsource);
		wayBillInfoVo.setCreateTimeStart(startTime);
		wayBillInfoVo.setCreateTimeEnd(endTime);
		wayBillInfoVo.setWaybillStatusList(waybillStatusList);
		
		/*3、调用mapper获取运单号集合*/
		List<WayBillInfo> wayBillInfoList = waybillInfoMapper.selectWayBillInfoList(wayBillInfoVo);
		
		return wayBillInfoList;
	}

	@Override
	public int updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception {
		
		return 0;
	}

}
