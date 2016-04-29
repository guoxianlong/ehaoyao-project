package com.haoys.logisticsServer.timeTask.start;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.haoys.logisticsServer.timeTask.config.TaskConfig;
import com.haoys.logisticsServer.timeTask.task.AnOrderExpInfoTask;
import com.haoys.logisticsServer.timeTask.task.DevanExpInfoTask;
import com.haoys.logisticsServer.timeTask.task.GetTrackNumberInfo;
import com.haoys.logisticsServer.timeTask.task.LeakageOfSingleTask;
import com.haoys.logisticsServer.utils.VersionCachePool;

public class StartGetTrackNumInfo implements ServletContextListener {
	static Logger logger = Logger.getLogger(StartGetTrackNumInfo.class);

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		VersionCachePool.remove("ISFIRSTOPERATION");
		VersionCachePool.remove("DEVANEXPISFIRSTOPERATION");
		VersionCachePool.remove("ANORDERISFIRSTOPERATION");
		if (!TaskConfig.getStartFlag("GetTrackNumberStart")) {
		} else {
			Thread mainThread = new Thread(new Runnable() {
				public void run() {
					Timer mainTimer = new Timer();
					GetTrackNumberInfo sfThreadTask = new GetTrackNumberInfo();
					mainTimer.schedule(sfThreadTask, new Date(), TaskConfig
							.getInterval("GetTrackNumberInfo"));
				}
			});
			logger.info("从订单中心抓取快递单号定时任务启动");
			mainThread.start();
		}

		if (!TaskConfig.getStartFlag("GetDevanExpInfoStart")) {
		} else {
			Thread devanExpThread = new Thread(new Runnable() {
				public void run() {
					Timer mainTimer = new Timer();
					DevanExpInfoTask devanExpThreadTask = new DevanExpInfoTask();
					mainTimer.schedule(devanExpThreadTask, new Date(),
							TaskConfig.getInterval("GetDevanExpInfo"));
				}
			});
			logger.info("从订单中心抓无订单的快递单号定时任务启动");
			devanExpThread.start();
		}
		if (!TaskConfig.getStartFlag("GetAnOrderExpInfoStart")) {
		} else {
			Thread anOrderExpThread = new Thread(new Runnable() {
				public void run() {
					Timer mainTimer = new Timer();
					AnOrderExpInfoTask anOrderExpThreadTask = new AnOrderExpInfoTask();
					SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			try {
				date = sdf.parse(TaskConfig
						.getString("LeakageOfSingleTaskStart"));
			} catch (ParseException e) {

				e.printStackTrace();
			}
					mainTimer.schedule(anOrderExpThreadTask, date,
							TaskConfig.getInterval("GetAnOrderExpInfo"));
				}
			});
			logger.info("从订单中心抓无订单的快递单号定时任务启动");
			anOrderExpThread.start();
		}
		if (!TaskConfig.getStartFlag("LeakageOfSingleStart")) {
		} else {
			Thread UpdateThread = new Thread(new Runnable() {
				public void run() {
					Timer lsTimer = new Timer();
					LeakageOfSingleTask lsTask = new LeakageOfSingleTask();

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					try {
						date = sdf.parse(TaskConfig
								.getString("LeakageOfSingleTaskStart"));
					} catch (ParseException e) {

						e.printStackTrace();
					}
					lsTimer.schedule(lsTask, date, TaskConfig
							.getInterval("LeakageOfSingleTask"));
				}
			});
			logger.info("抓漏单定时任务开始启动");
			UpdateThread.start();
		}
	}

}
