package com.ehaoyao.opertioncenter.custServiceCenter.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.TrackInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommuInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommunicationVO;
import com.haoyao.goods.dao.BaseDao;

/**
 * 
 * Title: CommuDao.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午2:12:43
 */
public interface CommuDao extends BaseDao<Communication, Long>{

	/**
	 * 
	 * 查询沟通记录
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<Communication> queryCommuList(int firstResult, Integer pageSize, CommunicationVO cvo);

	/**
	 * 
	 * 查询沟通记录总数
	 * @return
	 */
	public int queryCommuCount(CommunicationVO cvo);

	/**
	 * 保存跟踪信息
	 */
	public void saveTrack(TrackInfo track);

	/**
	 * 查询沟通记录总数
	 */
	public int getCommuLsCount(CommuInfo commuVO);
	
	/**
	 * 查询沟通记录列表
	 */
	public List<CommuInfo> getCommuLs(PageModel<CommuInfo> pm, CommuInfo commuVO);

	/**
	 * 按沟通记录ID查询所有跟踪信息
	 */
	public List<TrackInfo> getTrackLsByCommuId(Long commuId);

}
