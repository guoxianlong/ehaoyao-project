/**
 * 
 */
package com.ehaoyao.opertioncenter.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.member.dao.HealthKeywordHeadDao;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordHead;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordHeadService;

/**
 * 健康分类Service实现类
 * @author kxr
 *
 */
@Service
public class HealthKeywordHeadServiceImpl implements HealthKeywordHeadService {

	@Autowired
	private HealthKeywordHeadDao healthKeywordHeadDao;
	
	/**
	 * @param healthKeywordHeadDao
	 */
	public void setHealthKeywordHeadDao(HealthKeywordHeadDao healthKeywordHeadDao) {
		this.healthKeywordHeadDao = healthKeywordHeadDao;
	}

	/**
	 * 查询健康分类
	 * @return
	 */
	@Override
	public List<HealthKeywordHead> getHealthKeywordHead() {
		return healthKeywordHeadDao.getHealthKeywordHead();
	}
	
	/**
	 * 添加健康分类
	 * @param health
	 * @return
	 */
	@Override
	public Object saveHealthKeywordHead(HealthKeywordHead health) {
		return healthKeywordHeadDao.saveHealthKeywordHead(health);
	}
	
	/**
	 * 修改健康分类
	 * @param health
	 * @return
	 */
	@Override
	public void updateHealthKeywordHead(HealthKeywordHead health) {
		healthKeywordHeadDao.updateHealthKeywordHead(health);
	}
	
	/**
	 * 根据id删除健康分类
	 * @param id
	 * @return
	 */
	@Override
	public void delhealthKeywordHead(HealthKeywordHead kealthKeywordHead) {
		healthKeywordHeadDao.delhealthKeywordHead(kealthKeywordHead);
	}

}
