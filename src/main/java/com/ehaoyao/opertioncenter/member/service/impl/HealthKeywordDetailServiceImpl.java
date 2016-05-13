package com.ehaoyao.opertioncenter.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.member.dao.HealthKeywordDetailDao;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordDetailService;

/**
 * 健康关键字Service实现类
 * @author kxr
 *
 */
@Service
public class HealthKeywordDetailServiceImpl implements HealthKeywordDetailService {

	@Autowired
	private HealthKeywordDetailDao healthKeywordDetailDao;
	
	/**
	 * @param healthKeywordDetailDao the healthKeywordDetailDao to set
	 */
	public void setHealthKeywordDetailDao(HealthKeywordDetailDao healthKeywordDetailDao) {
		this.healthKeywordDetailDao = healthKeywordDetailDao;
	}

	/**
	 * 根据分类查询健康关键字
	 * @param pid
	 * @return
	 */
	public List<HealthKeywordDetail> getHealthKeywordDetailByPid(Integer pid) {
		return healthKeywordDetailDao.getHealthKeywordDetailByPid(pid);
	}
	
	/**
	 * 根据电话号码查询健康关键字
	 * @param tel
	 * @return List<MemberHealthKeyword>
	 */
	public List<HealthKeywordDetail> getMemberHealthKeywordByTel(String tel) {
		return healthKeywordDetailDao.getMemberHealthKeywordByTel(tel);		
	}

	/**
	 * 添加健康关键字
	 * @param healthKeywordDetail
	 * @return
	 */
	@Override
	public Object saveHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail) {
		return healthKeywordDetailDao.saveHealthKeywordDetail(healthKeywordDetail);
	}

	/**
	 * 修改健康关键字
	 * @param healthKeywordDetail
	 */
	@Override
	public void updateHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail) {
		healthKeywordDetailDao.updateHealthKeywordDetail(healthKeywordDetail);
	}

	/**
	 * 删除健康关键字
	 * @param id
	 */
	@Override
	public void delHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail) {
		healthKeywordDetailDao.delHealthKeywordDetail(healthKeywordDetail);
	}

}
