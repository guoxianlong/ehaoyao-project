package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.User;

public interface UserDao extends BaseDao<User, Long>{


	List<Role> findAllRoles();

	User loadUserByUserName(String username);

	boolean findByUser(User user);

	List<User> findUser( int startNum, int pageSize, String hql);

	void deleteById(long id);

	List<User> findByName(String name,int startNum, int pageSize);

	Integer getTotal(String hqlString);

	User chenckByUserName(String userName);

	List<User> findUserByOrgId(Long orgId);

	
}
