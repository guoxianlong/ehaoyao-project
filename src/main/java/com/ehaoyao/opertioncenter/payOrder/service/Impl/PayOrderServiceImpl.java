package com.ehaoyao.opertioncenter.payOrder.service.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.payOrder.dao.IPayOrderDao;
import com.ehaoyao.opertioncenter.payOrder.service.IPayOrderService;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderInfoDetailVO;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderShowInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;



@Service
public class PayOrderServiceImpl implements IPayOrderService {
	
	private static final Logger logger = Logger.getLogger(PayOrderServiceImpl.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private IPayOrderDao iPayOrderDao;
	
	
	public PageModel<OrderShowInfo> getOrderInfos(PageModel<OrderShowInfo> pm,OrderInfoDetailVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iPayOrderDao.getCountOrderInfos(vo);
		    pm.setTotalRecords(count);
		}
		List<OrderShowInfo> ls = iPayOrderDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(OrderInfoDetailVO vo) throws Exception{
		return iPayOrderDao.getOrderDetails(vo);
	}
	/**
	 * 获取数据库存在的渠道
	 * 
	 */
	
	public List<OrderShowInfo> getOrderFlag(){
		return iPayOrderDao.getOrderFlag();
	}
}
