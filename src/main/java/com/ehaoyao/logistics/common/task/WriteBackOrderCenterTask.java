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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.service.OrderInfoService;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.service.WayBillDetailService;
import com.ehaoyao.logistics.common.service.WayBillInfoService;
import com.ehaoyao.logistics.common.utils.HttpUtils;
import com.ehaoyao.logistics.common.utils.VersionCachePool;
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
	private static final Logger logger = Logger.getLogger(WriteBackOrderCenterTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	private static String smsUrl = appConfigs.getString("smsUrl");
	//属性注入
	@Autowired
	WayBillInfoService wayBillInfoService;
	@Autowired
	WayBillDetailService wayBillDetailService;	
	@Autowired
	ToLogisticsCenterService toLogisticsService;	
	@Autowired
	ExpressInfoService expressInfoService;
	@Autowired
	OrderInfoService orderInfoService;
	/**
	 * 
	* @Description:将物流中心已妥投(s04)、未回写的运单回写到订单中心后,通知短信平台该运单已妥投，并将运单从缓存中删除.
	* 主要操作：只有该运单对应订单中心的订单orderStatus=s02,
	* 才对此订单做更新,订单中心更新字段order_status、expire_time,物流中心BillInfo的iswriteback变为1.
	* @param 
	* @return void
	* @throws
	 */
	public void writeBackOrderCenter() {
		Date programStartDate = new Date();//程序开始时间
		logger.info("【物流中心已妥投(s04)、未回写的运单 回写到订单中心程序开始！！！】");
		/*1、去物流中心抓取   已妥投、未回写、5天内的运单集合*/
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
		wayBillInfoVo.setLastTimeStart(startTime);
		wayBillInfoVo.setLastTimeEnd(endTime);
		wayBillInfoVo.setWaybillStatus(waybillStatus);
		wayBillInfoVo.setIsWriteback(isWriteback);
		 //1.4  调用业务层,抓取运单集合
		List<WayBillInfo> wayBillInfoList = new ArrayList<WayBillInfo>();
		try {
			wayBillInfoList = wayBillInfoService.queryWayBillInfoList(wayBillInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【抓取S04运单集合的程序出错！！！】", e);
		}
		if(wayBillInfoList.size()==0){
			logger.info("【查到物流中心已妥投(s04)、未回写的运单为0条,物流回写程序结束！！！】");
			return ;
		}
		/*2、遍历运单集合，准备批量处理*/
		int totalUpdateOrderInfoCount=0;//累计更新订单中心数据数
		int totalWriteBackCount = 0;//累计回写条数
		int totalSendSMSCount=0;//通知短信平台发送数
		int totalRemoveMemcachedWithWBILCount=0;//累计删除缓存数
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
			int updateOrderInfoCount=0;
			int removeMemcachedWithWBIL=0;
			try {
				//3.1 更新订单中心的order_info,只更新order_status=s02的订单,更新字段为expire_time、order_status
				updateOrderInfoCount = orderInfoService.writeBackUpdateOrderInfo(subList);
				/**
				 * 可以允许在缓存中多次删同一运单,保证已回写的运单肯定被删除.
				 */
				//3.2 将已妥投运单从缓存中删除 
				removeMemcachedWithWBIL=removeMemcachedWithWBIL(subList);
				//3.3 将物流中心的wayBillInfo的isWriteBack标记为1
				writeBackCount = wayBillInfoService.writeBackUpdateWayBillInfo(subList);
			} catch (Exception e) {
				logger.error("【已妥投运单集合回写订单中心程序报错！！！】", e);
			}
				/**
				 * 运单回写成功后，才可以通知短信平台,防止多次通知,即使调用短信业务失败也不能多次发送短信.
				 */
			// 3.4 通知短信平台发送短信()
			int sendSMSCount =0;
			if(writeBackCount>0){
				try {
					sendSMSCount = sendSMSWithWayBillInfo(subList);				
				} catch (Exception e) {
					logger.error("【通知短信平台发送短信程序异常！！！】", e);
				}				
			}
			totalSendSMSCount+=sendSMSCount;
			totalUpdateOrderInfoCount+=updateOrderInfoCount;
			totalWriteBackCount+=writeBackCount;
			totalRemoveMemcachedWithWBILCount+=removeMemcachedWithWBIL;
			logger.info("【共从物流中心获取已妥投、未回写运单"+wayBillInfoList.size()+"条记录，每次处理"+batchCount+"条，"
					+ "共需处理"+((wayBillInfoList.size()-1)/batchCount+1)+"次，当前处理第"+count+"次,/n"
					+ "本次更新订单中心数据成功:"+updateOrderInfoCount+"条,累计更新订单中心数据成功"+totalUpdateOrderInfoCount+"条,"
					+ "回写成功:"+writeBackCount+"条,累计回写成功"+totalWriteBackCount+"条,"
					+ "通知短信平台发送数"+sendSMSCount+"条,累计发送数"+totalSendSMSCount+"条】");
			count++;//每次处理后加1
		}
		Date programEndDate = new Date();
		logger.info("【本次程序--累计删除缓存总数：---"+totalRemoveMemcachedWithWBILCount+"】");
		logger.info("【物流中心已妥投(s04)、未回写的运单回写"+totalWriteBackCount+"条,共耗时："+(programEndDate.getTime()-programStartDate.getTime())/1000+"s,回写到订单中心程序结束！！！】");
	}
	
	
	
	/**
	 * 
	* @Description:根据已妥投运单，通知短信平台发送短信给收货人
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int sendSMSWithWayBillInfo(List<WayBillInfo> wayBillInfoList){
		int successCount=0;//短信发送成功总数
		if(null == wayBillInfoList || wayBillInfoList.size()==0){
			return successCount;
		}
		/*1、遍历妥投运单集合,调用接口发短信*/
		for (WayBillInfo wayBillInfo : wayBillInfoList) {
			String params = "type=1&orderFlag=" + wayBillInfo.getOrderFlag()
					+ "&orderNumber=" + wayBillInfo.getOrderNumber()
					+ "&expressNo=" + wayBillInfo.getWaybillNumber()
					+ "&expressComName=" + wayBillInfo.getWaybillSource();
			try {
				String res = HttpUtils.sendPost(smsUrl, params);
				JSONObject json = JSONObject.fromObject(res);
				String code = json.getString("code");
				if("1".equals(code)){
					successCount++;					
				}else{
					logger.info("【向短信平台发送短信，调运营中心接口失败！！！,请求url:" + smsUrl + ",传递的参数："
							+ params + ",接口返回的结果：" + res+"】");
				}
			} catch (Exception e) {
				logger.error("【调用短信通知接口程序异常！！！,请求参数:"+params+"】");
			}			
		}
		return successCount;
	}
	
	
	/**
	 * 
	* @Description:将已妥投运单集合从缓存中移除
	* @param @param wayBillInfoList
	* @param @return
	* @return 
	* @throws
	 */
	public int removeMemcachedWithWBIL(List<WayBillInfo> wayBillInfoList){
		int count =0;
		for (WayBillInfo wayBillInfo : wayBillInfoList) {
			try {
//				Object object=VersionCachePool.getObject(wayBillInfo.getWaybillSource()+"_"+wayBillInfo.getWaybillNumber());
//				System.out.println(object);
				boolean remove = VersionCachePool.remove(wayBillInfo.getWaybillSource()+"_"+wayBillInfo.getWaybillNumber());
				if(!remove){				
					logger.info(
							"【从缓存中不存在该运单物流信息！！！不能删除此运单缓存！！！来源+运单号：" + wayBillInfo.getWaybillSource()+ "_" + wayBillInfo.getWaybillNumber()+"】");
				}
				count++;
			} catch (Exception e) {
				logger.error(
						"【从缓存中删除已妥投、已回写运单程序错误！！来源+运单号：" + wayBillInfo.getWaybillSource()
								+ "_" + wayBillInfo.getWaybillNumber()+"】", e);
			}
		}
		logger.info("本次删除缓存数---------------------------："+count);
		return count;
	}
}
