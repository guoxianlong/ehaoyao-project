package com.ehaoyao.cfy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.cfy.mapper.ordercenter.ExpressInfoMapper;
import com.ehaoyao.cfy.mapper.ordercenter.InvoiceInfoMapper;
import com.ehaoyao.cfy.mapper.ordercenter.OrderDetailMapper;
import com.ehaoyao.cfy.mapper.ordercenter.OrderDetailThirdPartyMapper;
import com.ehaoyao.cfy.mapper.ordercenter.OrderInfoMapper;
import com.ehaoyao.cfy.model.ordercenter.ExpressInfo;
import com.ehaoyao.cfy.model.ordercenter.InvoiceInfo;
import com.ehaoyao.cfy.model.ordercenter.OrderDetail;
import com.ehaoyao.cfy.model.ordercenter.OrderDetailThirdParty;
import com.ehaoyao.cfy.model.ordercenter.OrderInfo;
import com.ehaoyao.cfy.service.OrderCenterService;
import com.ehaoyao.cfy.vo.operationcenter.OrderInfoVo;
import com.ehaoyao.cfy.vo.operationcenter.OrderMainInfo;

@Transactional(value="transactionManagerOrderCenter")
@Service(value="orderCenterService")
public class OrderCenterServiceImpl implements OrderCenterService {
	
	@Autowired
	OrderInfoMapper orderInfoMapper;
	
	@Autowired
	OrderDetailMapper orderDetailMapper;
	
	@Autowired
	OrderDetailThirdPartyMapper orderDetailThirdPartyMapper;

	@Autowired
	InvoiceInfoMapper invoiceInfoMapper;
	
	@Autowired
	ExpressInfoMapper expressInfoMapper;
	
	@Override
	public Object insertOrderCenter(List<OrderMainInfo> subList) throws Exception{
		int insCount = 0;
		if(subList==null ||subList.isEmpty()){
			return 0;
		}
		OrderMainInfo orderMainInfo;
		com.ehaoyao.cfy.model.operationcenter.OrderInfo orderInfoOperation;
		com.ehaoyao.cfy.model.operationcenter.InvoiceInfo invoiceInfoOperation;
		List<com.ehaoyao.cfy.model.operationcenter.OrderDetail> orderDetailOperationList;
		List<com.ehaoyao.cfy.model.operationcenter.OrderDetailThirdParty> orderDetailThirdPartyOperationList;
		OrderInfo orderInfo;
		InvoiceInfo invoiceInfo;
		ExpressInfo expressInfo;
		OrderInfoVo vo;
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		List<OrderDetailThirdParty> orderDetailThirdPartyList = new ArrayList<OrderDetailThirdParty>();
		List<InvoiceInfo> invoiceInfoList = new ArrayList<InvoiceInfo>();
		List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
		/**
		 * 循环处理每条订单信息
		 */
		for(int i=0;i<subList.size();i++){
			List<OrderDetail> orderDetailSubList = new ArrayList<OrderDetail>();
			List<OrderDetailThirdParty> orderDetailThirdPartySubList = new ArrayList<OrderDetailThirdParty>();
			orderMainInfo = subList.get(i);
			orderInfoOperation = orderMainInfo.getOrderInfo();
			orderDetailOperationList = orderMainInfo.getOrderDetailList();
			orderDetailThirdPartyOperationList = orderMainInfo.getOrderDetailThirdPartyList();
			invoiceInfoOperation = orderMainInfo.getInvoiceInfo();
			
			/**
			 * 判断订单中心中是否存在订单
			 */
			vo = new OrderInfoVo();
			vo.setOrderFlag(orderInfoOperation.getOrderFlag());
			vo.setOrderNumber(orderInfoOperation.getOrderNumber());
			orderInfo = orderInfoMapper.selectByCondition(vo);
			if(orderInfo!=null){
				continue;
			}
			
			/**
			 * 将运营中心实体数据转换为订单中心实体
			 */
			orderInfo = transOrderInfo(orderInfoOperation);
			orderDetailSubList = transOrderDetail(orderDetailOperationList,orderInfoOperation);
			if(!orderDetailThirdPartyOperationList.isEmpty()){
				orderDetailThirdPartySubList = transOrderDetailThirdParty(orderDetailThirdPartyOperationList);
			}
			expressInfo = transExpressInfo(orderInfoOperation);
			if ("1".equals(orderInfoOperation.getInvoiceStatus())) {
				invoiceInfo = transInvoiceInfo(invoiceInfoOperation,orderInfoOperation);
				invoiceInfoList.add(invoiceInfo);
			}
			
			/**
			 * 放入集合中批量提交
			 */
			orderInfoList.add(orderInfo);
			orderDetailList.addAll(orderDetailSubList);
			expressInfoList.add(expressInfo);
			if(!orderDetailThirdPartySubList.isEmpty()){
				orderDetailThirdPartyList.addAll(orderDetailThirdPartySubList);
			}
		}
		
		/**
		 * 插入数据库
		 */
		if(!orderInfoList.isEmpty()){
			insCount = orderInfoMapper.insertOrderInfoBatch(orderInfoList);
		}
		if(!orderDetailList.isEmpty()){
			orderDetailMapper.insertOrderDetailBatch(orderDetailList);
		}
		if(!invoiceInfoList.isEmpty()){
			invoiceInfoMapper.insertInvoiceInfoBatch(invoiceInfoList);
		}
		if(!expressInfoList.isEmpty()){
			expressInfoMapper.insertExpressInfoBatch(expressInfoList);
		}
		if(!orderDetailThirdPartyList.isEmpty()){
			orderDetailThirdPartyMapper.insertOrderDetailThirdPartyBatch(orderDetailThirdPartyList);
		}
		return insCount;
	}

