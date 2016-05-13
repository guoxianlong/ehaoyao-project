package com.haoyao.goods.dto;

import java.io.Serializable;

import com.haoyao.goods.model.IdEntity;

public class UserRoleVO extends IdEntity implements Serializable{

	private static final long serialVersionUID = -461986968678915004L;
	
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
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户角色ID
	 */
	private Long userRoleId;
	/**
	 * 是否授权
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
	 * @return the userRoleId
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}
	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
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
}
