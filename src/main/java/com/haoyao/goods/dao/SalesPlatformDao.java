package com.haoyao.goods.dao;

import java.util.List;

import com.haoyao.goods.model.SalesPlatform;

/**
 * 
* @ClassName: SalesPlatformDao 
* @Description: TODO(销售平台DAO类) 
* @author 马锐 bjmarui@126.com 
* @date 2014年3月19日 下午4:55:55 
*
 */
public interface SalesPlatformDao {
	/**
	 * 可以编号或名称模糊查询，也可以查询全部
	 * @param queryString
	 * @param name
	 * @return
	 */
	public List<Object> createQuery(final String queryString);
    
	/**
	 * 保存实体
	 * @param model
	 * @return
	 */
	public Object save(final Object model);
    
	/**
	 * 更新实体
	 * @param model
	 */
	public void update(final Object model);
    
	/**
	 * 删除实体
	 * @param model
	 */
	public void delete(final Object model);
	
    /**
     * 根据id查找到这个类
     * @param id
     * @return
     */
	public SalesPlatform findById(Integer id);
	
	/**
	 * 查询多少条记录
	 * @return
	 */
	public int count(String name);
	
	/**
	 * 根据条件查询并分页
	 * @param name
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<SalesPlatform> getList(final String name,final int currentPage, final int pageSize);

}
