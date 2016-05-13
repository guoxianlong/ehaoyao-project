package com.haoyao.goods.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
* @ClassName: SalesPlatform 
* @Description: TODO(平台数据字典) 
* @author 马锐 bjmarui@126.com 
* @date 2014年3月19日 上午10:47:43 
*
 */
@Entity
@Table(name = "salesPlatform")
public class SalesPlatform implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	/**
	 * 平台名称
	 */
	@Column(name = "platformName")
	private String platformName;
	/**
	 * 平台编码
	 */
	@Column(name = "platformCode")
	private String platformCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformCode() {
		return platformCode;
	}
	
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
}
