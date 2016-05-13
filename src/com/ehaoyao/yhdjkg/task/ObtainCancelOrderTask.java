package com.ehaoyao.yhdjkg.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.yhdjkg.utils.BaseMap;
import com.ehaoyao.yhdjkg.utils.DBUtil;
import com.ehaoyao.yhdjkg.utils.DateUtil;
import com.ehaoyao.yhdjkg.utils.PostClient;

/**
 * 
 * @author wls
 * @date 2016-04-08
 * 
 */
public class ObtainCancelOrderTask extends TimerTask {

	static Logger logger = Logger.getLogger(ObtainCancelOrderTask.class);
	
	private int timeInteval;
	
	public int getTimeInteval() {
		return timeInteval;
	}

	public void setTimeInteval(int timeInteval) {
		this.timeInteval = timeInteval;
	}
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public ObtainCancelOrderTask() {
		super();
	}

	public ObtainCancelOrderTask(int timeInteval) {
		super();
		this.timeInteval = timeInteval;
	}

	/**
	 * 获取退货列表
	 */
	public void run() {
		Date start_Time = new Date();
		logger.info("【1号店获取取消订单列表定时任务开始时间:"
				+ DateUtil.formatCurrentDateToStandardDate() + "】");
		int page = 1;
		int pages = 0;
		Long order_time = 0l;
		Integer order_count = 0;
		Map<String, String> map = BaseMap.getMap();
		map.put("method", "yhd.refund.get");
		java.util.Calendar c = java.util.GregorianCalendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, this.timeInteval);
		map.put("startTime", sdf.format(c.getTime()));
		map.put("endTime", sdf.format(new Date()));
		map.put("dateType", "1");	//时间类型 :1、申请时间 2、更新时间
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			do {
				map.put("curPage", page + "");
				String json = PostClient.sendByPost(BaseMap.ROUTER_URL, map,
						BaseMap.SECRET_KEY);
				JSONObject jsonObj = JSON.parseObject(json);
				if (jsonObj == null)
					return;
				JSONObject jo = jsonObj.getJSONObject("response");
				int count = jo.getIntValue("totalCount");
				if (count < 1)
					return;
				logger.info("【开始更新第" + page + "页取消订单】");
				pages = count / 50;
				pages = count % 50 == 0 ? pages : pages + 1;
				JSONArray ja = jo.getJSONObject("refundList").getJSONArray(
						"refund");
				String orderCode = "";
				for (int i = 0; i < ja.size(); i++) {
					try {
						JSONObject jor = null;
						jor = ja.getJSONObject(i);
						if (jor == null)
							continue;
						orderCode = jor.getString("orderCode");
						pstmt = con
								.prepareStatement("update order_info set order_status = 's04' where order_number = ? and order_flag = '"+BaseMap.getValue("channel_yhdcfy")+"' and order_status = 's00'");
						pstmt.setString(1, orderCode + "");
						Date endTime = new Date();
						int updateResult = pstmt.executeUpdate();
						order_time += new Date().getTime() - endTime.getTime();
						order_count++;
						if (updateResult > 0) {
							logger.info("【订单号:" + orderCode + "订单状态修改为s04成功】");
						} else {
							logger.info("【订单号:" + orderCode
									+ "订单状态修改为s04失败,该订单不存在或者已经修改状态】");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("【订单号:" + orderCode
								+ "订单状态修改为s04出现异常，异常信息:" + e.getMessage()+"】");
						continue;
					}
				}

				page++;
			} while (page <= pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("数据库连接出现异常:" + e.getMessage());
		} finally {
			DBUtil.closeAll(con, pstmt, null);
		}
		if (order_count > 0) {
			logger.info("【共有" + order_count + "条取消订单，更新每条数据约耗时"
					+ (order_time / order_count) + "ms】");
		}
		logger.info("【1号店 获取取消订单列表定时任务结束时间:"
				+ DateUtil.formatCurrentDateToStandardDate() + ",共耗时:"
				+ (new Date().getTime() - start_Time.getTime()) + "ms】");
	}
}
