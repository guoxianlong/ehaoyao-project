package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.dto.UserRoleVO;
import com.haoyao.goods.model.UserRole;

public interface UserRoleDao extends BaseDao<UserRole, Long>{

	List<UserRole> findRolesByUserId(Long userId);

	List<UserRole> findUNRolesByUserId(Long userId);

	UserRole findUserRoleByUserIDAndRoleId(Long userId, Long roleId);

	List<UserRole> findUserRoleByRoleId(Long roleId);

	List<UserRole> findUserRoleByUserId(Long userId);
    List<UserRoleVO> findRoleByUser(int startNum, Integer pageSize,UserRoleVO vo);
    Integer getUserRoleCount(UserRoleVO vo);
    
    
    
}
