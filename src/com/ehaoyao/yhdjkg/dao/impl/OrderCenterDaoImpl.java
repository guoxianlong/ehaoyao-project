package com.ehaoyao.yhdjkg.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.ehaoyao.yhdjkg.dao.IOrderCenterDao;
import com.ehaoyao.yhdjkg.model.order_center.ExpressInfo;
import com.ehaoyao.yhdjkg.model.order_center.InvoiceInfo;
import com.ehaoyao.yhdjkg.model.order_center.OrderDetail;
import com.ehaoyao.yhdjkg.model.order_center.OrderInfo;
import com.ehaoyao.yhdjkg.utils.StringUtils;

@Repository("orderCenterDaoImpl")
public class OrderCenterDaoImpl extends JdbcBaseDao implements IOrderCenterDao{

	private static Logger logger=LoggerFactory.getLogger(OrderCenterDaoImpl.class);

	@Override
	public void doSaveOrder(OrderInfo orderInfo) {
		String sql_order=SQLConstants.doSaveOrderInfo;
//		original_id,order_number,start_time,expire_time,remark,pay_type,price,receiver,
//		address_detail,mobile,telephone,delivery_date,province,city,country,order_price,
//		discount_amount,express_price,order_flag,over_return_free,nick_name,address_alias,
//		app_signature,order_status,fee_type,payment_time,kf_account
		Object[] order={
				orderInfo.getOriginalId(),
				orderInfo.getOrderNumber(),
				orderInfo.getStartTime(),
				orderInfo.getExpireTime(),
				orderInfo.getRemark(),
				orderInfo.getPayType(),
				orderInfo.getPrice(),
				orderInfo.getReceiver(),
				orderInfo.getAddressDetail(),
				orderInfo.getMobile(),
				orderInfo.getTelephone(),
				orderInfo.getDeliveryDate(),
				orderInfo.getProvince(),
				orderInfo.getCity(),
				orderInfo.getCountry(),
				orderInfo.getOrderPrice(),
				orderInfo.getDiscountAmount(),
				orderInfo.getExpressPrice(),
				orderInfo.getOrderFlag(),
				orderInfo.getOverReturnFree(),
				orderInfo.getNickName(),
				orderInfo.getAddressAlias(),
				orderInfo.getAppSignature(),
				orderInfo.getOrderStatus(),
				orderInfo.getFeeType(),
				orderInfo.getPayMentTime(),
				orderInfo.getKfAccount()
				};
		orderJdbcTemplate.update(sql_order, order);
	}

