package com.ehaoyao.opertioncenter.reportForm.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

public interface IThirdOrderAuditReportService {
	
	
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
	 * 导出excel
	 * @param vo
	 * @param workbook2003
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(ThirdOrderAuditVO vo, HSSFWorkbook workbook2003) throws Exception;

}
