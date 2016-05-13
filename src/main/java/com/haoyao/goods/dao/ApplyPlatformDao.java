package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.model.ApplyPlatform;

/**
 * 应用平台
 * @author Administrator
 *
 */
public interface ApplyPlatformDao extends BaseDao<ApplyPlatform, Long>{
	/**
	 * 查询应用平台
	 * @param queryString
	 * @return
	 */
	public List<ApplyPlatform> getPlatformList();
	
	public List<ApplyPlatform> getPlatformList(int startNum, Integer pageSize,String hqlString);
	
	public List<ApplyPlatform> findPlatforms(String hqlString);
	
	public ApplyPlatform loadByName(String name);
	
	public ApplyPlatform loadByRoleId(Long roleId);
	
	public ApplyPlatform loadByRescId(Long rescid);
}
