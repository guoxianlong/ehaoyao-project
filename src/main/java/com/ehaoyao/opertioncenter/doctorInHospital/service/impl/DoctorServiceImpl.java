package com.ehaoyao.opertioncenter.doctorInHospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.doctorInHospital.dao.DoctorDao;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorModel;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	 @Autowired
	 DoctorDao doctorDao;

	public void save(DoctorModel doctorModel) {
		doctorDao.save(doctorModel);
	}

	public List<DoctorModel> queryAll() {
		return doctorDao.getAllList();
	}

	public DoctorModel get(Integer id) {
		return doctorDao.get(id);
	}

	public List<DoctorModel> queryHql(String hql, Map params, int startNum,
			int pageSize) {
		return doctorDao.queryByHQL(hql, params, startNum, pageSize);
	}
	
	public List<DoctorModel> queryHql(String hql,Map params){
		return doctorDao.queryByHQL(hql, params);
	}

	public void update(DoctorModel doctorModel) {
		doctorDao.update(doctorModel);
	}
}
