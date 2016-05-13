package com.haoyao.goods.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.common.ConfigConstants;
import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.haoyao.goods.dto.UserRoleVO;
import com.haoyao.goods.model.ApplyPlatform;
import com.haoyao.goods.model.Organization;
import com.haoyao.goods.model.User;
import com.haoyao.goods.model.UserRole;
import com.haoyao.goods.service.ApplyPlatformServiceImpl;
import com.haoyao.goods.service.DataSyncServiceImpl;
import com.haoyao.goods.service.OrganizationServiceImpl;
import com.haoyao.goods.service.RoleServiceImpl;
import com.haoyao.goods.service.UserRoleServiceImpl;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.MD5Utils;
import com.haoyao.goods.util.MainUtils;
import com.haoyao.goods.util.SignUtils;

@Controller
@RequestMapping({"/user.do","/user2.do"})
public class UserAction extends BaseAction {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserRoleServiceImpl userRoleService;
	@Autowired
	private RoleServiceImpl roleService;
	@Autowired
	private OrganizationServiceImpl organizationService;
	@Autowired
	private DataSyncServiceImpl dataSyncServiceImpl;

	@Autowired
	private ApplyPlatformServiceImpl applyPlatformService;
	private String outScreenSign = PropertiesUtil.getProperties("extend.properties", "outScreenSign");
	/**
	 * @param applyPlatformService the applyPlatformService to set
	 */
	public void setApplyPlatformService(
			ApplyPlatformServiceImpl applyPlatformService) {
		this.applyPlatformService = applyPlatformService;
	}

