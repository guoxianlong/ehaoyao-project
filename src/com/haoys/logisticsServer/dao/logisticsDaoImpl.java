package com.haoys.logisticsServer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.dao.spi.LogisticsDao;
import com.haoys.logisticsServer.entity.LogisticsDetail;
import com.haoys.logisticsServer.entity.LogisticsInfo;
import com.haoys.logisticsServer.timeTask.config.TaskConfig;
import com.haoys.logisticsServer.utils.DBUtils;
import com.haoys.logisticsServer.vo.ElseExpress;
import com.haoys.logisticsServer.vo.ExpressVo;
import com.haoys.logisticsServer.vo.OrderNumberVo;

public class logisticsDaoImpl implements LogisticsDao {
	static Logger logger = Logger.getLogger(logisticsDaoImpl.class);

	/**
	 * 向t_logistics_info表里插入初始数据
	 * 
	 * @param con
	 * @param logisticsInfo
	 * @return
	 */
	public Integer insertLogisticsInfo(Connection con,
			LogisticsInfo logisticsInfo) {
		String sql = "insert into t_logistics_info set tracking_number='"
				+ logisticsInfo.getTrackingNumber() + "',track_source='"
				+ logisticsInfo.getSource()
				+ "',track_status='S00',start_time='"
				+ logisticsInfo.getStartTime() + "',order_number='"
				+ logisticsInfo.getOrderNumber() + "',order_flag='"
				+ logisticsInfo.getOrderFlag() + "',is_writeback=0";
		// logger.info("向t_logistics_info表里批量插入初始数据:" + sql);
		Integer boo = DBUtils.executeUpdate(con, sql);
		sql = null;
		logisticsInfo = null;
		return boo;
	}

	/**
	 * 将完成状态的快递信息插入信息表里
	 * 
	 * @param con
	 * @param LogisticsDetail
	 * @return
	 */
	public Integer insertLogisticsDetail(Connection con,
			LogisticsDetail logisticsDetail) {
		String sql = "insert into t_logistics_detal set receipt_time='"
				+ logisticsDetail.getReceiptTime() + "',receipt_address='"
				+ logisticsDetail.getReceiptAddress() + "',context='"
				+ logisticsDetail.getContext() + "',tracking_number='"
				+ logisticsDetail.getTrackingNumber() + "'";
		// logger.info("将完成状态的快递信息插入信息表里:" + sql);
		Integer boo = DBUtils.executeUpdate(con, sql);
		return boo;
	}

	/**
	 * 修改快递回写状态、快递状态为S04（已完结状态）及结束时间
	 * 
	 * @param con
	 * @param trackNumber
	 * @param source
	 * @param date
	 * @param isWriteBack
	 * @return
	 */
	public Integer updateIsWriteBack(Connection con, String trackNumber,
			String source, String date, Integer isWriteBack) {
		String sql = "update t_logistics_info set track_status='S04',end_time='"
				+ date
				+ "',is_writeback="
				+ isWriteBack
				+ " where tracking_number='"
				+ trackNumber
				+ "' and track_source='" + source + "'";
		if (source.equals("ems")) {
			sql = "update t_logistics_info ti set track_status='S04',end_time='"
					+ date
					+ "',is_writeback="
					+ isWriteBack
					+ " where tracking_number='"
					+ trackNumber
					+ "' AND ti.track_source in ('ems','youzhengguonei')";
		}

		Integer boo = DBUtils.executeUpdate(con, sql);
		return boo;
	}

