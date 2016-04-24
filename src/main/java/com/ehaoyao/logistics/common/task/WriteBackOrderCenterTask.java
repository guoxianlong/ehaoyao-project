/**
 * 
 */
package com.ehaoyao.logistics.common.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.service.WaybillDetailService;
import com.ehaoyao.logistics.common.service.WaybillInfoService;


/**
 * @author xushunxing 
 * @version 创建时间：2016年4月21日 下午2:52:07
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public class WriteBackOrderCenterTask {
	private static final Logger logger = Logger.getLogger(ExpressInfoInitTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	//属性注入
	@Autowired
	WaybillInfoService waybillInfoService;
	@Autowired
	WaybillDetailService waybillDetailService;
	
	@Autowired
	ToLogisticsCenterService toLogisticsService;
	
	@Autowired
	ExpressInfoService expressInfoService;
	/**
	 * 
	* @Description:将物流中心已妥投(s04)、未回写的运单 回写到订单中心
	* @param 
	* @return void
	* @throws
	 */
	public void writeBackOrderCenterTask() {
		/*1、去物流中心抓取   已妥投、未回写、五天内的运单集合*/
		//1.1   抓运单开始时间、结束时间
		String writeBackDateStr = appConfigs.getString("writeBackDate");
		int writeBackDate = Integer.parseInt(writeBackDateStr);
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -writeBackDate);
		Date startTime = calendar.getTime();
		Date endTime = new Date();
		 //1.2   运单状态、是否回写
		String waybillStatus="s04";
		int  isWriteback = 0;
		 //1.3  调用业务层,抓取运单集合
	/*	waybillInfoService.queryWayBillInfoList(date, waybillSource, waybillStatusList)*/
		/*2、遍历运单集合，准备批量处理*/
		/*3、将运单回写到订单中心,成功后，更新该运单对应订单在订单中心的数据（order_status、expire_time）、
		 * 更新该运单在物流中心的数据（is_writeback）*/
	}
	
}
