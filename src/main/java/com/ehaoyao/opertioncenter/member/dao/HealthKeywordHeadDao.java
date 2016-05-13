package com.ehaoyao.opertioncenter.member.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.HealthKeywordHead;

/**
 * 健康分类Dao接口类
 * @author kxr
 *
 */
public interface HealthKeywordHeadDao {
	
	/**
	 * 查询健康分类
	 * @return
	 */
	public List<HealthKeywordHead> getHealthKeywordHead();
	
	/**
	 * 添加健康分类
	 * @param health
	 * @return
	 */
	public Object saveHealthKeywordHead(HealthKeywordHead health);
	
	/**
	 * 修改健康分类
	 * @param health
	 * @return
	 */
	public void updateHealthKeywordHead(HealthKeywordHead health);
	
	/**
	 * 根据id删除健康分类
	 * @param id
	 * @return
	 */
	public void delhealthKeywordHead(HealthKeywordHead kealthKeywordHead);

}