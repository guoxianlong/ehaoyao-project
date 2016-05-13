package com.haoyao.goods.model;

public class SyncParam {
	 //String restUri = url+"?dataType={dataType}&actionType={actionType}&data={data}";
	
	private String dataType;
	private String actionType;
	private Object object;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
}
