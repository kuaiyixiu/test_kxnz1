package com.kyx.biz.meal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.meal.model.Meal;

@Repository("mealMapper")
public interface MealMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Meal record);

	int insertSelective(Meal record);

	Meal selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Meal record);

	int updateByPrimaryKey(Meal record);

	List<Meal> selectMealList(Meal meal);

	List<Meal> selectMealByShopIds(@Param("list") List<String> shopIds,
			@Param("statusList") List<String> statusList);
}