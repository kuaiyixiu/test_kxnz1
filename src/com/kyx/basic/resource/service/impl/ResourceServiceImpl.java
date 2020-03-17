package com.kyx.basic.resource.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.resource.mapper.ResourceMapper;
import com.kyx.basic.resource.model.Resource;
import com.kyx.basic.resource.service.ResourceService;
import com.kyx.basic.resourcesrole.mapper.ResourcesRoleMapper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	@javax.annotation.Resource
	private ResourceMapper resourceMapper;

	@javax.annotation.Resource
	private ResourcesRoleMapper resourcesRoleMapper;

	private static String USING = "1";

	private static String FORBID = "0";

	@Override
	public List<Resource> getUserResource(Integer roleId) {

		return resourceMapper.getUserResource(roleId);
	}

	@Override
	public List<Resource> findAll() {
		return resourceMapper.getAll();
	}

	@Override
	public List<Resource> getMenu(Boolean isAdmin,List<SimpleGrantedAuthority> list,Integer parentid) {
		List<Resource> all = this.findAll();
		List<Resource> menuList = new ArrayList<Resource>();
		for (Resource resource : all) {
			if (hasResKey(isAdmin,list, resource.getResKey())) {
				if (parentid != null) {
					if (resource.getParentId().intValue() == parentid.intValue())
						menuList.add(resource);
				} else
					menuList.add(resource);
			}

		}
		return menuList;
	}

	/**
	 * 判断角色是否拥有权限
	 * @param isAdmin 是否是管理员
	 * 
	 * @param list
	 * @param reskey
	 * @return
	 */
	private boolean hasResKey(Boolean isAdmin, List<SimpleGrantedAuthority> list, String reskey) {
		//
		if(isAdmin)
			return true;
		for (SimpleGrantedAuthority authority : list) {
			if (authority.getAuthority().substring(5).equals(reskey))
				return true;
		}
		return false;
	}

	@Override
	public GrdData queryMenu(Resource resource, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		// 默认展示时id不传，则查询顶级菜单一级
		if (resource.getParentId() == null) {
			resource.setParentId(Resource.DEFAULT_TOP_PARENT_ID);
		}

		// 如果是返回的
		if (StringUtils.equals(resource.getQueryType(), "back")) {
			resource = setParamById(resource);
		}
		List<Resource> menuList = resourceMapper.queryMenu(resource);
		return new GrdData((long) menuList.size(), menuList);
	}

	@Override
	public RetInfo saveMenu(Resource resource) {
		resource.setEnabled(USING);
		resource.setCreateDate(new Date());
		resource.setType(Resource.DEFAULT_TYPE);
		resource.setName(resource.getMenuName());

		if (resourceMapper.insertSelective(resource) > 0) {
			return RetInfo.Success("添加成功");
		}

		return RetInfo.Error("添加失败");
	}

	@Override
	public Resource selectByPrimaryKey(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo editMenu(Resource resource) {
		resource.setName(resource.getMenuName());
		if (resourceMapper.updateByPrimaryKeySelective(resource) > 0) {
			return RetInfo.Success("编辑成功");
		}

		return RetInfo.Error("编辑失败");
	}

	@Override
	public RetInfo delData(String ids) {
		String[] idArr = ids.split(";");
		int count = 0;
		for (String id : idArr) {
			Resource resource = new Resource();
			resource.setId(Integer.valueOf(id));
			resource.setEnabled(FORBID);
			// 如果拥有子集，则略过
			if (hasChilren(id)) {
				continue;
			}
			count += resourceMapper.updateByPrimaryKeySelective(resource);
			resourcesRoleMapper.deleteByRescId(Integer.valueOf(id));
		}

		if (count == idArr.length) {
			return RetInfo.Success("删除成功");
		}

		return RetInfo.Error("删除失败");
	}

	/**
	 * 是否有子集
	 * 
	 * @param id
	 * @return
	 */
	private boolean hasChilren(String id) {
		Resource resource = new Resource();
		resource.setParentId(Integer.valueOf(id));
		List<Resource> childrens = resourceMapper.queryMenu(resource);

		return childrens.size() > 0;
	}

	/**
	 * 设置参数 按照id查询出上级元素的parentid
	 * 
	 * @param resource
	 * @return
	 */
	private Resource setParamById(Resource resource) {
		Resource thisResource = selectByPrimaryKey(resource.getParentId());
		Integer pId = Resource.DEFAULT_TOP_PARENT_ID;
		if (thisResource != null) {
			pId = thisResource.getParentId();
		}
		resource.setParentId(pId);

		return resource;
	}

	@Override
	public List<Resource> getMenuByRoleId(Integer id) {
		List<Resource> list = resourceMapper.getMenuByRoleId(id);
		return list;
	}
}
