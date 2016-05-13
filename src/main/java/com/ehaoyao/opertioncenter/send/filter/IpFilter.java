package com.ehaoyao.opertioncenter.send.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ehaoyao.opertioncenter.common.PropertiesUtil;


public class IpFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	public static String ips = PropertiesUtil.getProperties("extend.properties", "ip");
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {   
         HttpServletRequest request=(HttpServletRequest)arg0;      
         HttpServletResponse response  =(HttpServletResponse) arg1; 
         String uri = request.getRequestURI();
         if(uri.endsWith("error.html")|| uri.endsWith(".html"))
         {
        	 arg2.doFilter(arg0, arg1);
        	 return;
         }
         String ip = request.getHeader("x-forwarded-for");  
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("Proxy-Client-IP");  
         }  
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getRemoteAddr();  
         }  
         System.out.println(ip);
         if(ip==null || "".equals(ip)){
        	 response.sendRedirect(request.getContextPath() + "/sms/error.html");    
             return;
         }
         if(ips == null || "".equals(ips)){
        	 response.sendRedirect(request.getContextPath() + "/sms/error.html");    
             return;
         }
         String[] ipArr = ips.split(",");
         boolean pass = false;
         for(String i:ipArr){
        	 if(ip.equals(i)){
        		 pass = true;
        		 break;
        	 }
         }
         if(!pass){
        	 response.sendRedirect(request.getContextPath() + "/sms/error.html");    
             return;
         }
         arg2.doFilter(arg0, arg1);
         return;      
  }   
  public void init(FilterConfig arg0) throws ServletException {   
  }   
}
