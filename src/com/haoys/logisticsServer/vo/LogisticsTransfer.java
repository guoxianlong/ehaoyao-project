package com.haoys.logisticsServer.vo;

import java.io.Serializable;
import com.haoys.logisticsServer.entity.LogisticsDetail;

public class LogisticsTransfer implements Serializable {
	private static final long serialVersionUID = 6466243954437100581L;
	private Integer struts;
	private String trackNumber;
	private String source;
	private LogisticsDetail[] logisticsDetail;

	public Integer getStruts() {
		return struts;
	}

	public void setStruts(Integer struts) {
		this.struts = struts;
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

	public LogisticsDetail[] getLogisticsDetail() {
		return logisticsDetail;
	}

	public void setLogisticsDetail(LogisticsDetail[] logisticsDetail) {
		this.logisticsDetail = logisticsDetail;
	}

	

}
