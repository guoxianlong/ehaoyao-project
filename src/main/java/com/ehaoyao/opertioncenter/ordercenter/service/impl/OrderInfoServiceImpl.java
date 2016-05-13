package com.ehaoyao.opertioncenter.ordercenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.ordercenter.orderModel.OrderDetail;
import com.ehaoyao.opertioncenter.ordercenter.service.OrderInfoService;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderDataVO;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderParamVO;
import com.ehaoyao.opertioncenter.send.dao.OrderInfoDao;

/**
 * 
 * Title: OrderInfoServiceImpl.java
 * 
 * Description: 订单中心订单信息
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月23日 下午2:48:30
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
	
	@Autowired
	private OrderInfoDao orderDao;

	/**
	 * 查询订单
	 */
	@Override
	public PageModel<OrderDataVO> getOrderData(PageModel<OrderDataVO> pm,OrderParamVO orderVO) {
		if(pm.getPageSize()>0){
			int count = orderDao.getOrderDataCount(orderVO);
			pm.setTotalRecords(count);
		}
		List<OrderDataVO> ls = orderDao.getOrderData(pm,orderVO);
		pm.setList(ls);
		return pm;
	}

	/**
	 * 查询订单详情
	 */
	@Override
	public List<OrderDetail> getOrderDetails(OrderParamVO orderVO) {
		return orderDao.getOrderDetails(orderVO);
	}
	
	/**
	 * 新订单数量
	 */
	public int getNewOrderCount(){
		OrderParamVO orderVO = new OrderParamVO();
		//初始化，未处理订单
		orderVO.setJdsOrderStatus("0");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(df.parse(df.format(new Date())));
			//3天之内
			ca.add(Calendar.DAY_OF_MONTH, -3);
		} catch (Exception e) {
		}
 		orderVO.setStartDate(df.format(ca.getTime()));
		int count = orderDao.getOrderDataCount(orderVO);
		return count;
	}

}
