package com.haoyao.goods.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.haoyao.goods.dao.PermissionDao;
import com.haoyao.goods.dto.PermissionVO;
import com.haoyao.goods.model.Permission;

public class PermissionDaoImpl extends BaseDaoImpl<Permission, Long> implements
		PermissionDao {
	
	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	
	@SuppressWarnings("unchecked")
	public List<Permission> findByName(String name) {
		return getHibernateTemplate().find(
				" from Permission where name like '%" + name + "%' ");
	}

	@SuppressWarnings("unchecked")
	public List<Permission> findperms(String hqlString) {
		return getHibernateTemplate().find("from Permission where 1 = 1 " + hqlString );
	}
	
	public int findperms(PermissionVO vo) {
		String sql = "select count(*) from permission AS a LEFT JOIN applyPlatform u on a.platformId = u.id where 1=1";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}

	@SuppressWarnings("unchecked")
	public Permission loadByName(String name) {
		List<Permission> list = getHibernateTemplate().find("from Permission p where p.name = ? ",name);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Permission loadByUrl(String url) {
		List<Permission> list = getHibernateTemplate().find("from Permission p where p.url = ? ",url);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	public List<Permission> findPermissions(int startNum, Integer pageSize,
			String hqlString) {
		return queryByHQL("from Permission where 1 = 1 " + hqlString , null, startNum, pageSize);
	}
	
	public List<PermissionVO> findPermissions(int startNum, Integer pageSize,
			PermissionVO vo) {
		String sql = "select a.*,u.`name` platformNm from permission AS a LEFT JOIN applyPlatform u on a.platformId = u.id where 1=1";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql +=" order by platformNm desc,a.name";
        sql += " limit "+startNum+","+pageSize;
        List<PermissionVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<PermissionVO>(PermissionVO.class));
		return ls;
	}
	//获取查询条件
	private String getWhereSql(PermissionVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if( vo.getName() != null && !"".equals(vo.getName().trim()) ){
				wsql.append(" and a.`name` like ? ");
				paramList.add("%"+ vo.getName().trim() + "%");
			}
			if( vo.getUrl() != null && !"".equals(vo.getUrl().trim()) ){
				wsql.append(" and a.url like ? ");
				paramList.add("%"+ vo.getUrl().trim() + "%");
			}
			if( vo.getPlatformId() != null && vo.getPlatformId() > 0){
				wsql.append(" and a.platformId = ? ");
				paramList.add(vo.getPlatformId());
			}
		}
		return wsql.toString();
	}
}
