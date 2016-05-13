package com.ehaoyao.opertioncenter.reportForm.dao;

import java.util.List;




import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.reportForm.vo.CommuInfoVo;
import com.haoyao.goods.dao.BaseDao;


/**
 * 沟通记录报表
 * @author Administrator
 *
 */
public interface CommunicationReportDao extends BaseDao<CommuInfoVo, Long>{

	/**
	 * 
	 * 查询沟通记录
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<CommuInfoVo> queryCommuList(int firstResult, Integer pageSize, CommuInfoVo ci);

	/**
	 * 
	 * 查询沟通记录总数
	 * @return
	 */
	public int queryCommuCount(CommuInfoVo ci);

	public HSSFWorkbook writeToExcel(CommuInfoVo ciVo,
			HSSFWorkbook workbook2003);

	public boolean controlShow(String conditons);
}
