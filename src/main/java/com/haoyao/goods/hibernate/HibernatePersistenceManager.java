package com.haoyao.goods.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.haoyao.goods.exception.DAOException;
import com.haoyao.goods.persistence.IHibernatePersistence;


/**
 * DAO接口的Hibernate实现.
 * 
 * @author 
 * @update 2011-9-2 上午09:14:28
 */

@Transactional
@Repository
public class HibernatePersistenceManager implements IHibernatePersistence {
	@Resource
    private HibernateTemplate hibernateTemplate;

    protected final Log log = LogFactory.getLog(getClass());

    /**
     * 默认的每次记录数.
     */
    protected int pagesize = 10;

    /**
     * 当前页数.
     */
    protected int pageno = 1;

    /**
     * 总记录数.
     */
    protected int rectotal = 0;

    /**
     * 总页数.
     */
    protected int pagetotal = 0;

    private static Map classToHibernateTypeMap = new HashMap();

    static {
	classToHibernateTypeMap.put(BigDecimal.class, Hibernate.BIG_DECIMAL);
	classToHibernateTypeMap.put(Boolean.class, Hibernate.BOOLEAN);
	classToHibernateTypeMap.put(Byte.class, Hibernate.BYTE);
	classToHibernateTypeMap.put(Character.class, Hibernate.CHARACTER);
	classToHibernateTypeMap.put(Date.class, Hibernate.DATE);
	classToHibernateTypeMap.put(Double.class, Hibernate.DOUBLE);
	classToHibernateTypeMap.put(Float.class, Hibernate.FLOAT);
	classToHibernateTypeMap.put(Integer.class, Hibernate.INTEGER);
	classToHibernateTypeMap.put(Long.class, Hibernate.LONG);
	classToHibernateTypeMap.put(Short.class, Hibernate.SHORT);
	classToHibernateTypeMap.put(String.class, Hibernate.STRING);
	classToHibernateTypeMap.put(Timestamp.class, Hibernate.TIMESTAMP);
    }

    protected HibernatePersistenceManager() {
	super();
    }

