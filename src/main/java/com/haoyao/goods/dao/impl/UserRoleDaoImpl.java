package com.haoyao.goods.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.haoyao.goods.dao.UserRoleDao;
import com.haoyao.goods.dto.UserRoleVO;
import com.haoyao.goods.model.UserRole;

public class UserRoleDaoImpl extends BaseDaoImpl<UserRole, Long> implements UserRoleDao{
	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	public List<UserRole> findRolesByUserId(Long userId) {
		return getHibernateTemplate().find("from UserRole ur where ur.userId = ? ",userId);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findUNRolesByUserId(Long userId) {
		return getHibernateTemplate().find("from UserRole ur where ur.userId != ? ",userId);
	}

	public UserRole findUserRoleByUserIDAndRoleId(final Long userId, final Long roleId) {
		return (UserRole) getHibernateTemplate().execute(new HibernateCallback<UserRole>() {
			public UserRole doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query  = session.createQuery("from UserRole ur where ur.userId = ? and ur.roleId = ? ")
										.setLong(0, userId)
										.setLong(1, roleId);
				if(query.list() != null && query.list().size() > 0){
					return (UserRole) query.list().get(0);
				}
				return null;
			}
		});
	}


	@SuppressWarnings("unchecked")
	public List<UserRole> findUserRoleByRoleId(Long roleId) {
		return getHibernateTemplate().find("from UserRole ur where ur.roleId = ?",roleId);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findUserRoleByUserId(Long userId) {
		return getHibernateTemplate().find("from UserRole ur where ur.userId = ?",userId);
	}
	public List<UserRoleVO> findRoleByUser(int startNum, Integer pageSize,UserRoleVO vo){
		String sql = "select r.*,ur.id userRoleId,apf.`name` paltformNm from role r LEFT JOIN (select id,roleId from user_role where userId=";
		sql += vo.getUserId();		
		sql += ")ur on r.id=ur.roleId LEFT JOIN applyPlatform apf on r.platformId = apf.id where 1=1";
		List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql += " limit "+startNum+","+pageSize;
        List<UserRoleVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<UserRoleVO>(UserRoleVO.class));
		return ls;
	}
	public Integer getUserRoleCount(UserRoleVO vo){
		String sql = "select count(*) from role r LEFT JOIN (select id,roleId from user_role where userId=";
		sql += vo.getUserId();		
		sql += ")ur on r.id=ur.roleId LEFT JOIN applyPlatform apf on r.platformId = apf.id where 1=1";
		List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	//获取查询条件
	private String getWhereSql(UserRoleVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if( vo.getName() != null && !"".equals(vo.getName().trim()) ){
				wsql.append(" and (r.`name` like ? or r.`remark` like ?) ");
				paramList.add("%"+ vo.getName().trim() + "%");
				paramList.add("%"+ vo.getName().trim() + "%");
			}
			if( vo.getPlatformId() != null && vo.getPlatformId() > 0){
				wsql.append(" and r.platformId = ? ");
				paramList.add(vo.getPlatformId());
			}
			if(vo.getIsWarrant() == 1){
				wsql.append(" and (ur.id is null or ur.id = '')");
			}
			if(vo.getIsWarrant() == 2){
				wsql.append(" and (ur.id is not null and ur.id <> '')");
			}
		}
		return wsql.toString();
	}
}
