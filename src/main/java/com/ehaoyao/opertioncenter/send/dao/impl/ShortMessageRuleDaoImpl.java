package com.ehaoyao.opertioncenter.send.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.send.dao.ShortMessageRuleDao;
import com.ehaoyao.opertioncenter.send.model.ChannelRule;
import com.ehaoyao.opertioncenter.send.model.ShortMessageRule;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 
 * Title: ShortMessageRuleDao.java
 * 
 * Description: 短信规则
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:58:56
 */
@Repository
public class ShortMessageRuleDaoImpl extends
		BaseDaoImpl<ShortMessageRule, Long> implements ShortMessageRuleDao {

	@Resource(name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
		
	//获取短信规则
	@Override
	public List<ShortMessageRule> getRules(ShortMessageRuleVO<ShortMessageRule> vo) {
		String hql = "from "+ShortMessageRule.class.getName() + " r where 1=1 ";
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			hql = "select r from "+ShortMessageRule.class.getName() + " r,"+ChannelRule.class.getName()+" c"
					+ " where c.ruleId=r.id ";
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(vo,paramMap);
		hql += " order by r.orderStatus asc";
		List<ShortMessageRule> ls = null;
		if(vo.getPageModel()!=null && vo.getPageModel().getPageSize()>0){
			ls = queryByHQL(hql.toString() , paramMap, vo.getPageModel().getFirstResult(), vo.getPageModel().getPageSize());
		}else{
			ls = queryByHQL(hql.toString() , paramMap);
		}
		return ls;
	}
	
	//获取短信规则记录总数
	@Override
	public int getRulesCount(ShortMessageRuleVO<ShortMessageRule> vo) {
		String hql = "select count(*) from "+ShortMessageRule.class.getName() + " r where 1=1 ";
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			hql = "select count(*) from "+ShortMessageRule.class.getName() + " r, "+ChannelRule.class.getName()+" c"
					+ " where 1=1 and c.ruleId=r.id  ";
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(vo,paramMap);
		int count = queryCountByHQL(hql.toString() , paramMap);
		return count;
	}
	
	//短信规则查询条件
	private String getWhereSql(ShortMessageRuleVO<ShortMessageRule> vo,Map<String,Object> paramMap){
		StringBuffer wHql = new StringBuffer("");
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			wHql.append("and c.orderFlag = :orderFlag ");
			paramMap.put("orderFlag", vo.getOrderFlag().trim());
		}
		if(vo.getEnable()!=null && vo.getEnable().trim().length()>0){
			wHql.append("and r.enable = :enable ");
			paramMap.put("enable", vo.getEnable().trim());
		}
		if(vo.getOrderStatus()!=null && vo.getOrderStatus().trim().length()>0){
			wHql.append("and r.orderStatus = :orderStatus ");
			paramMap.put("orderStatus", vo.getOrderStatus().trim());
		}
		if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
			wHql.append("and r.cashDelivery = :cashDelivery ");
			paramMap.put("cashDelivery", vo.getCashDelivery().trim());
		}
		if(vo.getCurHour()!=null){
			wHql.append("and r.startHour <= :startHour ");
			paramMap.put("startHour", vo.getCurHour());
			wHql.append("and r.endHour > :endHour ");
			paramMap.put("endHour", vo.getCurHour());
		}
		return wHql.toString();
	}

	@Override
	public void onOrOffRule(ShortMessageRule rule) {
		String hql = "update "+ShortMessageRule.class.getName()+" r set r.enable = :enable where r.id = :id ";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("enable",rule.getEnable());
		q.setParameter("id", rule.getId());
		q.executeUpdate();
	}
	
	/**
	 * 判断各渠道下规则是否重复
	 */
	@Override
	public List<RuleInfoVO> getIsExistRule(RuleInfoVO cr) {
		String sql = "select c.id,r.id as ruleId,c.order_flag,r.rule_name,r.content,r.order_status,r.cash_delivery,"
				+ " r.start_hour,r.end_hour,r.out_time_flag,r.enable,r.last_time,r.word_count"
				+ " from short_message_rule r, channel_rule c where 1=1 and r.id=c.rule_id ";
		List<Object> paramList = new ArrayList<Object>();
		if(cr.getOrderFlag()!=null && cr.getOrderFlag().trim().length()>0){
			String flags = "";
			String[] arr = cr.getOrderFlag().trim().split(",");
			for(String s:arr){
				if(flags.length()>0){
					flags += ",";
				}
				flags += "'"+s+"'";
			}
			sql += " and c.order_flag in ("+flags+") ";
		}
		if(cr.getOrderStatus()!=null && cr.getOrderStatus().trim().length()>0){
			sql += "and r.order_status = ? ";
			paramList.add(cr.getOrderStatus().trim());
		}
		if(cr.getCashDelivery()!=null && cr.getCashDelivery().trim().length()>0){
			sql += "and r.cash_delivery = ? ";
			paramList.add(cr.getCashDelivery().trim());
		}
		List<RuleInfoVO> ls = jdbcTemplate.query(sql, paramList.toArray(), BeanPropertyRowMapper.newInstance(RuleInfoVO.class));
		return ls;
	}

	/**
	 * 按规则ID查询规则及对应渠道
	 */
	@Override
	public List<RuleInfoVO> getRuleByRuleId(String ruleId) {
		String sql = "select c.id,r.id as ruleId,c.order_flag,r.rule_name,r.content,r.order_status,r.cash_delivery,"
				+ " r.start_hour,r.end_hour,r.out_time_flag,r.enable,r.last_time,r.word_count "
				+ " from short_message_rule r left join channel_rule c on r.id = c.rule_id "
				+ " where r.id = ? ";
		List<RuleInfoVO> ls = jdbcTemplate.query(sql, new String[]{ruleId}, BeanPropertyRowMapper.newInstance(RuleInfoVO.class));
		return ls;
	}

	@Override
	public void addChannelRule(ChannelRule cr) {
		getHibernateTemplate().save(cr);
	}

	//按规则ID删除渠道规则关联信息
	@Override
	public void delChannelByRuleId(Long ruleId) {
		if(ruleId!=null){
			String sql = "delete from channel_rule where rule_id = ? ";
			jdbcTemplate.update(sql,new Object[]{ruleId} );
		}
	}
	
	//按渠道规则关系表ID删除渠道规则关联信息
	@Override
	public void delChannelById(Long id){
		if(id!=null){
			String sql = "delete from channel_rule where id = ? ";
			jdbcTemplate.update(sql,new Object[]{id} );
		}
	}
	
	/**
	 * 查询渠道规则信息
	 */
	public List<RuleInfoVO> getRuleInfoLs(ShortMessageRuleVO<RuleInfoVO> ruleVO){
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.id,r.id as ruleId,c.order_flag,r.rule_name,r.content,r.order_status,r.cash_delivery,"
				+ " r.start_hour,r.end_hour,r.out_time_flag,r.enable,r.last_time,r.word_count "
				+ " from short_message_rule r, channel_rule c where r.id = c.rule_id ");
		sql.append(getRuleInfoWhereSql(ruleVO,paramList));
		sql.append(" order by r.order_status ");
		PageModel<RuleInfoVO> pm = ruleVO.getPageModel();
		if(pm!=null && pm.getPageSize()>0){
			sql.append(" limit "+pm.getFirstResult()+","+pm.getPageSize());
		}
		List<RuleInfoVO> ls = jdbcTemplate.query(sql.toString(), paramList.toArray(), BeanPropertyRowMapper.newInstance(RuleInfoVO.class));
		return ls;
	}

	/**
	 * 查询渠道规则信息总数
	 */
	public int getRuleInfoLsCount(ShortMessageRuleVO<RuleInfoVO> ruleVO){
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from short_message_rule r, channel_rule c where r.id = c.rule_id ");
		sql.append(getRuleInfoWhereSql(ruleVO,paramList));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), paramList.toArray(),Integer.class);
		if(count!=null){
			return count;
		}else{
			return 0;
		}
	}
	
	//规则信息查询条件
	private String getRuleInfoWhereSql(ShortMessageRuleVO<RuleInfoVO> vo,List<Object> paramList){
		StringBuffer wHql = new StringBuffer();
		if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
			wHql.append("and c.order_flag = ? ");
			paramList.add(vo.getOrderFlag().trim());
		}
		if(vo.getEnable()!=null && vo.getEnable().trim().length()>0){
			wHql.append("and r.enable = ? ");
			paramList.add( vo.getEnable().trim());
		}
		if(vo.getOrderStatus()!=null && vo.getOrderStatus().trim().length()>0){
			wHql.append("and r.order_status = ? ");
			paramList.add(vo.getOrderStatus().trim());
		}
		if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
			wHql.append("and r.cash_delivery = ? ");
			paramList.add(vo.getCashDelivery().trim());
		}
		if(vo.getCurHour()!=null){
			wHql.append("and r.start_hour <= ? ");
			paramList.add(vo.getCurHour());
			wHql.append("and r.end_hour > ? ");
			paramList.add(vo.getCurHour());
		}
		return wHql.toString();
	}
	
	/*
	 * 按规则ID查询渠道
	 */
	public List<ChannelRule> getChannelByRuleId(Long ruleId){
		if(ruleId!=null){
			StringBuffer sql = new StringBuffer();
			sql.append("select c.id,c.rule_id,c.order_flag,c.last_time from channel_rule c where c.rule_id = ? ");
			List<ChannelRule> ls = jdbcTemplate.query(sql.toString(), new Object[]{ruleId}, BeanPropertyRowMapper.newInstance(ChannelRule.class));
			return ls;
		}else{
			return null;
		}
	}

}
