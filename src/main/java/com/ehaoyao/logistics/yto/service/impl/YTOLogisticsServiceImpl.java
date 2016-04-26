package com.ehaoyao.logistics.yto.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;
import com.ehaoyao.logistics.yto.utils.JaxbUtil;
import com.ehaoyao.logistics.yto.utils.SortByUploadTime;
import com.ehaoyao.logistics.yto.utils.YTServiceNet;
import com.ehaoyao.logistics.yto.vo.Response;
import com.ehaoyao.logistics.yto.vo.Result;
import com.ehaoyao.logistics.yto.vo.Ufinterface;
import com.ehaoyao.logistics.yto.vo.WaybillProcessInfo;

@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="ytoLogisticsService")
public class YTOLogisticsServiceImpl implements YTOLogisticsService {
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	private static final Logger logger = Logger.getLogger(YTOLogisticsServiceImpl.class);
	String waybillsource = ytoConfig.getString("waybillsource");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	WayBillInfoMapper waybillInfoMapper;
	
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	
	@Override
	public List<WayBillInfo> selectYTOInitWayBills() throws Exception {
		int orderIntervalTime = Integer.parseInt(ytoConfig.getString("normal_updLogistics_minute"));
		Date startTime=DateUtil.getPreMinute(orderIntervalTime);//当前时间向前推迟xxx分钟
		Date endTime=new Date();
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1 运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add("s00");
		waybillStatusList.add("s01");
		waybillStatusList.add("s02");
		
		/*2、将查询条件封装*/
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setWaybillSource(waybillsource);
		wayBillInfoVo.setCreateTimeStart(startTime);
		wayBillInfoVo.setCreateTimeEnd(endTime);
		wayBillInfoVo.setWaybillStatusList(waybillStatusList);
		
		/*3、调用mapper获取运单号集合*/
		List<WayBillInfo> wayBillInfoList = waybillInfoMapper.selectWayBillInfoList(wayBillInfoVo);
		
		return wayBillInfoList;
	}

	@Override
	public Integer updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception {
		String waybillNumber = "";
		String result = "";
		Date currDate = new Date();
		int dealCount = 0;
		List<WayBillDetail> wbDetailList = new ArrayList<WayBillDetail>();
		List<WayBillInfo> wbnfoList = new ArrayList<WayBillInfo>();
		String upLoadTimeNull = "";
		
		for(int i=0;i<wayBillInfoList.size();i++){
			//1,	根据运单号调用圆通物流接口，获取物流信息
			WayBillInfo wayBillInfo = wayBillInfoList.get(i);
			waybillNumber = wayBillInfo.getWaybillNumber();
//			waybillNumber = "806804266812";
			logger.info("【"+Thread.currentThread().getName()+"开始抓取圆通快递单号为：" + waybillNumber + "的快递信息,当前第"+(i+1)+"条】");
			// 截取No:字符串，校正运单号
			if ("No:".equals(waybillNumber.substring(0, 3))) {
				result = YTServiceNet.getExpressInfo(waybillNumber.substring(3, waybillNumber.length()).trim());
			} else {
				result = YTServiceNet.getExpressInfo(waybillNumber.trim());
			}
			result=result.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			Response response=(Response) JaxbUtil.createXMLToBean(new StringReader(result), Response.class);
			Ufinterface expInfo=(Ufinterface) JaxbUtil.createXMLToBean(new StringReader(result.trim()), Ufinterface.class);
			boolean flag = this.checkRespons(result,wayBillInfo,response,expInfo);
			if(!flag){
//				logger.info("【处理圆通返回信息出现错误，请求物流单号："+waybillNumber.trim()+"，圆通接口返回信息："+result+"】");
				continue;
			}
			
			//2,	根据返回物流集合更新物流中心最新物流信息
			List<WaybillProcessInfo> infoList = expInfo.getResult().getWaybillProcessInfo();
			Collections.sort(infoList, new SortByUploadTime());
			
			for(WaybillProcessInfo expressInfo:infoList){
				String upLoadTime = expressInfo.getUploadTime().replaceAll("/", "-");
				/*try {
					sdf.parse(upLoadTime);
				} catch (Exception e) {
					logger.info("#######################"+result);
					e.printStackTrace();
				}*/
				if(wayBillInfo.getLastTime() != null && upLoadTime!=null && upLoadTime.trim().length()>0 && sdf.parse(upLoadTime).before(wayBillInfo.getLastTime())){
					continue;
				}
				if(upLoadTime==null || upLoadTime.trim().length()<=0 || "".equals(upLoadTime)){
					upLoadTimeNull += waybillNumber+",";
					continue;
				}
				WayBillDetail wayBillDetail = new WayBillDetail();
				String context=expressInfo.getProcessInfo();
				wayBillDetail.setWaybillSource(waybillsource);
				wayBillDetail.setWaybillNumber(waybillNumber);
				wayBillDetail.setWaybillStatus(opeTitleTOWayBillStatus(context));
				wayBillDetail.setWaybillContent(context);
				wayBillDetail.setWaybillTime(sdf.parse(upLoadTime));
				wayBillDetail.setCreateTime(currDate);
				wbDetailList.add(wayBillDetail);
			}
			
			if(wbDetailList!=null && !wbDetailList.isEmpty()){
				String waybillStatus = opeTitleTOWayBillStatus(infoList.get(infoList.size()-1).getProcessInfo());
				wayBillInfo.setWaybillStatus(waybillStatus);
				String upLoadTime = infoList.get(infoList.size()-1).getUploadTime().replaceAll("/", "-");
				wayBillInfo.setLastTime((upLoadTime!=null && upLoadTime.trim().length()>0)?sdf.parse(upLoadTime):new Date());
				wbnfoList.add(wayBillInfo);
			}
		}
		
		if(upLoadTimeNull.length()>0){
			logger.info("【调用圆通物流，返回物流时间存在空的运单信息："+upLoadTimeNull+"】");
		}
		
		//3,	批量保存最新物流信息
		if(wbDetailList!=null && !wbDetailList.isEmpty()){
			wayBillDetailMapper.insertWayBillDetailBatch(wbDetailList);
		}
		//4,	批量更新最新物流状态及时间
		if(wbnfoList!=null && !wbnfoList.isEmpty()){
			dealCount = waybillInfoMapper.updateWayBillInfoBatch(wbnfoList);
		}
		
		return dealCount;
	}

