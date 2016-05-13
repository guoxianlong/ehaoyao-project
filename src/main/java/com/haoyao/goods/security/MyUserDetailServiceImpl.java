package com.haoyao.goods.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.haoyao.goods.model.Permission;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.RolePermission;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.PermissionServiceImpl;
import com.haoyao.goods.service.RolePermissionService;
import com.haoyao.goods.service.UserRoleServiceImpl;
import com.haoyao.goods.service.UserServiceImpl;

/**
 * 权限验证
 * @author Administrator
 *
 */
public class MyUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionServiceImpl permissionService;
	@Autowired
	private UserRoleServiceImpl userRoleService;
	
	/**
	 * 点击登录后会调用此方法，
	 * 获取用户的权限
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		//System.out.println("---------MyUserDetailServiceImpl:loadUserByUsername------正在加载用户名和密码，用户名为："+username);
		
		boolean enabled = true;                //用户帐号是否已启用
        boolean accountNonExpired = true;        //是否过期
        boolean credentialsNonExpired = true;   //用户凭证是否已经过期
        boolean accountNonLocked = true; //是否锁定 
		
		User user = userService.loadUserByUserName(username);
		if( user == null ){
			enabled = false;
		}else if( user.getLockStatus() == 1 ){
			accountNonLocked = false;
		}
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		//如果你使用资源和权限配置在xml文件中，如：<intercept-url pattern="/user/admin" access="hasRole('ROLE_ADMIN')"/>；
		//并且也不想用数据库存储，所有用户都具有相同的权限的话，你可以手动保存角色(如：预订网站)。
		//authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		List<Role> roles = userRoleService.findUserRolesByUsername(username);
		for(Role role : roles){
			GrantedAuthority ga = new SimpleGrantedAuthority(role.getName());
			authorities.add(ga);
			List<RolePermission> perms = rolePermissionService.findPermsByRoleId(role.getId());
			for( RolePermission rolePerm : perms ){
				Permission perm = permissionService.loadById(rolePerm.getPermissionId());
				GrantedAuthority perga = new SimpleGrantedAuthority(perm.getUrl());
				authorities.add(perga);
			}
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassWord(), 
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked, 
				authorities);
	}
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	public void setRolePermissionService(RolePermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}
	public void setUserRoleService(UserRoleServiceImpl userRoleService) {
		this.userRoleService = userRoleService;
	}
	public void setPermissionService(PermissionServiceImpl permissionService) {
		this.permissionService = permissionService;
	}

}
