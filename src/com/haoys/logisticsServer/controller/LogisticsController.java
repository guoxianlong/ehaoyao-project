package com.haoys.logisticsServer.controller;

import com.haoys.logisticsServer.service.LogisticsServiceImpl;
import com.haoys.logisticsServer.service.spi.LogisticsService;
import com.haoys.logisticsServer.vo.LogisticsTransfer;


public class LogisticsController {
	private LogisticsService logService = new LogisticsServiceImpl();
	private LogisticsTransfer logisticsTran;
	private String trackNumber;
	private String source;
	
	public String queryDetail() {
		this.logisticsTran = this.logService.queryLogisticsDetailByMemOrDB(trackNumber, source);
		return "queryDetail";
	}
	
	
	
	public LogisticsService getLogService() {
		return logService;
	}
	public void setLogService(LogisticsService logService) {
		this.logService = logService;
	}
	public LogisticsTransfer getLogisticsTran() {
		return logisticsTran;
	}
	public void setLogisticsTran(LogisticsTransfer logisticsTran) {
		this.logisticsTran = logisticsTran;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
}
