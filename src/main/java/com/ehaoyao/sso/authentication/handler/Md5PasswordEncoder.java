package com.ehaoyao.sso.authentication.handler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jasig.cas.authentication.handler.PasswordEncoder;

public class Md5PasswordEncoder{

	public Md5PasswordEncoder() {
		// TODO Auto-generated constructor stub
	}

	public String encode(String password,Object salt) {
		// TODO Auto-generated method stub
		
		 String saltedPass = mergePasswordAndSalt(password, salt, false);
		StringBuffer sb = new StringBuffer();
		byte[] btInput = saltedPass.getBytes();
		MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();
	}
	   protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
	        if (password == null) {
	            password = "";
	        }

	        if (strict && (salt != null)) {
	            if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
	                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
	            }
	        }

	        if ((salt == null) || "".equals(salt)) {
	            return password;
	        } else {
	            return password + "{" + salt.toString() + "}";
	        }
	    }
}
