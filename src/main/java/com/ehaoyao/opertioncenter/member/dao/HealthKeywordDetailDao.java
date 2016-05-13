package com.ehaoyao.opertioncenter.member.dao;

import java.util.List;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;

/**
 * 健康关键字Dao接口类
 * @author kxr
 *
 */
public interface HealthKeywordDetailDao {
	
	/**
	 * 根据分类查询健康关键字
	 * @param pid
	 * @return
	 */
	public List<HealthKeywordDetail> getHealthKeywordDetailByPid(Integer pid);
	
	/**
	 * 根据ID查询健康关键字
	 * @param tel
	 */
	public List<HealthKeywordDetail> getMemberHealthKeywordByTel(String id);
	
	/**
	 * 添加健康关键字
	 * @param healthKeywordDetail
	 * @return
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
