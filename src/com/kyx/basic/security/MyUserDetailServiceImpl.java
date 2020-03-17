package com.kyx.basic.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kyx.basic.db.Dbs;
import com.kyx.basic.resource.service.ResourceService;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.service.UserService;


/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 权限验证类
 * @author 严大灯
 * @data 2019-3-28 下午6:17:27
 * @Descripton
 */
@Service("myUserDetailService")
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	@Resource
	private UserService userService;
	@Resource
	private ResourceService resourceService ;
	@Resource
	private ShopsService shopsService;
	// 登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//取得用户的权限
		com.kyx.basic.user.model.User users = userService.querySingleUser(username);
		if  (users==null)  
            throw new UsernameNotFoundException(username+" not exist!");  
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		// 封装成spring security的user
		User userdetail = new User(
				users.getUserName(), 
				users.getUserPassword(),
				true, 
				true, 
				true,
				true, 
				grantedAuths	//用户的权限
			);
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(com.kyx.basic.user.model.User user) {
		Shops shops=shopsService.getById(user.getShopId());
		//切换到子类数据库中获取角色菜单信息
		Dbs.setDbName(shops.getDbName());
		List<com.kyx.basic.resource.model.Resource> resources = resourceService.getUserResource(user.getRoleId());
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (com.kyx.basic.resource.model.Resource res : resources) {
			// TODO: 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
			// 关联代码：applicationContext-security.xml
			// 关联代码：MySecurityMetadataSource#loadResourceDefine
			authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getResKey()));
		}
		return authSet;
	}
}