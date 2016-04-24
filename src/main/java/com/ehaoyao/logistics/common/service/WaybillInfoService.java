
package com.ehaoyao.logistics.common.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:07:46
 * 类说明
 */
public interface WaybillInfoService {
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
}
