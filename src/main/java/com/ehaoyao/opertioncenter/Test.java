package com.ehaoyao.opertioncenter;

public class Test {
	

	public static void main(String[] args){
		try {
			String noAllowedBatchDealNums = "";
			noAllowedBatchDealNums += "、";
			System.out.println(noAllowedBatchDealNums+"-------"+noAllowedBatchDealNums.substring(0, noAllowedBatchDealNums.length()-1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
}
