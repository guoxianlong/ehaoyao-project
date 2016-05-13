package com.ehaoyao.opertioncenter.doctorInHospital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @description: 医生数据表
 * @author HuangXiaoxiao Email:huangxiaoxiao@ehaoyao.com
 * @date 2015年9月17日 下午5:50:42
 * @version V1.0
 */
@Entity
@Table(name = "doctor")
public class DoctorModel implements Serializable {


	private static final long serialVersionUID = -7746802562416784952L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false)
	private int id;
	/**
	 * 医生状态，"0"：无效  "1": 有效 
	 */
	@Column(name="status",columnDefinition="char(1) default 1")
	private String status="1";
	/**
	 * 医生名称
	 */
	@Column(name="doctor_name")
	private String doctorName;
	
	/**
	 * 医生名称拼音
	 */
	@Column(name="doctor_name_pinyin")
	private String doctorNamePinYin;
	 
	/** 
	 * 医生邮箱
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * 所在医院
	 */
	@Column(name="sales_rep_id")
	private String salesRepId;
	
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

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getSalesRepId() {
		return salesRepId;
	}

	public void setSalesRepId(String salesRepId) {
		this.salesRepId = salesRepId;
	}

	@Override
	public String toString() {
		return "DoctorModel [id=" + id + ", doctorName=" + doctorName
				+ ", email=" + email + ", salesRepId=" + salesRepId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

	public String getDoctorNamePinYin() {
		return doctorNamePinYin;
	}

	public void setDoctorNamePinYin(String doctorNamePinYin) {
		this.doctorNamePinYin = doctorNamePinYin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