    /**
     * 对数据进行持久化 保存.
     */
    public void save(Object o) {
	try {
	    getHibernateTemplate().saveOrUpdate(o);
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 对数据进行持久化 添加.
     */
    public Serializable add(Object o) {
	try {
	    return getHibernateTemplate().save(o);
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 对数据进行持久化 添加.
     */
    public Serializable addToDb(Object paramObject) {
	try {
	    return getHibernateTemplate().save(paramObject);
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 对数据进行持久化 更新.
     */
    public void update(Object o) {
	try {
	    getHibernateTemplate().update(o);
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 返回一个对象
     */
    public Object getObject(String paramString) {
	try {
	    List<Object> list = getHibernateTemplate().find(paramString);
	    if (list.iterator().hasNext()) {
		return list.iterator().next();
	    }
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 数据库查询，返回一个list.
     */
    public List getList(final String sqlstr) {
	try {
	    return getHibernateTemplate().find(sqlstr);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    /**
     * 数据库查询，返回一个map，里面
     */
    public Map getList(final String sqlstr, final int iPageNo, final int iPageSize) {
	try {
	    return (Map) getHibernateTemplate().execute(new HibernateCallback() {
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    Query query = session.createQuery(sqlstr);
		    List list = new ArrayList();
		    // 实现分页
		    setPageNo(iPageNo);
		    setPageSize(iPageSize);
		    setQueryPage(query);
		    list = query.list();
		    // 组织返回结果
		    Map map = new HashMap();
		    map.put("RecTotal", getRecTotal());
		    map.put("PageTotal", getPageTotal());
		    map.put("List", list);

		    // 返回
		    return map;
		}
	    });
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    // 批量删除对象
    public void remove(Serializable[] id) throws DataAccessException {
	for (int i = 0; i < id.length; i++) {
	    remove(id[i]);
	}
    }

    /**
     * @param i
     *            设置分页行数大小
     */
    protected void setPageSize(int i) {
	this.pagesize = i;
    }

    /**
     * @return 返回当前页号，初始值为1
     */
    protected int getPageNo() {
	return pageno;
    }

    /**
     * @param i
     *            设置当前页号
     */
    protected void setPageNo(int i) {
	this.pageno = i;
    }

    /**
     * @return 返回总记录数
     */
    protected Integer getRecTotal() {
	return new Integer(rectotal);
    }

    /**
     * @return 返回总页数
     */
    protected Integer getPageTotal() {
	return new Integer(pagetotal);
    }

    /**
     * 设置查询分页
     */
    protected void setQueryPage(final Query query) {
	HibernateCallback callback = new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException, SQLException {

		String queryString = null;

		String hql = query.getQueryString().toString();

		int groupint = hql.toUpperCase().indexOf("GROUP");
		int distinctint = hql.toUpperCase().indexOf("DISTINCT");
		if ((groupint > 0) || (distinctint > 0)) {
		    queryString = query.getQueryString();
		    int inttmp = session.createQuery(queryString).list().size();
		    Integer num = new Integer(inttmp);
		    return num;
		}

		int j = hql.toUpperCase().indexOf("ORDER");

		if (j > 0) {
		    hql = hql.substring(0, j);
		}
		if (hql.toUpperCase().indexOf("SELECT") != -1) {
		    int i = hql.toUpperCase().indexOf("FROM");

		    queryString = "Select count(*) " + hql.substring(i, hql.length());
		} else {
		    queryString = "Select count(*) " + hql;
		}

		if (log.isDebugEnabled()) {
		    log.debug(queryString);
		}
		// 设置分页起始记录号
		return (Long) session.createQuery(queryString).uniqueResult();

	    }
	};

	this.rectotal = new Integer(String.valueOf(getHibernateTemplate().execute(callback)));

	this.pagetotal = (this.rectotal - 1) / this.pagesize + 1;
	if (this.getPageNo() > pagetotal)
	    this.setPageNo(pagetotal);
	query.setFirstResult((this.pageno - 1) * this.pagesize);
	// 设置页内数据量
	query.setMaxResults(this.pagesize);

    }

    public List findAll(Class type) {
	try {
	    return findAllSorted(type, null);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public List findAllSorted(Class type, String sortField) {
	String query = "FROM result IN CLASS " + type.getName();
	if (sortField != null)
	    query += " ORDER BY LOWER(result." + sortField + ")";

	try {
	    return getHibernateTemplate().find(query);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

    }

    public List findAllSorted(Class type, String ascSortProperty, String descSortProperty) {

	String query = "FROM result IN CLASS " + type.getName();
	if (ascSortProperty != null)
	    query += " ORDER BY LOWER(result." + ascSortProperty + ")";
	if (descSortProperty != null)
	    query += " , LOWER(result." + descSortProperty + ") DESC";
	try {
	    return getHibernateTemplate().find(query);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public Object getById(Class cls, long id) {
	try {
	    return getHibernateTemplate().get(cls, new Long(id));
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

    }

    public Object getByPrimaryKey(Class type, Object pk) {
	try {
	    return getHibernateTemplate().get(type, (Serializable) pk);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public List find(String query, Object[] parameters, Class[] parameterTypes) {
	List results = null;
	String[] paras = (String[]) parameters;
	if ((parameterTypes != null) || (parameters != null)) {
	    Type[] hibernate_parameter_types = getHibernatedParameterTypes(parameterTypes);

	    results = getHibernateTemplate().findByNamedQueryAndNamedParam(query, paras, hibernate_parameter_types);
	} else {
	    results = getHibernateTemplate().find(query);
	}
	return results;
    }

    private static Type[] getHibernatedParameterTypes(Class[] types) {
	if (types == null) {
	    return null;
	}

	Type[] hib_types = new Type[types.length];

	for (int i = 0; i < types.length; i++) {
	    Class type = types[i];

	    hib_types[i] = (Type) classToHibernateTypeMap.get(type);
	}

	return hib_types;
    }

    public void remove(Object objectToRemove) {
	try {
	    this.getHibernateTemplate().delete(objectToRemove);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    // 执行SQL语句
    public void execute(final String sql) {
	HibernateCallback callback = new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		session.connection().setAutoCommit(false);

		try {
		    session.connection().createStatement().execute(sql);
		    session.connection().commit();
		} catch (Exception e) {
		    session.connection().rollback();
		}
		return null;
	    }

	};
	try {
	    getHibernateTemplate().execute(callback);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    // 获取下一个ID
    public String getNextID(String classname, String fieldname, int len) {
	long nextid = 0L;

	String hql = "select max(a." + fieldname + ") from " + classname + " a";
	List ls = getList(hql);
	if (ls.size() > 0) {
	    String s = (String) ls.get(0);
	    nextid = Long.parseLong(s) + 1;
	} else {
	    nextid = 1L;
	}

	StringBuffer sb = new StringBuffer();

	for (int i = 0; i < len; i++) {
	    sb.append("0");
	}

	java.text.DecimalFormat format = new java.text.DecimalFormat(sb.toString());

	return format.format(nextid);

    }

    public void flush(Object ob) {
	try {
	    // this.getSession().flush();
	    this.getHibernateTemplate().refresh(ob);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public void clearObject(Object ob) {
	try {
	    this.getHibernateTemplate().evict(ob);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public List getList(final String sqlstr, final Map mapps) {
	try {
	    return (List) getHibernateTemplate().execute(new HibernateCallback() {
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    Query query = session.createQuery(sqlstr);
		    if (mapps != null && !mapps.isEmpty()) {
			Iterator it = mapps.keySet().iterator();
			while (it.hasNext()) {
			    String numStr = (String) it.next();
			    int num = Integer.parseInt(numStr);
			    query.setParameter(num, mapps.get(numStr));
			}
		    }
		    return query.list();
		}
	    });
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    protected void setQueryPage(final Query query, final Map mapps) throws HibernateException {
	HibernateCallback callback = new HibernateCallback() {
	    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		Object[] parameters = null;
		Type[] parameterTypes = null;
		String queryString = null;

		if (mapps != null && !mapps.isEmpty()) {
		    Iterator it = mapps.keySet().iterator();
		    parameters = new Object[mapps.size()];
		    Class[] parameterTypesClass = new Class[mapps.size()];
		    while (it.hasNext()) {
			String numStr = (String) it.next();
			int num = Integer.parseInt(numStr);
			Object pavalue = mapps.get(numStr);
			parameters[num] = pavalue;
			parameterTypesClass[num] = pavalue.getClass();
			parameterTypes = getHibernatedParameterTypes(parameterTypesClass);
		    }
		}

		String hql = query.getQueryString().toString();

		int groupint = hql.toUpperCase().indexOf("GROUP");
		int distinctint = hql.toUpperCase().indexOf("DISTINCT");
		if ((groupint > 0) || (distinctint > 0)) {
		    queryString = query.getQueryString();
		    int inttmp = session.createQuery(queryString).setParameters(parameters, parameterTypes).list().size();
		    Integer num = new Integer(inttmp);
		    return num;
		}

		int j = query.getQueryString().toUpperCase().indexOf("ORDER");

		if (j > 0) {
		    hql = hql.substring(0, j);
		}
		if (hql.toUpperCase().indexOf("SELECT") != -1) {
		    int i = hql.toUpperCase().indexOf("FROM");

		    queryString = "Select count(*) " + hql.substring(i, hql.length());
		} else {
		    queryString = "Select count(*) " + hql;
		}

		if (log.isDebugEnabled()) {
		    log.debug(queryString);
		}
		// 设置分页起始记录号
		return ((Long) session.createQuery(queryString).setParameters(parameters, parameterTypes).iterate().next()).intValue();
	    }
	};
	try {
	    this.rectotal = new Integer(String.valueOf(getHibernateTemplate().execute(callback)));
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

	this.pagetotal = (this.rectotal - 1) / this.pagesize + 1;
	if (this.getPageNo() > pagetotal)
	    this.setPageNo(pagetotal);
	query.setFirstResult((this.pageno - 1) * this.pagesize);

	// 设置页内数据量
	query.setMaxResults(this.pagesize);

    }

    public Map getList(final String sqlstr, final Map mapps, final int iPageNo, final int iPageSize) {
	try {
	    return (Map) getHibernateTemplate().execute(new HibernateCallback() {
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    Query query = session.createQuery(sqlstr);
		    List list = new ArrayList();

		    if (mapps != null && !mapps.isEmpty()) {
			Iterator it = mapps.keySet().iterator();
			while (it.hasNext()) {
			    String numStr = (String) it.next();
			    int num = Integer.parseInt(numStr);
			    query.setParameter(num, mapps.get(numStr));
			}
		    }

		    // 实现分页
		    setPageNo(iPageNo);
		    setPageSize(iPageSize);
		    if (mapps != null && !mapps.isEmpty()) {
			setQueryPage(query, mapps);
		    } else {
			setQueryPage(query);
		    }
		    list = query.list();

		    // 组织返回结果
		    Map map = new HashMap();

		    map.put("RecTotal", getRecTotal());
		    map.put("PageTotal", getPageTotal());
		    map.put("List", list);

		    // 返回
		    return map;
		}
	    });
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public void execute(final String sqlstr, final Map mapps) {
	HibernateCallback callback = new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		session.connection().setAutoCommit(false);

		try {
		    PreparedStatement ps = session.connection().prepareStatement(sqlstr);
		    if (mapps != null && !mapps.isEmpty()) {
			Iterator it = mapps.keySet().iterator();
			while (it.hasNext()) {
			    String numStr = (String) it.next();
			    int num = Integer.parseInt(numStr);
			    ps.setObject(num, mapps.get(numStr));
			}
		    }
		    ps.execute();
		    session.connection().commit();
		} catch (Exception e) {
		    session.connection().rollback();
		}
		return null;
	    }

	};
	try {
	    getHibernateTemplate().execute(callback);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
    }

}