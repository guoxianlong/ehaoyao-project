package com.ehaoyao.logistics.yto.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.ehaoyao.logistics.common.utils.VersionCachePool;
import com.ehaoyao.logistics.common.vo.LogisticsDetail;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.yto.service.YTOLogisticsService;
import com.ehaoyao.logistics.yto.utils.JaxbUtil;
import com.ehaoyao.logistics.yto.utils.SortByUploadTime;
import com.ehaoyao.logistics.yto.utils.YTServiceNet;
import com.ehaoyao.logistics.yto.vo.Response;
import com.ehaoyao.logistics.yto.vo.Result;
import com.ehaoyao.logistics.yto.vo.Ufinterface;
import com.ehaoyao.logistics.yto.vo.WaybillProcessInfo;

/**
 * 主要业务实现类
 * @author longshanw
 *
 */
@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="ytoLogisticsService")
public class YTOLogisticsServiceImpl implements YTOLogisticsService {
	private static ResourceBundle ytoConfig = ResourceBundle.getBundle("ytoConfig");
	private static final Logger logger = Logger.getLogger(YTOLogisticsServiceImpl.class);
	String waybillsource = ytoConfig.getString("waybillsource");
	
	@Autowired
	WayBillInfoMapper waybillInfoMapper;
	
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	
	/**
	 * 获取圆通未完成配送运单集合
	 * @param wayBillInfoVo 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<WayBillInfo> selectYTOInitWayBills(WayBillInfoVo wayBillInfoVo) throws Exception {
		
		/* 调用mapper获取运单号集合*/
		List<WayBillInfo> wayBillInfoList = waybillInfoMapper.selectWayBillInfoList(wayBillInfoVo);
		
