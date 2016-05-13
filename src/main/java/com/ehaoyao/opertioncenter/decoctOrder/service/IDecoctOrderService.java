package com.ehaoyao.opertioncenter.decoctOrder.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

import net.sf.json.JSONArray;

public interface IDecoctOrderService {
	
	/**
	 * 订单查询
	 * @throws Exception 
	 */
	public PageModel<OrderInfo> getOrderInfos(PageModel<OrderInfo> pm,ThirdOrderAuditVO vo) throws Exception;
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception;

	/**
	 * 更新订单状态
	 * @param orderNumberParam
	 * @return
	 * @throws Exception
	 */
	public String updateOrderStatus(String[] orderNumberParam) throws Exception;

	/**
	 * 根据订单号返回订单结果集
	 * @param orderNums
	 * @return
	 * @throws Exception
	 */
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception;

	public JSONArray getOrderInfos(ThirdOrderAuditVO vo) throws Exception;

}
