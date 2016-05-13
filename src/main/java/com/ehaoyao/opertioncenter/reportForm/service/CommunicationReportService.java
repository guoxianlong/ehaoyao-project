package com.ehaoyao.opertioncenter.reportForm.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.reportForm.vo.CommuInfoVo;


/**
 * 沟通记录报表
 * @author Administrator
 *
 */
public interface CommunicationReportService {
	
	/**
	 * 查询沟通记录
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<CommuInfoVo> queryCommuList(int firstResult, int pageSize, CommuInfoVo ci);
	
	/**
	 * 
	 * 沟通记录总数
	 * @param cvo
	 * @return
	 */
	public int queryCommuCount(CommuInfoVo ci);

	public HSSFWorkbook writeToExcel(CommuInfoVo ciVo,
			HSSFWorkbook workbook2003);
	
	public boolean controlShow(String conditons);
}
