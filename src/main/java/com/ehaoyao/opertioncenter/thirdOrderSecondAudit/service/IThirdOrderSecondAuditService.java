package com.ehaoyao.opertioncenter.thirdOrderSecondAudit.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface IThirdOrderSecondAuditService {
	
	/**
	 * 订单查询
	 * @throws Exception 
	 */
	public PageModel<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm,ThirdOrderAuditVO vo) throws Exception;
	
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

	/**
	 * 批量审核通过订单
	 * @param orderNumberFlagsParam 订单号/渠道 数组
	 * @return
	 */
	public String updateAuditStatusBatch(String[] orderNumberFlagsParam) throws Exception;

	/**
	 * 获取订单各项信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public OrderMainInfo getOrderMainInfo(ThirdOrderAuditVO vo) throws Exception;
}
