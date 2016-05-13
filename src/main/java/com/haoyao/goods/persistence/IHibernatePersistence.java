package com.haoyao.goods.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * DAO接口Hibernate.
 * 
 * @author 
 * @update 
 */

public abstract interface IHibernatePersistence {
    /**
     * 获取HibernateTemplate类.
     * 
     * @return HibernateTemplate
     * @author 
     * @update 
     */
    public HibernateTemplate getHibernateTemplate();

    /**
     * 获取对象
     * 
     * @param paramString
     *            sql语句
     * @return
     * @author 
     * @update 
     */
    public abstract Object getObject(String paramString);

    /**
     * 拼凑sql语句获取所有数据集.
     * 
     * @param paramString
     *            sql语句
     * @return List数据集
     * @author 
     * @update 
     */
    public abstract List getList(String paramString);

    /**
     * 构造查询条件获取所有数据集.
     * 
     * @param paramString
     *            sql语句
     * @param paramMap
     *            查询条件(key-索引号,value-值)
     * @return List数据集
     * @author 
     * @update 
     */
    public abstract List getList(String paramString, Map paramMap);

    /**
     * 拼凑sql语句获取分页数据集.
     * 
     * @param paramString
     *            sql语句
     * @param paramInt1
     *            第几页
     * @param paramInt2
     *            每页显示多少条
     * @return Map(RecTotal-总记录数,PageTotal-总页数,List-数据) @
     * @author 
     * @update 
     */
    public abstract Map getList(String paramString, int paramInt1, int paramInt2);

    /**
     * 构造查询条件获取分页数据集.
     * 
     * @param paramString
     *            sql语句
     * @param paramMap
     *            查询条件(key-字段名,value-值)
     * @param paramInt1
     *            第几页
     * @param paramInt2
     *            每页显示多少条
     * @return Map(RecTotal-总记录数,PageTotal-总页数,List-数据)
     * @author 
     * @update 
     */
    public abstract Map getList(String paramString, Map paramMap, int paramInt1, int paramInt2);

    /**
     * 添加或更新对象.
     * 
     * @param paramObject
     *            要添加或更新的对象
     * @author 
     * @update 
     */
    public abstract void save(Object paramObject);

    /**
     * 添加对象.
     * 
     * @param paramObject
     *            要添加的对象
     * @author 
     * @update 
     */
    public abstract Serializable add(Object paramObject);

    /**
     * 更新对象.
     * 
     * @param paramObject
     *            要更新的对象
     * @author 
     * @update 
     */
    public abstract void update(Object paramObject);

    /**
     * 
     * @param paramArrayOfSerializable
     *            @
     * @description 批量删除对象
     * @version 1.0
     * @author 
     * @update Aug 8, 
     */
    public abstract void remove(Serializable[] paramArrayOfSerializable);

    /**
     * 删除对象.
     * 
     * @param paramObject
     *            要删除的对象
     * @author 
     * @update 
     */
    public abstract void remove(Object paramObject);

    /**
     * 根据ID查询对象.
     * 
     * @param paramClass
     *            要查询的实Class
     * @param paramLong
     *            id
     * @return
     * @author 
     * @update 
     */
    public abstract Object getById(Class paramClass, long paramLong);

    /**
     * 根据主键查询对象.
     * 
     * @param paramClass
     *            要查询的实体class
     * @param paramObject
     *            主键
     * @return
     * @author 
     * @update 
     */
    public abstract Object getByPrimaryKey(Class paramClass, Object paramObject);

    /**
     * 根据对象获取全部数据.
     * 
     * @param paramClass
     *            实体对象
     * @return
     * @author 
     * @update 
     */
    public abstract List findAll(Class paramClass);

    /**
     * 根据对象获取数据 并按对象.属性排列.
     * 
     * @param paramClass
     *            对象
     * @param paramString
     *            排序属性
     * @return
     * @author 
     * @update 
     */
    public abstract List findAllSorted(Class paramClass, String paramString);

    /**
     * 根据对象获取数据 并按对象.属性排列.
     * 
     * @param paramClass
     *            对象
     * @param paramString1
     *            排序属性
     * @param paramString2
     *            排序属性
     * @return
     * @author 
     * @update 
     */
    public abstract List findAllSorted(Class paramClass, String paramString1, String paramString2);

    /**
     * 构造查询条件获取所有数据.
     * 
     * @param paramString
     *            sql语句
     * @param paramArrayOfObject
     *            条件值
     * @param paramArrayOfClass
     *            条件类型
     * @return
     * @author 
     * @update 
     */
    public abstract List find(String paramString, Object[] paramArrayOfObject, Class[] paramArrayOfClass);

    /**
     * 执行SQL语句.
     * 
     * @param paramString
     *            sql语句
     * @author 
     * @update 
     */
    public abstract void execute(String paramString);

    /**
     * 根据查询条件执行SQL语句.
     * 
     * @param paramString
     *            sql语句
     * @param paramMap
     *            查询条件(key-字段名,value-值)
     * @author 
     * @update 
     */
    public abstract void execute(String paramString, Map paramMap);

    /**
     * 获取下一个ID.
     * 
     * @param paramString1
     *            class名
     * @param paramString2
     *            字段名
     * @param paramInt
     *            长度
     * @return
     * @author 
     * @update 
     */
    public abstract String getNextID(String paramString1, String paramString2, int paramInt);

    /**
     * 刷新.
     * 
     * @param paramObject
     * @author 
     * @update 
     */
    public abstract void flush(Object paramObject);

    /**
     * session清除.
     * 
     * @param paramObject
     * @author 
     * @update 
     */
    public abstract void clearObject(Object paramObject);

}