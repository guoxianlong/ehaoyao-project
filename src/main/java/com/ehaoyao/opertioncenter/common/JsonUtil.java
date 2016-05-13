package com.ehaoyao.opertioncenter.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.filters.CompositePropertyFilter;

public class JsonUtil {
	
	public static Map includeName;
	
	/**
	 * 把数据对象转换成json字符串
	 * DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	 * 数组对象形如：[{}, {}, {}, ...]
	 * map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...}
	 * @param object
	 * @return
	 */
	public static String getJSONString(Object object) throws Exception{
		return getJSONString(object,new String[]{});
	}
	
	/**
	 * 把数据对象转换成json字符串
	 * DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	 * 数组对象形如：[{}, {}, {}, ...]
	 * map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...}
	 * @param object
	 * @return
	 */
	public static String getJSONString(Object object,String[] excludes) throws Exception{
		String jsonString = null;
		//日期值处理器
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.getMergedExcludes().add("treeParent");
		jsonConfig.getMergedExcludes().add("treeName");
		jsonConfig.getMergedExcludes().add("treeText");
		if(includeName!=null){
			final Map nMap = (HashMap)((HashMap)includeName).clone();
			jsonConfig.setJsonPropertyFilter(new CompositePropertyFilter(){
					public boolean apply(Object arg0, String arg1, Object value) {
						if(nMap!=null&&nMap.get(arg1)!=null){
							return false;
						}else{
							if(value==null||String.valueOf(value).equals("")){
								return true;
							}
							return false;
						}
					}
					
			});
			includeName=null;
		}
		if(object != null){
			if(object instanceof Collection || object instanceof Object[]){
				jsonString = JSONArray.fromObject(object, jsonConfig).toString();
			}else{
				jsonString = JSONObject.fromObject(object, jsonConfig).toString();
			}
		}
		return jsonString == null ? "{}" : jsonString;
	}

}
