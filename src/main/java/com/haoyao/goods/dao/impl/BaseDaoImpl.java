package com.haoyao.goods.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.haoyao.goods.dao.BaseDao;

/**
 * Dao实现类 - 基类
 * ============================================================================
 */
@Repository("baseDao")
public class BaseDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements BaseDao<T, PK> {

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";// "排序"属性名称
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	private static final String ID = "id";

	private Class<T> entityClass;

	 @Resource(name = "sessionFactory")
	 public void setBaseDaoSessionFactory(SessionFactory sessionFactory) {
	 super.setSessionFactory(sessionFactory);
	 }

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) this.getHibernateTemplate().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllList() {
		final String hql = " from " + entityClass.getName();

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	public Long getTotalCount() {
		final String hql = "select count(*) from " + entityClass.getName();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		});
		return ((BigDecimal) list.get(0)).longValue();
	}


	

	@SuppressWarnings("unchecked")
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = (T) this.getHibernateTemplate().load(entityClass, id);
		this.getHibernateTemplate().delete(entity);
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = (T) this.getHibernateTemplate().load(entityClass, id);
			this.getHibernateTemplate().delete(entity);
		}
		getHibernateTemplate().flush();
	}

	public void flush() {
		this.getHibernateTemplate().flush();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		this.getHibernateTemplate().evict(object);
	}

	public void clear() {
		this.getHibernateTemplate().clear();
	}

	public List<T> queryByHQL(final String hql) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	public List<T> queryByHQL(final String hql, final Map params) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setProperties(params);
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	public List<T> getList(final int startNum, final int pageSize) {
		final String hql = " from " + entityClass.getName();

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(startNum);
				query.setFetchSize(pageSize);
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	public List<T> queryByHQL(final String hql, final Map params, final int startNum, final int pageSize) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setProperties(params);
				query.setFirstResult(startNum);
				query.setMaxResults(pageSize);
				List list = query.list();
				return list;
			}
		});
		return list;
	}
	
	public int queryCountByHQL(final String hql, final Map params) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setProperties(params);
				List list = query.list();
				return list;
			}
		});
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return 0;
		}
	}
	
	public Object save(final Object model) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				session.save(model);
				session.flush();
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void update(final Object model) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				session.update(model);
				session.flush();
				return null;
			}
		});
	}

	public void delete(final Object model) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				session.delete(model);
				session.flush();
				return null;
			}
		});
	}
}
