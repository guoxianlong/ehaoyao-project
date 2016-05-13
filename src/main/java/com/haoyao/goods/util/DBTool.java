package com.haoyao.goods.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetWarning;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.JDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.haoyao.goods.exception.DAOException;





/**
 * JDBC 工具类.
 * 
 * @author 
 * @update 2011-9-2 下午04:32:57
 */   

@SuppressWarnings("unchecked")
public class DBTool {
    protected final Log log = LogFactory.getLog(getClass());
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DataSource dataSource;

    /**
     * 获取数据连接.
     * 
     * @return
     * @throws DataAccessException
     * @author 
     * @update 2011-9-2 下午04:33:17
     */
    private Connection getConnection() throws DataAccessException {
	Connection con = null;
	try {
	    con = dataSource.getConnection();
	    // con = DataSourceUtils.getConnection(dataSource);
	    return con;
	} catch (SQLException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("Get Connection occur Error!");
	}
    }

    /**
     * 获取数据库产品名称.
     * 
     * @return
     * @throws DataAccessException
     * @author 
     * @update 2011-9-2 下午04:33:28
     */
    public String getDatabaseProductName() throws DataAccessException {
	String databaseProductName = "";
	Connection conn = null;
	try {
	    conn = getConnection();
	    DatabaseMetaData databaseMetaData = conn.getMetaData();
	    databaseProductName = databaseMetaData.getDatabaseProductName();
	} catch (SQLException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	} finally {
	    try {
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
		log.error(ex);
		throw new DAOException("数据库操作异常!");
	    }
	}
	return databaseProductName;
    }

    /**
     * 
     * @param sql
     *            SQL语句
     * @param pageNo
     *            页码
     * @param pageSize
     *            页面大小
     * @param rowMapper
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     * @description 分页查询
     * @version 1.0
     * @author 史明松
     * @update Aug 24, 2011 9:39:08 AM
     */
    /**
     * 分页查询. *
     * 
     * @param sql
     *            SQL语句
     * @param pageNo
     *            页码
     * @param pageSize
     *            页面大小
     * @param rowMapper
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @author 史明松
     * @update 2011-9-2 下午04:33:41
     */
    public Map getMysqlPageMap(String sql, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sql + ") a";
	    recTotal = jdbcTemplate.queryForInt(sbak);
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = "select * from (" + sql + ") a limit " + String.valueOf((actualPageNo - 1) * pageSize) + "," + pageSize;
	    List list = (List) jdbcTemplate.query(sbak, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
	return hashmap;
    }

    /**
     * 分页查询.
     * 
     * @param sql
     *            SQL语句
     * @param args
     *            args 参数
     * @param pageNo
     *            页码
     * @param pageSize
     *            页面大小
     * @param rowMapper
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @author 
     * @update Aug 24, 2011 9:39:08 AM
     */
    public Map getMysqlPageMap(String sql, Object[] args, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sql + ") a";
	    recTotal = jdbcTemplate.queryForInt(sbak, args);
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = "select * from (" + sql + ") a limit " + String.valueOf((actualPageNo - 1) * pageSize) + "," + pageSize;
	    List list = (List) jdbcTemplate.query(sbak, args, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException("数据库操作异常!");
	}
	return hashmap;
    }

    /**
     * Oracle分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法性能非常高，适合对大数据量数据的分页，但是仅仅针对有效。.
     * 
     * @param sql
     *            SQL语句
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     */
    public Map getOraclePageMap(String sql, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sql + ")";
	    recTotal = jdbcTemplate.queryForInt(sbak);

	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = "select * from (select row_.*,rownum as orarownum from (" + sql + ") row_ where rownum < "
		    + String.valueOf(actualPageNo * pageSize + 1) + ") where orarownum >= " + String.valueOf((actualPageNo - 1) * pageSize + 1);
	    List list = (List) jdbcTemplate.query(sbak, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	}

	return hashmap;
    }

    /**
     * Oracle分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法性能非常高，适合对大数据量数据的分页，但是仅仅针对Oracle有效。.
     * 
     * @param sql
     *            SQL语句
     * @param Object
     *            [] args 参数
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     */
    public Map getOraclePageMap(String sql, Object[] args, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sql + ")";
	    recTotal = jdbcTemplate.queryForInt(sbak, args);
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = "select * from (select row_.*,rownum as orarownum from (" + sql + ") row_ where rownum < "
		    + String.valueOf(actualPageNo * pageSize + 1) + ") where orarownum >= " + String.valueOf((actualPageNo - 1) * pageSize + 1);
	    List list = (List) jdbcTemplate.query(sbak, args, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	}

	return hashmap;
    }
    
    /**
     * SqlServer分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 
     * 
     * @param sql
     *            SQL语句
     * @param Object
     *            [] args 参数
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     * 
     * @author add by wangwj 
     * 
     * @update 2012-12-03 下午 14:00:45
     */
    public Map getSqlServerPageMap(String sql, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    
	    //处理sql(拆解order by语句，去除别名字段)
	    String[] sqls = sqlHandle(sql);
	    
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sqls[0] + ")a";
	    recTotal = jdbcTemplate.queryForInt(sbak);
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);
	    
	    sbak = "select * from (select row_number() over ("+ sqls[1] +")temprownumber,* from (select top "+String.valueOf(actualPageNo * pageSize)
	    		+"tempcolumn=0,* from (" + sqls[0] + ")a )b )c where temprownumber > "+ String.valueOf((actualPageNo - 1) * pageSize);
	    List list = (List) jdbcTemplate.query(sbak, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	}

	return hashmap;
    }
    
