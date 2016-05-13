package com.ehaoyao.yhdjkg.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class BaseMap {
	public  static String SECRET_KEY;
	public  static String ROUTER_URL ;//= "http://openapi.yhd.com/app/api/rest/router";
	
	public  static String channel_yhdcfy;
	
	static ResourceBundle properties = null;
	static{
		try{
			String proFilePath=System.getProperty("user.dir")+"/config/config.properties";
			File outFile=new File(proFilePath);
			InputStream in=new BufferedInputStream(new FileInputStream(outFile));
			properties = new PropertyResourceBundle(in);
			SECRET_KEY=properties.getString("SECRET_KEY");
			ROUTER_URL=properties.getString("ROUTER_URL");
			
			channel_yhdcfy = properties.getString("channel_yhdcfy");
			
		} catch (Exception e) {
			e.printStackTrace();
			//logger.info("Connection初始化失败");
		} 
	}
	public static	Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();		
	 	map.put("sessionKey", properties.getString("SESSION_KEY"));
        map.put("appKey", properties.getString("APP_KEY"));
        map.put("format", "json");        
        map.put("ver", "1.0"); 
        map.put("timestamp", DateUtil.formatCurrentDateToStandardDate());
		
		return map;
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
