
package com.ehaoyao.logistics.yto.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;
import com.ehaoyao.logistics.yto.task.thread.YTOLogisticsThread;

/**
 * @author wls 
 * @date	2016-04-25
 * 更新订单采用圆通快递配送的物流信息
 * 圆通技术qq:626933286 电话：18782937014 
 */
@Component("ytoLogisticsMissTask")
public class YTOLogisticsMissTask {
	private static final Logger logger = Logger.getLogger(YTOLogisticsMissTask.class);
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	
	//属性注入
	@Autowired
	YTOLogisticsService ytoLogisticsService;
	/**
	 * 更新订单采用圆通快递配送的物流信息
	 */
	public void updateYTOMissWayBills(){
		long startTime = System.currentTimeMillis();
		int fixThreadCount =Integer.parseInt(ytoConfig.getString("fixThreadCount"));//每个线程处理条数
		
		List<WayBillInfo>  subThreadList = null;//每个线程处理结果集
		try {
			long queryStartTime = System.currentTimeMillis();
			//获取查询条件实体
			WayBillInfoVo wayBillInfoVo = queryParamVo();
			//获取采用圆通快递配送的物流信息集合
			List<WayBillInfo> wayBillInfoList = ytoLogisticsService.selectYTOInitWayBills(wayBillInfoVo);
			long queryEndTime = System.currentTimeMillis();
			logger.info("【圆通漏单-获取采用圆通快递配送的物流信息集合，共耗时："+(queryEndTime-queryStartTime)/1000+"s】");
			
			
			//多线程任务处理
			int threadCount = (wayBillInfoList.size()-1)/fixThreadCount+1;
			logger.info("【圆通漏单-本次任务共需创建："+threadCount+"个线程处理,每个线程处理"+fixThreadCount+"条,共需处理"+wayBillInfoList.size()+"条】");

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
				while(true){
					if(pool.isTerminated()){
						break;
					}
						Thread.sleep(10000);
				}
				
			logger.info("【圆通漏单-###pool.isTerminated()="+pool.isTerminated()+"###】");
			long endTime = System.currentTimeMillis();
			logger.info("【圆通漏单-更新物流中心-采用圆通物流配送的物流信息，已完成,共耗时："+(endTime-startTime)/1000+"s】");
		} catch (Exception e) {
			logger.error("【圆通漏单-更新圆通物流信息出错，异常信息："+e.getMessage()+"】");
		}
	}

	/**
	 * 查询条件封装
	 * @return
	 */
	public WayBillInfoVo queryParamVo(){
		String waybillsource = ytoConfig.getString("waybillsource");
		int miss_updLogistics_start = Integer.parseInt(ytoConfig.getString("miss_updLogistics_start"));
		int miss_updLogistics_end = Integer.parseInt(ytoConfig.getString("miss_updLogistics_end"));
		Date startTimeQuery=DateUtil.getPreMinute(miss_updLogistics_start);//当前时间向前推迟miss_updLogistics_start分钟，作为开始时间
		Date endTimeQuery=DateUtil.getPreMinute(miss_updLogistics_end);//当前时间向前推迟miss_updLogistics_end分钟，作为结束时间
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1 运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_COLLECTPARCEL);
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_DISTRIBUTION);
		
		/*2、将查询条件封装*/
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setWaybillSource(waybillsource);
		wayBillInfoVo.setCreateTimeStart(startTimeQuery);
		wayBillInfoVo.setCreateTimeEnd(endTimeQuery);
		wayBillInfoVo.setWaybillStatusList(waybillStatusList);
		return wayBillInfoVo;
	}
}
