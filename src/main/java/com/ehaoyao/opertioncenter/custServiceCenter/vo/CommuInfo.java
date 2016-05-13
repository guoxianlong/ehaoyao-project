package com.ehaoyao.opertioncenter.custServiceCenter.vo;

/**
 * 
 * Title: CommuInfo.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月4日 下午6:06:40
 */
public class CommuInfo {
	private Long id;

	private String tel;// 客户电话
	private String userName;// 客户姓名
	private String custNo;// 客户编号
	private String custSource;// 客户来源
	private String acceptResult;// 沟通类型一级分类
	private String secondType;// 沟通类型二级分类
	private String thirdType;// 沟通类型三级分类
	private String proCategory;// 品类类别
	private String depCategory;// 科组分类
	private String diseaseCategory;// 病种类别
	private String remark;// 备注
	private String consultDate;// 本次沟通时间，咨询时间
	private String custServCode;// 本次沟通客服工号
	private String isPlaceOrder;//是否成单
	private String isNewUser;//是否新用户
	private String createTime;//创建时间，首次咨询时间

	private String commuId;// 沟通记录表ID
	private String proKeywords;// 产品关键词
	private String proMealIds;// 关键词商品套餐ID
	private String proSkus;// 关键词商品SKU
	private String isOrder;// 是否订购
	private String noOrderCause;// 未订购原因
	private String isTrack;// 是否跟踪
	private String visitDate;// 回访日期
	private String trackInfo;// 跟踪信息
	private String createUser;// 跟踪信息创建人，客服

	// 查询参数，咨询日期
	private String startDate;
	private String endDate;
	private String isEnable;//是否可修改

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustSource() {
		return custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public String getAcceptResult() {
		return acceptResult;
	}

	public void setAcceptResult(String acceptResult) {
		this.acceptResult = acceptResult;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getProCategory() {
		return proCategory;
	}

	public void setProCategory(String proCategory) {
		this.proCategory = proCategory;
	}

	public String getDepCategory() {
		return depCategory;
	}

	public void setDepCategory(String depCategory) {
		this.depCategory = depCategory;
	}

	public String getDiseaseCategory() {
		return diseaseCategory;
	}

	public void setDiseaseCategory(String diseaseCategory) {
		this.diseaseCategory = diseaseCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}

	public String getCustServCode() {
		return custServCode;
	}

	public void setCustServCode(String custServCode) {
		this.custServCode = custServCode;
	}

	public String getIsPlaceOrder() {
		return isPlaceOrder;
	}

	public void setIsPlaceOrder(String isPlaceOrder) {
		this.isPlaceOrder = isPlaceOrder;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCommuId() {
		return commuId;
	}

	public void setCommuId(String commuId) {
		this.commuId = commuId;
	}

	public String getProKeywords() {
		return proKeywords;
	}

	public void setProKeywords(String proKeywords) {
		this.proKeywords = proKeywords;
	}

	public String getProMealIds() {
		return proMealIds;
	}

	public void setProMealIds(String proMealIds) {
		this.proMealIds = proMealIds;
	}

	public String getProSkus() {
		return proSkus;
	}

	public void setProSkus(String proSkus) {
		this.proSkus = proSkus;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getNoOrderCause() {
		return noOrderCause;
	}

	public void setNoOrderCause(String noOrderCause) {
		this.noOrderCause = noOrderCause;
	}

	public String getIsTrack() {
		return isTrack;
	}

	public void setIsTrack(String isTrack) {
		this.isTrack = isTrack;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

}
