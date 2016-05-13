
package com.ehaoyao.logistics.jd.task;

import java.text.SimpleDateFormat;
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
import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.service.JDWayBillDetailService;
import com.ehaoyao.logistics.jd.service.JDWayBillInfoService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 上午11:04:41
 * 类说明
 */
@Component("jDExpressMissTask")
public class JDExpressMissTask {
	private static final Logger logger = Logger.getLogger(JDExpressMissTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ResourceBundle jdconfig = ResourceBundle.getBundle("jdconfig");
	//属性注入
	@Autowired
	JDWayBillInfoService waybillInfoService;
	@Autowired
	JDWayBillDetailService waybillDetailService;
	
	@Autowired
	ToLogisticsCenterService toLogisticsService;
	
	@Autowired
	ExpressInfoService expressInfoService;
	
	/**
	 * 调用京东的API,更新未妥投的运单信息
	 * @throws InterruptedException 
	 */
	public void updateMissWayBillToLogisticsDB(){
		ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(jdconfig.getString("ThreadPool")));
		Date startDate=new Date();
		logger.info("【京东更新物流(漏单)-开始更新未妥投、未拒收的 京东运单！开始时间："+sdf.format(startDate)+"】");
		/*1、抓取创建日期、物流中心的未妥投、未拒收运单号集合*/
		int miss_updLogistics_start =  Integer.parseInt(jdconfig.getString("miss_updLogistics_start"));
		int miss_updLogistics_end = Integer.parseInt(jdconfig.getString("miss_updLogistics_end"));
		Date startTimeQuery = DateUtil.getPreMinute(miss_updLogistics_start);
		Date endTimeQuery = DateUtil.getPreMinute(miss_updLogistics_end);
		String waybillSource=jdconfig.getString("waybillsource");
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1 运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_COLLECTPARCEL);
		waybillStatusList.add(WayBillInfo.WAYBILL_INFO_STATUS_DISTRIBUTION);		
		List<WayBillInfo> wayBillInfoList=new ArrayList<WayBillInfo>();
		try {
			//1.2 根据 运单来源、日期、状态等条件查询出  运单Info集合
			 wayBillInfoList = waybillInfoService.queryWayBillInfoList(startTimeQuery,endTimeQuery, waybillSource, waybillStatusList);
		} catch (Exception e) {
			logger.error("【 京东更新物流(漏单)-抓取京东的未妥投、未拒收运单程序异常！！】",e);
		}
		logger.info("【 京东更新物流(漏单)-本次从物流中心抓取未妥投、未拒收京东单子个数："+wayBillInfoList.size()+"个!!】");
		/*2、遍历运单号集合,调用京东API获取每个运单的物流信息，并更新到物流中心*/
		int startThreadNeedCount = Integer.parseInt(jdconfig.getString("startThreadNeedCount"));//每多少运单开启一个更新运单线程
		if(wayBillInfoList.size()>0){			
			for(int i = 0;i<wayBillInfoList.size();i+=startThreadNeedCount){
				List<WayBillInfo>  subList = null;
				if((i+startThreadNeedCount)>wayBillInfoList.size()){
					subList = wayBillInfoList.subList(i,wayBillInfoList.size());
				}else{
					subList = wayBillInfoList.subList(i,i+startThreadNeedCount);
				}
				//	将订单中心查询到的已配送的订单及运单号等信息插入物流中心
				if(subList!=null&&!subList.isEmpty()){
					UpdateWayBillThread updateWayBillThread = new UpdateWayBillThread();
					updateWayBillThread.setWayBillInfoList(subList);
					updateWayBillThread.setWaybillInfoService(waybillInfoService);
					if(!pool.isShutdown()){
						pool.execute(updateWayBillThread);					
					}
				}
			}
		}else{
			logger.info("【京东更新物流(漏单)-物流中心暂时没有未妥投未拒收的京东运单可以抓取！！！】");
		}
		//关闭线程池
		pool.shutdown();
		while(true){
			if(pool.isTerminated()){
				break;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				logger.info("【京东更新物流(漏单)-主程序线程："+Thread.currentThread().getName()+"睡眠10s的方法发生异常】",e);
			}			
		}
		long endTime = System.currentTimeMillis();
		logger.info("【京东更新物流(漏单)-更新未妥投、未拒收的京东运单程序结束,共耗时："+(endTime-startDate.getTime())/1000+"s】");
	}
	
	public void dealLogic(){
		System.out.println(sdf.format(new Date()));
	}
}
