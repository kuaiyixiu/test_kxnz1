package com.kyx.biz.mealdt.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.biz.mealdt.service.MealDtService;

/**
 * 客户控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/mealDt")
public class MealDtController {

	@Resource
	private MealDtService mealDtService;

	/**
	 * 根据meal_id查询mealdt
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryMealDts", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryMealDts(Integer mealId) {

		return JSON.toJSONString(mealDtService.selectByMealId(mealId));
	}

}