		return wayBillInfoList;
	}

	/**
	 * 更新圆通运单信息
	 * @param wayBillInfoList
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer updateYTOWayBills(List<WayBillInfo> wayBillInfoList) throws Exception {
		String waybillNumber = "";
		String result = "";
		Date currDate = new Date();
		int dealCount = 0;
		List<WayBillDetail> wbDetailList = new ArrayList<WayBillDetail>();
		List<WayBillInfo> wbnfoList = new ArrayList<WayBillInfo>();
		String upLoadTimeNull = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String putMemError = "";
		for(int i=0;i<wayBillInfoList.size();i++){
			/**
			 * 用于定义运单是否有需要更新的运单，如果有则更新主表最新平台运单时间及状态,即waybill_info(last_time/waybill_status)
			 */
			int countIns = 0;
			//1,	根据运单号调用圆通物流接口，获取物流信息
			WayBillInfo wayBillInfo = wayBillInfoList.get(i);
			waybillNumber = wayBillInfo.getWaybillNumber();
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
				continue;
			}
			
			
			//2,	根据返回物流集合更新物流中心最新物流信息
			List<WaybillProcessInfo> infoList = expInfo.getResult().getWaybillProcessInfo();
			Collections.sort(infoList, new SortByUploadTime());
			
			/**
			 *	将圆通运单信息放入至缓存服务器 
			 */
			boolean putMemcachedFlag = putMemcached(infoList,waybillNumber);
			if(!putMemcachedFlag){
				putMemError+=waybillNumber+",";
			}
			
			int index=0;//存放for运行终止的最新游标
			for(int j=0;j<infoList.size();j++){
				WaybillProcessInfo expressInfo = infoList.get(j);
				String upLoadTime = expressInfo.getUploadTime();
				String context=expressInfo.getProcessInfo();
				if(upLoadTime==null || upLoadTime.trim().length()==0 ){
					upLoadTimeNull += waybillNumber+",";
					continue;
				}
				upLoadTime = upLoadTime.replaceAll("/", "-");
				if (wayBillInfo.getLastTime() != null && (sdf.parse(upLoadTime).before(wayBillInfo.getLastTime())
								|| sdf.parse(upLoadTime).equals(wayBillInfo.getLastTime()))) {
					continue;
				}
				
				
				WayBillDetail wayBillDetail = new WayBillDetail();
				wayBillDetail.setWaybillSource(waybillsource);
				wayBillDetail.setWaybillNumber(waybillNumber);
				wayBillDetail.setWaybillStatus(opeTitleTOWayBillStatus(context,j,infoList.size()));
				wayBillDetail.setWaybillContent(context);
				wayBillDetail.setWaybillTime(sdf.parse(upLoadTime));
				wayBillDetail.setCreateTime(currDate);
				wbDetailList.add(wayBillDetail);
				
				String wayBillStatus = opeTitleTOWayBillStatus(context,j,infoList.size());
				index = j;
				countIns++;
				/**
				 * 圆通公司存在已签收、已拒收的状态之后的最新物流信息错误数据，故在此判断并跳出，不再保存已签收、已拒收之后的物流信息
				 */
				if(WayBillInfo.WAYBILL_INFO_STATUS_DELIVERED.equals(wayBillStatus) || WayBillInfo.WAYBILL_INFO_STATUS_REJECTION.equals(wayBillStatus)){
					break;
				}
			}
			
			if(countIns>0){
				WaybillProcessInfo wpi = infoList.get(index);
				String waybillStatus = opeTitleTOWayBillStatus(wpi.getProcessInfo(),index,infoList.size());
				wayBillInfo.setWaybillStatus(waybillStatus);
				String upLoadTime = wpi.getUploadTime().replaceAll("/", "-");
				wayBillInfo.setLastTime(sdf.parse(upLoadTime));
				wbnfoList.add(wayBillInfo);
			}
		}
		
		if(putMemError.trim().length()>0){
			logger.info("【圆通运单-放入缓存服务器失败的运单信息："+putMemError+"】");
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
			logger.info("【"+Thread.currentThread().getName()+"圆通运单号：" + waybillNumber + ",获取物流流转信息失败】");
			return false;
		}
		
		int index=result.indexOf("<ufinterface>");
		
		if(index==-1){
			if(null!=response){
//				logger.error(Thread.currentThread().getName()+",圆通运单号：" + waybillNumber + ",返回信息为##"+response.getReason()+"##");
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
			logger.info(Thread.currentThread().getName()+"圆通运单号：" + waybillNumber + "没有返回结果信息");
			return false;
		}
		return true;
	}

	/**
	 * 
	* @Description:将opeTitle转化成状态
	* @param  opeTitle
	* @param  index 游标
	* @param size 总条数	
	* @param @return
	* @return String
	* @throws
	 */
	public String opeTitleTOWayBillStatus (String opeTitle,Integer index,Integer size ){
		if ((opeTitle.contains("已收件")||opeTitle.contains("已收入")||opeTitle.contains("已打包")) && index==0 && size>=1) {
			return WayBillInfo.WAYBILL_INFO_STATUS_COLLECTPARCEL;
		}else if(opeTitle.replaceAll(" ", "").contains("客户签收人:已退回")){
			return WayBillInfo.WAYBILL_INFO_STATUS_REJECTION;
		}else if(opeTitle.replaceAll(" ", "").contains("客户签收人:")){
			return WayBillInfo.WAYBILL_INFO_STATUS_DELIVERED;
		}else{
			return WayBillInfo.WAYBILL_INFO_STATUS_DISTRIBUTION;
		}
	}
	
	/**
	 * 获取最大的物流单号
	 * @param wbDetailList
	 * @return
	 */
	public String getLastWayBillStatus(List<WayBillDetail> wbDetailList){
		Comparator<WayBillDetail> comp = new Comparator<WayBillDetail>() {

			@Override
			public int compare(WayBillDetail o1, WayBillDetail o2) {
				if(o1.getWaybillStatus().compareTo(o2.getWaybillStatus())>=0){
					return 1;
				}else{
					return -1;
				}
			}
		};
		WayBillDetail wb = Collections.max(wbDetailList, comp);
		return wb.getWaybillStatus();
	}
	
	/**
	 * 将运单信息放入至缓存服务器 
	 * @param infoList
	 * @param waybillNumber
	 */
	public boolean putMemcached(List<WaybillProcessInfo> infoList,String waybillNumber) throws Exception{
		
		List<LogisticsDetail> logDetail = new ArrayList<LogisticsDetail>();
		for (WaybillProcessInfo info : infoList) {
			LogisticsDetail detail = new LogisticsDetail();
			String date=info.getUploadTime();
			date=date.replaceAll("/", "-");
			detail.setReceiptTime(date);
			detail.setReceiptAddress("");
			detail.setContext(info.getProcessInfo());//该订单的物流实时更新内容
			detail.setTrackingNumber(waybillNumber);//物流单号
			logDetail.add(detail);
		}
		//将该订单的物流信息放入缓存服务器中
		if(!logDetail.isEmpty()){
			/*PutMemcachedResponse putMemcachedResponse =LogisticsServerClient.putMemcached("jd", waybillNumber,
					logDetail);
			if(putMemcachedResponse.get_return()!=1){
				System.out.println("putMemcachedResponse="+JSONObject.toJSONString(putMemcachedResponse));
			}
			return putMemcachedResponse.get_return()==1?true:false;*/
			return VersionCachePool.putObject(waybillsource+"_" + waybillNumber, logDetail);
		}
		return false;
	}
}
