package com.kyx.biz.car.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.carMaintain.model.CarMaintain;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.orders.service.OrdersServeService;

/**
 * 车辆控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/car")
public class CarController extends BaseController {

	@Resource
	private CarService carService;

	@Resource
	private OrdersProductService ordersProductService;

	@Resource
	private OrdersServeService ordersServeService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {

		return "car/carInfo";
	}

	/**
	 * 查询客户
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryAllCars", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryAllCars(Car car, Pager pager) {
		GrdData result = carService.selectAllCarList(car, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 编辑界面
	 * 
	 * @return
	 */
	@RequestMapping("/editCarView")
	public String editCarView(Model model, Integer id) {
		model.addAttribute("carTypes",
				JSON.toJSONString(carService.queryAllCarType()));
		model.addAttribute("carDetail",
				JSON.toJSONString(carService.selectCarById(id)));

		return "car/editCar";
	}

	/**
	 * 删除车辆信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delData")
	@ResponseBody
	public String delData(String ids) {
		RetInfo retInfo = carService.delCar(ids);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 更新车辆信息
	 * 
	 * @param car
	 * @return
	 */
	@RequestMapping(value = "/updateCar", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String updateCustom(Car car) {
		RetInfo retInfo = carService.updateCar(car);
		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 保险到期首页
	 * 
	 * @return
	 */
	@RequestMapping("/insureIndex")
	public String insureIndex(Model model) {
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(3));

		return "car/insureIndex";
	}

	/**
	 * 查询车辆保险到期
	 * 
	 * @param car
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryCarInsure", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCarInsure(Car car, Pager pager) {
		GrdData result = carService.selectCarByInsure(car, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 车检到期首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkIndex")
	public String checkIndex(Model model) {
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(3));

		return "car/checkIndex";
	}

	/**
	 * 车检到期
	 * 
	 * @param car
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryCarCheck", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCarCheck(Car car, Pager pager) {
		GrdData result = carService.selectCarByCheck(car, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 添加车辆保养界面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/addCarMaintainView")
	public String addCarMaintainView(Model model, Integer id) {
		model.addAttribute("carId", id);
		model.addAttribute("nextAddTime", carService.queryNextMaintainTime(id));
		return "car/addCarMaintain";
	}

	/**
	 * 添加车辆保养
	 * 
	 * @param carMaintain
	 * @return
	 */
	@RequestMapping("/addCarMaintain")
	@ResponseBody
	public String addCarMaintain(CarMaintain carMaintain) {
		RetInfo retInfo = carService.addCarMaintain(carMaintain);
		return AppUtils.getReturnInfo(retInfo);
	}

	@RequestMapping("/carDetailView")
	public String carDetailView(Model model, Integer id) {
		model.addAttribute("car",
				JSON.toJSONString(carService.selectCarById(id)));
		model.addAttribute("carId", id);
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(3));
		model.addAttribute("carNumber", carService.selectCarById(id)
				.getCarNumber());

		return "car/carDetail";
	}

	@RequestMapping(value = "/queryCarMaintain", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCarMaintain(Integer carId, Pager pager) {
		GrdData result = carService.queryCarMaintain(carId, pager);

		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/queryConsumeOrders", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryConsumeOrders(Orders orders, Pager pager,
			HttpServletRequest request) {
		orders.setShopId(getShopId(request));
		GrdData result = carService.queryConsumeOrders(orders, pager);

		return JSON.toJSONString(result);
	}

	@RequestMapping("/ordersDetailView")
	public String ordersDetailView(Model model, Integer id) {
		model.addAttribute("orderId", id);

		return "car/ordersDetail";
	}

	@RequestMapping(value = "/queryOrdersProduct", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryOrdersProduct(Integer orderId) {
		GrdData result = ordersProductService.queryProductsByOrderId(orderId);

		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/queryOrdersServers", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryOrdersServers(Integer orderId) {
		GrdData result = ordersServeService.queryServesByOrderId(orderId);

		return JSON.toJSONString(result);
	}

	@RequestMapping("/maintainIndex")
	public String maintainIndex(Model model) {
		model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(1));
		model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(2));
		model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(3));

		return "car/maintainIndex";
	}

	/**
	 * 查询车辆保养到期
	 * 
	 * @param car
	 * @return
	 */
	@RequestMapping(value = "/queryCarMaintains", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCarMaintains(Car car) {
		GrdData result = carService.queryCarMaintain(car);

		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 编辑界面
	 * 
	 * @return
	 */
	@RequestMapping("/editCarViewByNumber")
	public String editCarView(Model model, String carNumber) {
		model.addAttribute("carTypes",JSON.toJSONString(carService.queryAllCarType()));
		Car car = carService.selectByCarNo(carNumber);
		model.addAttribute("carDetail",JSON.toJSONString(carService.selectCarById(car.getId())));
		return "car/editCar";
	}
}
