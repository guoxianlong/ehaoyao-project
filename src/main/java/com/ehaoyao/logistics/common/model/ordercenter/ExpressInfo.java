package com.ehaoyao.logistics.common.model.ordercenter;
// Generated 2016-4-13 22:30:12 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单中心,物流表实体映射表
 */
@Entity
@Table(name = "express_info")
public class ExpressInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112740078126174810L;
	/**
	 * 物流表主键
	 */
	private Long id;
	/**
	 * 运单号
	 */
	private String expressId;
	/**
	 * 运单公司id
	 */
	private String expressComId;
	/**
	 * 运单公司编码
	 */
	private String expressComCode;
	/**
	 * 运单公司名称
	 */
	private String expressComName;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 运费
	 */
	private Double expressPrice;
	/**
	 * 配送日期类型
	 */
	private String deliveryDateType;
	/**
	 * 配送类型
	 */
	private String deliveryType;
	/**
	 * 物流状态
	 */
	private String expressStatus;
	/**
	 * 配送日期
	 */
	private String deliveryDate;
	/**
	 * 配送提醒
	 */
	private String deliveryNotice;
	/**
	 * 备注
	 */
	private String remark;
	private String distributionCenterName;
	private String pickingCode;
	private String distributionStationName;
	private int productsCount;
	private String outboundTime;
	/**
	 * 渠道标识
	 */
	private String orderFlag;
	private String jdTradeNo;
	/**
	 * 订单时间
	 */
	private String startTime;
	private String shuoldPay;
	/**
	 * 支付类型
	 */
	private String payType;

	public ExpressInfo() {
	}

	public ExpressInfo(String orderNumber, int productsCount, String orderFlag) {
		this.orderNumber = orderNumber;
		this.productsCount = productsCount;
		this.orderFlag = orderFlag;
	}

	public ExpressInfo(String expressId, String expressComId, String expressComCode, String expressComName,
			String orderNumber, Double expressPrice, String deliveryDateType, String deliveryType, String expressStatus,
			String deliveryDate, String deliveryNotice, String remark, String distributionCenterName,
			String pickingCode, String distributionStationName, int productsCount, String outboundTime,
			String orderFlag, String jdTradeNo, String startTime, String shuoldPay, String payType) {
		this.expressId = expressId;
		this.expressComId = expressComId;
		this.expressComCode = expressComCode;
		this.expressComName = expressComName;
		this.orderNumber = orderNumber;
		this.expressPrice = expressPrice;
		this.deliveryDateType = deliveryDateType;
		this.deliveryType = deliveryType;
		this.expressStatus = expressStatus;
		this.deliveryDate = deliveryDate;
		this.deliveryNotice = deliveryNotice;
		this.remark = remark;
		this.distributionCenterName = distributionCenterName;
		this.pickingCode = pickingCode;
		this.distributionStationName = distributionStationName;
		this.productsCount = productsCount;
		this.outboundTime = outboundTime;
		this.orderFlag = orderFlag;
		this.jdTradeNo = jdTradeNo;
		this.startTime = startTime;
		this.shuoldPay = shuoldPay;
		this.payType = payType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "express_id")
	public String getExpressId() {
		return this.expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	@Column(name = "express_com_id", length = 100)
	public String getExpressComId() {
		return this.expressComId;
	}

	public void setExpressComId(String expressComId) {
		this.expressComId = expressComId;
	}

	@Column(name = "express_com_code", length = 30)
	public String getExpressComCode() {
		return this.expressComCode;
	}

	public void setExpressComCode(String expressComCode) {
		this.expressComCode = expressComCode;
	}

	@Column(name = "express_com_name")
	public String getExpressComName() {
		return this.expressComName;
	}

	public void setExpressComName(String expressComName) {
		this.expressComName = expressComName;
	}

	@Column(name = "order_number", nullable = false)
	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "express_price", precision = 22, scale = 0)
	public Double getExpressPrice() {
		return this.expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	@Column(name = "delivery_date_type")
	public String getDeliveryDateType() {
		return this.deliveryDateType;
	}

	public void setDeliveryDateType(String deliveryDateType) {
		this.deliveryDateType = deliveryDateType;
	}

	@Column(name = "delivery_type")
	public String getDeliveryType() {
		return this.deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@Column(name = "express_status")
	public String getExpressStatus() {
		return this.expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	@Column(name = "delivery_date")
	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "delivery_notice", length = 4)
	public String getDeliveryNotice() {
		return this.deliveryNotice;
	}

	public void setDeliveryNotice(String deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}

	@Column(name = "remark", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "distribution_center_name")
	public String getDistributionCenterName() {
		return this.distributionCenterName;
	}

	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}

	@Column(name = "picking_code")
	public String getPickingCode() {
		return this.pickingCode;
	}

	public void setPickingCode(String pickingCode) {
		this.pickingCode = pickingCode;
	}

	@Column(name = "distribution_station_name")
	public String getDistributionStationName() {
		return this.distributionStationName;
	}

	public void setDistributionStationName(String distributionStationName) {
		this.distributionStationName = distributionStationName;
	}

	@Column(name = "products_count", nullable = false)
	public int getProductsCount() {
		return this.productsCount;
	}

	public void setProductsCount(int productsCount) {
		this.productsCount = productsCount;
	}

	@Column(name = "outbound_time")
	public String getOutboundTime() {
		return this.outboundTime;
	}

	public void setOutboundTime(String outboundTime) {
		this.outboundTime = outboundTime;
	}

	@Column(name = "order_flag", nullable = false)
	public String getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	@Column(name = "jd_trade_no")
	public String getJdTradeNo() {
		return this.jdTradeNo;
	}

	public void setJdTradeNo(String jdTradeNo) {
		this.jdTradeNo = jdTradeNo;
	}

	@Column(name = "start_time", length = 50)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "shuold_pay", length = 50)
	public String getShuoldPay() {
		return this.shuoldPay;
	}

	public void setShuoldPay(String shuoldPay) {
		this.shuoldPay = shuoldPay;
	}

	@Column(name = "pay_type", length = 50)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
