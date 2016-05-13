package com.haoyao.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haoyao.goods.dao.ApplyPlatformDao;
import com.haoyao.goods.model.ApplyPlatform;

@Service
public class ApplyPlatformServiceImpl {

	@Autowired
	private ApplyPlatformDao platformDao;

	/**
	 * @param platformDao the platformDao to set
	 */
	public void setPlatformDao(ApplyPlatformDao platformDao) {
		this.platformDao = platformDao;
	}

	public List<ApplyPlatform> getPlatformList(){
		return platformDao.getPlatformList();
	}
	
	public List<ApplyPlatform> getPlatformList(int startNum, Integer pageSize,String hql) {
		return platformDao.getPlatformList(startNum, pageSize,hql);
	}
	
	/**
	 * 条件查询应用平台数量
	 * @param hqlString
	 * @return
	 */
	public Integer getPlatformCount(String hqlString) {
		return platformDao.findPlatforms(hqlString).size();
	}
	public void save(ApplyPlatform param) {
		platformDao.save(param);
	}
	
	public ApplyPlatform getApplyPlatformById(Long id){
		return platformDao.get(id); 
	}
	
	public void update(ApplyPlatform param) {
		platformDao.update(param);
	}
	
	public void delete(ApplyPlatform param) {
		platformDao.delete(param);
	}
	public ApplyPlatform loadByName(String name){
		return platformDao.loadByName(name);
	}
	public ApplyPlatform loadByRoleId(Long roleId){
		return platformDao.loadByRoleId(roleId);
	}
	public ApplyPlatform loadByRescId(Long rescId){
		return platformDao.loadByRescId(rescId);
	}
}
