package com.ehaoyao.yhdjkg.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class EncryptionUtil {
	/**
	 * logger
	 */
	public static final Logger logging = Logger
			.getLogger(EncryptionUtil.class);

	public static String getParamStr(String[] arr, Map paramMap) {
		StringBuilder sb = new StringBuilder();
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i] + paramMap.get(arr[i]));
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> convertRequestParamMap(Map parameterMap) {
		Map<String, String> paramMap = null;
		if (parameterMap != null && parameterMap.size() > 0) {
			Iterator it = parameterMap.entrySet().iterator();
			paramMap = new HashMap();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object obj = entry.getValue();
				String entryKye = (String) entry.getKey();
				if ("sign".equalsIgnoreCase(entryKye)) {
					continue;// signֵ��������֤
				}
				String val = "";
				if (obj instanceof String[]) {
					String[] strs = (String[]) obj;
					for (int i = 0; i < strs.length; i++) {
						val += strs[i];
					}
				} else {
					val = obj.toString();
				}
				paramMap.put(entryKye, val);
			}
		}
		return paramMap;
	}

	public static String generateSign(String testSecureKye,
			String interFaceName, Map<String, String[]> parameterMap)
			throws BusinessException {

		Map<String, String> newParamMap = convertRequestParamMap(parameterMap);
		if (newParamMap == null) {
			logging.error("合作商请求参数为空，请联系方合作商技术人员。");
			throw new BusinessException("方合作商请求参数为空，请联系方合作商技术人员。");
		}
		String[] ucParamArr = null;
		Set<String> newParamSet = newParamMap.keySet();
		ucParamArr = newParamSet.toArray(new String[newParamSet.size()]);
		// 对数组进行排序
		Arrays.sort(ucParamArr);
		logging.info("调用的接口为："+interFaceName);
		String sortedParam = getParamStr(ucParamArr, newParamMap);
		if (interFaceName == null || "".equals(interFaceName)) {
			logging.info("调用的接口为："+interFaceName);
			logging.error("方合作商调用的API为空，");
			throw new BusinessException("方合作商调用的API为空");
		}

		if (testSecureKye == null || "".equals(testSecureKye)) {
			logging.error("方合作商安全key为空");
			throw new BusinessException("方合作商ID为空");
		}

		// 构建加密前的字符串，api+参数串+安全key
		StringBuilder waitingEncrypStringSB = new StringBuilder();
		waitingEncrypStringSB.append(interFaceName).append(sortedParam)
				.append(testSecureKye);
		// 进行Md5加密
//		logging.info("加密前字符串：" + waitingEncrypStringSB.toString());
		String encryptionStr = EncoderHandlerUtil.encode("SHA1",
				waitingEncrypStringSB.toString());

		return encryptionStr;
	}

	public static void main(String[] args) throws BusinessException {
//		String orderInterface = "firstOrderQuery";
//		String secretKey = "&^gK&L3f";
//		secretKey="33230000e9030ghjtsfgh0564a0ce621";
//		Map<String, String[]> paramMap = new HashMap<String, String[]>();
//		paramMap.put("cooperatorId", new String[] { "1000098" });
//		paramMap.put("cooperatorOrderId", new String[] { "168562321" });
//
//		System.out.println(generateSign(secretKey, orderInterface, paramMap));
		
		
		String orderInterface = "orderstatus";
		String secretKey = "33230000e9030ghjtsfgh0564a0ce621";
		Map<String, String[]> map=new HashMap<String,String[]>();
		map.put("xxx", new String[]{"中文"});
		map.put("zzz", new String[]{"2"});
		map.put("yyy", new String[]{"3"});
		
		String sign = generateSign(secretKey, orderInterface, map);
		System.out.println(sign);

	}
}
