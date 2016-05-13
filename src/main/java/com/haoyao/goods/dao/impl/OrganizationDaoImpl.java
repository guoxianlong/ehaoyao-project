package com.haoyao.goods.dao.impl;

import java.util.List;

import com.haoyao.goods.dao.OrganizationDao;
import com.haoyao.goods.model.Organization;

public class OrganizationDaoImpl extends BaseDaoImpl<Organization,Long> implements OrganizationDao{

	@SuppressWarnings("unchecked")
	public List<Organization> findByName(String name) {
		return getHibernateTemplate().find("from Organization org where org.name = ?",name);
	}
	@SuppressWarnings("unchecked")
	public Long getNewCode() {
		Long newCode = 10001l;
		List<Organization> list = getHibernateTemplate().find(" from Organization org order by org.code desc ");
		if( list != null && list.size() > 0 ){
			return Long.parseLong(list.get(0).getCode()) + 1; 
		}
		return newCode;
	}
	@SuppressWarnings("unchecked")
	public Organization loadByName(String name) {
		List<Organization> list = getHibernateTemplate().find(" from Organization org where org.name = ? " ,name);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	public List<Organization> findOrgList(Integer startNum, Integer pageSize,
			String hqlString) {
		return queryByHQL("from Organization where 1 = 1 " + hqlString , null, startNum, pageSize);
	}
	@SuppressWarnings("unchecked")
	public List<Organization> getOrgList(String hqlString) {
		return getHibernateTemplate().find(" from Organization where 1 = 1 " + hqlString);
	}

}
