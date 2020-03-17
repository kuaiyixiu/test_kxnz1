package com.kyx.biz.meal.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.meal.service.MealService;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.service.ServeService;

/**
 * 菜单控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/meal")
public class MealController extends BaseController {

	@Resource
	private ServeService serveService;

	@Resource
	private MealService mealService;

	@Resource
	private ProductService productService;

	// jsp前缀
	private static final String JSP_PREFIX = "customConfig/meal";

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		Map<String, String> map = (Map<String, String>) request.getSession()
				.getAttribute(Shops.SHOP_LIST_SESSION);
		model.addAttribute("shopList", map);
		model.addAttribute(
				"shopListMap",
				JSON.toJSONString(request.getSession().getAttribute(
						Shops.SHOP_LIST_SESSION)));
		model.addAttribute("shopId", getShopId(request));

		return JSP_PREFIX + "/meal";
	}

	/**
	 * 查询所有会员类型
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryMeals", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryMeals(Meal meal, Pager pager) {
		GrdData result = mealService.queryMeals(meal, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 套餐添加界面
	 * 
	 * @param meal
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addMealView")
	public String addMealView(Meal meal, HttpServletRequest request, Model model) {

		return JSP_PREFIX + "/addMeal";
	}

	/**
	 * 套餐详情
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/detailView")
	public String detailView(Integer id, HttpServletRequest request, Model model) {
		Map<String, String> shopMap = (Map<String, String>) request
				.getSession().getAttribute(Shops.SHOP_LIST_SESSION);
		Meal meal = mealService.queryMealById(id, shopMap);
		model.addAttribute("meal", meal);
		model.addAttribute("mealDts", meal.getMealDts());

		return JSP_PREFIX + "/mealDetail";
	}

	/**
	 * 添加套餐
	 * 
	 * @param meal
	 * @param request
	 * @return
	 */
	@RequestMapping("/addMeal")
	@ResponseBody
	public RetInfo addMeal(Meal meal, HttpServletRequest request) {
		meal.setShopId(getShopId(request));
		RetInfo ret = mealService.addMeal(meal);

		return ret;
	}

	/**
	 * 添加服务视图
	 * 
	 * @return
	 */
	@RequestMapping("/addServeView")
	public String addServeView(Model model, HttpServletRequest request) {
		model.addAttribute("serveList",
				serveService.getServeList(getServe(request)));

		return JSP_PREFIX + "/addServe";
	}

	/**
	 * 添加产品视图
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addProductView")
	public String addProductView(Model model, HttpServletRequest request) {
		model.addAttribute("productList",
				productService.getProductList(getProduct(request)));

		return JSP_PREFIX + "/addProduct";
	}

	/**
	 * 下架套餐
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/obtainedMeal")
	@ResponseBody
	public RetInfo obtainedMeal(Integer ids, HttpServletRequest request) {

		return mealService.obtainedMeal(ids);
	}

	private Serve getServe(HttpServletRequest request) {
		Serve serve = new Serve();
		int shopId = getShopId(request);
		serve.setShopId(shopId);

		return serve;
	}

	private Product getProduct(HttpServletRequest request) {
		Product product = new Product();
		int shopId = getShopId(request);
		product.setShopId(shopId);

		return product;
	}
}
