package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 每笔订单详情
 * @author xushunxing
 *
 */
public class PAOrderDetailDO {
	//每笔订单
	private PABizOrderDetailDO bizOrderDetailDO;
	//Ems快递单信息
	private PAEmsInfoDO emsInfoDO;
	//发票信息
	private PAInvoiceDO invoiceDO;
	//收货人地址
	private PALgOrderInfoDO lgOrderInfoDO;
	//订单客户详细信息
	private PASnOrderDetailDO snOrderDetailDO;
	
	
	
	public PASnOrderDetailDO getSnOrderDetailDO() {
		return snOrderDetailDO;
	}

	public void setSnOrderDetailDO(PASnOrderDetailDO snOrderDetailDO) {
		this.snOrderDetailDO = snOrderDetailDO;
	}

	public PABizOrderDetailDO getBizOrderDetailDO() {
		return bizOrderDetailDO;
	}

	public void setBizOrderDetailDO(PABizOrderDetailDO bizOrderDetailDO) {
		this.bizOrderDetailDO = bizOrderDetailDO;
	}

	public PAEmsInfoDO getEmsInfoDO() {
		return emsInfoDO;
	}

	public void setEmsInfoDO(PAEmsInfoDO emsInfoDO) {
		this.emsInfoDO = emsInfoDO;
	}

	public PAInvoiceDO getInvoiceDO() {
		return invoiceDO;
	}

	public void setInvoiceDO(PAInvoiceDO invoiceDO) {
		this.invoiceDO = invoiceDO;
	}

	public PALgOrderInfoDO getLgOrderInfoDO() {
		return lgOrderInfoDO;
	}

	public void setLgOrderInfoDO(PALgOrderInfoDO lgOrderInfoDO) {
		this.lgOrderInfoDO = lgOrderInfoDO;
	}

	@Override
	public String toString() {
		return "OrderDetailDO [bizOrderDetailDO=" + bizOrderDetailDO
				+ ", emsInfoDO=" + emsInfoDO + ", invoiceDO=" + invoiceDO
				+ ", lgOrderInfoDO=" + lgOrderInfoDO + "]";
	}

}
