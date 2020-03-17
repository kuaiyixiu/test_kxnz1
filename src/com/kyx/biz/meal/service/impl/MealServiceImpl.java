package com.kyx.biz.meal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.meal.mapper.MealMapper;
import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.meal.service.MealService;
import com.kyx.biz.mealdt.mapper.MealDtMapper;
import com.kyx.biz.mealdt.model.MealDt;
import com.kyx.biz.mealdt.service.MealDtService;

@Service("mealService")
public class MealServiceImpl implements MealService {
	@Resource
	private MealMapper mealMapper;

	@Resource
	private MealDtMapper mealDtMapper;

	@Resource
	private MealDtService mealDtService;

	@Override
	public List<Meal> queryMeals(Meal meal) {
		List<Meal> list = mealMapper.selectMealList(meal);
		for (Meal m : list) {
			Date date = AppUtils.getDayByCycle(new Date(), m.getDay());
			m.setEndTime(AppUtils.date2String1(date));
		}

		return list;
	}

	@Override
	public GrdData queryMeals(Meal meal, Pager pager) {
		List<String> shopIds = getShopIds(meal);
		List<String> statusList = getStatus(meal);
		if (shopIds.size() == 0 || statusList.size() == 0) {
			return new GrdData(Long.valueOf(0), new ArrayList<>());
		}

		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Meal> meals = mealMapper.selectMealByShopIds(shopIds, statusList);
		meals = setMealDt(meals);

		return new GrdData(Long.valueOf(meals.size()), meals);
	}

	@Override
	public RetInfo addMeal(Meal meal) {
		boolean bool1 = mealMapper.insertSelective(meal) > 0;
		boolean bool2 = addMealDT(meal);

		return new RetInfo(bool1 && bool2, "添加套餐");
	}

	/**
	 * 添加套餐明细
	 * 
	 * @param meal
	 * @return
	 */
	private boolean addMealDT(Meal meal) {
		String mealDts = meal.getMealDtStr();
		if (StringUtils.isEmpty(mealDts)) {
			return true;
		}

		boolean bool1 = true;
		List<MealDt> list = JSONObject.parseArray(mealDts, MealDt.class);
		for (MealDt mealDt : list) {
			mealDt.setMealId(meal.getId());
			bool1 = mealDtMapper.insertSelective(mealDt) > 0;
		}

		return bool1;
	}

	/**
	 * 设置套餐明细
	 * 
	 * @param meals
	 * @return
	 */
	private List<Meal> setMealDt(List<Meal> meals) {
		for (Meal meal : meals) {
			MealDt mealDt = new MealDt();
			mealDt.setMealId(meal.getId());
			List<MealDt> mealDts = mealDtMapper.selectMeals(mealDt);
			mealDts = mealDtService.transferMealDts(mealDts);
			meal.setMealDts(mealDts);
		}

		return meals;
	}

	/**
	 * 得到shopid
	 * 
	 * @param meal
	 * @return
	 */
	private List<String> getShopIds(Meal meal) {
		String shopId = meal.getShopIds();
		if (StringUtils.isEmpty(shopId)) {
			return new ArrayList<>();
		}

		String[] shopIds = shopId.split(",");
		List<String> list = Arrays.asList(shopIds);

		return list;
	}

	/**
	 * 得到状态
	 * 
	 * @param meal
	 * @return
	 */
	private List<String> getStatus(Meal meal) {
		String status = meal.getStatusStr();
		if (StringUtils.isEmpty(status)) {
			return new ArrayList<>();
		}

		String[] shopIds = status.split(",");
		List<String> list = Arrays.asList(shopIds);

		return list;
	}

	@Override
	public RetInfo obtainedMeal(Integer ids) {
		Meal meal = new Meal();
		meal.setId(ids);
		meal.setStatus(Meal.OBTAINED);

		return new RetInfo(mealMapper.updateByPrimaryKeySelective(meal) > 0,
				"下架套餐");
	}

	@Override
	public Meal queryMealById(Integer id, Map<String, String> shopMap) {
		Meal meal = mealMapper.selectByPrimaryKey(id);
		meal.setShopName(shopMap.get(meal.getShopId()));
		meal = setMealDt(meal);

		return meal;
	}

	private Meal setMealDt(Meal meal) {
		MealDt mealDt = new MealDt();
		mealDt.setMealId(meal.getId());
		List<MealDt> mealDts = mealDtMapper.selectMeals(mealDt);
		meal.setMealDts(mealDts);
		meal = mealDtService.setMealDts(meal);

		return meal;
	}

	@Override
	public List<Meal> queryAppBuyMeals(Meal meal) {
		List<Meal> list = mealMapper.selectMealList(meal);
		for (Meal m : list) {
			Date date = AppUtils.getDayByCycle(new Date(), m.getDay());
			m.setEndTime(AppUtils.date2String1(date));
			setMealDt(m);
		}

		return list;
	}

}
