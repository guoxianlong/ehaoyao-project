/**
 * 
 */
package com.ehaoyao.logistics.common.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月22日 下午2:29:03
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public interface OrderInfoService {
	/**
	 * 
	* @Description：根据物流中心妥投的运单，更新订单中心的order_info表,更新字段为expire_time、order_status
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int writeBackUpdateOrderInfo (List<WayBillInfo> wayBillInfoList) throws Exception ;
}
