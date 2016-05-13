package com.ehaoyao.yhdjkg.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class TaskConfig {
	
	public static ResourceBundle properties = null;
	
	public static int cfy_normalorder_forward_minute = 1;
	
	
	static {
		try {
			String proFilePath=System.getProperty("user.dir")+"/config/taskTime.properties";
			File outFile=new File(proFilePath);
			InputStream in=new BufferedInputStream(new FileInputStream(outFile));
			properties = new PropertyResourceBundle(in);
			cfy_normalorder_forward_minute = Integer.parseInt(properties.getString("cfy_normalorder_forward_minute")); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long getInterval(Class<?> className) {
		return Long.parseLong(properties.getString(className.getSimpleName()));
	}
	
	public static String getValue(String key) {
		String value=properties.getString(key);
		try {
			return value!=null?new String(value.getBytes(Charset.forName("UTF-8"))):null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