	/**
	 * 运营中心订单商品套餐表数据转换为订单中心实体
	 * @param orderDetailThirdPartyOperationList
	 * @return
	 * @throws Exception
	 */
	private List<OrderDetailThirdParty> transOrderDetailThirdParty(
			List<com.ehaoyao.cfy.model.operationcenter.OrderDetailThirdParty> orderDetailThirdPartyOperationList) throws Exception  {
		List<OrderDetailThirdParty> orderDetailThirdPartyList = new ArrayList<OrderDetailThirdParty>();
		OrderDetailThirdParty orderDetailThirdParty;
		if(orderDetailThirdPartyOperationList==null || orderDetailThirdPartyOperationList.isEmpty()){
			return null;
		}
		for(int i=0;i<orderDetailThirdPartyOperationList.size();i++){
			orderDetailThirdParty = new OrderDetailThirdParty();
			com.ehaoyao.cfy.model.operationcenter.OrderDetailThirdParty orderDetailThirdPartyOperation = orderDetailThirdPartyOperationList.get(i);
			orderDetailThirdParty.setCount(orderDetailThirdPartyOperation.getCount());
			orderDetailThirdParty.setEhaoyaoGroupPrice(orderDetailThirdPartyOperation.getEhaoyaoGroupPrice());
			orderDetailThirdParty.setGroupId(orderDetailThirdPartyOperation.getGroupId()!=null?Integer.parseInt(orderDetailThirdPartyOperation.getGroupId()):null);
			orderDetailThirdParty.setGroupName(orderDetailThirdPartyOperation.getGroupName());
			orderDetailThirdParty.setOrderFlag(orderDetailThirdPartyOperation.getOrderFlag());
			orderDetailThirdParty.setOrderNumber(orderDetailThirdPartyOperation.getOrderNumber());
			orderDetailThirdParty.setThirdpartyGroupPrice(orderDetailThirdPartyOperation.getThirdpartyGroupPrice());
			orderDetailThirdParty.setThirdpartyTotalPrice(orderDetailThirdPartyOperation.getThirdpartyTotalPrice());
			orderDetailThirdPartyList.add(orderDetailThirdParty);
		}
		
		return orderDetailThirdPartyList;
	}

