package com.haoyao.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.User;

@Service
public class UserServiceImpl{

	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User loadUserByUserName(String username) {
		return userDao.loadUserByUserName(username);
	}

	/**
	 * 获取所有的资源
	 * @return
	 */
	public List<Role> findAllRoles() {
		return userDao.findAllRoles();
	}

	/**
	 * 根据账号密码获取user
	 * @param user
	 * @return
	 */
	public boolean findByUser(User user) {
		return userDao.findByUser(user);
	}

	/**
	 * 条件获取用户集合
	 * @param startNum
	 * @param pageSize
	 * @param hql
	 * @return
	 */
	public List<User> findUser( int startNum, int pageSize,String hql){
		return userDao.findUser(startNum, pageSize,hql);
	}

	public void delById(long id) {
		userDao.deleteById(id);
	}

	public void save(User user) {
		userDao.save(user);
	}

	public User loadUserById(Long id) {
		return userDao.get(id);
	}

	public void delete(User user) {
		userDao.delete(user);
	}

	public void update(User user) {
		userDao.update(user);
	}

	/**
	 * 条件获取用户总数
	 * @param hqlString
	 * @return
	 */
	public Integer findUserCount(String hqlString) {
		return userDao.getTotal(hqlString);
	}

	/**
	 * 根据用户名称获取用户
	 * @param userName
	 * @return
	 */
	public User checkByUserName(String userName) {
		return userDao.chenckByUserName(userName);
	}

	/**
	 * 根据机构id获取所有关联的用户
	 * @param orgId
	 * @return
	 */
	public List<User> findUserByOrgId(Long orgId) {
		return userDao.findUserByOrgId(orgId);
	}
}
