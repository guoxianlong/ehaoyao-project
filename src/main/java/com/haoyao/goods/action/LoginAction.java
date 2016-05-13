package com.haoyao.goods.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

@Controller
@RequestMapping("/login.do")
public class LoginAction extends BaseAction {
	
	@Autowired
	private UserServiceImpl userService;

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	
	@RequestMapping( params = ("method=checkUserName") )
	public void findByUserName( String userName, HttpServletResponse response){
		User user = userService.checkByUserName(userName);
		try{
			if( user == null )
				response.getWriter().print("{\"result\":\"false\"}");
			else
				response.getWriter().print("{\"result\":\"true\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
}
