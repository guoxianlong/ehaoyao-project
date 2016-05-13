/**
 * 
 */
package com.ehaoyao.opertioncenter.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class EntityUtil {
	/**
	 * 渠道标志与名称Map
	 */
	public static Map<String, String> map = null;
	public static Map<String, String> map2 = null;

	/**
	 * 所有渠道map
	 */
	public static Map<String,String> getAllChannel() {
		if(map==null){
			map = new HashMap<String, String>();
			map.put("cdehy", "成都易好药");
			map.put("CFY", "处方药");
			map.put("cn21", "21世纪");
			map.put("ddw", "当当网");
			map.put("DXD", "代下单");
			map.put("EHY", "易好药");
			map.put("hysdyf", "淘宝大药房");
			map.put("HYSGW", "新官网");
			map.put("JD", "京东SOPL");
			map.put("JDDYF", "嘉定大药房");//2014-12-25
			map.put("JDMYD", "京东母婴店");
			map.put("JDSG", "京东闪购");
			map.put("lysop", "浏友1店");
			map.put("lysop2", "浏友2店");
			map.put("MSJK", "民生健康（京东）");
			map.put("SNYG", "苏宁易购");
			map.put("SOP", "京东SOP");
			map.put("tbhys", "天猫");
			map.put("womai", "中粮我买网");//2014-12-25
			map.put("wx", "微信");
			map.put("wxshop", "微信小店");
			map.put("yhd", "1号店");
			map.put("YMX", "亚马逊");
			map.put("YMXRC", "亚马逊入仓");
			//2014-12-25
			map.put("SYDYF", "石药大药房");
			map.put("TMMYD", "天猫母婴店");//好药师母婴专营店(天猫店铺)	TMMYD
			map.put("MEHY", "移动官网");//移动官网（新官网移动平台）	MEHY
			map.put("JDPP", "京东拍拍");
			map.put("YLYY", "以岭药业");
			map.put("TMDQD", "易好药电器店（天猫）");//易好药电器店（天猫店铺）	TMDQD
			map.put("ZGYD", "中国移动（江苏）");
			map.put("TMHB", "互帮轮椅（天猫）");
			map.put("HWG", "美健通海外购");//美健通海外购（天猫海淘C店）	HWG
			map.put("MSDD", "民生健康（当当)");
			map.put("SJPA", "手机平安");
			map.put("PACFY", "平安处方药");
			//2015-12-29
			map.put("JDDJ", "京东到家");
			map.put("TMCFY", "天猫处方药");
			map.put("ZSTY", "掌上糖医");
			//2016-04-11
			map.put("yhdcfy", "一号店处方药");
		}
		return map;
	}
	
	public static String getChannelName(String orderFlag) {
		String result = "";
		if (null != orderFlag) {
			if (map2 == null) {
				map2 = new HashMap<String,String>();
				if (map == null) {
					map = getAllChannel();
				}
				Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
					map2.put(entry.getKey().toUpperCase(),entry.getValue().toString());
				}
			}
			result = map2.get(orderFlag.toUpperCase());
		}
		return result;
	}
	
	public static String getOrderFlagChange(String orderFlag){
		return getChannelName(orderFlag);
//		String result = "";
//		if(null != orderFlag){
//			if(orderFlag.equals("cdehy")){
//				result = "成都易好药";
//			} else if(orderFlag.equals("CFY")){
//				result = "处方药";
//			}else if(orderFlag.equals("cn21")){
//				result = "21世纪";
//			} else if(orderFlag.equals("ddw")){
//				result = "当当网";
//			} else if(orderFlag.equals("DXD")){
//				result = "代下单";
//			} else if(orderFlag.equals("EHY")){
//				result = "易好药";
//			} else if(orderFlag.equals("hysdyf")){
//				result = "淘宝大药房";
//			} else if(orderFlag.equals("HYSGW")){
//				result = "新官网";
//			} else if(orderFlag.equals("JD")){
//				result = "京东SOPL";
//			}else if(orderFlag.equals("JDMYD")){
//				result = "京东母婴店";
//			}else if(orderFlag.equals("JDSG")){
//				result = "京东闪购";
//			}else if(orderFlag.equals("lysop")){
//				result = "lysop";
//			} else if(orderFlag.equals("lysop2")){
//				result = "lysop2";
//			} else if(orderFlag.equals("MSJK")){
//				result = "民生健康";
//			} else if(orderFlag.equals("SNYG")){
//				result = "苏宁易购";
//			} else if(orderFlag.equals("SOP")){
//				result = "京东SOP";
//			} else if(orderFlag.equals("tbhys")){
//				result = "天猫";
//			} else if(orderFlag.equals("wx")){
//				result = "微信";
//			} else if(orderFlag.equals("wxshop")){
//				result = "微信小店";
//			} else if(orderFlag.equals("yhd")){
//				result = "1号店";
//			} else if(orderFlag.equals("YMX")){
//				result = "亚马逊";
//			} else if(orderFlag.equals("YMXRC")){
//				result = "亚马逊入仓";
//			}
//		}
//		return result;
	}
	
	public static String getCashDeliveryChange(String status){
		String result = "";
		if(null != status){
			if(status.equals("1")){
				result = "仅针对货到付款";
			} else if(status.equals("2")){
				result = "仅针对顺丰";
			} else if(status.equals("3")){
				result = "仅针对官网会员";
			} else if(status.equals("4")){
				result = "仅针对非官网会员";
			}
		} 
		return result;
	}
	
	public static String getOrderStatusChange(String status){
		String result = "";
		if(null != status){
			if(status.equals("1")){
				result = "已下单";
			} else if(status.equals("2")){
				result = "配货中";
			} else if(status.equals("3")){
				result = "已发货";
			} else if(status.equals("4")){
				result = "已签收";
			} else if(status.equals("5")){
				result = "已拆单";
			} else if(status.equals("6")){
				result = "运单已复核";
			} else if(status.equals("7")){
				result = "已退款";
			}
		} 
		return result;
	}

}
