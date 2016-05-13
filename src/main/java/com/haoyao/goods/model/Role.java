package com.haoyao.goods.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role extends IdEntity implements Serializable{

	private static final long serialVersionUID = -4525556006674096971L;
	
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色备注
	 */
	private String remark;
	/**
	 * 所属系统
	 */
	private String platformId;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the platformId
	 */
	public String getPlatformId() {
		return platformId;
	}

	/**
	 * @param platformId the platformId to set
	 */
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
}
