package com.kyx.basic.resource.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.basic.resource.model.Resource;
@Repository("resourceMapper")
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
	List<Resource> getUserResource(Integer roleId);

	List<Resource> getAll();
	
	List<Resource> queryMenu(Resource resource);

	List<Resource> getMenuByRoleId(Integer id);
}