package com.ehaoyao.opertioncenter.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.member.dao.MemberHealthKeywordDao;
import com.ehaoyao.opertioncenter.member.model.MemberHealthKeyword;
import com.ehaoyao.opertioncenter.member.service.MemberHealthKeywordService;

/**
 * 会员健康关键字关系表Service实现类
 * @author kxr
 *
 */
@Service
public class MemberHealthKeywordServiceImpl implements MemberHealthKeywordService {
	
	@Autowired
	private MemberHealthKeywordDao memberHealthKeywordDao;

	/**
	 * @param memberHealthKeywordDao the memberHealthKeywordDao to set
	 */
	public void setMemberHealthKeywordDao(MemberHealthKeywordDao memberHealthKeywordDao) {
		this.memberHealthKeywordDao = memberHealthKeywordDao;
	}

	/**
	 * 查询会员健康关键字关系表
	 * @return List<MemberHealthKeyword>
	 */
	@Override
	public List<MemberHealthKeyword> getMemberHealthKeyword() {
		return memberHealthKeywordDao.getMemberHealthKeyword();
	}

	/**
	 * 添加会员健康关键字关系表
	 * @param MemberHealthKeyword
	 * @return int
	 */
	@Override
	public void saveMemberHealthKeyword(MemberHealthKeyword memberHealth) {
		memberHealthKeywordDao.saveMemberHealthKeyword(memberHealth);
	}

	/**
	 * 修改会员健康关键字关系表
	 * @param memberHealth
	 */
	@Override
	public void updateMemberHealthKeyword(MemberHealthKeyword memberHealth) {
		memberHealthKeywordDao.updateMemberHealthKeyword(memberHealth);
	}

	/**
	 * 根据id删除会员健康关键字关系表
	 * @param memberHealth,id
	 */
	@Override
	public void delMemberHealthKeyword(String tel) {
		memberHealthKeywordDao.delMemberHealthKeyword(tel);
	}

}
