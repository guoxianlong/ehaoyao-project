package com.haoyao.goods.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;



/**
 * String功能函数.
 * 
 * @author 
 * @update 2011-9-2 下午04:47:54
 */

public class StringUtil {
    public static void main(String[] args) {
	System.out.println(getRandomNumber(6));
    }

    /**
     * 获取文件名.
     * 
     * @param path
     * @return
     * @version 1.0
     * @author 史明松
     * @update Jun 28, 2010 1:02:35 PM
     */
    public static String getFileName(String path) {
	int beginIndex = path.lastIndexOf("/");
	if (beginIndex >= 0) {
	    beginIndex = beginIndex + 1;
	}
	int endIndex = path.length();
	if (endIndex <= 0)
	    return "";
	return path.substring(beginIndex, endIndex);
    }

    /**
     * 用户特殊字符的转义 支持\\ ' % _ .
     * 
     * @param src
     * @return
     * @author 
     * @update 2011-9-2 下午04:48:23
     */
    public static String dealString(String src) {
	src = src.replace("\\", "\\\\");
	src = src.replace("'", "\\'");
	src = src.replace("%", "\\%");
	src = src.replace("_", "\\_");
	return src;
    }

    /**
     * 获取随机数字.
     * 
     * @param length
     *            指定随机数的长度
     * @return
     */
    public static String getRandomNumber(int length) {
	return StringUtil.getRandomString(length, 2);
    }

    /**
     * 获取随机字符串.
     * 
     * @param length
     *            指定随机数的长度
     * @return
     */
    public static String getRandomChar(int length) {
	return StringUtil.getRandomString(length, 1);
    }

    /**
     * 获取随机数或随机字符串.
     * 
     * @param length 
     * @param type
     *            0－－数字+大小写字母；1－－大小写字母；2－－数字；3－－数字+小写字母；其它－－同0
     * @return
     */
    public static synchronized String getRandomString(int length, int type) {
	StringBuffer sb = new StringBuffer();
	Random random = new Random();
	for (int i = 0; i < length; i++) {
	    if (type == 0) {
		sb.append(_ALLCHAR.charAt(random.nextInt(_ALLCHAR.length())));
	    } else if (type == 1) {
		sb.append(_LETTERCHAR.charAt(random.nextInt(_LETTERCHAR.length())));
	    } else if (type == 2) {
		sb.append(_NUMBERCHAR.charAt(random.nextInt(_NUMBERCHAR.length())));
	    } else if (type == 3) {
		sb.append(_NUMBERCHAR_LETTERCHAR.charAt(random.nextInt(_NUMBERCHAR_LETTERCHAR.length())));
	    } else {
		sb.append(_ALLCHAR.charAt(random.nextInt(_ALLCHAR.length())));
	    }
	}
	return sb.toString();
    }

    private static final String _ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String _LETTERCHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String _NUMBERCHAR_LETTERCHAR = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String _NUMBERCHAR = "0123456789";

    /**
     * 保留粘贴前的文本的格式效果.
     * 
     * @param s
     * @return
     * @author 
     * @update 2011-9-2 下午04:49:13
     */
    public static String translate(String s) {
	if (s == null || s.length() < 1) {
	    return "";
	}
	StringBuffer buf = new StringBuffer();
	for (int i = 0, limit = s.length(); i < limit; i++) {
	    int c = s.charAt(i);
	    switch (c) {
	    case ' ':
		buf.append("&nbsp;");
		break;
	    case '<':
		buf.append("&lt;");
		break;
	    case '>':
		buf.append("&gt;");
		break;
	    case '\n':
		buf.append("<br>");
		break;
	    default:
		buf.append((char) c);
	    }
	}
	return buf.toString();
    }

    /**
     * 处理空字符串.
     * 
     * @param str
     * @return String 如果为空 则返回""
     */
    public static String doEmpty(String str) {
	return doEmpty(str, "");
    }

