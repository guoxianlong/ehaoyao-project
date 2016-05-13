package com.ehaoyao.opertioncenter.cmOrderAudit.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface ICMOrderAuditService {
	
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
	 * 根据订单号返回订单结果集
	 * @param orderNums
	 * @return
	 * @throws Exception
	 */
	public Object getOrderInfosByOrderNums(ThirdOrderAuditVO vo) throws Exception;
	
	/**
	 * 更新订单审核状态
	 * @param vo 参数实体
	 * @return
	 * @throws Exception
	 */
	public String updateAuditStatus(ThirdOrderAuditVO vo) throws Exception;


}
