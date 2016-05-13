package com.ehaoyao.opertioncenter.reportForm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.reportForm.vo.CustServStatisticsVo;

/**
 * 
 * <p>Title: CustServStatisticsService</p>
 * <p>Description:客服统计 </p>
 * @author	zsj
 * @date	2015年1月29日下午4:06:25
 * @version 1.0
 */
public interface CustServStatisticsService {

	/**
	 * 查询客服统计
	 * @param firstResult
	 * @param pageSize
	 * @param cs
	 * @return
	 */
	public List<CustServStatisticsVo> queryStatisticsList(int firstResult, int pageSize,
			CustServStatisticsVo cs);

	/**
	 * 客服统计总数
	 * @param cs
	 * @return
	 */
	public int queryStatisticsCount(CustServStatisticsVo cs);
	
	/**
	 * 查询合计
	 * @param cs
	 * @return
	 */
	public CustServStatisticsVo querySum(CustServStatisticsVo cs);
	
	/**
	 * 导出产品咨询日报
	 * @param request 
	 * @param cs
	 * @param name 
	 * @param workbook2003
	 * @return
	 */
	public void writeToExcel(HttpServletRequest request, CustServStatisticsVo cs,
			String name, HSSFWorkbook workbook2003);

}
