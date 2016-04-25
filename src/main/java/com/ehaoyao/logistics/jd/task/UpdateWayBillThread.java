
package com.ehaoyao.logistics.jd.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.service.JDWayBillInfoService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月15日 下午3:22:26
 *  更新JD运单info与detail的线程 （物流跟踪信息）
 */
public class UpdateWayBillThread extends Thread {
	private static final Logger logger = Logger.getLogger(UpdateWayBillThread.class);
	private static ReadConfigs configs = new ReadConfigs("jdconfig");
	private List<WayBillInfo> wayBillInfoList;
	private  JDWayBillInfoService  waybillInfoService;
	
	public List<WayBillInfo> getWayBillInfoList() {
		return wayBillInfoList;
	}

	public void setWayBillInfoList(List<WayBillInfo> wayBillInfoList) {
		this.wayBillInfoList = wayBillInfoList;
	}

	public JDWayBillInfoService getWaybillInfoService() {
		return waybillInfoService;
	}

	public void setWaybillInfoService(JDWayBillInfoService waybillInfoService) {
		this.waybillInfoService = waybillInfoService;
	}

	public void run() {
		logger.info("启动京东快递更新物流信息新线程！！！！！");
		Date dateStart = new Date();
		int updateCount = 0 ;//每次最终成功更新条数
		int totalUpdateCount = 0;//累计最终成功更新条数
		int updateSize = configs.getInteger("updateSize");//每次处理条数
		int count = 1;//当前处理次数 
		for(int i = 0;i<wayBillInfoList.size();i+=updateSize){
			List<WayBillInfo>  subList = null;
			if((i+updateSize)>wayBillInfoList.size()){
				subList =	wayBillInfoList.subList(i,wayBillInfoList.size());
			}else{
				subList = wayBillInfoList.subList(i,i+updateSize);
			}
			//	调用业务层方法,批量更新 wayBillInfo和wayBillDetail(物流跟踪信息)
			if(subList!=null&&!subList.isEmpty()){
				try {
					updateCount = waybillInfoService.updateWaybillInfoListByJD(subList);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(Thread.currentThread().getName()+"【物流中心更新运单程序出错！！】");
				}
				totalUpdateCount+=updateCount;
				logger.info(Thread.currentThread().getName()+"【物流中心抓取到未妥投、未拒收运单"+wayBillInfoList.size()+"条记录，每次处理"+updateSize+"条，共需处理"+((wayBillInfoList.size()-1)/updateSize+1)+"次，当前处理第"+count+"次，当前成功更新"+updateCount+"条，累计成功更新"+totalUpdateCount+"条】");
				count++;//每次处理后加1
			}
		}
		Date dateEnd = new Date();
		logger.info(Thread.currentThread().getName()+"本次京东快递更新物流跟踪信息线程结束！！！耗时："+(dateEnd.getTime()-dateStart.getTime())+"s");
	}
}
