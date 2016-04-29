package com.haoys.logisticsServer.ws.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.entity.LogisticsDetail;
import com.haoys.logisticsServer.entity.LogisticsInfo;
import com.haoys.logisticsServer.service.LogisticsServiceImpl;
import com.haoys.logisticsServer.service.spi.LogisticsService;
import com.haoys.logisticsServer.timeTask.config.TaskConfig;
import com.haoys.logisticsServer.utils.SortByReceiptTime;
import com.haoys.logisticsServer.utils.VersionCachePool;
import com.haoys.logisticsServer.vo.ElseExpress;
import com.haoys.logisticsServer.vo.LogisticsTransfer;
import com.haoys.logisticsServer.vo.TrackNumAndSource;

public class LogisticsWebService {
	private static LogisticsService logService = new LogisticsServiceImpl();
	static Logger logger = Logger.getLogger(LogisticsWebService.class);
static String dxUrl="http://192.168.98.201:8090/sms/rule/recOrderSta";
	/**
	 * 按来源获取未完成状态的快递单号
	 * 
	 * @param source
	 *            快递来源公司编码
	 * @return String集合，包含快递单号
	 */
	public static List<String> getTrackNumberBySource(String source) {
		Object obj = VersionCachePool.getObject(source + "_LASTMAXID");
		Integer LASTMAXID = 0;
		Calendar calendar = Calendar.getInstance();
		if (null != obj) {
			LASTMAXID = Integer.parseInt(String.valueOf(obj));
			int dt = TaskConfig.getInteger("SourceNotFirstOperation");
			calendar.add(Calendar.HOUR_OF_DAY, -dt); // 得到dt小时前的时间
		} else {
			logger.info(source + "快递更新首次运行");
			int dt = TaskConfig.getInteger("SourceFirstOperation");
			calendar.add(Calendar.DATE, -dt); // 得到前dt天
		}
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(calendar.getTime());
		List<String> trackNum = logService.queryTrackNumberFromInfo(source,
				date, LASTMAXID);
		return trackNum;
	}

	/**
	 * 获取指定渠道大于date到当前时间减N天内所有未完成的漏单快递单号
	 * 
	 * @param date
	 *            日期时间
	 * @return 包含快递单号的List<String>集合
	 */
	public List<String> getLeakageOfSingle(String source, String date) {

		return logService.getLeakageOfSingle(source, date);
	}

	/**
	 * 获取没有接口和爬虫的快递公司单号及渠道
	 * 
	 * @return ElseExpress的对象集合，包含单号和来源
	 */
	public List<ElseExpress> getElseExpressesTrackNum() {
		/**
		 * 上次运行时最大数据库ID值，默认为0， 从缓存中取source_LASTMAXID为其赋值
		 * */
		Object obj = VersionCachePool.getObject("else_LASTMAXID");
		Integer LASTMAXID = 0;

		Calendar calendar = Calendar.getInstance();
		// 判断缓存中是否有指定渠道LASTMAXID
		if (null != obj) {
			LASTMAXID = Integer.parseInt(String.valueOf(obj));
			int dt = TaskConfig.getInteger("SourceNotFirstOperation");
			calendar.add(Calendar.HOUR_OF_DAY, -dt); // 得到前dt小时
		} else {
			logger.info("没有接口和爬虫的快递更新首次运行");
			int dt = TaskConfig.getInteger("SourceFirstOperation");
			calendar.add(Calendar.DATE, -dt); // 得到前dt天
		}
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(calendar.getTime());
		return logService.queryElseTrackNum(date, LASTMAXID);
	}

	/**
	 * 获取时间大于date小于当前时间减N天内没有接口和爬虫的漏单单号和来源
	 * 
	 * @param date
	 *            日期时间
	 * @return ElseExpress的对象集合，包含单号和来源
	 */
	public List<ElseExpress> getElseLeakageOfSingle(String date) {

		return logService.getElseLeakageOfSingle(date);
	}

	/**
	 * 将快递信息按“来源_单号”的Key形式存入memcached中
	 * 
	 * @param source
	 *            快递来源公司编码
	 * @param trackNum
	 *            快递单号
	 * @param LogisticsDetail
	 *            存有快递信息的LogisticsDetail数组
	 * @return
	 */
	public Integer putMemcached(String source, String trackNum,
			LogisticsDetail[] LogisticsDetail) {
		List<LogisticsDetail> detail = new ArrayList<LogisticsDetail>();
		for (LogisticsDetail lo : LogisticsDetail) {
			detail.add(lo);

		}
		VersionCachePool.putObject(source + "_" + trackNum, detail);
		return 1;
	}

