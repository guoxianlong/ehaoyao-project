package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.model.Organization;

public interface OrganizationDao extends BaseDao<Organization, Long>{

	List<Organization> findByName(String name);

	Long getNewCode();

	Organization loadByName(String name);

	List<Organization> findOrgList(Integer startNum, Integer pageSize,
			String hqlString);

	List<Organization> getOrgList(String hqlString);

}
