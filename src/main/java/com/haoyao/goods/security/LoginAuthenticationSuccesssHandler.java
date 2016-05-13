package com.haoyao.goods.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 验证成功页面，
 * 根据url跳转
 * @author Administrator
 *
 */
public class LoginAuthenticationSuccesssHandler implements
		AuthenticationSuccessHandler {

	private String defaultUrl;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		 Collection<? extends GrantedAuthority> list = authentication.getAuthorities();
		 if( list == null || list.size() == 0 ){
			 String url = request.getContextPath() + "/login.jsp?error=3";
			 PrintWriter out = response.getWriter();
			 out.println("<html>");  
			 out.println("<script type=\"text/javascript\">");  
			 out.println("window.open ('" + url + "','_top')"); 
			 out.println("</script>");  
			 out.println("</html>");
			 return;
		 }
		response.sendRedirect(request.getContextPath()+defaultUrl);
	}
	
	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}
