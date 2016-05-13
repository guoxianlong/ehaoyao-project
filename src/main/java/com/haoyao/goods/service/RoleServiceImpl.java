package com.haoyao.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.RoleDao;
import com.haoyao.goods.dto.RoleVO;
import com.haoyao.goods.model.Role;

@Service
public class RoleServiceImpl {

	@Autowired
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 根据条件分页查询角色信息
	 * @param startNum
	 * @param pageSize
	 * @param hqlString
	 * @return
	 */
	public List<Role> findRoles(int startNum, Integer pageSize, String hqlString) {
		return roleDao.findRoles(startNum, pageSize,hqlString);
	}
	
	public List<RoleVO> findRoles(int startNum, Integer pageSize,RoleVO vo) {
		return roleDao.findRoles(startNum, pageSize,vo);
	}

	public Role loadById(Long id) {
		return roleDao.get(id);
	}

	public void del(Role role) {
		roleDao.delete(role);
	}

	public void del(Long id) {
		roleDao.delete(id);
	}

	public void save(Role role) {
		roleDao.save(role);
	}

	public void update(Role role) {
		roleDao.update(role);
	}

	/**
	 * 根据角色名称模糊查询
	 * @param name
	 * @return
	 */
	public List<Role> findByName(String name) {
		return roleDao.findByName(name);
	}

	/**
	 * 条件获取角色总数
	 * @param hqlString
	 * @return
	 */
	public Integer getRoleCount(String hqlString) {
		return roleDao.getRoleCount(hqlString);
	}
	
	/**
	 * 条件获取角色总数
	 * @param hqlString
	 * @return
	 */
	public Integer getRoleCount(RoleVO vo) {
		return roleDao.getRoleCount(vo);
	}

	/**
	 * 根据角色名称获取角色
	 * @param name
	 * @return
	 */
	public Role loadByName(String name) {
		return roleDao.loadByName(name);
	}
}