	@Override
	public void doSaveOrderDetail(final List<OrderDetail> orderDetails) {
		
		String sql_order_detail=SQLConstants.doSaveOrderDetailInfo;
//		order_number,merchant_id,total_price,count,product_id,unit,product_name,price,order_flag,djbh_id,eflag,spId,discount_amount
		orderJdbcTemplate.batchUpdate(sql_order_detail, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderDetail orderDetail=orderDetails.get(i);
				int j= 1;
				ps.setString(j++, orderDetail.getOrderNumber());
				ps.setString(j++, orderDetail.getMerchantId());
				ps.setDouble(j++, orderDetail.getTotalPrice());
				ps.setDouble(j++, orderDetail.getCount());
				ps.setString(j++, orderDetail.getProductId());
				ps.setString(j++, orderDetail.getUnit());
				ps.setString(j++, orderDetail.getProductName());
				ps.setDouble(j++, orderDetail.getPrice());
				ps.setString(j++, orderDetail.getOrderFlag());
				ps.setString(j++, orderDetail.getDjbh_id());
				ps.setString(j++, orderDetail.getEflag());
				ps.setString(j++, orderDetail.getSpId());
				ps.setObject(j++, orderDetail.getDisTotalPrice());
			}
			
			@Override
			public int getBatchSize() {
				return orderDetails.size();
			}
		});
	}

	@Override
	public void doSaveInvoice(InvoiceInfo invoiceInfo) {
		String sql_invoice=SQLConstants.doSaveInvoiceInfo;
//		order_number,invoice_title,invoice_content,order_price,
//		invoice_type,remark,order_flag
		Object[] invoice={
				invoiceInfo.getOrderNumber(),
				invoiceInfo.getInvoiceTitle(),
				invoiceInfo.getInvoiceContent(),
				invoiceInfo.getOrderPrice(),
				invoiceInfo.getInvoiceType(),
				invoiceInfo.getRemark(),
				invoiceInfo.getOrderFlag()
		};
		orderJdbcTemplate.update(sql_invoice, invoice);
	}

	@Override
	public void doSaveExpress(ExpressInfo expressInfo) {
		String sql_express=SQLConstants.doSaveExpressInfo;
//		express_id,express_com_name,order_number,express_price,delivery_date_type,delivery_type,
//		express_status,delivery_date,delivery_notice,remark,distribution_center_name,picking_code,
//		distribution_station_name,products_count,outbound_time,order_flag,start_time,shuold_pay,pay_type
		Object[] express={
				expressInfo.getExpressId(),
				expressInfo.getExpressComName(),
				expressInfo.getOrderNumber(),
				expressInfo.getExpressPrice(),
				expressInfo.getDeliveryDateType(),
				expressInfo.getDeliveryType(),
				expressInfo.getExpressStatus(),
				expressInfo.getDeliveryDate(),
				expressInfo.getDeliveryNotice(),
				expressInfo.getRemark(),
				expressInfo.getDistributionCenterName(),
				expressInfo.getPickingCode(),
				expressInfo.getDistributionStationName(),
				expressInfo.getProductsCount(),
				expressInfo.getOutboundTime(),
				expressInfo.getOrderFlag(),
				expressInfo.getStartTime(),
				expressInfo.getShuoldPay(),
				expressInfo.getPayType()
		};
		orderJdbcTemplate.update(sql_express, express);
	}

	@Override
	public boolean isExistsByOrderId(String orderNumber,String orderFlag) {
		String sql=SQLConstants.isExistsByOrderId;
		try {
			String orderNumberStr = orderJdbcTemplate.queryForObject(sql, String.class, orderNumber,orderFlag);
			if(StringUtils.isNotEmpty(orderNumberStr)){
				return true;
			}
		} catch (Exception e) {
			logger.info("新订单将要入库标识为:{}的订单号:{}",orderFlag,orderNumber);
			return false;
		}
		return false;
	}

	@Override
	public void doSaveBuyerInfo(OrderInfo orderInfo) {
		String sql=SQLConstants.doSaveBuyerInfo;
//		order_number,buyer_alipay_no,buyer_email,buyer_nick,order_flag
		Object[] obj={
				orderInfo.getOrderNumber(),
				orderInfo.getTm_buyer_alipay_no(),
				orderInfo.getTm_buyer_email(),
				orderInfo.getTm_buyer_nick(),
				orderInfo.getOrderFlag()
				};
		orderJdbcTemplate.update(sql, obj);
		
	}
	
	
	@Override
	public boolean findOrderByOrderId(String orderNumber, String orderFlag) {
		logger.info("当前需要在数据库中查询的订单为:["+orderNumber+"],其订单标示为:"+orderFlag);
		String sql=SQLConstants.isExistsByOrderId;
		try {
			//String orderNumberStr = orderJdbcTemplate.queryForObject(sql, String.class, orderNumber,orderFlag);
			List<String> orderNumberList = orderJdbcTemplate.queryForList(sql, new Object[]{orderNumber,orderFlag},String.class);
			if(orderNumberList!=null&&!orderNumberList.isEmpty()){
				logger.info("从数据库中查找到的订单数量为:"+orderNumberList.size());
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据库订单编号为:["+orderNumber+"]过程中发生异常,异常信息为:"+e);
			return false;
		}
	}

	
}