	/**
	 * 调用圆通物流接口返回值各项信息校验
	 * @param result
	 * @param wayBillInfo
	 * @param response
	 * @param expInfo
	 * @return
	 */
	private boolean checkRespons(String result, WayBillInfo wayBillInfo,Response response,Ufinterface expInfo) {
		String waybillNumber = wayBillInfo.getWaybillNumber();
		if (result == null || result.trim().length()<=0) {
			logger.info("【"+Thread.currentThread().getName()+"圆通运单号：##" + waybillNumber + "##获取物流流转信息失败】");
			return false;
		}
		
		int index=result.indexOf("<ufinterface>");
		
		if(index==-1){
			if(null!=response){
				logger.error(Thread.currentThread().getName()+"圆通接口请求错误,错误信息为##"+response.getReason()+"##");
				if("请求已超时".equals(response.getReason().trim())){
					logger.info(Thread.currentThread().getName()+"圆通接口请求错误,错误信息为XML##"+result.trim() +"##");
				}
			}else{
				logger.info(Thread.currentThread().getName()+"圆通接口请求错误,因XML转换JavaBean失败，未能获取错误信息！！！！！");
			}
			return false;
		}
		
		if(expInfo == null){
			logger.info(Thread.currentThread().getName()+"XML转换javaBean失败，圆通运单号：" + waybillNumber);
			return false;
		}
		Result res=expInfo.getResult();
		if(res == null || res.getWaybillProcessInfo()==null || res.getWaybillProcessInfo().isEmpty()){
			logger.info(Thread.currentThread().getName()+"圆通运单号：##" + waybillNumber + "##没有返回结果信息");
			return false;
		}
		return true;
	}

	/**
	 * 
	* @Description:将opeTitle转化成状态
	* @param @param opeTitle
	* @param @return
	* @return String
	* @throws
	 */
	public String opeTitleTOWayBillStatus (String opeTitle ){
		if (opeTitle.contains("已收件")) {
			return "s01";
		}else if(opeTitle.contains("已签收")){
			return "s04";
		}else if(opeTitle.contains("拒收")){
			return "s03";
		}else{
			return "s02";
		}
	}
}
