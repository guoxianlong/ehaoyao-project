package com.ehaoyao.opertioncenter.auditOrder.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderQueryParam;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderResponse;
import com.ehaoyao.opertioncenter.auditOrder.service.AuditPAOrderService;
import com.pajk.openapi.codec.client.RequestEncoder;
import com.pajk.openapi.codec.client.RequestEntity;
import com.pajk.openapi.codec.client.ResponseDecoder;

/**
 * @author xushunxing 
 * @version 创建时间：2015年10月19日 下午4:08:48
 * 类说明
 */
@Service
public class AuditPAOrderServiceImpl implements AuditPAOrderService{
	private static final Logger logger = Logger.getLogger(AuditPAOrderServiceImpl.class);
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	* @Description:调用平安订单查询接口，获得平安订单数据包装对象
	* @param @param paOrderQueryParam
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	 */
	public PAOrderResponse getPAOrderResponse(PAOrderQueryParam paOrderQueryParam )throws Exception {
		String partnerId = "haoyaoshi_01";
		// 请求/响应报文使用 
		String key = "8aaa9339547fb66351e4a66972cfe359"; 
		// 测试环境apiId 
		String apiId = "4e445b9deb45ab69954fdc0a4fbec65c#PROD"; 
		String apiName = "B2cQueryOrderInformation"; 
		Long userVenderId = new Long(2083950007);
		String apiGroup = "shennongLogistics";
		// 开始请求报⽂文编码 
		RequestEncoder encoder = new RequestEncoder(partnerId, key, apiId);
		// 填充API参数 
		encoder.addParameter(userVenderId);
		//订单创建时间   开始
		encoder.addParameter(paOrderQueryParam.getStartCreated());
		//订单创建时间   结束
		encoder.addParameter(paOrderQueryParam.getEndCreated());
		//  支付状态: 2:已支付;
		encoder.addParameter("2");
		//	退款状态
		encoder.addParameter("E");
		// 订单类型
		encoder.addParameter(paOrderQueryParam.getOrderType());
		//  当前页
		encoder.addParameter(paOrderQueryParam.getPageNo());
		//  每页显示记录数
		encoder.addParameter(paOrderQueryParam.getPageSize());
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(paOrderQueryParam);
		logger.info("查询平安处方药订单参数=="+jo.toString());
		// 结束请求报⽂文编码,作为HTTP POST BODY 
		RequestEntity re = encoder.encode();
		String pastData = re.getFormParams();
		String url = "http://openapi.jk.cn/api/v1/"+apiGroup+"/"+apiName + "?";
		url += re.getQueryParams();
		url += "&" + pastData;
		logger.info("url="+url);
		// 客户端发送HTTP POST请求
		// 获得HTTP相应结果(JSON格式) 
		String text = restTemplate.postForObject(url, null, String.class);
		// 从response中提取key=object的value 
		Map obj = JSON.parseObject(text,Map.class);
		// 响应结果解码 
		ResponseDecoder decoder = new ResponseDecoder(key);
		boolean decoded = decoder.decode(obj.get("object").toString()); 
		PAOrderResponse paOrderResponse =null;
		if(decoded){
			// 当解码成功时，获得上层业务数据    
			String data = decoder.getData();
			data = data.substring(1,data.length()-1);
			data = data.replaceAll("\\\\","");
			// 将json数据转换为  平安订单数据包装对象
			paOrderResponse=JSONObject.parseObject(data, PAOrderResponse.class);		
		}else{			
			logger.error("查询平安订单接口秘钥配置错误，url：" + url);
		}
		return paOrderResponse;
	}

