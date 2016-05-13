package com.ehaoyao.opertioncenter.doctorInHospital.dao;

import com.ehaoyao.opertioncenter.doctorInHospital.vo.HosDoctorUrlVO;
import com.haoyao.goods.dao.BaseDao;

public interface HosDoctorUrlDao extends BaseDao<HosDoctorUrlVO, Integer> {
	
	public HosDoctorUrlVO getHosDoctorUrlId(int id);
}
