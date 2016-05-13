package com.haoyao.goods.dto;

import java.io.Serializable;

import com.haoyao.goods.model.IdEntity;

public class RoleVO extends IdEntity implements Serializable{

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
	 * 所属应用平台ID
	 */
	private Long platformId;
	/**
	 * 所属应用平台名称
	 */
	private String paltformNm;
	
	/**
	 * @return the paltformNm
	 */
	public String getPaltformNm() {
		return paltformNm;
	}

	/**
	 * @param paltformNm the paltformNm to set
	 */
	public void setPaltformNm(String paltformNm) {
		this.paltformNm = paltformNm;
	}

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
	public Long getPlatformId() {
		return platformId;
	}
	/**
	 * @param platformId the platformId to set
	 */
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	
}
