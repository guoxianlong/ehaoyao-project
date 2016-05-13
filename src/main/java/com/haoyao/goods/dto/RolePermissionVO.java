package com.haoyao.goods.dto;

import java.io.Serializable;

import com.haoyao.goods.model.IdEntity;

public class RolePermissionVO extends IdEntity implements Serializable{

	private static final long serialVersionUID = -461986968678915004L;
	
	private Long rolePerId;
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
	 * 所属平台名称
	 */
	private String platformNm;
	/**
	 * roleID
	 */
	private Long roleId;
	/**
	 * 资源ID
	 */
	private Long permissionId;
	/**
	 * 授权与否
	 */
	private int isWarrant;
	
	/**
	 * @return the isWarrant
	 */
	public int getIsWarrant() {
		return isWarrant;
	}
	/**
	 * @param isWarrant the isWarrant to set
	 */
	public void setIsWarrant(int isWarrant) {
		this.isWarrant = isWarrant;
	}
	/**
	 * @return the permissionId
	 */
	public Long getPermissionId() {
		return permissionId;
	}
	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	/**
	 * @return the rolePerId
	 */
	public Long getRolePerId() {
		return rolePerId;
	}
	/**
	 * @param rolePerId the rolePerId to set
	 */
	public void setRolePerId(Long rolePerId) {
		this.rolePerId = rolePerId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
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
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
