package com.ehaoyao.opertioncenter.doctorInHospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.doctorInHospital.dao.SalesRepDao;
import com.ehaoyao.opertioncenter.doctorInHospital.model.SalesRepModel;
import com.ehaoyao.opertioncenter.doctorInHospital.service.SalesRepService;

@Service
public class SalesRepServiceImpl implements SalesRepService {
	@Autowired
	SalesRepDao salesRepDao;
	
	public void setSalesRepDao(SalesRepDao salesRepDao) {
		this.salesRepDao = salesRepDao;
	}

	@Override
	public void save(SalesRepModel salesRepModel) {
		salesRepDao.save(salesRepModel);
	}

	@Override
	public List<SalesRepModel> queryAll() {
		return salesRepDao.getAllList();
	}

	@Override
	public SalesRepModel get(Integer id) {
		return salesRepDao.get(id);
	}

	@Override
	public List<SalesRepModel> queryHql(String hql,Map params,int startNum,int pageSize) {
		return salesRepDao.queryByHQL(hql, params, startNum, pageSize);
	}
	@Override
	public void update(SalesRepModel salesRepModel) {
		salesRepDao.update(salesRepModel);
	}

	@Override
	public List<SalesRepModel> queryHql(String hql,Map params) {
		return salesRepDao.queryByHQL(hql, params);
	}
}
