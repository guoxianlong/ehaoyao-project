package com.haoyao.goods.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="role_permission")
public class RolePermission extends IdEntity {

	private Long roleId;
	
	private Long permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	

}
