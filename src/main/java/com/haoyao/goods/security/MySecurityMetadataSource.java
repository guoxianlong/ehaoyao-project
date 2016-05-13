package com.haoyao.goods.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.haoyao.goods.model.Permission;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.service.RolePermissionService;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.ResourceMap;

/**
 * @description  资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private UserServiceImpl userService;
	private RolePermissionService rolePermissionService;
	private AntPathMatcher urlMatcher = new AntPathMatcher();
	private List<Permission> permissions = null;
	
	public MySecurityMetadataSource(UserServiceImpl userService,RolePermissionService rolePermissionService) {
		this.userService = userService;
		this.rolePermissionService = rolePermissionService;
		loadResourcesDefine();
	}
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 加载所有资源和权限的关系
	 */
	public void loadResourcesDefine(){
		ResourceMap.clean();
		//加载所有的角色
		List<Role> roles = userService.findAllRoles();
		for(Role role : roles){
			//获取角色的资源信息
			permissions = rolePermissionService.findResourcesByRoleName(role.getName());
			for(Permission permission : permissions){
				Collection<ConfigAttribute> configAttributes = null;
				ConfigAttribute configAttribute = new SecurityConfig(role.getName());
				if(ResourceMap.containsKey(permission.getUrl())){
					configAttributes = ResourceMap.get(permission.getUrl());
					configAttributes.add(configAttribute);
				}else{
					configAttributes = new ArrayList<ConfigAttribute>() ;
					configAttributes.add(configAttribute);
				}
				ResourceMap.put(permission.getUrl(), configAttributes);
			}
		}
	}
	/* 
	 * 根据请求的资源地址，获取它所拥有的权限
	 * 返回请求的资源需要的权限
	 */
	public Collection<ConfigAttribute> getAttributes(Object obj)
			throws IllegalArgumentException {
		loadResourcesDefine();
		//获取请求的url方法
		String url = ((FilterInvocation)obj).getRequestUrl();
		if( url.indexOf("&") != -1 )
			url = url.substring(0,url.indexOf("&"));
		//获取请求的url资源
		String methodUrl = "";
		if( url.indexOf("?") != -1 )
			methodUrl = url.substring(0, url.indexOf("?"));
		
		Iterator<String> it = ResourceMap.ketSetIterator();
		Collection<ConfigAttribute> urlSet = new HashSet<ConfigAttribute>();
		while(it.hasNext()){
			String _url = it.next();
			//如果该拥有url资源
			if(!"".equals(methodUrl) && urlMatcher.match(methodUrl, _url)){
				Iterator<ConfigAttribute> urlIt = ResourceMap.get(_url).iterator();
				while( urlIt.hasNext() ){
					urlSet.add(urlIt.next());
				}
			//如果拥有方法资源 
			}else if(urlMatcher.match(url, _url)){
				Iterator<ConfigAttribute> urlIt = ResourceMap.get(_url).iterator();
				while( urlIt.hasNext() ){
					urlSet.add(urlIt.next());
				}
			} 
		}
		//返回所有的角色
		if( urlSet != null && urlSet.size() > 0 )
			return urlSet;
		return null;
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}
	
}
