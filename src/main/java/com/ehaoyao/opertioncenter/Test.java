package com.ehaoyao.opertioncenter;

public class Test {

	public static void main(String[] args) {
		String test = "1234567";
		String result = test!=null&&test.length()>4?test.substring(0, test.length()-4)+"****":"";
		System.out.println(result);

	}

}
