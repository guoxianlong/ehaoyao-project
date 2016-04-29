package com.haoys.logisticsServer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DBUtils {
	static Logger logger = Logger.getLogger(DBUtils.class);
	static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

	/**
	 * 获取物流中心数据库连接
	 * 
	 * @param setAutoCommit
	 *            是否开启手动提交事物true：开启 false：不开启
	 * @return
	 */
	public static Connection getLogisticsConnection(Boolean setAutoCommit) {
		Connection conn = null;

		try {
			Class.forName(bundle.getString("driverClassName"));
			conn = DriverManager.getConnection(bundle.getString("logisticsUrl"), bundle
					.getString("logisticsUsername"), bundle.getString("logisticsPassword"));
		} catch (Exception e) {
			logger.error("物流中心主库获取数据库连接失败");
			e.printStackTrace();
		}
		if (null != conn && setAutoCommit) {
			setAutoCommit(conn);
		}
		return conn;
	}
	/**
	 * 获取物流中心热备数据库连接
	 * 
	 * @param setAutoCommit
	 *            是否开启手动提交事物true：开启 false：不开启
	 * @return
	 */
	public static Connection getLogisticsBackupConnection(Boolean setAutoCommit) {
		Connection conn = null;

		try {
			Class.forName(bundle.getString("driverClassName"));
			conn = DriverManager.getConnection(bundle.getString("logisticsBackupUrl"), bundle
					.getString("logisticsBackupUsername"), bundle.getString("logisticsBackupPassword"));
			if (null != conn && setAutoCommit) {
				setAutoCommit(conn);
			}
		} catch (Exception e) {
			logger.error("物流中心热备库获取数据库连接失败");
			conn=null;
			conn=getLogisticsConnection(setAutoCommit);
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 获取订单中心数据库连接
	 * 
	 * @param setAutoCommit
	 *            是否开启手动提交事物true：开启 false：不开启
	 * @return
	 */
	public static Connection getOrderConnection(Boolean setAutoCommit) {
		Connection conn = null;

		try {
			Class.forName(bundle.getString("driverClassName"));
			conn = DriverManager.getConnection(bundle.getString("orderUrl"), bundle
					.getString("orderUsername"), bundle.getString("orderPassword"));
		} catch (Exception e) {
			logger.error("订单中心主库获取数据库连接失败");
			e.printStackTrace();
		}
		if (null != conn && setAutoCommit) {
			setAutoCommit(conn);
		}
		return conn;
	}
	/**
	 * 获取订单中心热备数据库连接
	 * 
	 * @param setAutoCommit
	 *            是否开启手动提交事物true：开启 false：不开启
	 * @return
	 */
	public static Connection getOrderBackupConnection(Boolean setAutoCommit) {
		Connection conn = null;

		try {
			Class.forName(bundle.getString("driverClassName"));
			conn = DriverManager.getConnection(bundle.getString("orderBackupUrl"), bundle
					.getString("orderBackupUsername"), bundle.getString("orderBackupPassword"));
			if (null != conn && setAutoCommit) {
				setAutoCommit(conn);
			}
		} catch (Exception e) {
			logger.error("订单中心热备库获取数据库连接失败");
			conn=null;
			conn=getOrderConnection(setAutoCommit);
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/***
	 * 数据操作
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(Connection conn, String sql,
			Object... params) {
		int count = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			sql=null;
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			params=null;
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.info("数据操作失败");
			e.printStackTrace();
		} finally {
			closeAll(pstmt, null, null);
		}
		return count;
	}

	/***
	 * 数据查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet executeQuery(Connection con, String sql,
			Object... params) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			sql=null;
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			params=null;
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			logger.info("executeQuery查询失败");
			e.printStackTrace();
		}
		return rs;
	}

	// 用于事物提交的方法
	public static void commitTrans(Connection con) {

		try {
			logger.info("开始提交事物");
			con.commit();
		} catch (Exception e) {
			try {
				logger.info("事物提交失败，开始回滚");
				con.rollback();
			} catch (SQLException e1) {
				logger.info("事物回滚失败");
				e1.printStackTrace();
			}

		}
	}

	public static void setAutoCommit(Connection con) {
		try {
			logger.info("关闭事物自动提交");
			con.setAutoCommit(false);
		} catch (SQLException e) {
			logger.info("事物自动提交关闭失败");
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			logger.info("事物回滚失败");
			e.printStackTrace();
		}

	}

	/***
	 * 关闭Statement、ResultSet、Connection连接
	 * 
	 * @param stmt
	 * @param con
	 * @param rs
	 */
	public static void closeAll(Statement stmt, Connection con, ResultSet rs) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.info("Statement关闭失败");
		}
		try {
			if (con != null) {
				con.close();
				logger.info("物流中心的Connection关闭成功");
			}
		} catch (SQLException e) {
			logger.info("物流中心的Connection关闭失败");
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			logger.info("ResultSet关闭失败");
		}
	}
}
