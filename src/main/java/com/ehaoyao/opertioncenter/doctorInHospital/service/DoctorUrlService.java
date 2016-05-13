package com.ehaoyao.opertioncenter.doctorInHospital.service;

import java.util.List;
import java.util.Map;

import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorUrlModel;

public interface DoctorUrlService {
	public void save(DoctorUrlModel doctorUrlModel);
	
	public List<DoctorUrlModel> queryAll();
	
	public DoctorUrlModel get(Integer id);
	
	public List<DoctorUrlModel> queryHql(String hql,Map params,int startNum,int pageSize);
	
	public List<DoctorUrlModel> queryHql(String hql,Map params);
	
	public  void update(DoctorUrlModel doctorUrlModel);
}
