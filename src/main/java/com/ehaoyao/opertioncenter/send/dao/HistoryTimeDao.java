package com.ehaoyao.opertioncenter.send.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.send.model.HistoryTime;
import com.haoyao.goods.dao.BaseDao;

/**
 * 
 * Title: HistoryTimeDao.java
 * 
 * Description: 时间记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:57:16
 */
public interface HistoryTimeDao extends BaseDao<HistoryTime, Long>{
	
	/**
	 * 获取时间记录
	 */
	public List<HistoryTime> getHt(HistoryTime ht);

}
