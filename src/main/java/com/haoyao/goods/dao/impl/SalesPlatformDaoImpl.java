package com.haoyao.goods.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.haoyao.goods.dao.SalesPlatformDao;
import com.haoyao.goods.model.SalesPlatform;

/**
 * 
* @ClassName: SalesPlatformDaoImpl 
* @Description: TODO(销售平台DAO实现类) 
* @author 马锐 bjmarui@126.com 
* @date 2014年3月19日 下午4:56:46 
*
 */
public class SalesPlatformDaoImpl extends HibernateDaoSupport implements SalesPlatformDao {
	
private HibernateTemplate hibernateTemplate;
	
   /**
    * 保存一个实体
    */
	public Object save(final Object entity) {
		return  getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.save(entity);
				return entity;
			}
		});
	}
    
	/**
	 * 删除
	 */
	public void delete(final Object entity) {
		 getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.delete(entity);
				session.flush();
				return null;
			}
		});
		
	}
   
	/**
	 * 更新
	 */
	public void update(final Object entity) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.update(entity);
				session.flush();
				return null;
			}
		});
	}
    
	/**
	 * 根据条件查询
	 */
	public List<Object> createQuery(final String queryString) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				List<SalesPlatform> list=query.list();
				return list;
			}
		});
	}
    
	/**
	 * 根据id查找到这个类
	 */
	public SalesPlatform findById(Integer id) {
		return this.getHibernateTemplate().get(SalesPlatform.class, id);
	}

    
	/**
	 * 根据条件查询有多少条记录
	 */
	public int count(String name) {

		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from SalesPlatform p");
		
		if (name!= null && !"".equals(name)) {
			sql.append(" where platformName like '%").append(name)
					.append("%' or p.platformCode like '%").append(name).append("%'");
		}
		List<Long> count=this.getHibernateTemplate().find(sql.toString());
		return count.get(0).intValue();
	
	}
	
	/**
	 * 根据条件查询并分页
	 */
	@SuppressWarnings("unchecked")
	public List<SalesPlatform> getList(final String name,final int currentPage, final int pageSize) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<SalesPlatform>>() {
			public List<SalesPlatform> doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				StringBuffer sql = new StringBuffer();
				sql.append("select p from SalesPlatform p");
				
				if (name!= null && !"".equals(name)) {
					sql.append(" where p.platformName like '%").append(name.trim())
							.append("%' or p.platformCode like '%").append(name.trim()).append("%'");
				}
				return session.createQuery(sql.toString()).setFirstResult(currentPage*pageSize).setMaxResults(pageSize).list();
			}
		});
	}

}