    /**
     * SqlServer分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 
     * 
     * @param sql
     *            SQL语句
     * @param Object
     *            [] args 参数
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     * 
     * @author add by wangwj 
     * 
     * @update 2012-12-03 下午 16:31:45
     */
    public Map getSqlServerPageMap(String sql, Object[] args, int pageNo, int pageSize, RowMapper rowMapper) {
	sql = StringUtil.escapeSQLTags(sql);
	HashMap hashmap = null;
	int recTotal = 0;
	int actualPageNo = 1;
	try {
	    String sbak = "";
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    
	    //处理sql(拆解order by语句，去除别名字段)
	    String[] sqls = sqlHandle(sql);
	    
	    // 首先精确的获取记录集的大小
	    sbak = "select count(*) from (" + sqls[0] + ")a";
	    recTotal = jdbcTemplate.queryForInt(sbak, args);
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);
	    
	    sbak = "select * from (select row_number() over ("+ sqls[1] +")temprownumber,* from (select top "+String.valueOf(actualPageNo * pageSize)
		+"tempcolumn=0,* from (" + sqls[0] + ")a )b )c where temprownumber > "+ String.valueOf((actualPageNo - 1) * pageSize);
	    List list = (List) jdbcTemplate.query(sbak, args, new RowMapperResultSetExtractor(rowMapper));

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("List", list);

	} catch (EmptyResultDataAccessException e) {
	    recTotal = 0;
	}

