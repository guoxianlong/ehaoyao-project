package com.ehaoyao.opertioncenter.callerScreen.service;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.ehaoyao.ice.common.bean.OrderBean;
import com.ehaoyao.ice.common.bean.OrderGoodsBean;
import com.ehaoyao.ice.common.bean.OrderQueryParam;

public interface CallerScreenService {
	
	/**
	 * 获取官网订单
	 * @param orderSn  订单号
	 * @param status
	 *              订单状态（0为未付款、20：待发货、30：已发货、40：已完成（用户确认）、50：已取消。51 退换货（目前只有天猫数据，商城不提供功能操作。）,52 锁定状态（目前只针对京东，商城不提供功能操作。））
	 * @param phone  收货人手机
	 * @param paymentMethodType 支付方式 （0为线上支付，1为线下支付）
	 * @param orderType 订单类型（0、为普通类型，1、为代下单，2、为处方药，3、惠氏订单。4、金斯利安）
	 * @param pageIndex  第几页
	 * @param pageNumber 每页显示的条数
	 * @return
	 */
	public HashMap<String, Object> getOfficialOrder(OrderQueryParam orderQueryParam) throws Exception;
	
	/**
	 * 根据订单号查找商品明细
	 * @param orderSn
	 * @return
	 */
	public List<OrderGoodsBean> getGoodsDetail(String orderSn);

}
