package com.ehaoyao.opertioncenter.member.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.vo.OrderHouseVO;

/**
 * @author Administrator
 * 会员信息
 */
public interface TaskAllocateDao {
	//public Object addTask(Task task);
	public List<OrderHouseVO> getOrderList(int firstResult, Integer pageSize,OrderHouseVO vo);
	public int getOrderCount(OrderHouseVO vo); 
	public void addBatchScyh(List<Member> userList);
}
