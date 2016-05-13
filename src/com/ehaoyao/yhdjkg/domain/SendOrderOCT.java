package com.ehaoyao.yhdjkg.domain;

import java.io.Serializable;

public class SendOrderOCT implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */   
	private static final long serialVersionUID = -5226838466369852162L;
	
	private String sign;
	private String channel;
	private String paramsData;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getParamsData() {
		return paramsData;
	}
	public void setParamsData(String paramsData) {
		this.paramsData = paramsData;
	}

}
