package com.ehaoyao.logistics.yto.task.thread;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;

import net.sf.json.JSONArray;

public class YTOLogisticsThread implements Runnable {
	private static final Logger logger = Logger.getLogger(YTOLogisticsThread.class);
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");

	private YTOLogisticsService ytoLogisticsService;
	private List<WayBillInfo> subThreadList;
	
	
	public YTOLogisticsService getYtoLogisticsService() {
		return ytoLogisticsService;
	}

	public void setYtoLogisticsService(YTOLogisticsService ytoLogisticsService) {
		this.ytoLogisticsService = ytoLogisticsService;
	}

	public List<WayBillInfo> getSubThreadList() {
		return subThreadList;
	}

	public void setSubThreadList(List<WayBillInfo> subThreadList) {
		this.subThreadList = subThreadList;
	}

	@Override
	public void run() {

		long threadStartTime = System.currentTimeMillis();
		int dealCount =Integer.parseInt(ytoConfig.getString("commitCount"));//每次处理条数
		List<WayBillInfo>  subList = null;
		int updCount = 0 ;//每次最终成功更新运单条数
		int count = 1;//当前处理次数
		int totalUpdCount=0;//插入总条数
		JSONArray arr = null;
		try {
			for(int i = 0;i<subThreadList.size();i+=dealCount){
				if((i+dealCount)>subThreadList.size()){
					subList = subThreadList.subList(i,subThreadList.size());
				}else{
					subList = subThreadList.subList(i,i+dealCount);
				}
				//分批次/条数，调用圆通物流接口并更新物流中心运单物流信息
				if(subList!=null&&!subList.isEmpty()){
					updCount = (Integer) ytoLogisticsService.updateYTOWayBills(subList);
					totalUpdCount+=updCount;
					logger.info("【线程："+Thread.currentThread().getName()+"，共需处理"+subThreadList.size()+"条记录，每次处理"+dealCount+"条，共需处理"+((subThreadList.size()-1)/dealCount+1)+"次，当前处理第"+count+"次，当前成功更新运单物流信息"+updCount+"条，累计成功更新运单物流信息"+totalUpdCount+"条】");
					count++;//每次处理后加1
				}
			}
		} catch (Exception e) {
			if(subList!=null){
				arr = JSONArray.fromObject(subList);
			}
			logger.error("【线程："+Thread.currentThread().getName()+"处理异常！处理信息："+(arr!=null?arr.toString():"")+"异常信息：】"+e);
			e.printStackTrace();
		}finally{
			long threadEndTime = System.currentTimeMillis();
			logger.info("【线程："+Thread.currentThread().getName()+"，处理结束，共成功更新运单物流信息"+totalUpdCount+"条记录,一共耗时："+(threadEndTime-threadStartTime)/1000.0+"s】");
		}
	}

}
