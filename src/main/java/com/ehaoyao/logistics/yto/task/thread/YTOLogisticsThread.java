package com.ehaoyao.logistics.yto.task.thread;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;

public class YTOLogisticsThread implements Runnable {

	private YTOLogisticsService ytoLogisticsService;
	private List<WayBillInfo> subThreadList;
	
	
	public YTOLogisticsService getYtoLogisticsService() {
		return ytoLogisticsService;
	}

	public void setYtoLogisticsService(YTOLogisticsService ytoLogisticsService) {
		this.ytoLogisticsService = ytoLogisticsService;
	}

	public List<WayBillInfo> getSubThreadList() {
		return subThreadList;
	}

	public void setSubThreadList(List<WayBillInfo> subThreadList) {
		this.subThreadList = subThreadList;
	}

	@Override
	public void run() {
		

	}

}
