package com.haoyao.goods.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

/**
 * 验证失败页面
 * @author Administrator
 *
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private UserServiceImpl userService;
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@SuppressWarnings("deprecation")
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authentication)
			throws IOException, ServletException {
		String userName = (String) authentication.getAuthentication().getPrincipal();
		User user = userService.checkByUserName(userName);
		Integer param = 0;
		if( user.getLockStatus() == 1 ){
			param = 2;//用户为锁定状态
		}else{
			param = 1;//密码错误
		}
		String url = request.getContextPath() + "/login.jsp";
		if( param == 1 ){
			url = url + "?error=1";
		}else if( param == 2 ){
			url = url + "?error=2";
		}
		PrintWriter out = response.getWriter();
		out.println("<html>");  
		out.println("<script type=\"text/javascript\">");  
		out.println("window.open ('" + url + "','_top')"); 
		out.println("</script>");  
		out.println("</html>");
		return;
	}

}
