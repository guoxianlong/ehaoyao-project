package com.ehaoyao.opertioncenter.custServiceCenter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.custServiceCenter.dao.CommuDao;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.TrackInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommuInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommunicationVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;

/**
 * 
 * Title: CommuDaoImpl.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午2:18:10
 */
@Repository
public class CommuDaoImpl extends BaseDaoImpl<Communication, Long> implements CommuDao {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 沟通记录查询
	 * @param firstResult
	 * @param pageSize
	 * @param cvo
	 * @return
	 */
	@Override
	public List<Communication> queryCommuList(int firstResult, Integer pageSize, CommunicationVO cvo) {
		String hql = "from "+Communication.class.getName() + " where 1=1 ";
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(cvo,paramMap);
		hql += " order by createTime desc";
		List<Communication> ls = queryByHQL(hql.toString() , paramMap, firstResult, pageSize);
		return ls;
	}
	
	//沟通记录总数
	@SuppressWarnings("rawtypes")
	@Override
	public int queryCommuCount(CommunicationVO cvo) {
		String hql = "select count(*) from "+Communication.class.getName()+ " where 1=1 ";
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		hql += getWhereSql(cvo,paramMap);
		final String strHql = hql;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(strHql);
				query.setProperties(paramMap);
				List list = query.list();
				return list;
			}
		});
		if(list!=null && list.size()>0){
			return Integer.valueOf(list.get(0).toString());
		}else{
			return 0;
		}
	}
	//获取查询条件
	private String getWhereSql(CommunicationVO cvo,Map<String,Object> paramMap){
		StringBuffer hql = new StringBuffer() ;
		if(cvo!=null){
			if(cvo.getTel()!=null&&cvo.getTel().trim().length()>0){
				hql.append(" and tel = :tel");
				paramMap.put("tel", cvo.getTel().trim());
			}
			if(cvo.getStartDate()!=null&&cvo.getStartDate().trim().length()>0){
				hql.append(" and createTime >= :startDate");
				paramMap.put("startDate", cvo.getStartDate().trim());
			}
			if(cvo.getEndDate()!=null&&cvo.getEndDate().trim().length()>0){
				hql.append(" and createTime <= :endDate");
				paramMap.put("endDate", cvo.getEndDate().trim());
			}
		}
		return hql.toString();
	}

	/**
	 * 保存跟踪信息
	 */
	public void saveTrack(TrackInfo track){
		this.getHibernateTemplate().save(track);
	}

	/**
	 * 查询沟通记录总数
	 */
	public int getCommuLsCount(CommuInfo commuVO){
		StringBuffer sql = new StringBuffer("select count(*)");
		sql.append(" from communication c left join track_info t ");
		sql.append(" on c.id=t.commu_id and c.consult_date = t.consult_date where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
        sql.append(getCommuSql(commuVO,paramList));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), paramList.toArray(), Integer.class);
		return count;
	}
	
	/**
	 * 查询沟通记录列表
	 * “产品咨询”，“客户维护”，查看最近一次跟踪信息
	 * 其它类型，无跟踪信息
	 */
	public List<CommuInfo> getCommuLs(PageModel<CommuInfo> pm, CommuInfo commuVO){
		StringBuffer sql = new StringBuffer("select");
		sql.append(" c.id as commuId, c.tel, c.user_name, c.accept_result, c.second_type, c.third_type,");
		sql.append(" c.pro_category, c.dep_category, c.disease_category,");
		sql.append(" c.remark, c.create_user, c.create_time, c.cust_no, c.cust_source,");
		sql.append(" c.is_place_order, c.place_order_date, c.order_quantity, c.order_total_price, c.product_code,");
		sql.append(" c.is_today_tracking, c.consult_date, c.cust_serv_code, c.create_time,");
		sql.append(" t.id,t.is_track, t.visit_date, t.pro_keywords, t.pro_meal_ids,");
		sql.append(" t.pro_skus,t.is_order,t.no_order_cause, t.track_info");
		sql.append(" from communication c left join track_info t ");
		sql.append(" on c.id=t.commu_id and c.consult_date = t.consult_date where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
        sql.append(getCommuSql(commuVO,paramList));
        sql.append(" order by c.create_time desc ");
        if(pm!=null && pm.getPageSize()>0){
        	sql.append(" limit "+pm.getFirstResult()+","+pm.getPageSize());
        }
        List<CommuInfo> ls = jdbcTemplate.query(sql.toString(),paramList.toArray(),BeanPropertyRowMapper.newInstance(CommuInfo.class));
		return ls;
	}

	//获取查询条件
	private String getCommuSql(CommuInfo commuVO, List<Object> paramList) {
		StringBuffer sql = new StringBuffer();
		if(commuVO!=null){
			if(commuVO.getCommuId()!=null && commuVO.getCommuId().trim().length()>0){
				sql.append(" and c.id = ?");
				paramList.add(commuVO.getCommuId().trim());
			}
			if(commuVO.getTel()!=null && commuVO.getTel().trim().length()>0){
				sql.append(" and c.tel = ?");
				paramList.add(commuVO.getTel().trim());
			}
			//沟通类型
			if(commuVO.getAcceptResult()!=null && commuVO.getAcceptResult().trim().length()>0){
				if("咨询".equals(commuVO.getAcceptResult())){
					sql.append(" and c.accept_result in ('咨询','产品咨询','客户维护','无效电话')");
				}else if("产品咨询".equals(commuVO.getAcceptResult())){
					sql.append(" and c.accept_result in ('咨询','产品咨询')");
				}else if("客户维护".equals(commuVO.getAcceptResult())){
					sql.append(" and c.accept_result = '客户维护'");
				}else{
					sql.append(" and c.accept_result = ?");
					paramList.add(commuVO.getAcceptResult().trim());
				}
			}
			if(commuVO.getStartDate()!=null&&commuVO.getStartDate().trim().length()>0){
				sql.append(" and c.create_time >= ?");
				paramList.add(commuVO.getStartDate().trim());
			}
			if(commuVO.getEndDate()!=null && commuVO.getEndDate().trim().length()>0){
				sql.append(" and c.create_time <= ?");
				paramList.add(commuVO.getEndDate().trim()+" 23:59:59");
			}
		}
		return sql.toString();
	}
	
	/**
	 * 按沟通记录ID查询所有跟踪信息
	 */
	@SuppressWarnings("unchecked")
	public List<TrackInfo> getTrackLsByCommuId(Long commuId){
		String hql = " from "+TrackInfo.class.getName()+" where commuId = ? order by consultDate desc";
		List<TrackInfo> ls = this.getHibernateTemplate().find(hql, new Object[]{commuId});
		return ls;
	}
}