	/**
	 * 获取用户集合
	 * @param request
	 * @param modelMap
	 * @param user
	 * @return
	 */
	@RequestMapping(params = ("method=user"))
	public String user(HttpServletRequest request,ModelMap modelMap,User user) {
		StringBuilder hqlString = new StringBuilder(100);
		if( user.getUserName() != null && !"".equals(user.getUserName().trim()) ){
			hqlString.append(" and (userName like '%" + user.getUserName().trim() + "%' or name like '%" + user.getUserName().trim() + "%') ");
		}
		if( user.getOrgId() != null ){
			hqlString.append(" and orgId = " + user.getOrgId() + " ");
		}
		if( user.getLockStatus() != null  ){
			hqlString.append(" and lockStatus = " + user.getLockStatus() + " ");
		}
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		if( pageno == null || "".equals(pageno) ){
			this.setPageno(1);
		}else{
			this.setPageno(Integer.parseInt(pageno));
			if( this.getPageno() < 1 ){
				this.setPageno(1);
			}
		}
		if( pageSize == null || "".equals(pageSize) || "".equals(pageSize) ){
			this.setPageSize(20);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		recTotal = userService.findUserCount(hqlString.toString());
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<User> userList = userService.findUser((this.getPageno() - 1) * this.getPageSize(),this.getPageSize(),hqlString.toString());
		List<Organization> orgList = organizationService.findAllOrg();
		modelMap.put("userList", userList);
		modelMap.put("orgList", orgList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("user",user );
		return "user/user";
	}

	/**
	 * 进入添加用户页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=add"))
	public String add( ModelMap modelMap ) {
		List<Organization> orgList = organizationService.findAllOrg();
		modelMap.put("orgList", orgList);
		return "user/user_add";
	}

	/**
	 * 进入修改用户页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=data"))
	public String data(Long id, ModelMap modelMap) {
		//modelMap.put("user", userService.loadUserById(id));
		User user = userService.loadUserById(id);
		modelMap.put("id", id);
		modelMap.put("userName", user.getUserName());
		modelMap.put("name", user.getName());
		modelMap.put("oldPswd", user.getOldPswd());
		modelMap.put("email", user.getEmail());
		modelMap.put("orgId", user.getOrgId());
		modelMap.put("lockStatus", user.getLockStatus());
		modelMap.put("createTime", user.getCreateTime());
		modelMap.put("orgList", organizationService.findAllOrg());
		return "user/user_update";
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping(params = ("method=update"))
	public String update(User user1) {
		
		User user = userService.loadUserById(user1.getId());
		MainUtils.copy(user1, user);
		
		user.setOldPswd(user.getPassWord());
		String passWord = user.getPassWord() + "{" + user.getUserName() + "}";
		user.setPassWord(MD5Utils.MD5(passWord));
		userService.update(user);
		
		List<ApplyPlatform> applylist = applyPlatformService.getPlatformList();
		List<String> urls = new ArrayList<String>();
		
		String url = null;
		for(ApplyPlatform app:applylist){
			url = app.getUrl();
			if(!"".equals(url) && url != null ){
				urls.add(app.getUrl());
			}
		}
		if(urls.size()>0){
			dataSyncServiceImpl.sendDataToTargetSys(urls, ConfigConstants.DATA_TYPE_USER, ConfigConstants.ACTION_TYPE_UPDATE, user);
		}
		return "redirect:user.do?method=user";
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(params = ("method=del"))
	public String del(Long id) {
		List<UserRole> list = userRoleService.findRolesByUserId(id);
		if( list != null && list.size() > 0 ){
			for( UserRole ur : list ){
				userRoleService.delete(ur);
			}
		}
		userService.delete(userService.loadUserById(id));
		
		List<ApplyPlatform> applylist = applyPlatformService.getPlatformList();
		List<String> urls = new ArrayList<String>();
		String url = null;
		for(ApplyPlatform app:applylist){
			url = app.getUrl();
			if(!"".equals(url) && url != null ){
				urls.add(app.getUrl());
			}
		}
		if(urls.size()>0){
			dataSyncServiceImpl.sendDataToTargetSys(urls, ConfigConstants.DATA_TYPE_USER, ConfigConstants.ACTION_TYPE_DELETE, id);
		}
		return "redirect:user.do?method=user";
	}

	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	@RequestMapping(params = ("method=save"))
	public String save(User user) {
		user.setOldPswd(user.getPassWord());
		String passWord = user.getPassWord() + "{" + user.getUserName() + "}";
		user.setPassWord(MD5Utils.MD5(passWord));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setCreateTime(format.format(new Date()));
		userService.save(user);
		User newUser = userService.loadUserByUserName(user.getUserName());
		Long id = newUser.getId();
		List<ApplyPlatform> list = applyPlatformService.getPlatformList();
		List<String> urls = new ArrayList<String>();
		String url = null;
		for(ApplyPlatform app:list){
			url = app.getUrl();
			if(!"".equals(url) && url != null ){
				urls.add(app.getUrl());
			}
		}
		if(urls.size()>0){
			dataSyncServiceImpl.sendDataToTargetSys(urls, ConfigConstants.DATA_TYPE_USER, ConfigConstants.ACTION_TYPE_INSERT, newUser);
		}
		return "redirect:user.do?method=detail&userId=" + newUser.getId();
	}


	/**
	 * 进入用户授权页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=detail"))
	public String detail(UserRoleVO vo, ModelMap modelMap,HttpServletRequest request) {
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		if( pageno == null || "".equals(pageno) ){
			this.setPageno(1);
		}else{
			this.setPageno(Integer.parseInt(pageno));
			if( this.getPageno() < 1 ){
				this.setPageno(1);
			}
		}
		if( pageSize == null || "".equals(pageSize) || "".equals(pageSize) ){
			this.setPageSize(20);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		recTotal = userRoleService.getUserRoleCount(vo);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<UserRoleVO> userRoleList = userRoleService.findRoleByUser((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),vo);
		User user = userService.loadUserById(vo.getUserId());
		modelMap.put("userRoleList", userRoleList);
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("user", user);
		modelMap.put("vo",vo );
		return "user/user_role";
	}
	
	/**
	 * 授权
	 * @param permissionId
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(params = ("method=warrant"))
	public void warrant(Long roleId,Long userId,HttpServletResponse response) {
		try {
			if(userId == null || userId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			if(roleId == null || roleId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			UserRole userRole = userRoleService.findUserRoleByUserIDAndRoleId(userId, roleId);
			if(userRole != null){
				userRoleService.delete(userRole);
			}
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			userRoleService.save(ur);
			
			ApplyPlatform apply = applyPlatformService.loadByRoleId(roleId);
			String url = null;
			if(apply != null){
				url = apply.getUrl();
				if(!"".equals(url)&& url !=null)
				{
					dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_USER_ROLE, ConfigConstants.ACTION_TYPE_INSERT, ur);
				}
			}
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取消授权
	 * @param permissionId
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(params = ("method=notWarrant"))
	public void notWarrant(Long roleId, Long userId,HttpServletResponse response) {
		try {
			if(roleId == null || roleId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			if(userId == null || userId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			UserRole userRole = userRoleService.findUserRoleByUserIDAndRoleId(userId, roleId);
			if(userRole == null){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			userRoleService.delete(userRole);
			
			ApplyPlatform apply = applyPlatformService.loadByRoleId(roleId);
			String url = null;
			if(apply != null){
				url = apply.getUrl();
				if(!"".equals(url)&& url != null){
					dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_USER_ROLE, ConfigConstants.ACTION_TYPE_DELETE, userRole);
				}
			}
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存用户授权信息
	 * @param roles
	 * @param userId
	 * @param response
	 */
	@RequestMapping(params = ("method=saveRoles"))
	public void saveRoles(String roles, Long userId,HttpServletResponse response) {
		List<UserRole> userRoleList = userRoleService.findRolesByUserId(userId);
		for ( UserRole userRole : userRoleList ){
			userRoleService.delete(userRole);
		}
		if( roles != null && !"".equals(roles.trim()) ){
			String[] roleIds = roles.split(",");
			for( int i = 0; i < roleIds.length;i++ ){
				UserRole userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(Long.parseLong(roleIds[i]));
				userRoleService.save(userRole);
			}
		}
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @param response
	 */
	@RequestMapping( params = ("method=checkUserName") )
	public void findByUserName( String userName, HttpServletResponse response){
		User user = userService.checkByUserName(userName);
		try{
			if( user == null )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id判断用户名是否存在
	 * @param userName
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkUserNameById") )
	public void checkUserNameById( String userName,String id,HttpServletResponse response){
		User user = userService.checkByUserName(userName);
		try{
			if( user == null || id.equals(user.getId().toString()) )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	/**
	 * 根据id判断用户是否存在
	 * @param userName
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkUserById") )
	public void checkUserById(HttpServletResponse response,HttpServletRequest request){
		response.setContentType("text/html;charset=utf-8"); 
		User user = null;
		String message = null;
		String userId = request.getParameter("custServCode");
		String reqSign = request.getParameter("sign");
		/**对请求加密*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("custServCode",userId);
		HashMap<String, String> signMap = SignUtils.sign(map, outScreenSign);
		String sign = signMap.get("appSign");
		if(!sign.equals(reqSign)){
			message = "签名验证失败！";
		}
		if(message == null || "".equals(message.trim())){
			try{
				user = userService.checkByUserName(userId);
			}catch(Exception e){
				message = e.getMessage();
				e.printStackTrace();
			}
		}
		try{
			if(message != null && !"".equals(message)){
				response.getWriter().print("{\"code\":\"1\",\"message\":\""+message+"\"}");
				return;
			}
			if( user == null || "".equals(user.getId().toString()) ){
				response.getWriter().print("{\"code\":\"1\",\"message\":\"不存在此客户ID对应的客服！\"}");
				return;
			}
			response.getWriter().print("{\"code\":\"0\",\"message\":null}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
}
