package com.haoys.logisticsServer.dao.spi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.haoys.logisticsServer.entity.LogisticsDetail;
import com.haoys.logisticsServer.entity.LogisticsInfo;
import com.haoys.logisticsServer.vo.ElseExpress;
import com.haoys.logisticsServer.vo.ExpressVo;
import com.haoys.logisticsServer.vo.OrderNumberVo;

public interface LogisticsDao {

	/**
	 * 向t_logistics_info表里插入初始数据
	 * 
	 * @param con
	 * @param logisticsInfo
	 * @return
	 */
	Integer insertLogisticsInfo(Connection con, LogisticsInfo logisticsInfo);

	/**
	 * 查询当前数据库最大ID值
	 * 
	 * @param con
	 * @return
	 */
	Integer queryMaxIdFromInfo(Connection con);

	/**
	 * 查询相应渠道下lastMaxID和nowMaxID之间 大于指定日期的所有未完成快递单号
	 * 
	 * @param con
	 * @param lastMaxId
	 * @param nowMaxId
	 * @param source
	 * @param date
	 * @return
	 */
	List<String> queryTrackNumberFromInfo(Connection con, int lastMaxId,
			int nowMaxId, String source, String date);

	/**
	 * 将完成状态的快递信息插入数据库中
	 * 
	 * @param con
	 * @param LogisticsDetail
	 * @return
	 */
	Integer insertLogisticsDetail(Connection con,
			LogisticsDetail logisticsDetail);

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
	Integer updateIsWriteBack(Connection con, String trackNumber,
			String source, String date, Integer isWriteBack);

	/**
	 * 按快递单号和来源查询快递信息
	 * 
	 * @param con
	 * @param trackNumber
	 * @param source
	 * @return
	 */
	List<LogisticsDetail> queryDetailbyTrackNumAndSource(Connection con,
			String trackNumber, String source);

	/**
	 * 通过快递单号查询数据库，判断数据中是否有此单号信息，用来去重
	 * 
	 * @param con
	 * @param trackNum
	 * @return 1存在 0为不存在
	 * @throws SQLException 
	 */
	public Integer queryByTrackNum(Connection con, String trackNum) throws SQLException;

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
			Integer lastMaxId, Integer nowMaxId, String date);

	/**
	 * 用快递单号从t_logistics_detal表中查询ID，用来去重  
	 * 
	 * @param con
	 *            Connection对象
	 * @param trackNum
	 *            快递单号
	 * @return 1存在 0为不存在
	 */
	Integer queryIDbyTrackNum(Connection con, String trackNum);

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
	List<String> queryLeakageOfSingle(Connection con, String source,
			String startDate, String endDate);

	/**
	 * 获取没有接口和爬虫的快递的漏单方法
	 * 
	 * @param con
	 *            Connection对象
	 * @param date
	 *            日期时间
	 * @return
	 */
	List<ElseExpress> queryElseLeakageOfSingle(Connection con,
			String startDate, String endDate);
	/**
	 * 根据ID查询物流信息
	 * @param con
	 * @param id
	 * @return
	 */
	List<LogisticsInfo> queryLogisticsInfoById(Connection con, Integer startId,Integer endId);
	/**
	 * 根据快递单号查询订单号和渠道
	 * */
	OrderNumberVo queryOrderNumberAndFlag(Connection con,String trackNumber);
	/**
	 * 根据订单号和渠道查询所对应的快递单号和快递完成状态
	 * */
	List<ExpressVo> queryTrackNumAndStatus(Connection con,String orderNumber,String orderFlag);
	/**
	 * 通过物流单号、快递公司code获取订单号和订单渠道
	 * @param con
	 * @param trackNum
	 * @param source
	 * @return
	 * @throws SQLException 
	 */
	OrderNumberVo getOrderNumberAndFlag(Connection con,String trackNum,String source) throws SQLException;
	/**
	 * 通过快递单号和渠道查询是否存在订单信息
	 * 用来进行二次去重，1：已存在 0：不存在
	 * @param con
	 * @param trackNum
	 * @param source
	 * @return
	 * @throws SQLException 
	 */
	Integer qureyOrderNumberByTrack(Connection con,String trackNum,String source) throws SQLException;
	/**
	 * 通过运单号和快递公司名称修改快递信息，添加订单信息
	 * @param con
	 * @param orderNumber
	 * @param orderFlag
	 * @param trackNum
	 * @param source
	 * @return
	 */
	Integer updateTrackInfoToSetOrderInfo(Connection con,String orderNumber,String orderFlag,String startTime,String trackNum,String source);
}
