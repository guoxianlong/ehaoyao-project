/**
 * 
 */
package com.ehaoyao.logistics.common.vo;

import java.io.Serializable;

/**
 * @author xushunxing 
 * @version 创建时间：2016年5月6日 下午5:32:03
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public class LogisticsDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -211127479616639146L;

	/*
	 * This type was generated from the piece of schema that had name =
	 * LogisticsDetail Namespace URI =
	 * http://entity.logisticsServer.haoys.com/xsd Namespace Prefix = ns1
	 */

	/**
	 * field for Context
	 */

	protected java.lang.String localContext;

	/*
	 * This tracker boolean wil be used to detect whether the user called
	 * the set method for this attribute. It will be used to determine
	 * whether to include this field in the serialized XML
	 */
	protected boolean localContextTracker = false;

	public boolean isContextSpecified() {
		return localContextTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getContext() {
		return localContext;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Context
	 */
	public void setContext(java.lang.String param) {
		localContextTracker = true;

		this.localContext = param;

	}

	/**
	 * field for Id
	 */

	protected int localId;

	/*
	 * This tracker boolean wil be used to detect whether the user called
	 * the set method for this attribute. It will be used to determine
	 * whether to include this field in the serialized XML
	 */
	protected boolean localIdTracker = false;

	public boolean isIdSpecified() {
		return localIdTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getId() {
		return localId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Id
	 */
	public void setId(int param) {
		localIdTracker = true;

		this.localId = param;

	}

	/**
	 * field for ReceiptAddress
	 */

	protected java.lang.String localReceiptAddress;

	/*
	 * This tracker boolean wil be used to detect whether the user called
	 * the set method for this attribute. It will be used to determine
	 * whether to include this field in the serialized XML
	 */
	protected boolean localReceiptAddressTracker = false;

	public boolean isReceiptAddressSpecified() {
		return localReceiptAddressTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getReceiptAddress() {
		return localReceiptAddress;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ReceiptAddress
	 */
	public void setReceiptAddress(java.lang.String param) {
		localReceiptAddressTracker = true;

		this.localReceiptAddress = param;

	}

	/**
	 * field for ReceiptTime
	 */

	protected java.lang.String localReceiptTime;

	/*
	 * This tracker boolean wil be used to detect whether the user called
	 * the set method for this attribute. It will be used to determine
	 * whether to include this field in the serialized XML
	 */
	protected boolean localReceiptTimeTracker = false;

	public boolean isReceiptTimeSpecified() {
		return localReceiptTimeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getReceiptTime() {
		return localReceiptTime;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            ReceiptTime
	 */
	public void setReceiptTime(java.lang.String param) {
		localReceiptTimeTracker = true;

		this.localReceiptTime = param;

	}

	/**
	 * field for TrackingNumber
	 */

	protected java.lang.String localTrackingNumber;

	/*
	 * This tracker boolean wil be used to detect whether the user called
	 * the set method for this attribute. It will be used to determine
	 * whether to include this field in the serialized XML
	 */
	protected boolean localTrackingNumberTracker = false;

	public boolean isTrackingNumberSpecified() {
		return localTrackingNumberTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTrackingNumber() {
		return localTrackingNumber;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            TrackingNumber
	 */
	public void setTrackingNumber(java.lang.String param) {
		localTrackingNumberTracker = true;

		this.localTrackingNumber = param;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result
				+ ((localContext == null) ? 0 : localContext.hashCode());
		result = prime * result + (localContextTracker ? 1231 : 1237);
		result = prime * result + localId;
		result = prime * result + (localIdTracker ? 1231 : 1237);
		result = prime
				* result
				+ ((localReceiptAddress == null) ? 0 : localReceiptAddress
						.hashCode());
		result = prime * result + (localReceiptAddressTracker ? 1231 : 1237);
		result = prime
				* result
				+ ((localReceiptTime == null) ? 0 : localReceiptTime.hashCode());
		result = prime * result + (localReceiptTimeTracker ? 1231 : 1237);
		result = prime
				* result
				+ ((localTrackingNumber == null) ? 0 : localTrackingNumber
						.hashCode());
		result = prime * result + (localTrackingNumberTracker ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogisticsDetail other = (LogisticsDetail) obj;
		if (localContext == null) {
			if (other.localContext != null)
				return false;
		} else if (!localContext.equals(other.localContext))
			return false;
		if (localContextTracker != other.localContextTracker)
			return false;
		if (localId != other.localId)
			return false;
		if (localIdTracker != other.localIdTracker)
			return false;
		if (localReceiptAddress == null) {
			if (other.localReceiptAddress != null)
				return false;
		} else if (!localReceiptAddress.equals(other.localReceiptAddress))
			return false;
		if (localReceiptAddressTracker != other.localReceiptAddressTracker)
			return false;
		if (localReceiptTime == null) {
			if (other.localReceiptTime != null)
				return false;
		} else if (!localReceiptTime.equals(other.localReceiptTime))
			return false;
		if (localReceiptTimeTracker != other.localReceiptTimeTracker)
			return false;
		if (localTrackingNumber == null) {
			if (other.localTrackingNumber != null)
				return false;
		} else if (!localTrackingNumber.equals(other.localTrackingNumber))
			return false;
		if (localTrackingNumberTracker != other.localTrackingNumberTracker)
			return false;
		return true;
	}


	
}
