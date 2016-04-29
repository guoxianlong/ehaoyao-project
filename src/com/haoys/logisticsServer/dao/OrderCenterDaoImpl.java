package com.haoys.logisticsServer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.dao.spi.OrderCenterDao;

import com.haoys.logisticsServer.utils.DBUtils;
import com.haoys.logisticsServer.vo.OrderNumberVo;
import com.haoys.logisticsServer.vo.OrderVo;
import com.haoys.logisticsServer.vo.WXSendInfoVo;

public class OrderCenterDaoImpl implements OrderCenterDao {
	static Logger logger = Logger.getLogger(logisticsDaoImpl.class);

	/**
	 * 获取符合条件的数据的总数量
	 * 
	 * @param con
	 * @param date
	 * @return
	 */
	public Integer getCountofTrackNumInfo(Connection con, String startDate,
			String endDate) {
		String sql = "SELECT count(id) as count FROM express_info e WHERE e.start_time>'"
				+ startDate
				+ "' AND e.start_time<'"
				+ endDate
				+ "' AND e.express_id!=''";
		 logger.info("获取订单中心快递单号总数量：" + sql);
		Integer count = 0;
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			if (rs.next()) {
				count = rs.getInt("count");
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

		return count;
	}

	/**
	 * 获取大于指定日期内的说有快递单号信息
	 * 
	 * @param con
	 * @param date
	 * @return
	 */
	public List<OrderVo> getTrackNumInfo(Connection con, String startDate,
			String endDate, int index, int count) {

		List<OrderVo> orderVo = new ArrayList<OrderVo>();
		String sql = "SELECT express_id,express_com_id,express_com_code,express_com_name,start_time,order_number,order_flag FROM express_info  WHERE start_time>'"
				+ startDate
				+ "' AND start_time<'"
				+ endDate
				+ "' AND express_id is not NULL AND express_id!='' limit "
				+ index + "," + count + "";
		// logger.info("获取订单中心快递单号：" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				String expressId = rs.getString("express_id");
				if (null != expressId) {
					expressId = expressId.trim();
				}
				String expressComId = rs.getString("express_com_id");
				if (null != expressComId) {
					expressId = expressId.trim();
				}
				String expressComCode = rs.getString("express_com_code");
				if (null != expressComCode) {
					expressComCode = expressComCode.trim();
				}
				String expressComName = rs.getString("express_com_name");
				if (null != expressComName) {
					expressComName = expressComName.trim();
				}
				String orderNumber = rs.getString("order_number");
				if (null != orderNumber) {
					orderNumber = orderNumber.trim();
				}
				String orderFlag = rs.getString("order_flag");
				if (null != orderFlag) {
					orderFlag = orderFlag.trim();
				}
				orderVo.add(new OrderVo(expressId, expressComId,
						expressComCode, expressComName, rs
								.getString("start_time"), orderNumber,
						orderFlag));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.info("ResultSet出现异常");
			e.printStackTrace();
		}
		return orderVo;
	}

	/***
	 * 快递签收，修改相应订单状态为s03
	 */
	public Integer writeBackOrderCenter(Connection con, String trackNumber,
			String date) {
		String sql = "UPDATE order_info oi,express_info ei SET oi.order_status='s03',oi.expire_time='"
				+ date
				+ "' WHERE oi.order_number=ei.order_number AND ei.express_id='"
				+ trackNumber + "'";
		// logger.info("快递签收，修改相应订单状态为s03：" + sql);
		Integer boo = DBUtils.executeUpdate(con, sql);
		return boo;
	}

