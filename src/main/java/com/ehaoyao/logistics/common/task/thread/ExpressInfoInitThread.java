package com.ehaoyao.logistics.common.task.thread;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;

import net.sf.json.JSONArray;

public class ExpressInfoInitThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ExpressInfoInitThread.class);
	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	
	private List<OrderExpressVo> subThreadList;
	
	private ToLogisticsCenterService toLogisticsService;
	
	public List<OrderExpressVo> getSubThreadList() {
		return subThreadList;
	}

	public void setSubThreadList(List<OrderExpressVo> subThreadList) {
		this.subThreadList = subThreadList;
	}

	public ToLogisticsCenterService getToLogisticsService() {
		return toLogisticsService;
	}

	public void setToLogisticsService(ToLogisticsCenterService toLogisticsService) {
		this.toLogisticsService = toLogisticsService;
	}

	@Override
	public void run() {
		long threadStartTime = System.currentTimeMillis();
		int dealCount =Integer.parseInt(appConfigs.getString("commitCount"));//每次处理条数
		List<OrderExpressVo>  subList = null;
		int insCount = 0 ;//每次最终成功插入条数
		int count = 1;//当前处理次数
		int totalInsCount=0;//插入总条数
		JSONArray arr = null;
		try {
			for(int i = 0;i<subThreadList.size();i+=dealCount){
				if((i+dealCount)>subThreadList.size()){
					subList = subThreadList.subList(i,subThreadList.size());
				}else{
					subList = subThreadList.subList(i,i+dealCount);
				}
				//	将订单中心查询到的已配送的订单及运单号等信息插入物流中心
				if(subList!=null&&!subList.isEmpty()){
					insCount = (Integer) toLogisticsService.insertLogisticsCenter(subList);
					totalInsCount+=insCount;
					logger.info("【线程："+Thread.currentThread().getName()+"，共需处理"+subThreadList.size()+"条记录，每次处理"+dealCount+"条，共需处理"+((subThreadList.size()-1)/dealCount+1)+"次，当前处理第"+count+"次，当前成功插入"+insCount+"条，累计成功插入"+totalInsCount+"条】");
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
			logger.info("【线程："+Thread.currentThread().getName()+"，处理结束，共成功插入"+totalInsCount+"条记录,一共耗时："+(threadEndTime-threadStartTime)/1000.0+"s】");
		}
		

	}

}
