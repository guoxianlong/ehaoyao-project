package com.ehaoyao.opertioncenter.custServiceCenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.custServiceCenter.dao.CommuDao;
import com.ehaoyao.opertioncenter.custServiceCenter.dao.ReservationDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.model.TrackInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.service.CommuService;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommuInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommunicationVO;
import com.haoyao.goods.dao.UserDao;
import com.haoyao.goods.model.User;

/**
 * 
 * Title: CommuServiceImpl.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午1:57:26
 */
@Service
public class CommuServiceImpl implements CommuService {
	
	@Autowired
	private CommuDao commuDao;
	@Autowired
	private ReservationDao reservatDao;
	@Autowired
	private UserDao userDao;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void setCommuDao(CommuDao commuDao) {
		this.commuDao = commuDao;
	}

	/**
	 * @param reservatDao the reservatDao to set
	 */
	public void setReservatDao(ReservationDao reservatDao) {
		this.reservatDao = reservatDao;
	}

	//查询沟通记录列表
	@Override
	public List<Communication> queryCommuList(int firstResult, int pageSize,
			CommunicationVO cvo) {
		return commuDao.queryCommuList(firstResult, pageSize, cvo);
	}

	//沟通记录总数
	@Override
	public int queryCommuCount(CommunicationVO cvo) {
		return commuDao.queryCommuCount(cvo);
	}
	
	/**
	 * 新增或修改沟通记录
	 */
	@Override
	public void saveOrUpdate(Communication cm) {
		if(cm!=null){
			if(cm.getId()!=null){//更新
				Communication commu = commuDao.get(cm.getId());
				if(commu!=null){
					BeanUtils.copyProperties(cm, commu, new String[]{"id","createUser","createTime"});
					commuDao.update(commu);
				}else{//新增
					commuDao.save(cm);
				}
			}else{//新增
				commuDao.save(cm);
			}
		}
	}
	
	/**
	 * 保存沟通记录
	 */
	public Communication saveCommu(CommuInfo commuVO){
		Communication commu = null;
		if(commuVO!=null){
			//获取客服姓名
			String custServCode = commuVO.getCustServCode();
			String custServName = null;
			if(custServCode!=null && !"".equals(custServCode.trim())){
				User user = userDao.loadUserByUserName(custServCode);
				if(user!=null){
					custServName = user.getName();
				}
			}
			if("咨询".equals(commuVO.getAcceptResult()) || "产品咨询".equals(commuVO.getAcceptResult()) || "客户维护".equals(commuVO.getAcceptResult())){
				//修改
				if(commuVO.getId()!=null){
					commu = commuDao.get(commuVO.getId());
					if(commu!=null){
						commu.setConsultDate(df.format(new Date()));
						commu.setIsOrder(commuVO.getIsOrder());
						commu.setCustServCode(commuVO.getCustServCode());
						commu.setCustServName(custServName);
						commu.setIsNewUser(commuVO.getIsNewUser());//是否新用户
						commuDao.update(commu);
					}else{
						return null;
					}
				}else{//新增
					commu = new Communication();
					//沟通信息
					BeanUtils.copyProperties(commuVO, commu);
					commu.setCreateUser(commuVO.getCustServCode());
					commu.setCustServName(custServName);
					commu.setCreateTime(df.format(new Date()));
					commu.setConsultDate(df.format(new Date()));
					commuDao.save(commu);
				}
				//追加跟踪信息
				TrackInfo track = new TrackInfo();
				BeanUtils.copyProperties(commuVO, track);
				track.setCommuId(commu.getId());
				//本次咨询时间必须与沟通记录咨询时间完全相同
				track.setConsultDate(commu.getConsultDate());
				track.setCreateUser(commuVO.getCustServCode());
				track.setCreateUserName(custServName);//创建人，客服姓名
				track.setCreateTime(df.format(new Date()));
				//保存跟踪信息
				commuDao.saveTrack(track);
				//追加今日任务
				if(commuVO.getIsTrack() != null && "1".equals(commuVO.getIsTrack())){
					Reservation re = new Reservation();
					re.setComment(commuVO.getTrackInfo());//备注
					re.setCustServiceNo(commuVO.getCustServCode());//客服编码
					re.setReserveTime(commuVO.getVisitDate());//预约回访日期
					re.setTel(commuVO.getTel());//手机号
					re.setAcceptResult(commuVO.getAcceptResult());//沟通类型
					re.setCustSource(commuVO.getCustSource());//客户来源
					re.setLastTime(commu.getConsultDate());
					re.setProKeywords(commuVO.getProKeywords());
					reservatDao.savaReservat(re);
				}
			}else{//非“产品咨询”，无跟踪信息
				if(commuVO.getId()!=null){
					commu = commuDao.get(commuVO.getId());
					if(commu!=null){
						commu.setConsultDate(df.format(new Date()));
						commu.setRemark(commuVO.getRemark());
						commu.setUserName(commuVO.getUserName());
						commu.setCustServCode(commuVO.getCustServCode());//沟通客服工号
						commu.setCustServName(custServName);
						commuDao.update(commu);
					}
				}else{
					commu = new Communication();
					//沟通信息
					BeanUtils.copyProperties(commuVO, commu);
					commu.setCustServName(custServName);
					commu.setCreateUser(commuVO.getCustServCode());
					commu.setCreateTime(df.format(new Date()));
					commu.setConsultDate(df.format(new Date()));
					commuDao.save(commu);
				}
			}
		}
		return commu;
	}
	
	/**
	 * 沟通记录查询
	 */
	public PageModel<CommuInfo> getCommuLs(PageModel<CommuInfo> pm,CommuInfo commuVO){
		if(pm.getPageSize()>0){
			int count = commuDao.getCommuLsCount(commuVO);
			pm.setTotalRecords(count);
		}
		List<CommuInfo> ls = commuDao.getCommuLs(pm,commuVO);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 按沟通记录ID查询所有跟踪信息
	 */
	public List<TrackInfo> getTrackLsByCommuId(Long commuId){
		return commuDao.getTrackLsByCommuId(commuId);
	}

	/**
	 * 按ID查询沟通记录
	 */
	public Communication getCommuById(Long id){
		return commuDao.get(id);
	}
	
	/**
	 * 更新沟通记录
	 */
	public void updateCommu(Communication comm){
		commuDao.update(comm);
	}
	
}
