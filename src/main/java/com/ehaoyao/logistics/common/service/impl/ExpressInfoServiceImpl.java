package com.ehaoyao.logistics.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.ordercenter.ExpressInfoMapper;
import com.ehaoyao.logistics.common.model.ordercenter.OrderInfo;
import com.ehaoyao.logistics.common.service.ExpressInfoService;
import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.vo.OrderExpressVo;

@Transactional(value="transactionManagerOrderCenter")
@Service(value="expressInfoService")
public class ExpressInfoServiceImpl implements ExpressInfoService {

	private static ResourceBundle appConfigs = ResourceBundle.getBundle("application");
	
	@Autowired
	ExpressInfoMapper expressInfoMapper;
	
	@Resource(name="sqlSessionFactoryOrderCenter")
	SqlSessionFactory sqlSessionFactory;
	
	@Resource(name="sqlSessionTemplateOrderCenter")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	
	@Override
	public List<OrderExpressVo> selectExpressInfoList() throws Exception {
		List<OrderExpressVo> orderExpressList;
		
		//1,	从订单中心获取已配送的初始订单
		int orderIntervalTime = Integer.parseInt(appConfigs.getString("normal_tologistics_minute"));
		String startTime=DateUtil.getDate(DateUtil.getPreMinute(orderIntervalTime),2,null);//当前时间向前推迟xxx分钟
		String endTime=DateUtil.getDate(new Date(), 2, null);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String[] orderStatusArr = {OrderInfo.ORDER_INFO_ORDER_STATUS_POST,OrderInfo.ORDER_INFO_ORDER_STATUS_SEND};
		map.put("orderStatusArr", orderStatusArr);
		map.put("wayBillTimeStart", startTime);
		map.put("wayBillTimeEnd", endTime);
		orderExpressList = expressInfoMapper.selectHasShipByCondition(map);
		
		return orderExpressList;
	}

}
