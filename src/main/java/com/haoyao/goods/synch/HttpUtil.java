package com.haoyao.goods.synch;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 鎻愪緵閫氳繃HTTP鍗忚鑾峰彇鍐呭鐨勬柟娉�<br/>
 * 鎵�湁鎻愪緵鏂规硶涓殑params鍙傛暟鍦ㄥ唴閮ㄤ笉浼氳繘琛岃嚜鍔ㄧ殑url encode锛屽鏋滄彁浜ゅ弬鏁伴渶瑕佽繘琛寀rl encode锛岃璋冪敤鏂硅嚜琛屽鐞�
 * @author wuhuoxin
 */
public class HttpUtil {
	/**
	 * logger
	 */
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	
	/**
	 * 鏀寔鐨凥ttp method
	 *
	 */
	private static enum HttpMethod {
		POST,DELETE,GET,PUT,HEAD;
	};
	
	private static String invokeUrl(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String encoding, HttpMethod method){
		//鏋勯�璇锋眰鍙傛暟瀛楃涓�
		StringBuilder paramsStr = null;
		if(params != null){
			paramsStr = new StringBuilder();
			Set<Map.Entry> entries = params.entrySet();
			for(Map.Entry entry:entries){
				String value = (entry.getValue()!=null)?(String.valueOf(entry.getValue())):"";
				paramsStr.append(entry.getKey() + "=" + value + "&");
			}
			//鍙湁POST鏂规硶鎵嶈兘閫氳繃OutputStream(鍗砯orm鐨勫舰寮�鎻愪氦鍙傛暟
			if(method != HttpMethod.POST){
				url += "?"+paramsStr.toString();
			}
		}
		
		URL uUrl = null;
		HttpURLConnection conn = null;
		try {
			//鍒涘缓鍜屽垵濮嬪寲杩炴帴
			uUrl = new URL(url);
			conn = (HttpURLConnection) uUrl.openConnection();
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			conn.setRequestMethod(method.toString());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//璁剧疆杩炴帴瓒呮椂鏃堕棿
			conn.setConnectTimeout(connectTimeout);
			//璁剧疆璇诲彇瓒呮椂鏃堕棿
			conn.setReadTimeout(readTimeout);
			//鎸囧畾璇锋眰header鍙傛暟
			if(headers != null && headers.size() > 0){
				Set<String> headerSet = headers.keySet();
				for(String key:headerSet){
					conn.setRequestProperty(key, headers.get(key));
				}
			}

			if(paramsStr != null && method == HttpMethod.POST){
				//鍙戦�璇锋眰鍙傛暟
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encoding));
				out.write(paramsStr.toString());
				out.flush();
				out.close();
			}
			
