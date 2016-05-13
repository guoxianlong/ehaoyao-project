package com.ehaoyao.opertioncenter.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_healthKeyword")
public class MemberHealthKeyword implements Serializable {

	/**
	 * 类的序列化
	 */
	private static final long serialVersionUID = -1L;
	
	/**
	 * ID
	 */
	@Id
	@Column(name="id",unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 主键，会员ID
	 */
	@Column(name="mid",nullable= false)
	private String mid;
	
	/**
	 * 主键，健康关键字ID
	 */
	@Column(name="pid",nullable= false)
	private String pid;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

}
