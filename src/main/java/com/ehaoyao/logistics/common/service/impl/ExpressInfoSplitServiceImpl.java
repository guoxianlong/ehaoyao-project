package com.ehaoyao.logistics.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.ordercenter.ExpressInfoRemoveMapper;
import com.ehaoyao.logistics.common.service.ExpressInfoSplitService;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;

@Transactional(value="transactionManagerOrderCenter")
@Service(value="expressInfoSplitService")
public class ExpressInfoSplitServiceImpl implements ExpressInfoSplitService {

private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	
	@Autowired
	ExpressInfoRemoveMapper expressInfoSplitMapper;
	
	@Override
	public List<OrderExpressVo> selectExpressInfoSplitList() throws Exception {
		List<OrderExpressVo> orderExpressList;
		
		//1,	从订单中心获取已配送的初始订单
		int orderIntervalTime = Integer.parseInt(appConfigs.getString("normal_tologistics_minute"));
		String startTime=DateUtil.getDate(DateUtil.getPreMinute(orderIntervalTime),2,null);//当前时间向前推迟xxx分钟
		String endTime=DateUtil.getDate(new Date(), 2, null);
		
		Map<String,Object> map = new HashMap<String,Object>();
		/**
		 * 拆单情况，有可能出现一单配送成功并回写订单中心物流状态，而其他单无法初始物流表的状况，所以不需增加s02状态的限制
		 */
//		String[] orderStatusArr = {OrderInfo.ORDER_INFO_ORDER_STATUS_SEND};
//		map.put("orderStatusArr", orderStatusArr);
		map.put("wayBillTimeStart", startTime);
		map.put("wayBillTimeEnd", endTime);
		orderExpressList = expressInfoSplitMapper.selectHasShipByCondition(map);
		
		return orderExpressList;
	}

}
