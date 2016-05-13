package com.haoyao.goods.synch;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.google.code.kaptcha.Constants;



public class StringUtil {
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * 检查发票抬头是否合法。
	 * @param invoiceTitle
	 * @return
	 */
	public static boolean checkInvoiceTitle(String invoiceTitle){
		
		 Pattern p = Pattern.compile("^[0-9A-Za-z\u4e00-\u9fa5]{1,20}$");
	        Matcher m = p.matcher(invoiceTitle);
	        while(m.find()){
	           return true;
	        }
	        return false;
	}
	
	public static void main(String[] args) {
		String invoiceTitle="dff";
		boolean ifSuc=checkInvoiceTitle(invoiceTitle);
		System.out.println("是否为正？"+ifSuc);
	}
	/**
	 * 检查邮政编码是否合法。
	 * @param postcode
	 * @return
	 */
	public static boolean checkPostcode(String postcode){
		if(StringUtil.isNullOrBlank(postcode)){
			return false;
		}
		String ss="^[\\d]{6}$";
		//String reg="^[\\w\\u4E00-\\u9FA5\\uF900-\\uFA2D]*$";
		//String regex="^\\w{2,20}$";
		Pattern pattern=Pattern.compile(ss);
		Matcher matcher=pattern.matcher(postcode);
		return matcher.find();
	}
	/**
	 * 检查字符串是否为中文
	 * @param str
	 * @return
	 */
	public static boolean checkChineseStr(String str){
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]+$");
        Matcher m = p.matcher(str);
        while(m.find()){
           return true;
        }
        return false;
	}
	/**
	 * 检查是否为链接。
	 * 如果非链接，则返回首页。
	 * 如果是，则返回参数传进来的URL。
	 * @param url
	 * @return
	 */
	public static String checkUrl(String url,String domain){
		String domainFromUrl=getClientUrlDomain(url);
		if(StringUtil.isNullOrBlank(domainFromUrl)){
			return null;
		}else if(domain.equalsIgnoreCase(domainFromUrl)){
			 return url;
		}
		return null;
	}
	/**
	 * 获取网址的顶级域。
	 * @param clientUrl
	 * @return
	 */
	public static String getClientUrlDomain(String clientUrl) {
		if (StringUtil.isNullOrBlank(clientUrl))
			return null;
		Pattern pattern = Pattern
				.compile("^https?://([a-zA-Z0-9]*\\.)*([a-zA-Z0-9]*\\.[a-zA-Z0-9]*)[\\?/#]?.*");
		Matcher matcher = pattern.matcher(clientUrl);
		if (matcher.find()) {
			return matcher.group(2);
		}
		return null;
	}
	/**
	 * 在搜索框的联想功能使用。
	 * @return
	 */
	public static boolean checkSearchKey(String str){
		
		 String regex="[\\w\\u4e00-\\u9fa5]+";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(str);
		return matcher.find();
	}
	/**
	 * 删除时间中的秒数部分。
	 */
	public static String deleteSecondInDateStr(String dateStr){
		if(StringUtil.isNullOrBlank(dateStr)){
			return "";
		}
		int lastIndex=dateStr.lastIndexOf(":");
		
		if(lastIndex>-1){
			return dateStr.substring(0,lastIndex);
		}
		return dateStr;
	}
	
	//产生一个随机数 方法1
	public static String getRandomString(int length)
	{
	String str="abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
	Random random=new Random();
	StringBuffer sf=new StringBuffer();
	for(int i=0;i<length;i++)
	{
	 int number=random.nextInt(62);//0~61
	 sf.append(str.charAt(number));
	 
	}
	return sf.toString();
	}
	
	public static int getIntFromMapStrValue(Map<String, Object> paramMap,String mapKey){
		Object obj=paramMap.get(mapKey);
		if(obj!=null && obj instanceof Integer){
			return (Integer)obj;
		}
		return -1;
	}
	
	/**
	 * 获取请求的整个URL，例如请求URL是http://test.fadu123.com:9999/auth/showBottom,此方法返回的也是此值。
	 */
	public static String getRequestFullUrl(HttpServletRequest request){
		StringBuffer urlSb=request.getRequestURL();
		return urlSb.toString();
	}
	public static boolean checkPasswordFormat(String password){
		if(isNullOrBlank(password)){
			return false;
		}
		if(password.length()>=6 && password.length()<=20){
			return true;
		}else{
			return false;
		}
	}
	public static  String[] buildArrByFlag(String ids,String regexFlag){
		if(StringUtil.isNullOrBlank(ids)){
			return null;
		}
		String [] idArr=ids.split(regexFlag);
		return idArr;
	}
	/**
	 * 获取问题编号。
	 * @return
	 */
	public static String getQuestionKey(){
		return System.currentTimeMillis()+"";
	}

	/*public static String buildResetPasswordMkey(int userId,String email,long timeStamp){
		String key=userId+"&"+email+"$"+timeStamp+"dfdk&^%$";
		String md5Value=Md5Encrypt.md5(key, "UTF-8");
		return md5Value;
	}*/
	/**
	 * 检查是否为email格式的数据。
	 * @param email
	 * @return
	 */
	public static boolean checkEmailFormat(String email){
		if(isNullOrBlank(email)){
			return false;
		}
		//String regex="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";
		String regex="^([a-zA-Z0-9_\\.-]+)@([\\da-zA-Z\\.-]+)\\.([A-Za-z\\.]{2,6})$";
		Pattern   pattern   =   Pattern.compile(regex);     
	    Matcher   matcher   =   pattern.matcher(email);     
	    return matcher.find(); 
	}
	public static boolean checkIsPhone(String phone){
		if(StringUtil.isNullOrBlank(phone)){
			return false;
		}
		String regex="^[0-9]{3,4}\\-?[0-9]{7,8}$";//"^1[0-9]{10}||[0-9]{3-4}-[0-9][7-8]$";
		Pattern   pattern   =   Pattern.compile(regex);     
	    Matcher   matcher   =   pattern.matcher(phone);     
	    return matcher.find(); 
	}
	//检查验证码.
	/*public static boolean checkValidateCode(String code,HttpServletRequest request){
		String kaptchaExpected = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(StringUtil.isNullOrBlank(kaptchaExpected)){
			log.error("在session中获取正确的验证码字符串无法获取!");
			throw new RuntimeException("在session中获取正确的验证码字符串无法获取!");
		}
		
		if(StringUtil.isNullOrBlank(code)){
			return false;
		}
		if(code.equalsIgnoreCase(kaptchaExpected)){
			return true;
		}
		return false;
	}*/
	
	public static  boolean isNullOrBlank(String str){
		if(str==null){
			return true;
		}
		str=str.trim();
		if(!str.equals("")){
			return false;
		}else{
			return true;
		}
	}
	public static boolean isNotBlank(String str){
		return !isNullOrBlank(str);
	}
	
	public static int convertStrToInt(String str){
		int n = -1;
		if(null!=str && !"".equals(str.trim())){
			try {
				n = parseInt(str.trim());
			} catch (Exception e) {
			}
		}
		return n;
	}
	public static long convertStrToLong(String str){
		long n = -1;
		if(null!=str && !"".equals(str.trim())){
			n = Long.valueOf(str);
		}
		return n;
	}
	public static double convertStrToDouble(String str){
		double n = -1;
		if(null!=str && !"".equals(str.trim())){
			n =  Double.parseDouble(str.trim());
		}
		return n;
	}
	public static float convertStrToFloat(String str){
		float n = -1;
		if(null!=str && !"".equals(str.trim())){
			n =  Float.parseFloat(str.trim());
		}
		return n;
	}
	public static int parseInt(String idStr)throws Exception{
		if(StringUtil.isNullOrBlank(idStr)){
			return -1;
		}
		try{
			idStr=idStr.trim();
			return Integer.parseInt(idStr);
		}catch(Exception e){
			throw new Exception("抱歉，你输入的id值是非数字型！");
		}
	}
	public static int substringIdByKey(String keyStr,String flag){
		if(keyStr.toLowerCase().startsWith(flag.toLowerCase())){//父类。
			String idStr=keyStr.substring(flag.length());
			return StringUtil.convertStrToInt(idStr);
		}
		return -1;
	}
	/**
	 * 
	 * @param sign
	 * @param num  截取哪一个位置的值 ，注意从1开始。
	 * @return
	 */
	public static String getStrBySplitNum(String str,String sign,int num){
		if(str==null){
			return null;
		}
		String [] strArr=str.trim().split(sign);
		if(strArr.length>0 && num>0 && strArr.length>=num ){
			return strArr[num-1];
		}
		return null;
	}
	/**
	 * http://www.ehaoyao.com
	 * @param request
	 * @return
	 */
	public static String getProjectBaseUrl(HttpServletRequest request){
   		String path = request.getContextPath();
   		int port =request.getServerPort();
   		String basePath = request.getScheme()+"://"+request.getServerName();
   		if(port!=80){
   			basePath=basePath+":"+request.getServerPort();
   		}
   		basePath=basePath+path;
   		return basePath;
   	}
	/**
	 * 咨询详情页面，用户名称显示规则。
	 * @param loginUserName
	 * @return
	 */
	public static String changeSomeLoginNameSign(String loginUserName){
		if(StringUtil.isNullOrBlank(loginUserName)){
			return "";
		}
		String[] nameArr=loginUserName.split("@");
		if(nameArr.length<1){
			throw new IllegalArgumentException("登录名为空！不可继续");
		}
		String name=nameArr[0];
		StringBuffer newNameSB=new StringBuffer("");
			for(int i=0;i<name.length();i++){
				if(name.length() <6 || i<5){
					newNameSB.append("*");
				}else{
					newNameSB.append(name.charAt(i));
				}
			}
			if(nameArr.length>1){
				newNameSB.append("@"+nameArr[1]);
			}
			return newNameSB.toString();
	}
	/**
	 * 检查是否数字、字母或下划线组成。
	 * @param urlKey
	 * @return
	 */
	public static boolean checkNumAndEnglish(String str){
		if(StringUtil.isNullOrBlank(str)){
			return false;
		}
		String regex="^\\w+$";
		Pattern   pattern   =   Pattern.compile(regex);     
	    Matcher   matcher   =   pattern.matcher(str);     
	    return matcher.find(); 
	}
	
	//将数组转换为字符串，以sign相隔
	public static String convertArrayToString(String[] array, String sign){
		if (array!=null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				String[] temp = array[i].split(sign);
				sb.append(temp[0]);
				if ((i+1)<array.length)sb.append(sign);
			}
			return sb.toString();
		}
		return "";
	}
	
	// 过滤特殊字符  
    public   static   String stringFilter(String str) throws PatternSyntaxException { 
      // 清除掉所有特殊字符  
    	if(str!=null){
	      String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
	      Pattern   p   =   Pattern.compile(regEx);     
	      Matcher   m   =   p.matcher(str);     
	      return   m.replaceAll("").trim();
    	}else{
    		return "";
    	}
    }   
    public static boolean checkIsUrlKey(String urlKey){
		if(StringUtil.isNullOrBlank(urlKey)){
			return false;
		}
		String regex="^\\w+$";
		Pattern   pattern   =   Pattern.compile(regex);     
	    Matcher   matcher   =   pattern.matcher(urlKey);     
	    return matcher.find(); 
	}
    public static boolean checkLoginName(String str){
		if(StringUtil.isNullOrBlank(str)){
			return false;
		}
		//String ss="^[\\w\u4E00-\u9FA5]{2-10}$";
		String reg="^[\\w\\u4E00-\\u9FA5\\uF900-\\uFA2D]{2,20}$";
		//String regex="^\\w{2,20}$";,
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher=pattern.matcher(str);
		return matcher.find();
	}
	/**
	 *检查URL是否以xxxx.com为顶级域名。
	 * @param url
	 * @return
	 */
	public static boolean checkDomain(String url,String domain){
		if(!StringUtil.isNullOrBlank(url)){
			if(url.trim().toLowerCase().startsWith("http://")
					||url.trim().toLowerCase().startsWith("https://")){
				String domainFromUrl=getClientUrlDomain(url);
				if(StringUtil.isNullOrBlank(domainFromUrl)){
					return false;
				}else if(domain.equalsIgnoreCase(domainFromUrl)){
					 return true;
				}
			}
		}
		
		return true;
	}
	/**
	 * 验证真实姓名、邮件接收人。
	 * 验证登录名称，字母、数字及下划线。
	 * @param str
	 * @return
	 */
	public static boolean checkRealName(String str){
		if(StringUtil.isNullOrBlank(str)){
			return false;
		}
	//	String ss="^[\\w\u4E00-\u9FA5]{2-10}$";//中文也允许
		 String regex="^[\\w\\u4e00-\\u9fa5]{2,20}$";
		//String reg="^[\\w\\u4E00-\\u9FA5\\uF900-\\uFA2D]*$";
		//String regex="^\\w{2,20}$";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 获取项目的绝对路径。
	 * @param request
	 * @return
	 */
	public static String getProjectAbsolutePath(HttpServletRequest request){
		String absolutePath=request.getSession().getServletContext().getRealPath("/");
		return absolutePath;
	}
	
	public static boolean checkPhone(String phone){
		if(StringUtil.isNullOrBlank(phone)){
			return false;
		}
		String regex="^[0-9]{3,4}\\-?[0-9]{7,8}$";//"^1[0-9]{10}||[0-9]{3-4}-[0-9][7-8]$";
		Pattern   pattern   =   Pattern.compile(regex);     
	    Matcher   matcher   =   pattern.matcher(phone);     
	    return matcher.find(); 
	}
}
