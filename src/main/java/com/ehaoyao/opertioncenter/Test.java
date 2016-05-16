package com.ehaoyao.opertioncenter;

public class Test {
	

	public static void main(String[] args){
		try {
			
			for(int i=0;i<10;i++){
				try {
					if(i==4){
						throw new RuntimeException("异常。。。");
					}
				} catch (Exception e) {
					continue;
				}
				System.out.println(i);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	
	
}
