package com.ehaoyao.opertioncenter.reportForm.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.reportForm.dao.CommunicationReportDao;
import com.ehaoyao.opertioncenter.reportForm.vo.CommuInfoVo;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 沟通记录报表
 * 
 * @author Administrator
 * 
 */
@Repository
public class CommunicationReportDaoImpl extends BaseDaoImpl<CommuInfoVo, Long>
		implements CommunicationReportDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * 沟通记录查询
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return
	 */
	@Override
	public List<CommuInfoVo> queryCommuList(int firstResult, Integer pageSize,
			CommuInfoVo ci) {

		String sql = "select c.cust_serv_code,c.accept_result,c.second_type,c.cust_no,c.cust_source,"// 客服工号,沟通类型(一级),沟通类型(二级)客户编号,客户来源
				+ "t.pro_skus,c.consult_date,c.create_time,c.is_order,c.place_order_date,"// 产品编码,咨询日期,最近操作,是否订购,成单日期
				+ "c.is_place_order,t.pro_keywords,c.dep_category,c.disease_category,c.order_quantity,"// 是否成单,产品关键词,科组类别,病种类别,订单数量
				+ "c.order_total_price,t.is_track,t.track_info,t.no_order_cause,u.name,c.tel,c.is_new_user"// 订单金额,是否跟踪,跟踪信息,客服姓名,客户电话,是否新用户
				+ " from communication AS c LEFT JOIN track_info t on c.id=t.commu_id and c.consult_date=t.consult_date"//连接track_info表
				+ " LEFT JOIN user u on c.cust_serv_code=u.userName where 1=1";//连接user表
		List<Object> paramList = new ArrayList<Object>();
		sql += getWhereSql(ci, paramList);
		sql += " ORDER BY c.create_time desc ";
		sql += " limit " + firstResult + "," + pageSize;
		List<CommuInfoVo> ls = jdbcTemplate.query(sql, paramList.toArray(),
				new BeanPropertyRowMapper<CommuInfoVo>(CommuInfoVo.class));
		return ls;
	}

	// 沟通记录总数
	@Override
	public int queryCommuCount(CommuInfoVo ci) {
		String sql = "select count(*) from communication AS c LEFT JOIN track_info AS t on c.id=t.commu_id and c.consult_date=t.consult_date"
				+ " LEFT JOIN user u on c.cust_serv_code=u.userName where 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		sql += getWhereSql(ci, paramList);
		Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(),
				Integer.class);
		return count;
	}

	// 获取查询条件
	private String getWhereSql(CommuInfoVo ci, List<Object> paramList) {

		StringBuffer wsql = new StringBuffer();
		if (ci != null) {
			// 咨询日期
			if (ci.getStartDate() != null
					&& ci.getStartDate().trim().length() > 0) {
				wsql.append(" and c.create_time >= ?");
				paramList.add(ci.getStartDate().trim());
			}
			if (ci.getEndDate() != null && ci.getEndDate().trim().length() > 0) {
				wsql.append(" and c.create_time <= ?");
				paramList.add(ci.getEndDate().trim());
			}
			// 客服工号
			if (ci.getCustServCode() != null
					&& !"".equals(ci.getCustServCode().trim())) {
				wsql.append(" and c.cust_serv_code = ?");
				paramList.add(ci.getCustServCode().trim());
			}
			// 客服姓名
			if (ci.getName() != null
					&& !"".equals(ci.getName().trim())) {
				wsql.append(" and u.name = ?");
				paramList.add(ci.getName().trim());
			}
			// 联系分组
			if (ci.getConnGroup() != null
					&& !"".equals(ci.getConnGroup().trim())) {
				// 得到3天前的date
				Date now = new Date();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String thrBefore = df.format(new Date(now.getTime() - 3 * 24
						* 60 * 60 * 1000));
				if (ci.getConnGroup().equals("2")) {
					wsql.append(" and c.create_time >= ?");
					paramList.add(thrBefore.trim());
				}
				if (ci.getConnGroup().equals("3")) {
					wsql.append(" and c.create_time <= ?");
					paramList.add(thrBefore.trim());
				}
			}
			// 客户来源
			if (ci.getCustSource() != null
					&& !"".equals(ci.getCustSource().trim())) {
				wsql.append(" and c.cust_source = ?");
				paramList.add(ci.getCustSource().trim());
			}
			// 沟通类别（一级）
			if (ci.getAcceptResult() != null
					&& !"".equals(ci.getAcceptResult().trim())) {
				wsql.append(" and c.accept_result = ?");
				paramList.add(ci.getAcceptResult().trim());
			}
			// 沟通类别（二级）
			if (ci.getSecondType() != null
					&& !"".equals(ci.getSecondType().trim())) {
				wsql.append(" and c.second_type = ?");
				paramList.add(ci.getSecondType().trim());
			}
			// 品类类别
			if (ci.getProCategory() != null
					&& !"".equals(ci.getProCategory().trim())) {
				wsql.append(" and t.pro_category = ?");
				paramList.add(ci.getProCategory().trim());
			}
			// 科组类别
			if (ci.getDepCategory() != null
					&& !"".equals(ci.getDepCategory().trim())) {
				wsql.append(" and t.dep_category = ?");
				paramList.add(ci.getDepCategory().trim());
			}
			// 病种类别
			if (ci.getDiseaseCategory() != null
					&& !"".equals(ci.getDiseaseCategory().trim())) {
				wsql.append(" and t.disease_category = ?");
				paramList.add(ci.getDiseaseCategory().trim());
			}
			// 是否成单
			if (ci.getIsPlaceOrder() != null
					&& !"".equals(ci.getIsPlaceOrder().trim())) {
				wsql.append(" and c.is_place_order = ?");
				paramList.add(ci.getIsPlaceOrder().trim());
			}
			// 是否订购
			if (ci.getIsOrder() != null && !"".equals(ci.getIsOrder().trim())) {
				wsql.append(" and c.is_order = ?");
				paramList.add(ci.getIsOrder().trim());
			}
			// 是否跟踪
			if (ci.getIsTrack() != null && !"".equals(ci.getIsTrack().trim())) {
				wsql.append(" and t.is_track = ?");
				paramList.add(ci.getIsTrack().trim());
			}
			// 是否新用户
			if (ci.getIsNewUser() != null && !"".equals(ci.getIsNewUser().trim())) {
				wsql.append(" and c.is_new_user = ?");
				paramList.add(ci.getIsNewUser().trim());
			}
			// 今日跟踪
			if (ci.getTodayTrack() != null
					&& !"".equals(ci.getTodayTrack().trim())) {
				// 得到今日零时
				String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";
				if (ci.getTodayTrack().equals("1")) {
					wsql.append(" and c.consult_date >= ?");
					paramList.add(todayString.trim());
				}
				if (ci.getTodayTrack().equals("0")) {
					wsql.append(" and c.consult_date < ?");
					paramList.add(todayString.trim());
				}
			}
			//产品编码
			if (ci.getProSkus() != null
					&& !"".equals(ci.getProSkus().trim())) {
				wsql.append(" and t.pro_skus like ?");
				paramList.add("%"+ci.getProSkus().trim()+"%");
			}
			//产品关键字
			if (ci.getProKeywords() != null
					&& !"".equals(ci.getProKeywords().trim())) {
				wsql.append(" and t.pro_keywords like ?");
				paramList.add("%"+ci.getProKeywords().trim()+"%");
			}
		}
		return wsql.toString();
	}

	/**
	 * <p>
	 * Title: writeToExcel
	 * </p>
	 * <p>
	 * Description:将集合写入excel
	 * </p>
	 */
	@Override
	public HSSFWorkbook writeToExcel(CommuInfoVo ciVo, HSSFWorkbook workbook2003) {
		int count = queryCommuCount(ciVo);
		List<CommuInfoVo> commuList = queryCommuList(0, count, ciVo);
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("沟通记录");
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
		headerStyle.setFillBackgroundColor(HSSFColor.RED.index);
		headerStyle.setFillForegroundColor(HSSFColor.RED.index);
		// 创建查询条件样式
		HSSFCellStyle conditionStyle = (HSSFCellStyle) workbook2003
				.createCellStyle();// 设置字体样式
		HSSFFont conditionFont = (HSSFFont) workbook2003.createFont();
		conditionFont.setFontName("宋体");
		conditionFont.setFontHeightInPoints((short) 12);
		conditionFont.setColor(HSSFColor.RED.index);
		conditionStyle.setFont(conditionFont);
		conditionStyle.setFillBackgroundColor(HSSFColor.RED.index);
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
		for (int i = 0; i < 21; i++) {
			sheet.setDefaultColumnStyle(i, commonStyle);
			sheet.setColumnWidth(i, 3500);
		}
		// 设置特定列宽
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(14, 2000);
		sheet.setColumnWidth(15, 3500);
		sheet.setColumnWidth(16, 2000);
		sheet.setColumnWidth(17, 3500);
		sheet.setColumnWidth(8, 3500);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 6500);//咨询日期
		sheet.setColumnWidth(7, 6500);
		sheet.setColumnWidth(9, 6500);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(18, 7000);
		sheet.setColumnWidth(19, 7000);
		sheet.setColumnWidth(20, 7000);

		// 沟通记录标题
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 20));
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(30f);

		HSSFCell cell00 = row0.createCell(0);
		cell00.setCellStyle(headerStyle);
		cell00.setCellValue("沟通记录");
		// 咨询日期
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell10 = row1.createCell(0);
		if (ciVo.getStartDate() != null
				&& !"".equals(ciVo.getStartDate().trim())) {
			if (ciVo.getEndDate() != null
					&& !"".equals(ciVo.getEndDate().trim())) {
				cell10.setCellValue("咨询日期:"
						+ ciVo.getStartDate().substring(0, 10) + "至"
						+ ciVo.getEndDate().substring(0, 10));
			} else {
				cell10.setCellValue("咨询日期:"
						+ ciVo.getStartDate().substring(0, 10) + "至"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
		} else {
			if (ciVo.getEndDate() != null
					&& !"".equals(ciVo.getEndDate().trim())) {
				cell10.setCellValue("咨询日期:"
						+ ciVo.getEndDate().substring(0, 10) + "以前");
			} else {
				cell10.setCellValue("咨询日期:全部");
			}
		}
	/*	// 客服工号
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
		HSSFCell cell14 = row1.createCell(4);
		if (ciVo.getCustServCode() != null
				&& !"".equals(ciVo.getCustServCode().trim())) {
			cell14.setCellValue("客服工号:" + ciVo.getCustServCode());
		} else {
			cell14.setCellValue("客服工号:");
		}*/
		// 客服姓名
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
		HSSFCell cell14 = row1.createCell(4);
		if (ciVo.getName() != null
				&& !"".equals(ciVo.getName().trim())) {
			cell14.setCellValue("客服姓名:" + ciVo.getName());
		} else {
			cell14.setCellValue("客服姓名:全部");
		}
		// 联系分组
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 7));
		HSSFCell cell16 = row1.createCell(6);
		cell16.setCellValue("联系分组:全部");
		if (ciVo.getConnGroup() != null
				&& !"".equals(ciVo.getConnGroup().trim())) {
			cell16.setCellValue("联系分组:"
					+ (ciVo.getConnGroup().equals("2") ? "呼入组" : "呼出组"));
		}
		// 沟通类型
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));
		HSSFCell cell18 = row1.createCell(8);
		cell18.setCellValue("沟通类型:全部");
		if (ciVo.getAcceptResult() != null
				&& !"".equals(ciVo.getAcceptResult().trim())) {
			cell18.setCellValue("沟通类型:" + ciVo.getAcceptResult());
		}
		// 今日跟踪
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 11));
		HSSFCell cell110 = row1.createCell(10);
		cell110.setCellValue("今日跟踪:全部");
		if (ciVo.getTodayTrack() != null
				&& !"".equals(ciVo.getTodayTrack().trim())) {
			cell110.setCellValue("今日跟踪:" + (ciVo.getTodayTrack().equals("1") ? "已跟踪" : "未跟踪"));
		}

		// 品类类别
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
		HSSFRow row2 = sheet.createRow(2);
		HSSFRow row3 = sheet.createRow(3);
		row3.setRowStyle(conditionStyle);
		HSSFCell cell20 = row2.createCell(0);
		cell20.setCellValue("品类类别:全部");
		if (ciVo.getProCategory() != null
				&& !"".equals(ciVo.getProCategory().trim())) {
			cell20.setCellValue("品类类别:" + ciVo.getProCategory());
		}
		// 科种类别
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
		HSSFCell cell22 = row2.createCell(2);
		cell22.setCellValue("科种类别:全部");
		if (ciVo.getDepCategory() != null
				&& !"".equals(ciVo.getDepCategory().trim())) {
			cell22.setCellValue("科种类别:" + ciVo.getDepCategory());
		}
		// 病种类别
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		HSSFCell cell24 = row2.createCell(4);
		cell24.setCellValue("病种类别:全部");
		if (ciVo.getDiseaseCategory() != null
				&& !"".equals(ciVo.getDiseaseCategory().trim())) {
			cell24.setCellValue("病种类别:" + ciVo.getDiseaseCategory());
		}
		// 是否成单
		HSSFCell cell26 = row2.createCell(6);
		cell26.setCellValue("是否成单:全部");
		if (ciVo.getIsPlaceOrder() != null
				&& !"".equals(ciVo.getIsPlaceOrder().trim())) {
			cell26.setCellValue("是否成单:"
					+ (ciVo.getIsPlaceOrder().equals("1") ? "是" : "否"));
		}
		// 是否订购
		HSSFCell cell27 = row2.createCell(7);
		cell27.setCellValue("是否订购:全部");
		if (ciVo.getIsOrder() != null && !"".equals(ciVo.getIsOrder().trim())) {
			cell27.setCellValue("是否订购:"
					+ (ciVo.getIsOrder().equals("1") ? "是" : "否"));
		}
		// 是否跟踪
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 9));
		HSSFCell cell28 = row2.createCell(8);
		cell28.setCellValue("是否跟踪:全部");
		if (ciVo.getIsTrack() != null && !"".equals(ciVo.getIsTrack().trim())) {
			cell28.setCellValue("是否跟踪:"
					+ (ciVo.getIsTrack().equals("1") ? "是" : "否"));
		}
		//是否新用户
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 11));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
		HSSFCell cell210 = row2.createCell(10);
		cell210.setCellValue("是否新用户:全部");
		if (ciVo.getIsNewUser() != null && !"".equals(ciVo.getIsNewUser().trim())) {
			cell210.setCellValue("是否新用户:"
					+ (ciVo.getIsNewUser().equals("1") ? "是" : "否"));
		}
		
		// 创建表头
		HSSFRow row4 = sheet.createRow(4);
		int k = 0;
		if(this.controlShow("ROLE_TEL_SHOW")){
			HSSFCell cell40 = row4.createCell(k);
			cell40.setCellStyle(cell_Style);
			cell40.setCellValue("手机号");
		}else{
			HSSFCell cell41 = row4.createCell(k);
			cell41.setCellStyle(cell_Style);
			cell41.setCellValue("客服姓名");
		}

		HSSFCell cell42 = row4.createCell(k+1);
		cell42.setCellStyle(cell_Style);
		cell42.setCellValue("沟通类型");

		HSSFCell cell43 = row4.createCell(k+2);
		cell43.setCellStyle(cell_Style);
		cell43.setCellValue("客户编号");

		HSSFCell cell44 = row4.createCell(k+3);
		cell44.setCellStyle(cell_Style);
		cell44.setCellValue("客户来源");
		
		HSSFCell cell45 = row4.createCell(k+4);
		cell45.setCellStyle(cell_Style);
		cell45.setCellValue("是否新用户");
		
		HSSFCell cell455 = row4.createCell(k+5);
		cell455.setCellStyle(cell_Style);
		cell455.setCellValue("产品编码");

		HSSFCell cell46 = row4.createCell(k+6);
		cell46.setCellStyle(cell_Style);
		cell46.setCellValue("咨询日期");

		HSSFCell cell477 = row4.createCell(k+7);
		cell477.setCellStyle(cell_Style);
		cell477.setCellValue("最近操作日期");

		HSSFCell cell47 = row4.createCell(k+8);
		cell47.setCellStyle(cell_Style);
		cell47.setCellValue("是否订购");

		HSSFCell cell48 = row4.createCell(k+9);
		cell48.setCellStyle(cell_Style);
		cell48.setCellValue("成单日期");

		HSSFCell cell49 = row4.createCell(k+10);
		cell49.setCellStyle(cell_Style);
		cell49.setCellValue("是否成单");

		HSSFCell cell410 = row4.createCell(k+11);
		cell410.setCellStyle(cell_Style);
		cell410.setCellValue("产品关键词");

		HSSFCell cell411 = row4.createCell(k+12);
		cell411.setCellStyle(cell_Style);
		cell411.setCellValue("科组类别");

		HSSFCell cell412 = row4.createCell(k+13);
		cell412.setCellStyle(cell_Style);
		cell412.setCellValue("病种类别");

		HSSFCell cell413 = row4.createCell(k+14);
		cell413.setCellStyle(cell_Style);
		cell413.setCellValue("订单数量");

		HSSFCell cell414 = row4.createCell(k+15);
		cell414.setCellStyle(cell_Style);
		cell414.setCellValue("订单金额");

		HSSFCell cell415 = row4.createCell(k+16);
		cell415.setCellStyle(cell_Style);
		cell415.setCellValue("是否跟踪");
		
		HSSFCell cell418 = row4.createCell(k+17);
		cell418.setCellStyle(cell_Style);
		cell418.setCellValue("今日跟踪");
		
		HSSFCell cell419 = row4.createCell(k+18);
		cell419.setCellStyle(cell_Style);
		cell419.setCellValue("跟踪信息");
		
		HSSFCell cell420 = row4.createCell(k+19);
		cell420.setCellStyle(cell_Style);
		cell420.setCellValue("未订购原因");

		int j=0;
		// 写入查询数据
		for (int i = 0; i < commuList.size(); i++) {
			HSSFRow perRow = sheet.createRow(i + 5);
			CommuInfoVo perCiv = commuList.get(i);
			// 手机号
			if(this.controlShow("ROLE_TEL_SHOW")){
				if (perCiv.getTel() != null) {
					perRow.createCell(j).setCellValue(perCiv.getTel());
				}
			}else{
				// 客服姓名
				if (perCiv.getName() != null) {
					perRow.createCell(j).setCellValue(perCiv.getName());
				}
			}
			// 沟通类型
			if (perCiv.getAcceptResult() != null) {
				perRow.createCell(j+1).setCellValue(perCiv.getAcceptResult());
			}
			// 客户编号
			if (perCiv.getCustNo() != null) {
				perRow.createCell(j+2).setCellValue(perCiv.getCustNo());
			}
			// 客户来源
			if (perCiv.getCustSource() != null) {
				String res = perCiv.getCustSource();
				if("ZX".equals(res)){
					perRow.createCell(j+3).setCellValue("在线");
				}else if("LY".equals(res)){
					perRow.createCell(j+3).setCellValue("留言");
				}else if("TEL_IN".equals(res) || "TEL_OUT".equals(res)){
					perRow.createCell(j+3).setCellValue("电话");
				}
			}
			//是否新用户
			if (perCiv.getIsNewUser() != null) {
				perRow.createCell(j+4).setCellValue(
						perCiv.getIsNewUser().equals("1") ? "是" : "否");
			}
			// 产品编码
			if (perCiv.getProSkus() != null) {
				perRow.createCell(j+5).setCellValue(
						perCiv.getProSkus().replace(",", ", "));
			}
			// 咨询日期
			if (perCiv.getConsultDate() != null) {
				perRow.createCell(j+6).setCellValue(perCiv.getCreateTime());
			}
			// 最近操作日期
			if (perCiv.getConsultDate() != null) {
				perRow.createCell(j+7).setCellValue(perCiv.getConsultDate());
			}
			// 是否订购
			if (perCiv.getIsOrder() != null) {
				perRow.createCell(j+8).setCellValue(
						perCiv.getIsOrder().equals("1") ? "是" : "否");
			}
			// 成单日期
			if (perCiv.getPlaceOrderDate() != null) {
				perRow.createCell(j+9).setCellValue(perCiv.getPlaceOrderDate());
			}
			// 是否成单
			if (perCiv.getIsPlaceOrder() != null) {
				perRow.createCell(j+10).setCellValue(
						perCiv.getIsPlaceOrder().equals("1") ? "是" : "否");
			}
			// 产品关键词
			if (perCiv.getProKeywords() != null) {
				perRow.createCell(j+11).setCellValue(
						perCiv.getProKeywords().replace("{;}", "")
								.replace("{,}", "; "));
			}
			// 科组类别
			if (perCiv.getDepCategory() != null) {
				perRow.createCell(j+12).setCellValue(perCiv.getDepCategory());
			}
			// 病种类别
			if (perCiv.getDiseaseCategory() != null) {
				perRow.createCell(j+13).setCellValue(perCiv.getDiseaseCategory());
			}
			// 订单数量
			if (perCiv.getOrderQuantity() != null) {
				perRow.createCell(j+14).setCellValue(perCiv.getOrderQuantity());
			}
			// 订单金额
			if (perCiv.getOrderTotalPrice() != null) {
				perRow.createCell(j+15).setCellValue(perCiv.getOrderTotalPrice());
			}
			// 是否跟踪
			if (perCiv.getIsTrack() != null) {
				perRow.createCell(j+16).setCellValue(
						perCiv.getIsTrack().equals("1") ? "是" : "否");
			}
			// 今日跟踪
			if (perCiv.getTodayTrackValue() != null) {
				perRow.createCell(j+17).setCellValue(
						perCiv.getTodayTrackValue().equals("1") ? "已跟踪" : "未跟踪");
			}
			// 跟踪信息
			if (perCiv.getTrackInfo() != null) {
				perRow.createCell(j+18).setCellValue(perCiv.getTrackInfo());
			}
			// 未订购原因
			if (perCiv.getNoOrderCause() != null) {
				perRow.createCell(j+19).setCellValue(perCiv.getNoOrderCause());
			}

		}
		return workbook2003;
	}
	
	public boolean controlShow(String conditons){
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				Collection<? extends GrantedAuthority> list = ((UserDetails) principal).getAuthorities();
				Object[] object = list!=null?list.toArray():null;
				for(int i=0;object!=null && object.length>0 && i<object.length;i++){
					object[i]=object[i].toString().trim();
				}
				if(conditons!=null && conditons.length()>0 && Arrays.asList(object).contains(conditons)){
					return true;
				}
			}
		}
		return false;
	}

}
