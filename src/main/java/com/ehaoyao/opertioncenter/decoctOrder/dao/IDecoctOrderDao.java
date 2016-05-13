package com.ehaoyao.opertioncenter.decoctOrder.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface IDecoctOrderDao {


	/**
	 * 获取查询总条数
	 * @param vo 实体参数
	 * @return
	 * @throws Exception
	 */
	public int getCountOrderInfos(ThirdOrderAuditVO vo) throws Exception;

	/**
	 * 根据实体参数获取查询订单结果集
	 * @param pm 分页对象
	 * @param vo 实体参数
	 * @return
	 * @throws Exception
	 */
	public List<OrderInfo> getOrderInfos(PageModel<OrderInfo> pm, ThirdOrderAuditVO vo) throws Exception;

	/**
	 * 根据实体参数获取订单明细结果集详情
	 * @param vo  实体参数
	 * @return
	 * @throws Exception
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception;
	
	
	/**
	 * 根据订单号批量更新订单表
	 * @param orderInfoList
	 * @return
	 * @throws Exception
	 */
	public int[] updateOrderInfoBatch(final List<OrderInfo> orderInfoList) throws Exception ;

	

	/**
	 * 根据订单号返回订单结果集
	 * @param orderNumFlags  订单号渠道拼接串
	 * @return
	 * @throws Exception
	 */
	public Object getOrderInfosByOrderNumFlags(String orderNumFlags) throws Exception ;
}
