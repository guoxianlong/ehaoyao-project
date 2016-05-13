package com.ehaoyao.yhdjkg.utils;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * Base64加解密工具类
 * @author longshanw
 * @date 2016年3月22日
 * @version 0.0.1
 */
public class Base64Util {
	  /**
	   * 使用Base64进行编码
	   */
	public static String encode(String encodeContent,String charSet) { 
	    if (encodeContent == null) {
	      return null;
	    }
	    BASE64Encoder encoder = new BASE64Encoder(); 
	    return encoder.encode(encodeContent.getBytes(Charset.forName(charSet)));
	  }
	
	

	  
	  /**
	   * 使用Base64进行解码
	   * @return 解码后的内容
	   */
	public static String decode(String decodeContent) { 
		  byte[] bytes = null;  
	      String result = null;  
	      if (decodeContent != null) {  
	          BASE64Decoder decoder = new BASE64Decoder();  
	          try {  
	        	  bytes = decoder.decodeBuffer(new ByteArrayInputStream(decodeContent.getBytes()));  
	              result = new String(bytes);  
	          } catch (Exception e) {  
	              e.printStackTrace();  
	          }  
	      }  
	      return result;  
	  }
	  
	  /**
	   * 使用Base64进行解码
	   * @param charset  字符编码
	   * @return 解码后的内容
	   */
	public static String decode(String decodeContent,String charset) { 
		  byte[] bytes = null;  
	      String result = null;  
	      if (decodeContent != null) {  
	          BASE64Decoder decoder = new BASE64Decoder();  
	          try {  
	        	  bytes = decoder.decodeBuffer(new ByteArrayInputStream(decodeContent.getBytes(Charset.forName(charset))));
	        	  if(charset!=null && charset.length()>0){
	        		  result = new String(bytes,charset);
	        	  }else{
	        		  result = new String(bytes);  
	        	  }
	          } catch (Exception e) {  
	              e.printStackTrace();  
	          }  
	      }  
	      return result;  
	  }
	  
	
	/**
	 * java原生base64加密后替换特殊字符，用于解决各浏览器自动urlencoding问题
	 * @param encodeContent
	 * @return
	 */
	public static String encodeURL(String encodeContent,String charSet) { 
		String encodeValue = encode(encodeContent,charSet);
	    if (encodeValue == null) {
	      return null;
	    }
	    return encodeValue.split("=")[0].replace('+', '-').replace('/', '_');
	  }
	
	/**
	 * 替换后解码，用于解决各浏览器自动urlencoding问题
	 * @param decodeContent
	 * @param charset
	 * @return
	 */
	public static String decodeURL(String decodeContent,String charset) { 
			if(decodeContent == null){
				return null;
			}else{
				decodeContent = decodeContent.replace('-', '+').replace('_', '/');
			}
		  String  decodeValue = decode(decodeContent, charset);
	      return decodeValue;  
	  }
     
  
}