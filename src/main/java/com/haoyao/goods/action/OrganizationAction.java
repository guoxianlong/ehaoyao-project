package com.haoyao.goods.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyao.goods.model.Organization;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.OrganizationServiceImpl;
import com.haoyao.goods.service.UserServiceImpl;

@Controller
@RequestMapping("/org.do")
public class OrganizationAction extends BaseAction{

	@Autowired
	private OrganizationServiceImpl organizationService;
	@Autowired
	private UserServiceImpl userService;

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public void setOrganizationService(OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}
	
	/**
	 * 条件查询机构信息
	 * @param modelMap
	 * @param request
	 * @param org
	 * @return
	 */
	@RequestMapping(params = ("method=organization") )
	public String organization( ModelMap modelMap ,HttpServletRequest request,Organization org){
		StringBuilder hqlString = new StringBuilder(100);
		if( org.getCode() != null && !"".equals(org.getCode().trim()) ){
			hqlString.append(" and code like '%" + org.getCode().trim() + "%' ");
		}
		if( org.getName() != null && !"".equals(org.getName().trim()) ){
			hqlString.append(" and name like '%" + org.getName().trim() + "%' ");
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
		recTotal = organizationService.getOrgCount(hqlString.toString());
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<Organization> orgList = organizationService.findOrgList((this.getPageno() - 1) * this.getPageSize(),this.getPageSize(),hqlString.toString() );
		modelMap.put("orgList", orgList);
		
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("org", org);
		return "organization/organization";
	}
	
	/**
	 * 进入添加机构页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=add") )
	public String add( ModelMap modelMap ){
		Long code = organizationService.getNewCode();
		modelMap.put("code", code);
		return "organization/organization_add";
	}
	
	/**
	 * 保存机构信息
	 * @param org
	 * @return
	 */
	@RequestMapping(params = ("method=save") )
	public String save( Organization org ){
		organizationService.save(org);
		return "redirect:org.do?method=organization";
	}
	
	/**
	 * 进入修改机构页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=data") )
	public String data( Long id,ModelMap modelMap ){
		Organization org = organizationService.loadById(id);
		modelMap.put("org", org);
		return "organization/organization_update";
	}
	
	/**
	 * 修改机构
	 * @param org
	 * @return
	 */
	@RequestMapping(params = ("method=update") )
	public String update( Organization org ){
		organizationService.update(org);
		return "redirect:org.do?method=organization";
	}
	
	/**
	 * 删除机构
	 * @param id
	 * @param response
	 */
	@RequestMapping(params = ("method=del") )
	public void delete( Long id, HttpServletResponse response ){
		List<User> userList = userService.findUserByOrgId(id);
		if( userList != null && userList.size() > 0 ){
			try {
				response.getWriter().print("{\"result\":\"false\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		organizationService.delete(organizationService.loadById(id));
		try {
			response.getWriter().print("{\"result\":\"true\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 机构详细信息
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=detail") )
	public String detail( Long id,ModelMap modelMap ){
		Organization org = organizationService.loadById(id);
		modelMap.put("org", org);
		return "organization/organization_detail";
	}
	
	/**
	 * 验证机构名称
	 * @param name
	 * @param response
	 */
	@RequestMapping( params = ("method=checkName") )
	public void checkName(String name , HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Organization org = organizationService.loadByName(name);
			if( org == null )
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id验证机构名称 
	 * @param name
	 * @param id
	 * @param response
	 */
	@RequestMapping( params = ("method=checkNameById") )
	public void checkNameById(String name , String id, HttpServletResponse response){
		try{
			name = java.net.URLDecoder.decode(name, "UTF-8");
			Organization org = organizationService.loadByName(name);
			if( org == null || id.equals(org.getId().toString()))
				response.getWriter().print("{\"result\":\"true\"}");
			else
				response.getWriter().print("{\"result\":\"false\"}");
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
}
