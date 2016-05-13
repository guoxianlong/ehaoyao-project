package com.ehaoyao.opertioncenter.member.vo;

import java.io.Serializable;
/**
 * @author Administrator
 * 会员信息
 */
public class MemberVO  implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 会员编号
	 */
	private String memberCode;
	/**
	 * 会员名称
	 */
	private String memberName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 开始查询年龄
	 */
	private String startAge;
	/**
	 * 结束查询年龄
	 */
	private String endAge;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String distrct;
	/**
	 * 健康状况
	 */
	private String health;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 状态
	 */
	private String memberStatus;
	/**
	 * 态度
	 */
	private String attitude;
	/**
	 * pageno
	 */
	private String pageno;
	private String pageSize;
	private String recTotal;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 头像地址
	 */
	private String headImageUrl;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 是否分配客服
	 */
	private int isAllot;
	
	/**
	 * @return the isAllot
	 */
	public int getIsAllot() {
		return isAllot;
	}
	/**
	 * @param isAllot the isAllot to set
	 */
	public void setIsAllot(int isAllot) {
		this.isAllot = isAllot;
	}
	/**
	 * @return the distrct
	 */
	public String getDistrct() {
		return distrct;
	}
	/**
	 * @param distrct the distrct to set
	 */
	public void setDistrct(String distrct) {
		this.distrct = distrct;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * @return the pageno
	 */
	public String getPageno() {
		return pageno;
	}
	/**
	 * @param pageno the pageno to set
	 */
	public void setPageno(String pageno) {
		this.pageno = pageno;
	}
	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the recTotal
	 */
	public String getRecTotal() {
		return recTotal;
	}
	/**
	 * @param recTotal the recTotal to set
	 */
	public void setRecTotal(String recTotal) {
		this.recTotal = recTotal;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
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
	 * @return the startAge
	 */
	public String getStartAge() {
		return startAge;
	}
	/**
	 * @param startAge the startAge to set
	 */
	public void setStartAge(String startAge) {
		this.startAge = startAge;
	}
	/**
	 * @return the endAge
	 */
	public String getEndAge() {
		return endAge;
	}
	/**
	 * @param endAge the endAge to set
	 */
	public void setEndAge(String endAge) {
		this.endAge = endAge;
	}
}
