package com.haoyao.goods.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="permission")
public class Permission extends IdEntity implements Serializable{

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
	private String platformId;

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
