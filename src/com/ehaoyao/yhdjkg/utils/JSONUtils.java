package com.ehaoyao.yhdjkg.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JSONUtils extends JSON{


	/**
	 * 带有复杂泛型的类转换
	 * @param jsonStr
	 * @param typeReference
	 * @return
	 */
	public static <T> T getPerson(String jsonStr, TypeReference<T> typeReference) {
		T t = JSON.parseObject(jsonStr, typeReference);
		return t;
	}
	
	/**
	 * 不带有复杂泛型的类转换
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> T getPerson(String jsonStr, Class<T> cls) {
		T t = JSON.parseObject(jsonStr, cls);
		return t;
	}

	/**
	 * 集合list转换
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getPersons(String jsonStr, Class<T> cls) {
		List<T> list = JSON.parseArray(jsonStr, cls);
		return list;
	}

	/**
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String jsonStr) {
		List<Map<String, Object>> list = JSON.parseObject(jsonStr,
				new TypeReference<List<Map<String, Object>>>() {
				});
		return list;
	}
	
	// @SuppressWarnings("unchecked")
		// public static <T> List<T> getList4Json(String jsonString,
		// Class<T> pojoClasss) {
		//
		// JSONArray jsonArray = JSON.parseArray(jsonString);
		// JSONObject jsonObject;
		// T pojoValue;
		// List<T> list = new ArrayList<T>();
		// JsonConfig config = new JsonConfig();
		// config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
		// @Override
		// public String transformToJavaIdentifier(String str) {
		// char[] chars = str.toCharArray();
		// chars[0] = Character.toLowerCase(chars[0]);
		// return new String(chars);
		// }
		// });
		// config.setRootClass(pojoClasss);
		// for (int i = 0, k = jsonArray.size(); i < k; i++) {
		// jsonObject = jsonArray.getJSONObject(i);
		// Object bean = JSONObject.toBean(jsonObject, config);
		// pojoValue = (T) bean;
		// list.add(pojoValue);
		// }
		// return list;
		// }

}
