package com.haoys.logisticsServer.dao.spi;

import java.sql.Connection;

import java.util.List;

import com.haoys.logisticsServer.vo.OrderNumberVo;
import com.haoys.logisticsServer.vo.OrderVo;
import com.haoys.logisticsServer.vo.WXSendInfoVo;

public interface OrderCenterDao {
	/**
	 * 获取符合条件的数据的总数量
	 * @param con
	 * @param date
	 * @return
	 */
	Integer getCountofTrackNumInfo(Connection con,String startDate,String endDate);
	
	/**
	 * 获取大于指定日期范围内的所有快递单号信息
	 * 
	 * @param con
	 * @param date
	 * @return
	 */
	List<OrderVo> getTrackNumInfo(Connection con,String startDate,String endDate,int index,int count);
	
	/**
	 * 更新订单状态，回写订单结束时间
	 * 
	 * @param con
	 * @param trackNumber
	 * @param date
	 * @return
	 */
	Integer writeBackOrderCenter(Connection con, String trackNumber, String date);
	/**
	 * 1、获取指定时间段内所有拆单订单号、渠道	 
	 * @param con
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<OrderNumberVo> getTrackNumberAndFlag(Connection con,String startDate,String endDate);
	/**
	 * 2、根据单号和渠道获取所有符合条件的快递信息
	 * @param con
	 * @param orderNumber
	 * @param orderFlag
	 * @return
	 */
	List<OrderVo> getExpressInfo(Connection con,String orderNumber,String orderFlag);
	
	/**
	 * 获取符合条件的无订单数据的总数量
	 * @param con
	 * @param date
	 * @return
	 */
	Integer getCountofAnOrderTrackInfo(Connection con,String startDate,String endDate);
	
	/**
	 * 获取大于指定日期范围内的所有无订单的快递单号信息
	 * 
	 * @param con
	 * @param date
	 * @return
	 */
	List<OrderVo> getAnOrderTrackInfo(Connection con,String startDate,String endDate,int index,int count);
	/**
	 * 通过快递运单号获取订单号、订单渠道、收货人手机号码
	 * @param conn
	 * @param trackNumber
	 * @return
	 */
	WXSendInfoVo getSendWXInfo(Connection conn,String trackNumber);
	/**
	 * 通过订单好和渠道获取所有商品名称
	 * @param conn
	 * @param orderNumber
	 * @return
	 */
	String getProductName(Connection conn,String orderNumber,String orderFlag);
}
