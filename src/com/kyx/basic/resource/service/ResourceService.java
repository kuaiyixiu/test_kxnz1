package com.kyx.basic.resource.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.resource.model.Resource;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;

public interface ResourceService {
	/**
	 * 根据用户角色查询资源
	 * 
	 * @param roleId
	 * @return
	 */
	List<Resource> getUserResource(Integer roleId);

	/**
	 * 查询所有资源
	 * 
	 * @return
	 */
	List<Resource> findAll();

	/**
	 * 根据角色查询菜单 
	 * @param isAdmin 
	 * @param list
	 * @param parentid
	 * @return
	 */
	List<com.kyx.basic.resource.model.Resource> getMenu(Boolean isAdmin, List<SimpleGrantedAuthority> list, Integer parentid);

	/**
	 * 查询菜单
	 * 
	 * @param resource
	 * @return
	 */
	GrdData queryMenu(Resource resource, Pager pager);

	@Transactional
	RetInfo saveMenu(Resource resource);

	Resource selectByPrimaryKey(Integer id);

	@Transactional
	RetInfo editMenu(Resource resource);

	@Transactional
	RetInfo delData(String ids);
	/**
	 * 根据角色查询菜单信息
	 * @param id
	 * @return
	 */
	List<Resource> getMenuByRoleId(Integer id);
}
