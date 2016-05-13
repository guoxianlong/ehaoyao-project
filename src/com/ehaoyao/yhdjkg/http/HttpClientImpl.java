package com.ehaoyao.yhdjkg.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author sjfeng
 * http get post 的简单包装
 */
@Service
public class HttpClientImpl implements HttpClientWrap{
	
	public static final int TIME_OUT=100000;
	public static final String CONTENT_CHARSET="UTF-8";
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private HttpClient httpClient;
	
	@PostConstruct
	private void init() {
		httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
		httpClient.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, HttpClientImpl.TIME_OUT);
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,HttpClientImpl.CONTENT_CHARSET);
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
	}
	
	/**
	 * 请求参数包装
	 * @param map
	 * @return
	 */
	private NameValuePair[] mapToNameValuepairs(Map<String, String> map) {
		if (map != null && !map.isEmpty()) {
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			NameValuePair valuePair;
			int i = 0;
			while (it.hasNext() && i < map.size()) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) it.next();
				if (m.getKey()!=null
						&& m.getValue()!=null) {
					valuePair = new NameValuePair(m.getKey().toString(), m
							.getValue().toString());
					params.add(valuePair);
				}
			}
			NameValuePair[] result = new NameValuePair[params.size()];
			result = params.toArray(result);
			return result;
		}
		return new NameValuePair[] { new NameValuePair() };
	}
	
	@Override
	public String executeGET(String targetUrl, Map<String, String> parameters,Map<String, String> headers){
		GetMethod getMethod = new GetMethod(targetUrl);
		if(parameters!=null&&!parameters.isEmpty()){
			logger.info("-----Request info-----");
			//get 参数封装
			getMethod.setQueryString(mapToNameValuepairs(parameters));
			//输出传递参数
			Iterator<Entry<String, String>> parameter=parameters.entrySet().iterator();
			while(parameter.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) parameter.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}else{
			logger.info("parameters的参数为空");
		}
		if(headers!=null&&!headers.isEmpty()){
			logger.info("*****Headers info*****");
			Iterator<Entry<String, String>> it=headers.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) it.next();
				getMethod.addRequestHeader(m.getKey(),m.getValue());
			}
			//输出Header信息
			Iterator<Entry<String, String>> header=headers.entrySet().iterator();
			while(header.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) header.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}
		try {
			int flag=httpClient.executeMethod(getMethod);
			logger.info("GET request:{}", flag);
			String response = getMethod.getResponseBodyAsString();
//			logger.info("Response_JSON={}",response);
			if(flag!=200){
				logger.error("TargetURL={};Result_Str={};Response_JSON={}",targetUrl,flag,response);
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	@Override
	public String executePOST(String targetUrl, Map<String, String> parameters,Map<String, String> headers){
		PostMethod postMethod = new PostMethod(targetUrl);
		if(parameters!=null&&!parameters.isEmpty()){
			logger.info("-----Request info-----");
			//post 参数封装
			postMethod.setRequestBody(mapToNameValuepairs(parameters));
			//输出传递参数
			Iterator<Entry<String, String>> parameter=parameters.entrySet().iterator();
			while(parameter.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) parameter.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
			
		}
		if(headers!=null&&!headers.isEmpty()){
			logger.info("*****Headers info*****");
			Iterator<Entry<String, String>> it=headers.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) it.next();
				postMethod.addRequestHeader(m.getKey(),m.getValue());
			}
			//输出Header信息
			Iterator<Entry<String, String>> header=headers.entrySet().iterator();
			while(header.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) header.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}
		try {
			int flag=httpClient.executeMethod(postMethod);
			logger.info("POST request:{}", flag);
			String response = postMethod.getResponseBodyAsString();
//			logger.info("Response_JSON={}",response);
			if(flag!=200){
				logger.error("TargetURL={};Result_Str={};Response_JSON={}",targetUrl,flag,response);
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String executePOST(String targetUrl, String data,
			Map<String, String> headers) {
		
		PostMethod postMethod = new PostMethod(targetUrl);
		if(headers!=null&&!headers.isEmpty()){
			logger.info("*****Headers info*****");
			Iterator<Entry<String, String>> it=headers.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) it.next();
				postMethod.addRequestHeader(m.getKey(),m.getValue());
			}
			//输出Header信息
			Iterator<Entry<String, String>> header=headers.entrySet().iterator();
			while(header.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) header.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}
		try {
			postMethod.setRequestBody(data);
			int flag=httpClient.executeMethod(postMethod);
			logger.info("POST request:{}", flag);
			String response = postMethod.getResponseBodyAsString();
//			logger.info("Response_JSON={}",response);
			if(flag!=200){
				logger.error("TargetURL={};Result_Str={};Response_JSON={}",targetUrl,flag,response);
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
	
	@Override
	public String executeGET(String targetUrl){
		GetMethod getMethod = new GetMethod(targetUrl);
		try {
			int flag=httpClient.executeMethod(getMethod);
			logger.info("GET request:{}", flag);
			String response = getMethod.getResponseBodyAsString();
//			logger.info("Response_JSON={}",response);
			if(flag!=200){
				logger.error("TargetURL={};Result_Str={};Response_JSON={}",targetUrl,flag,response);
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	
	/**
	 * 扩展出的HTTP返回状态码
	 * @param url
	 * @return
	 */
	public int executeGETStatus(String url) {
		GetMethod getMethod = new GetMethod(url);
		try {
			return httpClient.executeMethod(getMethod);
		} catch (HttpException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally {
			getMethod.releaseConnection();
		}
		return -1;
	}

	@Override
	public byte[] executeGetAsByte(String targetUrl,
			Map<String, String> parameters, Map<String, String> headers) {
		
		GetMethod getMethod = new GetMethod(targetUrl);
		if(parameters!=null&&!parameters.isEmpty()){
			logger.info("-----Request info-----");
			//get 参数封装
			getMethod.setQueryString(mapToNameValuepairs(parameters));
			//输出传递参数
			Iterator<Entry<String, String>> parameter=parameters.entrySet().iterator();
			while(parameter.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) parameter.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}else{
			logger.info("parameters的参数为空");
		}
		if(headers!=null&&!headers.isEmpty()){
			logger.info("*****Headers info*****");
			Iterator<Entry<String, String>> it=headers.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) it.next();
				getMethod.addRequestHeader(m.getKey(),m.getValue());
			}
			//输出Header信息
			Iterator<Entry<String, String>> header=headers.entrySet().iterator();
			while(header.hasNext()){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> m = (Map.Entry) header.next();
				logger.info("key={},value={}",m.getKey(),m.getValue());
			}
		}
		try {
			int flag=httpClient.executeMethod(getMethod);
			logger.info("GET request:{}", flag);
			byte[] response = getMethod.getResponseBody();
//			logger.info("Response_JSON={}",response);
			if(flag!=200){
				logger.error("TargetURL={};Result_Str={};Response_JSON={}",targetUrl,flag,response);
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	
}
