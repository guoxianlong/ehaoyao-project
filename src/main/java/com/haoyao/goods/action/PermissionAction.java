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
import com.haoyao.goods.dto.PermissionVO;
import com.haoyao.goods.model.ApplyPlatform;
import com.haoyao.goods.model.Permission;
import com.haoyao.goods.model.RolePermission;
import com.haoyao.goods.service.ApplyPlatformServiceImpl;
import com.haoyao.goods.service.DataSyncServiceImpl;
import com.haoyao.goods.service.PermissionServiceImpl;
import com.haoyao.goods.service.RolePermissionService;
import com.haoyao.goods.util.MainUtils;

@Controller
@RequestMapping("/permission.do")
public class PermissionAction extends BaseAction{
	
	@Autowired
	private PermissionServiceImpl permissionService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private ApplyPlatformServiceImpl applyPlatformService;
	
	
	@Autowired
	private DataSyncServiceImpl dataSyncServiceImpl;
	
	public void setPermissionService(PermissionServiceImpl permissionService) {
		this.permissionService = permissionService;
	}
	/**
	 * @param applyPlatformService the applyPlatformService to set
	 */
	public void setApplyPlatformService(
			ApplyPlatformServiceImpl applyPlatformService) {
		this.applyPlatformService = applyPlatformService;
	}

	/**
	 * 条件查询资源信息
	 * @param modelMap
	 * @param request
	 * @param perm
	 * @return
	 */
	@RequestMapping(params = ("method=permission"))
	public String permission( ModelMap modelMap ,HttpServletRequest request,PermissionVO vo){
		/*StringBuilder hqlString = new StringBuilder(100);
		if( perm.getName() != null && !"".equals(perm.getName().trim()) ){
			hqlString.append(" and name like '%" + perm.getName().trim() + "%' ");
		}
		if( perm.getUrl() != null && !"".equals(perm.getUrl().trim()) ){
			hqlString.append(" and url like '%" + perm.getUrl().trim() + "%' ");
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
		recTotal = permissionService.getPermCount(vo);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<PermissionVO> permissionList = permissionService.findPermissions((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),vo);
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		modelMap.put("permissionList", permissionList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("permission", vo);
		return "permission/permission";
	}
	
	/**
	 * 删除资源
	 * @param id
	 * @param response
	 */
	@RequestMapping(params = ("method=del") )
	public void del( Long id, HttpServletResponse response ){
		ApplyPlatform apply = applyPlatformService.loadByRescId(id);
		List<RolePermission> list = rolePermissionService.findRolePermByPermId(id);
		if( list != null && list.size() > 0 ){
			try {
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		permissionService.del(id);
		
		
		String url = null;
		if(apply != null){
			url = apply.getUrl();
			if(url !=null && !"".equals(url) ){
			dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_RESC, ConfigConstants.ACTION_TYPE_DELETE, id);
		}}
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进入修改资源页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=data") )
	public String data( Long id,ModelMap modelMap ){
		modelMap.put("permission", permissionService.loadById(id));
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		return "permission/permission_update";
	}
	
	/**
	 * 进入添加资源页面
	 * @return
	 */
	@RequestMapping(params = ("method=add") )
	public String add(ModelMap modelMap ,HttpServletRequest request){
		List<ApplyPlatform> apfls = applyPlatformService.getPlatformList();
		modelMap.put("apfls", apfls);
		return "permission/permission_add";
	}
	
	/**
	 * 保存资源信息
	 * @param permission
	 * @return
	 */
	@RequestMapping(params = ("method=save") )
	public String save( Permission permission ){
		permissionService.save(permission);
		
		ApplyPlatform apply = applyPlatformService.loadByRescId(permission.getId());
		String url = null;
		if(apply != null){
			url = apply.getUrl();
			if(url !=null && !"".equals(url) ){
			dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_RESC, ConfigConstants.ACTION_TYPE_INSERT, permission);
		}}
		
		return "redirect:permission.do?method=permission";
	}
	
	/**
	 * 修改资源
	 * @param permission
	 * @return
	 */
	@RequestMapping(params = ("method=update") )
	public String update( Permission permission1 ){
		
		Permission permission = permissionService.loadById(permission1.getId());
		
		MainUtils.copy(permission1,permission);
		permissionService.update(permission);
		
		ApplyPlatform apply = applyPlatformService.loadByRescId(permission.getId());
		String url = null;
		if(apply != null){
			url = apply.getUrl();
			if(url !=null && !"".equals(url) ){
			dataSyncServiceImpl.sendDataToTargetSys(url, ConfigConstants.DATA_TYPE_RESC, ConfigConstants.ACTION_TYPE_UPDATE, permission);
		}}
		
		return "redirect:permission.do?method=permission";
	}
	
	/**
	 * 验证资源名称
	 * @param name
	 * @param response
	 */
	@RequestMapping( params = ("method=checkName") )
	public void checkName(String name , HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Permission perm = permissionService.loadByName(name);
			if( perm == null )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 验证url
	 * @param url
	 * @param response
	 */
	@RequestMapping( params = ("method=checkUrl") )
	public void checkUrl(String url , HttpServletResponse response){
		Permission perm = permissionService.loadByUrl(url);
		try{
			if( perm == null )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id验证资源名称
	 * @param name
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkNameById") )
	public void checkNameById(String name,String id,HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Permission perm = permissionService.loadByName(name);
			if( perm == null || id.equals(perm.getId().toString()))
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id验证资源url
	 * @param url
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkUrlById") )
	public void checkUrlById(String url,String id,HttpServletResponse response){
		Permission perm = permissionService.loadByUrl(url);
		try{
			if( perm == null || id.equals(perm.getId().toString()))
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
}
