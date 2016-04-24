
package com.ehaoyao.logistics.jd.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.service.JDWayBillDetailService;
import com.ehaoyao.logistics.jd.service.JDWayBillInfoService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 上午11:04:41
 * 类说明
 */
@Component("jDExpressTask")
public class JDExpressTask {
	private static final Logger logger = Logger.getLogger(JDExpressTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ReadConfigs configs = new ReadConfigs("jdconfig");
	private static ExecutorService pool = Executors.newFixedThreadPool(configs.getInteger("ThreadPool"));
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
	 */
	public void updateWayBillToLogisticsDB(){
		Date startDate=new Date();
		logger.info("开始更新京东的未妥投、未拒收运单！开始时间："+sdf.format(startDate));
		/*1、抓取创建日期7天内、物流中心的未妥投、未拒收运单号集合*/
		int dt=configs.getInteger("NormalStartDate");
		String waybillSource="jd";
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1 运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add("s00");
		waybillStatusList.add("s01");
		waybillStatusList.add("s02");
		List<WayBillInfo> wayBillInfoList=new ArrayList<WayBillInfo>();
		try {
			//1.2 根据 运单来源、日期、状态等条件查询出  运单Info集合
			 wayBillInfoList = waybillInfoService.queryWayBillInfoList(dt, waybillSource, waybillStatusList);
		} catch (Exception e) {
			logger.error("抓取京东的未妥投、未拒收运单程序异常！！",e);
		}
		logger.info("本次从物流中心抓取未妥投、未拒收单子个数："+wayBillInfoList.size()+"个!!");
		/*2、遍历运单号集合,调用京东API获取每个运单的物流信息，并更新到物流中心*/
		int startThreadNeedCount = configs.getInteger("startThreadNeedCount");//每多少运单开启一个更新运单线程
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
			logger.info("物流中心暂时没有未妥投未拒收的运单可以抓取！！！！！！！！！！");
		}
	}
	
	public void dealLogic(){
		System.out.println(sdf.format(new Date()));
	}
}
