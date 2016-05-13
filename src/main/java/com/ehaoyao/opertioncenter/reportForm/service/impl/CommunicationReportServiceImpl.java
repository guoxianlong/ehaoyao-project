package com.ehaoyao.opertioncenter.reportForm.service.impl;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.reportForm.dao.CommunicationReportDao;
import com.ehaoyao.opertioncenter.reportForm.service.CommunicationReportService;
import com.ehaoyao.opertioncenter.reportForm.vo.CommuInfoVo;


/**
 * 沟通记录报表
 * @author Administrator
 *
 */
@Service
public class CommunicationReportServiceImpl implements CommunicationReportService {

	@Autowired
	private CommunicationReportDao commuDao;

	/**
	 * @param commuDao the commuDao to set
	 */
	public void setCommuDao(CommunicationReportDao commuDao) {
		this.commuDao = commuDao;
	}
	/**
	 * 查询沟通记录列表
	 */
	@Override
	public List<CommuInfoVo> queryCommuList(int firstResult, int pageSize,
			CommuInfoVo ci) {
		return commuDao.queryCommuList(firstResult, pageSize, ci);
	}
	/**
	 * 沟通记录总数
	 */
	@Override
	public int queryCommuCount(CommuInfoVo ci) {
		return commuDao.queryCommuCount(ci);
	}
	/**
	 * 将集合数据写入excel
	 */
	@Override
	public HSSFWorkbook writeToExcel(CommuInfoVo ciVo,
			HSSFWorkbook workbook2003) {
		return commuDao.writeToExcel(ciVo,workbook2003);
	}
	
	/**
	 * 控制界面元素展示及导出
	 */
	@Override
	public boolean controlShow(String conditons){
		return commuDao.controlShow(conditons);
	}
}
