package com.ehaoyao.opertioncenter.custServiceCenter.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;
import com.haoyao.goods.dao.BaseDao;

/**
 * 预约
 * @author Administrator
 *
 */
public interface ReservationDao extends BaseDao<Reservation, Long>{

	/**
	 * 
	 * 查询今日任务
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<ReservationVO> getReservaList(int firstResult, Integer pageSize, ReservationVO cvo);
	/**
	 * 新增预约回访时间
	 * @param cvo
	 * @return
	 */
	public Object savaReservat(Reservation cvo);
	/**
	 * 批量插入预约回访信息
	 * @param list
	 */
	public void saveReservatBatch(List<Reservation> list);
	/**
	 * 新增预约回访时间时判断是否已经存在预约
	 * @param cvo
	 * @return
	 */
	public Reservation getReservaInfo(ReservationVO cvo);
	
	public int getReservaCount(ReservationVO cvo);
	/**
	 * 修改今日任务完成状态
	 */
	public void complete(int id);
	
	/**
	 * 统计预约回访今日任务
	 */
	public int countTodayReserva(ReservationVO vo);
	/**
	 * <p>Title: writeToExcel</p>
	 * <p>Description: 导出今日任务报表</p>
	 * @param request
	 * @param workbook2003
	 */
	public void writeToExcel(HttpServletRequest request,
			HSSFWorkbook workbook2003);
}
