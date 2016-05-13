package com.ehaoyao.opertioncenter.reportForm.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.reportForm.dao.IThirdOrderAuditReportDao;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.ehaoyao.opertioncenter.thirdOrderSecondAudit.dao.IThirdOrderSecondAuditDao;

@Repository
public class ThirdOrderAuditReportDaoImpl implements IThirdOrderAuditReportDao {

	private static final Logger logger = Logger.getLogger(ThirdOrderAuditReportDaoImpl.class);
	
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IThirdOrderSecondAuditDao iThirdOrderSecondAuditDao;
	
	@Override
	public int getCountOrderInfos(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) "
				+ " FROM ((order_audit_log g JOIN order_info f ON g.order_number = f.order_number AND g.order_flag = f.order_flag )  "
				+ " left join (select o.* from invoice_info o join "
				+ " (select v.order_number,v.order_flag,max(v.create_time) as create_time from invoice_info v group by v.order_number,v.order_flag) d "
				+ " on o.order_number=d.order_number and o.order_flag=d.order_flag and o.create_time=d.create_time ) i "
				+ " on i.order_number=g.order_number and i.order_flag=g.order_flag)"
				+ " JOIN ( SELECT g.order_number, g.order_flag, max(g.create_time) AS create_time FROM order_audit_log g"
				+ " GROUP BY g.order_number, g.order_flag ) k ON g.order_number = k.order_number"
				+ " AND g.order_flag = k.order_flag AND g.create_time = k.create_time  "
				+ " WHERE f.prescription_type='1' ");
		sql.append(getOrderDataSql(vo));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		return count;
	}

	@Override
	public List<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm, ThirdOrderAuditVO vo) throws Exception{
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ( CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT
				+ "' THEN 1 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' THEN 2 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRERETURN
				+ "' THEN 3 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_SUCC
				+ "' THEN 4 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN + "' THEN 5 END) AS seqno,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT
				+ "' THEN '' WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.audit_user  " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN (select gg.audit_user from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag "
				+ " and gg.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 ) END ) AS kf_audit_user," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.create_time " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN (select gg.create_time from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag "
				+ "  and gg.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 )  END ) AS kf_audit_time," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.audit_description " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN "
				+ " (select gg.audit_description from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag  and gg.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 ) END ) AS kf_audit_description,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT + "'  || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN g.audit_user END ) AS doctor_audit_user," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' || g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' || g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRERETURN
				+ "' THEN '' WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_RETURN + "' THEN g.create_time END ) AS doctor_audit_time,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN g.audit_description END ) AS doctor_audit_description, "
				+ "g.order_audit_log_id, g.order_number, g.order_flag, g.kf_account, g.audit_user, g.audit_status,"
				+ "g.audit_description, g.create_time,"
				+ "f.order_id, f.order_number as info_order_number, f.order_flag as info_order_flag, f.user_id, f.user_code, f.nick_name, "
				+ "f.user_name, f.prescription_type,f.order_status, f.audit_status as info_audit_status,f.invoice_status, f.pay_status, "
				+ "f.order_type, f.pay_type, f.order_time, f.receiver,f.patient_name, f.sex, f.age, f.pregnant_flag, f.decoct_flag, "
				+ "f.dose, f.small_pic_link, f.big_pic_link,f.payment_time, f.delivery_date, f.hospital_presc_date, f.address_detail, "
				+ "f.mobile, f.telephone,f.province, f.city, f.area, f.country, f.price, f.order_price, f.discount_amount, f.over_return_free, f.express_price, "
				+ "f.plat_pay_price, f.instructions, f.remark, f.fee_type, f.last_time, f.create_time as info_create_time,"
				+ "i.invoice_id, i.order_number as invoice_order_number, i.order_flag as invoice_order_flag, i.invoice_type,i.invoice_title, "
				+ "i.invoice_content,i.remark as invoice_remark, i.create_time as invoice_create_time "
				+ " FROM ((order_audit_log g JOIN order_info f ON g.order_number = f.order_number AND g.order_flag = f.order_flag )  "
				+ " left join (select o.* from invoice_info o join "
				+ " (select v.order_number,v.order_flag,max(v.create_time) as create_time from invoice_info v group by v.order_number,v.order_flag) d "
				+ " on o.order_number=d.order_number and o.order_flag=d.order_flag and o.create_time=d.create_time ) i "
				+ " on i.order_number=g.order_number and i.order_flag=g.order_flag)"
				+ " JOIN ( SELECT g.order_number, g.order_flag, max(g.create_time) AS create_time FROM order_audit_log g"
				+ " GROUP BY g.order_number, g.order_flag ) k ON g.order_number = k.order_number"
				+ " AND g.order_flag = k.order_flag AND g.create_time = k.create_time  "
				+ " WHERE f.prescription_type='1' ");
		List<OrderMainInfo> list = null;
		sql.append(getOrderDataSql(vo));
		if(vo.getOrderBy()!=null&&vo.getOrderBy().trim().length()>0&&vo.getSort()!=null&&vo.getSort().trim().length()>0){
			sql.append(" order by "+vo.getOrderBy()+" "+vo.getSort());
		}else{
			sql.append(" order by seqno asc,f.order_time asc ");
		}
		if (pm.getPageSize() > 0) {
			sql.append(" limit " + pm.getFirstResult() + "," + pm.getPageSize());
		}
		list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderMainInfo.class));
		logger.info("【运营中心-处方审核报表查询sql:】"+sql.toString());
		if(list!=null && !list.isEmpty()){
			boolean flag = false;
			for(int i=0;i<list.size();i++){
				flag = iThirdOrderSecondAuditDao.getSecondAuditFlag(list.get(i).getOrderFlag());
				if(!flag){
					list.get(i).setKfAccount("");
					list.get(i).setKfAuditDescription("");
					list.get(i).setKfAuditTime("");
					list.get(i).setKfAuditUser("");
				}
			}
		}
		return list;
	}

	@Override
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  order_detail_id ,  order_number , order_flag,  merchant_id ,  product_id ,  product_name ,  total_price ,  product_spec ,  product_brand ,"
				+ "  pack_count ,  count ,  price ,  unit ,  gift_flag ,  prescription_type ,  product_license_no ,  pharmacy_company ,  discount_amount ,"
				+ "  create_time ,  last_time ");
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
		list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderDetail.class));
		return list;
	}
	
	@Override
	public List<OrderMainInfo> getOrderMainInfosbyConditions(ThirdOrderAuditVO vo) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ( CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT
				+ "' THEN 1 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' THEN 2 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRERETURN
				+ "' THEN 3 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_SUCC
				+ "' THEN 4 WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN + "' THEN 5 END) AS seqno,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT
				+ "' THEN '' WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.audit_user  " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN (select gg.audit_user from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag "
				+ " and gg.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 ) END ) AS kf_audit_user," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.create_time " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN (select gg.create_time from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag "
				+ "  and gg.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 )  END ) AS kf_audit_time," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN g.audit_description " + " WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN "
				+ " (select gg.audit_description from order_audit_log gg where gg.order_number=g.order_number and gg.order_flag=g.order_flag  and gg.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' order by gg.create_time desc limit 1 ) END ) AS kf_audit_description,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT + "'  || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN g.audit_user END ) AS doctor_audit_user," + " (CASE WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' || g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRESUCC
				+ "' || g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_PRERETURN
				+ "' THEN '' WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_RETURN + "' THEN g.create_time END ) AS doctor_audit_time,"
				+ " (CASE WHEN g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_WAIT + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRESUCC + "' || g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_PRERETURN + "' THEN '' WHEN g.audit_status = '"
				+ OrderInfo.ORDER_AUDIT_STATUS_SUCC + "' ||g.audit_status = '" + OrderInfo.ORDER_AUDIT_STATUS_RETURN
				+ "' THEN g.audit_description END ) AS doctor_audit_description, "
				+ "g.order_audit_log_id, g.order_number, g.order_flag, g.kf_account, g.audit_user, g.audit_status,"
				+ "g.audit_description, g.create_time,"
				+ "f.order_id, f.order_number as info_order_number, f.order_flag as info_order_flag, f.user_id, f.user_code, f.nick_name, "
				+ "f.user_name, f.prescription_type,f.order_status, f.audit_status as info_audit_status,f.invoice_status, f.pay_status, "
				+ "f.order_type, f.pay_type, f.order_time, f.receiver,f.patient_name, f.sex, f.age, f.pregnant_flag, f.decoct_flag, "
				+ "f.dose, f.small_pic_link, f.big_pic_link,f.payment_time, f.delivery_date, f.hospital_presc_date, f.address_detail, "
				+ "f.mobile, f.telephone,f.province, f.city, f.area, f.country, f.price, f.order_price, f.discount_amount, f.over_return_free, f.express_price, "
				+ "f.plat_pay_price, f.instructions, f.remark, f.fee_type, f.last_time, f.create_time as info_create_time,"
				+ "i.invoice_id, i.order_number as invoice_order_number, i.order_flag as invoice_order_flag, i.invoice_type,i.invoice_title, "
				+ "i.invoice_content,i.remark as invoice_remark, i.create_time as invoice_create_time "
				+ " FROM ((order_audit_log g JOIN order_info f ON g.order_number = f.order_number AND g.order_flag = f.order_flag )  "
				+ " left join (select o.* from invoice_info o join "
				+ " (select v.order_number,v.order_flag,max(v.create_time) as create_time from invoice_info v group by v.order_number,v.order_flag) d "
				+ " on o.order_number=d.order_number and o.order_flag=d.order_flag and o.create_time=d.create_time ) i "
				+ " on i.order_number=g.order_number and i.order_flag=g.order_flag)"
				+ " JOIN ( SELECT g.order_number, g.order_flag, max(g.create_time) AS create_time FROM order_audit_log g"
				+ " GROUP BY g.order_number, g.order_flag ) k ON g.order_number = k.order_number"
				+ " AND g.order_flag = k.order_flag AND g.create_time = k.create_time  "
				+ " WHERE f.prescription_type='1' ");
		List<OrderMainInfo> list = null;
		sql.append(getOrderDataSql(vo));
		sql.append(" order by f.order_time desc ");
		list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderMainInfo.class));
		return list;
	}

	/**
	 * 查询条件
	 * 
	 * @param vo
	 * @param params
	 * @return
	 */
	private String getOrderDataSql(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		if (vo != null) {
			if (vo.getOrderNumber() != null && vo.getOrderNumber().length() > 0) {
				sql.append(" and TRIM(BOTH ' ' from g.order_number) like '%"+vo.getOrderNumber()+"%' ");
			}
			if (vo.getOrderFlag() != null && vo.getOrderFlag().trim().length() > 0 && !"-1".equals(vo.getOrderFlag().trim())) {
				sql.append(" and g.order_flag = '"+vo.getOrderFlag().trim()+"'");
			}
			if (vo.getStartDate() != null && vo.getStartDate().trim().length() > 0) {
				sql.append(" and date_format(f.order_time,'%Y-%m-%d') >= date_format('"+vo.getStartDate().trim()+"','%Y-%m-%d')");
			}
			if (vo.getEndDate() != null && vo.getEndDate().trim().length() > 0) {
				sql.append(" and date_format(f.order_time,'%Y-%m-%d') <= date_format('"+vo.getEndDate().trim()+"','%Y-%m-%d')");
			}
			if(vo.getAuditStatus() != null && vo.getAuditStatus().trim().length() > 0 && !"-1".equals(vo.getAuditStatus().trim())){
				sql.append(" and g.audit_status = '"+vo.getAuditStatus().trim()+"' ");
			}
		}
		return sql.toString();
	}
	
}
