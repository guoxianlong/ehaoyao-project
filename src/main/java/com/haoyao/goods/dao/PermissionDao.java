package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.dto.PermissionVO;
import com.haoyao.goods.model.Permission;

public interface PermissionDao extends BaseDao<Permission, Long>{

	List<Permission> findByName( String name );

	List<Permission> findperms(String hqlString);
	int findperms(PermissionVO vo);

	Permission loadByName(String name);

	Permission loadByUrl(String url);

	List<Permission> findPermissions(int startNum, Integer pageSize,
			String hqlString);
	List<PermissionVO> findPermissions(int startNum, Integer pageSize,PermissionVO vo);
}
