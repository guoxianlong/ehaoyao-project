package com.haoys.logisticsServer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.dao.OrderCenterDaoImpl;
import com.haoys.logisticsServer.dao.logisticsDaoImpl;
import com.haoys.logisticsServer.dao.spi.LogisticsDao;
import com.haoys.logisticsServer.dao.spi.OrderCenterDao;
import com.haoys.logisticsServer.entity.LogisticsDetail;
import com.haoys.logisticsServer.entity.LogisticsInfo;
import com.haoys.logisticsServer.service.spi.LogisticsService;
import com.haoys.logisticsServer.timeTask.config.TaskConfig;
import com.haoys.logisticsServer.utils.DBUtils;
import com.haoys.logisticsServer.utils.EncoderHandler;
import com.haoys.logisticsServer.utils.HttpUtils;
import com.haoys.logisticsServer.utils.PageModel;
import com.haoys.logisticsServer.utils.SortByReceiptTime;
import com.haoys.logisticsServer.utils.VersionCachePool;
import com.haoys.logisticsServer.vo.ElseExpress;
import com.haoys.logisticsServer.vo.LogisticsTransfer;
import com.haoys.logisticsServer.vo.OrderNumberVo;
import com.haoys.logisticsServer.vo.OrderVo;
import com.haoys.logisticsServer.vo.WXSendInfoVo;

public class LogisticsServiceImpl implements LogisticsService {
	private static Logger logger = Logger.getLogger(LogisticsServiceImpl.class);
	private static LogisticsDao logDao = new logisticsDaoImpl();
	private static OrderCenterDao orderDao = new OrderCenterDaoImpl();
	private static ResourceBundle bundle = ResourceBundle.getBundle("express");
	private static ResourceBundle bundle1 = ResourceBundle
			.getBundle("orderCodeToKD100Code");
	private static ResourceBundle bundle2 = ResourceBundle
			.getBundle("expressIDtoKD100Code");
	private String url = TaskConfig.getString("DxUrl");

	/**
	 * 查询指定渠道LastMaxID和NowMaxID之间 指定日期内未完成的快递单号
	 * 
	 * @param source
	 * @return
	 */
	public List<String> queryTrackNumberFromInfo(String source, String date,
			Integer LASTMAXID) {
		Connection con = DBUtils.getLogisticsBackupConnection(false);
		/**
		 * 上次运行时最大数据库ID值，默认为0， 从缓存中取KD_SF_LASTMAXID为其赋值
		 * */

		Integer NOWMAXID = logDao.queryMaxIdFromInfo(con);
		List<String> trackNum = logDao.queryTrackNumberFromInfo(con, LASTMAXID,
				NOWMAXID, source, date);
		VersionCachePool.putObject(source + "_LASTMAXID", NOWMAXID);
		DBUtils.closeAll(null, con, null);
		return trackNum;
	}

	/**
	 * 向t_logistics_detail中插入快递信息并修改t_logistics_info中快递状态
	 * 
	 * @param trackNumber
	 * @param LogisticsDetail
	 * @param source
	 * @param date
	 * @param isWriteBack
	 * @return 3代表数据库中已经存在该订单数据，1表示插入成功 0表示失败
	 */
	public Integer insertDetailAndUpdateStatus(String trackNumber,
			List<LogisticsDetail> logisticsDetail, String source, String date,
			Integer isWriteBack) {
		Integer boo = 0;
		if (null != logisticsDetail && !logisticsDetail.isEmpty()) {
			Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
			Connection con = DBUtils.getLogisticsConnection(true);
			Integer bool = logDao.queryIDbyTrackNum(logBackCon, trackNumber);
			// 判断数据库中是否存在
			if (bool <= 0) {
				for (LogisticsDetail detail : logisticsDetail) {
					boo = logDao.insertLogisticsDetail(con, detail);
					if (boo <= 0) {
						DBUtils.rollback(con);
						DBUtils.closeAll(null, con, null);
					}
				}

				boo = logDao.updateIsWriteBack(con, trackNumber, source, date,
						isWriteBack);
				if (boo <= 0) {
					logger.info("快递信息写入数据库失败，渠道和运单号为" + source + "：：："
							+ trackNumber);
					DBUtils.rollback(con);
				} else {
					DBUtils.commitTrans(con);
					try {
						// 调用短信平台接口，通知短信平台发送短信
						sendOverToDxTerrace(trackNumber, source);

					} catch (Exception e) {
						logger.error("向短信平台发送短信失败！！！！！");
					}
					try {

						// 调用微信接口，通知微信发信息
						sendStautsToWX(trackNumber, source, "已签收");
					} catch (Exception e) {
						logger.error("向微信信平台发送已签收信息失败！！！！！");
					}
				}
			} else {
				// logger.info("数据库中已经存在了" + trackNumber + "的信息");
				DBUtils.closeAll(null, con, null);
				con = null;
				bool = null;
				boo = 3;
			}
			bool = null;
			DBUtils.closeAll(null, logBackCon, null);
			DBUtils.closeAll(null, con, null);
			logBackCon = null;
			con = null;
		}

		return boo;
	}

