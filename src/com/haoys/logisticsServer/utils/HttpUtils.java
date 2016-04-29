package com.haoys.logisticsServer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class HttpUtils {
	static Logger logger=Logger.getLogger(HttpUtils.class);
public static String sendHttpPost(String url,String params){
	String result=null;
	URL uri;
	try {
		uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("content-type", "application/xml");
		conn.connect();// 握手
		OutputStream os = conn.getOutputStream();// 拿到输出流
		os.write(params.getBytes("UTF-8"));
		os.flush();
		os.close();
		InputStream is = conn.getInputStream();// 拿到输入流
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		result = br.readLine();		
		br.close();
		isr.close();
		is.close();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	return result;
}

/**
 * 发送post请求
 * 
 * */
public static String sendPost(String url, String param) {
	PrintWriter out = null;
	BufferedReader in = null;
	String result = "";
	try {
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		//OutputStream outputStream = conn.getOutputStream();
		//outputStream.write(param.getBytes("UTF-8"));
		out = new PrintWriter(conn.getOutputStream());
		// 发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {
			result += "\n" + line;
		}
	} catch (Exception e) {
		logger.error("发送POST请求出现异常！" + e);
		e.printStackTrace();
	}
	// 使用finally块来关闭输出流、输入流
	finally {
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	return result;
}

public static void main(String[] args) {
	String outputStr = "{\"userPhone\":\"13263394663\",\"information\":\"已发货\"} ";
	String url="http://124.202.153.75/haoyao/send/messageApi.do?method=sendLogisticsMessage";
	String ss=sendPost(url,outputStr);
	System.out.println(ss);
/*	String mobile="13263394663";
	String stauts="已发货";
	String param="{\"userPhone\":\""+mobile+"\",\"information\":\""+stauts+"\"}";
	System.out.println(param);*/
}
}
