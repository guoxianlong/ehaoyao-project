package com.ehaoyao.opertioncenter.decoctOrder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.decoctOrder.dao.IDecoctOrderDao;
import com.ehaoyao.opertioncenter.decoctOrder.service.IDecoctOrderService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class DecoctOrderServiceImpl implements IDecoctOrderService {

	@Autowired
	private IDecoctOrderDao iDecoctOrderDao;
	
	/**
	 * 煎药处订单查询
	 * @throws Exception 
	 */
	public PageModel<OrderInfo> getOrderInfos(PageModel<OrderInfo> pm,ThirdOrderAuditVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iDecoctOrderDao.getCountOrderInfos(vo);
			pm.setTotalRecords(count);
		}
		List<OrderInfo> ls = iDecoctOrderDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception{
		return iDecoctOrderDao.getOrderDetails(vo);
	}

	@Override
	public String updateOrderStatus(String[] orderNumberParam) throws Exception {
		String[] oprInfoArr = null;
		String orderNumberFlag = null;
		String orderStatus = null;
		OrderInfo orderInfo = null;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for(int i=0;orderNumberParam!=null&&i<orderNumberParam.length;i++){
			oprInfoArr = orderNumberParam[i]!=null&&orderNumberParam[i].length()>0?orderNumberParam[i].split(","):null;
			orderNumberFlag = oprInfoArr[0]!=null&&oprInfoArr[0].length()>0?oprInfoArr[0].trim():null;
			orderStatus = oprInfoArr[1]!=null&&oprInfoArr[1].length()>0?oprInfoArr[1].trim():null;
			if(orderNumberFlag!=null&&orderNumberFlag.length()>0){
					orderInfo = (OrderInfo) iDecoctOrderDao.getOrderInfosByOrderNumFlags(orderNumberFlag);
					orderInfo.setOrderStatus(orderStatus);
					orderInfoList.add(orderInfo);
			}
		}
		int[] updArr = iDecoctOrderDao.updateOrderInfoBatch(orderInfoList);
		return "成功完成"+updArr.length+"笔订单状态更新！";
	}

	
	@Override
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception {
		Object retnObj = null;
		if(vo!=null){
			retnObj = iDecoctOrderDao.getOrderInfosByOrderNumFlags((vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"")+(vo.getOrderFlag()!=null?vo.getOrderFlag().trim():""));
		}
		return retnObj;
	}

	@Override
	public JSONArray getOrderInfos(ThirdOrderAuditVO vo) throws Exception {
		OrderInfo orderInfo = null;
		JSONArray orderArr = new JSONArray();
		JSONArray orderDetailArr = new JSONArray();
		JSONObject json = new JSONObject();
		List<OrderDetail> orderDetailList = null;
		String orderNumFlags = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
		String[] orderNumFlagArr = orderNumFlags.split(",");
		if(orderNumFlagArr!=null&&orderNumFlagArr.length>0){
			for(int i=0;i<orderNumFlagArr.length;i++){
				orderInfo = (OrderInfo) iDecoctOrderDao.getOrderInfosByOrderNumFlags(orderNumFlagArr[i]);
				vo.setOrderNumber(orderInfo.getOrderNumber());
				vo.setOrderFlag(orderInfo.getOrderFlag());
				orderDetailList = iDecoctOrderDao.getOrderDetails(vo);
				orderDetailArr = JSONArray.fromObject(orderDetailList);
				json.put("orderInfo", JSONObject.fromObject(orderInfo));
				json.put("details", orderDetailArr);
				orderArr.add(json);
			}
		}
		return orderArr;
	}

}
