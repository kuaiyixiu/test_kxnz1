package com.kyx.biz.mealdt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.mealdt.model.MealDt;

@Repository("mealDtMapper")
public interface MealDtMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(MealDt record);

	int insertSelective(MealDt record);

	MealDt selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(MealDt record);

	int updateByPrimaryKey(MealDt record);

	List<MealDt> selectMeals(MealDt record);

	/**
	 * 查询客户订单
	 * 
	 * @param record
	 * @return
	 */
	List<MealDt> selectCustomMealDts(@Param("id") Integer id);

	int getCountByTypeAndId(int type, Integer itemId);
	
}