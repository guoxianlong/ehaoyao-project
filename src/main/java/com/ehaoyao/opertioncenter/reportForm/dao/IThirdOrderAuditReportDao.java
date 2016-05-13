package com.ehaoyao.opertioncenter.reportForm.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface IThirdOrderAuditReportDao {

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
	public List<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm, ThirdOrderAuditVO vo) throws Exception;

	/**
	 * 根据实体参数获取订单明细结果集详情
	 * @param vo  实体参数
	 * @return
	 * @throws Exception
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception;
	
	/**
	 * 根据查询条件获取订单
	 * @param vo 条件实体
	 * @return
	 * @throws Exception
	 */
	List<OrderMainInfo> getOrderMainInfosbyConditions(ThirdOrderAuditVO vo) throws Exception;
	

}
