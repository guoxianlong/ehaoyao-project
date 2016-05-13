package com.ehaoyao.opertioncenter.auditOrder.model;
/**
 * 每笔订单的EMS快递信息
 * @author xushunxing
 *
 */
public class PAEmsInfoDO {
	private int carrierId;
	private String carrierNick;
	private String trackingNumber;

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getCarrierNick() {
		return carrierNick;
	}

	public void setCarrierNick(String carrierNick) {
		this.carrierNick = carrierNick;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

}
