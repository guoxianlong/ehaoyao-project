package com.ehaoyao.opertioncenter.custServiceCenter.service;

import java.util.List;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.TrackInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommuInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommunicationVO;

/**
 * 
 * Title: CommuService.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午1:47:59
 */
public interface CommuService {
	
	/**
	 * 查询沟通记录
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return 
	 */
	public List<Communication> queryCommuList(int firstResult, int pageSize, CommunicationVO cvo);
	
	/**
	 * 
	 * 沟通记录总数
	 * @param cvo
	 * @return
	 */
	public int queryCommuCount(CommunicationVO cvo);

	/**
	 * 新增或修改沟通记录
	 * @param cm
	 */
	public void saveOrUpdate(Communication cm);

	/**
	 * 保存沟通记录
	 * @return 
	 */
	public Communication saveCommu(CommuInfo commuVO);

	/**
	 * 沟通记录查询
	 */
	public PageModel<CommuInfo> getCommuLs(PageModel<CommuInfo> pm,CommuInfo commuVO);

	/**
	 * 按沟通记录ID查询所有跟踪信息
	 */
	public List<TrackInfo> getTrackLsByCommuId(Long commuId);

	/**
	 * 按ID查询沟通记录
	 */
	public Communication getCommuById(Long id);

	/**
	 * 修改沟通记录
	 */
	public void updateCommu(Communication comm);

}