	/**
	 * 运营中心物流表数据转换为订单中心实体
	 * @param orderInfoOperation
	 * @throws Exception
	 * @return
	 */
	private ExpressInfo transExpressInfo(com.ehaoyao.cfy.model.operationcenter.OrderInfo orderInfoOperation) throws Exception {
		ExpressInfo expressInfo = new ExpressInfo();
		expressInfo.setOrderFlag(orderInfoOperation.getOrderFlag());
		expressInfo.setOrderNumber(orderInfoOperation.getOrderNumber());
		expressInfo.setExpressPrice(orderInfoOperation.getExpressPrice()!=null?orderInfoOperation.getExpressPrice().doubleValue():null);
		expressInfo.setRemark(orderInfoOperation.getRemark());
		expressInfo.setStartTime(orderInfoOperation.getOrderTime());
		expressInfo.setProductsCount(orderInfoOperation.getProductCount());
		expressInfo.setPayType(com.ehaoyao.cfy.model.operationcenter.OrderInfo.getPayTypeDesc(orderInfoOperation.getPayType()));
		if("货到付款".equals(com.ehaoyao.cfy.model.operationcenter.OrderInfo.getPayTypeDesc(orderInfoOperation.getPayType()))){
			expressInfo.setShuoldPay(orderInfoOperation.getPrice()!=null?orderInfoOperation.getPrice().doubleValue()+"":null);
		}else{
			expressInfo.setShuoldPay("0");//网上支付0
		}
		return expressInfo;
	}

	/**
	 * 运营中心发票表数据转换为订单中心实体
	 * @param invoiceInfoOperation
	 * @throws Exception
	 * @return
	 */
	private InvoiceInfo transInvoiceInfo(
			com.ehaoyao.cfy.model.operationcenter.InvoiceInfo invoiceInfoOperation,com.ehaoyao.cfy.model.operationcenter.OrderInfo orderInfoOperation) throws Exception {
		InvoiceInfo invoiceInfo = new InvoiceInfo();
		invoiceInfo.setInvoiceContent(invoiceInfoOperation.getInvoiceContent());
		invoiceInfo.setInvoiceTitle(invoiceInfoOperation.getInvoiceTitle());
		invoiceInfo.setInvoiceType(com.ehaoyao.cfy.model.operationcenter.InvoiceInfo.getInvoiceTypeDesc(invoiceInfoOperation.getInvoiceType()));
		invoiceInfo.setOrderFlag(invoiceInfoOperation.getOrderFlag());
		invoiceInfo.setOrderNumber(invoiceInfoOperation.getOrderNumber());
		invoiceInfo.setOrderPrice(orderInfoOperation.getPrice()!=null?orderInfoOperation.getPrice().doubleValue():null);
		invoiceInfo.setRemark(invoiceInfoOperation.getRemark());
		return invoiceInfo;
	}

