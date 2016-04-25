/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.OrderInfoService;
import com.ehaoyao.logistics.common.service.WayBillInfoService;
import com.ehaoyao.logistics.common.vo.WayBillInfoVo;
import com.ehaoyao.logistics.jd.service.impl.JDWayBillInfoServiceImpl;
/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:09:38
 * 类说明
 */
@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="wayBillInfoService")
public class WayBillInfoServiceImpl implements WayBillInfoService {
	private static final Logger logger = Logger.getLogger(WayBillInfoServiceImpl.class);
	@Autowired
	private WayBillInfoMapper wayBillInfoMapper;
	@Autowired
	private OrderInfoService orderInfoService;
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
		return wayBillInfoMapper.selectWayBillInfoList(wayBillInfoVo);
	}
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
	public int writeBackToOrdercenter(List<WayBillInfo> wayBillInfoList) throws Exception {
		/*1、 调用orderinfo的业务层,将状态回写*/
		int updateOrderInfoCount =0;
		try {
			updateOrderInfoCount = orderInfoService.writeBackUpdateOrderInfo(wayBillInfoList);			
		} catch (Exception e) {
			logger.error("已妥投运单回写程序--更新订单中心订单数据程序出错！ 运单信息："+JSONArray.fromObject(wayBillInfoList), e);
			throw new Exception();
		}
		/*2、调用挖吖billinfo的业务层，更改isWriteBack为1*/
		ArrayList<WayBillInfo> needUpdateList = new ArrayList<WayBillInfo>();
		for (WayBillInfo wayBillInfo : wayBillInfoList) {
			wayBillInfo.setIsWriteback(1);
			needUpdateList.add(wayBillInfo);		
		}
		int updateWayBillInfoCount =0;
		try {
			updateWayBillInfoCount = wayBillInfoMapper.updateWayBillInfoBatch(needUpdateList);			
		} catch (Exception e) {
			logger.error("已妥投运单回写程序--回写到订单中心成功后,更新运单在物流中心的WayBillInfo表的isWriteBack字段为1的程序出错！ 运单信息："+JSONArray.fromObject(wayBillInfoList), e);
			throw new Exception();
		}	
		return updateWayBillInfoCount;
	}	
}
