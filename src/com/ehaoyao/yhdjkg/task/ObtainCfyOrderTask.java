package com.ehaoyao.yhdjkg.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.yhdjkg.model.operation_center.InvoiceInfo;
import com.ehaoyao.yhdjkg.model.operation_center.OrderDetail;
import com.ehaoyao.yhdjkg.model.operation_center.OrderInfo;
import com.ehaoyao.yhdjkg.start.YhdTaskStart;
import com.ehaoyao.yhdjkg.utils.Base64Util;
import com.ehaoyao.yhdjkg.utils.BaseMap;
import com.ehaoyao.yhdjkg.utils.DateUtil;
import com.ehaoyao.yhdjkg.utils.EncryptionUtil;
import com.ehaoyao.yhdjkg.utils.HttpUtils;
import com.ehaoyao.yhdjkg.utils.PostClient;
/**
 * 
 * @author longshanw
 * @date 2016-4-1
 * @desciption 定时获取一号店处方药信息并调用运营中心"保存处方药"接口
 */
public class ObtainCfyOrderTask extends TimerTask{
	
	private static final Logger logger = Logger.getLogger(ObtainCfyOrderTask.class);
	
	private int timeInteval;
	
	public int getTimeInteval() {
		return timeInteval;
	}

	public void setTimeInteval(int timeInteval) {
		this.timeInteval = timeInteval;
	}

	
	
	public ObtainCfyOrderTask() {
		super();
	}

	public ObtainCfyOrderTask(int timeInteval) {
		super();
		this.timeInteval = timeInteval;
	}



	static String channel_yhdcfy=BaseMap.getValue("channel_yhdcfy");

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void run() {
		synchronized ( YhdTaskStart.lock ) {
			logger.info("----------抓取处方药订单定时任务获取lock----------");
			getCfyOrderTask();
			logger.info("----------抓取处方药订单定时任务释放lock----------");
		}
	}
	
	
	
	/**
	 * 获取处方药订单
	 */
	public void getCfyOrderTask() {
		Date start_Time = new Date();
		Object[] respObj = null;
		logger.info("【"+channel_yhdcfy+" 插入订单开始时间】:"+DateUtil.formatCurrentDateToStandardDate());
		int page = 1;
		int pageSize = 50;
		int pageCount = 0;
		
		List<JSONArray> yhdordersList = new ArrayList<JSONArray>();
		// 系统级参数设置
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.orders.get");
		// 应用级参数设置

		//改为最新时间推1小时,接口通过付款时间抓单
		java.util.Calendar c = java.util.GregorianCalendar.getInstance();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			c.setTime(d);
			c.add(Calendar.MINUTE, - this.timeInteval);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(""+channel_yhdcfy+" 时间转换失败"+ex.getMessage());
		}
		String startTime = sdf.format(c.getTime());
		
