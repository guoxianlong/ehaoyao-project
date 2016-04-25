
package com.ehaoyao.logistics.common.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:07:46
 * 类说明
 */
public interface WayBillInfoService {
	/**
	 * 
	* @Description:按条件实体类WayBillInfoVo ,查询运单集合
	* @param @param date
	* @param @param waybillSource
	* @param @param waybillStatusList
	* @param @return
	* @param @throws Exception
	* @return List<String>
	* @throws
	 */
	public List<WayBillInfo> queryWayBillInfoList(WayBillInfoVo wayBillInfoVo)throws Exception;
	/**
	 * 
	* @Description:将S04的运单回写到订单中心的order_info表,主要内容：S04运单对应在order_Info的订单，
	* 将此订单的expire_time更新为签收时间（物流中心的wayBillInfo的last_time）， order_status更新为S03.
	* 物流中心的wayBillInfo的isWriteBack标记为1				
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int writeBackToOrdercenter (List<WayBillInfo> wayBillInfoList) throws Exception;
}
