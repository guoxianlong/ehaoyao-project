package com.haoyao.goods.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.common.ConfigConstants;
import com.haoyao.goods.dto.RolePermissionVO;
import com.haoyao.goods.dto.RoleVO;
import com.haoyao.goods.model.ApplyPlatform;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.RolePermission;
import com.haoyao.goods.model.UserRole;
import com.haoyao.goods.security.MySecurityMetadataSource;
import com.haoyao.goods.service.ApplyPlatformServiceImpl;
import com.haoyao.goods.service.DataSyncServiceImpl;
import com.haoyao.goods.service.PermissionServiceImpl;
import com.haoyao.goods.service.RolePermissionService;
import com.haoyao.goods.service.RoleServiceImpl;
import com.haoyao.goods.service.UserRoleServiceImpl;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.MainUtils;

@Controller
@RequestMapping("/role.do")
public class RoleAction extends BaseAction{

	@Autowired
	private RoleServiceImpl roleService;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionServiceImpl permissionService;
	@Autowired
	private UserRoleServiceImpl userRoleService;
	
	@Autowired
	private ApplyPlatformServiceImpl applyPlatformService;
	
	@Autowired
	private DataSyncServiceImpl dataSyncServiceImpl;
	/**
	 * @param applyPlatformService the applyPlatformService to set
	 */
	public void setApplyPlatformService(
			ApplyPlatformServiceImpl applyPlatformService) {
		this.applyPlatformService = applyPlatformService;
	}
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public void setUserRoleService(UserRoleServiceImpl userRoleService) {
		this.userRoleService = userRoleService;
	}

	public void setPermissionService(PermissionServiceImpl permissionService) {
		this.permissionService = permissionService;
	}

	public void setRolePermissionService(RolePermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}

	public void setRoleService(RoleServiceImpl roleService) {
		this.roleService = roleService;
	}

