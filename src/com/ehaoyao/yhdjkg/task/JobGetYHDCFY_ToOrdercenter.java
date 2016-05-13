package com.ehaoyao.yhdjkg.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.yhdjkg.domain.OperationResponse;
import com.ehaoyao.yhdjkg.domain.ParamsData;
import com.ehaoyao.yhdjkg.domain.SendOrderOCT;
import com.ehaoyao.yhdjkg.http.HttpClientWrap;
import com.ehaoyao.yhdjkg.service.IOrderCenterService;
import com.ehaoyao.yhdjkg.utils.Base64Util;
import com.ehaoyao.yhdjkg.utils.BaseMap;
import com.ehaoyao.yhdjkg.utils.DateUtil;
import com.ehaoyao.yhdjkg.utils.EncryptionUtil;
import com.ehaoyao.yhdjkg.utils.JSONUtils;

/**
 *从运营中心获取审核通过之后的处方药信息 （不包含发票信息）
 * @author wls
 */
@Service
public class JobGetYHDCFY_ToOrdercenter {
	
	private static Logger logger=LoggerFactory.getLogger(JobGetYHDCFY_ToOrdercenter.class);
	
	@Resource
	IOrderCenterService orderCenterService;
	@Resource
	private HttpClientWrap httpClient; 

	/**
	 * 从运营中心获取审核通过之后的处方药信息 ,并保存到订单中心
	 */
	public void saveCFYOrdersToOrdercenter(){
		logger.info("【获取审核通过的处方药定时任务开始】");
		saveCFYOrders();
		logger.info("【获取审核通过的处方药定时任务结束】");
	}
	
	/**
	 * 保存数据
	 */
	private void saveCFYOrders() {
		String channel=BaseMap.getValue("channel_yhdcfy");
		String operation_secretKey=BaseMap.getValue("secretKey_yhdcfy");
		Integer hour= Integer.valueOf(BaseMap.getValue("operation_orderInterval_hour"));
		String startTime = DateUtil.getDate(DateUtil.getPreHour(hour), 2, null);// 当前时间向前推迟xxx小时
		String endTime = DateUtil.getDate(new Date(), 2, null);
		int pageSize=50;
		int totalPages=1;
		for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
			logger.info("分页查询，查询{}平台，第 {} 页。",channel,pageNo);
			try {
				totalPages=saveOrderToOrdercenterByPage(channel, operation_secretKey, startTime, endTime, pageSize, pageNo);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("分页查询出错了，错误信息：{}",e);
				continue;
			}
		}
	}//end method
	
	/**
	 * 分页获取运营中心药师审核通过的订单，然后将数据保存到订单中心
	 * @param channel  平台标识
	 * @param operation_secretKey 
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNo
	 * @throws Exception 
	 */
	private Integer saveOrderToOrdercenterByPage(String channel,
			String operation_secretKey, String startTime, String endTime,
			int pageSize, int pageNo) throws Exception {
		String res = sendPost(pageSize,pageNo, channel, operation_secretKey,startTime, endTime);
		logger.info("运营中心返回结果：{}",res);
		OperationResponse result=JSONObject.parseObject(res, OperationResponse.class);
		if(result!=null&&"1".equals(result.getIfSuc())){
			List<ParamsData> data=result.getData();
			logger.info("");
			if(data!=null&&data.size()>0){
				for (ParamsData paramsData2 : data) {
					try {
						orderCenterService.doSaveOneCFYOrder(channel, operation_secretKey, startTime, endTime, pageSize, pageNo, paramsData2);
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("保存订单{},平台标识：{} 的时候出错了，错误信息：{}", paramsData2
								.getOrderInfo().getOrderNumber(), paramsData2
								.getOrderInfo().getOrderFlag(), e);
						continue;
					}
				}
			}else{
				logger.info("调用运营中心的接口返回数据库为空");
			}
			return result.getTotalPages();
		}else{
			logger.info("调用运营中心接口出错了，接口返回信息：{}",res);
		}
		return 0;
	}
	
	
	/**
	 * 调用运营中心接口并返回结果
	 * @param channel 平台标识
	 * @param operation_secretKey  密钥
	 * @param startTime  开始时间
	 * @param endTime 结束时间
	 * @return
	 * @throws Exception 
	 */
	private String sendPost(int pageSize,int pageNo,String channel,String operation_secretKey, String startTime, String endTime) throws Exception {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		JSONObject auditTime = new JSONObject();//查询条件
		auditTime.put("auditTimeStart",  startTime);
		auditTime.put("auditTimeEnd",  endTime);
		auditTime.put("pageSize",  pageSize);
		auditTime.put("pageNo",  pageNo);
		String paramsData=JSONUtils.toJSONString(auditTime);//时间参数
		paramMap.put("paramsData",new String[]{paramsData});//加密参数
		String sign = EncryptionUtil.generateSign(operation_secretKey, BaseMap.getValue("reqMethodNam_getAuditPassOrderInfos"), paramMap);//加密后的sign
		SendOrderOCT param=new SendOrderOCT();//http请求参数
		param.setChannel(channel);
		param.setParamsData(paramsData);
		param.setSign(sign);
		String json=JSONUtils.toJSONString(param);
		logger.info("发送运营加密前的json={}",json);
		String base64Json=Base64Util.encode(json,"UTF-8");
		logger.info("发送运营加密后的json={}",base64Json);
		String res=httpClient.executePOST(BaseMap.getValue("operation_url")+BaseMap.getValue("reqMethodNam_getAuditPassOrderInfos"), base64Json, null);
		return res;
	}
}
