package com.haoyao.goods.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.haoyao.goods.model.Permission;
import com.haoyao.goods.model.Role;
import com.haoyao.goods.model.RolePermission;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.PermissionServiceImpl;
import com.haoyao.goods.service.RolePermissionService;
import com.haoyao.goods.service.UserRoleServiceImpl;
import com.haoyao.goods.service.UserServiceImpl;


/**
 * Title: MyCasAssertionUserDetailsService.java
 * 
 * Description: 描述
 * 
 * @author panyd-001
 * @version 1.0
 * @created 2014年9月24日 上午10:07:15
 */

public class MyCasAssertionUserDetailsService extends
		AbstractCasAssertionUserDetailsService {
	
    private static final String NON_EXISTENT_PASSWORD_VALUE = "NO_PASSWORD";


    private boolean convertToUpperCase = true;

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionServiceImpl permissionService;
	@Autowired
	private UserRoleServiceImpl userRoleService;
	/**
	 * 构造函数
	 */

	public MyCasAssertionUserDetailsService() {

	}

	/**
	 * @param assertion
	 * @return
	 * @see org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService#loadUserDetails(org.jasig.cas.client.validation.Assertion)
	 */

	@Override
	protected UserDetails loadUserDetails(Assertion assertion) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        
		boolean enabled = true;                //用户帐号是否已启用
        boolean accountNonExpired = true;        //是否过期
        boolean credentialsNonExpired = true;   //用户凭证是否已经过期
        boolean accountNonLocked = true; //是否锁定 
		String username = assertion.getPrincipal().getName();
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
     * Converts the returned attribute values to uppercase values.
     *
     * @param convertToUpperCase true if it should convert, false otherwise.
     */
    public void setConvertToUpperCase(final boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }
    
	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public RolePermissionService getRolePermissionService() {
		return rolePermissionService;
	}

	public void setRolePermissionService(RolePermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}

	public PermissionServiceImpl getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionServiceImpl permissionService) {
		this.permissionService = permissionService;
	}

	public UserRoleServiceImpl getUserRoleService() {
		return userRoleService;
	}

	public void setUserRoleService(UserRoleServiceImpl userRoleService) {
		this.userRoleService = userRoleService;
	}
}
