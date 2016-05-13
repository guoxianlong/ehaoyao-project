package com.ehaoyao.yhdjkg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;


public class PostClient {

	/**
	 * 提交post请求（支持文件上传）
	 * 
	 * @param urlStr
	 * @param appParamMap
	 * @param filePathArray
	 * @param secretKey
	 * @return
	 */
	public static String sendByPost(String urlStr, Map<String, String> appParamMap, String[] filePathArray, String secretKey) {
		try {
			
			HttpClient httpClient = new HttpClient();
			PostMethod httpPost = new PostMethod(urlStr);

			//对参数进行排序操作
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			if (appParamMap != null) {
				treeMap.putAll(appParamMap);
			}

			//生成sign验证码
			String sign = Md5Util.md5Signature(treeMap, secretKey);
			treeMap.put("sign", sign);
			Iterator<String> iterator = treeMap.keySet().iterator();

			List<Part> partList = new ArrayList<Part>();
			
			//设置参数信息
			while (iterator.hasNext()) {
				String key = iterator.next();
				partList.add(new StringPart(key, treeMap.get(key), "UTF-8"));
			}

			//存在文件的情况下，设置文件信息
			if(filePathArray != null && filePathArray.length > 0){
				for(String filePath : filePathArray){
					File file = new File(filePath);
					partList.add(new CustomFilePart(file.getName(), file));
				}
			}
			
			//设置参数以及文件信息
			Part[] parts = new Part[partList.size()];
			parts = partList.toArray(parts);
			httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));

			//执行post请求
			httpClient.executeMethod(httpPost);

			//获取响应的信息
			InputStream inputStream = httpPost.getResponseBodyAsStream();

			//输出响应的信息
			StringBuffer postResult = new StringBuffer();
			String readLine = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((readLine = reader.readLine()) != null) {
				postResult.append(readLine);
			}

			return postResult.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提交post请求（不支持文件上传）
	 * 
	 * @param urlStr
	 * @param appParamMap
	 * @param secretKey
	 * @return
	 */
	public static String sendByPost(String urlStr, Map<String, String> appParamMap, String secretKey) {
		try {
			
			HttpClient httpClient = new HttpClient();
			PostMethod httpPost = new PostMethod(urlStr);

			httpPost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;; charset=utf-8");
			
			//对参数进行排序操作
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			if (appParamMap != null) {
				treeMap.putAll(appParamMap);
			}

			//生成sign验证码
			String sign = Md5Util.md5Signature(treeMap, secretKey);
			treeMap.put("sign", sign);
			Iterator<String> iterator = treeMap.keySet().iterator();

			
			//设置参数信息
			while (iterator.hasNext()) {
				String key = iterator.next();
//				System.out.println(key+"="+treeMap.get(key)+"&");
				httpPost.addParameter(new NameValuePair(key, treeMap.get(key)));
			}
			
			//执行post请求
			httpClient.executeMethod(httpPost);

			//获取响应的信息
			InputStream inputStream = httpPost.getResponseBodyAsStream();

			//输出响应的信息
			StringBuffer postResult = new StringBuffer();
			String readLine = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((readLine = reader.readLine()) != null) {
				postResult.append(readLine);
			}

			return postResult.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
