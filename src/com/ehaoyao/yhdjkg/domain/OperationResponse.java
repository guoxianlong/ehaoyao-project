package com.ehaoyao.yhdjkg.domain;

import java.util.List;

public class OperationResponse {
	private String ifSuc;
	private String code;
	private String msg;
	private List<ParamsData> data;
	private Integer totalPages;//总页数
	
	
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public String getIfSuc() {
		return ifSuc;
	}
	public void setIfSuc(String ifSuc) {
		this.ifSuc = ifSuc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<ParamsData> getData() {
		return data;
	}
	public void setData(List<ParamsData> data) {
		this.data = data;
	}
	
	

}
