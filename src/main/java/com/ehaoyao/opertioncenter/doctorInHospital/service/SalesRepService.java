package com.ehaoyao.opertioncenter.doctorInHospital.service;

import java.util.List;
import java.util.Map;

import com.ehaoyao.opertioncenter.doctorInHospital.model.SalesRepModel;

public interface SalesRepService {
		public void save(SalesRepModel salesRepModel);
		
		public List<SalesRepModel> queryAll();
		
		public SalesRepModel get(Integer id);
		
		public List<SalesRepModel> queryHql(String hql,Map params,int startNum,int pageSize);
		
		public  void update(SalesRepModel salesRepModel);
		
		public List<SalesRepModel> queryHql(String hql,Map params);

}