	/**
	 * 运营中心订单明细表数据转换为订单中心实体
	 * @param orderDetailOperationList
	 * @param orderInfoOperation 
	 * @throws Exception
	 * @return
	 */
	private List<OrderDetail> transOrderDetail(
			List<com.ehaoyao.cfy.model.operationcenter.OrderDetail> orderDetailOperationList, com.ehaoyao.cfy.model.operationcenter.OrderInfo orderInfoOperation) throws Exception {
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		OrderDetail orderDetail;
		int productCount=0;
		if(orderDetailOperationList==null || orderDetailOperationList.isEmpty()){
			return null;
		}
		for(int i=0;i<orderDetailOperationList.size();i++){
			orderDetail = new OrderDetail();
			com.ehaoyao.cfy.model.operationcenter.OrderDetail orderDetailOperation = orderDetailOperationList.get(i);
			productCount+=(orderDetailOperation.getCount()!=null?orderDetailOperation.getCount():0);
			orderDetail.setCount(orderDetailOperation.getCount()!=null?orderDetailOperation.getCount().doubleValue():null);
			orderDetail.setDiscountAmount(orderDetailOperation.getDiscountAmount()!=null?orderDetailOperation.getDiscountAmount()+"":null);
			orderDetail.setMerchantId(orderDetailOperation.getMerchantId()==null?"":orderDetailOperation.getMerchantId());
			orderDetail.setOrderFlag(orderDetailOperation.getOrderFlag());
			orderDetail.setOrderNumber(orderDetailOperation.getOrderNumber());
			orderDetail.setPrice(orderDetailOperation.getPrice()!=null?orderDetailOperation.getPrice().doubleValue():null);
			orderDetail.setProductId(orderDetailOperation.getProductId());
			orderDetail.setProductName(orderDetailOperation.getProductName());
			orderDetail.setTotalPrice(orderDetailOperation.getTotalPrice()!=null?orderDetailOperation.getTotalPrice().doubleValue():null);
			orderDetail.setUnit(orderDetailOperation.getUnit());
			orderDetailList.add(orderDetail);
		}
		orderInfoOperation.setProductCount(productCount);
		return orderDetailList;
	}

	/**
	 * 运营中心订单主表数据转换为订单中心实体
	 * @param orderInfoOperation
	 * @throws Exception
	 * @return
	 */
	private OrderInfo transOrderInfo(com.ehaoyao.cfy.model.operationcenter.OrderInfo orderInfoOperation) throws Exception{
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setAddressDetail(orderInfoOperation.getAddressDetail());
		orderInfo.setCity(orderInfoOperation.getCity());
		orderInfo.setCountry(orderInfoOperation.getArea());
		orderInfo.setDiscountAmount(orderInfoOperation.getDiscountAmount()!=null?orderInfoOperation.getDiscountAmount().doubleValue():null);
		orderInfo.setExpressPrice(orderInfoOperation.getExpressPrice()!=null?orderInfoOperation.getExpressPrice().doubleValue():null);
		orderInfo.setKfAccount(orderInfoOperation.getKfAccount());
		orderInfo.setMobile(orderInfoOperation.getMobile());
		orderInfo.setNickName(orderInfoOperation.getNickName());
		orderInfo.setOrderFlag(orderInfoOperation.getOrderFlag());
		orderInfo.setOrderNumber(orderInfoOperation.getOrderNumber());
		orderInfo.setOrderPrice(orderInfoOperation.getOrderPrice()!=null?orderInfoOperation.getOrderPrice().doubleValue():null);
		orderInfo.setOrderStatus(OrderInfo.ORDER_INFO_ORDER_STATUS_INIT);
		orderInfo.setOverReturnFree(orderInfoOperation.getOverReturnFree()!=null?orderInfoOperation.getOverReturnFree().toString():null);
		orderInfo.setPaymentTime(orderInfoOperation.getPaymentTime());
		orderInfo.setPayType(com.ehaoyao.cfy.model.operationcenter.OrderInfo.getPayTypeDesc(orderInfoOperation.getPayType()));
		orderInfo.setPrice(orderInfoOperation.getPrice()!=null?orderInfoOperation.getPrice().doubleValue():null);
		orderInfo.setFeeType(orderInfoOperation.getPlatPayPrice()!=null?orderInfoOperation.getPlatPayPrice()+"":null);//平台支付金额
		orderInfo.setProvince(orderInfoOperation.getProvince());
		orderInfo.setReceiver(orderInfoOperation.getReceiver());
		orderInfo.setRemark(orderInfoOperation.getRemark());
		orderInfo.setStartTime(orderInfoOperation.getOrderTime());
		orderInfo.setTelephone(orderInfoOperation.getTelephone());
//		orderInfo.setToErp("0");
		return orderInfo;
	}


}
