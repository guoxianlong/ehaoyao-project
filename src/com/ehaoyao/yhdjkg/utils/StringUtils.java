package com.ehaoyao.yhdjkg.utils;

/**
 * User: sjfeng Date: 13-8-27 Time: 下午4:10 To change this template use File |
 * Settings | File Templates.
 */
public class StringUtils extends org.springframework.util.StringUtils {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		}
		return false;
	}

	public static String stringFormat(String value) {
		if (value == null || "".equals(value))
			value = "0";
		return value;
	}
	
	public static String replaceData(String value){
		value=value.replace("\"", "\'");
		return value;
	}
	
//	public static void main(String[] args) {
//		String value="广东省佛山市南庄镇万科城4区一座（请投递到\"速递易\"收件箱）";
//		value =replaceData(value);
//		System.out.println(value);
//		
//	}
}