package com.haoyao.goods.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * DAO接口JDBC.
 * 
 * @author 
 * @update 
 */

@SuppressWarnings("unchecked")
public interface IJdbcPersistence {

    /**
     * 获取Spring的JdbcTemplate类.
     * 
     * @return
     * @author 
     * @update 
     */
    public JdbcTemplate getJdbcTemplate();

    /** 
     * 查询返回RowSet.
     * 
     * @param sqlstr
     *            sql语句
     * @return rowset 返回数据
     * @author 
     * @update 
     */
    public SqlRowSet getRowSet(String sqlstr);

    /**
     * 查询返回RowSet.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            参数
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public SqlRowSet getRowSet(String sqlstr, Object[] args);

    /**
     * 查询返回long.
     * 
     * @param sqlstr
     *            sql语句
     * @return
     * @author 
     * @update 
     */
    public long getLong(String sqlstr);

    /**
     * 查询返回long.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            参数
     * @return
     * @author 
     * @update 
     */
    public long getLong(String sqlstr, Object[] args);

    /**
     * 查询返回int.
     * 
     * @param sqlstr
     *            sql语句
     * @return
     * @author 
     * @update 
     */
    public int getInt(String sqlstr);

    /**
     * 查询返回int.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            参数
     * @return
     * @author 
     * @update 
     */
    public int getInt(String sqlstr, Object[] args);

    /**
     * 查询返回Object.
     * 
     * @param sqlstr
     *            sql语句
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public Object getObject(String sqlstr, RowMapper rowMapper);

    /**
     * 
     * 查询返回Object.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            参数
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public Object getObject(String sqlstr, Object[] args, RowMapper rowMapper);

    /**
     * 获取所有记录.
     * 
     * @param sqlstr
     *            sql语句
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public List getList(String sqlstr, RowMapper rowMapper);

    /**
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            参数
     * @param rowMapper
     *            返回数据
     * @return
     * @description 获取所有记录
     * @version 1.0
     * @author 
     * @update 
     */
    /**
     * 获取所有记录.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            相当于PreparedStatement的？号传值 new Object[]{'1',2}
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public List getList(String sqlstr, Object[] args, RowMapper rowMapper);

    /**
     * 分页查询 目前只支持 mysql Oracle.
     * 
     * @param sqlstr
     *            sql语句
     * @param iPageNo
     *            页码
     * @param iPageSize
     *            每面大小
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public Map getList(String sqlstr, int iPageNo, int iPageSize, RowMapper rowMapper);

    /**
     * 分页查询.
     * 
     * @param sqlstr
     *            sql语句
     * @param args
     *            相当于PreparedStatement的？号传值 new Object[]{'1',2}
     * @param iPageNo
     *            页码
     * @param iPageSize
     *            每页数据大小
     * @param rowMapper
     *            返回数据
     * @return
     * @author 
     * @update 
     */
    public Map getList(String sqlstr, Object[] args, int iPageNo, int iPageSize, RowMapper rowMapper);
}
