package com.ehaoyao.opertioncenter.custServiceCenter.vo;

/**
 * 
 * Title: Communication.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月26日 下午1:30:50
 */
public class CommunicationVO {
	private Long id;// ID
	private String tel;// 电话
	private String productName;// 购买商品名称
	private String acceptResult;// 受理结果
	private String remark;// 备注
	private String createUser;// 创建者 ，受理人

	// 日期
	private String startDate;
	private String endDate;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAcceptResult() {
		return acceptResult;
	}

	public void setAcceptResult(String acceptResult) {
		this.acceptResult = acceptResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
