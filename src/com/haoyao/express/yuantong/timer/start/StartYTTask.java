package com.haoyao.express.yuantong.timer.start;

import java.util.Date;
import java.util.Timer;


import org.apache.log4j.Logger;


import com.haoyao.express.yuantong.timer.task.YTThreadTask;
import com.haoyao.express.yuantong.utils.TaskConfig;



public class StartYTTask {

	static Logger logger = Logger.getLogger(StartYTTask.class);
	public static void startTask() {
		if (TaskConfig.getStartFlag("YTThreadTask")) {			
			Thread STThread = new Thread(new Runnable() {
				public void run() {		
					Timer mainTimer = new Timer();	
					YTThreadTask sfTask = new YTThreadTask();
					mainTimer.schedule(sfTask, new Date(),
							TaskConfig.getInterval("YTThreadStart"));
				}
			});		
			logger.info("圆通抓取快递信息定时任务启动");
			STThread.start();	
		}
		
	}
	public static void main(String[] args) {
		startTask();
	}
}
