package com.kyx.biz.customMeal.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.customMeal.model.CustomMeal;

@Repository("customMealMapper")
public interface CustomMealMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CustomMeal record);

	int insertSelective(CustomMeal record);

	CustomMeal selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CustomMeal record);

	int updateByPrimaryKey(CustomMeal record);

	/**
	 * 查询客户套餐信息
	 * 
	 * @param record
	 * @return
	 */
	List<CustomMeal> selectCustomMeal(CustomMeal record);

	/**
	 * 查询过期套餐
	 * 
	 * @param record
	 * @return
	 */
	List<CustomMeal> selectExpireMeals(CustomMeal record);
}