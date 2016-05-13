/**
 * 
 */
package com.ehaoyao.opertioncenter.member.service;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;

/**
 * 健康关键字Service接口类
 * @author kxr
 *
 */
public interface HealthKeywordDetailService {
	
	/**
	 * 根据分类查询健康关键字
	 * @param pid
	 */
	public List<HealthKeywordDetail> getHealthKeywordDetailByPid(Integer pid);
	
	/**
	 * 根据电话号码查询健康关键字
	 * @param tel
	 */
	public List<HealthKeywordDetail> getMemberHealthKeywordByTel(String tel);
	
	/**
	 * 添加健康关键字
	 * @param healthKeywordDetail
	 */
	public Object saveHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail);
	
	/**
	 * 修改健康关键字
	 * @param healthKeywordDetail
	 */
	public void updateHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail);
	
	/**
	 * 删除健康关键字
	 * @param id
	 */
	public void delHealthKeywordDetail(HealthKeywordDetail healthKeywordDetail);

}
