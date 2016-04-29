package com.haoys.logisticsServer.service.spi;

import java.util.List;

import com.haoys.logisticsServer.entity.LogisticsDetail;
import com.haoys.logisticsServer.entity.LogisticsInfo;
import com.haoys.logisticsServer.vo.ElseExpress;
import com.haoys.logisticsServer.vo.LogisticsTransfer;

public interface LogisticsService {
	/**
	 * 查询指定渠道LastMaxID和NowMaxID之间 指定日期内未完成的快递单号
	 * 
	 * @param source
	 * @return
	 */
	List<String> queryTrackNumberFromInfo(String source, String date,
			Integer LASTMAXID);

	/**
	 * 向t_logistics_detail中插入快递信息并修改t_logistics_info中快递状态
	 * 
	 * @param trackNumber
	 * @param LogisticsDetail
	 * @param source
	 * @param date
	 * @param isWriteBack
	 * @return
	 */
	Integer insertDetailAndUpdateStatus(String trackNumber,
			List<LogisticsDetail> LogisticsDetail, String source, String date,
			Integer isWriteBack);

	/**
	 * 从数据库中查询快递信息(为WEBService服务用)
	 * 
	 * @param trackNumber
	 * @param source
	 * @return
	 */
	List<LogisticsDetail> queryLogisticsDetailByDB(String trackNumber,
			String source);

	/**
	 * 根据快递来源、单号查询快递信息（先从缓存取，没有再从数据库查询）
	 * 
	 * @param trackNumber
	 * @param source
	 * @return
	 */
	LogisticsTransfer queryLogisticsDetailByMemOrDB(String trackNumber,
			String source);

	/**
	 * 获取没有接口爬虫的快递单号
	 * 
	 * @param date
	 *            日期时间
	 * @param LASTMAXID
	 *            上次运行最大ID值
	 * @return
	 */
	List<ElseExpress> queryElseTrackNum(String date, Integer LASTMAXID);

	/**
	 * 回写订单中心
	 * 
	 * @param trackNum
	 * @param date
	 * @return
	 */
	Integer writeBackOrderCenter(String trackNum, String date);

	/**
	 * 从订单中心抓取快递初始数据存入物流中心
	 */
	void getTrackNumToLogistics(String startDate,String endDate);
	/**
	 * 获取指定渠道的快递漏单单号
	 * @param source 快递渠道编码
	 * @param startDate 日期时间
	 * @return
	 */
	List<String> getLeakageOfSingle(String source,String startDate);
	/**
	 * 获取没有接口和爬虫的快递漏单单号及渠道
	 * @param startDate 日期时间
	 * @return
	 */
	List<ElseExpress> getElseLeakageOfSingle(String startDate);
	/**
	 * 获取数据库最大ID
	 * @return
	 */
	Integer getMaxID();
	/**
	 * 通过ID查询快递信息
	 * @param id
	 * @return
	 */
	List<LogisticsInfo> getLogistInfoById(Integer startId,Integer endId);
	
	/**
	 * 从订单中心获取拆单的快递信息
	 * @param startDate
	 * @param endDate
	 */
	void getDevanExpInfo(String startDate,String endDate);
	/**
	 * 物流签收，通知短信平台发送短信
	 * @param trackNumber
	 * @param source
	 */
	void sendOverToDxTerrace(String trackNumber,String source);
	/**
	 * 从订单中心获取无订单号的快递信息
	 * @param startDate
	 * @param endDate
	 */
	void getAnOrderExpInfo(String startDate,String endDate);
	/**
	 * 向微信发送已发货、已签收通知
	 * @param trackNumber
	 * @param stauts
	 */
	void sendStautsToWX(String trackNumber,String source,String stauts);
	/**
	 * 获取快递公司的快递100英文编码
	 * @param expressComName
	 * @param expressComCode
	 * @param expressComID
	 * @return
	 */
	String getSource(String expressComName,String expressComCode,String expressComID);
}
