package com.haoyao.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.RolePermissionDao;
import com.haoyao.goods.dto.RolePermissionVO;
import com.haoyao.goods.model.Permission;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.RolePermission;

@Service
public class RolePermissionService {
	@Autowired
	private RolePermissionDao rolePermissionDao;
	@Autowired
	private RoleServiceImpl roleService;
	@Autowired
	private PermissionServiceImpl permissionService;
	
	public void setRolePermissionDao(RolePermissionDao rolePermissionDao) {
		this.rolePermissionDao = rolePermissionDao;
	}
	public void setRoleService(RoleServiceImpl roleService) {
		this.roleService = roleService;
	}
	public void setPermissionService(PermissionServiceImpl permissionService) {
		this.permissionService = permissionService;
	}

	/**
	 * 根据角色Id获取关联的资源集合
	 * @param roleId
	 * @return
	 */
	public List<RolePermission> findPermsByRoleId(Long roleId) {
		return rolePermissionDao.findPermsByRoleId(roleId);
	}
	
	public List<RolePermissionVO> findPermsByRole(int startNum, Integer pageSize,RolePermissionVO vo) {
		return rolePermissionDao.findPermsByRole(startNum, pageSize, vo);
	}


	public void save(RolePermission rolePermission) {
		rolePermissionDao.save(rolePermission);
	}


	/**
	 * 根据角色id和资源id获取bean
	 * @param roleId
	 * @param permId
	 * @return
	 */
	public RolePermission findRolePermByRoleIDAndPermId(Long roleId, Long permId) {
		return rolePermissionDao.findRolePermByRoleIDAndPermId(roleId,permId);
	}


	public void delete(RolePermission rolePermission) {
		rolePermissionDao.delete(rolePermission);
	}

	
	/**
	 * 根据资源id获取和该资源关联的角色信息
	 * @param permid
	 * @return
	 */
	public List<RolePermission> findRolePermByPermId(Long permid) {
		return rolePermissionDao.findRolePermByPermId(permid);
	}

	/**
	 * 根据角色名寻找对应的资源
	 */
	public List<Permission> findResourcesByRoleName(final String name) {
		List<Permission> list = new ArrayList<Permission>();
		List<Role> roles = roleService.findByName(name);
		if( roles != null && roles.size() > 0 ){
			Long roleId = roles.get(0).getId();
			List<RolePermission> rolePermissionList = rolePermissionDao.findPermsByRoleId(roleId);
			if( rolePermissionList != null && rolePermissionList.size() > 0 ){
				for( RolePermission rp : rolePermissionList ){
					list.add(permissionService.loadById(rp.getPermissionId()));
				}
			}
			
		}
		return list;
	}
	public Integer getRoleCount(RolePermissionVO vo){
		return rolePermissionDao.getRoleCount(vo);
	}
}
