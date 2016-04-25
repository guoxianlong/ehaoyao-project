package com.ehaoyao.logistics.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;
import com.ehaoyao.logistics.common.vo.WayBillTransVo;

@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="toLogisticsCenterService")
public class ToLogisticsCenterServiceImpl implements ToLogisticsCenterService {
	
	private static final Logger logger = Logger.getLogger(ToLogisticsCenterServiceImpl.class);
	private static ResourceBundle bundle = ResourceBundle.getBundle("express");
	private static ResourceBundle bundle1 = ResourceBundle.getBundle("orderCodeToKD100Code");
	private static ResourceBundle bundle2 = ResourceBundle.getBundle("expressIDtoKD100Code");
	
	@Autowired
	WayBillInfoMapper wayBillInfoMapper;
	
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	
	@Resource(name="sqlSessionTemplateLogisticsCenter")
	private SqlSessionTemplate logisticsSqlSessionTemplate;
	
	
	
	
	/**
	 * 将订单中心查询到的已配送的订单及运单号等信息插入物流中心
	 * @param orderExpressList
	 * @return
	 * @throws Exception
	 */
	public synchronized Object insertLogisticsCenter(List<OrderExpressVo> orderExpressList) throws Exception{
		WayBillInfo wayBillInfo;
		WayBillDetail wayBillDetail;
		List<WayBillInfo> wayBillInfoList ;
		List<WayBillInfo> wayBillInfoTempList;
		List<WayBillDetail> wayBillDetailList;
		String wayBillSource; //运单来源标识
		String repeatOrderNums = "";//从订单获取的已配送的初始订单，但在物流中心库中已存在的订单拼串
		String wayBillSourceIsNull = "";//运单来源为空或未配置的订单拼串
		int insWayBillInfoCount = 0;
		Date currDate = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		wayBillInfoList = new ArrayList<WayBillInfo>();
		wayBillDetailList = new ArrayList<WayBillDetail>();
		
		List<WayBillTransVo> wayBillTransVoList = new ArrayList<WayBillTransVo>();
		WayBillTransVo willTransVo;
		/**
		 * 将orderExpressList元素根据运单来源及运单号去重
		 */
		//1,将运单。订单信息临时放入wayBillTransVoList
		for(OrderExpressVo orderExpress : orderExpressList){
			//1.1,获取快递来源
			wayBillSource = getSource(orderExpress.getExpressComName(),orderExpress.getExpressComCode(), orderExpress.getExpressComId());
			if(wayBillSource==null || wayBillSource.trim().length()==0){
				wayBillSourceIsNull += (orderExpress.getOrderFlag()+orderExpress.getOrderNumber());
				continue;
			}
			willTransVo = new WayBillTransVo();
			willTransVo.setWayBillSource(wayBillSource);
			willTransVo.setWayBillSourceNum(wayBillSource.trim()+orderExpress.getExpressId().trim());
			willTransVo.setOrderFlagNum(orderExpress.getOrderFlag().trim()+orderExpress.getOrderNumber().trim());
			willTransVo.setOrderExpress(orderExpress);
			wayBillTransVoList.add(willTransVo);
		}
		
		//2，根据运单号/运单来源将wayBillTransVoList去重
		List<WayBillTransVo> wayBillTransVoNewList = this.removeWayBillTransVoDuplicate(wayBillTransVoList);
		
		//3,遍历去重后的wayBillTransVoList处理并初始运单信息
		for(WayBillTransVo wayBillTransVo : wayBillTransVoNewList){
			OrderExpressVo orderExpress = wayBillTransVo.getOrderExpress();
			
			//3.1,获取快递来源
			wayBillSource = wayBillTransVo.getWayBillSource();
			
			//3.2,判断订单在物流中心库中是否重复订单
			map.put("waybillSource", wayBillSource);
			map.put("waybillNumber", orderExpress.getExpressId());
			wayBillInfoTempList = wayBillInfoMapper.selectWayBillInfoListByCondition(map);
			if(wayBillInfoTempList!=null&&!wayBillInfoTempList.isEmpty()){
				repeatOrderNums += (orderExpress.getOrderFlag()+"-"+orderExpress.getOrderNumber()+"-"+wayBillSource+"-"+orderExpress.getExpressId()+",");
				continue;
			}
			//3.3,保存物流主表
			wayBillInfo = new WayBillInfo();
			wayBillInfo.setOrderFlag(orderExpress.getOrderFlag());
			wayBillInfo.setOrderNumber(orderExpress.getOrderNumber());
			wayBillInfo.setWaybillSource(wayBillSource);
			wayBillInfo.setWaybillNumber(orderExpress.getExpressId());
			wayBillInfo.setIsWriteback(0);
			wayBillInfo.setWaybillStatus(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
			wayBillInfo.setCreateTime(currDate);
			wayBillInfoList.add(wayBillInfo);
			
			//3.4,保存物流明细表
			wayBillDetail = new WayBillDetail();
			wayBillDetail.setWaybillSource(wayBillSource);
			wayBillDetail.setWaybillNumber(orderExpress.getExpressId());
			wayBillDetail.setWaybillStatus(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
			wayBillDetail.setCreateTime(currDate);
			wayBillDetailList.add(wayBillDetail);
			
		}
		
		//4,记录日志
		if(wayBillSourceIsNull!=null && wayBillSourceIsNull.trim().length()>0){
			logger.info("【从订单中心抓取初始订单至物流中心任务，运单来源为空或未配置的订单拼串信息："+repeatOrderNums+"】");
		}
		if(repeatOrderNums!=null && repeatOrderNums.trim().length()>0){
			logger.info("【从订单中心抓取初始订单至物流中心任务，重复未插入(数据库中已存在)的订单标识-订单号-运单标识-运单号："+repeatOrderNums+"】");
		}
		
		//5,保存结果集
		if(wayBillInfoList!=null && !wayBillInfoList.isEmpty()){
			insWayBillInfoCount = wayBillInfoMapper.insertWayBillInfoBatch(wayBillInfoList);
		}
		if(wayBillDetailList!=null && !wayBillDetailList.isEmpty()){
			wayBillDetailMapper.insertWayBillDetailBatch(wayBillDetailList);
		}
		return insWayBillInfoCount;
	}

	
	/**
	 * 获取运单来源标识
	 * @param expressComName
	 * @param expressComCode
	 * @param expressComID
	 * @return
	 */
	public String getSource(String expressComName, String expressComCode, String expressComID) throws Exception{
		String source ;
		/*
		 * logger.info("快递公司ID：" + order.getExpressComId());
		 */
		if (null != expressComID && !"".equals(expressComID.trim())) {
			try {
				source = bundle2.getString(expressComID.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		}
		/*
		 * logger.info("快递公司编码：" + order.getExpressComCode());
		 */
		if (null != expressComCode && !"".equals(expressComCode.trim())) {
			try {
				source = bundle1.getString(expressComCode.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		}
		/*
		 * logger.info("快递公司名称：" + order.getExpressComName());
		 */
		if (null != expressComName && !"".equals(expressComName.trim())) {
			try {
				source = bundle.getString(expressComName.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		} 
		source = "unknow";
		return source;
	}
	
	
	public List<WayBillTransVo> removeWayBillTransVoDuplicate(List<WayBillTransVo> list) {
	       Set<WayBillTransVo> set = new HashSet<WayBillTransVo>();
	       List<WayBillTransVo> newList = new ArrayList<WayBillTransVo>();
	       String repeatOrderNums = "";
	       for (Iterator<WayBillTransVo> iter = list.iterator(); iter.hasNext();) {
	    	   WayBillTransVo element = (WayBillTransVo) iter.next();
	           if (set.add(element)){
	        	   newList.add(element);
	           }else{
	        	   repeatOrderNums += (element.getWayBillSourceNum()+"-"+element.getOrderFlagNum()+",");
	           }
	       }
	       if(repeatOrderNums!=null && repeatOrderNums.trim().length()>0){
	    	   logger.info("【从订单中心抓取初始订单至物流中心任务，重复未插入(当前处理批次中重复)的运单/订单信息："+repeatOrderNums+"】");
	       }
	       return newList;
	   }	

}
