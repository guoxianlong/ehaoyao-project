package com.ehaoyao.opertioncenter.member.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.model.MemberTransit;

/**
 * @author Administrator
 * 会员信息
 */
public interface MemberDao {
	
	/**
	 * 会员查询
	 * @param firstResult 从第几条数据开始查找
	 * @param pageSize 当前第几页
	 * @param hqlString
	 * @return
	 */
	public List<Member> queryMemberList(int firstResult, Integer pageSize, String hqlString);
	/**
	 * 获得会员总条数
	 * @param hqlString
	 * @return
	 */
	public int getMemberCount(String hqlString);
	/**
	 * 按联系方式查询会员明细信息
	 * @param tel
	 * @return
	 */
	public Member queryMemberByTel(final String tel);
	
	/**
	 * 添加会员信息
	 * @param member
	 */
	public Object addMember(Member member);
	
	/**
	 * 修改会员信息
	 * @param hql
	 */
	public int updateMember(String hql);
	
	/**
	 * 修改会员信息 
	 * @param member 会员实体
	 */
	public void updateMember(Member member);
	/**
	 * 分配客服
	 */
	public void updateMemberByTel(String tel,String userName);
	/*public void updateMemberByTel(List<String> cusList,List<Member> memberList,int i) ;*/
	/**
	 * 批量更新
	 */
	public void updateUserNameByTelBatch(List<MemberTransit> userList);
	/**
	 * 批量插入
	 */
	public void addMemberBatch(List<Member> userList);

}
