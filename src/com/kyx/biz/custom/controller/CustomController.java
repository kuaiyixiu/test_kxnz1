package com.kyx.biz.custom.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.meal.service.MealService;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.recharge.service.RechargeService;

/**
 * 客户控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/custom")
public class CustomController extends BaseController {

	@Resource
	private CustomService customService;

	@Resource
	private CustomTypeService customTypeService;

	@Resource
	private CarService carService;

	@Resource
	private MealService mealService;

	@Resource
	private PayTypeService payTypeService;
	
	@Resource
	private RechargeService rechargeService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		model.addAttribute("customTypes",
				JSON.toJSONString(customTypeService.selectCustomTypes(null)));

		model.addAttribute(
				"shopListMap",
				JSON.toJSONString(request.getSession().getAttribute(
						Shops.SHOP_LIST_SESSION)));

		return "custom/customInfo";
	}

	/**
	 * 查询客户
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryCustoms", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCustoms(Custom custom, Pager pager) {
		GrdData result = customService.queryCustoms(custom, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detailView")
	public String detailView(Model model, Integer id) {
		model.addAttribute("custom", customService.selectByPrimaryKey(id));
		model.addAttribute("car",
				JSON.toJSONString(carService.selectByCustomId(id)));

		return "custom/customDetail";
	}

	/**
	 * 添加界面
	 * 
	 * @return
	 */
	@RequestMapping("/addCustomView")
	public String addCustomView(Model model) {
		model.addAttribute("customTypes",
				JSON.toJSONString(customTypeService.selectCustomTypes(null)));

		return "custom/addCustom";
	}

	/**
	 * 保存客户信息
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/saveCustom", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "会员", description = "保存会员")
	public String saveCustom(Custom custom, HttpServletRequest request) {
		custom.setShopId(getShopId(request));
		RetInfo retInfo = customService.saveCustom(custom);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 删除会员和车辆信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delData")
	@ResponseBody
	@SystemControllerLog(module = "会员", description = "删除会员") 
	public String delData(String ids) {
		RetInfo retInfo = customService.delCustomAndCar(ids);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 编辑视图
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/editCustomView")
	public String editCustomView(Model model, Integer id) {
		model.addAttribute("custom", customService.selectCustomAndCar(id));

		return "custom/editCustom";
	}

	/**
	 * 更新会员信息
	 * 
	 * @param custom
	 * @return
	 */
	@RequestMapping(value = "/updateCustom", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "会员", description = "更新会员")
	public String updateCustom(Custom custom) {
		RetInfo retInfo = customService.updateCustom(custom);
		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 充值视图
	 * 
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/rechargeView")
	public String rechargeView(Model model, Integer id,
			HttpServletRequest request) {
		model.addAttribute("custom", customService.selectCustomAndCar(id));
		model.addAttribute("customTypes",
				JSON.toJSONString(customTypeService.selectCustomTypes(null)));
		model.addAttribute("shopListMap",JSON.toJSONString(request.getSession().getAttribute(Shops.SHOP_LIST_SESSION)));

		return "custom/recharge";
	}

	/**
	 * 购买套餐视图
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/buyMealView")
	public String buyMealView(Model model, HttpServletRequest request) {
		model.addAttribute("meals", JSON.toJSONString(mealService
				.queryMeals(setMealParam(request))));
		Shops shops = (Shops) request.getSession().getAttribute(
				Shops.SHOPS_SESSION);
		model.addAttribute("payTypes",
				JSON.toJSONString(payTypeService.getPayType(PayType.CUSTOMTYPE)));
		model.addAttribute("nextYear", AppUtils.getDayByYear(1));

		return "custom/buyMeal";
	}

	/**
	 * 设置meal参数
	 * 
	 * @param request
	 * @return
	 */
	private Meal setMealParam(HttpServletRequest request) {
		Meal meal = new Meal();
		meal.setStatus(AppUtils.USING);

		return meal;
	}

	/**
	 * 购买套餐
	 * 
	 * @param customRequest
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveCustomMeal", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "会员", description = "购买客户套餐")
	public String saveCustomMeal(CustomRequest customRequest,
			HttpServletRequest request) {
		customRequest.setShopId(getShopId(request));
		RetInfo retInfo = customService.saveCustomMeal(customRequest);
		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 查询
	 * 
	 * @param customRequest
	 * @return
	 */
	@RequestMapping(value = "/queryCustomMeal", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCustomMeal(CustomRequest customRequest,
			HttpServletRequest request) {
		customRequest.setShopId(getShopId(request));
		GrdData result = customService.queryCustomMeal(customRequest);

		return JSON.toJSONString(result);
	}

	/**
	 * 删除客户套餐
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delCustomMeal", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "会员", description = "删除客户套餐")
	public String delCustomMeal(String ids) {
		RetInfo retInfo = customService.delCustomMeal(Integer.valueOf(ids));
		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 客户套餐明细删除
	 * 
	 * @param id
	 * @param pId
	 * @return
	 */
	@RequestMapping(value = "/delCustomMealDt", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String delCustomMealDt(String id, String pId) {
		RetInfo retInfo = customService.delCustomMealDt(Integer.valueOf(id),
				pId);
		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 客户套餐明细删除界面 TODO(daibin)不做了
	 * 
	 * @param model
	 * @param id
	 * @param price
	 * @param pId
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMealDtView")
	public String buyMealView(Model model, String id, String price, String pId,
			HttpServletRequest request) {
		model.addAttribute("id", id);
		model.addAttribute("price", price);
		model.addAttribute("pId", pId);
		model.addAttribute("payTypes", JSON.toJSONString(payTypeService.getPayType(PayType.CUSTOMTYPE)));

		return "custom/delMealDtView";
	}

	@RequestMapping("/expireIndex")
	public String expireIndex(Model model) {
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(3));

		return "custom/expireIndex";
	}

	/**
	 * 查询过期套餐详情
	 * 
	 * @param customRequest
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryExpireMealDts", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryExpireMealDts(CustomRequest customRequest, Pager pager) {
		GrdData result = customService.queryExpireMealDts(customRequest, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 会员跨店首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/otherShopIndex")
	public String otherShopIndex(Model model, HttpServletRequest request) {
		model.addAttribute(
				"shopListMap",
				JSON.toJSONString(request.getSession().getAttribute(
						Shops.SHOP_LIST_SESSION)));
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(-1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(-2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(-3));
		model.addAttribute("customShopId", getShopId(request));

		return "custom/otherShopIndex";
	}

	/**
	 * 会员跨店查询
	 * 
	 * @param orders
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryOtherShop", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryOtherShop(Orders orders, Pager pager) {
		GrdData result = customService.queryOtherShop(orders, pager);

		return JSON.toJSONString(result);
	}
	/**
	 * 会员充值
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addRecharge")
	public String addRecharge(Model model,HttpServletRequest request,String cardNo) {
		model.addAttribute("cardNo", cardNo);
		//获取客户付款信息
		List<PayType> custPay=payTypeService.getPayTypes(PayType.CUSTOMTYPE);
		
		model.addAttribute("custPay", custPay);
		return "custom/addRecharge";
	}
	/**
	 * 充值
	 * @return
	 */
	@RequestMapping(value = "/saveRechargeData")
	@ResponseBody
	public String saveRechargeData(Recharge recharge,HttpServletRequest request) {
		recharge.setOptUser(getUser(request).getUserRealname());
		RetInfo retInfo=rechargeService.saveRechargeData(recharge);
		return AppUtils.getReturnInfo(retInfo);
	}

  /**
   * 查询客户
   * 
   * @param custom
   * @param pager
   * @return
   */
  @RequestMapping(value = "/queryCustomAndCar", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCustomAndCar(Custom custom, Pager pager) {
    GrdData result = customService.queryCustomAndCar(custom, pager);

    return JSON.toJSONString(result);
  }
}
