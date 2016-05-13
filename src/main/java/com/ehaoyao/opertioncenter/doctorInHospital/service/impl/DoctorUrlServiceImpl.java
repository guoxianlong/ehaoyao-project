package com.ehaoyao.opertioncenter.doctorInHospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.doctorInHospital.dao.DoctorUrlDao;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorUrlModel;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorUrlService;
@Service
public class DoctorUrlServiceImpl implements DoctorUrlService {
	@Autowired 
	DoctorUrlDao doctorUrlDao;
	
	public void save(DoctorUrlModel doctorDataUrlModel) {
		doctorUrlDao.save(doctorDataUrlModel);
	}

	public List<DoctorUrlModel> queryAll() {
		return doctorUrlDao.getAllList();
	}

	public DoctorUrlModel get(Integer id) {
		return doctorUrlDao.get(id);
	}

	public List<DoctorUrlModel> queryHql(String hql, Map params, int startNum,
			int pageSize) {
		return doctorUrlDao.queryByHQL(hql, params, startNum, pageSize);
	}

	
	public void update(DoctorUrlModel doctorUrlModel) {
		doctorUrlDao.update(doctorUrlModel);
		
	}

	public List<DoctorUrlModel> queryHql(String hql, Map params) {
		// TODO Auto-generated method stub
		return doctorUrlDao.queryByHQL(hql, params);
	}

}
