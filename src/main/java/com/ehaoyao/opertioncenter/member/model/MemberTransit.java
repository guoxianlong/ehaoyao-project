package com.ehaoyao.opertioncenter.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Administrator
 * 任务信息
 */
@Entity
@Table(name = "member_transit")
public class MemberTransit implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "tel",length=50,nullable = false)
	private String tel;
	/**
	 * 专属客服
	 */
	@Column(name= "user_name",length = 50)
	private String userName;
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
	
}