	/**
	 * 查询lastMaxId到nowMaxId内大于指定日期的所有未完成快递单号
	 * 
	 * @param con
	 * @param lastMaxId
	 * @param nowMaxId
	 * @param source
	 * @param date
	 * @return
	 */
	public List<String> queryTrackNumberFromInfo(Connection con, int lastMaxId,
			int nowMaxId, String source, String date) {
		List<String> trackNum = new ArrayList<String>();
		String sql = "select tracking_number from t_logistics_info where id> "
				+ lastMaxId + "  and id<=" + nowMaxId + " and start_time>'"
				+ date + "' AND track_source='" + source
				+ "' and track_status!='S04'";
		if (source.equals("ems")) {
			sql = "select tracking_number from t_logistics_info ti where id>"
					+ lastMaxId
					+ " and id<="
					+ nowMaxId
					+ " and start_time>'"
					+ date
					+ "' AND ti.track_source in ('ems','youzhengguonei') and track_status!='S04'";
		}
		// logger.info("查询lastMaxId到nowMaxId内大于" + date + "的所有未完成" + source
		// + "快递单号:" + sql);

		ResultSet rs = DBUtils.executeQuery(con, sql);

		try {

			while (rs.next()) {

				trackNum.add(rs.getString("tracking_number"));
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return trackNum;
	}

	/**
	 * 获取其他没有接口和爬虫的快递单号和渠道
	 * 
	 * @param con
	 * @param lastMaxId
	 * @param nowMaxId
	 * @param date
	 * @return
	 */
	public List<ElseExpress> queryElseTrackNum(Connection con,
			Integer lastMaxId, Integer nowMaxId, String date) {
		String str = TaskConfig.getString("ExistCompany");
		String[] stb = str.split(",");
		StringBuffer strb = new StringBuffer();
		if (null != stb && stb.length > 0) {
			for (int i = 0; i < stb.length; i++) {
				if (i == 0) {
					strb.append("'");
					strb.append(stb[i]);
					strb.append("'");
				} else {
					strb.append(",'");
					strb.append(stb[i]);
					strb.append("'");
				}

			}
		}
		String sql = "SELECT tracking_number,track_source FROM t_logistics_info tl WHERE  id>"
				+ lastMaxId
				+ " and id<="
				+ nowMaxId
				+ " and start_time>'"
				+ date
				+ "'AND tl.track_source NOT IN ("
				+ strb.toString()
				+ ") AND tl.track_status!='S04'";
		// logger.info("获取其他没有接口和爬虫的快递单号和渠道:" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		List<ElseExpress> elseList = new ArrayList<ElseExpress>();
		try {
			while (rs.next()) {
				ElseExpress elseexp = new ElseExpress();
				elseexp.setTrackNum(rs.getString("tracking_number"));
				elseexp.setSource(rs.getString("track_source"));
				elseList.add(elseexp);
			}
			rs.close();
			return elseList;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return elseList;
	}

	/**
	 * 按快递单号和来源查询快递信息
	 * 
	 * @param con
	 * @param trackNumber
	 * @param source
	 * @return
	 */
	public List<LogisticsDetail> queryDetailbyTrackNumAndSource(Connection con,
			String trackNumber, String source) {
		String sql = "SELECT td.tracking_number,receipt_address,context,receipt_time from t_logistics_info ti,t_logistics_detal td  WHERE ti.track_source='"
				+ source
				+ "' AND ti.tracking_number=td.tracking_number AND td.tracking_number='"
				+ trackNumber + "' GROUP BY td.receipt_time ASC";
		if (null == source || "".equals(source)) {
			sql = "SELECT td.tracking_number,receipt_address,context,receipt_time from t_logistics_info ti,t_logistics_detal td  WHERE ti.tracking_number=td.tracking_number AND td.tracking_number='"
					+ trackNumber + "' GROUP BY td.receipt_time ASC";
		} else if ("ems".equals(source)) {

			sql = "SELECT td.tracking_number,receipt_address,context,receipt_time from t_logistics_info ti,t_logistics_detal td  WHERE ti.tracking_number=td.tracking_number AND td.tracking_number='"
					+ trackNumber
					+ "' AND ti.track_source in ('ems','youzhengguonei') GROUP BY td.receipt_time ASC";

		}
		// logger.info("按快递单号和来源查询快递信息:" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		List<LogisticsDetail> logDetailList = new ArrayList<LogisticsDetail>();
		try {
			while (rs.next()) {
				logDetailList.add(new LogisticsDetail(rs
						.getString("receipt_time"), rs.getString("context"), rs
						.getString("receipt_address"), rs
						.getString("tracking_number")));

			}
			rs.close();
			rs = null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return logDetailList;

	}

	/**
	 * 查询当前数据库最大ID值
	 * 
	 * @param con
	 * @return
	 */
	public Integer queryMaxIdFromInfo(Connection con) {

		String sql = "select MAX(id) FROM t_logistics_info";
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 
	 * 通过快递单号查询数据库，判断数据中是否有此单号信息，用来去重
	 * 
	 * @param con
	 *            Connection对象
	 * @param trackNum
	 *            快递单号
	 * @return 1存在 0为不存在
	 * @throws SQLException
	 */
	public Integer queryByTrackNum(Connection con, String trackNum)
			throws SQLException {
		String sql = "SELECT id from t_logistics_info WHERE tracking_number='"
				+ trackNum + "'";
		ResultSet rs = DBUtils.executeQuery(con, sql);

		if (rs.next()) {
			rs.close();
			return 1;
		}

		return 0;
	}

	/**
	 * 用快递单号从t_logistics_detal表中查询ID，用来去重
	 * 
	 * @param con
	 *            Connection对象
	 * @param trackNum
	 *            快递单号
	 * @return 1存在 0为不存在
	 */
	public Integer queryIDbyTrackNum(Connection con, String trackNum) {
		String sql = "SELECT id from t_logistics_detal WHERE tracking_number='"
				+ trackNum + "'";
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			if (rs.next()) {
				return 1;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 获取指定渠道的指定日期之前的所有未完成快递单号，用于处理漏单
	 * 
	 * @param con
	 *            Connection对象
	 * @param source
	 *            渠道
	 * @param date
	 *            日期
	 * @return
	 */
	public List<String> queryLeakageOfSingle(Connection con, String source,
			String startDate, String endDate) {
		List<String> trackNum = new ArrayList<String>();
		String sql = "select tracking_number from t_logistics_info where start_time>'"
				+ startDate
				+ "' and start_time <'"
				+ endDate
				+ "'AND track_source='" + source + "' and track_status!='S04'";

		if (source.equals("ems")) {
			sql = "select tracking_number from t_logistics_info ti where start_time>'"
					+ startDate
					+ "' and start_time<'"
					+ endDate
					+ "' AND ti.track_source in ('ems','youzhengguonei') and track_status!='S04'";
		}
		/*
		 * logger.info("查询" + startDate + "到" + endDate + "的所有未完成" + source +
		 * "快递单号:" + sql);
		 */
		ResultSet rs = DBUtils.executeQuery(con, sql);

		try {

			while (rs.next()) {

				trackNum.add(rs.getString("tracking_number"));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return trackNum;
	}

	/**
	 * 获取没有接口和爬虫的快递的漏单方法
	 * 
	 * @param con
	 *            Connection对象
	 * @param date
	 *            日期时间
	 * @return
	 */
	public List<ElseExpress> queryElseLeakageOfSingle(Connection con,
			String startDate, String endDate) {

		String str = TaskConfig.getString("ExistCompany");
		String[] stb = str.split(",");
		StringBuffer strb = new StringBuffer();
		if (null != stb && stb.length > 0) {
			for (int i = 0; i < stb.length; i++) {
				if (i == 0) {
					strb.append("'");
					strb.append(stb[i]);
					strb.append("'");
				} else {
					strb.append(",'");
					strb.append(stb[i]);
					strb.append("'");
				}

			}
		}
		String sql = "SELECT tracking_number,track_source FROM t_logistics_info tl WHERE tl.track_source NOT IN ("
				+ strb.toString()
				+ ") AND tl.track_status!='S04' and start_time>'"
				+ startDate
				+ "' and start_time<'" + endDate + "'";
		// logger.info("获取其他没有接口和爬虫的快递单号和渠道:" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		List<ElseExpress> elseList = new ArrayList<ElseExpress>();
		try {
			while (rs.next()) {
				ElseExpress elseexp = new ElseExpress();
				elseexp.setTrackNum(rs.getString("tracking_number"));
				elseexp.setSource(rs.getString("track_source"));
				elseList.add(elseexp);
			}
			rs.close();
			return elseList;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return elseList;
	}

	public List<LogisticsInfo> queryLogisticsInfoById(Connection con,
			Integer startId, Integer endId) {
		List<LogisticsInfo> logistics = new ArrayList<LogisticsInfo>();
		String sql = "select id,tracking_number,track_source,track_status,start_time,end_time,order_number,order_flag"
				+ " from t_logistics_info where id>="
				+ startId
				+ " and id<="
				+ endId;
		// logger.info("根据ID批量获取物流信息：" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				LogisticsInfo logis = new LogisticsInfo();
				logis.setId(rs.getInt("id"));
				logis.setTrackingNumber(rs.getString("tracking_number"));
				logis.setOrderNumber(rs.getString("order_number"));
				logis.setOrderFlag(rs.getString("order_flag"));
				logis.setSource(rs.getString("track_source"));
				logis.setStatus(rs.getString("track_status"));
				logis.setStartTime(rs.getString("start_time"));
				logis.setEndTime(rs.getString("end_time"));
				logistics.add(logis);
			}
			return logistics;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public OrderNumberVo queryOrderNumberAndFlag(Connection con,
			String trackNumber) {
		OrderNumberVo orderVo = null;
		String sql = "SELECT order_number,order_flag FROM t_logistics_info WHERE tracking_number='"
				+ trackNumber + "'";
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				orderVo = new OrderNumberVo();
				orderVo.setOrderNumber(rs.getString("order_number"));
				orderVo.setOrderFlag(rs.getString("order_flag"));
			}
			rs.close();
			rs = null;

		} catch (SQLException e) {
			logger.info("ResultSet操作失败");
			e.printStackTrace();
		}
		return orderVo;
	}

	public List<ExpressVo> queryTrackNumAndStatus(Connection con,
			String orderNumber, String orderFlag) {
		List<ExpressVo> expList = new ArrayList<ExpressVo>();
		String sql = "SELECT tracking_number,track_status FROM t_logistics_info WHERE order_number='"
				+ orderNumber + "' and order_flag='" + orderFlag + "'";
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				ExpressVo expVo = new ExpressVo();
				expVo.setTrackNum(rs.getString("tracking_number"));
				expVo.setStatus(rs.getString("track_status"));
				expList.add(expVo);
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.info("ResultSet操作失败");
			e.printStackTrace();
		}
		return expList;
	}

	public OrderNumberVo getOrderNumberAndFlag(Connection con, String trackNum,
			String source) throws SQLException {
		OrderNumberVo order = null;
		String sql = "SELECT order_number,order_flag FROM t_logistics_info WHERE tracking_number='"
				+ trackNum + "' and track_source='" + source + "'";
		ResultSet rs = DBUtils.executeQuery(con, sql);
		while (rs.next()) {
			order = new OrderNumberVo();
			order.setOrderNumber(rs.getString("order_number"));
			order.setOrderFlag(rs.getString("order_flag"));
		}
		return order;
	}

	public Integer qureyOrderNumberByTrack(Connection con, String trackNum,
			String source) throws SQLException {
		String sql = "SELECT order_number FROM t_logistics_info WHERE tracking_number=? AND track_source=? AND order_number!='' AND order_number IS NOT NULL";
		logger.info("二次校验SQL："+sql);
		ResultSet rs = DBUtils.executeQuery(con, sql, trackNum, source);
		while (rs.next()) {
			return 1;
		}
		return 0;
	}

	public Integer updateTrackInfoToSetOrderInfo(Connection con,
			String orderNumber, String orderFlag,String startTime,String trackNum, String source) {
		String sql = "UPDATE t_logistics_info SET order_number=?,order_flag=?,start_time=? WHERE tracking_number=? AND track_source=?";
		logger.info("更新订单信息SQL："+sql);
		return DBUtils.executeUpdate(con, sql, orderNumber, orderFlag,startTime,
				trackNum, source);

	}
}