	/**
	 * 条件查询角色信息
	 * @param modelMap
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping(params = ("method=role"))
	public String role( ModelMap modelMap ,HttpServletRequest request,RoleVO role){
		/*String hqlString = "";
		if( role.getName() != null && !"".equals(role.getName().trim()) ){
			hqlString = " and name like '%" + role.getName().trim() + "%' ";
		}*/
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
		recTotal = roleService.getRoleCount(role);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<RoleVO> roleList = roleService.findRoles((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),role);
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		modelMap.put("roleList", roleList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("role",role );
		return "role/role";
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @param response
	 */
	@RequestMapping(params = ("method=del") )
	public void del( Long id ,HttpServletResponse response){
		ApplyPlatform apply = applyPlatformService.loadByRoleId(id);
		List<UserRole> userRoleList = userRoleService.findUserRoleByRoleId(id);
		if( userRoleList != null && userRoleList.size() > 0 ){
			try {
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<RolePermission> list = rolePermissionService.findPermsByRoleId(id);
		if( list != null && list.size() > 0 ){
			for( RolePermission rp : list ){
				rolePermissionService.delete(rp);
			}
		}
		roleService.del(id);
		
		
		
		String url = null;
		if(apply != null){
			url = apply.getUrl();
			if(url !=null && !"".equals(url) ){
				dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_ROLE, ConfigConstants.ACTION_TYPE_DELETE, id);
			}
		}
		new MySecurityMetadataSource(userService, rolePermissionService);
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进入修改角色页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=data") )
	public String data( Long id,ModelMap modelMap ){
		modelMap.put("role", roleService.loadById(id));
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		return "role/role_update";
	}
	
	/**
	 * 进入添加角色页面
	 * @return
	 */
	@RequestMapping(params = ("method=add") )
	public String add(ModelMap modelMap){
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		return "role/role_add";
	}
	
	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	@RequestMapping(params = ("method=save") )
	public String save( Role role ){
		roleService.save(role);
		Role newRole = roleService.loadByName(role.getName());
		if(newRole !=null){
			ApplyPlatform apply = applyPlatformService.loadByRoleId(newRole.getId());
			String url = null;
			if(apply != null){
				url = apply.getUrl();
				if(url !=null && !"".equals(url) ){
				dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_ROLE, ConfigConstants.ACTION_TYPE_INSERT, newRole);
				}
			}
		}
		return "redirect:role.do?method=detail&roleId=" + newRole.getId();
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping(params = ("method=update") )
	public String update( Role role1 ){
		
		Long roleId = role1.getId();
		Role role = null;
		if(roleId != null){
			role = roleService.loadById(roleId);
		}
		MainUtils.copy(role1, role);
		roleService.update(role);
		
		ApplyPlatform apply = applyPlatformService.loadByRoleId(role.getId());
		String url = null;
		if(apply != null){
			url = apply.getUrl();
			if(url !=null && !"".equals(url) ){
			dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_ROLE, ConfigConstants.ACTION_TYPE_UPDATE, role);
			}
		}
		return "redirect:role.do?method=role";
	}
	
	/**
	 * 进入角色授权页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=detail"))
	public String detail(RolePermissionVO vo, ModelMap modelMap,HttpServletRequest request) {
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
		String platformId=request.getParameter("platformId");
		if(platformId !=null&&!"".equals(platformId)){
			vo.setPlatformId(Long.parseLong(platformId));
		}
		recTotal = rolePermissionService.getRoleCount(vo);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<RolePermissionVO> rolePermList = rolePermissionService.findPermsByRole((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),vo);
		RoleVO role = new RoleVO();
		role.setId(vo.getRoleId());
		List<RoleVO> roleList = roleService.findRoles(0,1,role);
		RoleVO roleVo = new RoleVO();
		if(roleList != null && roleList.size() > 0){
			roleVo = roleList.get(0);
		}
		//Role role = roleService.loadById(vo.getId());
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		modelMap.put("rolePermList", rolePermList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("role",roleVo );
		modelMap.put("vo",vo );
		return "role/role_permission";
	}
	/*@RequestMapping(params = ("method=detail"))
	public String detail(Long id, ModelMap modelMap) {
		Role role = roleService.loadById(id);
		List<RolePermission> rolePermList = rolePermissionService.findPermsByRoleId(id);
		List<Permission> permList = new ArrayList<Permission>();
		List<Permission> allPermList = permissionService.findAllperms();
		if (rolePermList != null && rolePermList.size() > 0) {
			for (RolePermission rolePerm : rolePermList) {
				Permission perm = permissionService.loadById(rolePerm.getPermissionId());
				allPermList.remove(perm);
				permList.add(perm);
			}
		}
		modelMap.put("permList", permList);
		modelMap.put("unPermList", allPermList);
		modelMap.put("role", role);
		return "role/role_permission";
	}*/
	/**
	 * 授权
	 * @param permissionId
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(params = ("method=warrant"))
	public void warrant(Long permissionId, Long roleId,HttpServletResponse response) {
		try {
			if(permissionId == null || permissionId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			if(roleId == null || roleId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			RolePermission roleperm = rolePermissionService.findRolePermByRoleIDAndPermId(roleId, permissionId);
			if(roleperm != null){
				rolePermissionService.delete(roleperm);
			}
			RolePermission rp = new RolePermission();
			rp.setRoleId(roleId);
			rp.setPermissionId(permissionId);
			rolePermissionService.save(rp);
			
			ApplyPlatform apply = applyPlatformService.loadByRoleId(roleId);
			String url = null;
			if(apply != null){
				url = apply.getUrl();
				if(url !=null && !"".equals(url) ){
				dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_ROLE_RESC, ConfigConstants.ACTION_TYPE_INSERT, rp);
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
	public void notWarrant(Long permissionId, Long roleId,HttpServletResponse response) {
		try {
			if(permissionId == null || permissionId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			if(roleId == null || roleId <= 0){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			RolePermission roleperm = rolePermissionService.findRolePermByRoleIDAndPermId(roleId, permissionId);
			if(roleperm == null){
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			}
			rolePermissionService.delete(roleperm);
			
			ApplyPlatform apply = applyPlatformService.loadByRoleId(roleId);
			String url = null;
			if(apply != null){
				url = apply.getUrl();
				if(url !=null && !"".equals(url) ){
				dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_ROLE_RESC, ConfigConstants.ACTION_TYPE_DELETE, roleperm);
				}
			}
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存角色授权信息
	 * @param perms
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(params = ("method=savePrems"))
	public void savePrems(String perms, Long roleId,HttpServletResponse response) {
		List<RolePermission> rolePermList = rolePermissionService.findPermsByRoleId(roleId);
		for ( RolePermission rolePerm : rolePermList ){
			rolePermissionService.delete(rolePerm);
		}
		if( perms != null && !"".equals(perms.trim()) ){
			String[] permsIds = perms.split(",");
			for( int i = 0; i < permsIds.length;i++ ){
				RolePermission roleperm = new RolePermission();
				roleperm.setRoleId(roleId);
				roleperm.setPermissionId(Long.parseLong(permsIds[i]));
				rolePermissionService.save(roleperm);
			}
		}
		new MySecurityMetadataSource(userService, rolePermissionService);
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 验证角色名称
	 * @param name
	 * @param response
	 */
	@RequestMapping( params = ("method=checkName") )
	public void checkName(String name , HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Role role = roleService.loadByName(name);
			if( role == null )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID验证角色名称
	 * @param name
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkNameById") )
	public void checkNameById(String name,String id,HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Role role = roleService.loadByName(name);
			if( role == null || id.equals(role.getId().toString()))
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
}
