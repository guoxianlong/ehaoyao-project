package com.haoys.logisticsServer.vo;

public class TrackNumAndSource {
private String source;
private String trackNum;
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getTrackNum() {
	return trackNum;
}
public void setTrackNum(String trackNum) {
	this.trackNum = trackNum;
}
public TrackNumAndSource(String source, String trackNum) {
	super();
	this.source = source;
	this.trackNum = trackNum;
}
public TrackNumAndSource() {
	super();
}

}
