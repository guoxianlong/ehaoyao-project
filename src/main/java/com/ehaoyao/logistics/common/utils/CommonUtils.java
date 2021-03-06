package com.ehaoyao.logistics.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CommonUtils {

	public static List<Object> removeDuplicate(List<Object> list) {
	       Set<Object> set = new HashSet<Object>();
	       List<Object> newList = new ArrayList<Object>();
	       for (Iterator<Object> iter = list.iterator(); iter.hasNext();) {
	           Object element = (Object) iter.next();
	           if (set.add(element))
	               newList.add(element);
	       }
	       return newList;
	   }
	
}
