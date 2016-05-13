package com.haoyao.goods.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="organization")
public class Organization extends IdEntity{

	/**
	 * 机构代码
	 */
	private String code;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 负责任
	 */
	private String manager;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
