package com.ehaoyao.cfy.task.thread;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ehaoyao.cfy.service.OrderCenterService;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

import net.sf.json.JSONArray;

public class DealAuditPassOrderThread implements Runnable {

	private static final Logger logger = Logger.getLogger(DealAuditPassOrderThread.class);
	private static ResourceBundle application = ResourceBundle.getBundle("application");
	private OrderCenterService orderCenterService;
	private List<OrderMainInfo> subThreadList;
	
	
	public OrderCenterService getOrderCenterService() {
		return orderCenterService;
	}

	public void setOrderCenterService(OrderCenterService orderCenterService) {
		this.orderCenterService = orderCenterService;
	}

	public List<OrderMainInfo> getSubThreadList() {
		return subThreadList;
	}



	public void setSubThreadList(List<OrderMainInfo> subThreadList) {
		this.subThreadList = subThreadList;
	}



	@Override
	public void run() {
		long threadStartTime = System.currentTimeMillis();
		int dealCount =Integer.parseInt(application.getString("commitCount"));//每次处理条数
		List<OrderMainInfo>  subList = null;
		int insCount = 0 ;//每次最终成功处理条数
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
				//分批次/条数，调用圆通物流接口并更新物流中心运单物流信息
				if(subList!=null&&!subList.isEmpty()){
					try {
						insCount = (Integer) orderCenterService.insertOrderCenter(subList);
						totalInsCount+=insCount;
						logger.info("【药师审核通过处方药订单初始至订单中心-线程：" + Thread.currentThread().getName() + "，共需处理" + subThreadList.size() + "条记录，每次处理"
								+ dealCount + "条，共需处理" + ((subThreadList.size() - 1) / dealCount + 1) + "次，当前处理第" + count
								+ "次，当前成功处理" + insCount + "条，累计成功处理" + totalInsCount + "条】");
						count++;//每次处理后加1
					} catch (Exception e) {
						if(subList!=null){
							arr = JSONArray.fromObject(subList);
						}
						logger.error("【药师审核通过处方药订单初始至订单中心-线程：" + Thread.currentThread().getName() + "，共需处理" + subThreadList.size() + "条记录，每次处理"
							+ dealCount + "条，共需处理" + ((subThreadList.size() - 1) / dealCount + 1) + "次，当前处理第" + count
							+ "次，当前成功处理" + insCount + "条，累计成功处理" + totalInsCount + "条，处理信息："+(arr!=null?arr.toString():"")+"，处理出错，错误信息："+e.getMessage()+"】");
						e.printStackTrace();
					}
				}
			}
		}finally{
			long threadEndTime = System.currentTimeMillis();
			logger.info("【药师审核通过处方药订单初始至订单中心-线程："+Thread.currentThread().getName()+"，处理结束，共成功处理"+totalInsCount+"条记录,一共耗时："+(threadEndTime-threadStartTime)/1000.0+"s】");
		}
		
	}

}
