package com.ehaoyao.opertioncenter.member.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.custServiceCenter.dao.ReservationDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.member.dao.MemberDao;
import com.ehaoyao.opertioncenter.member.dao.TaskAllocateDao;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.model.MemberTransit;
import com.ehaoyao.opertioncenter.member.service.TaskAllocateService;
import com.ehaoyao.opertioncenter.member.vo.OrderHouseVO;

/**
 * @author Administrator
 * 订单信息
 */
@Service
public class TaskAllocateServiceImpl implements TaskAllocateService{
	@Autowired
	private TaskAllocateDao taskAllocateDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ReservationDao reservationDao;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @param memberDao the memberDao to set
	 */
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	/**
	 * @param taskAllocateDao the taskAllocateDao to set
	 */
	public void setTaskAllocateDao(TaskAllocateDao taskAllocateDao) {
		this.taskAllocateDao = taskAllocateDao;
	}
	/** 
	 * 获取订单list
	 */
	@Override
	public List<OrderHouseVO> getOrderList(int firstResult, Integer pageSize,
			OrderHouseVO vo) {
		return taskAllocateDao.getOrderList(firstResult, pageSize, vo);
	}
	/**
	 * 获取订单总数
	 */
	@Override
	public int getOrderCount(OrderHouseVO vo) {
		return taskAllocateDao.getOrderCount(vo);
	}
	/**
	 * 分配客服
	 */
	@Override
	public void updateUserName(List<String> serList, List<String> telList,int count){
		for(int i=0;i<count;i++){
			for(int j=1;j<=serList.size() && i*serList.size()+j<=telList.size();j++){
				String tel = telList.get(i * serList.size() + j-1);
				String cus = serList.get(j-1);
				if(tel == null || "".equals(tel.trim())){
					continue;
				}
				memberDao.updateMemberByTel(tel.trim(), cus);
			}
		}
	}
	/**
	 * 批量分配客服
	 */
	@Override
	public void updateBatchUserName(List<String> serList,List<Member> memberList, int count){
		List<MemberTransit> list = new ArrayList<MemberTransit>();
		MemberTransit param = null;
		for(int i=0;i<count;i++){
			for(int j=1;j<=serList.size() && i * serList.size() + j<=memberList.size();j++){
				param = new MemberTransit();
				String tel = memberList.get(i * serList.size() + j-1).getTel();
				String userName = serList.get(j-1);
				param.setTel(tel);
				param.setUserName(userName);
				list.add(param);
			}
		}
		memberDao.updateUserNameByTelBatch(list);
	}
	