	/**
	 * 将已完成订单从Memcached中取出存入数据库，并从memcached中删除该条信息
	 * 
	 * @param source
	 *            快递来源公司编码
	 * @param trackNum
	 *            快递单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Integer putInfoToDB(String source, String trackNum) {
		List<LogisticsDetail> detailList = (List<LogisticsDetail>) VersionCachePool
				.getObject(source + "_" + trackNum);
		if (null != detailList) {			
			Collections.sort(detailList, new SortByReceiptTime());
			LogisticsDetail detail = detailList.get(detailList.size() - 1);
			String date = detail.getReceiptTime();
			Integer isWriteBack = 0;			
			Integer bool = logService.writeBackOrderCenter(trackNum, date);
			if (bool <= 0) {
				logger.info("快递单号为：" + trackNum + "推送到订单中心失败          " + bool);
			} else {
				isWriteBack = 1;
			}
			Integer boo = logService.insertDetailAndUpdateStatus(trackNum,
					detailList, source, date, isWriteBack);

			if (boo > 0) {
				VersionCachePool.remove(source + "_" + trackNum);
				logger.info(source+"_"+trackNum+"写入数据库成功！！！！！");								
				return boo;
			} else {
				logger.info(source+"_"+trackNum+"写入数据库失败！！！");
				return boo;
			}
		}
		return 0;
	}

	/**
	 * 按单号和来源查询快递信息
	 * 
	 * @param source
	 *            快递来源公司代码
	 * @param trackNumber
	 *            快递单号
	 * @return 包含快递信息的封装对象
	 */
	@SuppressWarnings("unchecked")
	public LogisticsTransfer queryLogisticsDetail(String source,
			String trackNumber) {
		LogisticsTransfer logTransfet = new LogisticsTransfer();
		logTransfet.setTrackNumber(trackNumber);
		logTransfet.setSource(source);
		List<LogisticsDetail> logDetail = (List<LogisticsDetail>) VersionCachePool
				.getObject(source + "_" + trackNumber);
		if (null == logDetail) {
			logger.info("缓存中没有获取到" + source + "__" + trackNumber
					+ "的快递信息，准备从数据库中获取");
			logDetail = logService
					.queryLogisticsDetailByDB(trackNumber, source);
			if (null != logDetail) {
				logTransfet.setStruts(3);
			} else {
				logger.info("没有获取到" + source + "__" + trackNumber + "的快递信息");
			}
		} else {
			logTransfet.setStruts(0);
		}
		LogisticsDetail[] detail;
		if (null != logDetail) {
			Collections.sort(logDetail, new SortByReceiptTime());
			detail = new LogisticsDetail[logDetail.size()];
			int x = 0;
			for (LogisticsDetail log : logDetail) {
				detail[x] = log;
				x++;
			}
			logDetail.clear();
			logDetail = null;
		} else {
			detail = new LogisticsDetail[0];
		}
		logTransfet.setLogisticsDetail(detail);
		return logTransfet;
	}

	/**
	 * 从缓存中移除指定渠道的LastMaxID信息
	 * 
	 * @param source
	 *            快递来源公司代码
	 * @return
	 */
	public Integer removeLastMaxId(String source) {
		logger.info("需要删除快递LastMaxID：" + source + "_LASTMAXID");

		VersionCachePool.remove(source + "_LASTMAXID");
		return 1;
	}

	/**
	 * 查詢当前数据库最大ID值
	 * 
	 * @return
	 */
	public Integer getMaxID() {
		return logService.getMaxID();
	}

	/**
	 * 根据ID查询快递信息
	 * 
	 * @param id
	 * @return
	 */
	public LogisticsInfo[] getLogisticsInfoByID(Integer startId, Integer endId) {
		LogisticsInfo[] logisticsInfo = null;
		List<LogisticsInfo> logInfo = logService.getLogistInfoById(startId,
				endId);

		if (null != logInfo) {
			logisticsInfo = logInfo.toArray(new LogisticsInfo[logInfo.size()]);
		}
		return logisticsInfo;
	}

