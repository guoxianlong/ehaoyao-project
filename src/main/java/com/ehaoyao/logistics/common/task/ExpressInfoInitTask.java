
package com.ehaoyao.logistics.common.task;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.task.thread.ExpressInfoInitThread;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;

/**
 * @author wls 
 * @date	2016-4-20
 * 将订单中心已配送的初始订单信息保存至物流中心
 */
@Component("expressInfoInitTask")
public class ExpressInfoInitTask {
	private static final Logger logger = Logger.getLogger(ExpressInfoInitTask.class);
	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	
	//属性注入
	@Autowired
	ToLogisticsCenterService toLogisticsService;
	
	@Autowired
	ExpressInfoService expressInfoService;
	
	/**
	 * 将订单中心的运单写入到物流中心
	 */
	public void insertWayBill(){
		
		int fixThreadCount =Integer.parseInt(appConfigs.getString("fixThreadCount"));//每个线程处理条数
		
		List<OrderExpressVo>  subThreadList = null;//每个线程处理结果集
		try {
			long queryStartTime = System.currentTimeMillis();
			//从订单中心获取已配送的订单信息集合
			List<OrderExpressVo> orderExpressList = expressInfoService.selectExpressInfoList();
			long queryEndTime = System.currentTimeMillis();
			logger.info("【从订单中心获取已配送的订单信息集合，共耗时："+(queryEndTime-queryStartTime)/1000+"s】");
			
			
			//多线程任务处理
			int threadCount = (orderExpressList.size()-1)/fixThreadCount+1;
			logger.info("【本次任务共需创建："+threadCount+"个线程处理,每个线程处理"+fixThreadCount+"条,共需处理"+orderExpressList.size()+"条】");

			//创建线程池
			ExecutorService pool = Executors.newFixedThreadPool(threadCount);
			//创建子线程处理任务
			for(int i = 0;i<orderExpressList.size();i+=fixThreadCount){
				if((i+fixThreadCount)>orderExpressList.size()){
					subThreadList = orderExpressList.subList(i,orderExpressList.size());
				}else{
					subThreadList = orderExpressList.subList(i,i+fixThreadCount);
				}
				ExpressInfoInitThread expressInitThread = new ExpressInfoInitThread();
				expressInitThread.setToLogisticsService(toLogisticsService);
				expressInitThread.setSubThreadList(subThreadList);
				pool.execute(expressInitThread);
			}
			
			//关闭线程池
			pool.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
