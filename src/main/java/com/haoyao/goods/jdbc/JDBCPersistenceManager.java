package com.haoyao.goods.jdbc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;   

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.haoyao.goods.exception.DAOException;
import com.haoyao.goods.persistence.IJdbcPersistence;
import com.haoyao.goods.util.DBTool;



/**
 * DAO接口jdbc实现.
 * 
 * @author 
 * @update 2011-9-2 上午09:39:03
 */

@SuppressWarnings("unchecked")
@Repository
public class JDBCPersistenceManager implements IJdbcPersistence {

    protected final Log log = LogFactory.getLog(getClass());
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 数据库工具类
     */
    @Resource 
    private DBTool dbTool;

    /**
     * 数据库名
     */
    private String databaseProductName;


    public SqlRowSet getRowSet(String sqlstr) {
	try {
	    return jdbcTemplate.queryForRowSet(sqlstr);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public SqlRowSet getRowSet(String sqlstr, Object[] args) {
	try {
	    return jdbcTemplate.queryForRowSet(sqlstr, args);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public long getLong(String sqlstr) {
	try {
	    return jdbcTemplate.queryForLong(sqlstr);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public long getLong(String sqlstr, Object[] args) {
	try {
	    return jdbcTemplate.queryForLong(sqlstr, args);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public int getInt(String sqlstr) {
	try {
	    return jdbcTemplate.queryForInt(sqlstr);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public int getInt(String sqlstr, Object[] args) {
	try {
	    return jdbcTemplate.queryForInt(sqlstr, args);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public Object getObject(String sqlstr, RowMapper rowMapper) {
	try {
	    return jdbcTemplate.queryForObject(sqlstr, rowMapper);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public Object getObject(String sqlstr, Object[] args, RowMapper rowMapper) {
	try {
	    return jdbcTemplate.queryForObject(sqlstr, args, rowMapper);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
    }

    public List getList(String sqlstr, RowMapper rowMapper) {
	List list = null;
	try {
	    list = (List) jdbcTemplate.query(sqlstr, new RowMapperResultSetExtractor(rowMapper));
	} catch (EmptyResultDataAccessException e) {
	    return null;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

	return list;
    }

    public List getList(String sqlstr, Object[] args, RowMapper rowMapper) {
	List list = null;
	try {
	    list = (List) jdbcTemplate.query(sqlstr, args, new RowMapperResultSetExtractor(rowMapper));
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
	return list;
    }

    public Map getList(String sqlstr, int iPageNo, int iPageSize, RowMapper rowMapper) {
	Map map = null;
	databaseProductName = dbTool.getDatabaseProductName();
	try {
	    if (databaseProductName.equals("MySQL")) {
		map = dbTool.getMysqlPageMap(sqlstr, iPageNo, iPageSize, rowMapper);
	    } else if (databaseProductName.equals("Oracle")) {
		map = dbTool.getOraclePageMap(sqlstr, iPageNo, iPageSize, rowMapper);
	    }else if (databaseProductName.equals("Microsoft SQL Server")) {//添加sqlserver数据库连接查询 add by wangwj 2012-12-03 下午14:00:00
		map = dbTool.getSqlServerPageMap(sqlstr, iPageNo, iPageSize, rowMapper);
	    }

	    // 本发分页方法通用性非常不错，但是对于大数据量的数据库操作性能较差
	    // map = dbTool.getPageSet(sqlstr, iPageNo, iPageSize);
	    // Informix专用
	    // map = dbTool.getINFPageSet(sqlstr, iPageNo, iPageSize);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

	return map;
    }

    public Map getList(String sqlstr, Object[] args, int iPageNo, int iPageSize, RowMapper rowMapper) {
	Map map = null;
	databaseProductName = dbTool.getDatabaseProductName();
	try {
	    if (databaseProductName.equals("MySQL")) {
		map = dbTool.getMysqlPageMap(sqlstr, args, iPageNo, iPageSize, rowMapper);
	    } else if (databaseProductName.equals("Oracle")) {
		map = dbTool.getOraclePageMap(sqlstr, args, iPageNo, iPageSize, rowMapper);
	    }else if (databaseProductName.equals("Microsoft SQL Server")) {  //添加sqlserver数据库连接查询 add by wangwj 2012-12-03 下午14:25:00
		map = dbTool.getSqlServerPageMap(sqlstr, args, iPageNo, iPageSize, rowMapper);
	    }
	    
	    // 本发分页方法通用性非常不错，但是对于大数据量的数据库操作性能较差
	    // map = dbTool.getPageSet(sqlstr,mapPs, iPageNo, iPageSize);
	    // Informix专用
	    // map = dbTool.getINFPageSet(sqlstr,mapPs, iPageNo, iPageSize);
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}

	return map;
    }

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }
}
