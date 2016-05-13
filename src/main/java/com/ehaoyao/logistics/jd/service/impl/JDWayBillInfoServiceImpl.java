/**
 * 
 */
package com.ehaoyao.logistics.jd.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.ehaoyao.logistics.jd.service.JDWayBillDetailService;
import com.ehaoyao.logistics.jd.service.JDWayBillInfoService;
import com.ehaoyao.logistics.jd.utils.JDExpressUtils;
import com.ehaoyao.logistics.jd.utils.SortByOpeTime;
import com.jd.open.api.sdk.response.etms.TraceApiDto;


/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:09:38
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
@Transactional(value="transactionManagerLogisticsCenter")
@Service
public class JDWayBillInfoServiceImpl implements JDWayBillInfoService {
	private static final Logger logger = Logger.getLogger(JDWayBillInfoServiceImpl.class);
	@Autowired
	private WayBillInfoMapper waybillInfoMapper;
	@Autowired
	private WayBillDetailMapper wayBillDetailMapper;
	@Autowired
	private JDWayBillDetailService waybillDetailService;
	/**
	* @Description:查询 date天内、waybillSource 、waybillStatus多个状态的运单集合
	* @param @param date
	* @param @param waybillStatus
	* @param @return
	* @return 
	* @throws
	*/
	public List<String> getWaybillNumbers(int date,String waybillSource,
			List<String> waybillStatusList)throws Exception {
		/*1、 获得开始抓运单时间、结束时间*/
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -date);
		Date startTime = calendar.getTime();
		Date endTime = new Date();
		/*2、将查询条件封装*/
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setCreateTimeStart(startTime);;
		wayBillInfoVo.setCreateTimeEnd(endTime);
		wayBillInfoVo.setWaybillStatusList(waybillStatusList);
		wayBillInfoVo.setWaybillSource(waybillSource);
		/*3、调用mapper获取运单号集合*/
		List<String> waybillNumbers =new ArrayList<String>();
		waybillNumbers = waybillInfoMapper.queryWayBillNumbers(wayBillInfoVo);				
		return waybillNumbers;
	}
	/**
	* @Description:调用JDAPI,更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param waybillNumber
	* @param @return
	* @return 
	* @throws
	*/ 
	@Override
	public int updateWaybillByJD(String waybillNumber) throws Exception{
		SimpleDateFormat jdSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		logger.info(Thread.currentThread().getName() +"开始抓取京东快递运单号为：" + waybillNumber + "快递信息");
		String strs = waybillNumber.substring(0, 3);
		List<TraceApiDto> apiDtos = null;
		/*1、获取该订单的所有物流详细信息 apiDtos*/
		if ("No:".equals(strs)) {
			String track = waybillNumber.substring(3, waybillNumber.length());
			apiDtos = JDExpressUtils.getDetails(track);
		} else {
			apiDtos = JDExpressUtils.getDetails(waybillNumber);
		}
		/*2、将物流跟踪信息集合按照 OPenTime 进行排序，取最新的物流跟踪信息来更新WayBillInfo到DB,
		 * 添加新的几条物流跟踪信息到WayBillDetail表中,
		 * 新旧物流跟踪信息的标准:新的物流跟踪信息的OPenTime字段比WayBillInfo的lastTime大*/
		//2.1  根据运单号从物流中心获得运单info
		WayBillInfo  wayBillInfo= waybillInfoMapper.selectByWayBillNumber(waybillNumber);		
		//2.2  对跟踪信息集合根据 OPenTIme进行排序
		if(null !=apiDtos){
			Collections.sort(apiDtos, new SortByOpeTime());
			Date lastTime = wayBillInfo.getLastTime();
			int waybillDetailNeedCount=0;//需要添加的新物流跟踪信息个数
			int waybillDetailSuccessCount =0;//添加成功的新物流跟踪信息个数
			//2.3 遍历 跟踪信息集合(京东), 如果OpenTime大于lastTime或lastTime为null，则添加跟踪信息到DB
			for (int i = 0; i < apiDtos.size(); i++) {
				TraceApiDto traceApiDto = apiDtos.get(i);
				String opeTime = traceApiDto.getOpeTime();
				Date parseOpeTime=null;
				try {
					parseOpeTime = jdSdf.parse(opeTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(null==lastTime||parseOpeTime.after(lastTime)){
					waybillDetailNeedCount++;
					//创建wayBillDetail，并封装属性
					WayBillDetail waybillDetail = new WayBillDetail();
					waybillDetail.setCreateTime(wayBillInfo.getCreateTime());
					waybillDetail.setWaybillNumber(waybillNumber);
					waybillDetail.setWaybillSource(wayBillInfo.getWaybillSource());
					waybillDetail.setWaybillTime(parseOpeTime);
					waybillDetail.setWaybillContent(traceApiDto.getOpeRemark()+":"+traceApiDto.getOpeTitle());
					// waybillStatus 运单状态 /s00 :初始化  s01:揽件(快递签收)  s02:配送中 s03:拒收  s04：妥投
					String opeTitle = traceApiDto.getOpeTitle();
					String waybillStatus=opeTitleTOWayBillStatus(opeTitle);
					waybillDetail.setWaybillStatus(waybillStatus);
					//2.4  调用wayBillDetailService 插入数据库
					int insertWayBillDetail =0;
					try {
						insertWayBillDetail = waybillDetailService.insertWayBillDetail(waybillDetail);						
					} catch (Exception e) {
						logger.error(Thread.currentThread().getName()+"添加运单的新跟踪信息到物流中心DB程序异常！！ 运单号："+waybillNumber,e);
						throw new Exception();
					}
					if (insertWayBillDetail==1){
						waybillDetailSuccessCount++;
					}
				}				
			}
			logger.info(Thread.currentThread().getName()+"运单号："+waybillNumber+"需要添加的新物流跟踪信息"+waybillDetailNeedCount+"条,添加成功的新物流跟踪信息"+waybillDetailSuccessCount+"条");
			//2.5  根据最新跟踪信息，更新运单info
			if(waybillDetailSuccessCount>0){
				TraceApiDto newestTraceApiDto = apiDtos.get(apiDtos.size()-1);
				String opeTimeNew = newestTraceApiDto.getOpeTime();
				Date lastTimeNew =null;
				try {
					lastTimeNew = jdSdf.parse(opeTimeNew);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				wayBillInfo.setLastTime(lastTimeNew);
				//2.6 给wayBillInfo设置 运单状态S
				String opeTitle = newestTraceApiDto.getOpeTitle();
				String waybillStatusNew = opeTitleTOWayBillStatus(opeTitle);
				wayBillInfo.setWaybillStatus(waybillStatusNew);
				//2.7、更新wayBillInfo到物流中心
				int waybillInfoSucessCount=0;
				try {
					waybillInfoSucessCount = waybillInfoMapper.updateByPrimaryKey(wayBillInfo);			
				} catch (Exception e) {
					logger.error(Thread.currentThread().getName()+"更新运单info 程序异常！！ 运单号："+waybillNumber,e);
					throw new Exception();
				}
				return waybillInfoSucessCount;
			}
			//从京东获取的跟踪信息与物流中心的一样,则不更新 ，为0
			return 0; 
		}
		//从京东获取的跟踪信息为null
		logger.info(Thread.currentThread().getName()+"运单号："+waybillNumber+",调用京东接口返回的信息："+apiDtos);
		return 0;
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
		if ("快递签收".equals(opeTitle)) {
			return "s01";
		}else if("拒收".equals(opeTitle)){
			return "s03";
		}else if("妥投".equals(opeTitle)){
			return "s04";
		}else{
			return "s02";
		}
	}
	/**
	* @Description:根据运单号集合，调用JDAPI,批量更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param waybillNumberList
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	@Override
	public int updateWaybillByJD(List<String> waybillNumberList)
			throws Exception {
		SimpleDateFormat jdSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<WayBillInfo> waybillInfoList =new ArrayList<WayBillInfo>();
		List<WayBillDetail> waybillDetailList = new ArrayList<WayBillDetail>();
		for (String waybillNumber : waybillNumberList) {
			String strs = waybillNumber.substring(0, 3);
			List<TraceApiDto> apiDtos = null;
			/*1、获取该订单的所有物流详细信息 apiDtos*/
			if ("No:".equals(strs)) {
				String track = waybillNumber.substring(3, waybillNumber.length());
				apiDtos = JDExpressUtils.getDetails(track);
			} else {
				apiDtos = JDExpressUtils.getDetails(waybillNumber);
			}
			/*2、将物流跟踪信息集合按照 OPenTime 进行排序，取最新的物流跟踪信息来更新WayBillInfo到DB,
			 * 添加新的几条物流跟踪信息到WayBillDetail表中,
			 * 新旧物流跟踪信息的标准:新的物流跟踪信息的OPenTime字段比WayBillInfo的lastTime大*/
			//2.1  根据运单号从物流中心获得运单info
			WayBillInfo  wayBillInfo= waybillInfoMapper.selectByWayBillNumber(waybillNumber);		
			//2.2  对跟踪信息集合根据 OPenTIme进行排序
			if(null !=apiDtos){
				Collections.sort(apiDtos, new SortByOpeTime());
				Date lastTime = wayBillInfo.getLastTime();
				int waybillDetailSuccessCount =0;//添加成功的新物流跟踪信息个数
				//2.3 遍历 跟踪信息集合(京东), 如果OpenTime大于lastTime或lastTime为null，则添加跟踪信息到DB
				for (int i = 0; i < apiDtos.size(); i++) {
					TraceApiDto traceApiDto = apiDtos.get(i);
					String opeTime = traceApiDto.getOpeTime();
					Date parseOpeTime=null;
					try {
						parseOpeTime = jdSdf.parse(opeTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(null==lastTime||parseOpeTime.after(lastTime)){
						//创建wayBillDetail，并封装属性
						WayBillDetail waybillDetail = new WayBillDetail();
						waybillDetail.setCreateTime(wayBillInfo.getCreateTime());
						waybillDetail.setWaybillNumber(waybillNumber);
						waybillDetail.setWaybillSource(wayBillInfo.getWaybillSource());
						waybillDetail.setWaybillTime(parseOpeTime);
						waybillDetail.setWaybillContent(traceApiDto.getOpeRemark()+":"+traceApiDto.getOpeTitle());
						// waybillStatus 运单状态 /s00 :初始化  s01:揽件(快递签收)  s02:配送中 s03:拒收  s04：妥投
						String opeTitle = traceApiDto.getOpeTitle();
						String waybillStatus=opeTitleTOWayBillStatus(opeTitle);
						waybillDetail.setWaybillStatus(waybillStatus);
						//2.4  添加到待更新的 运单详情集合中
						waybillDetailList.add(waybillDetail);
						waybillDetailSuccessCount++;
					}
				}
				if(waybillDetailSuccessCount>0){
					TraceApiDto newestTraceApiDto = apiDtos.get(apiDtos.size()-1);
					String opeTimeNew = newestTraceApiDto.getOpeTime();
					Date lastTimeNew =null;
					try {
						lastTimeNew = jdSdf.parse(opeTimeNew);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					wayBillInfo.setLastTime(lastTimeNew);
					//2.6 给wayBillInfo设置 运单状态S
					String opeTitle = newestTraceApiDto.getOpeTitle();
					String waybillStatusNew = opeTitleTOWayBillStatus(opeTitle);
					wayBillInfo.setWaybillStatus(waybillStatusNew);
					//2.7、将wayBillInfo添加到待更新的运单info集合
					waybillInfoList.add(wayBillInfo);	
				}
			}
		}
		//3.1  批量插入插入新的跟踪信息
		if(waybillDetailList!=null && !waybillDetailList.isEmpty()){
			waybillDetailService.insertWayBillDetail(waybillDetailList);
		}
		//3.2  批量更新 运单Info
		int updateCount =0;
		if(waybillInfoList!=null && !waybillInfoList.isEmpty()){
			updateCount = waybillInfoMapper.updateWayBillInfoBatch(waybillInfoList);
		}
		return updateCount;
	}
	/**
	* @Description:查询startDate~endDate时间内、waybillSource 、waybillStatus多个状态的运单集合
	* @param @param date
	* @param @param waybillSource
	* @param @param waybillStatusList
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	public List<WayBillInfo> queryWayBillInfoList(Date startDate,Date endDate,
			String waybillSource, List<String> waybillStatusList)
			throws Exception {
		/*1、将查询条件封装*/
		WayBillInfoVo wayBillInfoVo = new WayBillInfoVo();
		wayBillInfoVo.setCreateTimeStart(startDate);
		wayBillInfoVo.setCreateTimeEnd(endDate);
		wayBillInfoVo.setWaybillStatusList(waybillStatusList);
		wayBillInfoVo.setWaybillSource(waybillSource);
		/*2、调用mapper获取运单号集合*/
		List<WayBillInfo> wayBillInfoList = waybillInfoMapper.selectWayBillInfoList(wayBillInfoVo);				
		return wayBillInfoList;
	}
	/**
	* @Description:根据运单info集合，调用JDAPI,批量更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param wayBillInfoList
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	@Override
	public int updateWaybillInfoListByJD(List<WayBillInfo> wayBillInfoList)
			throws Exception {
		SimpleDateFormat jdSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ArrayList<WayBillInfo> waitUpdateWayBillInfoList = new ArrayList<WayBillInfo>();
		ArrayList<WayBillDetail> waitInsertWayBillDetailList = new ArrayList<WayBillDetail>();
		String putMemError = "";
		for (WayBillInfo wayBillInfo: wayBillInfoList) {
			String waybillNumber=wayBillInfo.getWaybillNumber();
			String strs = waybillNumber.substring(0, 3);
			List<TraceApiDto> apiDtos = null;
			/*1、获取该订单的所有物流详细信息 apiDtos*/
			if ("No:".equals(strs)) {
				String track = waybillNumber.substring(3, waybillNumber.length());
				apiDtos = JDExpressUtils.getDetails(track);
			} else {
				apiDtos = JDExpressUtils.getDetails(waybillNumber);
			}
			/*2、将物流跟踪信息集合按照 OPenTime 进行排序，取最新的物流跟踪信息来更新WayBillInfo到DB,
			 * 添加新的几条物流跟踪信息到WayBillDetail表中,
			 * 新旧物流跟踪信息的标准:新的物流跟踪信息的OPenTime字段比WayBillInfo的lastTime大*/		
			//2.1  对跟踪信息集合根据 OPenTIme进行排序
			if(null !=apiDtos && !apiDtos.isEmpty()){
				/**
				 *	将京东运单信息放入至缓存服务器 
				 */
				boolean flag = putMemcached(apiDtos,waybillNumber);
				if(!flag){
					putMemError+=waybillNumber+",";
				}
				
				Collections.sort(apiDtos, new SortByOpeTime());
				Date lastTime = wayBillInfo.getLastTime();
				int waybillDetailSuccessCount =0;//添加成功的新物流跟踪信息个数
				//2.3 遍历 跟踪信息集合(京东), 如果OpenTime大于lastTime或lastTime为null，则添加跟踪信息到DB
				int newestIndex=0;//最新的跟踪信息索引
				for (int i = 0; i < apiDtos.size(); i++) {
					TraceApiDto traceApiDto = apiDtos.get(i);
					String opeTime = traceApiDto.getOpeTime();
					Date parseOpeTime=null;
					try {
						parseOpeTime = jdSdf.parse(opeTime);
					} catch (ParseException e) {
						e.printStackTrace();
						throw new Exception(e);
					}
					if(null==lastTime||parseOpeTime.after(lastTime)){
						//创建wayBillDetail，并封装属性
						WayBillDetail waybillDetail = new WayBillDetail();
						waybillDetail.setCreateTime(new Date());
						waybillDetail.setWaybillNumber(waybillNumber);
						waybillDetail.setWaybillSource(wayBillInfo.getWaybillSource());
						waybillDetail.setWaybillTime(parseOpeTime);
						waybillDetail.setWaybillContent(traceApiDto.getOpeRemark()+":"+traceApiDto.getOpeTitle());
						// waybillStatus 运单状态 /s00 :初始化  s01:揽件(快递签收)  s02:配送中 s03:拒收  s04：妥投
						String opeTitle = traceApiDto.getOpeTitle();
						String waybillStatus=opeTitleTOWayBillStatus(opeTitle);
						waybillDetail.setWaybillStatus(waybillStatus);
						//2.4  添加到待更新的 运单详情集合中
						waitInsertWayBillDetailList.add(waybillDetail);
						waybillDetailSuccessCount++;
						//2.5 为防止物流公司传来的跟踪信息有误(某些运单妥投后,还会出现新的跟踪信息),对物流状态为s04跟踪信息以后的，均不做保存/更新处理。
						if(waybillStatus=="s03" || waybillStatus=="s04"){
							newestIndex=i;
							break;
						}
						newestIndex=i;
					}
				}
				// 2.5.1  根据该运单 最新的物流跟踪信息，更新运单Info
				if(waybillDetailSuccessCount>0){
					TraceApiDto newestTraceApiDto = apiDtos.get(newestIndex);
					String opeTimeNew = newestTraceApiDto.getOpeTime();
					Date lastTimeNew =null;
					try {
						lastTimeNew = jdSdf.parse(opeTimeNew);
					} catch (ParseException e) {
						e.printStackTrace();
						throw new Exception(e);
					}
					wayBillInfo.setLastTime(lastTimeNew);
					//2.6 给wayBillInfo设置 运单状态S
					String opeTitle = newestTraceApiDto.getOpeTitle();
					String waybillStatusNew = opeTitleTOWayBillStatus(opeTitle);
					wayBillInfo.setWaybillStatus(waybillStatusNew);
					//2.7、将wayBillInfo添加到待更新的运单info集合
					waitUpdateWayBillInfoList.add(wayBillInfo);	
				}
			}
		}
		
		if(putMemError.trim().length()>0){
			logger.info("【京东运单-放入缓存服务器失败的运单信息："+putMemError+"】");
		}
		//3.1  批量插入插入新的跟踪信息
		if(waitInsertWayBillDetailList!=null && !waitInsertWayBillDetailList.isEmpty()){
			wayBillDetailMapper.insertWayBillDetailBatch(waitInsertWayBillDetailList);
		}
		//3.2  批量更新 运单Info
		int updateCount =0;
		if(waitUpdateWayBillInfoList!=null && !waitUpdateWayBillInfoList.isEmpty()){
			updateCount = waybillInfoMapper.updateWayBillInfoBatch(waitUpdateWayBillInfoList);
		}
		return updateCount;
	}
	
	/**
	 * 将运单信息放入至缓存服务器 
	 * @param apiDtos
	 * @param waybillNumber
	 */
	public boolean putMemcached(List<TraceApiDto> apiDtos,String waybillNumber){
		
		List<LogisticsDetail> logDetail = new ArrayList<LogisticsDetail>();
		for (TraceApiDto tap : apiDtos) {
			LogisticsDetail detail = new LogisticsDetail();
			String date=tap.getOpeTime();
			date=date.replaceAll("/", "-");
			detail.setReceiptTime(date);
			detail.setReceiptAddress("");
			detail.setContext(tap.getOpeRemark() + ":"
					+ tap.getOpeTitle());//该订单的物流实时更新内容
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
			return VersionCachePool.putObject("jd_" + waybillNumber, logDetail);
		}
		return false;
	}
}
