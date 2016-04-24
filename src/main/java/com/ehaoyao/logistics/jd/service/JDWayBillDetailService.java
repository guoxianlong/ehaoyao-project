/**
 * 
 */
package com.ehaoyao.logistics.jd.service;

import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:10:28
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public interface JDWayBillDetailService{
	/**
	 * 
	* @Description:保存单个运单跟踪信息
	* @param @param waybillDetail
	* @param @return
	* @return int
	* @throws
	 */
	int insertWayBillDetail (WayBillDetail waybillDetail)throws Exception;
	/**
	 * 
	* @Description:保存多个运单跟踪信息
	* @param @param waybillDetail
	* @param @return
	* @return int
	* @throws
	 */
	int insertWayBillDetail (List<WayBillDetail> waybillDetailList)throws Exception;
}
