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
 *	医生url表
 * @author HuangXiaoxiao
 * @Email huangxiaoxiao@ehaoyao.com
 * @Date 2015年9月18日 下午3:49:06
 * @version V1.0
 */
@Entity
@Table(name="doctor_url")
public class DoctorUrlModel implements Serializable{
	private static final long serialVersionUID = 9079338478294767059L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false)
	private int id;
	
	/**
	 * 药品所属医生
	 */						
	@Column(name="doctor_id")
	private String doctorId;
	
	/**
	 * 医生药品url
	 */
	@Column(name="url")
	private String url;
	
	/**
	 * 二维码图片地址
	 */
	@Column(name="quick_response_code_url")
	private String quickResponseCodeUrl;
	
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


	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getQuickResponseCodeUrl() {
		return quickResponseCodeUrl;
	}

	public void setQuickResponseCodeUrl(String quickResponseCodeUrl) {
		this.quickResponseCodeUrl = quickResponseCodeUrl;
	}

	@Override
	public String toString() {
		return "DoctorUrlModel [id=" + id + ", doctorId=" + doctorId + ", url="
				+ url + ", quickResponseCodeUrl=" + quickResponseCodeUrl
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
}