		paramMap.put("startTime", startTime);
		String endTime=ObtainCfyOrderTask.sdf.format(new Date());
		paramMap.put("endTime", endTime);
//		paramMap.put("orderStatusList", "ORDER_WAIT_SEND");
		paramMap.put("orderStatusList", "ORDER_FINISH");
		paramMap.put("dateType", "5");
		paramMap.put("pageRows", pageSize + "");
		try {
			logger.info("【获取1号店健康馆处方药，请求参数：method=yhd.orders.get;    paramMap="+paramMap.toString()+"】");
			do {
				paramMap.put("curPage", page + "");
				String responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,
						paramMap, BaseMap.SECRET_KEY);
				if( responseData == null || "".equals(responseData)){
					logger.info("【获取1号店健康馆处方药，调用一号店订单接口失败，返回数据为空】responseData:"+responseData);
					return;
				}
				JSONObject jo = JSON.parseObject(responseData);
				int totalCount = jo.getJSONObject("response").getIntValue("totalCount");
				
				logger.info("【获取1号店健康馆处方药，共"+totalCount+"条,共"+pageCount+"页,每页"+pageSize+"条,第"+page+"页，订单列表接口返回数据】："+responseData);
				
				
				if (totalCount < 1){
					JSONArray errDetailInfo = jo.getJSONObject("response").getJSONObject("errInfoList").getJSONArray("errDetailInfo");
					logger.error("【获取1号店健康馆处方药，调用一号店订单接口返回错误信息】:" + errDetailInfo.toString());
					return;
				}
				pageCount = totalCount / pageSize
						+ (totalCount % pageSize == 0 ? 0 : 1);
				//订单结果集JSON数组格式
				JSONArray orderJA = jo.getJSONObject("response").getJSONObject(
						"orderList").getJSONArray("order");
				int count = orderJA.size();
				if( count < 1 ){
					logger.info("【获取1号店健康馆处方药，没有获取一号店新订单】");
					return;
				}
				StringBuilder orderCode = new StringBuilder(); //所有的订单
				for (int i = 0; i < count; i++) {
					if( i == 0)
						//获取所有的订单编号，以","隔开
						orderCode.append(orderJA.getJSONObject(i).getString("orderCode"));
					else
						orderCode.append("," + orderJA.getJSONObject(i).getString("orderCode"));
				}
				//根据订单号批量获取订单详情
				responseData = orderDetail(orderCode.toString());
				logger.info("一号店批量获获取订单详情，请求订单编号："+orderCode.toString()+"，返回JSON："+responseData);
				responseData = responseData.replaceAll("\\\\", "/").replaceAll(
						"/\"", "");
				if( responseData == null || "".equals(responseData) ){
					logger.info("【调用一号店订单详细接口出现错误，返回空值,订单号】:" + orderCode.toString() );
					return;
				}
				JSONObject orderJo = JSON.parseObject(responseData);
				JSONObject orderInfoList = orderJo.getJSONObject("response")
						.getJSONObject("orderInfoList");
				JSONArray orderInfo = orderInfoList.getJSONArray("orderInfo");

				//将每页查询的一号店好药师健康馆处方药订单，放入yhdordersList集合
				yhdordersList.add(orderInfo);
				
				page++;
			} while (page <= pageCount);
			respObj = dealCfyOrder(yhdordersList);
			if(respObj!=null){
				if(respObj[0]!=null){
					logger.error("【一号店抓单程序出现异常，异常信息】:" + respObj[0].toString());
					return;
				}
				if(respObj[2]!=null){
					logger.info("【一号店抓单，调用运营中心保存处方药订单接口，返回信息】:" + respObj[2].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【一号店抓单程序出现异常，异常信息】:" + e.getMessage());
		}
		logger.info("【"+channel_yhdcfy+" 共插入" + (respObj!=null&&respObj[1]!=null?respObj[1]:0) + "条数据，插入订单结束时间】:"+DateUtil.formatCurrentDateToStandardDate()+",共耗时:"+(new Date().getTime() - start_Time.getTime())+"ms***");
	}

	/**
	 * 处理并调用运营中心保存处方药订单接口
	 * @param ordersList
	 */
	public Object[] dealCfyOrder(List<JSONArray> ordersList) throws Exception{
		JSONArray yhdOrderInfoArr;
		JSONObject yhdOrderDetail;
		JSONObject yhdOrderItemList;
		JSONArray yhdOrderItemArr;
		JSONObject yhdOrderItem;
		JSONObject jobj;//单笔订单集合
		OrderInfo orderInfo;//单笔订单_订单主表
		OrderDetail orderDetail;//单笔订单_订单明细
		InvoiceInfo invoiceInfo = null;//单笔订单_发票信息
		List<OrderDetail> orderDetailPrescList;//单笔订单_订单明细集合
		JSONArray jArr = new JSONArray();//获取到一号店好药师健康馆订单集合
		Object[] retObj = new Object[3];//Object[0]:异常信息 Object[1]：处方药总条数 Object[2]：调用接口返回信息
		int count = 0;
		for(int k=0;k<ordersList.size();k++){
			yhdOrderInfoArr = ordersList.get(k);
			if(yhdOrderInfoArr==null){
				 retObj[0] = "【一号店平台订单数据封装错误！】";
				 return retObj;
			}
			for (int i = 0; i < yhdOrderInfoArr.size(); i++) {
				JSONObject order = yhdOrderInfoArr.getJSONObject(i);
				yhdOrderDetail = JSONObject.parseObject(order.get("orderDetail")!=null?order.get("orderDetail").toString():"");

				/**
				 * 非处方药则跳出循环
				 * 订单类型：0:前台普通订单1:团购订单 2:EPP订单 3:处方药订单 4:B2B订单 5:店中店代售 
				 * 6:平安3g标志 10:定期购订单 11:预售订单 12:闪购 13:合约机 14:手机充值 15:选号入网
				 * 16:二手品 17:1闪团订单 18:生活团订单 19:定金预售 20:社区团订单 21:微店订单22:OMO订单
				 * 23:跨境通海购订单 24:快拍订单 25:手机流量充值订单 26:支付宝渠道订单 27:门店订单
				 */
				if(!"3".equals(yhdOrderDetail.getString("businessType"))){
					continue;
				}
				
				System.out.println("OrderNumber"+yhdOrderDetail.getString("orderCode")+"businessType="+yhdOrderDetail.getString("businessType"));
				
				yhdOrderItemList = JSONObject.parseObject(order.get("orderItemList")!=null?order.get("orderItemList").toString():"");
				yhdOrderItemArr = JSONArray.parseArray(yhdOrderItemList.get("orderItem")!=null?yhdOrderItemList.get("orderItem").toString():"");
				
				jobj = new JSONObject();
				orderDetailPrescList = new ArrayList<OrderDetail>();
				orderInfo = new OrderInfo();
				
				
				//设置订单主表值
				orderInfo.setAddressDetail(yhdOrderDetail.getString("goodReceiverProvince")+yhdOrderDetail.getString("goodReceiverCity")+yhdOrderDetail.getString("goodReceiverCounty")+yhdOrderDetail.getString("goodReceiverAddress"));
				orderInfo.setCity(yhdOrderDetail.getString("goodReceiverCity"));
				orderInfo.setArea(yhdOrderDetail.getString("goodReceiverCounty"));
				orderInfo.setDiscountAmount(yhdOrderDetail.getBigDecimal("orderPromotionDiscount"));
				orderInfo.setExpressPrice(yhdOrderDetail.getBigDecimal("orderDeliveryFee"));
				orderInfo.setInvoiceStatus(invoiceStatusTransfer(yhdOrderDetail.getString("orderNeedInvoice")));
				orderInfo.setMobile(yhdOrderDetail.getString("goodReceiverMoblie"));
				orderInfo.setOrderFlag(channel_yhdcfy);
				orderInfo.setOrderNumber(yhdOrderDetail.getString("orderCode"));
				orderInfo.setOrderPrice(yhdOrderDetail.getBigDecimal("productAmount").add(yhdOrderDetail.getBigDecimal("orderDeliveryFee")));
				orderInfo.setOrderTime(yhdOrderDetail.getString("orderCreateTime"));
				if(yhdOrderDetail.getDate("orderPaymentConfirmDate")!=null){
					orderInfo.setPaymentTime(sdf.format(yhdOrderDetail.getDate("orderPaymentConfirmDate")));
				}
				if(yhdOrderDetail.getDate("orderPaymentConfirmDate")!=null&&yhdOrderDetail.getString("payServiceType")!=null){
					orderInfo.setPayStatus(OrderInfo.ORDER_PAY_STATUS_PAID);
				}else{
					orderInfo.setPayStatus(OrderInfo.ORDER_PAY_STATUS_NOPAY);
				}
				orderInfo.setPayType(payTypeTransfer(yhdOrderDetail.getString("payServiceType")));
				orderInfo.setPlatPayPrice(yhdOrderDetail.getBigDecimal("orderPlatformDiscount"));
				orderInfo.setOverReturnFree(yhdOrderDetail.getBigDecimal("orderCouponDiscount"));
				orderInfo.setPrescriptionType("1");
				orderInfo.setPrice(yhdOrderDetail.getBigDecimal("realAmount"));
				orderInfo.setProvince(yhdOrderDetail.getString("goodReceiverProvince"));
				orderInfo.setReceiver(yhdOrderDetail.getString("goodReceiverName"));
				orderInfo.setRemark(yhdOrderDetail.getString("deliveryRemark"));
				orderInfo.setTelephone(yhdOrderDetail.getString("goodReceiverPhone"));
//				orderInfo.setAge(yhdOrderDetail.getInteger(""));
//				orderInfo.setSex(yhdOrderDetail.getString(""));
				
				//设置订单明细值
				if(yhdOrderItemArr!=null && !yhdOrderItemArr.isEmpty()){
					for(int n=0;n<yhdOrderItemArr.size();n++){
						yhdOrderItem = yhdOrderItemArr.get(n)!=null?(JSONObject)yhdOrderItemArr.get(n):null;
						if(yhdOrderItem!=null){
							orderDetail = new OrderDetail();
							orderDetail.setOrderFlag(channel_yhdcfy);
							orderDetail.setOrderNumber(yhdOrderItem.getString("orderId"));
							orderDetail.setCount(yhdOrderItem.getInteger("orderItemNum"));
							orderDetail.setDiscountAmount(yhdOrderItem.getBigDecimal("discountAmoun"));
							orderDetail.setMerchantId(yhdOrderItem.getString("productId"));
							orderDetail.setPrescriptionType(yhdOrderItem.getString(""));
							orderDetail.setPrice(yhdOrderItem.getBigDecimal("orderItemPrice"));
							orderDetail.setProductId(yhdOrderItem.getString("outerId"));
							orderDetail.setProductName(yhdOrderItem.getString("productCName"));
							orderDetail.setTotalPrice(yhdOrderItem.getBigDecimal("orderItemPrice").multiply(new BigDecimal(yhdOrderItem.getInteger("orderItemNum"))));
//							orderDetail.setGiftFlag(yhdOrderItem.getString(""));
//							orderDetail.setPharmacyCompany(yhdOrderItem.getString(""));
//							orderDetail.setProductBrand(yhdOrderItem.getString(""));
//							orderDetail.setProductLicenseNo(yhdOrderItem.getString(""));
//							orderDetail.setProductSpec(yhdOrderItem.getString(""));
//							orderDetail.setUnit(yhdOrderItem.getString(""));
							orderDetailPrescList.add(orderDetail);
						}
					}
				}
				
				
				//判断并设置发票信息
				String invoiceStatus = invoiceStatusTransfer(yhdOrderDetail.getString("orderNeedInvoice"));
				if(invoiceStatus!=null && invoiceStatus.trim().length()>0&&OrderInfo.ORDER_INVOICE_STATUS_YES.equals(invoiceStatus)){
					invoiceInfo = new InvoiceInfo();
					invoiceInfo.setInvoiceStatus(invoiceStatus);
					invoiceInfo.setInvoiceType(invoiceTypeTransfer(yhdOrderDetail.getString("orderNeedInvoice")));
					invoiceInfo.setInvoiceTitle(yhdOrderDetail.getString("invoiceTitle"));
//					jobj.put("invoiceInfo", JSONObject.toJSONString(invoiceInfo));
					jobj.put("invoiceInfo", invoiceInfo);
				}
				
				
//				jobj.put("orderInfo", JSONObject.toJSONString(orderInfo));
				jobj.put("orderInfo", orderInfo);
//				jobj.put("details", JSONArray.toJSONString(orderDetailPrescList));
				jobj.put("details", orderDetailPrescList);
				jArr.add(jobj);
				count++;//计数，统计本次调用接口保存处方药订单数量
			}
		}
		
		logger.info("【调用运营中心保存处方药订单接口，请求主体数据内容】："+jArr.toJSONString());
		
		if(jArr!=null && !jArr.isEmpty()){
			String secretKey_yhdcfy = BaseMap.getValue("secretKey_yhdcfy");
			String reqMethodNam = BaseMap.getValue("reqMethodNam_savePrescOrder");
			String operation_url = BaseMap.getValue("operation_url");
			Map<String, String[]> map=new HashMap<String,String[]>();
			map.put("paramsData", new String[]{Base64Util.encode(jArr.toJSONString(), "UTF-8")});
			//生成加密串sign值
			String sign = EncryptionUtil.generateSign(secretKey_yhdcfy, reqMethodNam, map);
			
			JSONObject json = new JSONObject();
			json.put("sign", sign);
			json.put("channel", channel_yhdcfy);
			json.put("paramsData",  Base64Util.encode(jArr.toJSONString(), "UTF-8"));
			String retStr = HttpUtils.sendPost(operation_url+reqMethodNam,Base64Util.encode(json.toString(),"UTF-8"));
			json = JSONObject.parseObject(retStr);
			
			retObj[1] = count;
			retObj[2] = retStr;
		}else{
			
		}
    	
    	return retObj;
	}

	/**
	 * 调用1号店批量获取订单详情接口
	 * @param orderCode
	 * @return
	 */
	public String orderDetail(String orderCode) {
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.orders.detail.get");
		paramMap.put("orderCodeList", orderCode);

		String responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,
				paramMap, BaseMap.SECRET_KEY);
		return responseData;
	}

