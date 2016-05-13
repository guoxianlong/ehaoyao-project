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

import com.haoyao.goods.dao.RolePermissionDao;
import com.haoyao.goods.dto.RolePermissionVO;
import com.haoyao.goods.model.RolePermission;

public class RolePermissionDaoImpl extends BaseDaoImpl<RolePermission, Long> implements RolePermissionDao{

	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<RolePermission> findPermsByRoleId(Long roleId) {
		return getHibernateTemplate().find(" from RolePermission rp where rp.roleId = ? " ,roleId);
	}

	public RolePermission findRolePermByRoleIDAndPermId(final Long roleId, final Long permId) {
		return (RolePermission) getHibernateTemplate().execute(new HibernateCallback<RolePermission>() {
			public RolePermission doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query  = session.createQuery("from RolePermission rp where rp.roleId = ? and rp.permissionId = ? ")
										.setLong(0, roleId)
										.setLong(1, permId);
				if(query.list() != null && query.list().size() > 0){
					return (RolePermission) query.list().get(0);
				}
				return null;
			}
		});
	}


	@SuppressWarnings("unchecked")
	public List<RolePermission> findRolePermByPermId(Long permid) {
		return getHibernateTemplate().find("from RolePermission rp where rp.permissionId = ?",permid);
	}

	@Override
	public List<RolePermissionVO> findPermsByRole(int startNum, Integer pageSize,RolePermissionVO vo) {
		String sql = "select p.*,rp.id rolePerId,apf.`name` platformNm from permission p LEFT JOIN (select id,permissionId from role_permission where roleId=";
		sql += vo.getRoleId();		
		sql += " ) rp on p.id=rp.permissionId LEFT JOIN applyPlatform apf on p.platformId = apf.id where 1=1";
		List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql += " order by p.name";
        sql += " limit "+startNum+","+pageSize;
        List<RolePermissionVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<RolePermissionVO>(RolePermissionVO.class));
		return ls;
	}
	
	public Integer getRoleCount(RolePermissionVO vo){
		String sql = "select count(*)  from permission p LEFT JOIN (select id,permissionId from role_permission where roleId=";
		sql += vo.getRoleId();		
		sql += " ) rp on p.id=rp.permissionId LEFT JOIN applyPlatform apf on p.platformId = apf.id where 1=1";
		List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	
	//获取查询条件
	private String getWhereSql(RolePermissionVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if( vo.getPlatformId() != null && vo.getPlatformId() > 0){
				wsql.append(" and p.platformId = ? ");
				paramList.add(vo.getPlatformId());
			}
			if( vo.getName() != null && !"".equals(vo.getName().trim())){
				wsql.append(" and p.name like ? ");
				paramList.add("%"+ vo.getName().trim() + "%");
			}
			if( vo.getUrl() != null && !"".equals(vo.getUrl().trim()) ){
				wsql.append(" and p.url like ? ");
				paramList.add("%"+ vo.getUrl().trim() + "%");
			}
			if(vo.getIsWarrant() == 1){
				wsql.append(" and (rp.id is null or  rp.id = '')");
			}
			if(vo.getIsWarrant() == 2){
				wsql.append(" and (rp.id is not null and rp.id <> '')");
			}
		}
		return wsql.toString();
	}
}
