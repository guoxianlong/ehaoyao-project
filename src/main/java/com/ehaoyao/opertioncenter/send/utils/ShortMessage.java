package com.ehaoyao.opertioncenter.send.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.json.JSONObject;

import com.ehaoyao.opertioncenter.common.PropertiesUtil;

public class ShortMessage {
	
	public static String uuid = PropertiesUtil.getProperties("extend.properties", "uuid");
	public static String token = PropertiesUtil.getProperties("extend.properties", "token");
	public static String url = PropertiesUtil.getProperties("extend.properties", "send_url");

	/**
	 * 
	 * @param phone 手机号
	 * @param message 短信内容
	 * @param level 短信等级，单发为5-8级, 群发为2-4级,手机验证码为9级，级数越大，发送顺序越前
	 */
	public static String sendSM(String phone, String message, String level) throws Exception {
		if(phone==null || "".equals(phone=phone.trim())){
			return "2";
		}
		int m = 65;
		int n = (message.length() - 1) / m + 1;
		String subMesge = "";
		String res = "";
		for (int i = 0; i < n; i++) {
			if (i < n - 1) {
				subMesge = message.substring(i * m, (i + 1) * m);
			} else {
				subMesge = message.substring(i * m);
			}
			res = sendMesg(phone, subMesge, level);
			if (!"1".equals(res)) {
				break;
			}
		}
		return res;
	}
	
	public static String sendMesg(String phone,String message,String level) throws Exception{
        URL postUrl = new URL(url);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // Set the post method. Default is GET
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
        // connection.setFollowRedirects(true);
 
        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        connection.setInstanceFollowRedirects(true);
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection .getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
        String content = "phoneNumber="+phone+"&messageContent="+URLEncoder.encode(message, "utf-8")+"&sendLevel="+level+"&uuid="+uuid+"&token="+token;
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
        out.writeBytes(content); 
        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        String line="";
        String res = "";
        while ((line = reader.readLine()) != null){
            res += line;
        }
        reader.close();
        connection.disconnect();
        if(res!=null && res.trim().length()>0){
        	try {
				JSONObject json = JSONObject.fromObject(res.trim());
				JSONObject reponse = json.getJSONObject("response");
				if ("1".equals(reponse.get("code").toString().trim())) {
					return "1";//发送成功
				} else {//发送失败
					return "tip:"+(reponse.get("tip")!=null?reponse.get("tip").toString():null);
				}
			} catch (Exception e) {
				return "message:"+res.trim();
			}
		}
        return "2";
	}
	
	
	/**
	 * 判断当前时间是否在指定时间段内
	 */
	public static boolean betwenTime(Integer sh,Integer eh){
		if(sh==null||eh==null){
			return false;	
		}
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		Calendar sc = Calendar.getInstance();
		String s = df1.format(sc.getTime());
		try {
			sc.setTime(df1.parse(s));
			sc.set(Calendar.HOUR_OF_DAY, sh);
		} catch (ParseException e1) {
			return false;
		}
		if(ca.compareTo(sc)<0)
			return false;
		
		String e = df1.format(sc.getTime());
		try {
			sc.setTime(df1.parse(e));
			sc.set(Calendar.HOUR_OF_DAY, eh);
		} catch (ParseException e1) {
			return false;
		}
		
		if(ca.compareTo(sc)>0)
			return false;
		
		return true;
	}
	
}
