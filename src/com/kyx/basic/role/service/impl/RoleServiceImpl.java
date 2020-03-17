package com.kyx.basic.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.resource.service.ResourceService;
import com.kyx.basic.resourcesrole.service.ResourcesRoleService;
import com.kyx.basic.role.mapper.RoleMapper;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.role.service.RoleService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private ResourceService resourceService;
	@Resource
	private ResourcesRoleService resourcesRoleService;
	@Resource
	private UserService userService;
	@Override
	public GrdData getRolesInfo(Role role, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Role> list=roleMapper.getRolesInfo(role);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public Role selectByPrimaryKey(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo saveData(Role role) {
		RetInfo retInfo;
		int count=0;
		//处理checkbox内容
		role.setEnable(role.getEnable()==null?"0":role.getEnable());
		if(role.getId()==null){//新增
			count=roleMapper.insertSelective(role);
		}else{
			count=roleMapper.updateByPrimaryKey(role);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public RetInfo delData(String ids,Integer shopId) {
		RetInfo retInfo;
		String[] idArr=ids.split(";");
		//先判断是否被使用
		String currentDbName=Dbs.getDbName();
		Dbs.setDbName(Dbs.getMainDbName());
		Boolean canDel=true;
		for(int i=0;i<idArr.length;i++){
			List<User> list=userService.getByShopId(shopId);
			int c=userService.getByRoleId(Integer.valueOf(idArr[i]),shopId);
			if(c>0){
				canDel=false;
				break;
			}
		}
		if(!canDel){
			retInfo=new RetInfo("error","删除失败，角色已被使用！");
			return retInfo;
		}
		Dbs.setDbName(currentDbName);
		int count=0;
		for(int i=0;i<idArr.length;i++){
			count=count+roleMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
			resourcesRoleService.deleteByRoleId(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

	@Override
	public List<Role> getAll() {
		
		return roleMapper.getAll();
	}

	@Override
	public String getMenuInfoByRoleId(Integer id) {
		//查询所有的菜单信息
		List<com.kyx.basic.resource.model.Resource> list=resourceService.getMenuByRoleId(id);
		//查询一级菜单
		List<com.kyx.basic.resource.model.Resource> topList=getLevelMenu(0,list);
		List<Map> treeList=new ArrayList<Map>();
		for(com.kyx.basic.resource.model.Resource  res:topList){
			Map map=new HashMap();
			map.put("title", res.getName());
			map.put("value", res.getId());
			map.put("checked", res.getRoleId()!=null?true:false);
			map.put("data", getChildMap(res.getId(),list));
			treeList.add(map);
		}
		return JSONObject.toJSON(treeList).toString();
	}
    /**
     * 获取树形结构子结构
     * @param pid
     * @param list
     * @return
     */
	private List<Map> getChildMap(Integer pid, List<com.kyx.basic.resource.model.Resource> list) {
		List<com.kyx.basic.resource.model.Resource> levelList=getLevelMenu(pid,list);
		List<Map> treeList=new ArrayList<Map>();
		for(com.kyx.basic.resource.model.Resource  res:levelList){
			Map map=new HashMap();
			map.put("title", res.getName());
			map.put("value", res.getId());
			map.put("checked", res.getRoleId()!=null?true:false);
			map.put("data", getChildMap(res.getId(),list));
			treeList.add(map);
		}
		return treeList;
	}

	private List<com.kyx.basic.resource.model.Resource> getLevelMenu(int i, List<com.kyx.basic.resource.model.Resource> list) {
		List<com.kyx.basic.resource.model.Resource> topList=new ArrayList<com.kyx.basic.resource.model.Resource>();
		for(com.kyx.basic.resource.model.Resource  res:list){
			if(res.getParentId().intValue()==i)
				topList.add(res);
		}
		return topList;
	}

}