	/**
	* @Description:审核平安订单
	* @param @param bizOrderId
	* @param @param result
	* @param @param resultMsg
	* @param @param remark
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	@Override
	public String auditOrder(long bizOrderId, int result, String resultMsg,
			String remark) throws Exception {
		String msg =null;
		//1  配置   调用审核接口的参数
		String partnerId = "haoyaoshi_01";
		// 1.1请求响应报文使用 
		String key = "8aaa9339547fb66351e4a66972cfe359"; 
		// apiId，            平安平台还未开发出测试接口
		String apiId = "51ba608c079c53c3a1f98b9ab2837633#PROD"; 
		String apiName = "B2cNoticePrescribedOrder"; 
		Long userVenderId = new Long(2083950007);
		String apiGroup = "shennongLogistics";
		// 1.3 开始请求报⽂文编码 
		RequestEncoder encoder = new RequestEncoder(partnerId, key, apiId);
		// 1.4  填充API参数 
		encoder.addParameter(userVenderId);
		encoder.addParameter(bizOrderId);
		encoder.addParameter(result);
		encoder.addParameter(resultMsg);
		encoder.addParameter(remark);
		// 1.5  结束请求报⽂文编码,作为HTTP POST BODY 
		RequestEntity re = encoder.encode();
		String url = "http://openapi.jk.cn/api/v1/"+apiGroup+"/"+apiName + "?";
		url += re.getQueryParams();
		url += "&" + re.getFormParams();
		// 2  客户端发送HTTP POST请求，获得数据后，解析成符合要求的json字符串
		// 2.1  客户端发送HTTP POST请求， 获得HTTP相应结果(JSON格式)
		String text = restTemplate.postForObject(url, null, String.class);
		// 2.2从response中提取key=object的value 
		Map obj = JSON.parseObject(text,Map.class);
		// 2.3响应结果解码 
		ResponseDecoder decoder = new ResponseDecoder(key);
		boolean decoded = decoder.decode(obj.get("object").toString()); 
		if(decoded){    
			// 2.4 当解码成功时，获得上层业务数据    
			String data = decoder.getData();
			//2.5  当获得的类似的json数据有值时， 对该数据做一定处理，最终获得标准的json字符串
			if(data != null && !"".equals(data)){
				data = data.substring(1,data.length()-1);
				data = data.replaceAll("\\\\","");
				JSONObject json = JSONObject.parseObject(data);
				boolean rs = json.getBoolean("success");
				if(rs){
					return msg;
				}else{
					msg = json.getString("resultMsg");						
				}
			}else{
				msg = "获取data的值为" + data;
			}
		}else{
			msg = "审核失败！！接口秘钥配置错误，请联系开发人员！！！";
			logger.info("审核平安订单接口秘钥配置错误，url：" + url+",返回结果：" + text);
		}
	return msg;
	}
	/**
	 * 
	* @Description:更改平安订单状态的工具类
	* @param @param bizOrderId
	* @param @param result
	* @param @param resultMsg
	* @param @param remark
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	 */
/*	@Test
	public void auditOrderTest(long bizOrderId, int result, String resultMsg,
//			String remark) throws Exception {
		String msg =null;
		//1  配置   调用审核接口的参数
		String partnerId = "haoyaoshi_01";
		// 1.1请求响应报文使用 
		String key = "e420d293b0ce4e547266471fd38dd609"; 
		// 1.2测试环境apiId，            平安平台还未开发出测试接口
		String apiId = "51ba608c079c53c3a1f98b9ab2837633#TEST"; 
		String apiName = "B2cNoticePrescribedOrder"; 
		Long userVenderId = new Long(989810607);
		String apiGroup = "shennongLogistics";
		// 1.3 开始请求报⽂文编码 
		RequestEncoder encoder = new RequestEncoder(partnerId, key, apiId);
		// 1.4  填充API参数 
		long bizOrderId=2038480303l;
		int result=0;
		String resultMsg="未审核";
		String remark="111";
		encoder.addParameter(userVenderId);
		encoder.addParameter(bizOrderId);
		encoder.addParameter(result);
		encoder.addParameter(resultMsg);
		encoder.addParameter(remark);
		// 1.5  结束请求报⽂文编码,作为HTTP POST BODY 
		RequestEntity re = encoder.encode();
		String url = "http://openapi.test.pajk.cn/api/v1/"+apiGroup+"/"+apiName + "?";
		url += re.getQueryParams();
		url += "&" + re.getFormParams();
		// 2  客户端发送HTTP POST请求，获得数据后，解析成符合要求的json字符串
		// 2.1  客户端发送HTTP POST请求， 获得HTTP相应结果(JSON格式)
		String text = restTemplate.postForObject(url, null, String.class);
		// 2.2从response中提取key=object的value 
		Map obj = JSON.parseObject(text,Map.class);
		// 2.3响应结果解码 
		ResponseDecoder decoder = new ResponseDecoder(key);
		boolean decoded = decoder.decode(obj.get("object").toString()); 
		if(decoded){    
			// 2.4 当解码成功时，获得上层业务数据    
			String data = decoder.getData();
			//2.5  当获得的类似的json数据有值时， 对该数据做一定处理，最终获得标准的json字符串
			if(data != null && !"".equals(data)){
				data = data.substring(1,data.length()-1);
				data = data.replaceAll("\\\\","");
				JSONObject json = JSONObject.parseObject(data);
				boolean rs = json.getBoolean("success");
				if(rs){
				}else{
					msg = json.getString("resultMsg");						
				}
			}else{
				msg = "获取data的值为" + data;
			}
		}else{
			msg = "审核失败！！接口秘钥配置错误，请联系开发人员！！！";
			logger.info("审核平安订单接口秘钥配置错误，url：" + url);
		}
	}*/
}
