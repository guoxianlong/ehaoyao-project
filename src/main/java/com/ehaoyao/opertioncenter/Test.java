package com.ehaoyao.opertioncenter;

import com.ehaoyao.opertioncenter.thirdOrderAudit.service.impl.ThirdOrderAuditServiceImpl;

public class Test {
	

	public static void main(String[] args){
		try {
			Object[] obj = new ThirdOrderAuditServiceImpl().get360LastStatus("A1541864933527981101");
			System.out.println(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
}
