package com.haoys.logisticsServer.timeTask.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.service.LogisticsServiceImpl;
import com.haoys.logisticsServer.service.spi.LogisticsService;
import com.haoys.logisticsServer.timeTask.config.TaskConfig;
/**
 * 抓漏单定时任务
 * @author Administrator
 *
 */
public class LeakageOfSingleTask extends TimerTask{
	static Logger logger = Logger.getLogger(LeakageOfSingleTask.class);
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
		logger.info("抓漏单定时任务开始运行！！！！");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -TaskConfig.getInteger("LeakageOfSingleDate")); // 得到前N天
		String startdate =sdf.format(calendar
				.getTime());
		String endDate=sdf.format(new Date());
		logService.getTrackNumToLogistics(startdate,endDate);
		logger.info("抓漏单定时任务运行结束,等待下次运行！！！！");
	}
}
