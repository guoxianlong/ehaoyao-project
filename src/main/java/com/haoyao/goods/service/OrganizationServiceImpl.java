package com.haoyao.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.OrganizationDao;
import com.haoyao.goods.model.Organization;

@Service
public class OrganizationServiceImpl {
	
	@Autowired
	private OrganizationDao organizationDao;

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	/**
	 * 条件分页查询机构信息
	 * @param startNum
	 * @param pageSize
	 * @param hqlString
	 * @return
	 */
	public List<Organization> findOrgList(Integer startNum, Integer pageSize, String hqlString) {
		return organizationDao.findOrgList(startNum, pageSize,hqlString);
	}

	public void save(Organization org) {
		organizationDao.save(org);
	}

	/**
	 * 主键查找ID
	 * @param id
	 * @return
	 */
	public Organization loadById(Long id) {
		return organizationDao.get(id);
	}

	public void update(Organization org) {
		organizationDao.update(org);
	}

	public void delete(Organization org) {
		organizationDao.delete(org);
	}

	/**
	 * 获取所有的机构信息
	 * @return
	 */
	public List<Organization> findAllOrg() {
		return organizationDao.getAllList();
	}
	
	/**
	 * 条件查询机构总数
	 * @param hqlString
	 * @return
	 */
	public Integer getOrgCount(String hqlString) {
		return organizationDao.getOrgList(hqlString).size();
	}

	/**
	 * 根据名称获取机构信息
	 * @param name
	 * @return
	 */
	public List<Organization> findByName(String name) {
		return organizationDao.findByName(name);
	}

	/**
	 * 下一个机构代码
	 * @return
	 */
	public Long getNewCode() {
		return organizationDao.getNewCode();
	}

	/**
	 * 根据名称获取机构信息
	 * @param name
	 * @return
	 */
	public Organization loadByName(String name) {
		return organizationDao.loadByName(name);
	}
	
}
