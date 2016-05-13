package com.ehaoyao.opertioncenter.doctorInHospital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 销售代表数据和医院表
 * @author HuangXiaoxiao
 * @Email huangxiaoxiao@ehaoyao.com
 * @Date 2015年9月18日 下午3:21:56
 * @version V1.0
 */
@Entity
@Table(name = "sales_rep")
public class SalesRepModel implements Serializable {

	private static final long serialVersionUID = 1731348767307504641L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false)
	private int id;
	
	/**
	 * 医院状态，"0"：无效  "1": 有效 
	 */
	@Column(name="status",columnDefinition="char(1) default 1")
	private String status="1";
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 医院名称
	 */
	@Column(name="hospital_name")
	private String hospitalName;
	
	/**
	 * 医院名称
	 */
	@Column(name="hospital_name_pinyin")
	private String hospitalNamePinYin;

	/**
	 * 销售中心
	 */
	@Column(name = "sales_center")
	private String salesCenter;

	/**
	 * 省公司
	 */
	@Column(name = "provincial_company")
	private String provincialCompany;

	/**
	 * 办事处	 */
	@Column(name = "office")
	private String office;

	/**
	 * 代表名称
	 */
	@Column(name = "sales_rep_name")
	private String salesRepName;
	
	/**
	 *创建时间 
	 */
	@Column(name="create_time")
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name="update_time")
	private String updateTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSalesCenter() {
		return salesCenter;
	}

	public void setSalesCenter(String salesCenter) {
		this.salesCenter = salesCenter;
	}

	public String getProvincialCompany() {
		return provincialCompany;
	}

	public void setProvincialCompany(String provincialCompany) {
		this.provincialCompany = provincialCompany;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getSalesRepName() {
		return salesRepName;
	}

	public void setSalesRepName(String salesRepName) {
		this.salesRepName = salesRepName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalNamePinYin() {
		return hospitalNamePinYin;
	}

	public void setHospitalNamePinYin(String hospitalNamePinYin) {
		this.hospitalNamePinYin = hospitalNamePinYin;
	}

	
}
