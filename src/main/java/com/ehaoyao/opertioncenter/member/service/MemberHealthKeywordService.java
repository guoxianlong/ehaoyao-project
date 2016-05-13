/**
 * 
 */
package com.ehaoyao.opertioncenter.member.service;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.MemberHealthKeyword;

/**
 * 会员健康关键字关系表Service接口类
 * @author kxr
 *
 */
public interface MemberHealthKeywordService {
	
	/**
	 * 查询会员健康关键字关系表
	 * @return List<MemberHealthKeyword>
	 */
	public List<MemberHealthKeyword> getMemberHealthKeyword();
	
	/**
	 * 添加会员健康关键字关系表
	 * @param List<MemberHealthKeyword>
	 * @return int
	 */
	public void saveMemberHealthKeyword(MemberHealthKeyword memberHealth);
	
	/**
	 * 修改会员健康关键字关系表
	 * @param memberHealth
	 */
	public void updateMemberHealthKeyword(MemberHealthKeyword memberHealth);
	
	/**
	 * 根据id删除会员健康关键字关系表
	 * @param memberHealth,id
	 */
	public void delMemberHealthKeyword(String tel);

}
