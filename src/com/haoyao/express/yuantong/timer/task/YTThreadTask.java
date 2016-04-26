package com.haoyao.express.yuantong.timer.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.haoyao.express.yuantong.thread.YTExpressThread;

import com.haoyao.express.yuantong.utils.TaskConfig;
import com.haoys.logisticsserver.ws.client.LogisticsServerClient;






public class YTThreadTask extends TimerTask{
	static Logger logger = Logger.getLogger(YTThreadTask.class);
	public static ExecutorService pool = Executors.newFixedThreadPool(TaskConfig.getInteger("ThreadPool"));
	@Override
	public void run() {
		logger.info(Thread.currentThread().getName() + "圆通抓单程序开始运行");
		Calendar calendar = Calendar.getInstance();
		int dt = TaskConfig.getInteger("StartDate");
		calendar.add(Calendar.DATE, -dt); // 得到前dt天
		String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(calendar.getTime());
		List<String> ytExp = LogisticsServerClient.getLeakageOfSingle(
				"yuantong", startDate);
		if (null != ytExp && !ytExp.isEmpty()) {
			logger.info(Thread.currentThread().getName() + "本次圆通抓单程序共有："
					+ ytExp.size() + "条单需要抓取");
			int catchCount=TaskConfig.getInteger("CatchCount");
			List<String> trackList=new ArrayList<String>();
			int index=1;
			for(String trackNumber:ytExp){
				trackList.add(trackNumber);
				if(index%catchCount==0||index==ytExp.size()){
					YTExpressThread leakThred=new YTExpressThread();
					leakThred.setTrackNumberList(trackList);
					if(!pool.isShutdown()){
						pool.execute(leakThred);
					}
					trackList.clear();
				}
				index++;
			}
		} else {
			logger.info(Thread.currentThread().getName() + "圆通抓单程序本次暂无单可抓");
		}

	}
}
