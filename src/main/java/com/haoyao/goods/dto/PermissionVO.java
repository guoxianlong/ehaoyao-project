package com.haoyao.goods.dto;

import java.io.Serializable;

import com.haoyao.goods.model.IdEntity;

public class PermissionVO extends IdEntity implements Serializable{

	private static final long serialVersionUID = -461986968678915004L;
	
	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * url
	 */
	private String url;
	/**
	 * 所属应用平台ID
	 */
	private Long platformId;
	/**
	 * 所属应用平台名称
	 */
	private String platformNm;
	
	/**
	 * @return the platformNm
	 */
	public String getPlatformNm() {
		return platformNm;
	}

	/**
	 * @param platformNm the platformNm to set
	 */
	public void setPlatformNm(String platformNm) {
		this.platformNm = platformNm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
