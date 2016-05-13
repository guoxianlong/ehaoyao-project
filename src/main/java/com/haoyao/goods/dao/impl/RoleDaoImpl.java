package com.haoyao.goods.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.haoyao.goods.dao.RoleDao;
import com.haoyao.goods.dto.RoleVO;
import com.haoyao.goods.model.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {
	
	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<Role> findByName(String name) {
		return getHibernateTemplate().find(
				"from Role where name like '%" + name + "%' ");
	}

	public Integer getRoleCount(String hqlString) {
		return getHibernateTemplate().find("from Role where 1 = 1 " + hqlString).size();
	}

	public Role loadByName(String name) {
		@SuppressWarnings("unchecked")
		List<Role> roles = getHibernateTemplate().find("from Role r where r.name = ? ",name);
		if( roles != null && roles.size() > 0 ){
			return roles.get(0);
		}
		return null;
	}

	public List<Role> findRoles(int startNum, Integer pageSize, String hqlString) {
		return queryByHQL("from Role where 1 = 1 " + hqlString , null, startNum, pageSize);
	}
	
	public Integer getRoleCount(RoleVO vo) {
		String sql = "select count(*) from role AS a LEFT JOIN applyPlatform u on a.platformId = u.id where 1=1";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	
	public List<RoleVO> findRoles(int startNum, Integer pageSize,
			RoleVO vo) {
		String sql = "select a.*,u.`name` paltformNm from role AS a LEFT JOIN applyPlatform u on a.platformId = u.id where 1=1";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql += " limit "+startNum+","+pageSize;
        List<RoleVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<RoleVO>(RoleVO.class));
		return ls;
	}
	
	//获取查询条件
	private String getWhereSql(RoleVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if( vo.getName() != null && !"".equals(vo.getName().trim()) ){
				wsql.append(" and (a.`name` like ? or a.`remark` like ?) ");
				paramList.add("%"+ vo.getName().trim() + "%");
				paramList.add("%"+ vo.getName().trim() + "%");
			}
			if( vo.getPlatformId() != null && vo.getPlatformId() > 0){
				wsql.append(" and a.platformId = ? ");
				paramList.add(vo.getPlatformId());
			}
			if( vo.getId() != null && vo.getId() > 0){
				wsql.append(" and a.id = ?");
				paramList.add(vo.getId());
			}
		}
		return wsql.toString();
	}



	@Override
	public List<Role> findRolesByUserName(String userName,String platformId) {
		String sql = "select r.* from role r,user u, user_role ur where ur.roleId=r.id and ur.userId=u.id and u.userName='"+userName+"' and r.platformId='"+platformId+"'";
        List<Role> ls = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
		return ls;
	}
}
