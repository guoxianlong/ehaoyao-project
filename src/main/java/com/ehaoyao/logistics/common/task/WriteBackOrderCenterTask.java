/**
 * 
 */
package com.ehaoyao.logistics.common.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.service.WayBillDetailService;
import com.ehaoyao.logistics.common.service.WayBillInfoService;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;


/**
 * @author xushunxing 
 * @version 创建时间：2016年4月21日 下午2:52:07
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
@Component("writeBackOrderCenterTask")
public class WriteBackOrderCenterTask {
	private static final Logger logger = Logger.getLogger(ExpressInfoInitTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	//属性注入
	@Autowired
	WayBillInfoService wayBillInfoService;
	@Autowired
	WayBillDetailService wayBillDetailService;	
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
	public void writeBackOrderCenter() {
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
		// 1.3 封装查询条件包装类
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setCreateTimeStart(startTime);
		wayBillInfoVo.setCreateTimeEnd(endTime);
		wayBillInfoVo.setWaybillStatus(waybillStatus);
		wayBillInfoVo.setIsWriteback(isWriteback);
		 //1.4  调用业务层,抓取运单集合
		List<WayBillInfo> wayBillInfoList = new ArrayList<WayBillInfo>();
		try {
			wayBillInfoList = wayBillInfoService.queryWayBillInfoList(wayBillInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("抓取S04运单集合的程序出错！！", e);
		}
		/*2、遍历运单集合，准备批量处理*/
		int totalWriteBackCount = 0;//累计最终成功回写条数
		int batchCount =Integer.parseInt(appConfigs.getString("writeBackBatchCount"));//每次处理条数
		int count = 1;//当前处理次数
		for(int i = 0;i<wayBillInfoList.size();i+=batchCount){
			List<WayBillInfo>  subList = null;
			if((i+batchCount)>wayBillInfoList.size()){
				subList = wayBillInfoList.subList(i,wayBillInfoList.size());
			}else{
				subList = wayBillInfoList.subList(i,i+batchCount);
			}
			/*3、将运单回写到订单中心,成功后，更新该运单对应订单在订单中心的数据（order_status、expire_time）、
			 * 更新该运单在物流中心的数据（is_writeback）*/
			int writeBackCount = 0 ;//每次最终成功回写条数
			try {
				writeBackCount = wayBillInfoService.writeBackToOrdercenter(subList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("已妥投运单集合回写订单中心程序报错！！！！", e);
			}
			totalWriteBackCount+=writeBackCount;
			logger.info("【共从物流中心获取已妥投、未回写运单"+wayBillInfoList.size()+"条记录，每次处理"+batchCount+"条，共需处理"+((wayBillInfoList.size()-1)/batchCount+1)+"次，当前处理第"+count+"次，当前成功回写"+writeBackCount+"条，累计成功回写"+totalWriteBackCount+"条】");
			count++;//每次处理后加1
		}
		
	}
	
}
