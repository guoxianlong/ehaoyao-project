package com.ehaoyao.opertioncenter.reportForm.dao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.reportForm.dao.CustServStatisticsDao;
import com.ehaoyao.opertioncenter.reportForm.vo.CustServStatisticsVo;
import com.haoyao.goods.dao.impl.BaseDaoImpl;
/**
 * 
 * <p>Title: CustServStatisticsDaoImpl</p>
 * <p>Description:客服统计 </p>
 * @author	zsj
 * @date	2015年1月29日下午4:03:49
 * @version 1.0
 */
@Repository
public class CustServStatisticsDaoImpl extends BaseDaoImpl<CustServStatisticsVo, Long>
implements CustServStatisticsDao{

	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	
	/**
	 * <p>Title: queryStatisticsList</p>
	 * <p>Description: 查询客服统计</p>
	 * @param firstResult
	 * @param pageSize
	 * @param cs
	 * @return
	 */
	@Override
	public List<CustServStatisticsVo> queryStatisticsList(int firstResult, int pageSize,
			CustServStatisticsVo cs) {
		List<Object> paramList = new ArrayList<Object>();
		String sql= "SELECT t0.name,IFNULL(t1.consultAmount,0) consultAmount,IFNULL(t2.orderAmount,0) orderAmount,IFNULL(t2.orderTotalPrice,0) orderTotalPrice,IFNULL(t3.consultAmount,0) newUserConsult,IFNULL(t4.orderAmount,0) newUserOrder FROM "
				+ " (SELECT  u.name from user u) AS t0 LEFT JOIN "
				//当日咨询量
				+ " (SELECT  u.name,COUNT(t.consult_date) consultAmount FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName where 1=1 "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t1 on t0.name=t1.name LEFT JOIN "
				//订单数,订单总额
				+ " (SELECT  u.name,COUNT(c.is_place_order) orderAmount,SUM(c.order_total_price) orderTotalPrice FROM communication c LEFT JOIN track_info t ON t.commu_id=c.id and c.consult_date=t.consult_date LEFT JOIN user u ON c.cust_serv_code=u.userName WHERE c.is_place_order='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t2 on t1.name=t2.name LEFT JOIN "
				//新增会员咨询量
				+ " (SELECT  u.name,COUNT(t.consult_date) consultAmount FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName where c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t3 on t1.name=t3.name LEFT JOIN "
				//新增会员成单量
				+ " (SELECT  u.name,COUNT(c.is_place_order) orderAmount FROM communication c LEFT JOIN track_info t ON t.commu_id=c.id and c.consult_date=t.consult_date LEFT JOIN user u ON c.cust_serv_code=u.userName WHERE c.is_place_order='1' and c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t4 on t1.name=t4.name ";
		sql += " ORDER BY orderTotalPrice desc,consultAmount desc ";
		sql += " limit " + firstResult + "," + pageSize;
		List<CustServStatisticsVo> ls = jdbcTemplate.query(sql, paramList.toArray(),
				new BeanPropertyRowMapper<CustServStatisticsVo>(CustServStatisticsVo.class));
		return ls;
	}
	
	/**
	 * <p>Title: querySum</p>
	 * <p>Description:  查询合计</p>
	 * @param cs
	 * @return
	 */
	@Override
	public CustServStatisticsVo querySum(CustServStatisticsVo cs){
		List<Object> paramList = new ArrayList<Object>();
		String sql= " SELECT IFNULL(t1.consultAmount,0) consultAmount,IFNULL(t2.orderAmount,0) orderAmount,IFNULL(t2.orderTotalPrice,0) orderTotalPrice,IFNULL(t3.consultAmount,0) newUserConsult,IFNULL(t4.orderAmount,0) newUserOrder FROM  "
				+ " (SELECT  COUNT(t.consult_date) consultAmount FROM track_info t WHERE 1=1 "
				+ getWhereSql(cs, paramList)
				+ " ) AS t1,"
				+ " (SELECT  COUNT(c.is_place_order) orderAmount,SUM(c.order_total_price) orderTotalPrice FROM  communication c LEFT JOIN track_info t ON t.commu_id=c.id and c.consult_date=t.consult_date WHERE c.is_place_order='1' "
				+ getWhereSql(cs, paramList)
				+ " ) AS t2, "
				+ " (SELECT  COUNT(t.consult_date) consultAmount FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id WHERE c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " ) AS t3,"
				+ " (SELECT  COUNT(c.is_place_order) orderAmount FROM  communication c LEFT JOIN track_info t ON t.commu_id=c.id and c.consult_date=t.consult_date WHERE c.is_place_order='1' AND c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " ) AS t4 ";
		List<CustServStatisticsVo> custServList = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<CustServStatisticsVo>(CustServStatisticsVo.class));
		return custServList.get(0);
	}
	
	/**
	 * <p>Title: queryStatisticsCount</p>
	 * <p>Description:客服统计总数 </p>
	 * @param cs
	 * @return
	 */
	@Override
	public int queryStatisticsCount(CustServStatisticsVo cs) {
		List<Object> paramList = new ArrayList<Object>();
		String sql= "SELECT count(*) FROM "
				+ " (SELECT  u.name from user u) AS t0 LEFT JOIN "
				+ " (SELECT  u.name,COUNT(t.consult_date) consultAmount FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName where 1=1 "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t1 on t0.name=t1.name LEFT JOIN "
				+ " (SELECT  u.name,COUNT(c.is_place_order) orderAmount,SUM(c.order_total_price) orderTotalPrice FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName WHERE c.is_place_order='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t2 on t1.name=t2.name LEFT JOIN "
				+ " (SELECT  u.name,COUNT(t.consult_date) consultAmount FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName where c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t3 on t1.name=t3.name LEFT JOIN "
				+ " (SELECT  u.name,COUNT(c.is_place_order) orderAmount,SUM(c.order_total_price) orderTotalPrice FROM track_info t LEFT JOIN communication c ON t.commu_id=c.id LEFT JOIN user u ON c.cust_serv_code=u.userName WHERE c.is_place_order='1' and c.is_new_user='1' "
				+ getWhereSql(cs, paramList)
				+ " GROUP BY u.name) AS t4 on t1.name=t4.name ";
		Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	
	/**
	 * <p>Title: writeToExcel</p>
	 * <p>Description:导出产品咨询日报 </p>
	 * @param cs
	 * @param workbook2003
	 * @return
	 */
	@Override
	public void writeToExcel(HttpServletRequest request,CustServStatisticsVo cs,String name,
			HSSFWorkbook workbook2003) {
		int count = queryStatisticsCount(cs);
		List<CustServStatisticsVo> custList = queryStatisticsList(0, count,cs);
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("产品咨询日报");
		// 创建默认样式
		HSSFCellStyle commonStyle = (HSSFCellStyle) workbook2003
				.createCellStyle();// 设置字体样式
		HSSFFont commonFont = (HSSFFont) workbook2003.createFont();
		commonFont.setFontName("宋体");
		commonFont.setFontHeightInPoints((short) 12);
		commonStyle.setFont(commonFont);
		commonStyle.setWrapText(true); // 设置为自动换行
		commonStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		commonStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		commonStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		commonStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		commonStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		commonStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		commonStyle.setFillBackgroundColor(HSSFColor.RED.index);
		// 创建标题样式
		HSSFCellStyle headerStyle = workbook2003.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = (HSSFFont) workbook2003.createFont(); // 创建字体样式
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		headerFont.setFontName("宋体"); // 设置字体类型
		headerFont.setFontHeightInPoints((short) 16); // 设置字体大小
		headerStyle.setFont(headerFont); // 为标题样式设置字体样式
		// 创建查询条件样式
		HSSFCellStyle conditionStyle = (HSSFCellStyle) workbook2003
				.createCellStyle();// 设置字体样式
		HSSFFont conditionFont = (HSSFFont) workbook2003.createFont();
		conditionFont.setFontName("宋体");
		conditionFont.setFontHeightInPoints((short) 12);
		conditionFont.setColor(HSSFColor.RED.index);
		conditionStyle.setFont(conditionFont);
		conditionStyle.setFillBackgroundColor(HSSFColor.RED.index);
		conditionStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		conditionStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		conditionStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		conditionStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		conditionStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		conditionStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 创建表头样式
		HSSFCellStyle cell_Style = (HSSFCellStyle) workbook2003
				.createCellStyle();
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style.setWrapText(true); // 设置为自动换行
		HSSFFont cell_Font = (HSSFFont) workbook2003.createFont();
		cell_Font.setFontName("宋体");
		cell_Font.setFontHeightInPoints((short) 12);
		cell_Font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cell_Style.setFillBackgroundColor(HSSFColor.RED.index);// 设置背景

		// 设置默认字体和列宽
		for (int i = 0; i <= 9; i++) {
			sheet.setDefaultColumnStyle(i, commonStyle);
			sheet.setColumnWidth(i, 4750);
		}

		// 产品咨询日报标题
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(30f);
		HSSFCell cell00 = row0.createCell(0);
		cell00.setCellStyle(headerStyle);
		cell00.setCellValue("产品咨询日报");
		
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 9));
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell10 = row1.createCell(0);
		cell10.setCellStyle(conditionStyle);
		StringBuffer sb=new StringBuffer();
		// 统计日期
		if (cs.getConsultDate() != null && !"".equals(cs.getConsultDate().trim())) {
			sb.append("统计日期:"+cs.getConsultDate()+"    ");
		} else {
			sb.append("统计日期:"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"    ");
		}
		// 查询人
		sb.append("查询人:"+name+"    ");
		//查询时间
		sb.append("查询时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"    ");
		cell10.setCellValue(sb.toString());
		
		// 创建表头
		HSSFRow row4 = sheet.createRow(4);
		HSSFCell cell40 = row4.createCell(0);
		cell40.setCellStyle(cell_Style);
		cell40.setCellValue("客服姓名");

		HSSFCell cell41 = row4.createCell(1);
		cell41.setCellStyle(cell_Style);
		cell41.setCellValue("订单总额");

		HSSFCell cell42 = row4.createCell(2);
		cell42.setCellStyle(cell_Style);
		cell42.setCellValue("订单数");

		HSSFCell cell43 = row4.createCell(3);
		cell43.setCellStyle(cell_Style);
		cell43.setCellValue("客单价");
		
		HSSFCell cell44 = row4.createCell(4);
		cell44.setCellStyle(cell_Style);
		cell44.setCellValue("当日咨询量");
		
		HSSFCell cell45 = row4.createCell(5);
		cell45.setCellStyle(cell_Style);
		cell45.setCellValue("咨询转换率");

		HSSFCell cell46 = row4.createCell(6);
		cell46.setCellStyle(cell_Style);
		cell46.setCellValue("新增会员咨询量");

		HSSFCell cell47 = row4.createCell(7);
		cell47.setCellStyle(cell_Style);
		cell47.setCellValue("新增会员成单量");

		HSSFCell cell48 = row4.createCell(8);
		cell48.setCellStyle(cell_Style);
		cell48.setCellValue("新增会员转换率");
		
		HSSFCell cell49 = row4.createCell(9);
		cell49.setCellStyle(cell_Style);
		cell49.setCellValue("复购量");
		
		//写入查询数据
		DecimalFormat df=new DecimalFormat("######0.00");
		for (int i = 0; i < custList.size(); i++) {
			HSSFRow perRow = sheet.createRow(i + 5);
			CustServStatisticsVo perCs = custList.get(i);
			// 客服姓名
			if (perCs.getName() != null) {
				HSSFCell createCell = perRow.createCell(0);
				createCell.setCellStyle(conditionStyle);
				createCell.setCellValue(perCs.getName());
			}
			//订单总额
			perRow.createCell(1).setCellValue(df.format(Double.parseDouble(perCs.getOrderTotalPrice()))+"￥");
			//订单数
			perRow.createCell(2).setCellValue(perCs.getOrderAmount());
			//客单价
			if(!perCs.getOrderAmount().equals("0") && perCs.getOrderAmount()!=null){
				perRow.createCell(3).setCellValue(df.format(Double.parseDouble(perCs.getOrderTotalPrice())/Double.parseDouble(perCs.getOrderAmount()))+"￥");
			}else{
				perRow.createCell(3).setCellValue("0.00￥");
			}
			//当日咨询量
			perRow.createCell(4).setCellValue(perCs.getConsultAmount());
			//咨询转换率
			if(!perCs.getConsultAmount().equals("0") && perCs.getConsultAmount()!=null){
				perRow.createCell(5).setCellValue(df.format(100*Double.parseDouble(perCs.getOrderAmount())/Double.parseDouble(perCs.getConsultAmount()))+"%");
			}else{
				perRow.createCell(5).setCellValue("0.00%");
			}
			
			//新增会员咨询量
			perRow.createCell(6).setCellValue(perCs.getNewUserConsult());
			//新增会员成单量
			perRow.createCell(7).setCellValue(perCs.getNewUserOrder());
			//新增会员转换率
			if(!perCs.getNewUserConsult().equals("0") && perCs.getNewUserConsult()!=null){
				perRow.createCell(8).setCellValue(df.format(100*Double.parseDouble(perCs.getNewUserOrder())/Double.parseDouble(perCs.getNewUserConsult()))+"%");
			}else{
				perRow.createCell(8).setCellValue("0.00%");
			}
			//复购量
			perRow.createCell(9).setCellValue(Integer.parseInt(perCs.getOrderAmount())-Integer.parseInt(perCs.getNewUserOrder()));
		}
		//写入合计
		HSSFRow lastRow = sheet.createRow(custList.size() + 5);
		lastRow.createCell(0).setCellValue("合计");
		lastRow.createCell(1).setCellValue(df.format(Double.parseDouble(request.getParameter("orderTotalPriceSum")))+"￥");
		lastRow.createCell(2).setCellValue(request.getParameter("orderAmountSum")+"");
		lastRow.createCell(3).setCellValue(request.getParameter("perOrderPriceSum")+"￥");
		lastRow.createCell(4).setCellValue(request.getParameter("consultAmountSum")+"");
		lastRow.createCell(5).setCellValue(request.getParameter("transferSum")+"%");
		lastRow.createCell(6).setCellValue(request.getParameter("newUserConsultSum")+"");
		lastRow.createCell(7).setCellValue(request.getParameter("newUserOrderSum")+"");
		lastRow.createCell(8).setCellValue(request.getParameter("newTransferSum")+"%");
		lastRow.createCell(9).setCellValue(request.getParameter("rePurchaseSum")+"");
	}
	
	
	
	
	//获取查询条件
	private String getWhereSql(CustServStatisticsVo cs, List<Object> paramList) {
		StringBuffer sb = new StringBuffer();
		if (cs != null) {
			// 咨询日期
			if (cs.getConsultDate() != null && cs.getConsultDate().trim().length() > 0) {
				sb.append(" and t.consult_date >= ?");
				paramList.add(cs.getConsultDate().trim()+" 00:00:00");
				sb.append(" and t.consult_date <= ?");
				paramList.add(cs.getConsultDate().trim()+" 23:59:59");
			}
		}
		return sb.toString();
	}
	
}
