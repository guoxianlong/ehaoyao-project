/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.common.service.WayBillDetailService;
import com.ehaoyao.logistics.jd.service.impl.JDWayBillInfoServiceImpl;
/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:11:23
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="wayBillDetailService")
public class WayBillDetailServiceImpl implements WayBillDetailService {
	
	private static final Logger logger = Logger.getLogger(JDWayBillInfoServiceImpl.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	WayBillInfoMapper waybillInfoMapper;
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	/**
	* @Description:保存单个运单跟踪信息
	* @param @param waybillDetail
	* @param @return
	* @return 
	* @throws
	*/ 
	@Override
	public int insertWayBillDetail(WayBillDetail waybillDetail) throws Exception{	
		return wayBillDetailMapper.insert(waybillDetail);
	}
	/**
	* @Description:保存多个运单跟踪信息
	* @param @param waybillDetailList
	* @param @return
	* @param @throws Exception
	* @return 
	* @throws
	*/ 
	@Override
	public int insertWayBillDetail(List<WayBillDetail> waybillDetailList)
			throws Exception {
		return wayBillDetailMapper.insertWayBillDetailBatch(waybillDetailList);
	}
}
