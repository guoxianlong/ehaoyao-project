package com.ehaoyao.yhdjkg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.yhdjkg.dao.IOrderCenterDao;
import com.ehaoyao.yhdjkg.domain.InvoiceInfoPresc;
import com.ehaoyao.yhdjkg.domain.ParamsData;
import com.ehaoyao.yhdjkg.model.order_center.ExpressInfo;
import com.ehaoyao.yhdjkg.model.order_center.InvoiceInfo;
import com.ehaoyao.yhdjkg.model.order_center.OrderDetail;
import com.ehaoyao.yhdjkg.model.order_center.OrderInfo;
import com.ehaoyao.yhdjkg.service.IOrderCenterService;

@Service("orderCenterServiceImpl")
public class OrderCenterServiceImpl implements IOrderCenterService {

	private static Logger logger=LoggerFactory.getLogger(OrderCenterServiceImpl.class);
	
	
	@Resource
	private IOrderCenterDao orderCenterDaoImpl;
	
	


	/**
	 * 分页去商品中心查询审核通过的订单，然后将数据保存到当地中心
	 * @param channel  平台标识
	 * @param operation_secretKey 
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNo
	 */
	@Override
	@Transactional(rollbackFor=Exception.class) 
	public void doSaveOneCFYOrder(String channel, String operation_secretKey,
			String startTime, String endTime, int pageSize, int pageNo,
			ParamsData paramsData2) {
		Boolean isExist = orderCenterDaoImpl.isExistsByOrderId(paramsData2
				.getOrderInfo().getOrderNumber(), paramsData2.getOrderInfo()
				.getOrderFlag());
		if (isExist) {
			logger.info("订单号：{}，平台标识：{} 的订单已经存在。", paramsData2.getOrderInfo()
					.getOrderNumber(), paramsData2.getOrderInfo()
					.getOrderFlag());
		} else {
				OrderInfo orderInfo = getOrderInfo(paramsData2);
				List<OrderDetail> orderDetails = getOrderDetails(paramsData2);
				ExpressInfo expressInfo = getExpressInfo(paramsData2);
				InvoiceInfo invoiceInfo=getInvoiceInfo(paramsData2);
				orderCenterDaoImpl.doSaveOrder(orderInfo);
				orderCenterDaoImpl.doSaveOrderDetail(orderDetails);
				orderCenterDaoImpl.doSaveExpress(expressInfo);
				if(invoiceInfo!=null){
					orderCenterDaoImpl.doSaveInvoice(invoiceInfo);
				}
		}
	}


