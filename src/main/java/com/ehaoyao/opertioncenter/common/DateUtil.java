/**
 * 
 */
package com.ehaoyao.opertioncenter.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kangxr
 *
 */
public class DateUtil {
	
	private static SimpleDateFormat sdf;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getDateToString());
	}
	
	/**
	 * 获取当前时间并转换字符串
	 * @return String
	 */
	public static String getDateToString(){
		Date date = new Date();
		sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = sdf.format(date);
		return dateString;
	}

}