			//鎺ユ敹杩斿洖缁撴灉
			StringBuilder result = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn
					.getInputStream(),encoding));
			if(in != null){
				String line = "";
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
				in.close();
			}
			return result.toString();
		} catch (Exception e) {
			log.error("httpUtil获取数据出现异常：url"+url+","+params,e);
			//澶勭悊閿欒娴侊紝鎻愰珮http杩炴帴琚噸鐢ㄧ殑鍑犵巼
			try {
				byte[] buf = new byte[100];
				InputStream es = conn.getErrorStream();
				if(es != null){
					while (es.read(buf) > 0) {;}
					es.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			//鍏抽棴杩炴帴
			if (conn != null){
				conn.disconnect();
			}	
		}
		return null;			
	}
	
	/**
	 * POST鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滃鍔犫� <br/>
	 * 娉ㄦ剰锛欻ttp鏂规硶涓彧鏈塒OST鏂规硶鎵嶈兘浣跨敤body鏉ユ彁浜ゅ唴瀹�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String post(String url, Map params, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.POST);
	}
	
	/**
	 * POST鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滃鍔犫� <br/>
	 * 娉ㄦ剰锛欻ttp鏂规硶涓彧鏈塒OST鏂规硶鎵嶈兘浣跨敤body鏉ユ彁浜ゅ唴瀹�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param headers 璇锋眰澶村弬鏁�
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String post(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,headers,connectTimeout,readTimeout,charset,HttpMethod.POST);
	}
	
	/**
	 * GET鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滄煡璇⑩�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String get(String url, Map params, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.GET);
	}
	
	/**
	 * GET鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滄煡璇⑩�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param headers 璇锋眰澶村弬鏁�
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String get(String url, Map params, Map<String,String> headers,int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,headers,connectTimeout,readTimeout,charset,HttpMethod.GET);
	}
	
	/**
	 * PUT鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滄洿鏀光� <br/>
	 * 娉ㄦ剰锛歅UT鏂规硶涔熸槸浣跨敤url鎻愪氦鍙傛暟鍐呭鑰岄潪body锛屾墍浠ュ弬鏁版渶澶ч暱搴︽敹鍒版湇鍔″櫒绔疄鐜扮殑闄愬埗锛孯esin澶ф鏄�K
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String put(String url, Map params, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.PUT);
	}
	
	/**
	 * PUT鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滄洿鏀光� <br/>
	 * 娉ㄦ剰锛歅UT鏂规硶涔熸槸浣跨敤url鎻愪氦鍙傛暟鍐呭鑰岄潪body锛屾墍浠ュ弬鏁版渶澶ч暱搴︽敹鍒版湇鍔″櫒绔疄鐜扮殑闄愬埗锛孯esin澶ф鏄�K
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param headers 璇锋眰澶村弬鏁�
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String put(String url, Map params, Map<String,String> headers,int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,headers,connectTimeout,readTimeout,charset,HttpMethod.PUT);
	}	
	
	/**
	 * DELETE鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滃垹闄も�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String delete(String url, Map params, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.DELETE);
	}
	
	/**
	 * DELETE鏂规硶鎻愪氦Http璇锋眰锛岃涔変负鈥滃垹闄も�
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param headers 璇锋眰澶村弬鏁�
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String delete(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,headers,connectTimeout,readTimeout,charset,HttpMethod.DELETE);
	}
	
	/**
	 * HEAD鏂规硶鎻愪氦Http璇锋眰锛岃涔夊悓GET鏂规硶  <br/>
	 * 璺烥ET鏂规硶涓嶅悓鐨勬槸锛岀敤璇ユ柟娉曡姹傦紝鏈嶅姟绔笉杩斿洖message body鍙繑鍥炲ご淇℃伅锛岃兘鑺傜渷甯﹀
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String head(String url, Map params, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,null,connectTimeout,readTimeout,charset,HttpMethod.HEAD);
	}
	
	/**
	 * HEAD鏂规硶鎻愪氦Http璇锋眰锛岃涔夊悓GET鏂规硶  <br/>
	 * 璺烥ET鏂规硶涓嶅悓鐨勬槸锛岀敤璇ユ柟娉曡姹傦紝鏈嶅姟绔笉杩斿洖message body鍙繑鍥炲ご淇℃伅锛岃兘鑺傜渷甯﹀
	 * @param url 璧勬簮璺緞锛堝鏋渦rl涓凡缁忓寘鍚弬鏁帮紝鍒檖arams搴旇涓簄ull锛�
	 * @param params 鍙傛暟
	 * @param headers 璇锋眰澶村弬鏁�
	 * @param connectTimeout 杩炴帴瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param readTimeout 璇诲彇瓒呮椂鏃堕棿锛堝崟浣嶄负ms锛�
	 * @param charset 瀛楃闆嗭紙涓�埇璇ヤ负鈥渦tf-8鈥濓級
	 * @return
	 */
	public static String head(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String charset){
		return invokeUrl(url,params,headers,connectTimeout,readTimeout,charset,HttpMethod.HEAD);
	}

	/**
	 * 直接跳转
	 * @param text
	 * @param contentType
	 * @param noCache
	 * @param response
	 * @author xiao
	 */
	public static void render(String text, String contentType, boolean noCache,HttpServletResponse response){
		try {
			if(StringUtil.isNotBlank(contentType))
				response.setContentType(contentType);
			else
				response.setContentType("text/plain; charset=UTF-8");

			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}
			PrintWriter pr = response.getWriter();
			pr.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

