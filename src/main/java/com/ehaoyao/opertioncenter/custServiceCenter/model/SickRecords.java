package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * <p>Title: SickRecords</p>
 * <p>Description: 患病记录</p>
 * @author	zsj
 * @date	2015年1月5日下午1:56:12
 * @version 1.0
 */
@Entity
@Table(name = "sick_records")
public class SickRecords implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition="bigint", length=20, nullable = false)
	private Long id;
	/**
	 * 咨询日期
	 */
	@Column(name = "consult_date")
	private String consultDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConsultDate() {
		return consultDate;
	}
	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}
	public String getSickDescription() {
		return sickDescription;
	}
	public void setSickDescription(String sickDescription) {
		this.sickDescription = sickDescription;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
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
	public String getCfNumber() {
		return cfNumber;
	}
	public void setCfNumber(String cfNumber) {
		this.cfNumber = cfNumber;
	}
	public String getCfDoctor() {
		return cfDoctor;
	}
	public void setCfDoctor(String cfDoctor) {
		this.cfDoctor = cfDoctor;
	}
	/**
	 * 患病描述
	 */
	@Column(name = "sick_description")
	private String sickDescription;
	/**
	 * 就诊医院
	 */
	@Column(name = "hospital")
	private String hospital;
	/**
	 * 科组类别
	 */
	@Column(name = "dep_category")
	private String depCategory;
	/**
	 * 病种类别
	 */
	@Column(name = "disease_category")
	private String diseaseCategory;
	/**
	 * 处方编号
	 */
	@Column(name = "cf_number")
	private String cfNumber;
	/**
	 * 处方医生
	 */
	@Column(name = "cf_doctor")
	private String cfDoctor;

}