    /**
     * 处理空字符串.
     * 
     * @param str
     * @param defaultValue
     *            输出的默认值
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
	if (str == null || str.equalsIgnoreCase("null") || str.trim().equals("") || str.trim().equals("－请选择－")) {
	    str = defaultValue;
	} else if (str.startsWith("null")) {
	    str = str.substring(4, str.length());
	}
	return str.trim();
    }

    /**
     * 处理空字符串.
     * 
     * @param str
     * @param defaultValue
     *            输出的默认值
     * @param specialValue
     *            特殊的，当空用的字符串，例如：“缺测”
     * @return String
     */
    public static String doEmpty(String str, String defaultValue, String specialValue) {
	if (str == null || str.equalsIgnoreCase("null") || str.trim().equals("") || str.trim().equals("－请选择－")) {
	    str = defaultValue;
	} else if (str.startsWith("null")) {
	    str = str.substring(4, str.length());
	}
	return str.trim();
    }

    /**
     * 移去字符串数组中的null值和空字符串.
     * 
     * @param str
     *            要处理的字符串数组
     * @return
     */
    public static String[] removeNull(String[] str) {
	List<String> list = new ArrayList<String>();
	for (int i = 0; i < str.length; i++) {
	    if (str[i] != null && !str[i].trim().equals("")) {
		list.add(str[i]);
	    }
	}
	String[] newStr = new String[list.size()];
	for (int i = 0; i < list.size(); i++) {
	    newStr[i] = list.get(i).toString();
	}
	return newStr;
    }

    /**
     * @description 验证字符串是否为空.
     * @version 1.0
     * @author 
     * @update May 31, 2010 2:16:46 PM
     */
    public static boolean isEmpty(String str) {
	if (str == null || str.equals("null") || str.trim().equals(""))
	    return true;
	else
	    return false;

    }

    /**
     * 根据标识符截取字符串返回数组.
     * 
     * @param str
     *            要截取的字符串
     * @param regex
     *            标识符
     * @return
     */
    public static String[] getArryByRegex(String str, String regex) {
	String[] strArray = null;
	if (str != null && !str.equals("") && !regex.equals("") && regex != null) {
	    strArray = str.split(regex);
	}
	return strArray;
    }

    /**
     * 判断一个字符串数组中是否含有某个字符串.
     * 
     * @param strArray
     * @param str
     * @return
     */
    public static boolean isContainStr(String[] strArray, String str) {
	boolean result = false;
	if (strArray != null && strArray.length > 0) {
	    for (int i = 0; i < strArray.length; i++) {

		if (strArray[i].equals(str)) {
		    result = true;
		}
	    }
	}
	return result;
    }

