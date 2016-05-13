package com.ehaoyao.opertioncenter.decoctOrder.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.decoctOrder.dao.IDecoctOrderDao;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

@Repository
public class DecoctOrderDaoImpl implements IDecoctOrderDao {

	// 运营中心jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public int getCountOrderInfos(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM order_info ");
		sql.append(" WHERE prescription_type='0' and audit_status='SUCC' ");
		sql.append(getOrderDataSql(vo));
		Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		return count;
	}

	@Override
	public List<OrderInfo> getOrderInfos(PageModel<OrderInfo> pm, ThirdOrderAuditVO vo) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  order_id ,  order_number ,  order_flag ,  user_id ,  user_code ,  nick_name ,  user_name ,  prescription_type ,  order_status , "
				+ " audit_status ,  pay_status ,  order_type ,  pay_type ,  order_time ,  price ,  receiver ,  audit_user ,  patient_name ,  sex ,  age , "
				+ " pregnant_flag ,  decoct_flag ,  dose ,  small_pic_link ,  big_pic_link ,  payment_time ,  audit_time ,  delivery_date ,hospital_presc_date,  address_detail , "
				+ " mobile ,  telephone ,  province ,  city ,  country ,  order_price ,  discount_amount ,  instructions ,  audit_description ,  remark , "
				+ " fee_type ,  kf_account ,  last_time ,  create_time  ");
		sql.append(" FROM order_info");
		sql.append(" WHERE prescription_type='0' and audit_status='SUCC' ");
		List<OrderInfo> list = null;
		sql.append(getOrderDataSql(vo));
		sql.append(" order by order_time desc ");
		if (pm.getPageSize() > 0) {
			sql.append(" limit " + pm.getFirstResult() + "," + pm.getPageSize());
		}
		list = jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(OrderInfo.class));
		return list;
	}

	@Override
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo)  throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  order_detail_id ,  order_number , order_flag, merchant_id ,  product_id ,  product_name ,  total_price ,  product_spec ,  product_brand ,"
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
				sql.append(" and TRIM(BOTH ' ' from order_number) = '"+vo.getOrderNumber()+"' ");
			}
			if (vo.getOrderStatus() != null && vo.getOrderStatus().trim().length() > 0 && !"-1".equals(vo.getOrderStatus().trim())) {
				sql.append(" and order_status = '"+vo.getOrderStatus().trim()+"'");
			}
			if (vo.getStartDate() != null && vo.getStartDate().trim().length() > 0) {
				sql.append(" and date_format(order_time,'%Y-%m-%d') >= date_format('"+vo.getStartDate().trim()+"','%Y-%m-%d')");
			}
			if (vo.getEndDate() != null && vo.getEndDate().trim().length() > 0) {
				sql.append(" and date_format(order_time,'%Y-%m-%d') <= date_format('"+vo.getEndDate().trim()+"','%Y-%m-%d')");
			}
			if(vo.getAuditStatus() != null && vo.getAuditStatus().trim().length() > 0 && !"-1".equals(vo.getAuditStatus().trim())){
				sql.append(" and audit_status = '"+vo.getAuditStatus().trim()+"' ");
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
				+ "  audit_status ,  pay_status ,  order_type ,  pay_type ,  order_time ,  price ,  receiver ,  audit_user ,  patient_name ,  sex ,  age , "
				+ " pregnant_flag ,  decoct_flag ,  dose ,  small_pic_link ,  big_pic_link ,  payment_time ,  audit_time ,  delivery_date ,hospital_presc_date,  address_detail , "
				+ " mobile ,  telephone ,  province ,  city ,  country ,  order_price ,  discount_amount ,  instructions ,  audit_description ,  remark , "
				+ " fee_type ,  kf_account ,  last_time ,  create_time "
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
				+ " delivery_date=?, address_detail=?, mobile=?, telephone=?, province=?, city=?, country=?, order_price=?, discount_amount=?,"
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
	
	
}
