package com.ehaoyao.opertioncenter.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Administrator
 * 会员信息
 */
@Entity
@Table(name = "member")
public class Member  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 官网ID
	 */
	@Column(name = "member_code",length=20)
	private String memberCode;
	
	/**
	 * 会员名称
	 */
	@Column(name = "member_name",length=25)
	private String memberName;
	
	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickName;
	
	/**
	 * 电话
	 */
	@Id
	@Column(name = "tel",length=13,nullable = false)
	private String tel;
	
	/**
	 * 性别
	 */
	@Column(name = "sex",columnDefinition="char",length=1)
	private String sex;
	
	/**
	 * 年龄
	 */
	@Column(name = "age",columnDefinition="int")
	private String age;
	
	/**
	 * 状态
	 */
	@Column(name = "member_status",columnDefinition="char",length=1)
	private String memberStatus;
	
	/**
	 * 态度
	 */
	@Column(name = "attitude",columnDefinition="char",length=1)
	private String attitude;
	
	/**
	 * 是否官网客户
	 */
	/*@Column(name = "is_official")
	private String isOfficial;
	*/
	
	/**
	 * 是否微信客户
	 */
	/*
	@Column(name = "is_wechat")
	private String isWechat;*/
	
	/**
	 * 是否下过订单
	 */
	@Column(name = "is_order",columnDefinition="char",length=1)
	private String isOrder;
	
	/**
	 * 省
	 */
	@Column(name = "province",length=20)
	private String province;
	
	/**
	 * 市
	 */
	@Column(name = "city",length=20)
	private String city;
	/**
	 * 区
	 */
	@Column(name = "county",length=50)
	private String county;
	/**
	 * qq
	 */
	@Column(name = "qq",length=15)
	private String qq;
	
	/**
	 * 邮箱
	 */
	@Column(name = "email",length=30)
	private String email;
	
	/**
	 * 地址
	 */
	@Column(name = "address",length=200)
	private String address;
	
	/**
	 * 健康状况
	 */
	@Column(name = "health")
	private String health;
	
	/**
	 * 说明
	 */
	@Column(name = "comment")
	private String comment;
	
	/**
	 * 创建者
	 */
	@Column(name = "create_user",length=25)
	private String createUser;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time",columnDefinition="datetime")
	private String createTime;
	
	/**
	 * 更新者
	 */
	@Column(name = "update_user",length=25)
	private String updateUser;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time",columnDefinition="datetime")
	private String updateTime;
	
	/**
	 * 会员头像URL
	 */
	@Column(name= "head_image_url",length = 50)
	private String headImageUrl;
	/**
	 * 专属客服
	 */
	@Column(name= "user_name",length = 50)
	private String userName;
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the memberStatus
	 */
	public String getMemberStatus() {
		return memberStatus;
	}
	/**
	 * @param memberStatus the memberStatus to set
	 */
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return the isOrder
	 */
	public String getIsOrder() {
		return isOrder;
	}
	/**
	 * @param isOrder the isOrder to set
	 */
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the attitude
	 */
	public String getAttitude() {
		return attitude;
	}
	/**
	 * @param attitude the attitude to set
	 */
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	/**
	 * @return the health
	 */
	public String getHealth() {
		return health;
	}
	/**
	 * @param health the health to set
	 */
	public void setHealth(String health) {
		this.health = health;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the headImageUrl
	 */
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	/**
	 * @param headImageUrl the headImageUrl to set
	 */
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}
	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	
}
