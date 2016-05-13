package com.ehaoyao.yhdjkg.http;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

/**
 * @author sjfeng
 *
 */
public interface HttpClientWrap {
	
	
	/**
	 * Send HTTP request by get method
	 * @param targetUrl  目标URL
	 * @param parameters 请求参数
	 * @param header	包头属性设置 可以为null
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public byte[] executeGetAsByte(String targetUrl, Map<String, String> parameters,Map<String, String> header);
	
	
	/**
	 * Send HTTP request by get method
	 * @param targetUrl  目标URL
	 * @param parameters 请求参数
	 * @param header	包头属性设置 可以为null
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String executeGET(String targetUrl, Map<String, String> parameters,Map<String, String> header);

	
	/**
	 * Send HTTP request by post method
	 * @param targetUrl	目标URL
	 * @param parameters 请求参数
	 * @param header	包头属性设置
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String executePOST(String targetUrl, Map<String, String> parameters,Map<String, String> header);
	
	

	/**
	 * Send HTTP request by post method 
	 * @param targetUrl	目标URL
	 * @param data	直接数据串
	 * @param header	包头属性设置
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String executePOST(String targetUrl,String data,Map<String, String> header);



	/**
	 * Send HTTP request by get method 
	 * @param targetUrl	目标URL
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String executeGET(String targetUrl);
}
