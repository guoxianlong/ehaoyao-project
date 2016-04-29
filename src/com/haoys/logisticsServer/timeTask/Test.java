package com.haoys.logisticsServer.timeTask;

import com.haoys.logisticsServer.service.LogisticsServiceImpl;
import com.haoys.logisticsServer.service.spi.LogisticsService;

public class Test {

	public static void main(String[] args) {
//		ApplicationContext ac  = new ClassPathXmlApplicationContext("classpath:/*.xml");
//		LogisticsService logisticsService = ac.getBean(LogisticsService.class);
//		logisticsService.sendStautsToWX("VA26680637756", "jd", "已发货");
		
		LogisticsService logisticsService = new LogisticsServiceImpl();
		logisticsService.sendStautsToWX("806311048918", "yuantong", "已发货");
	}
}
