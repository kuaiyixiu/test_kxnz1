package com.kyx.biz.meal.service;

import java.util.List;
import java.util.Map;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.meal.model.Meal;

public interface MealService {
	List<Meal> queryMeals(Meal meal);

	/**
	 * 查询所有套餐
	 * 
	 * @param meal
	 * @param pager
	 * @return
	 */
	GrdData queryMeals(Meal meal, Pager pager);

	/**
	 * 新增套餐
	 * 
	 * @param meal
	 * @return
	 */
	RetInfo addMeal(Meal meal);

	/**
	 * 下架套餐
	 * 
	 * @param ids
	 * @return
	 */
	RetInfo obtainedMeal(Integer ids);

	/**
	 * 按照id查询套餐
	 * 
	 * @param id
	 * @param shopMap
	 * @return
	 */
	Meal queryMealById(Integer id, Map<String, String> shopMap);

	/**
	 * app端查询客户可购买套餐
	 * 
	 * @param meal
	 * @return
	 */
	List<Meal> queryAppBuyMeals(Meal meal);
}
