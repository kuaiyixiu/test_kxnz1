package com.kyx.basic.resourcesrole.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.resourcesrole.model.ResourcesRole;

@Repository("resourcesRoleMapper")
public interface ResourcesRoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ResourcesRole record);

	int insertSelective(ResourcesRole record);

	ResourcesRole selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ResourcesRole record);

	int updateByPrimaryKey(ResourcesRole record);

	int insertBatches(@Param("list") List<ResourcesRole> resourcesRoles);

	/**
	 * 根据角色删除
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);

	/**
	 * 根据菜单id删除
	 * 
	 * @param rescId
	 * @return
	 */
	int deleteByRescId(Integer rescId);

}