	/**
	 * 获取已完成快递信息
	 * 
	 * @param source
	 * @param trackNumber
	 * @return
	 */
	public LogisticsTransfer[] queryOverLogisticsDetail(
			TrackNumAndSource[] trackAndSource) {
		LogisticsTransfer[] logTransfer = null;
		if (null != trackAndSource && trackAndSource.length > 0) {
			List<LogisticsTransfer> logisticsTransfers = new ArrayList<LogisticsTransfer>();
			for (TrackNumAndSource trackSource : trackAndSource) {
				LogisticsTransfer logTransfet = new LogisticsTransfer();
				logTransfet.setTrackNumber(trackSource.getTrackNum());
				logTransfet.setSource(trackSource.getSource());
				List<LogisticsDetail> logDetail = logService
						.queryLogisticsDetailByDB(trackSource.getTrackNum(),
								trackSource.getSource());
				LogisticsDetail[] detail = null;
				if (null != logDetail && !logDetail.isEmpty()) {
					logTransfet.setStruts(3);
					Collections.sort(logDetail, new SortByReceiptTime());
					detail = new LogisticsDetail[logDetail.size()];
					int x = 0;
					for (LogisticsDetail log : logDetail) {
						detail[x] = log;
						x++;
					}
					logDetail.clear();
					logDetail = null;
					if (null != detail && detail[0] != null) {
						logTransfet.setLogisticsDetail(detail);
					} else {
						logTransfet.setLogisticsDetail(null);
					}
				}

				logisticsTransfers.add(logTransfet);
			}
			logTransfer = logisticsTransfers
					.toArray(new LogisticsTransfer[logisticsTransfers.size()]);
			logger.info("返回数组对象的长度：" + logTransfer.length);
		}

		return logTransfer;
	}

	/**
	 * 获取未完成快递信息
	 * 
	 * @param source
	 * @param trackNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LogisticsTransfer[] queryAnOverLogisticsDetail(
			TrackNumAndSource[] trackAndSource) {
		logger.info("获取未完成快递信息，所得数组是否为空：");
		logger.info(trackAndSource == null);
		LogisticsTransfer[] logTransfer = null;

		if (null != trackAndSource) {
			logTransfer = new LogisticsTransfer[trackAndSource.length];
			List<LogisticsTransfer> logisticsTransfers = new ArrayList<LogisticsTransfer>();
			for (TrackNumAndSource tas : trackAndSource) {
				LogisticsTransfer logTransfet = new LogisticsTransfer();
				logTransfet.setTrackNumber(tas.getTrackNum());
				logTransfet.setSource(tas.getSource());
				List<LogisticsDetail> logDetail = logService
						.queryLogisticsDetailByDB(tas.getTrackNum(), tas
								.getSource());
				if (null != logDetail && !logDetail.isEmpty()) {
					logger.info(tas.getSource() + "_" + tas.getTrackNum()
							+ "的状态已经修改为已完成了");
					logTransfet.setStruts(3);
				} else {
					logDetail = (List<LogisticsDetail>) VersionCachePool
							.getObject(tas.getSource() + "_"
									+ tas.getTrackNum());
					logTransfet.setStruts(0);
				}
				LogisticsDetail[] detail = null;
				if (null != logDetail && !logDetail.isEmpty()) {
					Collections.sort(logDetail, new SortByReceiptTime());
					detail = new LogisticsDetail[logDetail.size()];
					int x = 0;
					for (LogisticsDetail log : logDetail) {
						detail[x] = log;
						x++;
					}
					logDetail.clear();
					logDetail = null;
				}
				if (null != detail && detail[0] != null) {
					logTransfet.setLogisticsDetail(detail);
				} else {
					logTransfet.setLogisticsDetail(null);
				}

				logisticsTransfers.add(logTransfet);
			}
			if (!logisticsTransfers.isEmpty()) {
				logTransfer = logisticsTransfers
						.toArray(new LogisticsTransfer[logisticsTransfers
								.size()]);
				logger.info("返回数组对象的长度：" + logTransfer.length);
				return logTransfer;
			} else {
				logger.info("返回值为空");
			}
		} else {
			logger.info("所传参数为空！！！");

		}
		return logTransfer;
	}
}
