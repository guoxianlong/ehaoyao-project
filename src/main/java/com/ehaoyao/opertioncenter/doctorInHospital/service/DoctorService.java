package com.ehaoyao.opertioncenter.doctorInHospital.service;

import java.util.List;
import java.util.Map;

import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorModel;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorUrlModel;

public interface DoctorService {
	public void save(DoctorModel doctorModel);
	
	public List<DoctorModel> queryAll();
	
	public DoctorModel get(Integer id);
	
	public List<DoctorModel> queryHql(String hql,Map params,int startNum,int pageSize);
	
	public List<DoctorModel> queryHql(String hql,Map params);
	public  void update(DoctorModel doctorModel);

}