	/**
	 * 将数据转换为 ExpressInfo实体类（数据库对应表结构）
	 * @param paramsData（运营中心返回的对象）
	 * @return
	 */
	private ExpressInfo  getExpressInfo(ParamsData paramsData) {
		ExpressInfo expressInfo=new ExpressInfo();
		expressInfo.setOrderNumber(paramsData.getOrderInfo().getOrderNumber());
		expressInfo.setOrderFlag(paramsData.getOrderInfo().getOrderFlag());
		expressInfo.setExpressPrice(paramsData.getOrderInfo().getExpressPrice());
		expressInfo.setRemark(paramsData.getOrderInfo().getRemark());
		expressInfo.setStartTime(paramsData.getOrderInfo().getOrderTime());
		expressInfo.setProductsCount(paramsData.getOrderInfo().getProductCount());
		expressInfo.setPayType(paramsData.getOrderInfo().getPayTypeName());
		if("货到付款".equals(paramsData.getOrderInfo().getPayTypeName())){
			expressInfo.setShuoldPay(String.valueOf(paramsData.getOrderInfo().getPrice()));
		}else{
			expressInfo.setShuoldPay("0");//网上支付0
		}
		return expressInfo;
	}
	/**
	 * 将数据转换为 orderDetail实体类（数据库对应表结构）
	 * @param paramsData（运营中心返回的对象）
	 * @return
	 */
	private List<OrderDetail>  getOrderDetails(ParamsData paramsData) {
		List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
		List<com.ehaoyao.yhdjkg.domain.OrderDetail> details=paramsData.getDetails();
		int productCount=0;
		for (com.ehaoyao.yhdjkg.domain.OrderDetail detail : details) {
			OrderDetail orderDetail=new OrderDetail();
			productCount+=detail.getCount();
			orderDetail.setOrderNumber(paramsData.getOrderInfo().getOrderNumber());//订单号
			orderDetail.setCount(detail.getCount().doubleValue());//商品个数
			orderDetail.setPrice(detail.getPrice()==null?null:detail.getPrice().doubleValue());//价格
			orderDetail.setProductId(detail.getProductId());//商品id
			orderDetail.setMerchantId(detail.getProductId());//商品id
			orderDetail.setProductName(detail.getProductName());//商品名称
			orderDetail.setTotalPrice(detail.getTotalPrice()==null?null:detail.getTotalPrice().doubleValue());//总价
			orderDetail.setOrderFlag(paramsData.getOrderInfo().getOrderFlag());//订单标识
			orderDetail.setDisTotalPrice(detail.getDiscountAmount()==null?null:detail.getDiscountAmount().doubleValue());
			orderDetails.add(orderDetail);
		}
		paramsData.getOrderInfo().setProductCount(productCount);
		return orderDetails;
	}
	/**
	 * 将数据转换为 orderInfo实体类（数据库对应表结构）
	 * @param paramsData2（运营中心返回的对象）
	 * @return
	 */
	private OrderInfo getOrderInfo(ParamsData paramsData2) {
		OrderInfo orderInfo=new OrderInfo();
		com.ehaoyao.yhdjkg.domain.OrderInfo order=paramsData2.getOrderInfo();
		orderInfo.setOrderNumber(order.getOrderNumber());//订单号
		orderInfo.setOrderStatus("s00");//订单状态
		orderInfo.setStartTime(order.getOrderTime());//订单创建时间
		orderInfo.setRemark(order.getRemark());//备注
		orderInfo.setPayType(order.getPayTypeName());//付款类型
		orderInfo.setPrice(order.getPrice()==null?null:order.getPrice().doubleValue());//客户支付金额
		orderInfo.setReceiver(order.getReceiver());//联系人
		orderInfo.setAddressDetail(order.getAddressDetail());//详细地址
		orderInfo.setTelephone(order.getTelephone());//联系电话
		orderInfo.setMobile(order.getMobile());
		orderInfo.setProvince(order.getProvince());//省
		orderInfo.setCity(order.getCity());//市
		orderInfo.setCountry(order.getCountry());//区
		orderInfo.setOrderPrice(order.getOrderPrice()==null?null:order.getOrderPrice().doubleValue());//订单金额
		orderInfo.setDiscountAmount(order.getDiscountAmount()==null?null:order.getDiscountAmount().doubleValue());//优惠金额
		orderInfo.setOrderFlag(order.getOrderFlag());//订单标识
		orderInfo.setExpressPrice(order.getExpressPrice());//快递金额
		orderInfo.setOverReturnFree(String.valueOf(order.getOverReturnFree()));//满反满送金额
		orderInfo.setPayMentTime(order.getPaymentTime());//付款时间
		orderInfo.setFeeType(String.valueOf(order.getPlatPayPrice()));//平台支付金额
		orderInfo.setKfAccount(order.getKfAccount());
		return orderInfo;
	}
	/**
	 * 将数据转换为 InvoiceInfo实体类（数据库对应表结构）
	 * @param paramsData2（运营中心返回的对象）
	 * @return
	 */
	private InvoiceInfo getInvoiceInfo(ParamsData paramsData2) {
		InvoiceInfo invoiceInfo=new InvoiceInfo();
		InvoiceInfoPresc invoicePresc=paramsData2.getInvoiceInfo();
		logger.info("invoicePresc：{}",invoicePresc);
		if(invoicePresc==null){
			return null;
		}
		invoiceInfo.setOrderNumber(paramsData2.getOrderInfo().getOrderNumber());
		invoiceInfo.setOrderFlag(paramsData2.getOrderInfo().getOrderFlag());
		invoiceInfo.setInvoiceTitle(invoicePresc.getInvoiceTitle());
		invoiceInfo.setInvoiceContent(invoicePresc.getInvoiceContent());
		invoiceInfo.setInvoiceType(invoicePresc.getInvoiceTypeDesc());
		invoiceInfo.setRemark(invoicePresc.getRemark());
		invoiceInfo.setOrderPrice(paramsData2.getOrderInfo().getPrice().doubleValue());
		return invoiceInfo;
	}


	@Override
	public Boolean findOrderByOrderId(String tid, String shopAlias) {
		return orderCenterDaoImpl.findOrderByOrderId(tid,shopAlias);
		
	}


	



}