	/**
	 * 分配任务
	 */
	@Override
	public void addTask(String[] serIdArr,String[] cusArr,String ask,String taskDate){
		int cusLen = 0;;
		//计算需要分配多少次
		int count = 0;
		//转换成List
		List<String> serList = Arrays.asList(serIdArr);
		int serLen = serList.size();
		List<String> noAlltList = new ArrayList<String>();//未分配过客服的
		Member m = null;
		Reservation re = null;
		MemberTransit mt = null;
		List<Reservation> alltList =  new ArrayList<Reservation>();//未分配过客服的
		List<Member> newMeberList = new ArrayList<Member>();//新客户
		List<MemberTransit> updateMemberList = new ArrayList<MemberTransit>();
		for(int i=0;i<cusArr.length;i++){
			m = new Member();
			String [] arr = cusArr[i].split("}&");
			if(arr.length != 7){
				continue;
			}
			if("".equals(convertStr(arr[0]))){
				continue;
			}
			//是否新用户
			if("是".equals(convertStr(arr[5]))){
				m.setTel(convertStr(arr[0]));
				m.setMemberName(convertStr(arr[1]));
				m.setProvince(convertStr(arr[2]));
				m.setCity(convertStr(arr[3]));
				m.setCounty(convertStr(arr[4]));
				m.setCreateTime(df.format(new Date()));
				newMeberList.add(m);
			}
			//是否已经分配过客服
			if(!"".equals(convertStr(arr[6]))){
				re = new Reservation();
				re.setComment(ask);//备注
				re.setCustServiceNo(convertStr(arr[6]));//客服编码
				re.setReserveTime(taskDate);//预约回访日期
				re.setTel(convertStr(arr[0]));//手机号
				re.setAcceptResult("客户维护");//沟通类型
				re.setCustSource("老客维护");//客户来源
				alltList.add(re);
			}else{
				noAlltList.add(convertStr(arr[0]));
			}
		}
		//调用shuffle方法 随机打乱list顺序
		cusLen = noAlltList.size(); 
		count = cusLen % serLen == 0 ? cusLen / serLen : cusLen / serLen + 1;
		Collections.shuffle(serList);
		for(int i=0;i<count;i++){
			for(int j=1;j<=serLen && i*serLen+j<=cusLen;j++){
				String tel = noAlltList.get(i * serLen + j-1);
				String cus = serList.get(j-1);
				mt = new MemberTransit();
				mt.setTel(tel);
				mt.setUserName(cus);
				updateMemberList.add(mt);
				//保存今日任务
				re = new Reservation();
				re.setComment(ask);//备注
				re.setCustServiceNo(cus);//客服编码
				re.setReserveTime(taskDate);//预约回访日期
				re.setTel(tel);//手机号
				re.setAcceptResult("客户维护");//沟通类型
				re.setCustSource("老客维护");//客户来源
				alltList.add(re);
			}
		}
		memberDao.addMemberBatch(newMeberList);
		memberDao.updateUserNameByTelBatch(updateMemberList);
		reservationDao.saveReservatBatch(alltList);
	}
	/**
	 * 批量添加任务
	 * @param serIdArr
	 * @param cusArr
	 * @param ask
	 * @param taskDate
	 * @throws Exception
	 */
	@Override
	public void addBatchTask(List<String> serList,String ask,String taskDate,int page,int pageSize,OrderHouseVO vo){
		int serLen = serList.size();
		int cusLen = 0;
		List<OrderHouseVO> taskList = null;
		Member m = null;
		MemberTransit mt = null;
		Reservation re = null;
		int count = 0;
		List<String> noAlltList = null;//未分配过客服的
		List<Reservation> alltList =  null;//未分配过客服的
		List<Member> newMeberList = null;//新客户
		List<MemberTransit> updateMemberList = null;
		for(int n = 0;n<page;n++){
			taskList = taskAllocateDao.getOrderList(n * pageSize , pageSize,vo);
			if(taskList == null || taskList.size() == 0){
				continue;
			}
			alltList =  new ArrayList<Reservation>();//未分配过客服的
			newMeberList = new ArrayList<Member>();//新客户
			updateMemberList = new ArrayList<MemberTransit>();
			for(OrderHouseVO o:taskList){
				noAlltList = new ArrayList<String>();
				if(o == null){
					continue;
				}
				if(o.getTel() == null || "".equals(o.getTel().trim())){
					continue;
				}
				//新用户
				if(o.getIsExist() == null || "".equals(o.getIsExist().trim()) ){
					m = new Member();
					m.setTel(o.getTel());
					m.setMemberName(o.getName());
					m.setProvince(o.getProvince());
					m.setCity(o.getCity());
					m.setCounty(o.getCountry());
					m.setCreateTime(df.format(new Date()));
					newMeberList.add(m);
					//saveMember(m);
				}
				//是否分配过客服
				if(o.getUserName() != null && !"".equals(o.getUserName().trim())){
					//已经分配过客服
					re = new Reservation();
					re.setComment(ask);//备注
					re.setCustServiceNo(o.getUserName());//客服编码
					re.setReserveTime(taskDate);//预约回访日期
					re.setTel(o.getTel());//手机号
					re.setAcceptResult("客户维护");//沟通类型
					re.setCustSource("老客维护");//客户来源
					alltList.add(re);
					//reservatDao.savaReservat(re);
				}else{
					//未分配客服
					noAlltList.add(o.getTel());
				}
				//调用shuffle方法 随机打乱list顺序
				Collections.shuffle(serList);
				cusLen = noAlltList.size();
				count = cusLen % serLen == 0 ? cusLen / serLen : cusLen / serLen + 1;
				for(int i=0;i<count;i++){
					for(int j=1;j<=serLen && i*serLen+j<=cusLen;j++){
						String tel = noAlltList.get(i * serLen + j-1);
						String cus = serList.get(j-1);
						mt = new MemberTransit();
						mt.setTel(tel);
						mt.setUserName(cus);
						updateMemberList.add(mt);
						//保存今日任务
						re = new Reservation();
						re.setComment(ask);//备注
						re.setCustServiceNo(cus);//客服编码
						re.setReserveTime(taskDate);//预约回访日期
						re.setTel(tel);//手机号
						re.setAcceptResult("客户维护");//沟通类型
						re.setCustSource("老客维护");//客户来源
						alltList.add(re);
					}
				}
			}
			memberDao.addMemberBatch(newMeberList);
			memberDao.updateUserNameByTelBatch(updateMemberList);
			reservationDao.saveReservatBatch(alltList);
		}
	}
	/**
	 * 字符串转换
	 * @param str
	 * @return
	 */
	private String convertStr(String str){
		if(str == null || "".equals(str)){
			return "";
		}
		if(str.trim().startsWith("{")){
			return str.trim().substring(1);
		}
		return str.trim();
	}
	/**
	 * 保存客户信息
	 * @param member
	 */
	/*private void saveMember(Member member){
		Member m = memberDao.queryMemberByTel(member.getTel());
		if(m!=null){//修改
			m.setMemberName(member.getMemberName());
			m.setSex(member.getSex());
			m.setComment(member.getComment());
			//态度
			m.setAttitude(member.getAttitude());
			m.setEmail(member.getEmail());
			m.setProvince(member.getProvince());
			m.setCity(member.getCity());
			m.setCounty(member.getCounty());
			m.setUpdateTime(df.format(new Date()));
			m.setUpdateUser(member.getCreateUser());
			memberDao.updateMember(m);
		}else{
			member.setCreateTime(df.format(new Date()));
			memberDao.addMember(member);
		}
	}*/
}
