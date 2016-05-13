package com.ehaoyao.opertioncenter.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CommonUtils {

	/**
	 * List去重
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {
	       Set set = new HashSet();
	       List newList = new ArrayList();
	       for (Iterator<Object> iter = list.iterator(); iter.hasNext();) {
	           Object element = (Object) iter.next();
	           if (set.add(element))
	               newList.add(element);
	       }
	       return newList;
	   }

}
