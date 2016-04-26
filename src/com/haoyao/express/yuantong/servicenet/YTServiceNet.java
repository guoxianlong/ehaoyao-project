package com.haoyao.express.yuantong.servicenet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.haoyao.express.yuantong.utils.MD5Util;
import com.haoyao.express.yuantong.utils.TaskConfig;

public class YTServiceNet {
	private static Logger logger=Logger.getLogger(YTServiceNet.class);
	private static SimpleDateFormat dateformat1 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	 
	private static final String app_key = TaskConfig.getString("app_key");
	private static final String format = "XML";
	private static final String user_id = TaskConfig.getString("user_id");
	private static String secret_key =TaskConfig.getString("secret_key");
	private static final String vision = TaskConfig.getString("vision");
	public static final String WaybillSearchMethod=TaskConfig.getString("WaybillSearchMethod");
	private static final String queryurl = TaskConfig.getString("queryurl");

	public static String getExpressInfo(String trackNumber){
		String times = dateformat1.format(new Date());
	String paramet = secret_key+"app_key"+app_key+"format"+format+"method"+WaybillSearchMethod+"timestamp"+times+"user_id"+user_id+"v"+vision;
	String sign=MD5Util.code32(paramet, "UTF-8");
	//String data="[{'Number':'"+trackNumber+"'}]";
	String data="<?xml version=\"1.0\"?><ufinterface><Result><WaybillCode><Number>"+trackNumber+"</Number></WaybillCode></Result></ufinterface>";
	String param="sign="+sign+"&app_key=" + app_key +"&format=" + format
			+ "&method=" + WaybillSearchMethod + "&timestamp=" + times
			+ "&user_id=" + user_id + "&v=" + vision+"&param="+data;
	String res=sendPost(queryurl,param);
	return res;
}
/** 
 * 发送post请求
 * 
 * */
private static String sendPost(String url, String param) {
	PrintWriter out = null;
	BufferedReader in = null;
	String result = "";
	URLConnection conn=null;
	URL realUrl = null;
	try {
		realUrl = new URL(url);
		// 打开和URL之间的连接
		conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setConnectTimeout(120000);
		conn.setReadTimeout(120000);
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
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
		logger.info("发送POST请求出现异常！" + e);
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

}
