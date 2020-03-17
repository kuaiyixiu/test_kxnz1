package com.kyx.basic.resourcesrole.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.basic.resourcesrole.mapper.ResourcesRoleMapper;
import com.kyx.basic.resourcesrole.model.ResourcesRole;
import com.kyx.basic.resourcesrole.service.ResourcesRoleService;
import com.kyx.basic.security.MySecurityMetadataSource;
import com.kyx.basic.util.RetInfo;
import com.mysql.jdbc.StringUtils;
@Service("resourcesRoleService")
public class ResourcesRoleServiceImpl implements ResourcesRoleService {
    @Resource
	private ResourcesRoleMapper resourcesRoleMapper;
    @Resource
    private MySecurityMetadataSource securityMetadataSource;
	@Override
	public RetInfo savePermission(Integer roleId, String resIds) {
		RetInfo retInfo;
		if(roleId==null){
			retInfo=new RetInfo("error","保存失败");
			return retInfo;
		}
		//先根据角色删除
		resourcesRoleMapper.deleteByRoleId(roleId);
		String[] ids=resIds.split(",");
		List<ResourcesRole> list=new ArrayList<ResourcesRole>();
		for(int i=0;i<ids.length;i++){
			String resId=ids[i];
			if(!StringUtils.isNullOrEmpty(resId)){
				ResourcesRole resourcesRole=new ResourcesRole();
				resourcesRole.setRescId(Integer.valueOf(resId));
				resourcesRole.setRoleId(roleId);
				list.add(resourcesRole);
			}
		};
		int count=resourcesRoleMapper.insertBatches(list);
		securityMetadataSource.loadResource();
		if(count==list.size())
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}
	@Override
	public int deleteByRoleId(Integer roleId) {
		
		return resourcesRoleMapper.deleteByRoleId(roleId);
	}

}
