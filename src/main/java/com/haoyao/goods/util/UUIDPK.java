package com.haoyao.goods.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;

/**
 * UUID工具类.
 * 
 * @author 
 * @update 
 */

public class UUIDPK implements Serializable {
	public String POID;

	public UUIDPK() {
	}

	public UUIDPK(String id) {
		this.POID = id;
	}

	public String toString() {
		return this.POID;
	}

	public boolean equals(Object other) {
		boolean result = false;
		if (other == this) {
			return true;
		}
		if (!(other instanceof UUIDPK)) {
			return false;
		}

		result = this.POID == null ? false
				: ((UUIDPK) other).POID == null ? true : this.POID
						.equals(((UUIDPK) other).POID);

		return result;
	}

	public int hashCode() {
		return this.POID == null ? 0 : this.POID.hashCode();
	}

	public static String getUUID(Object obj) {
		String uuid = "";
		uuid = firstPart() + secondPart() + thirdPart(obj) + fourthPart();
		return uuid;
	}

	public static String getUUID() {
		String uuid = "";
		try {
			Properties props = new Properties();
			IdentifierGenerator gen = new UUIDHexGenerator();
			((Configurable) gen).configure(Hibernate.STRING, props, null);
			IdentifierGenerator gen2 = new UUIDHexGenerator();
			((Configurable) gen2).configure(Hibernate.STRING, props, null);
			uuid = (String) gen.generate(null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return uuid;
	}

	private static String firstPart() {
		long tmp = 0L;
		String tmpHex = null;
		tmp = System.currentTimeMillis();
		tmpHex = Long.toHexString(tmp);
		return tmpHex.substring(tmpHex.length() - 8);
	}

	private static String secondPart() {
		String sndPart = "";
		int tmpIp = 0;
		int tmp = 0;
		InetAddress localIPAddr = null;
		try {
			localIPAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		byte[] ipParts = localIPAddr.getAddress();

		tmp = ipParts[0];

		tmpIp = tmp << 24;

		tmp = ipParts[1];
		tmp <<= 16;
		tmpIp ^= tmp;

		tmp = ipParts[2];
		tmp <<= 8;
		tmpIp ^= tmp;

		tmp = ipParts[3];
		tmpIp ^= tmp;
		sndPart = Integer.toHexString(tmpIp);
		return getEightHex(sndPart);
	}

	private static String thirdPart(Object obj) {
		String tmpHex = "";
		tmpHex = Integer.toHexString(obj.hashCode());
		return getEightHex(tmpHex);
	}

	private static String fourthPart() {
		String tmpHex = "";
		SecureRandom sr = new SecureRandom();
		tmpHex = Integer.toHexString(sr.nextInt());
		return getEightHex(tmpHex);
	}

	private static String getEightHex(String part) {
		if (part.length() >= 8) {
			return part;
		}
		switch (part.length()) {
		case 0:
			part = "00000000";
			break;
		case 1:
			part = "0000000" + part;
			break;
		case 2:
			part = "000000" + part;
			break;
		case 3:
			part = "00000" + part;
			break;
		case 4:
			part = "0000" + part;
			break;
		case 5:
			part = "000" + part;
			break;
		case 6:
			part = "00" + part;
			break;
		case 7:
			part = "0" + part;
		}

		return part;
	}
}
