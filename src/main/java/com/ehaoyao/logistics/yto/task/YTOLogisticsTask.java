
package com.ehaoyao.logistics.yto.task;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;
import com.ehaoyao.logistics.yto.task.thread.YTOLogisticsThread;

/**
 * @author wls 
 * @date	2016-04-25
 * 更新订单采用圆通快递配送的物流信息
 */
@Component("ytoLogisticsTask")
public class YTOLogisticsTask {
	private static final Logger logger = Logger.getLogger(YTOLogisticsTask.class);
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	
	//属性注入
	@Autowired
	YTOLogisticsService ytoLogisticsService;
	/**
	 * 更新订单采用圆通快递配送的物流信息
	 */
	public void updateYTOWayBills(){
		
		int fixThreadCount =Integer.parseInt(ytoConfig.getString("fixThreadCount"));//每个线程处理条数
		
		List<WayBillInfo>  subThreadList = null;//每个线程处理结果集
		try {
			long queryStartTime = System.currentTimeMillis();
			//获取采用圆通快递配送的物流信息集合
			List<WayBillInfo> wayBillInfoList = ytoLogisticsService.selectYTOInitWayBills();
			long queryEndTime = System.currentTimeMillis();
			logger.info("【获取采用圆通快递配送的物流信息集合，共耗时："+(queryEndTime-queryStartTime)/1000+"s】");
			
			
			//多线程任务处理
			int threadCount = (wayBillInfoList.size()-1)/fixThreadCount+1;
			logger.info("【本次任务共需创建："+threadCount+"个线程处理,每个线程处理"+fixThreadCount+"条,共需处理"+wayBillInfoList.size()+"条】");

			//创建线程池
			ExecutorService pool = Executors.newFixedThreadPool(threadCount);
			//创建子线程处理任务
			for(int i = 0;i<wayBillInfoList.size();i+=fixThreadCount){
				if((i+fixThreadCount)>wayBillInfoList.size()){
					subThreadList = wayBillInfoList.subList(i,wayBillInfoList.size());
				}else{
					subThreadList = wayBillInfoList.subList(i,i+fixThreadCount);
				}
				YTOLogisticsThread ytoLogisticsThread = new YTOLogisticsThread();
				ytoLogisticsThread.setYtoLogisticsService(ytoLogisticsService);
				ytoLogisticsThread.setSubThreadList(subThreadList);
				pool.execute(ytoLogisticsThread);
			}
			
			//关闭线程池
			pool.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
