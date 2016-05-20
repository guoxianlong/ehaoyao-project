package com.ehaoyao.cfy.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartProject {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		context.start();
		/*@SuppressWarnings("resource")
		FileSystemXmlApplicationContext fileContext = new FileSystemXmlApplicationContext();
		fileContext.setConfigLocation("classpath:config/applicationContext.xml");
		fileContext.start();*/
	}

}
