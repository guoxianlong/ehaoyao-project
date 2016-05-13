package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.dto.RolePermissionVO;
import com.haoyao.goods.model.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission, Long>{

	List<RolePermission> findPermsByRoleId(Long roleId);
	List<RolePermissionVO> findPermsByRole(int startNum, Integer pageSize,RolePermissionVO vo);

	RolePermission findRolePermByRoleIDAndPermId(Long roleId, Long permId);

	List<RolePermission> findRolePermByPermId(Long permid);
	
	Integer getRoleCount(RolePermissionVO vo);

}
