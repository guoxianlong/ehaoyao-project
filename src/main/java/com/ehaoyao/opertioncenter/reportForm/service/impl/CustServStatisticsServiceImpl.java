package com.ehaoyao.opertioncenter.reportForm.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.reportForm.dao.CustServStatisticsDao;
import com.ehaoyao.opertioncenter.reportForm.service.CustServStatisticsService;
import com.ehaoyao.opertioncenter.reportForm.vo.CustServStatisticsVo;

/**
 * 
 * <p>Title: CustServStatisticsServiceImpl</p>
 * <p>Description:客服统计 </p>
 * @author	zsj
 * @date	2015年1月29日下午4:06:06
 * @version 1.0
 */
@Service
public class CustServStatisticsServiceImpl implements CustServStatisticsService{
	
	@Autowired
	CustServStatisticsDao statisticsDao;
	/**
	 * 查询客服统计
	 * @param firstResult
	 * @param pageSize
	 * @param cs
	 * @return
	 */
	public List<CustServStatisticsVo> queryStatisticsList(int firstResult, int pageSize,
			CustServStatisticsVo cs) {
		return statisticsDao.queryStatisticsList(firstResult,pageSize,cs);
	}
	
	/**
	 * 查询合计
	 * @param cs
	 * @return
	 */
	@Override
	public CustServStatisticsVo querySum(CustServStatisticsVo cs) {
		return statisticsDao.querySum(cs);
	}
	
	/**
	 * 客服统计总数
	 * @param cs
	 * @return
	 */
	public int queryStatisticsCount(CustServStatisticsVo cs) {
		return statisticsDao.queryStatisticsCount(cs);
	}

	/**
	 * 导出产品咨询日报
	 * @param cs
	 * @param workbook2003
	 * @return
	 */
	public void writeToExcel(HttpServletRequest request,CustServStatisticsVo cs,String name,
			HSSFWorkbook workbook2003) {
		 statisticsDao.writeToExcel(request,cs,name,workbook2003);
	}


}
