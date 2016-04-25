/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.ordercenter.OrderInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.OrderInfoService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月22日 下午2:30:02
 * 类说明
 */
@Transactional(value="transactionManagerOrderCenter")
@Service(value="orderInfoServiceImpl")
public class OrderInfoServiceImpl implements OrderInfoService {
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	/**
	 * 
	* @Description：根据物流中心妥投的运单，更新订单中心的order_info表,更新字段为expire_time、order_status
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int writeBackUpdateOrderInfo(List<WayBillInfo> wayBillInfoList) throws Exception {
		return orderInfoMapper.writeBackUpdateOrderInfo(wayBillInfoList);
	}

}
