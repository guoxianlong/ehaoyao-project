/**
 * 
 */
package com.ehaoyao.opertioncenter.member.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.MemberHealthKeyword;

/**
 * 会员健康关键字关系表Dao
 * @author kxr
 *
 */
public interface MemberHealthKeywordDao {
	
	/**
	 * 查询会员健康关键字关系表
	 */
	public List<MemberHealthKeyword> getMemberHealthKeyword();
	
	/**
	 * 添加会员健康关键字关系表
	 * @param memberHealth
	 */
	public void saveMemberHealthKeyword(MemberHealthKeyword memberHealth);
	
	/**
	 * 修改会员健康关键字关系表
	 * @param memberHealth
	 */
	public void updateMemberHealthKeyword(MemberHealthKeyword memberHealth);
	
	/**
	 * 根据id删除会员健康关键字关系表
	 * @param 主键ID
	 * @param MemberHealthKeyword
	 */
	public void delMemberHealthKeyword(String tel);

}
