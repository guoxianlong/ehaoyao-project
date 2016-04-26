package com.haoyao.express.yuantong.utils;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 定时任务相关配置
 */
public class TaskConfig {
	public static HashMap<String, String> taskCfgs;
	static ResourceBundle bundle = ResourceBundle.getBundle("taskConfig");
	static {
		taskCfgs = new HashMap<String, String>();
		try {
			Set<String> names = bundle.keySet();
			for (String name : names) {
				taskCfgs.put(name, bundle.getString(name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}


	/**
	 * 获取任务间隔
	 * 
	 * @param clazz
	 *            任务Class
	 */
	public static Long getInterval(Class<?> clazz) {
		return Long.valueOf(taskCfgs.get(clazz.getSimpleName()));
	}
	/**
	 * 获取任务间隔
	 * 
	 * @param clazz
	 *            任务Class
	 */
	public static Long getInterval(String name) {
		return Long.valueOf(taskCfgs.get(name));
	}
	
	public static Integer getInteger(String name) {
		return Integer.valueOf(taskCfgs.get(name));
	}
	public static String getString(String name) {
		return taskCfgs.get(name);
	}
	/**
	 * 是否启动该定时任�?
	 * 
	 * @param clazz
	 *            定时任务名称
	 * @return
	 */
	public static Boolean getStartFlag(Class<?> clazz) {
		return Boolean.valueOf(taskCfgs.get(clazz.getSimpleName()));
	}
	/**
	 * 是否启动该定时任�?
	 * 
	 * @param name
	 *            定时任务名称
	 * @return
	 */
	public static Boolean getStartFlag(String name) {
		return Boolean.valueOf(taskCfgs.get(name));
	}

	
}
