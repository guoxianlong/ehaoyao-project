package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Title: HealthRecord</p>
 * <p>Description: 健康档案</p>
 * @author	zsj
 * @date	2015年1月5日下午1:56:12
 * @version 1.0
 */
@Entity
@Table(name = "health_record")
public class HealthRecord implements Serializable{
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getIsMarriage() {
		return isMarriage;
	}
	public void setIsMarriage(String isMarriage) {
		this.isMarriage = isMarriage;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getMediInsuranceType() {
		return mediInsuranceType;
	}
	public void setMediInsuranceType(String mediInsuranceType) {
		this.mediInsuranceType = mediInsuranceType;
	}
	public String getMediInsuranceCard() {
		return mediInsuranceCard;
	}
	public void setMediInsuranceCard(String mediInsuranceCard) {
		this.mediInsuranceCard = mediInsuranceCard;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getAllergicHistory() {
		return allergicHistory;
	}
	public void setAllergicHistory(String allergicHistory) {
		this.allergicHistory = allergicHistory;
	}
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",columnDefinition="bigint", length=20, nullable = false)
	private Long id;
	/**
	 * 手机号
	 */
	@Column(name = "tel",length=13,nullable = false)
	private String tel;
	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 民族
	 */
	@Column(name = "nation")
	private String nation;
	/**
	 * 出生地
	 */
	@Column(name = "birth_place")
	private String birthPlace;
	/**
	 * 籍贯
	 */
	@Column(name = "native_place")
	private String nativePlace;
	/**
	 * 婚姻
	 */
	@Column(name = "is_marriage")
	private String isMarriage;
	/**
	 * 身份证号
	 */
	@Column(name = "id_card_number")
	private String idCardNumber;
	/**
	 * 医保类型
	 */
	@Column(name = "medi_insurance_type")
	private String mediInsuranceType;
	/**
	 * 医保卡号
	 */
	@Column(name = "medi_insurance_card")
	private String mediInsuranceCard;
	/**
	 * 单位
	 */
	@Column(name = "department")
	private String department;
	/**
	 * 职业
	 */
	@Column(name = "profession")
	private String profession;
	/**
	 * 家族史
	 */
	@Column(name = "family_history")
	private String familyHistory;
	/**
	 * 过敏史
	 */
	@Column(name = "allergic_history")
	private String allergicHistory;
	
}
