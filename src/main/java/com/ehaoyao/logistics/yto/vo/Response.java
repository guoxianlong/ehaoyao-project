package com.ehaoyao.logistics.yto.vo;

//

//此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
//请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
//在重新编译源模式时, 对此文件的所有修改都将丢失。
//生成时间: 2014.08.28 时间 10:20:39 AM CST 
//

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * anonymous complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
* &lt;complexType>
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element ref="{}success"/>
*         &lt;element ref="{}reason"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "success", "reason" })
@XmlRootElement(name = "Response")
public class Response {

	protected boolean success;
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "NCName")
	protected String reason;

	/**
	 * 获取success属性的值。
	 * 
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置success属性的值。
	 * 
	 */
	public void setSuccess(boolean value) {
		this.success = value;
	}

	/**
	 * 获取reason属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * 设置reason属性的值。
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReason(String value) {
		this.reason = value;
	}

}
