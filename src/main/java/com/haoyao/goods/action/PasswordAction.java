package com.haoyao.goods.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.MD5Utils;

@Controller
@RequestMapping("/password.do")
public class PasswordAction extends BaseAction{
	@Autowired
	private UserServiceImpl userService;
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	@RequestMapping( params = ("method=data") )
	public String data( ModelMap modelMap ){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();      
		if (principal instanceof UserDetails) {      
		    String userName = ((UserDetails) principal).getUsername();
		    User user = userService.checkByUserName(userName);
		    modelMap.put("user", user);
		}
		modelMap.put("result", 1);
		return "password";
	}
	
	@RequestMapping( params = ("method=update") )
	public String update(User user,HttpServletResponse response,ModelMap modelMap){
		user.setOldPswd(user.getPassWord());
		String passWord = user.getPassWord() + "{" + user.getUserName() + "}";
		user.setPassWord(MD5Utils.MD5(passWord));
		userService.update(user);
		modelMap.put("result", 0);
		return "password";
	}
	
	@RequestMapping( params = ("method=checkOldPassword") )
	public void checkOldPassword(String oldPassword,Long id,HttpServletResponse response){
		User user = userService.loadUserById(id);
		if( oldPassword.equals(user.getOldPswd()) ){
			try {
				response.getWriter().print("{\"result\":\"true\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print("{\"result\":\"false\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
