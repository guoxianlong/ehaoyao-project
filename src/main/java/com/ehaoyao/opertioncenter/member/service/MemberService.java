package com.ehaoyao.opertioncenter.member.service;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.Member;
/**
 * @author Administrator
 * 会员信息
 */
@org.springframework.stereotype.Service
public interface MemberService {
	
	/**
	 * 获得会员一览信息
	 * @param startNum
	 * @param pageSize
	 * @param hqlString
	 * @return
	 */
	public List<Member> queryMemberList(int startNum, Integer pageSize, String hqlString);
	/**
	 * 获得会员总条数
	 * @param hqlString
	 * @return
	 */
	public int getMemberCount(String hqlString);
	
	public Member queryMemberByTel(String tel);
	
	public void saveMemberList();
	
	/**
	 * 添加会员信息
	 * @param member
	 * @return
	 */
	public Object addMember(Member member);
	
	/**
	 * 注册官网会员
	 * @param member
	 * @return
	 */
	public String registerMember(String nickName,String memberName,String password);
	
	/**
	 * 修改会员信息
	 */
	public void updateMember(Member member);
	/**
	 * 给会员分配客服
	 */
	public void updateMemberByTel(String tel,String userName);
	//public void updateMemberByTel(List<String> cusList,List<Member> memberList,int i);
	
}
