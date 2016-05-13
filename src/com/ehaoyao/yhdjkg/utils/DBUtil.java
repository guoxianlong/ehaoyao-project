package com.ehaoyao.yhdjkg.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBUtil {
	private static final Logger logger = Logger.getLogger(DBUtil.class);
	private static String db_driver;
	private static String db_url;
	private static String db_userName;
	private static String db_passWord;
	static{
		Properties pro = new Properties();
		try {    
			String resource = "config/jdbc.properties";
	        InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(resource);
	        pro.load(is);
		} catch (FileNotFoundException e) {
			logger.info("Can't find the properties ! Error:  "+e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(e);
			e.printStackTrace();
		}
		db_driver = pro.getProperty("jdbc.driverClassName");
		db_url = pro.getProperty("jdbc.url");
		db_userName = pro.getProperty("jdbc.username");
		db_passWord = pro.getProperty("jdbc.password");
	}
	
	
	/***
	 * 执行修改sql
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql,Object ...params ){
		int count=0; 
		Connection con = null;
		PreparedStatement pstmt=null;
		
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			count=pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Connection事物回滚失败");
			}
			logger.info("数据修改失败，回滚事物");
		}finally{
			closeAll(con,pstmt,null);
		}
		return count;
		
	}

	/**
	 * 执行查询sql
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet executeQuery(Connection conn ,PreparedStatement ps,Object ... params){
		ResultSet rs = null;
		try {
			for(int i=0;i<params.length;i++){
				ps.setObject(i+1, params[i]);
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("executeQuery查询失败");
		}
		return rs;
	}

	/**
	 * 关闭连接
	 * @param stmt
	 * @param con
	 * @param rs
	 */
	public static void closeAll(Connection con,Statement stmt,ResultSet rs){
		try {
			if(stmt!=null){
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("Statement关闭失败");
		}
		try {
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			logger.error("Connection关闭失败");
		}
		try {
			if(rs!=null){
				rs.close();
			}
		} catch (Exception e) {
			logger.error("ResultSet关闭失败");
		}
	}

	/**
	 * 传入订单编号和订单标示，判断订单是否存在
	 * @param orderNum
	 * @param orderFlag
	 * @return
	 */
	public static boolean checkOrder(String orderNum ,String orderFlag){
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select order_number from order_info where order_number = ? and order_flag = ? ";
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, orderNum);
			ps.setString(2, orderFlag);
			rs = ps.executeQuery();
			if( rs.next() ){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("通过订单号和订单标示查询数据库信息出错,查询sql:" + sql);
		}finally{
			DBUtil.closeAll( con,ps, rs);
		}		
		return flag;
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(db_driver);
		Connection conn = DriverManager.getConnection(db_url,db_userName,db_passWord);
		return conn;
	}
	
	/**
	 * 查询最后一条订单开始时间
	 * @return
	 */
	public static String queryOrderStartTime() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String startTime = "";
		try {
			String sql = "SELECT start_time FROM order_info oi WHERE order_flag='yhdcfy' and order_status='s00' ORDER BY start_time DESC LIMIT 0,1";
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = DBUtil.executeQuery(con, ps);
			while (rs.next()) {
				startTime = rs.getString(1);
			}
		} catch (Exception e) {
			logger.info("数据库最后一条订单开始时间获取失败");
		} finally {
			DBUtil.closeAll(con,ps, rs );
		}
		return startTime;
	}

}
