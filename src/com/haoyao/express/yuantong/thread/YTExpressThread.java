package com.haoyao.express.yuantong.thread;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;

import com.haoyao.express.yuantong.javabean.fail.Response;
import com.haoyao.express.yuantong.javabean.successed.Result;
import com.haoyao.express.yuantong.javabean.successed.Ufinterface;
import com.haoyao.express.yuantong.javabean.successed.WaybillProcessInfo;
import com.haoyao.express.yuantong.servicenet.YTServiceNet;
import com.haoyao.express.yuantong.utils.JaxbUtil;
import com.haoys.logisticsserver.ws.client.LogisticsServerClient;
import com.haoys.logisticsserver.ws.service.LogisticsServiceStub.LogisticsDetail;




public class YTExpressThread extends Thread{
static Logger logger=Logger.getLogger(YTExpressThread.class);
private List<String> trackNumberList;
	@Override
	public void run() {
		logger.info("圆通多线程抓单启动新线程，线程名称为##"+Thread.currentThread().getName()+"##");
		List<String> trackNumber=this.getTrackNumberList();
		if (null!=trackNumber&&!trackNumber.isEmpty()) {
			// 用来存储已完结快递单号
			List<String> overLogistics = new ArrayList<String>();
			// 遍历圆通快递单号集合，逐一爬取快递信息
			int is=1;
			for (String trackNum : trackNumber) {
				logger.info(Thread.currentThread().getName()+"开始抓取圆通快递单号为：" + trackNum + "的快递信息,本次第"+is+"个单号");
				List<LogisticsDetail> logDetail = new ArrayList<LogisticsDetail>();
				String strs = trackNum.substring(0, 3);
				String result = "";
				// 截取No:字符串，校正运单号
				if ("No:".equals(strs)) {
					String track = trackNum.substring(3, trackNum.length());
					result = YTServiceNet.getExpressInfo(track.trim());
				} else {
					result = YTServiceNet.getExpressInfo(trackNum.trim());
				}
				if (null != result && !"".equals(result)) {
					// logger.info("圆通快递抓取到的XML信息："+result);
					int index=result.indexOf("<ufinterface>");
					if(index>-1){
						//result=result.replaceAll("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						Ufinterface expInfo=(Ufinterface) JaxbUtil.createXMLToBean(new StringReader(result.trim()), com.haoyao.express.yuantong.javabean.successed.Ufinterface.class);	
					if(null!=expInfo){
						Result res=expInfo.getResult();
						if(null!=res){
							List<WaybillProcessInfo> infoList=res.getWaybillProcessInfo();
							if(null!=infoList&&!infoList.isEmpty()){
								boolean isOver = false;
								for(WaybillProcessInfo expressInfo:infoList){
									LogisticsDetail details = new LogisticsDetail();
									String context=expressInfo.getProcessInfo();
									int cutIndex=context.indexOf(":");
									details.setReceiptTime(expressInfo.getUploadTime());
									details.setContext(context);
									details.setReceiptAddress("");
									details.setTrackingNumber(expressInfo.getWaybillNo());
									if(cutIndex>-1){
										String overText=context.substring(0, cutIndex);
										overText=overText.replaceAll(" ", "");
										if ("客户签收人".equals(overText)) {
											isOver = true;
										}
									}
									
									logDetail.add(details);
								}
								// 调用webService将物流信息存入缓存中
								LogisticsServerClient.putMemcached("yuantong",
										trackNum, logDetail);
								if (isOver) {
									logger.info(Thread.currentThread().getName()+"圆通快递已经签收，准备写入数据库，快递单号为：" + trackNum);
									Integer isOk = LogisticsServerClient
											.putInfoToDB("yuantong", trackNum);
									if (isOk <= 0) {
										logger.info(Thread.currentThread().getName()+"圆通快递单号为：" + trackNum
												+ "的快递写入数据库失败");
									} else {
										overLogistics.add(trackNum);
									}
								}
							}
						}else{
							logger.info(Thread.currentThread().getName()+"圆通运单号：##" + trackNum + "##没有返回结果信息");
						}
						
					}else{
						logger.info(Thread.currentThread().getName()+"XML转换javaBean失败，圆通运单号：" + trackNum);
					}
					}else{
						Response response=(Response) JaxbUtil.createXMLToBean(new StringReader(result), Response.class);
					if(null!=response){
						logger.error(Thread.currentThread().getName()+"圆通接口请求错误,错误信息为##"+response.getReason()+"##");
						if("请求已超时".equals(response.getReason().trim())){
							logger.error(Thread.currentThread().getName()+"圆通接口请求错误,错误信息为XML##"+result.trim() +"##");
							break;
						}
					}else{
						logger.info(Thread.currentThread().getName()+"圆通接口请求错误,因XML转换JavaBean失败，未能获取错误信息！！！！！");
					}
					}

				} else {
					logger.info(Thread.currentThread().getName()+"圆通运单号：##" + trackNum + "##获取物流流转信息失败");
				}
				is++;
			}
			if (!overLogistics.isEmpty()) {
				logger.info(Thread.currentThread().getName()+"本次圆通漏单程序共有：##" + overLogistics.size()
						+ "##个运单已经成功签收，并成功写入数据库中！！！！！");
				// 遍历overLogistics，获取已完成快递的订单号
				for (String str : overLogistics) {
					// 从trackNumber中移除已完成订单
					trackNumber.remove(str);
				}
			} else {
				logger.info(Thread.currentThread().getName()+"圆通快递暂时没有已完成快递信息");
			}
		} else {
			logger.info(Thread.currentThread().getName()+"圆通暂时没有快递信息可以更新！！！！！");
		}
	}
	public List<String> getTrackNumberList() {
		return trackNumberList;
	}
	public void setTrackNumberList(List<String> trackNumberLists) {
		this.trackNumberList = new ArrayList<String>();
		this.trackNumberList.addAll(trackNumberLists);
	}



	}