	return hashmap;
    }

    /**
     * 通用分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法通用性非常不错，但是对于大数据量的数据库操作性能较差.
     * 
     * @param sql
     *            SQL语句
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageNo 页码 Key:PageTotal 总页数 Key:RecTotal
     *         总记录数 Key:RowSet 分页记录集
     * @throws DBException
     */
    public Map getPageSet(String sql, int pageNo, int pageSize) {
	sql = StringUtil.escapeSQLTags(sql);
	Connection conn = null;
	PreparedStatement prestmt = null;
	HashMap hashmap = null;
	CachedRowSet wrset = null;

	try {
	    int actualPageNo = 1;
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    conn = getConnection();

	    prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    ResultSet rset = prestmt.executeQuery();
	    rset.last();
	    int recTotal = rset.getRow();
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);
	    // 开始定位到第pageNo页
	    rset.absolute((actualPageNo - 1) * pageSize + 1);

	    wrset = new CachedRowSet() {
			
			public void unsetMatchColumn(String[] columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String[] columnNames) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public String[] getMatchColumnNames() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getMatchColumnIndexes() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean wasNull() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void updateTimestamp(String columnLabel, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTimestamp(int columnIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(String columnLabel, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(int columnIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(String columnLabel, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(int columnIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(String columnLabel, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(int columnIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(String columnLabel, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(int columnIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(String columnLabel, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(int columnIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(String columnLabel, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(int columnIndex, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(String columnLabel, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(int columnIndex, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, NClob nClob)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(String columnLabel, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(int columnIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(String columnLabel, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(int columnIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(String columnLabel, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(int columnIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(String columnLabel, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(int columnIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(String columnLabel, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(int columnIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(String columnLabel, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(int columnIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(String columnLabel, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(int columnIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(String columnLabel, boolean x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(int columnIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(String columnLabel, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(int columnIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(String columnLabel, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(int columnIndex, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchSize(int rows) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchDirection(int direction) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean rowUpdated() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowInserted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean relative(int rows) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void refreshRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previous() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean next() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void moveToInsertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void moveToCurrentRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean last() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isClosed() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isBeforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isAfterLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void insertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public SQLWarning getWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getType() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Timestamp getTimestamp(String columnLabel, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Statement getStatement() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public short getShort(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public short getShort(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public SQLXML getSQLXML(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SQLXML getSQLXML(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getRow() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Ref getRef(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Ref getRef(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSetMetaData getMetaData() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getLong(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long getLong(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getHoldability() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchDirection() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Date getDate(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCursorName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getConcurrency() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Clob getClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Clob getClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte getByte(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte getByte(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getBoolean(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean getBoolean(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Blob getBlob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Blob getBlob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean first() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int findColumn(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void deleteRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void close() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearWarnings() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void cancelRowUpdates() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void afterLast() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean absolute(int row) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void setUsername(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setUrl(String url) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setURL(int parameterIndex, URL x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setType(int type) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTransactionIsolation(int level) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(String parameterName, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(int parameterIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(String parameterName, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(int parameterIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(String parameterName, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(int parameterIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(String parameterName, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(int parameterIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRef(int i, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setReadOnly(boolean value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setQueryTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPassword(String password) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType,
					int scale) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType,
					int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int paramIndex, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int parameterIndex, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(String parameterName, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(int parameterIndex, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxRows(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxFieldSize(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(String parameterName, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(int parameterIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(String parameterName, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(int parameterIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(String parameterName, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(int parameterIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setEscapeProcessing(boolean enable) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(String parameterName, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(int parameterIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDataSourceName(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setConcurrency(int concurrency) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCommand(String cmd) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int i, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(String parameterName, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(int parameterIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(String parameterName, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(int parameterIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(String parameterName, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(int parameterIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int i, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(String parameterName, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(int parameterIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setArray(int i, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void removeRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isReadOnly() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getUrl() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Map<String, Class<?>> getTypeMap() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getTransactionIsolation() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getQueryTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getMaxRows() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getMaxFieldSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getEscapeProcessing() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getDataSourceName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCommand() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearParameters() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void addRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public void undoUpdate() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoInsert() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoDelete() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public Collection<?> toCollection(String column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void setTableName(String tabName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSyncProvider(String provider) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShowDeleted(boolean b) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPageSize(int size) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMetaData(RowSetMetaData md) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setKeyColumns(int[] keys) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rowSetPopulated(RowSetEvent event, int numRows)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback(Savepoint s) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void restoreOriginal() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void release() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previousPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void populate(ResultSet rs, int startRow) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void populate(ResultSet data) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean nextPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getTableName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SyncProvider getSyncProvider() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getShowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public RowSetWarning getRowSetWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public ResultSet getOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSet getOriginal() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getKeyColumns() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public RowSet createShared() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopySchema() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopyNoConstraints() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopy() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void commit() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean columnUpdated(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean columnUpdated(int idx) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void acceptChanges(Connection con) throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}
			
			public void acceptChanges() throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <T> T getObject(int columnIndex, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getObject(String columnLabel, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	    wrset.populate(rset);

	    // 关闭最终的结果集
	    rset.close();

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("RowSet", wrset);

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    log.error(ex);
	    throw new DAOException("数据库操作异常!");
	} finally {
	    try {
		if (prestmt != null) {
		    prestmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
		log.error(ex);
		throw new DAOException("数据库操作异常!");
	    }
	}
	return hashmap;
    }

    /**
     * 通用分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法通用性非常不错，但是对于大数据量的数据库操作性能较差.
     * 
     * @param sql
     *            SQL语句
     * @param map
     *            参数map(参数位置,参数值对象)(PreparedStatement)
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageNo 页码 Key:PageTotal 总页数 Key:RecTotal
     *         总记录数 Key:RowSet 分页记录集
     * @throws DBException
     */
    public Map getPageSet(String sql, Map map, int pageNo, int pageSize) {
	sql = StringUtil.escapeSQLTags(sql);
	Connection conn = null;
	PreparedStatement prestmt = null;
	HashMap hashmap = null;
	CachedRowSet wrset = null;

	try {
	    int actualPageNo = 1;
	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }
	    conn = getConnection();

	    prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    if (map != null && !map.isEmpty()) {
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
		    String numStr = (String) it.next();
		    int num = Integer.parseInt(numStr);
		    prestmt.setObject(num, map.get(numStr));
		}
	    }
	    ResultSet rset = prestmt.executeQuery();
	    rset.last();
	    int recTotal = rset.getRow();
	    int pageTotal = (recTotal - 1) / pageSize + 1;

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);
	    // 开始定位到第pageNo页
	    rset.absolute((actualPageNo - 1) * pageSize + 1);

	    wrset = new CachedRowSet() {
			
			public void unsetMatchColumn(String[] columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String[] columnNames) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public String[] getMatchColumnNames() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getMatchColumnIndexes() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean wasNull() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void updateTimestamp(String columnLabel, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTimestamp(int columnIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(String columnLabel, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(int columnIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(String columnLabel, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(int columnIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(String columnLabel, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(int columnIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(String columnLabel, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(int columnIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(String columnLabel, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(int columnIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(String columnLabel, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(int columnIndex, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(String columnLabel, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(int columnIndex, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, NClob nClob)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(String columnLabel, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(int columnIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(String columnLabel, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(int columnIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(String columnLabel, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(int columnIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(String columnLabel, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(int columnIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(String columnLabel, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(int columnIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(String columnLabel, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(int columnIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(String columnLabel, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(int columnIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(String columnLabel, boolean x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(int columnIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(String columnLabel, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(int columnIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(String columnLabel, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(int columnIndex, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchSize(int rows) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchDirection(int direction) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean rowUpdated() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowInserted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean relative(int rows) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void refreshRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previous() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean next() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void moveToInsertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void moveToCurrentRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean last() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isClosed() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isBeforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isAfterLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void insertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public SQLWarning getWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getType() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Timestamp getTimestamp(String columnLabel, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Statement getStatement() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public short getShort(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public short getShort(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public SQLXML getSQLXML(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SQLXML getSQLXML(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getRow() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Ref getRef(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Ref getRef(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSetMetaData getMetaData() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getLong(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long getLong(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getHoldability() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchDirection() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Date getDate(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCursorName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getConcurrency() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Clob getClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Clob getClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte getByte(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte getByte(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getBoolean(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean getBoolean(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Blob getBlob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Blob getBlob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean first() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int findColumn(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void deleteRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void close() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearWarnings() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void cancelRowUpdates() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void afterLast() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean absolute(int row) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void setUsername(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setUrl(String url) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setURL(int parameterIndex, URL x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setType(int type) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTransactionIsolation(int level) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(String parameterName, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(int parameterIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(String parameterName, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(int parameterIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(String parameterName, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(int parameterIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(String parameterName, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(int parameterIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRef(int i, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setReadOnly(boolean value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setQueryTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPassword(String password) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType,
					int scale) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType,
					int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int paramIndex, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int parameterIndex, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(String parameterName, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(int parameterIndex, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxRows(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxFieldSize(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(String parameterName, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(int parameterIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(String parameterName, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(int parameterIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(String parameterName, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(int parameterIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setEscapeProcessing(boolean enable) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(String parameterName, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(int parameterIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDataSourceName(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setConcurrency(int concurrency) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCommand(String cmd) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int i, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(String parameterName, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(int parameterIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(String parameterName, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(int parameterIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(String parameterName, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(int parameterIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int i, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(String parameterName, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(int parameterIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setArray(int i, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void removeRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isReadOnly() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getUrl() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Map<String, Class<?>> getTypeMap() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getTransactionIsolation() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getQueryTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getMaxRows() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getMaxFieldSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getEscapeProcessing() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getDataSourceName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCommand() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearParameters() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void addRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public void undoUpdate() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoInsert() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoDelete() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public Collection<?> toCollection(String column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void setTableName(String tabName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSyncProvider(String provider) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShowDeleted(boolean b) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPageSize(int size) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMetaData(RowSetMetaData md) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setKeyColumns(int[] keys) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rowSetPopulated(RowSetEvent event, int numRows)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback(Savepoint s) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void restoreOriginal() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void release() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previousPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void populate(ResultSet rs, int startRow) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void populate(ResultSet data) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean nextPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getTableName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SyncProvider getSyncProvider() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getShowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public RowSetWarning getRowSetWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public ResultSet getOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSet getOriginal() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getKeyColumns() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public RowSet createShared() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopySchema() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopyNoConstraints() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopy() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void commit() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean columnUpdated(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean columnUpdated(int idx) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void acceptChanges(Connection con) throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}
			
			public void acceptChanges() throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <T> T getObject(int columnIndex, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getObject(String columnLabel, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	
	    wrset.populate(rset);

	    // 关闭最终的结果集
	    rset.close();

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("RowSet", wrset);

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    log.error(ex);
	    throw new DAOException("数据库操作异常!");
	} finally {
	    try {
		if (prestmt != null) {
		    prestmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
		log.error(ex);
		throw new DAOException("数据库操作异常!");
	    }
	}
	return hashmap;
    }

    /**
     * Informix分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法性能非常高，适合对大数据量数据的分页，但是仅仅针对Informix有效。.
     * 
     * @param sql
     *            SQL语句
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     */
    public Map getINFPageSet(String sql, int pageNo, int pageSize) {

	sql = StringUtil.escapeSQLTags(sql);

	Connection conn = null;
	PreparedStatement prestmt = null;
	HashMap hashmap = null;
	CachedRowSet wrset = null;

	try {
	    String sbak = "";
	    int actualPageNo = 1;

	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }

	    conn = getConnection();

	    // 首先精确的获取记录集的大小
	    prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    ResultSet rset = prestmt.executeQuery();
	    rset.last();
	    int recTotal = rset.getRow();
	    int pageTotal = (recTotal - 1) / pageSize + 1;
	    rset.close();

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = getSql(sql, String.valueOf(actualPageNo * pageSize));

	    prestmt = conn.prepareStatement(sbak, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    rset = prestmt.executeQuery();

	    int num = (actualPageNo - 1) * pageSize;
	    if (num > 0) {
		int i = 0;
		while (rset.next()) {
		    i++;
		    if (i == num) {
			break;
		    }
		}
	    }

	    wrset = new CachedRowSet() {
			
			public void unsetMatchColumn(String[] columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String[] columnNames) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public String[] getMatchColumnNames() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getMatchColumnIndexes() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean wasNull() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void updateTimestamp(String columnLabel, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTimestamp(int columnIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(String columnLabel, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(int columnIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(String columnLabel, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(int columnIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(String columnLabel, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(int columnIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(String columnLabel, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(int columnIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(String columnLabel, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(int columnIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(String columnLabel, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(int columnIndex, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(String columnLabel, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(int columnIndex, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, NClob nClob)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(String columnLabel, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(int columnIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(String columnLabel, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(int columnIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(String columnLabel, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(int columnIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(String columnLabel, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(int columnIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(String columnLabel, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(int columnIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(String columnLabel, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(int columnIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(String columnLabel, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(int columnIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(String columnLabel, boolean x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(int columnIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(String columnLabel, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(int columnIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(String columnLabel, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(int columnIndex, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchSize(int rows) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchDirection(int direction) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean rowUpdated() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowInserted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean relative(int rows) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void refreshRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previous() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean next() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void moveToInsertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void moveToCurrentRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean last() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isClosed() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isBeforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isAfterLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void insertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public SQLWarning getWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getType() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Timestamp getTimestamp(String columnLabel, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Statement getStatement() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public short getShort(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public short getShort(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public SQLXML getSQLXML(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SQLXML getSQLXML(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getRow() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Ref getRef(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Ref getRef(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSetMetaData getMetaData() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getLong(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long getLong(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getHoldability() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchDirection() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Date getDate(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCursorName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getConcurrency() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Clob getClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Clob getClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte getByte(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte getByte(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getBoolean(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean getBoolean(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Blob getBlob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Blob getBlob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean first() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int findColumn(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void deleteRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void close() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearWarnings() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void cancelRowUpdates() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void afterLast() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean absolute(int row) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void setUsername(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setUrl(String url) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setURL(int parameterIndex, URL x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setType(int type) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTransactionIsolation(int level) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(String parameterName, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(int parameterIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(String parameterName, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(int parameterIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(String parameterName, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(int parameterIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(String parameterName, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(int parameterIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRef(int i, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setReadOnly(boolean value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setQueryTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPassword(String password) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType,
					int scale) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType,
					int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int paramIndex, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int parameterIndex, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(String parameterName, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(int parameterIndex, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxRows(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxFieldSize(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(String parameterName, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(int parameterIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(String parameterName, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(int parameterIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(String parameterName, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(int parameterIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setEscapeProcessing(boolean enable) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(String parameterName, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(int parameterIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDataSourceName(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setConcurrency(int concurrency) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCommand(String cmd) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int i, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(String parameterName, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(int parameterIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(String parameterName, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(int parameterIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(String parameterName, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(int parameterIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int i, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(String parameterName, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(int parameterIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setArray(int i, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void removeRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isReadOnly() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getUrl() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Map<String, Class<?>> getTypeMap() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getTransactionIsolation() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getQueryTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getMaxRows() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getMaxFieldSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getEscapeProcessing() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getDataSourceName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCommand() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearParameters() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void addRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public void undoUpdate() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoInsert() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoDelete() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public Collection<?> toCollection(String column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void setTableName(String tabName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSyncProvider(String provider) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShowDeleted(boolean b) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPageSize(int size) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMetaData(RowSetMetaData md) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setKeyColumns(int[] keys) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rowSetPopulated(RowSetEvent event, int numRows)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback(Savepoint s) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void restoreOriginal() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void release() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previousPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void populate(ResultSet rs, int startRow) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void populate(ResultSet data) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean nextPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getTableName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SyncProvider getSyncProvider() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getShowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public RowSetWarning getRowSetWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public ResultSet getOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSet getOriginal() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getKeyColumns() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public RowSet createShared() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopySchema() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopyNoConstraints() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopy() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void commit() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean columnUpdated(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean columnUpdated(int idx) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void acceptChanges(Connection con) throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}
			
			public void acceptChanges() throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <T> T getObject(int columnIndex, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getObject(String columnLabel, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	    wrset.populate(rset);

	    // 关闭最终的结果集
	    rset.close();
	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("RowSet", wrset);

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    log.error(ex);
	    throw new DAOException("数据库操作异常!");
	} finally {
	    try {
		if (prestmt != null) {
		    prestmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
		log.error(ex);
		throw new DAOException("数据库操作异常!");
	    }
	}

	return hashmap;
    }

    /**
     * Informix分页查询 执行传入的SQL语句，根据页面大小以及页码将对应的结果集和分页信息存放到HashMap对象实例中。
     * 本发分页方法性能非常高，适合对大数据量数据的分页，但是仅仅针对Informix有效。.
     * 
     * @param sql
     *            SQL语句
     * @param map
     *            参数map(参数位置,参数值对象)(PreparedStatement)
     * @param pageSize
     *            页面大小
     * @param pageNo
     *            页码
     * @return 返回的是一个HashMap对象: Key:PageTotal 总页数 Key:RecTotal 总记录数 Key:RowSet
     *         分页记录集
     * @throws JDBCException
     */
    public Map getINFPageSet(String sql, Map map, int pageNo, int pageSize) {

	sql = StringUtil.escapeSQLTags(sql);

	Connection conn = null;
	PreparedStatement prestmt = null;
	HashMap hashmap = null;
	CachedRowSet wrset = null;

	try {
	    String sbak = "";
	    int actualPageNo = 1;

	    if (pageSize < 1) {
		throw new DAOException("页面大小(pageSize)不能为0或者为负数！");
	    }

	    conn = getConnection();

	    // 首先精确的获取记录集的大小
	    prestmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    if (map != null && !map.isEmpty()) {
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
		    String numStr = (String) it.next();
		    int num = Integer.parseInt(numStr);
		    prestmt.setObject(num, map.get(numStr));
		}
	    }
	    ResultSet rset = prestmt.executeQuery();
	    rset.last();

	    int recTotal = rset.getRow();
	    int pageTotal = (recTotal - 1) / pageSize + 1;
	    rset.close();

	    // 获得实际有效的页码
	    actualPageNo = pageNo < 1 ? 1 : (pageNo > pageTotal ? pageTotal : pageNo);

	    sbak = getSql(sql, String.valueOf(actualPageNo * pageSize));

	    prestmt = conn.prepareStatement(sbak, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    if (map != null && !map.isEmpty()) {
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
		    String numStr = (String) it.next();
		    int num = Integer.parseInt(numStr);
		    prestmt.setObject(num, map.get(numStr));
		}
	    }
	    rset = prestmt.executeQuery();
	    int numtmp = (actualPageNo - 1) * pageSize;
	    if (numtmp > 0) {
		int i = 0;
		while (rset.next()) {
		    i++;
		    if (i == numtmp) {
			break;
		    }
		}
	    }
	    wrset = new CachedRowSet() {
			
			public void unsetMatchColumn(String[] columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void unsetMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String[] columnNames) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int[] columnIdxes) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMatchColumn(int columnIdx) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public String[] getMatchColumnNames() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getMatchColumnIndexes() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean wasNull() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void updateTimestamp(String columnLabel, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTimestamp(int columnIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(String columnLabel, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateTime(int columnIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(String columnLabel, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateString(int columnIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(String columnLabel, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateShort(int columnIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(String columnLabel, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateSQLXML(int columnIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(String columnLabel, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRowId(int columnIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(String columnLabel, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateRef(int columnIndex, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x, int scaleOrLength)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(String columnLabel, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateObject(int columnIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNull(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(String columnLabel, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNString(int columnIndex, String nString)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(String columnLabel, NClob nClob)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateNCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(String columnLabel, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateLong(int columnIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(String columnLabel, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateInt(int columnIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(String columnLabel, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateFloat(int columnIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(String columnLabel, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDouble(int columnIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(String columnLabel, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateDate(int columnIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(String columnLabel, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateClob(int columnIndex, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(String columnLabel, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateCharacterStream(int columnIndex, Reader x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(String columnLabel, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBytes(int columnIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(String columnLabel, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateByte(int columnIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(String columnLabel, boolean x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBoolean(int columnIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(String columnLabel, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBlob(int columnIndex, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBinaryStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(String columnLabel, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateBigDecimal(int columnIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(String columnLabel, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateAsciiStream(int columnIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(String columnLabel, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void updateArray(int columnIndex, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchSize(int rows) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFetchDirection(int direction) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean rowUpdated() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowInserted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean rowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean relative(int rows) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void refreshRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previous() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean next() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void moveToInsertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void moveToCurrentRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean last() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isClosed() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isBeforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isAfterLast() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void insertRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public SQLWarning getWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getUnicodeStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public URL getURL(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getType() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Timestamp getTimestamp(String columnLabel, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Timestamp getTimestamp(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Time getTime(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Statement getStatement() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public short getShort(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public short getShort(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public SQLXML getSQLXML(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SQLXML getSQLXML(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public RowId getRowId(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getRow() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Ref getRef(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Ref getRef(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex, Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObject(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNString(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public NClob getNClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getNCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSetMetaData getMetaData() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getLong(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long getLong(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getInt(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getHoldability() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloat(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFetchDirection() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDouble(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Date getDate(String columnLabel, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex, Calendar cal) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Date getDate(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCursorName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getConcurrency() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Clob getClob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Clob getClob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte[] getBytes(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public byte getByte(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte getByte(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getBoolean(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean getBoolean(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Blob getBlob(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Blob getBlob(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getBinaryStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex, int scale)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Array getArray(int columnIndex) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean first() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int findColumn(String columnLabel) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void deleteRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void close() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearWarnings() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void cancelRowUpdates() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeFirst() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void afterLast() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean absolute(int row) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void setUsername(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setUrl(String url) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setURL(int parameterIndex, URL x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setType(int type) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTransactionIsolation(int level) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(String parameterName, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTimestamp(int parameterIndex, Timestamp x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(String parameterName, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setTime(int parameterIndex, Time x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(String parameterName, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setString(int parameterIndex, String x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(String parameterName, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShort(int parameterIndex, short x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(String parameterName, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSQLXML(int parameterIndex, SQLXML xmlObject)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(String parameterName, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRowId(int parameterIndex, RowId x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setRef(int i, Ref x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setReadOnly(boolean value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setQueryTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPassword(String password) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType,
					int scale) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType,
					int scaleOrLength) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x, int targetSqlType)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(int parameterIndex, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObject(String parameterName, Object x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int paramIndex, int sqlType, String typeName)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(String parameterName, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNull(int parameterIndex, int sqlType) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(String parameterName, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNString(int parameterIndex, String value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(int parameterIndex, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNClob(String parameterName, NClob value) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(String parameterName, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setNCharacterStream(int parameterIndex, Reader value)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxRows(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMaxFieldSize(int max) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(String parameterName, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLong(int parameterIndex, long x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(String parameterName, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setInt(int parameterIndex, int x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(String parameterName, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloat(int parameterIndex, float x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setEscapeProcessing(boolean enable) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(String parameterName, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDouble(int parameterIndex, double x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x, Calendar cal)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(String parameterName, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDate(int parameterIndex, Date x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDataSourceName(String name) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setConcurrency(int concurrency) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCommand(String cmd) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(String parameterName, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int parameterIndex, Reader reader) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setClob(int i, Clob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader,
					int length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(String parameterName, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setCharacterStream(int parameterIndex, Reader reader)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(String parameterName, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBytes(int parameterIndex, byte[] x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(String parameterName, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByte(int parameterIndex, byte x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(String parameterName, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBoolean(int parameterIndex, boolean x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream,
					long length) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(String parameterName, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int parameterIndex, InputStream inputStream)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBlob(int i, Blob x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBinaryStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(String parameterName, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBigDecimal(int parameterIndex, BigDecimal x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x, int length)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(String parameterName, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setAsciiStream(int parameterIndex, InputStream x)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setArray(int i, Array x) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void removeRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isReadOnly() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getUrl() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Map<String, Class<?>> getTypeMap() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getTransactionIsolation() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getQueryTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getMaxRows() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getMaxFieldSize() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getEscapeProcessing() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getDataSourceName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getCommand() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearParameters() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void addRowSetListener(RowSetListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public void undoUpdate() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoInsert() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void undoDelete() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public Collection<?> toCollection(String column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Collection<?> toCollection() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int size() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void setTableName(String tabName) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setSyncProvider(String provider) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShowDeleted(boolean b) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setPageSize(int size) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setMetaData(RowSetMetaData md) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void setKeyColumns(int[] keys) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rowSetPopulated(RowSetEvent event, int numRows)
					throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback(Savepoint s) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void rollback() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void restoreOriginal() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void release() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean previousPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void populate(ResultSet rs, int startRow) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public void populate(ResultSet data) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean nextPage() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getTableName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public SyncProvider getSyncProvider() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getShowDeleted() throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public RowSetWarning getRowSetWarnings() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public ResultSet getOriginalRow() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public ResultSet getOriginal() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int[] getKeyColumns() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void execute(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public RowSet createShared() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopySchema() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopyNoConstraints() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public CachedRowSet createCopy() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void commit() throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean columnUpdated(String columnName) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean columnUpdated(int idx) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void acceptChanges(Connection con) throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}
			
			public void acceptChanges() throws SyncProviderException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public <T> T getObject(int columnIndex, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getObject(String columnLabel, Class<T> type)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	    wrset.populate(rset);

	    // 关闭最终的结果集
	    rset.close();

	    // 组织返回的HashMap
	    hashmap = new HashMap();
	    hashmap.put("PageTotal", new Integer(pageTotal));
	    hashmap.put("RecTotal", new Integer(recTotal));
	    hashmap.put("RowSet", wrset);

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    log.error(ex);
	    throw new DAOException("数据库操作异常!");
	} finally {
	    try {
		if (prestmt != null) {
		    prestmt.close();
		}
		if (conn != null) {
		    conn.close();
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
		log.error(ex);
		throw new DAOException("数据库操作异常!");
	    }
	}
	return hashmap;
    }

    /**
     * 仅仅针对Informix有效.
     */
    private String getSql(String sql, String endNo) {
	String returnSql = "";
	if (sql.toUpperCase().indexOf("SELECT") != -1) {
	    int i = sql.toUpperCase().indexOf("SELECT");
	    returnSql = "SELECT FIRST " + endNo + "  " + sql.substring(i + 6, sql.length());
	}
	return returnSql;
    }
    
    /**
     * sql传入sqlserver方法处理
     * @param sql
     * @return 
     * @author 王文杰
     * @update Dec 4, 2012 9:34:53 AM
     */
    private String[] sqlHandle(String sql){
    	String[] sqls = new String[2];
    	int iSort = sql.lastIndexOf("order by");
	    sqls[1] = "order by tempcolumn";
		if(iSort >= 0){
			sqls[1] = sql.substring(iSort);
			sqls[0] = sql.substring(0, iSort);
			//判断非法别名字段
			int iPoint = sqls[1].lastIndexOf(".");
			if (iPoint >= 0) {
				log.info("sql语句中order by语法不能包含别名字段！  SQL: " +sql);
				throw new DAOException("sql语句中order by语法不能包含别名字段！  SQL: " +sql);
			}
			//判断子查询中包含非法order by
			int iIllegal =  sqls[0].indexOf("order by");
			if (iIllegal >= 0) {
				log.info("sql语句中子查询不得包含order by语法！  SQL: " +sql);
				throw new DAOException("sql语句中子查询不得包含order by语法！  SQL: " +sql);
			}
		}
		return sqls;
    }
}
