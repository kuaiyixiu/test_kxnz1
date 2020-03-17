package com.kyx.biz.mealdt.service;

import java.util.List;

import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.mealdt.model.MealDt;

public interface MealDtService {

	/**
	 * 根据meal_id查询
	 * 
	 * @param id
	 * @return
	 */
	List<MealDt> selectByMealId(Integer mealId);

	/**
	 * 转换套餐明细，查出对应的产品和服务信息
	 * 
	 * @param list
	 * @return
	 */
	List<MealDt> transferMealDts(List<MealDt> list);

	/**
	 * 设置套餐明细和总价格
	 * 
	 * @param meal
	 * @return
	 */
	Meal setMealDts(Meal meal);
    
	int getCountByTypeAndId(int type, Integer itemId);
}
