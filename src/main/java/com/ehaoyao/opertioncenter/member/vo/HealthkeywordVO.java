/**
 * 
 */
package com.ehaoyao.opertioncenter.member.vo;

import java.io.Serializable;
import java.util.List;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;

/**
 * @author Administrator
 *
 */
public class HealthkeywordVO implements Serializable {

	/**
	 * 类的序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 分类名称
	 */
	private String className;
	
	/**
	 * 创建者
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 更新者
	 */
	private String updateUser;
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	
	/**
	 * 健康关键字
	 */
	private List<HealthKeywordDetail> healthDetail;
	
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
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * @return the healthDetail
	 */
	public List<HealthKeywordDetail> getHealthDetail() {
		return healthDetail;
	}

	/**
	 * @param healthDetail the healthDetail to set
	 */
	public void setHealthDetail(List<HealthKeywordDetail> healthDetail) {
		this.healthDetail = healthDetail;
	}

}
