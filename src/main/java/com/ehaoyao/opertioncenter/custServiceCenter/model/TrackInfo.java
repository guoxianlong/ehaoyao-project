package com.ehaoyao.opertioncenter.custServiceCenter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title: TrackInfo.java</p>
 * <p>Description: 跟踪信息</p>
 * @author	zsj
 * @date	2015年1月4日下午4:42:02
 * @version 1.0
 */
@Entity
@Table(name = "track_info")
public class TrackInfo implements Serializable{
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
	 * 沟通记录主表ID
	 */
	@Column(name = "commu_id",columnDefinition="bigint", length=20)
	private Long commuId;
	
	/**
	 * 本次沟通时间，咨询日期
	 */
	@Column(name = "consult_date")
	private String consultDate;
	
	/**
	 * 产品关键词
	 */
	@Column(name = "pro_keywords",length=500)
	private String proKeywords;
	
	/**
	 * 产品关键词商品sku
	 */
	@Column(name = "pro_skus",length=500)
	private String proSkus;
	
	/**
	 * 产品关键词对应的商品套餐ID
	 */
	@Column(name = "pro_meal_ids")
	private String proMealIds;
	
	/**
	 * 是否订购
	 */
	@Column(name = "is_order")
	private String isOrder;
	
	/**
	 * 未订购原因
	 */
	@Column(name = "no_order_cause")
	private String noOrderCause;
	
	/**
	 * 是否跟踪
	 */
	@Column(name = "is_track")
	private String isTrack;
	
	/**
	 * 下次沟通时间，预约回访，回访日期
	 */
	@Column(name = "visit_date")
	private String visitDate;
	
	/**
	 * 跟踪信息，备注
	 */
	@Column(name = "track_info")
	private String trackInfo;
	
	/**
	 * 创建者，客服
	 */
	@Column(name = "create_user")
	private String createUser;
	
	/**
	 * 创建人姓名，客服
	 */
	@Column(name = "create_user_name")
	private String createUserName;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private String createTime;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommuId() {
		return commuId;
	}

	public void setCommuId(Long commuId) {
		this.commuId = commuId;
	}

	public String getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}

	public String getProKeywords() {
		return proKeywords;
	}

	public void setProKeywords(String proKeywords) {
		this.proKeywords = proKeywords;
	}

	public String getProSkus() {
		return proSkus;
	}

	public void setProSkus(String proSkus) {
		this.proSkus = proSkus;
	}

	public String getProMealIds() {
		return proMealIds;
	}

	public void setProMealIds(String proMealIds) {
		this.proMealIds = proMealIds;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
