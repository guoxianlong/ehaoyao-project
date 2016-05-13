package com.ehaoyao.opertioncenter.send.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.send.dao.ShortMessageLogDao;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLogHis;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;
/**
 * 
 * Title: ShortMessageLogDao.java
 * 
 * Description: 短信记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:58:37
 */
@Repository
public class ShortMessageLogDaoImpl extends BaseDaoImpl<ShortMessageLog, Long>
		implements ShortMessageLogDao {

	//按短信规则获取短信
	@Override
	public List<ShortMessageLog> getSMLogByRule(ShortMessageRuleVO<ShortMessageLog> vo) {
		String hql = "from " + ShortMessageLog.class.getName() + " s where 1=1 ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		hql += getWhereSql(vo, paramMap);
		hql += " order by s.lastTime asc";
		List<ShortMessageLog> ls = null;
		if(vo.getPageModel()!=null && vo.getPageModel().getPageSize()>0){
			ls = queryByHQL(hql.toString(), paramMap, vo.getPageModel().getFirstResult(), vo.getPageModel().getPageSize());
		}else{
			ls = queryByHQL(hql.toString(), paramMap);
		}
		return ls;
	}

	@Override
	public int getSMLogCountByRule(ShortMessageRuleVO<ShortMessageLog> vo) {
		String hql = "select count(*) from "+ShortMessageLog.class.getName() + " s where 1=1 ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(vo,paramMap);
		int count = queryCountByHQL(hql.toString(), paramMap);
		return count;
	}
	
	private String getWhereSql(ShortMessageRuleVO<ShortMessageLog> vo,Map<String,Object> paramMap){
		StringBuffer wHql = new StringBuffer(" and s.sendFlag='0' ");
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			wHql.append("and s.orderFlag = :orderFlag ");
			paramMap.put("orderFlag", vo.getOrderFlag().trim());
		}
		if(vo.getOrderStatus()!=null && vo.getOrderStatus().trim().length()>0){
			wHql.append("and s.orderStatus = :orderStatus ");
			paramMap.put("orderStatus", vo.getOrderStatus().trim());
		}
		if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
			wHql.append("and s.cashDelivery = :cashDelivery ");
			paramMap.put("cashDelivery", vo.getCashDelivery().trim());
		}
		if(vo.getStartTime()!=null){
			wHql.append("and s.statusTime >= :startTime ");
			paramMap.put("startTime", vo.getStartTime());
		}
		if(vo.getEndTime()!=null){
			wHql.append("and s.statusTime < :endTime ");
			paramMap.put("endTime", vo.getEndTime());
		}
		return wHql.toString();
	}
	
	//按短信信息获取短信
	@Override
	public List<ShortMessageLog> getSml(ShortMessageLogVO vo) {
		StringBuffer hql = new StringBuffer("from " + ShortMessageLog.class.getName() + " s where 1=1 ");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			hql.append("and s.orderFlag = :orderFlag ");
			paramMap.put("orderFlag", vo.getOrderFlag().trim());
		}
		if(vo.getOrderNumber()!=null && vo.getOrderNumber().trim().length()>0){
			hql.append("and s.orderNumber = :orderNumber ");
			paramMap.put("orderNumber", vo.getOrderNumber().trim());
		}
		if(vo.getOrderStatus()!=null && vo.getOrderStatus().trim().length()>0){
			hql.append("and s.orderStatus = :orderStatus ");
			paramMap.put("orderStatus", vo.getOrderStatus().trim());
		}
		if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
			hql.append("and s.cashDelivery = :cashDelivery ");
			paramMap.put("cashDelivery", vo.getCashDelivery().trim());
		}
		//运单号，拆单订单的运单号不同
		if(vo.getExpressNo()!=null && vo.getExpressNo().trim().length()>0){
			hql.append("and s.expressNo = :expressNo ");
			paramMap.put("expressNo", vo.getExpressNo());
		}
		//查询N天内的短信，检查重复短信		
		if(vo.getStatusTime()!=null){
			hql.append("and s.statusTime >= :statusTime ");
			paramMap.put("statusTime", vo.getStatusTime());
		}
		//查询N天前的历史短信，备份
		if(vo.getLastTime()!=null){
			hql.append("and s.lastTime < :lastTime ");
			paramMap.put("lastTime", vo.getLastTime());
		}
		hql.append(" order by s.lastTime asc");
		List<ShortMessageLog> ls = null;
		if(vo.getPageSize()>0){
			ls = queryByHQL(hql.toString(), paramMap, vo.getFirstResult(), vo.getPageSize());
		}else{
			ls = queryByHQL(hql.toString(), paramMap);
		}
		return ls;
	}

	//保存短信历史记录 
	@Override
	public void saveSmLogHis(ShortMessageLogHis smlHis) {
		this.getHibernateTemplate().save(smlHis);
	}

	@Override
	public void delSmLogById(Long id) {
		this.delete(id);
	}
	
	/**
	 * 统计各平台已发短信数量
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> countSms(String startDate, String endDate){
		List<Object> params = new ArrayList<Object>();
		String wheresql = "";
		if(startDate!=null && startDate.trim().length()>0){
			wheresql +=" and last_time>= ?";
			params.add(startDate.trim());
		}
		if(endDate!=null && endDate.trim().length()>0){
			wheresql +=" and last_time<= ?";
			params.add(endDate+" 23:59:59");
		}
		
		StringBuffer sql = new StringBuffer("select order_flag orderFlag,SUM(c) num from");
		sql.append(" (");
			sql.append(" select order_flag,SUM(CEIL(CHAR_LENGTH(content)/65)) c from short_message_log");
			sql.append(" where send_flag='1'");
			sql.append(wheresql);
			sql.append(" GROUP BY order_flag");
		sql.append(" UNION ALL ");
			sql.append(" select order_flag,SUM(CEIL(CHAR_LENGTH(content)/65)) from short_message_log_his");
			sql.append(" where send_flag='1' ");
			sql.append(wheresql);
			sql.append(" GROUP BY order_flag");
		sql.append(") a GROUP BY order_flag");
		sql.append(" ORDER BY num DESC");
		//两张表的查询参数相同
		List<Object> params2 = new ArrayList<Object>();
		params2.addAll(params);
		params2.addAll(params);
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		for(int i=0;i<params2.size();i++){
			q.setParameter(i,params2.get(i));
		}
		List<Map<String, Object>> ls = (List<Map<String, Object>>)q.list();
		return ls;
	}

}
