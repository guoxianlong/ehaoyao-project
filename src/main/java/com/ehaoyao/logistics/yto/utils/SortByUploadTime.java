package com.ehaoyao.logistics.yto.utils;

import java.util.Comparator;

import com.ehaoyao.logistics.yto.vo.WaybillProcessInfo;



public class SortByUploadTime implements Comparator<Object> {
	 public int compare(Object o1, Object o2) {
		 WaybillProcessInfo s1 = (WaybillProcessInfo) o1;
		 WaybillProcessInfo s2 = (WaybillProcessInfo) o2;
		 return s1.getUploadTime().compareTo(s2.getUploadTime());
		 }
	}