    /**
     * 判断是否是字符.
     */
    private static boolean isLetter(char c) {
	int k = 0x80;
	return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1.
     */
    private static int length(String s) {
	if (s == null)
	    return 0;
	char[] c = s.toCharArray();
	int len = 0;
	for (int i = 0; i < c.length; i++) {
	    len++;
	    if (!isLetter(c[i])) {
		len++;
	    }
	}
	return len;
    }

    /**
     * 注意： 一个汉字占两个长度<br>
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位.
     */
    public static String substring(String origin, int len) {
	if (origin == null || origin.equals("") || len < 1)
	    return "";
	byte[] strByte = new byte[len];
	if (len > length(origin)) {
	    return origin;
	}
	System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
	int count = 0;
	for (int i = 0; i < len; i++) {
	    int value = (int) strByte[i];
	    if (value < 0) {
		count++;
	    }
	}
	if (count % 2 != 0) {
	    len = (len == 1) ? ++len : --len;
	}
	return new String(strByte, 0, len);
    }

    /**
     * 截取字符长度.
     * 
     * @param origin
     * @param len
     *            返回截取和替代后的字符串最大的长度（一个汉字占两个长度）
     * @param replaceString
     *            如果源字符长度超过指定长度的后缀替代字符
     * @return
     */
    public static String substring(String origin, int len, String replaceString) {
	if (origin == null || origin.equals("") || len < 1)
	    return "";
	if (replaceString == null || replaceString.equals(""))
	    return substring(origin, len);
	int originLen = length(origin);
	int replaceStrLen = length(replaceString);
	if (originLen + replaceStrLen <= len)
	    return origin;
	if (replaceStrLen >= len)
	    return substring(replaceString, len);
	return substring(origin, len - replaceStrLen) + replaceString;
    }

    public static boolean isIn(String substring, String[] source) {
	if (source == null || source.length == 0) {
	    return false;
	}
	for (int i = 0; i < source.length; i++) {
	    String aSource = source[i];
	    if (aSource.equals(substring)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * 替换\r\n 的换行成html的<br>
     * .
     * 
     * @param input
     * @return
     * @author 史明松
     * @update 2011-9-2 下午04:54:59
     */
    public static String convertNewlines(String input) {
	input = replace(input, "\r\n", "\n");
	input = replace(input, "\n", "<BR />");
	return input;
    }

    public static String convertURL(String input) {
	if ((input == null) || (input.length() == 0)) {
	    return input;
	}
	StringBuffer buf = new StringBuffer(input.length() + 25);
	char[] chars = input.toCharArray();
	int len = input.length();
	int index = -1;
	int i = 0;
	int j = 0;
	int oldend = 0;
	label184: do {
	    char cur = chars[(i = index)];
	    j = -1;
	    if ((cur == 'f') && (index < len - 6)) {
		i++;
		if (chars[i] == 't') {
		    i++;
		    if (chars[i] == 'p')
			break label184;
		}
	    }
	    if ((cur == 'h') && ((i = index) < len - 7)) {
		i++;
		if (chars[i] == 't') {
		    i++;
		    if (chars[i] == 't') {
			i++;
			if (chars[i] == 'p') {
			    i++;
			    if (chars[i] != 's') {
				i--;
				if (chars[i] != 'p')
				    ;
			    } else if (i < len - 4) {
				i++;
				if (chars[i] == ':') {
				    i++;
				    if (chars[i] == '/') {
					i++;
					if (chars[i] == '/') {
					    i++;
					    j = i;
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    if (j > 0) {
		if ((index == 0) || (((cur = chars[(index - 1)]) != '\'') && (cur != '"') && (cur != '<') && (cur != '='))) {
		    cur = chars[j];
		    while (j < len) {
			if ((cur == ' ') || (cur == '\t') || (cur == '\'') || (cur == '"') || (cur == '<') || (cur == '[') || (cur == '\n')
				|| ((cur == '\r') && (j < len - 1) && (chars[(j + 1)] == '\n'))) {
			    break;
			}
			j++;
			if (j < len) {
			    cur = chars[j];
			}
		    }
		    cur = chars[(j - 1)];
		    if ((cur == '.') || (cur == ',') || (cur == ')') || (cur == ']')) {
			j--;
		    }
		    buf.append(chars, oldend, index - oldend);
		    buf.append("<a href=\"");
		    buf.append(chars, index, j - index);
		    buf.append('"');
		    buf.append(" target=\"_blank\"");
		    buf.append('>');
		    buf.append(chars, index, j - index);
		    buf.append("</a>");
		} else {
		    buf.append(chars, oldend, j - oldend);
		}
		oldend = index = j;
	    } else if ((cur == '[') && (index < len - 6) && (chars[(i = index + 1)] == 'u')) {
		i++;
		if (chars[i] == 'r') {
		    i++;
		    if (chars[i] == 'l') {
			i++;
			if ((chars[i] == '=') || (chars[i] == ' ')) {
			    i++;
			    j = i;
			    int u2;
			    int u1 = u2 = input.indexOf("]", j);
			    if (u1 > 0) {
				u2 = input.indexOf("[/url]", u1 + 1);
			    }
			    if (u2 < 0) {
				buf.append(chars, oldend, j - oldend);
				oldend = j;
			    } else {
				buf.append(chars, oldend, index - oldend);
				buf.append("<a href =\"");
				String href = input.substring(j, u1).trim();
				if ((href.indexOf("javascript:") == -1) && (href.indexOf("file:") == -1)) {
				    buf.append(href);
				}
				buf.append("\" target=\"_blank");
				buf.append("\">");
				buf.append(input.substring(u1 + 1, u2).trim());
				buf.append("</a>");
				oldend = u2 + 6;
			    }
			    index = oldend;
			}
		    }
		}
	    }
	    index++;
	} while (index < len);

	if (oldend < len) {
	    buf.append(chars, oldend, len - oldend);
	}
	return buf.toString();
    }

    public static String createBreaks(String input, int maxLength) {
	char[] chars = input.toCharArray();
	int len = chars.length;
	StringBuffer buf = new StringBuffer(len);
	int count = 0;
	int cur = 0;
	for (int i = 0; i < len; i++) {
	    if (Character.isWhitespace(chars[i])) {
		count = 0;
	    }
	    if (count >= maxLength) {
		count = 0;
		buf.append(chars, cur, i - cur).append(" ");
		cur = i;
	    }
	    count++;
	}
	buf.append(chars, cur, len - cur);
	return buf.toString();
    }

    public static String dspHtml(String input) {
	String str = input;
	str = createBreaks(str, 80);
	str = escapeHTMLTags(str);
	str = convertURL(str);
	str = convertNewlines(str);
	return str;
    }

    public static String escapeHTMLTags(String input) {
	if ((input == null) || (input.length() == 0)) {
	    return input;
	}
	StringBuffer buf = new StringBuffer();
	char ch = ' ';
	for (int i = 0; i < input.length(); i++) {
	    ch = input.charAt(i);
	    if (ch == '<')
		buf.append("&lt;");
	    else if (ch == '>')
		buf.append("&gt;");
	    else if (ch == '&')
		buf.append("&amp;");
	    else if (ch == '"')
		buf.append("&quot;");
	    else {
		buf.append(ch);
	    }
	}
	return buf.toString();
    }

    public static String escapeSQLTags(String input) {
	return input;
    }



    public static String join(List list, String delim) {
	if ((list == null) || (list.size() < 1)) {
	    return null;
	}
	StringBuffer buf = new StringBuffer();
	Iterator i = list.iterator();
	while (i.hasNext()) {
	    buf.append((String) i.next());
	    if (i.hasNext()) {
		buf.append(delim);
	    }
	}
	return buf.toString();
    }

    public static String notNull(String param) {
	return param == null ? "" : param.trim();
    }

    public static boolean nullOrBlank(String param) {
	return (param == null) || (param.trim().equals(""));
    }

    public static boolean parseBoolean(String param) {
	if (nullOrBlank(param)) {
	    return false;
	}
	switch (param.charAt(0)) {
	case '1':
	case 'T':
	case 'Y':
	case 't':
	case 'y':
	    return true;
	}
	return false;
    }

    /**
     * 字符串转成double.
     * 
     * @param param
     * @return
     * @author 
     * @update 2011-9-2 下午04:58:04
     */
    public static double parseDouble(String param) {
	double d = 0.0D;
	try {
	    d = Double.parseDouble(param);
	} catch (Exception localException) {
	}
	return d;
    }

    /**
     * 字符串转成float.
     * 
     * @param param
     * @return
     * @author 
     * @update 2011-9-2 下午04:58:25
     */
    public static float parseFloat(String param) {
	float f = 0.0F;
	try {
	    f = Float.parseFloat(param);
	} catch (Exception localException) {
	}
	return f;
    }

    /**
     * 字符串转成int.
     * 
     * @param param
     * @return
     * @author 
     * @update 2011-9-2 下午04:58:41
     */
    public static int parseInt(String param) {
	int i = 0;
	try {
	    i = Integer.parseInt(param);
	} catch (Exception e) {
	    i = (int) parseFloat(param);
	}
	return i;
    }

    /**
     * 字符串转成Long.
     * 
     * @param param
     * @return
     * @author 
     * @update 2011-9-2 下午04:59:01
     */
    public static long parseLong(String param) {
	long l = 0L;
	try {
	    l = Long.parseLong(param);
	} catch (Exception e) {
	    l = (long) parseDouble(param);
	}
	return l;
    }

    /**
     * 字符串转成Long.
     * 
     * @param param
     * @return
     * @author 
     * @update 2011-9-2 下午04:59:01
     */
    public static short parseShort(String param) {
	short s = 0;
	try {
	    s = Short.parseShort(param);
	} catch (Exception e) {
	    return s;
	}
	return s;
    }

    public static List quoteStrList(List list) {
	List tmpList = list;
	list = new ArrayList();
	Iterator i = tmpList.iterator();
	while (i.hasNext()) {
	    String str = (String) i.next();
	    str = "'" + str + "'";
	    list.add(str);
	}
	return list;
    }

    public static String replace(String mainString, String oldString, String newString) {
	if (mainString == null) {
	    return null;
	}
	int i = mainString.lastIndexOf(oldString);
	if (i < 0) {
	    return mainString;
	}
	StringBuffer mainSb = new StringBuffer(mainString);
	while (i >= 0) {
	    mainSb.replace(i, i + oldString.length(), newString);
	    i = mainString.lastIndexOf(oldString, i - 1);
	}
	return mainSb.toString();
    }

    public static List split(String str, String delim) {
	List splitList = null;
	StringTokenizer st = null;

	if (str == null) {
	    return splitList;
	}

	if (delim != null)
	    st = new StringTokenizer(str, delim);
	else {
	    st = new StringTokenizer(str);
	}

	if ((st != null) && (st.hasMoreTokens())) {
	    splitList = new ArrayList();

	    while (st.hasMoreTokens()) {
		splitList.add(st.nextToken());
	    }
	}
	return splitList;
    }

    /**
     * 判断如果一个字符串是空串的话，那么我们就返回一个""字符串.
     * 
     * @param str
     * @return
     */
    public static String isEmptyToStr(String str) {
	String result = "";
	if (!StringUtil.isEmpty(str)) {
	    result = str;
	}
	return result;
    }
   
    public static Integer[][] toarray(String s){
   	 Integer[][] d;
	      String sFirst[] = s.split(",");      //第一维
	      d = new Integer[sFirst.length][];    //d是一个二维数组
	      for(int i=0;i<sFirst.length;i++ ) {
	         String[] sSecond = sFirst[i].split(",");
	         d[i] = new Integer[sSecond.length];            //第二维 d[i]是一个一维数组
	         for(int  j=0;j<sSecond.length;j++ ) {
	            d[i][j] = Integer.parseInt(sSecond[j]);
	         }
	      }
		return d;
		}
    
    public static void copyProperties(Object dest, Object orig) {
    	try {
    	    BeanUtils.copyProperties(dest, orig);
    	} catch (IllegalAccessException ex) {
    	    ex.printStackTrace();
    	} catch (InvocationTargetException ex) {
    	    ex.printStackTrace();
    	}
        }
    
    /**
     * 把带逗号的字符串变成数组
     * 周亚男
     * @param s
     * @return
     */
   
    public static String[] convertStrToArray(String str){  
        String[] strArray = null;  
        strArray = str.split(",");  
        return strArray;  
    }  
    
    /**
     * 数组升序排序
     * 周亚男
     * @param arrays
     * @return
     */
    public static String shengxu(String[] arrays){
	  String temp="";
	  String dd="";
	  for(int i=0;i<arrays.length;i++){
	   for(int j=0;j<arrays.length-1;j++){
		  
	    if(Integer.parseInt(arrays[j])>Integer.parseInt(arrays[j+1])){
	     temp=arrays[j];
	     arrays[j]=arrays[j+1];
	     arrays[j+1]=temp;
	    }
	   }
	   String aa=arrays[i];
	   dd+=aa+",";
	  }
	return dd;
	 }
}
