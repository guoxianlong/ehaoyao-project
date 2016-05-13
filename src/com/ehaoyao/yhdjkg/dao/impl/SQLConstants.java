package com.ehaoyao.yhdjkg.dao.impl;

/**
 * <p>文件名称: ISQLConstants.java </p>
 * <p>文件描述: TODO 本类描述 </p>
 * <p>版权所有: Copyright (c) 2016-04-07 XINGTAI-DREAM, LTD.CO </p>
 * <p>完成日期: 2016-04-07</p>
 * @author wls
 * @see 
 * @param
 * @since JDK1.7
 */
public final class SQLConstants {
	
	
	
	/**
	 * 看订单中心主表是否存在该数据
	 */
	public static final String isExistsByOrderId="select order_number from order_info where order_number=? and order_flag=?";
	
	
	/**
	 * 看订单中心主表是否存在该数据
	 */
	public static final String isExistsOrderByOrderId="select order_number from order_info where order_number=? and order_flag=?";
	
	/**
	 * 保存主订单表
	 */
	public static final String doSaveOrderInfo="insert into order_info (original_id,order_number,start_time,expire_time,remark,pay_type,price,receiver,address_detail,mobile,telephone,delivery_date,province,city,country,order_price,discount_amount,express_price,order_flag,over_return_free,nick_name,address_alias,app_signature,order_status,fee_type,payment_time,kf_account) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 保存订单明细表
	 */
	public static final String doSaveOrderDetailInfo="insert into order_detail (order_number,merchant_id,total_price,count,product_id,unit,product_name,price,order_flag,djbh_id,eflag,spId,discount_amount) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 保存发票信息表
	 */
	public static final String doSaveInvoiceInfo="insert into invoice_info(order_number,invoice_title,invoice_content,order_price,invoice_type,remark,order_flag) values(?,?,?,?,?,?,?)";
	
	/**
	 * 保存快递信息表
	 */
	public static final String doSaveExpressInfo="insert into express_info(express_id,express_com_name,order_number,express_price,delivery_date_type,delivery_type,express_status,delivery_date,delivery_notice,remark,distribution_center_name,picking_code,distribution_station_name,products_count,outbound_time,order_flag,start_time,shuold_pay,pay_type)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 保存买家信息表
	 */
	public static final String doSaveBuyerInfo="insert into buyer_info (order_number,buyer_alipay_no,buyer_email,buyer_nick,order_flag) values (?,?,?,?,?)";
	
	
	
	
}
