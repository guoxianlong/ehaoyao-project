package com.ehaoyao.opertioncenter.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;

public class CharacterReplaceUtil {

	public static String replace(String str,ShortMessageLog bean){
		if(str == null || "".equals(str)){
			return "";
		}
		if (bean == null) {
			return str;
		}
		Matcher m = Pattern.compile("\\[([^\\[\\]]+)\\]").matcher(str);
		while (m.find()){
			if("userName".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getCustomerName()!=null?bean.getCustomerName().trim():""));
			}else if("lastName".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getCustomerLastname()!=null?bean.getCustomerLastname().trim():""));
			}else if("orderNumber".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getOrderNumber()!=null?bean.getOrderNumber().trim():""));
			}else if("expressName".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getExpressComName()!=null?bean.getExpressComName().trim():""));
			}else if("expressNumber".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getExpressNo()!=null?bean.getExpressNo().trim():""));
			}else if("money".equals(m.group(1))){
				str = str.replace("["+m.group(1)+"]", (bean.getRefundMoney()!=null?bean.getRefundMoney().trim():""));
			}
		}
		return str;
	}
	public static void main(String[] args) {
		String a = "亲爱的[userName]用户，您好";
		ShortMessageLog log = new ShortMessageLog();
		log.setCustomerName("张三");
		log.setCustomerLastname(null);
		System.out.println(CharacterReplaceUtil.replace(a,log));
	}
}
