package com.ehaoyao.opertioncenter.thirdOrderSecondAudit.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderAuditLog;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ParamConfig;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.ehaoyao.opertioncenter.thirdOrderSecondAudit.dao.IThirdOrderSecondAuditDao;

@Repository
public class ThirdOrderSecondAuditDaoImpl implements IThirdOrderSecondAuditDao {

	private static final Logger logger = Logger.getLogger(ThirdOrderSecondAuditDaoImpl.class);
	// 运营中心jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

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
				+ " WHERE f.prescription_type='1' and g.audit_status in ('"+OrderInfo.ORDER_AUDIT_STATUS_PRESUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_SUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_RETURN+"') ");
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
				+ " WHERE f.prescription_type='1' and g.audit_status in ('"+OrderInfo.ORDER_AUDIT_STATUS_PRESUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_SUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_RETURN+"')");
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
		logger.info("【运营中心-药师二级审核查询sql:】"+sql.toString());
		if(list!=null && !list.isEmpty()){
			for(int i=0;i<list.size();i++){
				list.get(i).setSecondAuditFlag(getSecondAuditFlag(list.get(i).getOrderFlag()));
			}
		}
		return list;
	}

	@Override
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  order_detail_id ,  order_number ,  merchant_id ,  product_id ,  product_name ,  total_price ,  product_spec ,  product_brand ,"
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

	
	@Override
	public Object getOrderInfosByOrderNumFlags(String orderNumFlags) throws Exception {
		String[] orderNumArr = null; 
		List<OrderInfo> orderInfoList = null;
		Object retnObj = null;
		String sql = "select   order_id ,  order_number ,  order_flag ,  user_id ,  user_code ,  nick_name ,  user_name ,  prescription_type ,  order_status ,"
				+ "  audit_status ,  pay_status ,  order_type ,  pay_type ,  order_time ,  price ,  receiver ,  patient_name ,  sex ,  age , "
				+ " pregnant_flag ,  decoct_flag ,  dose ,  small_pic_link ,  big_pic_link ,  payment_time ,  delivery_date ,  address_detail , "
				+ " mobile ,  telephone ,  province ,  city , area,  country ,  order_price ,  discount_amount ,  instructions ,  remark , "
				+ " fee_type ,  last_time ,  create_time "
				+ " from order_info where 1=1 ";
		if(orderNumFlags!=null && orderNumFlags.length()>0){
			orderNumArr = orderNumFlags.split(",");
			if(orderNumArr!=null){
				if(orderNumArr.length==1){
					sql += " and CONCAT(order_number,order_flag)='"+orderNumFlags+"'";
				}
				if(orderNumArr.length>1){
					sql += " and CONCAT(order_number,order_flag) in ('"+orderNumFlags.replace(",", "','").replace(" ", "")+"') ";
				}
			}
			orderInfoList = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderInfo.class));
		}else{
			return retnObj;
		}
		if(orderInfoList.size()>0){
			if(orderInfoList.size()==1){
				return retnObj = orderInfoList.get(0);
			}
			if(orderInfoList.size()>1){
				return retnObj = orderInfoList;
			}
		}
		return retnObj;
	}
	
	
	@Override
	public int[] updateOrderInfoBatch(final List<OrderInfo> orderInfoList) throws Exception{
		String sql = " update order_info set user_id=?, user_code=?, nick_name=?, user_name=?, prescription_type=?, order_status=?, "
				+ " audit_status=?, pay_status=?, order_type=?, pay_type=?, order_time=?, price=?, receiver=?, patient_name=?,"
				+ " sex=?, age=?, pregnant_flag=?, decoct_flag=?, dose=?, small_pic_link=?, big_pic_link=?, payment_time=?, "
				+ " delivery_date=?, address_detail=?, mobile=?, telephone=?, province=?, city=?, area=?, country=?, order_price=?, discount_amount=?,"
				+ " instructions=?, remark=?, fee_type=?, last_time=?  "
				+ " where order_number=? and order_flag=?";
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderInfo orderInfo = orderInfoList.get(i);
				int j=1;
				ps.setString(j++, orderInfo.getUserId());
				ps.setString(j++, orderInfo.getUserCode());
				ps.setString(j++, orderInfo.getNickName());
				ps.setString(j++, orderInfo.getUserName());
				ps.setString(j++, orderInfo.getPrescriptionType()!=null?orderInfo.getPrescriptionType().toString():"");
				ps.setString(j++, orderInfo.getOrderStatus());
				ps.setString(j++, orderInfo.getAuditStatus());
				ps.setString(j++, orderInfo.getPayStatus());
				ps.setString(j++, orderInfo.getOrderType());
				ps.setString(j++, orderInfo.getPayType());
				ps.setString(j++, orderInfo.getOrderTime());
				ps.setBigDecimal(j++, orderInfo.getPrice());
				ps.setString(j++, orderInfo.getReceiver());
				ps.setString(j++, orderInfo.getPatientName());
				ps.setString(j++, orderInfo.getSex());
				ps.setObject(j++, orderInfo.getAge());
				ps.setString(j++, orderInfo.getPregnantFlag()!=null?orderInfo.getPregnantFlag().toString():"");
				ps.setString(j++, orderInfo.getDecoctFlag()!=null?orderInfo.getDecoctFlag().toString():"");
				ps.setString(j++, orderInfo.getDose());
				ps.setString(j++, orderInfo.getSmallPicLink());
				ps.setString(j++, orderInfo.getBigPicLink());
				ps.setString(j++, orderInfo.getPaymentTime());
				ps.setString(j++, orderInfo.getDeliveryDate());
				ps.setString(j++, orderInfo.getAddressDetail());
				ps.setString(j++, orderInfo.getMobile());
				ps.setString(j++, orderInfo.getTelephone());
				ps.setString(j++, orderInfo.getProvince());
				ps.setString(j++, orderInfo.getCity());
				ps.setString(j++, orderInfo.getArea());
				ps.setString(j++, orderInfo.getCountry());
				ps.setBigDecimal(j++, orderInfo.getOrderPrice());
				ps.setBigDecimal(j++, orderInfo.getDiscountAmount());
				ps.setString(j++, orderInfo.getInstructions());
				ps.setString(j++, orderInfo.getRemark());
				ps.setString(j++, orderInfo.getFeeType());
				ps.setString(j++, orderInfo.getLastTime());
				ps.setString(j++, orderInfo.getOrderNumber());
				ps.setString(j++, orderInfo.getOrderFlag());
			}
			
			@Override
			public int getBatchSize() {
				return orderInfoList.size();
			}
		});
	}

	@Override
	public Object addOrderAuditLogBatch(final List<OrderAuditLog> orderAuditLogList) throws Exception {
		String sql = "INSERT INTO order_audit_log "
				+ "(order_number, order_flag, kf_account, audit_user, audit_status, audit_description, create_time) VALUES"
				+ " (?,?,?,?,?,?,?) ";
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderAuditLog orderAuditLog = orderAuditLogList.get(i);
				int j=1;
				ps.setString(j++, orderAuditLog.getOrderNumber());
				ps.setString(j++, orderAuditLog.getOrderFlag());
				ps.setString(j++, orderAuditLog.getKfAccount());
				ps.setString(j++, orderAuditLog.getAuditUser());
				ps.setString(j++, orderAuditLog.getAuditStatus());
				ps.setString(j++, orderAuditLog.getAuditDescription());
				ps.setString(j++, orderAuditLog.getCreateTime());
			}
			
			@Override
			public int getBatchSize() {
				return orderAuditLogList.size();
			}
		});
	}
	

	@Override
	public OrderMainInfo getOrderMainInfo(ThirdOrderAuditVO vo) throws Exception {
		OrderMainInfo orderMainInfo = null;
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
						+ " WHERE f.prescription_type='1' and g.audit_status in ('"+OrderInfo.ORDER_AUDIT_STATUS_PRESUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_SUCC+"','"+OrderInfo.ORDER_AUDIT_STATUS_RETURN+"') ");
		String orderNumber = vo.getOrderNumber()!=null && vo.getOrderNumber().length()>0 ? vo.getOrderNumber().trim() : "";
		String orderFlag = vo.getOrderFlag()!=null && vo.getOrderFlag().length()>0 ? vo.getOrderFlag().trim() : "";
		if(orderNumber.length()>0 && orderFlag.length()>0){
			sql.append(" and g.order_number = '"+vo.getOrderNumber()+"' and g.order_flag = '"+vo.getOrderFlag()+"' ");
			orderMainInfo = jdbcTemplate.queryForObject(sql.toString(), BeanPropertyRowMapper.newInstance(OrderMainInfo.class));
			if(orderMainInfo!=null){
				orderMainInfo.setSecondAuditFlag(getSecondAuditFlag(orderMainInfo.getOrderFlag()));
			}
			return orderMainInfo;
		}else{
			return null;
		}
	}
	
	/**
	 * 判断是否需要二次审核  
	 * @param orderFlag
	 * @return
	 * @throws Exception
	 */
	public boolean getSecondAuditFlag(String orderFlag)  throws Exception{
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from param_config where param_type='operation_center_presc_audit' and param_name='second_presc_audit' and param_status='1' ");
		ParamConfig paramConfig = jdbcTemplate.queryForObject(sql.toString(), BeanPropertyRowMapper.newInstance(ParamConfig.class));
		if(orderFlag!=null && orderFlag.trim().length()>0){
			if(paramConfig!=null){
					String[] pcArr = paramConfig.getParamValue().split(",");
					if(pcArr!=null && pcArr.length>0){
						for(int j=0;j<pcArr.length;j++){
							if(pcArr[j]!=null && pcArr[j].trim().length()>0 && pcArr[j].equals(orderFlag)){
								flag = true;
							}
						}
					}
			}
		}
		return flag;
	}
}