	// 从数据库中查询快递信息
	public List<LogisticsDetail> queryLogisticsDetailByDB(String trackNumber,
			String source) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		List<LogisticsDetail> detail = logDao.queryDetailbyTrackNumAndSource(
				logBackCon, trackNumber, source);
		DBUtils.closeAll(null, logBackCon, null);
		logBackCon = null;
		return detail;
	}

	/**
	 * 根据快递来源、单号查询快递信息（先从缓存取，没有再从数据库查询）
	 * 
	 * @param trackNumber
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LogisticsTransfer queryLogisticsDetailByMemOrDB(String trackNumber,
			String source) {
		LogisticsTransfer logTransfer = new LogisticsTransfer();
		logTransfer.setTrackNumber(trackNumber);
		List<LogisticsDetail> logDetail = (List<LogisticsDetail>) VersionCachePool
				.getObject(source + "_" + trackNumber);
		if (null == logDetail) {
			Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
			/*
			 * logger.info("缓存中没有关于" + source + "_" + trackNumber +
			 * "的信息，从数据库中查询信息");
			 */
			logDetail = logDao.queryDetailbyTrackNumAndSource(logBackCon,
					trackNumber, source);
			DBUtils.closeAll(null, logBackCon, null);
			if (null != logDetail) {
				logTransfer.setStruts(3);
			} else {
				logger.info("暂时没有关于" + source + "_" + trackNumber + "的信息");
				logTransfer.setStruts(0);
			}
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
		} else {
			detail = new LogisticsDetail[0];
		}
		logTransfer.setLogisticsDetail(detail);
		return logTransfer;
	}

	/**
	 * 获取没有接口爬虫的快递单号
	 * 
	 * @return
	 */
	public List<ElseExpress> queryElseTrackNum(String date, Integer LASTMAXID) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);

		Integer NOWMAXID = logDao.queryMaxIdFromInfo(logBackCon);
		List<ElseExpress> trackNum = logDao.queryElseTrackNum(logBackCon,
				LASTMAXID, NOWMAXID, date);
		VersionCachePool.putObject("else_LASTMAXID", NOWMAXID);
		DBUtils.closeAll(null, logBackCon, null);
		logBackCon = null;
		return trackNum;
	}

	/**
	 * 回写订单中心，修改订单状态
	 * 
	 * @param trackNum
	 * @param date
	 * @return
	 */
	public Integer writeBackOrderCenter(String trackNum, String date) {
		Connection orderCon = DBUtils.getOrderConnection(false);
		Integer boo = orderDao.writeBackOrderCenter(orderCon, trackNum, date);
		DBUtils.closeAll(null, orderCon, null);
		orderCon = null;
		return boo;
	}

	/**
	 * 从订单中心抓取快递初始数据存入物流中心
	 */
	public void getTrackNumToLogistics(String startDate, String endDate) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		Connection logCon = DBUtils.getLogisticsConnection(true);
		Connection orderBackCon = DBUtils.getOrderBackupConnection(false);
		PageModel<OrderVo> page = new PageModel<OrderVo>();
		page.setPageSize(100);
		Integer total = orderDao.getCountofTrackNumInfo(orderBackCon,
				startDate, endDate);
		if (total > 0) {
			page.setTotalRecords(total);
			logger.info("一共有：" + total + "条数据，共分为:" + page.getTotalPages()
					+ "页");
			total = null;
			while (page.getPageNo() <= page.getTotalPages()) {
				List<OrderVo> orderVo = orderDao.getTrackNumInfo(orderBackCon,
						startDate, endDate, page.getFirstResult(), page
								.getPageSize());
				if (null != orderVo && !orderVo.isEmpty()) {
					Integer count = 0;

					for (OrderVo order : orderVo) {
						Integer boo = 1;
						try {
							boo = logDao.queryByTrackNum(logBackCon, order
									.getExpressId().trim());
						} catch (SQLException e1) {
							logger.error("重复校验查询失败！！！！！");
							e1.printStackTrace();
						}
						if (boo <= 0) {
							count++;
							String source = getSource(
									order.getExpressComName(), order
											.getExpressComCode(), order
											.getExpressComId());
							// logger.info("切换后Code:" + source);
							LogisticsInfo lsif = new LogisticsInfo(order
									.getExpressId(), source, "S00", order
									.getStartTime(), order.getOrderNumber(),
									order.getOrderFlag(), 0);
							Integer bool = logDao.insertLogisticsInfo(logCon,
									lsif);
							lsif = null;
							if (bool <= 0) {
								logger.error(source + "_"
										+ order.getExpressId()
										+ "插入数据库失败，准备事物回滚");
								DBUtils.rollback(logCon);
								break;
							} else {
								try {

									// 调用微信接口，通知微信发信息
									sendStautsToWX(order.getExpressId(),
											source, "已发货");
								} catch (Exception e) {
									logger.error("向微信信平台发送已发货信息失败！！！！！");
								}
							}
							bool = null;
						} else {// 判断是否存在订单号
							String source = getSource(
									order.getExpressComName(), order
											.getExpressComCode(), order
											.getExpressComId());
							try {
								Integer isExist = logDao
										.qureyOrderNumberByTrack(logBackCon,
												order.getExpressId(), source);
								if (isExist <= 0) {

									Integer isSuccess = logDao
											.updateTrackInfoToSetOrderInfo(
													logCon, order
															.getOrderNumber(),
													order.getOrderFlag(), order
															.getStartTime(),
													order.getExpressId(),
													source);
									if (isSuccess <= 0) {
										logger.error(source + "_"
												+ order.getExpressId()
												+ "修改数据库信息失败，准备事物回滚");
										DBUtils.rollback(logCon);
										break;
									}else{
										count++;
										try {

											// 调用微信接口，通知微信发信息
											sendStautsToWX(order.getExpressId(),
													source, "已发货");
										} catch (Exception e) {
											logger.error("向微信信平台发送已发货信息失败！！！！！");
										}
									}
								}
							} catch (SQLException e) {
								logger.error("二次重复校验查询订单号失败！！！！！");
								e.printStackTrace();
							}
						}
						boo = null;
					}
					if (count > 0) {
						// logger.info("准备提交事物");
						DBUtils.commitTrans(logCon);
						count = 0;
					}
					orderVo = null;
					int pageNo = page.getPageNo();
					pageNo++;
					page.setPageNo(pageNo);
					if (page.getPageNo() % 40 == 0) {
						DBUtils.closeAll(null, logCon, null);
						logCon = DBUtils.getLogisticsConnection(true);
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("抓单程序睡眠失败");
						e.printStackTrace();
					}
				}
			}
		}
		DBUtils.closeAll(null, logCon, null);
		logBackCon = null;
		DBUtils.closeAll(null, logCon, null);
		logCon = null;
		startDate = null;
		endDate = null;
		DBUtils.closeAll(null, orderBackCon, null);
		orderBackCon = null;

		VersionCachePool.putObject("ISFIRSTOPERATION", 1);
	}

	/**
	 * 获取指定渠道的快递漏单单号
	 * 
	 * @param source
	 *            快递渠道编码
	 * @param startDate
	 *            日期时间
	 * @return
	 */
	public List<String> getLeakageOfSingle(String source, String startDate) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		List<String> trackList = logDao.queryLeakageOfSingle(logBackCon,
				source, startDate, endDate);
		DBUtils.closeAll(null, logBackCon, null);
		// calendar = null;
		logBackCon = null;
		endDate = null;
		return trackList;
	}

	/**
	 * 获取没有接口和爬虫的快递漏单单号及渠道
	 * 
	 * @param startDate
	 * @return
	 */
	public List<ElseExpress> getElseLeakageOfSingle(String startDate) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		Calendar calendar = Calendar.getInstance();
		int dt = TaskConfig.getInteger("EndDate");
		calendar.add(Calendar.DATE, -dt); // 得到前dt天
		String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(calendar.getTime());
		List<ElseExpress> trackList = logDao.queryElseLeakageOfSingle(
				logBackCon, startDate, endDate);
		DBUtils.closeAll(null, logBackCon, null);
		logBackCon = null;
		endDate = null;
		return trackList;
	}

	public List<LogisticsInfo> getLogistInfoById(Integer startId, Integer endId) {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		List<LogisticsInfo> log = logDao.queryLogisticsInfoById(logBackCon,
				startId, endId);
		DBUtils.closeAll(null, logBackCon, null);
		logBackCon = null;
		return log;
	}

	public Integer getMaxID() {
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		Integer i = logDao.queryMaxIdFromInfo(logBackCon);
		DBUtils.closeAll(null, logBackCon, null);
		return i;
	}

	public void getDevanExpInfo(String startDate, String endDate) {
		Connection logCon = DBUtils.getLogisticsConnection(true);
		Connection logBackCon = DBUtils.getLogisticsBackupConnection(false);
		Connection orderBackCon = DBUtils.getOrderBackupConnection(false);
		List<OrderNumberVo> orderNum = orderDao.getTrackNumberAndFlag(
				orderBackCon, startDate, endDate);
		if (null != orderNum && !orderNum.isEmpty()) {

			for (OrderNumberVo order : orderNum) {
				StringBuffer expressNo = new StringBuffer("");
				StringBuffer expComName = new StringBuffer("");
				List<OrderVo> orderVo = orderDao.getExpressInfo(orderBackCon,
						order.getOrderNumber(), order.getOrderFlag());
				if (null != orderVo && !orderVo.isEmpty()) {
					for (OrderVo vo : orderVo) {
						Integer boo = 1;
						try {
							boo = logDao.queryByTrackNum(logCon, vo
									.getExpressId());
						} catch (SQLException e1) {
							logger.error("重复校验查询失败！！！！！");
							e1.printStackTrace();
						}
						if (boo < 1) {
							String source = getSource(vo.getExpressComName(),
									vo.getExpressComCode(), vo
											.getExpressComId());
							// logger.info("切换后Code:" + source);
							expComName.append(source);
							expComName.append(",");
							expressNo.append(vo.getExpressId());
							expressNo.append(",");
							LogisticsInfo lsif = new LogisticsInfo(vo
									.getExpressId(), source, "S00", order
									.getStartTime(), order.getOrderNumber(),
									order.getOrderFlag(), 0);
							Integer bool = logDao.insertLogisticsInfo(logCon,
									lsif);
							if (bool <= 0) {
								logger.info(source + "_" + vo.getExpressId()
										+ "插入数据库失败，准备事物回滚");
								DBUtils.rollback(logCon);
								break;
							} else {
							}
						} else {
							// 判断是否存在订单号
							String source = getSource(vo.getExpressComName(),
									vo.getExpressComCode(), vo
											.getExpressComId());
							try {
								Integer isExist = logDao
										.qureyOrderNumberByTrack(logBackCon, vo
												.getExpressId(), source);
								if (isExist <= 0) {

									Integer isSuccess = logDao
											.updateTrackInfoToSetOrderInfo(
													logCon, order
															.getOrderNumber(),
													order.getOrderFlag(), order
															.getStartTime(), vo
															.getExpressId(),
													source);
									if (isSuccess <= 0) {
										logger.error(source + "_"
												+ vo.getExpressId()
												+ "修改数据库信息失败，准备事物回滚");
										DBUtils.rollback(logCon);
										break;
									}
								}
							} catch (SQLException e) {
								logger.error("二次重复校验查询订单号失败！！！！！");
								e.printStackTrace();
							}

						}

					}
					DBUtils.commitTrans(logCon);
					String expName = expComName.toString();
					String trackNum = expressNo.toString();
					if (!"".equals(trackNum) && !"".equals(expName)) {
						expName = expName.substring(0, expName.length() - 1);
						trackNum = trackNum.substring(0, trackNum.length() - 1);
						String params = "type=2&orderFlag="
								+ order.getOrderFlag() + "&orderNumber="
								+ order.getOrderNumber() + "&expressNo="
								+ trackNum + "&expressComName=" + expName;
						logger.info("向短信平台传递的参数：" + params);
						String res = HttpUtils.sendPost(url, params);
						logger.info("短信平台返回的结果：" + res);
					}

				}
			}
		}
		DBUtils.closeAll(null, logCon, null);
		DBUtils.closeAll(null, orderBackCon, null);
		VersionCachePool.putObject("DEVANEXPISFIRSTOPERATION", 1);
	}

	public void sendOverToDxTerrace(String trackNumber, String source) {
		if (null != trackNumber && !"".equals(trackNumber) && null != source
				&& !"".equals(source)) {
			Connection logBackconn = DBUtils
					.getLogisticsBackupConnection(false);
			OrderNumberVo order;
			try {
				order = logDao.getOrderNumberAndFlag(logBackconn, trackNumber,
						source);
				DBUtils.closeAll(null, logBackconn, null);
				logBackconn = null;
				if (null != order) {
					String params = "type=1&orderFlag=" + order.getOrderFlag()
							+ "&orderNumber=" + order.getOrderNumber()
							+ "&expressNo=" + trackNumber + "&expressComName="
							+ source;
					logger.info("通知短信平台发短信请求参数：" + params);
					String res = HttpUtils.sendPost(url, params);
					logger.info("通知短信平台返回结果：" + res);
				} else {
					logger.error("快递：" + source + "_" + trackNumber
							+ "的运单没有获取到订单号和渠道！！！！！");
				}

			} catch (SQLException e) {
				logger.error("快递：" + source + "_" + trackNumber
						+ "的运单获取订单号和渠道失败！！！！！");
				e.printStackTrace();
			}

		}

	}

	public void getAnOrderExpInfo(String startDate, String endDate) {
		Connection logCon = DBUtils.getLogisticsConnection(true);
		Connection orderBackCon = DBUtils.getOrderBackupConnection(false);
		PageModel<OrderVo> page = new PageModel<OrderVo>();
		page.setPageSize(100);
		Integer total = orderDao.getCountofAnOrderTrackInfo(orderBackCon,
				startDate, endDate);
		if (total > 0) {
			page.setTotalRecords(total);
			logger.info("一共有：" + total + "条数据，共分为:" + page.getTotalPages()
					+ "页");
			total = null;
			while (page.getPageNo() <= page.getTotalPages()) {
				List<OrderVo> orderVo = orderDao.getAnOrderTrackInfo(
						orderBackCon, startDate, endDate,
						page.getFirstResult(), page.getPageSize());
				if (null != orderVo && !orderVo.isEmpty()) {
					Integer count = 0;

					for (OrderVo order : orderVo) {
						Integer boo = 1;
						try {
							boo = logDao.queryByTrackNum(logCon, order
									.getExpressId().trim());
						} catch (SQLException e1) {
							logger.error("重复校验失败！！！！！");
							e1.printStackTrace();
						}
						if (boo <= 0) {
							count++;
							String source = getSource(
									order.getExpressComName(), order
											.getExpressComCode(), order
											.getExpressComId());
							// logger.info("切换后Code:" + source);
							LogisticsInfo lsif = new LogisticsInfo(order
									.getExpressId(), source, "S00", order
									.getStartTime(), "", order.getOrderFlag(),
									0);
							Integer bool = logDao.insertLogisticsInfo(logCon,
									lsif);
							lsif = null;
							if (bool <= 0) {
								logger.error(source + "_"
										+ order.getExpressId()
										+ "插入数据库失败，准备事物回滚");
								DBUtils.rollback(logCon);
								break;
							} else {
								// sendStautsToWX(order.getExpressId(),source,"已发货");
							}
							bool = null;
						}
						boo = null;
					}
					if (count > 0) {
						// logger.info("准备提交事物");
						DBUtils.commitTrans(logCon);
						count = 0;
					}
					orderVo = null;
					int pageNo = page.getPageNo();
					pageNo++;
					page.setPageNo(pageNo);
					if (page.getPageNo() % 40 == 0) {
						DBUtils.closeAll(null, logCon, null);
						logCon = DBUtils.getLogisticsConnection(true);
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						logger.error("抓取无订单快递单号程序睡眠失败");
						e.printStackTrace();
					}
				}
			}
		}
		DBUtils.closeAll(null, logCon, null);
		logCon = null;
		startDate = null;
		endDate = null;
		DBUtils.closeAll(null, orderBackCon, null);
		orderBackCon = null;

		VersionCachePool.putObject("ANORDERISFIRSTOPERATION", 1);
	}

	public void sendStautsToWX(String trackNumber, String source, String stauts) {
		if (null != trackNumber && !"".equals(trackNumber) && null != source
				&& !"".equals(source)) {
			logger.info("准备向微信发送订单" + stauts + "状态消息，物流公司名称、单号为：" + source
					+ "_" + trackNumber);
			ResourceBundle bundleCode = ResourceBundle
					.getBundle("expressCodeToName");
			ResourceBundle bundleFlag = ResourceBundle
					.getBundle("flagCodeToName");
			String expressName = bundleCode.getString(source);
			if (null != expressName && !"".equals(expressName)) {
				String wxUrl = TaskConfig.getString("WxUrl");
				Connection orderBackCon = DBUtils
						.getOrderBackupConnection(false);
				WXSendInfoVo wxSendInfo = orderDao.getSendWXInfo(orderBackCon,
						trackNumber);
				wxSendInfo.setExpressNo(trackNumber);
				wxSendInfo.setExpressName(expressName);
				wxSendInfo.setInformation(stauts);
				if (null != wxSendInfo) {
					if (null != wxSendInfo.getOrderNumber()
							&& !"".equals(wxSendInfo.getOrderNumber())
							&& null != wxSendInfo.getMobile()
							&& !"".equals(wxSendInfo.getMobile())) {
						String productName = orderDao.getProductName(
								orderBackCon, wxSendInfo.getOrderNumber(),
								wxSendInfo.getOrderFlag());
						if (null != productName && !"".equals(productName)) {
							String orderFlag = bundleFlag.getString(wxSendInfo
									.getOrderFlag());
							wxSendInfo.setOrderFlag(orderFlag);
							wxSendInfo.setProductName(productName);
							String simpleJson = wxSendInfo.getSimpleJson();
							// logger.info("加密json：" + simpleJson);
							String sign = EncoderHandler.encode("SHA1",
									simpleJson);
							wxSendInfo.setSign(sign);
							String ultimateJson = wxSendInfo.getUltimateJson();
							// logger.info("最終发送json：" + ultimateJson);
							String res = HttpUtils.sendHttpPost(wxUrl,
									ultimateJson);
							logger.info(trackNumber + "的" + stauts
									+ "通知微信返回结果：" + res);
						}
					}
				}
				DBUtils.closeAll(null, orderBackCon, null);
			}
		} else {
			logger.info("运单号或快递公司名称为空！！！！！");
		}

	}

	public String getSource(String expressComName, String expressComCode,
			String expressComID) {
		String source = "unknow";
		if (null != expressComName && !"".equals(expressComName.trim())) {
			try {
				/*
				 * logger.info("快递公司名称：" + order.getExpressComName());
				 */
				source = bundle.getString(expressComName.trim());
			} catch (Exception e) {

			}

		} else if (null != expressComCode && !"".equals(expressComCode.trim())) {
			try {
				/*
				 * logger.info("快递公司编码：" + order.getExpressComCode());
				 */
				source = bundle1.getString(expressComCode.trim());
			} catch (Exception e) {

			}

		} else if (null != expressComID && !"".equals(expressComID.trim())) {
			try {
				/*
				 * logger.info("快递公司ID：" + order.getExpressComId());
				 */
				source = bundle2.getString(expressComID.trim());
			} catch (Exception e) {

			}
		}
		return source;
	}

}
