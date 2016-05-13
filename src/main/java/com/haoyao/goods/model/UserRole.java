package com.haoyao.goods.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class UserRole extends IdEntity{

	private Long userId;
	
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	

	
	
}
