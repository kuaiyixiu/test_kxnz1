package com.kyx.biz.wechat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyx.basic.util.BasicContant;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.custom.model.Custom;

@Controller
@RequestMapping(value = "/wechat/car")
public class WechatCarController extends BaseController {
	@Resource
	private CarService carService;

	/**
	 * 首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, Integer id, HttpSession session) {
		List<Car> carList = carService.queryCarListByCustomId(id);
		Custom custom = (Custom) session
				.getAttribute(BasicContant.CUSTOMER_SESSION);

		model.addAttribute("custom", custom);
		model.addAttribute("carList", carList);
		return "car/car";
	}
	/*
	 * @RequestMapping(value = "/saveCustom", produces =
	 * "text/plain; charset=utf-8")
	 * 
	 * @ResponseBody public String saveCustom(Custom custom, HttpServletRequest
	 * request) { custom.setShopId(getShopId(request)); custom.setCustType(1);
	 * RetInfo retInfo = customService.addCustom(custom);
	 * 
	 * return AppUtils.getReturnInfo(retInfo); }
	 */
}
