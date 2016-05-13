package com.haoyao.goods.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyao.goods.model.ApplyPlatform;
import com.haoyao.goods.service.ApplyPlatformServiceImpl;

@Controller
@RequestMapping("/applyPlatform.do")
public class ApplyPlatformAction extends BaseAction{

	@Autowired
	private ApplyPlatformServiceImpl applyPlatformService;

	/**
	 * @param applyPlatformService the applyPlatformService to set
	 */
	public void setApplyPlatformService(
			ApplyPlatformServiceImpl applyPlatformService) {
		this.applyPlatformService = applyPlatformService;
	}

	/**
	 * 条件查询应用平台信息
	 * @param modelMap
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping(params = ("method=getApplyPlatformList"))
	public String getApplyPlatformList( ModelMap modelMap ,HttpServletRequest request,ApplyPlatform vo){
		String hqlString = "";
		if( vo.getName() != null && !"".equals(vo.getName().trim()) ){
			hqlString = " and name like '%" + vo.getName().trim() + "%' ";
		}
		if(vo.getUrl() != null && !"".equals(vo.getUrl().trim()) ){
			hqlString = " and url like '%" + vo.getUrl().trim() + "%' ";
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
		recTotal = applyPlatformService.getPlatformCount(hqlString);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<ApplyPlatform> platformList = applyPlatformService.getPlatformList((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),hqlString);
		modelMap.put("platformList", platformList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("vo",vo);
		return "applyPlatform/apply_platform";
	}
	
	/**
	 * 进入添加机构页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=add") )
	public String add( ModelMap modelMap){
		return "applyPlatform/apply_platform_add";
	}
	
	/**
	 * 保存机构信息
	 * @param org
	 * @return
	 */
	@RequestMapping(params = ("method=save") )
	public String save( ApplyPlatform apf){
		applyPlatformService.save(apf);
		return "redirect:applyPlatform.do?method=getApplyPlatformList";
	}
	
	/**
	 * 进入修改页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=data"))
	public String data(Long id, ModelMap modelMap) {
		ApplyPlatform apf = applyPlatformService.getApplyPlatformById(id);
		modelMap.put("apf", apf);
		return "applyPlatform/apply_platform_update";
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping(params = ("method=update"))
	public String update(ApplyPlatform apf) {
		applyPlatformService.update(apf);
		return "redirect:applyPlatform.do?method=getApplyPlatformList";
	}
	/**
	 * 根据ID验证资源名称
	 * @param name
	 * @param response
	 */
	@RequestMapping( params = ("method=checkNameById") )
	public void checkNameById(String id,String name, HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			ApplyPlatform apf = applyPlatformService.loadByName(name);
			if( apf == null || id.equals(apf.getId().toString()))
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	/**
	 * 验证资源名称
	 * @param name
	 * @param response
	 */
	@RequestMapping( params = ("method=checkName") )
	public void checkName(String name, HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			ApplyPlatform apf = applyPlatformService.loadByName(name);
			if( apf == null)
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(params = ("method=del"))
	public void del(Long id,HttpServletResponse response) {
		/*List<RolePermission> list = rolePermissionService.findRolePermByPermId(id);
		if( list != null && list.size() > 0 ){
			try {
				response.getWriter().print("{\"result\":\"false\"}");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		applyPlatformService.delete(applyPlatformService.getApplyPlatformById(id));
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
