package com.ehaoyao.cfy.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.cfy.model.operationcenter.OrderInfo;
import com.ehaoyao.cfy.service.OperationCenterService;
import com.ehaoyao.cfy.service.OrderCenterService;
import com.ehaoyao.cfy.task.thread.DealAuditPassOrderThread;
import com.ehaoyao.cfy.utils.DateUtil;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

@Component(value="dealAuditPassOrderMissTask")
public class DealAuditPassOrderMissTask {
	private static final Logger logger = Logger.getLogger(DealAuditPassOrderMissTask.class);
	@Autowired
	OperationCenterService operationCenterService;
	
	private static ResourceBundle application = ResourceBundle.getBundle("application");
	
	@Autowired
	OrderCenterService orderCenterService;
	
	
	public void dealLogic(){
		try {
			long startTime = System.currentTimeMillis();
			long queryStartTime = System.currentTimeMillis();
			/**
			 * 从运营中心，获取药师审核通过订单集合
			 */
			OrderInfoVo orderInfoVo = queryParam();
			List<OrderMainInfo>  orderMainInfoList = operationCenterService.selectAuditPassList(orderInfoVo);
			
			long queryEndTime = System.currentTimeMillis();
			logger.info("【漏单-获取药师审核通过处方药订单-运营中心查询，共耗时："+(queryEndTime-queryStartTime)/1000+"s】");
			if(orderMainInfoList!=null && !orderMainInfoList.isEmpty()){
				insOrderCenter(orderMainInfoList);
			}
			
			long endTime = System.currentTimeMillis();
			logger.info("【漏单-获取药师审核通过处方药订单初始至订单中心，已完成,共耗时："+(endTime-startTime)/1000+"s】");
		} catch (Exception e) {
			logger.error("【漏单-获取药师审核通过处方药订单初始至订单中心，异常信息："+e.getMessage()+"】");
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单插入订单中心
	 * @param orderMainInfoList
	 * @throws Exception
	 */
	private void insOrderCenter(List<OrderMainInfo>  orderMainInfoList) throws Exception{
		List<OrderMainInfo> subThreadList;
		int fixThreadCount =Integer.parseInt(application.getString("fixThreadCount"));//每个线程处理条数
		//多线程任务处理
		int threadCount = (orderMainInfoList.size()-1)/fixThreadCount+1;
		logger.info("【漏单-获取药师审核通过处方药订单初始至订单中心-本次任务共需创建："+threadCount+"个线程处理,每个线程处理"+fixThreadCount+"条,共需处理"+orderMainInfoList.size()+"条】");

		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		//创建子线程处理任务
		for(int i = 0;i<orderMainInfoList.size();i+=fixThreadCount){
			if((i+fixThreadCount)>orderMainInfoList.size()){
				subThreadList = orderMainInfoList.subList(i,orderMainInfoList.size());
			}else{
				subThreadList = orderMainInfoList.subList(i,i+fixThreadCount);
			}
			DealAuditPassOrderThread dealAuditPassOrderThread = new DealAuditPassOrderThread();
			dealAuditPassOrderThread.setOrderCenterService(orderCenterService);
			dealAuditPassOrderThread.setSubThreadList(subThreadList);
			pool.execute(dealAuditPassOrderThread);
		}
		
		//关闭线程池
		pool.shutdown();
		while(true){
			if(pool.isTerminated()){
				break;
			}
				Thread.sleep(5000);
		}
		
		logger.info("【漏单-获取药师审核通过处方药订单初始至订单中心-###pool.isTerminated()="+pool.isTerminated()+"###】");
	}

	/**
	 * 封装查询条件
	 * @return
	 */
	public OrderInfoVo queryParam() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int orderIntervalTime = Integer.parseInt(application.getString("miss_deal_hour_interval"));
		Date startTimeQuery=DateUtil.getPreHour(orderIntervalTime);//当前时间向前推迟xxx小时
		Date endTimeQuery=new Date();
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_SUCC);
		orderInfoVo.setAuditTimeStart(sdf.format(startTimeQuery));
		orderInfoVo.setAuditTimeEnd(sdf.format(endTimeQuery));
		return orderInfoVo;
	}
}
