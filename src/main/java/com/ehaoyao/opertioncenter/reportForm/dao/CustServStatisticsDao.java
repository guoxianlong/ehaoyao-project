package com.ehaoyao.opertioncenter.reportForm.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.reportForm.vo.CustServStatisticsVo;
import com.haoyao.goods.dao.BaseDao;

/**
 * 
 * <p>Title: CustServStatisticsDao</p>
 * <p>Description:客服统计 </p>
 * @author	zsj
 * @date	2015年1月29日下午3:53:54
 * @version 1.0
 */
public interface CustServStatisticsDao extends BaseDao<CustServStatisticsVo, Long>{
	/**
	 * <p>Title: queryStatisticsList</p>
	 * <p>Description: 查询客服统计</p>
	 * @param firstResult
	 * @param pageSize
	 * @param cs
	 * @return
	 */
	public List<CustServStatisticsVo> queryStatisticsList(int firstResult, int pageSize,
			CustServStatisticsVo cs);
	
	/**
	 * <p>Title: queryStatisticsCount</p>
	 * <p>Description: 客服统计总数</p>
	 * @param cs
	 * @return
	 */
	public int queryStatisticsCount(CustServStatisticsVo cs);
	
	/**
	 * <p>Title: writeToExcel</p>
	 * <p>Description:导出产品咨询日报 </p>
	 * @param request 
	 * @param cs
	 * @param workbook2003
	 * @return
	 */
	public void writeToExcel(HttpServletRequest request, CustServStatisticsVo cs,String name,
			HSSFWorkbook workbook2003);
	/**
	 * <p>Title: querySum</p>
	 * <p>Description:  查询合计</p>
	 * @param cs
	 * @return
	 */
	public CustServStatisticsVo querySum(CustServStatisticsVo cs);
	
}
