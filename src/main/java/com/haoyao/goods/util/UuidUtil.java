package com.haoyao.goods.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * UUID
 * 
 * @author hanxiaolei
 * 
 */
public class UuidUtil {

	public String uuid() {

	/*	StringBuffer buf = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < 2; i++) { // εδ½ιζΊζ?
			buf.append(rand.nextInt(10));
		}
		return "WX"+System.currentTimeMillis()+buf.toString();*/
		String temp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		temp = sdf.format(new java.util.Date());
		String te = temp.substring(3, 4)+temp.substring(4, 14);
		StringBuffer buf = new StringBuffer();
		Random rand = new Random();
		
		for (int i = 0; i < 3; i++) { // εδ½ιζΊζ?
			buf.append(rand.nextInt(10));
		}
		System.out.println("WX"+te+buf.toString());
		return "WX"+te+buf.toString();

	}

	/**
	 * θ·εζ₯ζ+ζΆι΄
	 * 
	 * @return
	 * 
	 */
	public String nowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(new Date());

		return sDate;
	}

	public static void main(String[] args) {
		String temp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		temp = sdf.format(new java.util.Date());
		String te = temp.substring(3, 4)+temp.substring(4, 14);
		StringBuffer buf = new StringBuffer();
		Random rand = new Random();
		
		for (int i = 0; i < 3; i++) { // εδ½ιζΊζ?
			buf.append(rand.nextInt(10));
		}
		System.out.println("WX"+te+buf.toString());
	
	
	}
}
