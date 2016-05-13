package com.ehaoyao.opertioncenter.custServiceCenter.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;

/**
 * 预约
 * @author Administrator
 *
 */
public interface ReservationService {
	
	/**
	 * 查询今日任务
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<ReservationVO> getReservaList(int firstResult, int pageSize, ReservationVO cvo);
	/**
	 * 新增预约时判断是否已经存在预约
	 * @param cvo
	 * @return
	 */
	public Reservation getReservaInfo(ReservationVO cvo);
	/**
	 * 
	 * @param cvo
	 * @return
	 */
	public void savaReservat(Reservation cvo);
	/**
	 * 查询今日任务总条数
	 */
	public int getReservaCount(ReservationVO cvo);
	/**
	 * 修改今日任务完成状态
	 */
	public void complete(int id);
	
	/**
	 * 统计今日预约任务
	 */
	public List<Map<String,Integer>> countTodayReserva(ReservationVO vo);
	/**
	 * 
	 * <p>Title: writeToExcel</p>
	 * <p>Description:导出今日任务报表 </p>
	 * @param request
	 * @param workbook2003
	 */
	public void writeToExcel(HttpServletRequest request,
			HSSFWorkbook workbook2003);
}
