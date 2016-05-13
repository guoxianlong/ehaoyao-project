package com.haoyao.goods.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.User;

public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	/**
	 * 获取所有的角色
	 */
	@SuppressWarnings("unchecked")
	public List<Role> findAllRoles() {
		return getHibernateTemplate().find("from Role where platformId ='1'");
	}


	/**
	 * 根据用户名获取用户
	 */
	@SuppressWarnings("unchecked")
	public User loadUserByUserName(String username) {
		List<User> userList = getHibernateTemplate().find(
				"from User where userName = ? ", username);
		if (userList == null || userList.size() == 0) {
			return new User();
		}
		return userList.get(0);
	}

	public boolean findByUser(final User user) {
		User resultUser = getHibernateTemplate().execute(
				new HibernateCallback<User>() {
					public User doInHibernate(Session session)
							throws HibernateException, SQLException {
						return (User) session
								.createQuery(
										"from User where userName = ? and password = ?")
								.setString(1, user.getUserName())
								.setString(2, user.getPassWord());
					}
				});
		return resultUser != null;
	}

	public List<User> findUser(int startNum, int pageSize ,String hql) {
		return queryByHQL("from User where 1 = 1 " + hql + " order by createTime desc", null, startNum, pageSize);
	}

	public void deleteById(long id) {
		User user = getHibernateTemplate().get(User.class, id);
		getHibernateTemplate().delete(user);
	}

	public List<User> findByName(String name,int startNum, int pageSize) {
		return queryByHQL(
				"from User u where u.userName like '%"+name+"%' order by u.createTime desc",null,startNum,pageSize);
	}


	public Integer getTotal(String hqlString) {
		return getHibernateTemplate().find("from User where 1 = 1 " + hqlString).size();
	}

	public User chenckByUserName(String userName) {
		@SuppressWarnings("unchecked")
		List<User> userList =  getHibernateTemplate().find("from User u where u.userName = ? ",userName);
		if( userList != null && userList.size() > 0 ){
			return userList.get(0);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<User> findUserByOrgId(Long orgId) {
		return getHibernateTemplate().find("from User u where u.orgId = ?" ,orgId);
	}

}
