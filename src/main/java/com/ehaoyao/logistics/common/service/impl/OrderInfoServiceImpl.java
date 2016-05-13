/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.ordercenter.OrderInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.OrderInfoService;
import com.ehaoyao.logistics.common.task.ExpressInfoInitTask;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月22日 下午2:30:02
 * 类说明
 */
@Transactional(value="transactionManagerOrderCenter")
@Service(value="orderInfoServiceImpl")
public class OrderInfoServiceImpl implements OrderInfoService {
	private static final Logger logger = Logger.getLogger(ExpressInfoInitTask.class);
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	/**
	 * 
	* @Description：根据物流中心妥投的运单，更新订单中心的order_info表orderStatus为s02的记录,更新字段为expire_time、order_status
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int writeBackUpdateOrderInfo(List<WayBillInfo> wayBillInfoList) throws Exception {
		int writeBackUpdateOrderInfo=0;
		try {
			writeBackUpdateOrderInfo = orderInfoMapper.writeBackUpdateOrderInfo(wayBillInfoList);
		} catch (Exception e) {
			logger.error("已妥投运单回写程序--回写到订单中心的程序出错！ 运单信息："+JSONArray.fromObject(wayBillInfoList), e);
			throw new Exception();
		}
		return writeBackUpdateOrderInfo;
	}

}
