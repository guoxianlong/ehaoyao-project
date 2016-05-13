package com.ehaoyao.opertioncenter.auditOrder.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 平安订单详细信息
 * @author xushunxing
 *
 */
public class PABizOrderDetailDO {

  private int actualTotalFee;
  //平安药房订单号
  private long bizOrderId;
  //订单状态
  private int bizType;
  //购买数量
  private int buyAmount;
  private long buyerAcceptTime;
  //购买人id
  private long buyerId;
  private long carrierAcceptTime;
  private int carrierId;
  private String carrierNick;

  private List<PADrugDO> drugDOList;

  private String gmtCreated;
  private long gmtModified;
  private int isMsgSuccess;
  private int isPrescribed;
  private long itemId;
  private String itemTitle;
  private int logisticsStatus;
  private long merchantId;
  private String merchantNick;
  //价格详情
  private PAOrderPriceDO orderPriceDO;
  
  private String outId;
  private int payStatus;
  private long payTime;
  private int recipeId;
  private int refundStatus;
  private long sellerAcceptTime;
  private long sellerId;

  
  
  public int getActualTotalFee() {
    return actualTotalFee;
  }

  public void setActualTotalFee(int actualTotalFee) {
    this.actualTotalFee = actualTotalFee;
  }

  public long getBizOrderId() {
    return bizOrderId;
  }

  public void setBizOrderId(long bizOrderId) {
    this.bizOrderId = bizOrderId;
  }

  public int getBizType() {
    return bizType;
  }

  public void setBizType(int bizType) {
    this.bizType = bizType;
  }

  public int getBuyAmount() {
    return buyAmount;
  }

  public void setBuyAmount(int buyAmount) {
    this.buyAmount = buyAmount;
  }

  public long getBuyerAcceptTime() {
    return buyerAcceptTime;
  }

  public void setBuyerAcceptTime(long buyerAcceptTime) {
    this.buyerAcceptTime = buyerAcceptTime;
  }

  public long getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(long buyerId) {
    this.buyerId = buyerId;
  }

  public long getCarrierAcceptTime() {
    return carrierAcceptTime;
  }

  public void setCarrierAcceptTime(long carrierAcceptTime) {
    this.carrierAcceptTime = carrierAcceptTime;
  }

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

  public List<PADrugDO> getDrugDOList() {
    return drugDOList;
  }

  public void setDrugDOList(List<PADrugDO> drugDOList) {
    this.drugDOList = drugDOList;
  }

  public String getGmtCreated() {
    return gmtCreated;
  }

  public void setGmtCreated(String gmtCreated) {
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  long num=Long.parseLong(gmtCreated); 
	  this.gmtCreated = sdf.format(new Date(num));
  }

  public long getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(long gmtModified) {
    this.gmtModified = gmtModified;
  }

  public int getIsMsgSuccess() {
    return isMsgSuccess;
  }

  public void setIsMsgSuccess(int isMsgSuccess) {
    this.isMsgSuccess = isMsgSuccess;
  }

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  public String getItemTitle() {
    return itemTitle;
  }

  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }

  public int getLogisticsStatus() {
    return logisticsStatus;
  }

  public void setLogisticsStatus(int logisticsStatus) {
    this.logisticsStatus = logisticsStatus;
  }

  public long getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(long merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantNick() {
    return merchantNick;
  }

  public void setMerchantNick(String merchantNick) {
    this.merchantNick = merchantNick;
  }

  public PAOrderPriceDO getOrderPriceDO() {
    return orderPriceDO;
  }

  public void setOrderPriceDO(PAOrderPriceDO orderPriceDO) {
    this.orderPriceDO = orderPriceDO;
  }

  public String getOutId() {
    return outId;
  }

  public void setOutId(String outId) {
    this.outId = outId;
  }

  public int getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(int payStatus) {
    this.payStatus = payStatus;
  }

  public long getPayTime() {
    return payTime;
  }

  public void setPayTime(long payTime) {
    this.payTime = payTime;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public int getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(int refundStatus) {
    this.refundStatus = refundStatus;
  }

  public long getSellerAcceptTime() {
    return sellerAcceptTime;
  }

  public void setSellerAcceptTime(long sellerAcceptTime) {
    this.sellerAcceptTime = sellerAcceptTime;
  }

  public long getSellerId() {
    return sellerId;
  }

  public void setSellerId(long sellerId) {
    this.sellerId = sellerId;
  }

  public int getIsPrescribed() {
	return isPrescribed;
  }

  public void setIsPrescribed(int isPrescribed) {
	this.isPrescribed = isPrescribed;
  }
  
}
