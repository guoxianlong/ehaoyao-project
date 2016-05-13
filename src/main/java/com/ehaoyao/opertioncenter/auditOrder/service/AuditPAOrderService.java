package com.ehaoyao.opertioncenter.auditOrder.service;

import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderQueryParam;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderResponse;

/**
 * @author xushunxing 
 * @version 创建时间：2015年10月19日 下午4:06:40
 * 类说明
 */
public interface AuditPAOrderService {
	/**
	 * 
	* @Description:获取平安订单数据包装类
	* @param @param orderQueryParam
	* @param @return
	* @param @throws Exception
	* @return PAOrderResponse
	* @throws
	 */
	public PAOrderResponse getPAOrderResponse(PAOrderQueryParam paOrderQueryParam ) throws Exception;
	public String auditOrder(long bizOrderId,int result,String resultMsg ,String remark) throws Exception;
	
}
