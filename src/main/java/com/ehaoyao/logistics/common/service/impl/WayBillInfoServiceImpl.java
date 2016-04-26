/**
 * 
 */
package com.ehaoyao.logistics.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.common.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.common.mapper.ordercenter.OrderInfoMapper;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.common.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.common.service.OrderInfoService;
import com.ehaoyao.logistics.common.service.WayBillDetailService;
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
	private WayBillDetailMapper wayBillDetailMapper;
	@Autowired
	private WayBillDetailService wayBillDetailService;
	@Autowired 
	private OrderInfoService orderInfoService;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private DataSourceTransactionManager transactionManagerOrderCenter;
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
	* @Description:将S04的wayBillInfo的isWriteBack的字段更新为1			
	* @param @param wayBillInfoList
	* @param @return
	* @return int
	* @throws
	 */
	public int writeBackUpdateWayBillInfo(List<WayBillInfo> wayBillInfoList) throws Exception {
		int updateWayBillInfoCount =0;
		try {
			/*1、调用billinfo的业务层，更改isWriteBack为1*/
			ArrayList<WayBillInfo> needUpdateList = new ArrayList<WayBillInfo>();
			for (WayBillInfo wayBillInfo : wayBillInfoList) {
				wayBillInfo.setIsWriteback(1);
				needUpdateList.add(wayBillInfo);		
			}
			updateWayBillInfoCount = wayBillInfoMapper.updateWayBillInfoBatch(needUpdateList);
		} catch (Exception e) {
			logger.error("已妥投运单回写程序--回写到订单中心成功后,更新运单在物流中心的WayBillInfo表的isWriteBack字段为1的程序出错！ 运单信息："+JSONArray.fromObject(wayBillInfoList), e);
			throw new Exception();
		}
		return updateWayBillInfoCount;
	}	
}