	/**
	 * 保存订单信息，
	 * 返回存储情况，是否存储成功
	 * @param order
	 * @return
	 */

	/**
	 * 发票接口，
	 * 返回json格式的字符串
	 * @param orderCodes
	 * @return
	 */
	public String getInvoices(String orderCodes) {
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.invoices.get");
		paramMap.put("orderCodeList", orderCodes);
		String responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,
				paramMap, BaseMap.SECRET_KEY);
		logger.info("订单号："+orderCodes+"调用发票接口返回json："+responseData);
		return responseData;
	}

	
	/**
	 * 根据平安提供的性别枚举值转换为系统定义的枚举值
	 * @param sexValue
	 * @return
	 */
	public String sexTransfer(String sexValue){
		String transferValue = null;
		if(sexValue!=null && sexValue.trim().length()>0){
			if("0".equals(sexValue.trim())){
				transferValue = "FEMALE";
			}else if("1".equals(sexValue.trim())){
				transferValue = "MALE";
			}else{
				transferValue = "UNKNOWN";
			}
		}
		return transferValue;
	}
	
	/**
	 * 支付状态转换
	 * @param payStatus
	 * @return
	 */
	public String payStatusTransfer(String payStatus){
		String payStatusValue = null;
		if(payStatus!=null && payStatus.trim().length()>0){
			if("0".equals(payStatus.trim())){
				payStatusValue = OrderInfo.ORDER_PAY_STATUS_NOPAY;
			}
			if("2".equals(payStatus.trim())){
				payStatusValue = OrderInfo.ORDER_PAY_STATUS_PAID;
			}
		}
		return payStatusValue;
	}
	
	/**
	 * 发票类型转换
	 * @param invoiceType
	 * @return
	 */
	public String invoiceTypeTransfer(String invoiceType){
		String invoiceTypeValue = null;
		if(invoiceType!=null && invoiceType.trim().length()>0){
			if("1".equals(invoiceType.trim()) || "2".equals(invoiceType.trim())){
				invoiceTypeValue = OrderInfo.ORDER_INVOICE_TYPE_PLAIN;
			}
			if("3".equals(invoiceType.trim())){
				invoiceTypeValue = OrderInfo.ORDER_INVOICE_TYPE_VAT;
			}
		}
		return invoiceTypeValue;
	}
	
	/**
	 * 是否开票转换
	 * @param invoiceType
	 * @return
	 */
	public String invoiceStatusTransfer(String invoiceStatus){
		String invoiceStatusValue = null;
		if(invoiceStatus!=null && invoiceStatus.trim().length()>0){
			if("0".equals(invoiceStatus.trim())){
				invoiceStatusValue = OrderInfo.ORDER_INVOICE_STATUS_NO;
			}else{
				invoiceStatusValue = OrderInfo.ORDER_INVOICE_STATUS_YES;
			}
		}
		return invoiceStatusValue;
	}
	
	public String payTypeTransfer(String payType){
		String payTypeValue = null;
		if(payType!=null && payType.trim().length()>0){
			switch (payType) {
			case "0":
				payTypeValue = "ACCOUNTPAY";//账户支付
				break;
			case "1":
				payTypeValue = "ONLINEPAY";//网上支付
				break;
			case "2":
				payTypeValue = "COD";//货到付款
				break;
			case "3":
				payTypeValue = "POSTREMITTANCE";//邮局汇款
				break;
			case "4":
				payTypeValue = "BANKTRANSFER";//银行转账
				break;
			case "5":
				payTypeValue = "POSPAY";//pos机
				break;
			case "6":
				payTypeValue = "WALITO";//万里通
				break;
			case "7":
				payTypeValue = "INSTALLMENTPAYMENT";//分期付款
				break;
			case "8":
				payTypeValue = "CONTRACTPERIOD";//合同账期
				break;
			case "9":
				payTypeValue = "ARRIVALTRANSFER";//货到转账
				break;
			case "10":
				payTypeValue = "ARRIVALPAYCHECK";//货到付支票
				break;
			case "12":
				payTypeValue = "ARRIVALBRUSHALIPAY";//货到刷支付宝
				break;
			default:
				break;
			}
		}
		return payTypeValue;
	}
}
