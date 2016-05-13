package com.haoyao.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.RoleDao;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.dao.UserRoleDao;
import com.haoyao.goods.dto.UserRoleVO;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.User;
import com.haoyao.goods.model.UserRole;

@Service
public class UserRoleServiceImpl {
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	/**
	 * 根据用户名查找所有的角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> findRolesByUserId(Long userId) {
		return userRoleDao.findRolesByUserId(userId);
	}

	/**
	 * 根据用户名获取所有未拥有的角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> findUNRolesByUserId(Long userId) {
		return userRoleDao.findUNRolesByUserId(userId);
	}


	public void save(UserRole userRole) {
		userRoleDao.save(userRole);
	}


	public void delete(UserRole userRole) {
		userRoleDao.delete(userRole);
	}

	/**
	 * 根据用户名和角色名获取用户角色信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole findUserRoleByUserIDAndRoleId(Long userId, Long roleId) {
		return userRoleDao.findUserRoleByUserIDAndRoleId(userId,roleId);
	}

	/**
	 * 根据角色Id获取所有关联用户
	 * @param roleId
	 * @return
	 */
	public List<UserRole> findUserRoleByRoleId(Long roleId) {
		return userRoleDao.findUserRoleByRoleId(roleId);
	}
	
	/**
	 * 根据用户名获取用户所有的角色
	 */
	public List<Role> findUserRolesByUsername(String username) {
		return roleDao.findRolesByUserName(username, "1");
	}
	public List<UserRoleVO> findRoleByUser(int startNum, Integer pageSize,UserRoleVO vo) {
		return userRoleDao.findRoleByUser(startNum, pageSize, vo);
	}
	public Integer getUserRoleCount(UserRoleVO vo){
		return userRoleDao.getUserRoleCount(vo);
	}
}
