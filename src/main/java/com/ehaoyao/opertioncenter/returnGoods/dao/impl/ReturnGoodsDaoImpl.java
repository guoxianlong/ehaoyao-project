package com.ehaoyao.opertioncenter.returnGoods.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.returnGoods.dao.ReturnGoodsDao;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsDetailInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.ReturnGoodsHistory;
import com.ehaoyao.opertioncenter.returnGoods.vo.GoodsInfoVO;
import com.haoyao.goods.dao.impl.BaseDaoImpl;


/**
 * 退货流程
 * @author zhang
 *
 */
@Repository
public class ReturnGoodsDaoImpl extends BaseDaoImpl<GoodsInfo, Long>
		implements ReturnGoodsDao {

	// 订单中心jdbcTemplate
	@Resource(name = "jdbcTemplateOrder")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "hibernateTemplateOrder")
	HibernateTemplate hibernateTemplate;
	
	/**
	 * 查看退货订单信息
	 */
	public List<GoodsInfo> getReturnGoodsInfo(PageModel<GoodsInfo> pm,
			GoodsInfoVO vo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT order_number,start_time,order_flag,receiver,mobile,price,remark,pay_type,");
		sql.append(" order_status,CONCAT(province,city,country) as address, order_price,kf_account");
		sql.append(" FROM order_info");
		sql.append(" WHERE order_flag = 'SJPA' AND order_status='s09' ");
		List<GoodsInfo> ls = null;
		List<Object> params = new ArrayList<Object>();
		sql.append(getOrderDataSql(vo,params));
		sql.append(" order by start_time desc ");
		if(pm.getPageSize()>0){
			sql.append(" limit "+pm.getFirstResult()+","+pm.getPageSize());
		}
		ls = jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GoodsInfo.class));
		return ls;
	}
	/**
	 * 查询条件
	 * @param vo
	 * @param params
	 * @return
	 */
	private String getOrderDataSql(GoodsInfoVO vo,List<Object> params){
		StringBuffer sql = new StringBuffer();
		if(vo!=null){
			if(vo.getOrderNumber()!=null && vo.getOrderNumber()>0){
				sql.append(" and TRIM(BOTH ' ' from order_number) = ? ");
				params.add(vo.getOrderNumber());
			}
			if(vo.getMobile()!=null && vo.getMobile().trim().length()>0){
				sql.append(" and mobile = ?");
				params.add(vo.getMobile().trim());
			}
			if(vo.getStartDate()!=null && vo.getStartDate().trim().length()>0){
				sql.append(" and start_time >= ?");
				params.add(vo.getStartDate().trim());
			}
			if(vo.getEndDate()!=null && vo.getEndDate().trim().length()>0){
				sql.append(" and start_time <= ?");
				params.add(vo.getEndDate().trim());
			}
		}
		return sql.toString();
	}
	/**
	 * 退货订单明细
	 */
	public List<GoodsDetailInfo> getGoodsDetails(GoodsInfoVO vo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT order_number,merchant_id,total_price,count,product_id,product_name,price,order_flag");
		sql.append(" FROM order_detail WHERE 1=1");
		List<GoodsDetailInfo> ls = null;
		List<String> params = new ArrayList<String>();
		if(vo!=null){
			if(vo.getOrderNumber()!=null && vo.getOrderNumber()>0){
				sql.append(" and TRIM(BOTH ' ' from order_number) = ? ");
				params.add(String.valueOf(vo.getOrderNumber()));
			}
		}
		ls = jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(GoodsDetailInfo.class));
		return ls;
	}
	/**
	 * 查看退货订单数量
	 */
	public int getRetuanOrderCount(GoodsInfoVO vo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM order_info o");
		sql.append(" WHERE order_flag = 'SJPA' AND order_status='s09' ");
		List<Object> params = new ArrayList<Object>();
		sql.append(getOrderDataSql(vo,params));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
		return count;
	}
	/**
	 * 更新退货单状态
	 */
	@Override
	public void updateStatus(GoodsInfoVO vo) {
		final String orderStatus = vo.getOrderStatus();
		final Long orderNumber = vo.getOrderNumber();
		jdbcTemplate.update("update order_info set order_status = ? where order_number  = ?", new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps)
			throws SQLException {
				ps.setString(1, orderStatus);
				ps.setLong(2, orderNumber);
			}
		});
	}
	/**
	 * 保留退货信息(历史数据)
	 */
	@Override
	public void insertHistory(GoodsInfoVO vo) {
		String orderStatus = vo.getOrderStatus();
		Long orderNumber = vo.getOrderNumber();
		String hql = "update "+GoodsInfo.class.getName()+" set orderStatus='" + orderStatus+ "' where TRIM(orderNumber)=:orderNumber";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("orderNumber", orderNumber);
		q.executeUpdate();
	}
	/**
	 * 更新退货状态(运营中心历史数据)
	 */
	@Override
	public void updateStatusOperation(GoodsInfoVO vo) {
		String orderStatus = vo.getOrderStatus();
		Long orderNumber = vo.getOrderNumber();
		String hql = "update "+GoodsInfo.class.getName()+" set orderStatus='" + orderStatus+ "' where TRIM(orderNumber)=:orderNumber";
		Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		q.setParameter("orderNumber", orderNumber);
		q.executeUpdate();
	}
	/**
	 * 记录操作记录
	 */
	@SuppressWarnings("unchecked")
	public void insertHistory(final ReturnGoodsHistory bean) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				session.save(bean);
				session.flush();
				return null;
			}
		});
	}
}
