package com.ehaoyao.opertioncenter.send.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.send.dao.HistoryTimeDao;
import com.ehaoyao.opertioncenter.send.model.HistoryTime;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

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
@Repository
public class HistoryTimeDaoImpl extends BaseDaoImpl<HistoryTime, Long> implements HistoryTimeDao {

	@Override
	public List<HistoryTime> getHt(HistoryTime ht) {
		String hql = "from " + HistoryTime.class.getName() + " h where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(ht!=null){
			if(ht.getOrderFlag()!=null){
				hql += " and h.orderFlag = :orderFlag ";
				paramMap.put("orderFlag", ht.getOrderFlag());
			}
			if(ht.getOrderStatus()!=null){
				hql += " and h.orderStatus = :orderStatus ";
				paramMap.put("orderStatus", ht.getOrderStatus());
			}
			if(ht.getCashDelivery()!=null){
				hql += " and h.cashDelivery = :cashDelivery ";
				paramMap.put("cashDelivery", ht.getCashDelivery());
			}
		}
		List<HistoryTime> ls = queryByHQL(hql.toString(), paramMap);
		return ls;
	}

}
