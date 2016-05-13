
package com.ehaoyao.logistics.jd.service;

import java.util.Date;
import java.util.List;

import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:07:46
 * 类说明
 */
public interface JDWayBillInfoService {
	/**
	 * 
	* @Description:查询 date天内、waybillSource 、waybillStatus多个状态的运单号集合
	* @param @return
	* @return List<String>
	* @throws
	 */
	public List<String> getWaybillNumbers(int date,String waybillSource,List<String> waybillStatusList)throws Exception;
	/**
	 * 
	* @Description:调用JDAPI,更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param waybillNumber
	* @param @return
	* @return 
	* @throws
	 */
	public int updateWaybillByJD(String waybillNumber) throws Exception;
	/**
	* @Description:根据运单号集合，调用JDAPI,批量更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param waybillNumberList
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	public int updateWaybillByJD(List<String> waybillNumberList ) throws Exception;
	public int updateWaybillInfoListByJD(List<WayBillInfo> wayBillInfoList ) throws Exception;
	/**
	 * 
	* @Description:查询startDate~endDate时间内、waybillSource 、waybillStatus多个状态的运单集合
	* @param @param date
	* @param @param waybillSource
	* @param @param waybillStatusList
	* @param @return
	* @param @throws Exception
	* @return List<String>
	* @throws
	 */
	public List<WayBillInfo> queryWayBillInfoList(Date startDate,Date endDate,String waybillSource,List<String> waybillStatusList)throws Exception;
}
