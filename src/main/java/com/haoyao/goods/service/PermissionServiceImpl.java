package com.haoyao.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.PermissionDao;
import com.haoyao.goods.dto.PermissionVO;
import com.haoyao.goods.model.Permission;

@Service
public class PermissionServiceImpl {
	
	@Autowired
	private PermissionDao permissionDao;

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	/**
	 * 条件查询获取分页资源洗洗
	 * @param startNum
	 * @param pageSize
	 * @param hqlString
	 * @return
	 */
	public List<Permission> findPermissions(int startNum, Integer pageSize, String hqlString) {
		return permissionDao.findPermissions(startNum, pageSize,hqlString);
	}
	public List<PermissionVO> findPermissions(int startNum, Integer pageSize, PermissionVO vo) {
		return permissionDao.findPermissions(startNum, pageSize,vo);
	}

	public void del(Long id) {
		permissionDao.delete(id);
	}

	public Permission loadById(Long id) {
		return permissionDao.get(id);
	}

	public void save(Permission permission) {
		permissionDao.save(permission);
	}

	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	/**
	 * 根据资源名称模糊查询资源列表
	 * @param name
	 * @return
	 */
	public List<Permission> findByName(String name) {
		return permissionDao.findByName(name);
	}

	/**
	 * 获取所有的资源
	 * @return
	 */
	public List<Permission> findAllperms() {
		return permissionDao.findperms("");
	}

	/**
	 * 条件查询资源数量
	 * @param hqlString
	 * @return
	 */
	public Integer getPermCount(String hqlString) {
		return permissionDao.findperms(hqlString).size();
	}
	/**
	 * 条件查询资源数量
	 * @param hqlString
	 * @return
	 */
	public Integer getPermCount(PermissionVO vo) {
		return permissionDao.findperms(vo);
	}


	/**
	 * 根据资源名称获取资源信息
	 * @param name
	 * @return
	 */
	public Permission loadByName(String name) {
		return permissionDao.loadByName(name);
	}
	
	/**
	 * 根据url后去资源信息
	 * @param url
	 * @return
	 */
	public Permission loadByUrl( String url ){
		return permissionDao.loadByUrl(url);
	}

}