	public List<OrderVo> getExpressInfo(Connection con, String orderNum,
			String orderFlags) {
		List<OrderVo> orderVo = new ArrayList<OrderVo>();
		String sql = "SELECT express_id,express_com_id,express_com_code,express_com_name  FROM express_info_remove  WHERE order_number='"
				+ orderNum
				+ "' AND order_flag='"
				+ orderFlags
				+ "' and express_id!=''";
		// logger.info("获取拆单物流数据："+sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				String expressId = rs.getString("express_id");
				if (null != expressId) {
					expressId = expressId.trim();
				}
				String expressComId = rs.getString("express_com_id");
				if (null != expressComId) {
					expressId = expressId.trim();
				}
				String expressComCode = rs.getString("express_com_code");
				if (null != expressComCode) {
					expressComCode = expressComCode.trim();
				}
				String expressComName = rs.getString("express_com_name");
				if (null != expressComName) {
					expressComName = expressComName.trim();
				}
				orderVo.add(new OrderVo(expressId, expressComId,
						expressComCode, expressComName));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.info("ResultSet出现异常");
			e.printStackTrace();
		}
		return orderVo;
	}

	public List<OrderNumberVo> getTrackNumberAndFlag(Connection con,
			String startDate, String endDate) {
		List<OrderNumberVo> orderList = new ArrayList<OrderNumberVo>();
		String sql = "SELECT  DISTINCT eir.order_number,eir.order_flag,oi.start_time FROM order_info oi,express_info_remove eir WHERE oi.order_number=eir.order_number AND oi.order_flag=eir.order_flag AND oi.start_time>'"
				+ startDate + "' AND oi.start_time<'" + endDate + "'";
		// logger.info("获取拆单订单号："+sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				OrderNumberVo order = new OrderNumberVo();
				order.setOrderNumber(rs.getString("order_number"));
				order.setOrderFlag(rs.getString("order_flag"));
				order.setStartTime(rs.getString("start_time"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
	}

	public List<OrderVo> getAnOrderTrackInfo(Connection con, String startDate,
			String endDate, int index, int count) {

		List<OrderVo> orderVo = new ArrayList<OrderVo>();
		String sql = "SELECT express_id,express_com_id,express_com_code,express_com_name,delivery_date FROM express_info_unorder  WHERE delivery_date>'"
				+ startDate
				+ "' AND delivery_date<'"
				+ endDate
				+ "' AND express_id is not NULL AND express_id!='' limit "
				+ index + "," + count + "";
		// logger.info("获取订单中心快递单号：" + sql);
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			while (rs.next()) {
				String expressId = rs.getString("express_id");
				if (null != expressId) {
					expressId = expressId.trim();
				}
				String expressComId = rs.getString("express_com_id");
				if (null != expressComId) {
					expressId = expressId.trim();
				}
				String expressComCode = rs.getString("express_com_code");
				if (null != expressComCode) {
					expressComCode = expressComCode.trim();
				}
				String expressComName = rs.getString("express_com_name");
				if (null != expressComName) {
					expressComName = expressComName.trim();
				}

				String orderFlag = "AnOrder";

				orderVo.add(new OrderVo(expressId, expressComId,
						expressComCode, expressComName, rs
								.getString("delivery_date"), orderFlag));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.info("ResultSet出现异常");
			e.printStackTrace();
		}
		return orderVo;

	}

	public Integer getCountofAnOrderTrackInfo(Connection con, String startDate,
			String endDate) {

		String sql = "SELECT count(id) as count FROM express_info_unorder  WHERE delivery_date>'"
				+ startDate
				+ "' AND delivery_date<'"
				+ endDate
				+ "' AND express_id!=''";
		// logger.info("获取订单中心快递单号总数量：" + sql);
		Integer count = 0;
		ResultSet rs = DBUtils.executeQuery(con, sql);
		try {
			if (rs.next()) {
				count = rs.getInt("count");
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

		return count;

	}

	public WXSendInfoVo getSendWXInfo(Connection conn, String trackNumber) {
		String sql = "SELECT oi.order_number,oi.order_flag,oi.mobile,oi.receiver,oi.start_time,ei.pay_type FROM order_info oi,express_info ei WHERE  ei.order_number=oi.order_number and ei.order_flag=oi.order_flag AND ei.express_id=?";
		ResultSet rs = DBUtils.executeQuery(conn, sql, trackNumber);
		try {
			if (rs.next()) {
				WXSendInfoVo wxInfo = new WXSendInfoVo();
				wxInfo.setOrderNumber(rs.getString("order_number"));
				wxInfo.setOrderFlag(rs.getString("order_flag"));
				wxInfo.setMobile(rs.getString("mobile"));
				wxInfo.setReceiver(rs.getString("receiver"));
				wxInfo.setStartTime(rs.getString("start_time"));
				wxInfo.setPayType(rs.getString("pay_type"));
				return wxInfo;
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
		return null;
	}

	public String getProductName(Connection conn, String orderNumber,
			String orderFlag) {
		StringBuffer productName = new StringBuffer();
		String sql = "SELECT product_name FROM order_detail WHERE order_number=? and order_flag=?";
		ResultSet rs = DBUtils.executeQuery(conn, sql, orderNumber, orderFlag);
		int isFirst = 0;
		try {
			while (rs.next()) {
				if (isFirst < 1) {
					productName.append(rs.getString("product_name"));
				} else {
					productName.append(",");
					productName.append(rs.getString("product_name"));
				}
				isFirst++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productName.toString();
	}
}
