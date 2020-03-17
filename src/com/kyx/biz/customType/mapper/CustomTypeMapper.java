package com.kyx.biz.customType.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.customType.model.CustomType;

@Repository("customTypeMapper")
public interface CustomTypeMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CustomType record);

	int insertSelective(CustomType record);

	CustomType selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CustomType record);

	int updateByPrimaryKey(CustomType record);

	List<CustomType> selectCustomTypes(CustomType customType);

	List<CustomType> selectByName(@Param("typeName") String typeName);

	List<CustomType> selectByNameAndNotId(@Param("typeName") String typeName,
			@Param("id") Integer id);
}