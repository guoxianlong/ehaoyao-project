package com.ehaoyao.opertioncenter.send.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.ordercenter.orderModel.OrderDetail;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderDataVO;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderParamVO;
import com.ehaoyao.opertioncenter.send.dao.OrderInfoDao;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;

/**
 * 
 * Title: OrderInfoDaoImpl.java
 * 
 * Description: 订单中心订单
 * 
 * @author Administrator
 * @version 1.0
 * @created 2014年11月12日 下午3:53:01
 */
@Repository
public class OrderInfoDaoImpl implements OrderInfoDao{
	private static final Logger logger = Logger.getLogger(OrderInfoDaoImpl.class);
	// 订单中心jdbcTemplate
	@Resource(name = "jdbcTemplateOrder")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "hibernateTemplateOrder")
	HibernateTemplate hibernateTemplate;


	@Override
	public List<OrderInfoVO> getOrders(ShortMessageRuleVO<OrderInfoVO> vo) {
		String sql = "SELECT o.order_number, o.receiver, o.mobile, o.order_flag, o.order_status,"
				+ " o.to_erp, o.pay_type, o.to_ordercenter_time, o.to_erp_time, o.last_time,"
				+ " e.express_id,e.express_com_id,e.express_com_code, e.express_com_name,"
				+ " e.express_status ,e.delivery_date"
				+ " FROM order_info o "
				+ " LEFT JOIN express_info e"
				+ " ON o.order_number=e.order_number and o.order_flag=e.order_flag"
				+ " WHERE 1=1 ";
        List<Object> paramList = new ArrayList<Object>();
        sql += getWhereSql(vo,paramList);
        sql += " limit "+vo.getPageModel().getFirstResult()+","+vo.getPageModel().getPageSize();
        List<OrderInfoVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<OrderInfoVO>(OrderInfoVO.class));
		return ls;
	}

	@Override
	public int getOrdersCount(ShortMessageRuleVO<OrderInfoVO> vo) {
		String sql = "SELECT count(*) "
				+ " FROM order_info o "
				+ " LEFT JOIN express_info e"
				+ " ON o.order_number=e.order_number and o.order_flag=e.order_flag"
				+ " WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		sql += getWhereSql(vo, paramList);
		Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}
	
	//短信规则查询条件
	private String getWhereSql(ShortMessageRuleVO<OrderInfoVO> vo,List<Object> paramList){
		if(vo!=null){
			StringBuffer wsql = new StringBuffer();
			if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
				wsql.append(" and o.order_flag = ? ");
				paramList.add(vo.getOrderFlag().trim());
			}
			//仅针对货到付款
			if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
				wsql.append(" and o.pay_type like ? ");
				paramList.add("%"+vo.getCashDelivery().trim()+"%");
			}
			if(vo.getOrderStatus()!=null && vo.getOrderStatus().trim().length()>0){
				String orderStatus = vo.getOrderStatus().trim();
				//已下单
				if("1".equals(orderStatus)){
					wsql.append(" and o.order_status='s00' and o.to_erp='0' ");
					if(vo.getStartTime()!=null){
						wsql.append(" and o.to_ordercenter_time >= ? ");
						paramList.add(vo.getStartTime());
					}
					if(vo.getEndTime()!=null){
						wsql.append(" and o.to_ordercenter_time < ? ");
						paramList.add(vo.getEndTime());
					}
					wsql.append(" order by o.to_ordercenter_time asc ");
				}else if("2".equals(orderStatus)){//配货中
					wsql.append(" and o.order_status='s00' and o.to_erp='1' ");
					if(vo.getStartTime()!=null){
						wsql.append(" and o.to_erp_time >= ? ");
						paramList.add(vo.getStartTime());
					}
					if(vo.getEndTime()!=null){
						wsql.append(" and o.to_erp_time < ? ");
						paramList.add(vo.getEndTime());
					}
					wsql.append(" order by o.to_erp_time asc ");
				}else if("3".equals(orderStatus)){//已出库
					//wsql.append(" and o.order_status='s02' and o.to_erp='4' ");
					wsql.append(" and o.order_status in ('s01','s02') ");
				}else if("4".equals(orderStatus)){//已完成
					wsql.append(" and o.order_status='s03' ");
				}else if("6".equals(orderStatus)){//运单复核
					wsql.append(" and o.order_status in ('s01','s02') ");
					if(vo.getsTime()!=null && vo.getsTime().trim().length()>0){
						wsql.append(" and e.delivery_date like ? ");
						paramList.add(vo.getsTime().substring(0, 10)+"%");
					}
					wsql.append(" order by o.last_time asc ");
				}
				
				if("3".equals(orderStatus) || "4".equals(orderStatus)){
					if(vo.getStartTime()!=null){
						wsql.append(" and o.last_time >= ? ");
						paramList.add(vo.getStartTime());
					}
					if(vo.getEndTime()!=null){
						wsql.append(" and o.last_time < ? ");
						paramList.add(vo.getEndTime());
					}
					wsql.append(" order by o.last_time asc ");
				}
				
			}
			return wsql.toString();
		}
		return "";
	}

	//按订单信息查询订单
	@Override
	public List<OrderInfoVO> getOrder(OrderInfoVO vo) {
		String sql = "SELECT o.order_number, o.receiver, o.mobile, o.order_flag, o.order_status,"
				+ " o.to_erp, o.pay_type, o.to_ordercenter_time, o.to_erp_time, o.last_time"
				+ " FROM order_info o WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(vo!=null){
			if(vo.getOrderNumber()!=null && vo.getOrderNumber().trim().length()>0 ){
				sql += " and o.order_number = ?";
				paramList.add(vo.getOrderNumber().trim());
			}
			if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
				sql += " and o.order_flag = ?";
				paramList.add(vo.getOrderFlag().trim());
			}
		}
		List<OrderInfoVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<OrderInfoVO>(OrderInfoVO.class));
		return ls;
	}

	//获取拆单物流信息
	@Override
	public List<OrderInfoVO> getExpressInfoRemoves(OrderInfoVO vo) {
		String sql = "SELECT e.express_id,e.express_com_id,e.express_com_code, e.express_com_name, e.order_number,"
				+ " e.delivery_date,e.order_flag"
				+ " FROM express_info_remove e WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(vo!=null){
			if(vo.getOrderNumber()!=null && vo.getOrderNumber().trim().length()>0 ){
				sql += " and e.order_number = ?";
				paramList.add(vo.getOrderNumber().trim());
			}
			if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
				sql += " and e.order_flag = ?";
				paramList.add(vo.getOrderFlag().trim());
			}
		}
		sql += " order by e.delivery_date desc ";
		List<OrderInfoVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<OrderInfoVO>(OrderInfoVO.class));
		return ls;
	}

	//获取退款订单总数
	@Override
	public int getOrderRefundsCount(ShortMessageRuleVO<OrderInfoVO> vo) {
		String sql = "SELECT count(*) FROM order_info o,order_refund r"
				+ " WHERE o.order_number=r.order_number and o.order_flag=r.order_flag ";
		List<Object> paramList = new ArrayList<Object>();
		sql += getRefundWhereSql(vo, paramList);
		Integer count = jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
		return count;
	}

	//获取退款订单
	@Override
	public List<OrderInfoVO> getOrderRefunds(ShortMessageRuleVO<OrderInfoVO> vo) {
		String sql = "SELECT o.order_number, r.receiver, r.moblie as mobile, o.order_flag, o.order_status,"
				+ " o.pay_type, o.last_time,r.refund_time,r.remark as refundMoney"
				+ " FROM order_info o,order_refund r"
				+ " WHERE o.order_number=r.order_number and o.order_flag=r.order_flag ";
        List<Object> paramList = new ArrayList<Object>();
        sql += getRefundWhereSql(vo,paramList);
        sql += " limit "+vo.getPageModel().getFirstResult()+","+vo.getPageModel().getPageSize();
        List<OrderInfoVO> ls = jdbcTemplate.query(sql, paramList.toArray(), new BeanPropertyRowMapper<OrderInfoVO>(OrderInfoVO.class));
		return ls;
	}

	/**
	 * 退款订单查询条件
	 */
	private String getRefundWhereSql(ShortMessageRuleVO<OrderInfoVO> vo, List<Object> paramList) {
		if(vo!=null){
			StringBuffer wsql = new StringBuffer();
			if(vo.getOrderFlag()!=null && vo.getOrderFlag().trim().length()>0){
				wsql.append(" and o.order_flag = ? ");
				paramList.add(vo.getOrderFlag().trim());
			}
			//仅针对货到付款
			if(vo.getCashDelivery()!=null && vo.getCashDelivery().trim().length()>0){
				wsql.append(" and o.pay_type like ? ");
				paramList.add("%"+vo.getCashDelivery().trim()+"%");
			}
			if(vo.getOrderStatus()!=null && "7".equals(vo.getOrderStatus().trim())){
				//退款金额不为空，退款成功
				wsql.append(" and r.remark is not null ");
				if(vo.getStartTime()!=null){
					wsql.append(" and r.refund_time >= ? ");
					paramList.add(vo.getStartTime());
				}
				if(vo.getEndTime()!=null){
					wsql.append(" and r.refund_time < ? ");
					paramList.add(vo.getEndTime());
				}
				wsql.append(" order by r.refund_time asc ");
			}
			return wsql.toString();
		}
		return "";
	}

	//查询订单总数
	@Override
	public int getOrderDataCount(OrderParamVO orderVO) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM order_info o, jsd_order j ");
		sql.append(" WHERE o.order_number=j.jsd_order_number ");
		List<Object> params = new ArrayList<Object>();
		sql.append(getOrderDataSql(orderVO,params));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
		return count;
	}
	
	/**
	 * 查询极速达订单
	 */
	public List<OrderDataVO> getOrderData(PageModel<OrderDataVO> pm, OrderParamVO orderVO){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o.order_number, o.start_time, remark, o.pay_type, o.receiver,");
		sql.append(" o.address_detail, o.mobile, o.order_price, o.country, o.order_flag, o.order_status,");
		sql.append(" o.to_erp, j.jds_order_status ");
		sql.append(" FROM order_info o, jsd_order j ");
		sql.append(" WHERE o.order_number=j.jsd_order_number ");
		List<OrderDataVO> ls = null;
		List<Object> params = new ArrayList<Object>();
		sql.append(getOrderDataSql(orderVO,params));
		sql.append(" order by o.start_time desc ");
		if(pm.getPageSize()>0){
			sql.append(" limit "+pm.getFirstResult()+","+pm.getPageSize());
		}
		ls = jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(OrderDataVO.class));
		try {
			logger.info("极速达订单查询："+sql.toString());
			logger.info("params："+org.apache.commons.lang.StringUtils.join(params,","));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
	}
	//查询条件
	private String getOrderDataSql(OrderParamVO orderVO,List<Object> params){
		StringBuffer sql = new StringBuffer();
		if(orderVO!=null){
			if(orderVO.getOrderNumber()!=null && orderVO.getOrderNumber().trim().length()>0){
				sql.append(" and TRIM(BOTH ' ' from o.order_number) = ? ");
				params.add(orderVO.getOrderNumber().trim());
			}
			if(orderVO.getOrderFlag()!=null && orderVO.getOrderFlag().trim().length()>0){
				sql.append(" and o.order_flag = ?");
				params.add(orderVO.getOrderFlag().trim());
			}
			if(orderVO.getTel()!=null && orderVO.getTel().trim().length()>0){
				sql.append(" and o.mobile = ?");
				params.add(orderVO.getTel().trim());
			}
			//订单日期-开始时间
			if(orderVO.getStartDate()!=null && orderVO.getStartDate().trim().length()>0){
				sql.append(" and o.start_time >= ?");
				params.add(orderVO.getStartDate().trim());
			}
			//订单日期-开始时间
			if(orderVO.getEndDate()!=null && orderVO.getEndDate().trim().length()>0){
				sql.append(" and o.start_time <= ?");
				params.add(orderVO.getEndDate().trim());
			}
			if(orderVO.getJdsOrderStatus()!=null && orderVO.getJdsOrderStatus().trim().length()>0){
				sql.append(" and j.jds_order_status = ?");
				params.add(orderVO.getJdsOrderStatus().trim());
			}
		}
		return sql.toString();
	}
	
	/**
	 * 查询订单详情
	 */
	@SuppressWarnings("unchecked")
	public List<OrderDetail> getOrderDetails(OrderParamVO orderVO){
		StringBuffer hql = new StringBuffer("from "+OrderDetail.class.getName()+" d where 1=1 ");
		List<String> params = new ArrayList<String>();
		List<OrderDetail> ls = null;
		if(orderVO!=null){
			if(orderVO.getOrderNumber()!=null && orderVO.getOrderNumber().trim().length()>0){
				hql.append(" and TRIM(BOTH ' ' from d.orderNumber) = ? ");
				params.add(orderVO.getOrderNumber().trim());
			}
			if(orderVO.getOrderFlag()!=null && orderVO.getOrderFlag().trim().length()>0){
				hql.append(" and d.orderFlag = ?");
				params.add(orderVO.getOrderFlag().trim());
			}
			ls = this.hibernateTemplate.find(hql.toString(),params.toArray());
		}
		return ls;
	}

}
