package com.ehaoyao.opertioncenter.custServiceCenter.dao.impl;

import java.sql.SQLException;
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
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.custServiceCenter.dao.ReservationDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 今日任务
 * @author Administrator
 *
 */
@Repository
public class ReservationDaoImpl extends BaseDaoImpl<Reservation, Long> implements ReservationDao {

	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	/**
	 * 今日任务
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return
	 */
	@Override
	public List<ReservationVO> getReservaList(int firstResult, Integer pageSize,ReservationVO vo) {
		/*String sql = "select a.*,u.`name` custServiceNm from reservation AS a LEFT JOIN user u on a.cust_service_no = u.userName " +
					"where not exists(select 1 from reservation where tel = a.tel and reserve_time > a.reserve_time) " +
					"and IFNULL(a.`status`,'0')='0' AND reserve_time <= curdate()";*/
		String sql = "select a.*,u.`name` custServiceNm,m.member_name custName from reservation AS a " +
					"LEFT JOIN user u on a.cust_service_no = u.userName LEFT JOIN member m ON a.tel = m.tel " +
				"where reserve_time <= curdate()";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql += " ORDER BY a.tel,a.reserve_time";
        sql += " limit "+firstResult+","+pageSize;
        List<ReservationVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<ReservationVO>(ReservationVO.class));
		return ls;
	}
	//获取查询条件
	private String getWhereSql(ReservationVO vo,List<Object> paramList){
		StringBuffer wsql = new StringBuffer();
		if(vo!=null){
			if(vo.getCustName()!=null && vo.getCustName().trim().length()>0){
				wsql.append(" and m.member_name = ? ");
				paramList.add(vo.getCustName().trim());
			}
			if(vo.getTel()!=null && vo.getTel().trim().length()>0){
				wsql.append(" and a.tel = ? ");
				paramList.add(vo.getTel().trim());
			}
			if(vo.getCustServiceNo()!=null && vo.getCustServiceNo().trim().length()>0){
				wsql.append(" and a.cust_service_no = ? ");
				paramList.add(vo.getCustServiceNo().trim());
			}
			if(vo.getCustServiceNm()!=null && vo.getCustServiceNm().trim().length()>0){
				wsql.append(" and u.name = ? ");
				paramList.add(vo.getCustServiceNm().trim());
			}
			if(vo.getStatus()!=null && "1".equals(vo.getStatus().trim())){
				wsql.append(" and a.`status` = '1' ");
			}else{
				wsql.append(" and IFNULL(a.`status`,'0')='0' ");
			}
			if(vo.getComment()!=null && vo.getComment().trim().length()>0){
				wsql.append(" and a.comment like ? ");
				paramList.add("%"+vo.getComment().trim()+"%");
			}
		}
		return wsql.toString();
	}
	/**
	 * 保存预约时间
	 */
	public Object savaReservat(final Reservation cvo) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				session.save(cvo);
				session.flush();
				return null;
			}
		});
	}
	@Override
	public Reservation getReservaInfo(ReservationVO vo) {
		String sql = "select MAX(reserve_time)reserve_time from reservation a where IFNULL(`status`,'0')='0' AND reserve_time >= curdate()";
		List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        List<Reservation> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<Reservation>(Reservation.class));
        Reservation rt = null;
        if(ls != null && ls.size() > 0){
        	rt = ls.get(0);
        }
		return rt;
	}
	/**
	 * 查询总条数
	 */
	public int getReservaCount(ReservationVO vo) {
		/*String sql = "select count(*) from reservation AS a LEFT JOIN user u on a.cust_service_no = u.userName " +
				"where not exists(select 1 from reservation where tel = a.tel and reserve_time > a.reserve_time) " +
				"and IFNULL(a.`status`,'0')='0' AND reserve_time <= curdate()";*/
		String sql = "select count(*) from reservation AS a " +
				"LEFT JOIN user u on a.cust_service_no = u.userName LEFT JOIN member m ON a.tel = m.tel " +
			"where reserve_time <= curdate()";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	/**
	 * 修改今日任务完成状态
	 */
	public void complete(int id){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = "update "+Reservation.class.getName()+" set status='1',completeTime='" + df.format(new Date())+ "' where id=:id";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	/**
	 * 统计预约回访今日任务
	 */
	public int countTodayReserva(ReservationVO vo){
		StringBuffer sqlBuf = new StringBuffer("select count(*) from reservation AS a where reserve_time <= curdate() ");
		List<Object> params = new ArrayList<Object>();
		if(vo!=null){
			//客服工号
			if(vo.getCustServiceNo()!=null && vo.getCustServiceNo().trim().length()>0){
				sqlBuf.append(" AND a.cust_service_no=? ");
				params.add(vo.getCustServiceNo().trim());
			}
			//状态
			if(vo.getStatus()!=null && vo.getStatus().trim().length()>0){
				//当天完成
				if("1".equals(vo.getStatus().trim())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					sqlBuf.append(" AND a.status = '1' AND complete_time >= '"+df.format(new Date())+"' AND complete_time <='"+df.format(new Date())+" 23:59:59'");
				}else{//今日任务未完成
					sqlBuf.append(" AND IFNULL(a.status,'0') != '1' ");
				}
			}
		}
		Integer count = jdbcTemplate.queryForObject(sqlBuf.toString(), params.toArray(),Integer.class);
		return count;
	}
/*	@Override
	public void delete(Reservation entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Reservation save(Reservation entity) {
		// TODO Auto-generated method stub
		return null;
	}*/
	/**
	 * <p>Title: writeToExcel</p>
	 * <p>Description:导出今日任务报表 </p>
	 * @param request
	 * @param workbook2003
	 */
	@Override
	public void writeToExcel(HttpServletRequest request,
			HSSFWorkbook workbook2003) {
		//从session中取出查询条件
		ReservationVO vo = (ReservationVO) request.getSession().getAttribute("cvo");
		List<ReservationVO> reservaList = getReservaList(0, getReservaCount(vo), vo);
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("今日任务");
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
		for (int i = 0; i <= 10; i++) {
			sheet.setDefaultColumnStyle(i, commonStyle);
			sheet.setColumnWidth(i, 4750);
		}
		// 设置特定列宽
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 3000);
		sheet.setColumnWidth(4, 5000);
		// 今日任务标题
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeightInPoints(30f);
		HSSFCell cell00 = row0.createCell(0);
		cell00.setCellStyle(headerStyle);
		cell00.setCellValue("今日任务");
		
		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 10));
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell10 = row1.createCell(0);
		cell10.setCellStyle(conditionStyle);
		StringBuffer sb=new StringBuffer();
		// 客户名称
		if (vo.getCustName() != null && !"".equals(vo.getCustName().trim())) {
			sb.append("客户名称:"+vo.getCustName()+"    ");
		} else {
			sb.append("客户名称:"+"    ");
		}
		//手机
		if (vo.getTel() != null && !"".equals(vo.getTel().trim())) {
			sb.append("手机:"+vo.getTel()+"    ");
		} else {
			sb.append("手机:"+"    ");
		}
		//客服工号
		if (vo.getCustServiceNo() != null && !"".equals(vo.getCustServiceNo().trim())) {
			sb.append("客服工号:"+vo.getCustServiceNo()+"    ");
		} else {
			sb.append("客服工号:"+"    ");
		}
		//健康顾问
		if (vo.getCustServiceNm() != null && !"".equals(vo.getCustServiceNm().trim())) {
			sb.append("健康顾问:"+vo.getCustServiceNm()+"    ");
		} else {
			sb.append("健康顾问:"+"    ");
		}
		cell10.setCellValue(sb.toString());
		
		// 创建表头
		HSSFRow row4 = sheet.createRow(4);
		HSSFCell cell40 = row4.createCell(0);
		cell40.setCellStyle(cell_Style);
		cell40.setCellValue("序号");

		HSSFCell cell41 = row4.createCell(1);
		cell41.setCellStyle(cell_Style);
		cell41.setCellValue("操作");

		HSSFCell cell42 = row4.createCell(2);
		cell42.setCellStyle(cell_Style);
		cell42.setCellValue("沟通类型");

		HSSFCell cell43 = row4.createCell(3);
		cell43.setCellStyle(cell_Style);
		cell43.setCellValue("客户名称");
		
		HSSFCell cell44 = row4.createCell(4);
		cell44.setCellStyle(cell_Style);
		cell44.setCellValue("产品关键词");
		
		HSSFCell cell45 = row4.createCell(5);
		cell45.setCellStyle(cell_Style);
		cell45.setCellValue("客户来源");

		HSSFCell cell46 = row4.createCell(6);
		cell46.setCellStyle(cell_Style);
		cell46.setCellValue("预约回访日期");

		HSSFCell cell47 = row4.createCell(7);
		cell47.setCellStyle(cell_Style);
		cell47.setCellValue("上次沟通日期");

		HSSFCell cell48 = row4.createCell(8);
		cell48.setCellStyle(cell_Style);
		cell48.setCellValue("客服工号");
		
		HSSFCell cell49 = row4.createCell(9);
		cell49.setCellStyle(cell_Style);
		cell49.setCellValue("健康顾问");
		
		HSSFCell cell410 = row4.createCell(10);
		cell410.setCellStyle(cell_Style);
		cell410.setCellValue("说明");
		
		//写入查询数据
		for(int i=0;i<reservaList.size();i++){
			ReservationVO perVo = reservaList.get(i);
			HSSFRow perRow = sheet.createRow(i + 5);
			//序号
			if (perVo != null) {
				HSSFCell createCell = perRow.createCell(0);
				createCell.setCellStyle(conditionStyle);
				createCell.setCellValue(i+1);
			}
			//操作
			if (perVo != null) {
				HSSFCell createCell = perRow.createCell(1);
				createCell.setCellValue("待完成");
			}
			//沟通类型
			if (perVo.getAcceptResult() != null) {
				HSSFCell createCell = perRow.createCell(2);
				createCell.setCellValue(perVo.getAcceptResult());
			}
			//客户名称
			if (perVo.getCustName() != null) {
				HSSFCell createCell = perRow.createCell(3);
				createCell.setCellValue(perVo.getCustName());
			}
			//产品关键词
			if (perVo.getProKeywords() != null) {
				HSSFCell createCell = perRow.createCell(4);
				createCell.setCellValue(perVo.getProKeywords().replace("{;}", "").replace("{,}", "; "));
			}
			//客户来源
			if (perVo.getCustSource() != null) {
				HSSFCell createCell = perRow.createCell(5);
				String cs="";
				if("ZX".equals(perVo.getCustSource())){
					cs="在线客服";
				}else if("XQ".equals(perVo.getCustSource())){
					cs="需求登记";
				}else if("TEL_IN".equals(perVo.getCustSource())){
					cs="呼入电话";
				}else if("TEL_OUT".equals(perVo.getCustSource())){
					cs="老客维护";
				};
				createCell.setCellValue(cs);
			}
			//预约回访日期
			if (perVo.getReserveTime() != null) {
				HSSFCell createCell = perRow.createCell(6);
				createCell.setCellValue(perVo.getReserveTime());
			}
			//上次沟通日期
			if (perVo.getLastTime() != null) {
				HSSFCell createCell = perRow.createCell(7);
				createCell.setCellValue(perVo.getLastTime());
			}
			//客服工号
			if (perVo.getCustServiceNo() != null) {
				HSSFCell createCell = perRow.createCell(8);
				createCell.setCellValue(perVo.getCustServiceNo());
			}
			//健康顾问
			if (perVo.getCustServiceNm() != null) {
				HSSFCell createCell = perRow.createCell(9);
				createCell.setCellValue(perVo.getCustServiceNm());
			}
			//说明
			if (perVo.getComment() != null) {
				HSSFCell createCell = perRow.createCell(10);
				createCell.setCellValue(perVo.getComment());
			}
			
		}
	}
	/**
	 * 批量保存今日任务
	 */
	@Override
	public void saveReservatBatch(List<Reservation> list) {
		Session session = null;  
		Transaction tx = null;
		long start = System.currentTimeMillis();
        if (list != null && list.size() > 0) {  
           try {  
               session = this.getHibernateTemplate().getSessionFactory().openSession();
               tx = session.beginTransaction();
               session.setCacheMode(CacheMode.IGNORE);
               for(int j=0;j<=list.size()-1;j++){
            	   session.save(list.get(j));
                   if (j % 2000 == 0
                           || list.size() - 1 == j) {  
                       session.flush();  
                       session.clear(); 
                   }  
               }
               long end = System.currentTimeMillis();
   			   System.out.println("保存今日任务所需时间" + (end - start));
   			   tx.commit(); // 提交事务 
           } catch (Exception e) {  
               e.printStackTrace();
               tx.rollback(); // 出错将回滚事务 
           } finally {  
               session.close(); // 关闭Session  
           }  
       } 
	}
}
