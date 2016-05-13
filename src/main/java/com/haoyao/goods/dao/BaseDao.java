package com.haoyao.goods.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;



/**
 * Dao接口 - Dao基接口
 * ============================================================================
 * ============================================================================
 */

public interface BaseDao<T, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);

	/**
	 * 获取所有实体对象集合
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAllList();

	/**
	 * 获取所有实体对象总数
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount();

	

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session
	 * 
	 */
	public void flush();

	/**
	 * 清除对象
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);

	/**
	 * 清除Session
	 * 
	 */
	public void clear();
	
	public List<T> queryByHQL(String hql);

	public List<T> queryByHQL(String hql, Map params);
	
	public List<T> queryByHQL(String hql, Map params,int startNum,int pageSize);
	
	public int queryCountByHQL(String hql, Map params);
	
	public List<T> getList(int startNum,int pageSize);
	
	
	public T save(T entity);

	public void update(T entity);


}