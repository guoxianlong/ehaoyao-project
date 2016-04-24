/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.WaybillInfoService;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:09:38
 * 类说明
 */

@Service(value="waybillInfoService")
public class WaybillInfoServiceImpl implements WaybillInfoService {
	
	/**
	* @Description:按条件实体类WayBillInfoVo ,查询运单集合
	* @param @param wayBillInfoVo
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	@Override
	public List<WayBillInfo> queryWayBillInfoList(WayBillInfoVo wayBillInfoVo)
			throws Exception {
		
		return null;
	}
	
}
