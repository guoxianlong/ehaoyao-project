package com.ehaoyao.opertioncenter.doctorInHospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.doctorInHospital.dao.HosDoctorUrlDao;
import com.ehaoyao.opertioncenter.doctorInHospital.service.HosDoctorUrlService;
import com.ehaoyao.opertioncenter.doctorInHospital.vo.HosDoctorUrlVO;

/**
 * 医院，医生，url关系查询
 * @author zhang
 *
 */
@Service
public class HosDoctorUrlServiceImpl implements HosDoctorUrlService {
	@Autowired
	HosDoctorUrlDao hosDoctorUrlDao;
	/**
	 * 根据urlID获取医院和医生ID
	 */
	@Override
	public HosDoctorUrlVO getHosDoctorUrlId(Integer id) {
		return hosDoctorUrlDao.getHosDoctorUrlId(id);
	}
}
