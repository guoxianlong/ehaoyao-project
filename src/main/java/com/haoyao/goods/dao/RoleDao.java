package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.dto.RoleVO;
import com.haoyao.goods.model.Role;

public interface RoleDao extends BaseDao<Role,Long>{

	List<Role> findByName(String name);

	Integer getRoleCount(String hqlString);
	Integer getRoleCount(RoleVO vo);

	Role loadByName(String name);

	List<Role> findRoles(int startNum, Integer pageSize, String hqlString);
	
	List<RoleVO> findRoles(int startNum, Integer pageSize,RoleVO vo);
	
	
	List<Role> findRolesByUserName(String userName,String platformId);
	
}
