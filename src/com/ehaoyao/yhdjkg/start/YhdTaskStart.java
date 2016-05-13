package com.ehaoyao.yhdjkg.start;

import java.util.Date;
import java.util.Timer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ehaoyao.yhdjkg.task.ExpressUpdTask;
import com.ehaoyao.yhdjkg.task.ObtainCancelOrderTask;
import com.ehaoyao.yhdjkg.task.ObtainCfyOrderTask;
import com.ehaoyao.yhdjkg.utils.TaskConfig;

/**
 * 所有接口都有访问限制。通配的限制是：200次/分钟；150000次/天
 * （维度是每个商家、每个接口都有150000的访问机会）
 *  如果你调用的数量超过了限制，那么接口会以错误码的形势返回，并不会阻止访问。只是生成一条错误码放到返回的数据结构里。
 * 技术群：群名称是YHD ISV 技术交流2群 247025520/139932165; 个人QQ号：30508728/174648482
 * @author Administrator
 *
 */

public class YhdTaskStart{

	public static Object lock = new Object();
//	private static final Logger logger = Logger.getLogger(YhdTaskStart.class);

	public void startTask() {
		//一号店好药师健康管获取处方药订单，并调用运营中心保存处方药订单数据接口，定时任务--正常抓单 
		Thread cfyOrderThread = new Thread(new Runnable() {
			public void run() {		
				Timer getCfyOrderTimer = new Timer();
				ObtainCfyOrderTask cfyOrderTask = new ObtainCfyOrderTask(Integer.parseInt(TaskConfig.getValue("cfy_normalorder_forward_minute")));
				getCfyOrderTimer.schedule(cfyOrderTask, new Date(),
						new Long(TaskConfig.getValue("cfy_grad_normalorder_interval_millisecond")));
			}
		});		
		cfyOrderThread.start();
		
		//一号店好药师健康管获取处方药订单，并调用运营中心保存处方药订单数据接口，定时任务--抓漏单  
		Thread cfyOrderThreadLeak = new Thread(new Runnable() {
			public void run() {		
				Timer getCfyOrderTimer = new Timer();
				ObtainCfyOrderTask cfyOrderTask = new ObtainCfyOrderTask(Integer.parseInt(TaskConfig.getValue("cfy_leakorder_forward_minute")));
				getCfyOrderTimer.schedule(cfyOrderTask, new Date(),
						new Long(TaskConfig.getValue("cfy_grad_leakorder_interval_millisecond")));
			}
		});		
		cfyOrderThreadLeak.start();
		
		//一号店发货定时任务 由s01(ERP已发货)更新状态为s02(物流信息(运单号/物流公司)同步并通知至1号店平台)
		Thread expressUpdTask = new Thread(new Runnable() {
			public void run() {		
				Timer orderExportTimer = new Timer();	
				ExpressUpdTask updateExpressTask = new ExpressUpdTask();
				orderExportTimer.schedule(updateExpressTask, new Date(),
						new Long(TaskConfig.getValue("cfy_normal_expressupd_interval_millisecond")));
			}
		});
		expressUpdTask.start();
		

		//一号店校验订单信息是否为取消状态定时任务--正常抓取消订单   更新状态为s04
		Thread cancelOrderTask = new Thread(new Runnable() {
			public void run() {		
				Timer orderExportTimer = new Timer();	
				ObtainCancelOrderTask cancelOrderTask = new ObtainCancelOrderTask(Integer.parseInt(TaskConfig.getValue("cfy_normal_cancelorder_forward_day")));
				orderExportTimer.schedule(cancelOrderTask, new Date(),
						new Long(TaskConfig.getValue("cfy_grad_normal_cancelorder_interval_millisecond")));
			}
		});		
		cancelOrderTask.start();
		
		//一号店校验订单信息是否为取消状态定时任务--抓取消订单漏单  更新状态为s04
		Thread cancelOrderTaskLeak = new Thread(new Runnable() {
			public void run() {
				Timer orderExportTimer = new Timer();	
				ObtainCancelOrderTask cancelOrderTask = new ObtainCancelOrderTask(Integer.parseInt(TaskConfig.getValue("cfy_leak_cancelorder_forward_day")));
				orderExportTimer.schedule(cancelOrderTask, new Date(),
						new Long(TaskConfig.getValue("cfy_grad_leak_cancelorder_interval_millisecond")));
			}
		});		
		cancelOrderTaskLeak.start();
		
		
		/*//一号店库存更新定时任务
		Thread stockUpdTask = new Thread(new Runnable() {
			public void run() {
				Timer orderExportTimer = new Timer();	
				StockUpdTask stockPriceTask = new StockUpdTask();
				orderExportTimer.schedule(stockPriceTask, new Date(),
						new Long(TaskConfig.getValue("cfy_normal_stockupd_interval_millisecond")));
			}
		});
		stockUpdTask.start();
		
		//一号店虚拟库存更新定时任务
		Thread virtualStockThread = new Thread(new Runnable() {
			public void run() {		
				Timer virtualStockTime = new Timer();	
				VirtualStockTask virtualStockTask = new VirtualStockTask();
				virtualStockTime.schedule(virtualStockTask, new Date(),
						new Long(TaskConfig.getValue("cfy_normal_virtualstockupd_interval_millisecond")));
			}
		});		
		virtualStockThread.start();*/
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//定时从1号店抓取处方药订单
		YhdTaskStart task = new YhdTaskStart();
		task.startTask();
		//定时调用运营中心接口,获取药师审核通过订单,保存至订单中心 订单状态为s00
		new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
	}
}

