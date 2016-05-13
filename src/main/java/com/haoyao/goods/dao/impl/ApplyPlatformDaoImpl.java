package com.haoyao.goods.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.haoyao.goods.dao.ApplyPlatformDao;
import com.haoyao.goods.model.ApplyPlatform;

/**
 * 应用平台
 * @author Administrator
 *
 */
@Repository
public class ApplyPlatformDaoImpl extends BaseDaoImpl<ApplyPlatform, Long> implements ApplyPlatformDao {
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<ApplyPlatform> getPlatformList() {
		String hql = "from "+ApplyPlatform.class.getName() + " where 1=1 ";
		//final Map<String,Object> paramMap = new HashMap<String,Object>();
		//hql += getWhereSql(pvo,paramMap);
		List<ApplyPlatform> ls = queryByHQL(hql.toString());
		return ls;
	}

	@Override
	public List<ApplyPlatform> getPlatformList(int startNum, Integer pageSize,
			String hqlString) {
		return queryByHQL("from ApplyPlatform where 1 = 1 " + hqlString , null, startNum, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplyPlatform> findPlatforms(String hqlString) {
		return getHibernateTemplate().find("from ApplyPlatform where 1 = 1 " + hqlString );
	}
	@SuppressWarnings("unchecked")
	public ApplyPlatform loadByName(String name) {
		List<ApplyPlatform> list = getHibernateTemplate().find("from ApplyPlatform p where p.name = ? ",name);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}


	@Override
	public ApplyPlatform loadByRoleId(Long roleId) {
		String sql = "select * from applyPlatform app where app.id = (select platformId from role where id="+roleId+")";
        ApplyPlatform app = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ApplyPlatform>(ApplyPlatform.class));
		return app;
	}

	@Override
	public ApplyPlatform loadByRescId(Long rescId) {
		
		//select * from applyPlatform app where app.id = (select platformId from permission where id='1')
		String sql = "select * from applyPlatform app where app.id = (select platformId from permission where id="+rescId+")";
        ApplyPlatform app = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ApplyPlatform>(ApplyPlatform.class));
		return app;
	}
}
