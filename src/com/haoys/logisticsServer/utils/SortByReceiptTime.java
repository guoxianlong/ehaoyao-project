package com.haoys.logisticsServer.utils;

import java.util.Comparator;

import com.haoys.logisticsServer.entity.LogisticsDetail;



public class SortByReceiptTime implements Comparator<Object> {
	 public int compare(Object o1, Object o2) {
		 LogisticsDetail s1 = (LogisticsDetail) o1;
		 LogisticsDetail s2 = (LogisticsDetail) o2;
		 return s1.getReceiptTime().compareTo(s2.getReceiptTime());
		 }
	}
