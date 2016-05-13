package com.ehaoyao.opertioncenter.payOrder.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.payOrder.dao.IPayOrderDao;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderInfoDetailVO;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderShowInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;


@Repository
public class PayOrderDaoImpl implements IPayOrderDao {
	
	private static final Logger logger = Logger.getLogger(PayOrderDaoImpl.class);

		// 订单中心jdbcTemplate
		@Resource(name = "jdbcTemplateOrder")
		JdbcTemplate jdbcTemplate;
		
		@Override
		public int getCountOrderInfos(OrderInfoDetailVO vo)  throws Exception{
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from order_info where 1=1");
			List<OrderShowInfo> list = null;
			sql.append(getOrderDataSql(vo));
			Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			
			return count;
		
		}

		/**
		 * 查询条件
		 * 
		 * @param vo
		 * @param params
		 * @return
		 */
		private String getOrderDataSql(OrderInfoDetailVO vo)  throws Exception{
			StringBuffer sql = new StringBuffer();
			if (vo != null) {
				if (vo.getOrderNumber() != null && vo.getOrderNumber().length() > 0) {
					sql.append(" and TRIM(BOTH ' ' from order_number) like '%"+vo.getOrderNumber()+"%' ");
				}
				if (vo.getOrderFlag() != null && vo.getOrderFlag().trim().length() > 0 && !"-1".equals(vo.getOrderFlag().trim())) {
					sql.append(" and order_flag = '"+vo.getOrderFlag().trim()+"'");
				}
				if (vo.getStartDate() != null && vo.getStartDate().trim().length() > 0) {
					sql.append(" and date_format(start_time,'%Y-%m-%d') >= date_format('"+vo.getStartDate().trim()+"','%Y-%m-%d')");
				}
				if (vo.getEndDate() != null && vo.getEndDate().trim().length() > 0) {
					sql.append(" and date_format(start_time,'%Y-%m-%d') <= date_format('"+vo.getEndDate().trim()+"','%Y-%m-%d')");
				}
		}
			return sql.toString();
	}
		
		@Override
		public List<OrderShowInfo> getOrderInfos(PageModel<OrderShowInfo> pm, OrderInfoDetailVO vo) throws Exception{
			StringBuffer sql = new StringBuffer();
			sql.append("select id,order_number,start_time orderTime,pay_type,price,receiver,address_detail,mobile,telephone,province,"
					+ "city,country,order_price,discount_amount,express_price,order_flag,order_status,to_erp,payment_time,"
					+ "to_ordercenter_time,to_erp_time,remark,kf_account from order_info where 1=1");
			List<OrderShowInfo> list = null;
			sql.append(getOrderDataSql(vo));
			if(vo.getOrderBy()!=null&&vo.getOrderBy().trim().length()>0&&vo.getSort()!=null&&vo.getSort().trim().length()>0){
				sql.append(" order by "+vo.getOrderBy()+" "+vo.getSort());
			}else{
				sql.append(" order by id asc,start_time asc ");
			}
			if (pm.getPageSize() > 0) {
				sql.append(" limit " + pm.getFirstResult() + "," + pm.getPageSize());
			}
			list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderShowInfo.class));
			logger.info("【订单查询sql:】"+sql.toString());
			return list;
			
		}	
		@Override
		public List<OrderDetail> getOrderDetails(OrderInfoDetailVO vo)  throws Exception{
			StringBuffer sql = new StringBuffer();
			sql.append("select id order_detail_id,order_number,merchant_id,total_price ,count,product_id ,unit ,product_name ,price ,"
					+ "order_flag,discount_amount");
			sql.append(" FROM order_detail WHERE 1=1");
			List<OrderDetail> list = null;
			if(vo!=null){
				if(vo.getOrderNumber()!=null && vo.getOrderNumber().length()>0){
					sql.append(" and TRIM(BOTH ' ' from order_number) = '"+vo.getOrderNumber()+"' ");
				}
				if(vo.getOrderFlag()!=null && vo.getOrderFlag().length()>0){
					sql.append(" and TRIM(BOTH ' ' from order_flag) = '"+vo.getOrderFlag()+"' ");
				}
			}
			logger.info("【订单详细查询sql:】"+sql.toString());
			list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderDetail.class));
			
			return list;
		}
		
		
		/**
		 * 获取数据库存在的渠道
		 * 
		 */
		@Override
		public List<OrderShowInfo> getOrderFlag(){
			StringBuffer sql=new StringBuffer();
			sql.append("select DISTINCT(order_flag) from order_info");
			List<OrderShowInfo> list = null;
		
			list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderShowInfo.class));
			return list;
		}
		
}
