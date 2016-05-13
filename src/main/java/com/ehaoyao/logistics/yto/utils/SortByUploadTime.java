package com.ehaoyao.logistics.yto.utils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ehaoyao.logistics.yto.service.impl.YTOLogisticsServiceImpl;
import com.ehaoyao.logistics.yto.vo.WaybillProcessInfo;



public class SortByUploadTime implements Comparator<Object> {
	private static final Logger logger = Logger.getLogger(YTOLogisticsServiceImpl.class);
	
	public int compare(Object o1, Object o2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WaybillProcessInfo s1 = (WaybillProcessInfo) o1;
		WaybillProcessInfo s2 = (WaybillProcessInfo) o2;
		Integer value = 0 ;
		try {
			Date date1 = sdf.parse(s1.getUploadTime().replaceAll("/", "-"));
			Date date2 = sdf.parse(s2.getUploadTime().replaceAll("/", "-"));
			if (date1.after(date2)) {
				value = 1;
			} 
			if (date1.before(date2)){
				value = -1;
			}
		} catch (Exception e) {
			logger.info("【转日期格式异常，原字符串："+s1.getUploadTime().replaceAll("/", "-")+","+s2.getUploadTime().replaceAll("/", "-")+"，异常信息:"+e.getMessage()+"】");
			e.printStackTrace();
		}
		return value;
	}
}
