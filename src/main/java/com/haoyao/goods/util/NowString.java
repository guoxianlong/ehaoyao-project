package com.haoyao.goods.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 获取当前日期时间
 * @author hanxiaolei
 *
 */
public class NowString {

	public static void main(String[] args) {
		
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时�?
	}
}
