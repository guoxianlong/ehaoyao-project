package com.haoyao.goods.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user")
public class User extends IdEntity implements Serializable{

	private static final long serialVersionUID = -2067022014590584536L;

	public static final Integer LOCK_STATUS_CODE = 0;
	
	public static final String LOCK_STATUS_TEXT = "启用";
	
	public static final Integer UNLOCK_STATUS_CODE = 1;
	
	public static final String UNLOCK_STATUS_TEXT = "锁定";
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 用户状态
	 */
	private Integer lockStatus;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 原密码
	 */
	private String oldPswd;
	
	/**
	 * 机构ID
	 */
	private Long orgId;
	
	/** 
	 * 姓名
	 */
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldPswd() {
		return oldPswd;
	}

	public void setOldPswd(String oldPswd) {
		this.oldPswd = oldPswd;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getLockStatusText() {
		return lockStatus.equals(User.LOCK_STATUS_CODE) ? User.LOCK_STATUS_TEXT : User.UNLOCK_STATUS_TEXT;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	
}
