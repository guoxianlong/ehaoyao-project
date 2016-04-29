package com.haoys.logisticsServer.timeTask.task;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.service.LogisticsServiceImpl;
import com.haoys.logisticsServer.service.spi.LogisticsService;
import com.haoys.logisticsServer.timeTask.config.TaskConfig;
import com.haoys.logisticsServer.utils.VersionCachePool;

public class GetTrackNumberInfo extends TimerTask{
	
	static Logger logger = Logger.getLogger(GetTrackNumberInfo.class);
	LogisticsService logService=new LogisticsServiceImpl();
	
	/**
	 * 缩短抓单时间，添加漏单抓取机制
	 */
	/**
	 * 从订单中心抓取快递单号信息并插入到
	 * 物流中心t_logistics_info表中定时任务
	 */
	@Override
	public void run() {
		logger.info("从订单中心抓取快递单号信息定时任务开始运行！！！！");
		String startDate = "";
        String endDate="";
		Integer isFirstOperation = (Integer) VersionCachePool
				.getObject("ISFIRSTOPERATION");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		if (null == isFirstOperation) {
			logger.info("获取快递单号程序首次运行");
			int dt = TaskConfig.getInteger("FirstOperation");
			calendar.add(Calendar.DATE, -dt); // 得到前一天
		} else {
			int dt = TaskConfig.getInteger("NotFirstOperation");
			calendar.add(Calendar.HOUR_OF_DAY, -dt); // 得到前一天
			
		}

		startDate = sdf.format(calendar
				.getTime());
		endDate=sdf.format(new Date());
		logService.getTrackNumToLogistics(startDate,endDate);
		logger.info("抓取时间为："+startDate+"到"+endDate+"快递单号获取定时任务运行结束,等待下次运行！！！！");
	}
	
	
}
