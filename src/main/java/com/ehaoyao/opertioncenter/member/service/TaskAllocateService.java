package com.ehaoyao.opertioncenter.member.service;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.vo.OrderHouseVO;
/**
 * @author Administrator
 * 会员信息
 */
public interface TaskAllocateService {
	public List<OrderHouseVO> getOrderList(int firstResult, Integer pageSize,OrderHouseVO vo);
	public int getOrderCount(OrderHouseVO vo); 
	public void updateUserName(List<String> serList,List<String> telList,int count);
	public void updateBatchUserName(List<String> serList,List<Member> memberList,int count);
	public void addTask(String[] serIdArr,String[] cusArr,String ask,String taskDate);
	public void addBatchTask(List<String> serList,String ask,String taskDate,int page,int pageSize,OrderHouseVO vo);
}
