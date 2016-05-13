package com.ehaoyao.opertioncenter.common;

import java.util.Random;

public class RandomUtil {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getRandom());			
		}
	}
	
	public static int getRandom(){
		int number;
		Random random = new Random();
		number = random.nextInt();
		return number;
	}

}
