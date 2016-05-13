package com.ehaoyao.opertioncenter.custServiceCenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.custServiceCenter.dao.ReservationDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.service.ReservationService;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;

/**
 * 
 * @author Administrator
 * 预约
 */
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDao reservatDao;
	
	/**
	 * 查询今日任务
	 */
	@Override
	public List<ReservationVO> getReservaList(int firstResult, int pageSize,ReservationVO cvo) {
		return reservatDao.getReservaList(firstResult, pageSize, cvo);
	}
	/**
	 * 新增预约时间
	 */
	@Override
	public void savaReservat(Reservation cvo) {
		reservatDao.savaReservat(cvo);
	}
	/**
	 * 查询是否有未完成的预约
	 */
	@Override
	public Reservation getReservaInfo(ReservationVO cvo) {
		return reservatDao.getReservaInfo(cvo);
	}
	@Override
	public int getReservaCount(ReservationVO cvo) {
		// TODO Auto-generated method stub
		return reservatDao.getReservaCount(cvo);
	}
	@Override
	public void complete(int id) {
		reservatDao.complete(id);
	}
	
	/**
	 * 统计今日预约任务
	 */
	public List<Map<String,Integer>> countTodayReserva(ReservationVO vo){
		List<Map<String,Integer>> ls = new ArrayList<Map<String,Integer>>();
		String custServiceNo = vo.getCustServiceNo();
		int count = 0;
		int total = 0;
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		/* ***统计客服部门任务（所有） ***/
		vo.setCustServiceNo(null);
		//已完成
		vo.setStatus("1");
		count = reservatDao.countTodayReserva(vo);
		total += count;
		map.put("completeCount", count);
		
		//未完成
		vo.setStatus("0");
		count = reservatDao.countTodayReserva(vo);
		total += count;
		map.put("uncompleteCount", count);
		
		//总计
		map.put("count", total);
		ls.add(map);
		
		vo.setCustServiceNo(custServiceNo);
		if(custServiceNo!=null && custServiceNo.trim().length()>0){
			map = new HashMap<String,Integer>();
			total = 0;
			/* ***统计客服个人任务 ***/
			//已完成
			vo.setStatus("1");
			count = reservatDao.countTodayReserva(vo);
			total += count;
			map.put("completeCount", count);
			
			//未完成
			vo.setStatus("0");
			count = reservatDao.countTodayReserva(vo);
			total += count;
			map.put("uncompleteCount", count);
			
			//总计
			map.put("count", total);
			ls.add(map);
		}
		return ls;
	}
	/**
	 * <p>Title: writeToExcel</p>
	 * <p>Description: 导出今日任务报表</p>
	 * @param request
	 * @param workbook2003
	 */
	@Override
	public void writeToExcel(HttpServletRequest request,
			HSSFWorkbook workbook2003) {
		reservatDao.writeToExcel(request,workbook2003);
	}

}
