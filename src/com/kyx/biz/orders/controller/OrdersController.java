package com.kyx.biz.orders.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustCouponService;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customMeal.model.CustomMeal;
import com.kyx.biz.orders.model.CarInfo;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersPay;
import com.kyx.biz.orders.model.OrdersProduct;
import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.orders.service.OrdersPayService;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.orders.service.OrdersServeService;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.wechat.service.WechatSendService;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;

/**
 * 订单管理
 * 
 * @author 童亦刚
 * @Descripton
 */
@Controller
@RequestMapping(value = "/orders")
public class OrdersController extends BaseController {

	@Resource
	private OrdersService ordersService;
	
	@Resource
	private OrdersServeService ordersServeService;
	
	@Resource
	private OrdersProductService ordersProductService;
	
	@Resource
	private OrdersPayService ordersPayService;

	@Resource
	private PayTypeService payTypeService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CarService carService;
	
	@Resource
	private CustomService customService;
	
	@Resource
	private CustCouponService custCouponService;

	@Resource
	private WechatSendService wechatSendService;
	
	@Resource
	private WechatCardRecordService wechatCardRecordService;

	//pageType=1订单列表  2挂账订单 3入账订单4消费记录
	@RequestMapping("/orderList/{pageType}")
	public String orderList(Model model, @PathVariable("pageType") Integer pageType ) {
		model.addAttribute("pageType",pageType);
		return "orders/orderlist";
	}

	//pageType=1订单列表  2挂账订单 3入账订单4消费记录
	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request,Orders orders, Integer pageType,Pager pager) {
		orders.setShopId(getShopId(request));
		GrdData result = ordersService.getOrdersList(orders,pageType, pager);
		return JSON.toJSONString(result);
	}

	/**
	 * 新增订单
	 */
	@RequestMapping("/addOrder/{kind}")
	@SystemControllerLog(module = "订单", description = "开单") 
	public String addOrder(Model model, HttpServletRequest request, @PathVariable("kind") Integer kind,String carNumber) {
		//carNumber= URLDecoder.decode(carNumber, "utf-8");
		Orders orders = new Orders();
		Integer orderType = null;
		if (kind == 1){ //临牌开单
			 orderType = 0;//临牌开单
		}else{ //车牌开单
			Car car = carService.selectByCarNo(carNumber);
			if (car == null){ //不存在车辆信息，就新增
				car = new Car();
				car.setCarType(Car.DEFAULT);
				car.setCarNumber(carNumber);
				carService.saveCar(car);
				orderType = 1;//非会员开单
			}else{
				if (car.getCustomId() == null){
					orderType = 1;// 非2会员开单
				}else {
					Custom custom = customService.selectByPrimaryKey(car.getCustomId());
					model.addAttribute("custom",custom);
					orders.setCustId(car.getCustomId()); //设置订单的会员卡信息
					orderType = 2;// 2会员开单
					
					//查询套餐
					handleMeal(request,model,car.getCustomId());
					 
				}
			}
			orders.setCarNumber(carNumber);//设置订单的车辆编号
			model.addAttribute("car",car);
		}
		orders.setCreateUser(AppUtils.getOptUserName(request.getSession()));
		orders.setShopId(getShopId(request));
		orders.setOrderStatus(1); //编辑
		orders.setOrderType(orderType); // 订单类型
		ordersService.saveOrders(orders);
		model.addAttribute("orders", orders);
		model.addAttribute("pageType", "1"); //新增
		return "orders/addOrder";
	}
	
	/**
	 * 处理套餐
	 * @param request
	 * @param model
	 * @param custId
	 */
	private void handleMeal(HttpServletRequest request,Model model,Integer custId){
		//查询套餐
		 CustomRequest customRequest = new CustomRequest();
		 customRequest.setCustId(custId);
		 List<CustomMeal>  customMeals  = customService.queryCustomMealList(customRequest,getShopId(request));
		 if(!ObjectUtils.isEmpty(customMeals)){
			 StringBuffer mealName = new StringBuffer("");
			 for(CustomMeal meal : customMeals){
				 mealName.append(meal.getName()+",");
			 }
			 model.addAttribute("mealName",  mealName.substring(0, mealName.length() - 1));
			 model.addAttribute("showMeal", "1");
		 }
	}
	
	/**
	 * 处理代金券 优惠券
	 * @param request
	 * @param model
	 * @param custId
	 */
	private void handleCustPay(HttpServletRequest request,Model model,Integer custId){
		 //查看代金券是否可用
		 int couponCount = custCouponService.selectCountByCustId(custId);
		 if(couponCount > 0)
			 model.addAttribute("couponCount",couponCount);
		 
		//查看优惠券是否可用
		int cardsetCount =  wechatCardRecordService.selectCountByCustId(custId);
		if(cardsetCount > 0)
			model.addAttribute("cardsetCount",cardsetCount);	
	}
	

	/**
	 * 更新订单
	 * @param car
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/submitOrders")
	@ResponseBody
	@SystemControllerLog(module = "订单", description = "更改订单状态") 
	public String submitOrders(HttpServletRequest request,Orders orders,Integer operateType) throws Exception {
		RetInfo retInfo = ordersService.updateOrders(request,orders,operateType);
		if(operateType == 4){ //施工完成，推送消费记录
			Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
			wechatSendService.sendOrderConsume(orders.getId(), shops);
		}
		return AppUtils.getReturnInfo(retInfo);
	}
	
	@RequestMapping(value = "/deleteOrders")
	@ResponseBody
	@SystemControllerLog(module = "订单", description = "作废订单") 
	public String deleteOrders(HttpServletRequest request,Integer ids) throws Exception {
		RetInfo retInfo = ordersService.deleteOrders( ids);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	
	//查看明细
	@RequestMapping("/toUpdateOrder/{id}/{pageType}")
	public String updateOrder(Model model, HttpServletRequest request, @PathVariable("id")Integer id, @PathVariable("pageType")Integer pageType) {
		Orders orders = ordersService.queryById( id);
		if (orders.getOrderType() == 1 || orders.getOrderType() == 2){
			Car car = carService.selectByCarNo(orders.getCarNumber());
			model.addAttribute("car",car);
			if (car.getCustomId() != null){
				Custom custom = customService.selectByPrimaryKey(car.getCustomId());
				model.addAttribute("custom",custom);
				
				//订单状态 0作废 1编辑 2待施工（已提交）3施工中  4施工完成 5入账 6挂账 7反入账 8反挂账 
				if (orders.getOrderStatus() == 1 || orders.getOrderStatus() == 2 || orders.getOrderStatus() == 3||  orders.getOrderStatus() == 4
						|| orders.getOrderStatus() == 7 || orders.getOrderStatus() == 8){
					handleMeal(request, model, car.getCustomId());
				}
				
				if(orders.getOrderStatus() == 4 || orders.getOrderStatus() == 6
						|| orders.getOrderStatus() == 7 || orders.getOrderStatus() == 8){
					handleCustPay(request, model,  car.getCustomId());
				}
				 
			}
		}
		
		model.addAttribute("orders",orders);
		model.addAttribute("pageType",pageType); //1新增 2编辑明细 3还款4查看
 		return "orders/addOrder";
	}
	
	 @RequestMapping(value="/getOrdersInfo")
	 @ResponseBody
	 public String getOrdersInfo(HttpServletRequest request,Integer id) throws Exception {
		Orders orders = new Orders();
		orders.setOrdersServeList(ordersServeService.queryListByOrderId( id));
		orders.setOrdersProductList(ordersProductService.queryListByOrderId( id));
		orders.setOrdersPayList(ordersPayService.queryListByOrderId(id));
		return JSON.toJSONString(orders);
	 }
	

	@RequestMapping("/addOrdersServe")
	public String addOrdersServe(Model model, HttpSession session,Integer custId) {
		Shops shops = (Shops) session.getAttribute("shopsSession");
		Dbs.setDbName(Dbs.getMainDbName());
		List<User> users = userService.getByShopId(shops.getId());
		// 施工人员select
		StringBuffer workUserStr = new StringBuffer("");
		workUserStr.append("<option   value=''></option>");
		if (!ObjectUtils.isEmpty(users)) {
			for (User user : users) {
				workUserStr.append("<option value='" + user.getId().toString() + "' {{#  if( item.serveUser ==  '" + user.getId().toString() + "'){ }}selected{{#  } }}    >" + user.getUserRealname() + "</option>");
			}
		}
		model.addAttribute("serveUserStr", workUserStr);
		// 服务销售select
		StringBuffer sellUserStr = new StringBuffer("");
		sellUserStr.append("<option   value=''></option>");
		if (!ObjectUtils.isEmpty(users)) {
			for (User user : users) {
				sellUserStr.append("<option value='" + user.getId().toString() + "' {{#  if( item.sellUser ==  '" + user.getId().toString() + "'){ }}selected{{#  } }}    >" + user.getUserRealname() + "</option>");
			}
		}
		model.addAttribute("sellUserStr", sellUserStr);
		model.addAttribute("custId", custId);
		return "orders/addOrdersServe";
	}
	
	
	 @RequestMapping(value="/updateOrdersServe/{id}")
	 public String updateOrdersServe(HttpServletRequest request,Model model,@PathVariable("id") Integer id) {
		 OrdersServe ordersServe = ordersServeService.queryById(request, id, null);
		 model.addAttribute("ordersServe", ordersServe);
		 Shops shops = (Shops) request.getSession().getAttribute("shopsSession");
		 Dbs.setDbName(Dbs.getMainDbName());
		 List<User> users = userService.getByShopId(shops.getId());
		 model.addAttribute("users", users);
		 return "orders/updateOrdersServe";
	 }
	
	

	@RequestMapping("/addOrdersProduct")
	public String addOrdersProduct(Model model, HttpSession session,Integer custId) {
		// 产品人员select
		Shops shops = (Shops) session.getAttribute("shopsSession");
		Dbs.setDbName(Dbs.getMainDbName());
		List<User> users = userService.getByShopId(shops.getId());
		StringBuffer productUserStr = new StringBuffer("");
		productUserStr.append("<option   value=''></option>");
		if (!ObjectUtils.isEmpty(users)) {
			for (User user : users) {
				productUserStr.append("<option value='" + user.getId().toString() + "' {{#  if( item.productUser ==  '" + user.getId().toString() + "'){ }}selected{{#  } }}    >" + user.getUserRealname() + "</option>");
			}
		}
		model.addAttribute("productUserStr", productUserStr);
		model.addAttribute("custId", custId);
		return "orders/addOrdersProduct";
	}
	
	
	 @RequestMapping(value="/updateOrdersProduct/{id}")
	 public String updateOrdersProduct(HttpServletRequest request,Model model,@PathVariable("id") Integer id) {
		 OrdersProduct ordersProduct = ordersProductService.queryById( id, null);
		 model.addAttribute("ordersProduct", ordersProduct);
		 Shops shops = (Shops) request.getSession().getAttribute("shopsSession");
		 Dbs.setDbName(Dbs.getMainDbName());
		 List<User> users = userService.getByShopId(shops.getId());
		 model.addAttribute("users", users);
		 return "orders/updateOrdersProduct";
	 }
	 
	 @RequestMapping(value="/addOrdersPay")
	 public String addOrdersPay(HttpServletRequest request,Model model) {
		 Shops shops = (Shops) request.getSession().getAttribute("shopsSession");
		 List<PayType> payTypes = payTypeService.getPayType(PayType.CUSTOMTYPE);
		 model.addAttribute("payTypes", payTypes);
		 model.addAttribute("shopId", shops.getId());
		 return "orders/addOrdersPay";
	 }
	 
	 
	 @RequestMapping(value="/updateOrdersPay/{id}")
	 public String updateOrdersPay(HttpServletRequest request,Model model,@PathVariable("id") Integer id) {
		 Shops shops = (Shops) request.getSession().getAttribute("shopsSession");
		 OrdersPay ordersPay =  ordersPayService.queryById(request, id, shops.getId());
	//	 List<PayType> payTypes = payTypeService.getPayType(PayType.CUSTOMTYPE);
		 model.addAttribute("ordersPay", ordersPay);
		// model.addAttribute("payTypes", payTypes);
		 model.addAttribute("shopId", shops.getId());
		 model.addAttribute("COUPONPAY", BasicContant.payTypeContant.COUPON);
		 model.addAttribute("CARDSETPAY", BasicContant.payTypeContant.CARDSET);
		 return "orders/updateOrdersPay";
	 }
	
	
	 @RequestMapping(value="/ajaxsaveOrdersServe")
	 @ResponseBody
	 public String ajaxsaveOrdersServe(HttpServletRequest request,@RequestBody List<OrdersServe> orderServiceList,Integer orderId) {

		 return AppUtils.getReturnInfo (ordersServeService.saveOrdersServeList( orderServiceList, orderId));
	 }

	 @RequestMapping(value="/ajaxsaveOrdersProduct")
	 @ResponseBody
	 public String ajaxsaveOrdersProduct(HttpServletRequest request,@RequestBody List<OrdersProduct> orderProductList,Integer orderId) throws Exception {
		 return AppUtils.getReturnInfo (ordersProductService.saveOrdersProductList( orderProductList, orderId));
	 }
	 
	 
	 @RequestMapping(value="/ajaxsaveOrdersPay")
	 @ResponseBody
	 public String ajaxsaveOrdersPay(HttpServletRequest request,@RequestBody List<OrdersPay> ordersPays,Integer orderId) {
		 return AppUtils.getReturnInfo (ordersPayService.saveOrdersPayList(ordersPays, orderId));
	 }
	  
	 
	 
	 
	 @RequestMapping(value="/ajaxupdateOrdersProduct")
	 @ResponseBody
	 public String ajaxupdateOrdersProduct(HttpServletRequest request,@RequestBody OrdersProduct ordersProduct) throws Exception {
		 return AppUtils.getReturnInfo (ordersProductService.updateOrdersProduct(ordersProduct));
	 }
	 
	 @RequestMapping(value="/ajaxupdateOrdersServe")
	 @ResponseBody
	 public String ajaxupdateOrdersServe(HttpServletRequest request,@RequestBody OrdersServe ordersServe) throws Exception {
		 return AppUtils.getReturnInfo (ordersServeService.updateOrdersServe(ordersServe));
	 }
	 
	 
	 @RequestMapping(value="/ajaxupdateOrdersPay")
	 @ResponseBody
	 public String ajaxupdateOrdersPay(HttpServletRequest request,@RequestBody OrdersPay ordersPay) throws Exception {
		 return AppUtils.getReturnInfo(ordersPayService.updateOrdersPay(ordersPay));
	 }
	 
	 
	 @RequestMapping(value="/delOrdersServe/{id}")
	 @ResponseBody
	 public String delOrderServe(HttpServletRequest request,@PathVariable("id") Integer id) {
		 return AppUtils.getReturnInfo ( ordersServeService.delOrdersServe(id));
	 }
	 
	 @RequestMapping(value="/delOrdersProduct/{id}")
	 @ResponseBody
	 public String delOrdersProduct(HttpServletRequest request,@PathVariable("id") Integer id) throws Exception {
		 return AppUtils.getReturnInfo ( ordersProductService.delOrdersProduct(id));
	 }
	 
	 
	 @RequestMapping(value="/delOrdersPay/{id}")
	 @ResponseBody
	 public String delOrdersPay(HttpServletRequest request,@PathVariable("id") Integer id) throws Exception {
		 return AppUtils.getReturnInfo ( ordersPayService.delOrdersPay(id));
	 }
	 
		
	 @RequestMapping(value="/chooseMeal/{customId}/{orderId}")
	 public String chooseMeal(HttpServletRequest request,Model model,@PathVariable("customId") Integer customId,@PathVariable("orderId") Integer orderId,
			 Integer itemId,Integer itemType,Integer dtId) {
		 CustomRequest customRequest = new CustomRequest();
		 customRequest.setCustId(customId);
		 customRequest.setItemId(itemId);
		 customRequest.setItemType(itemType);
		 List<CustomMeal>  customMeals  = customService.queryCustomMealList(customRequest,getShopId(request));
		 model.addAttribute("customMeals", customMeals);
		 model.addAttribute("itemType", itemType);
		 model.addAttribute("dtId", dtId);
		 model.addAttribute("itemId", itemId);
		 return "orders/chooseMeal";
	 }
	 
	 /**
	  * 提交选择的用户套餐
	 * @throws Exception 
	  */
	 @RequestMapping(value="/submitMeal")
	 @ResponseBody
	 public String submitMeal(HttpServletRequest request,String chooseMealInfo,Integer customId,Integer orderId,Integer dtId,Integer itemType) throws Exception {
		return  AppUtils.getReturnInfo(ordersService.submitMeal(chooseMealInfo, customId, orderId, getShopId(request),dtId,itemType));
	 }
	 
	 
	@RequestMapping(value = "/getCarInfoList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getCarInfoList(HttpServletRequest request,String carNumber) {
		CarInfo carInfo = new CarInfo();
		carInfo.setCode(0);
		carInfo.setCount(10);
		carInfo.setMsg("");
		List<String> car=carService.selectByCarNumber(carNumber);
//		List<String> car = new ArrayList<String>();
//		car.add("皖CTT308");
//		car.add("皖C11111");
//		car.add("皖C22223");
//		car.add("皖C5t4354");
//		car.add("皖CT5656");
		carInfo.setData(car);
		return JSON.toJSONString(carInfo);
	}
	/**
	 * 打印凭据
	 * @param model
	 * @param pageType
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/toprint/{pageType}/{orderId}")
	public String toprint(Model model, @PathVariable("pageType") Integer pageType,@PathVariable("orderId") Integer orderId,HttpServletRequest request) {
		Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
		model.addAttribute("shops", shops);
		model.addAttribute("pageType",pageType);
		BigDecimal serveAmount = new BigDecimal(0);
		BigDecimal productAmount = new BigDecimal(0);
		Orders orders = ordersService.queryById(orderId);
		orders.setOrdersServeList(ordersServeService.queryListByOrderId(orderId));
		orders.setOrdersProductList(ordersProductService.queryListByOrderId(orderId));
		orders.setOrdersPayList(ordersPayService.queryListByOrderId(orderId));
		for (int i = 0; i < orders.getOrdersServeList().size(); i++) {
			serveAmount = serveAmount.add(orders.getOrdersServeList().get(i).getPrice());
		}
		for (int i = 0; i < orders.getOrdersProductList().size(); i++) {
			productAmount = productAmount.add(orders.getOrdersProductList().get(i).getPrice().multiply
					(BigDecimal.valueOf(orders.getOrdersProductList().get(i).getQuantity())));
		}
		if (StringUtils.isNotBlank(orders.getCarNumber())) {
			Car car = carService.selectByCarNo(orders.getCarNumber());
			model.addAttribute("car",car);
		}
		if (orders.getCustId()!=null) {
			Custom custom = customService.selectByPrimaryKey(orders.getCustId());
			model.addAttribute("custom",custom);
		}
		model.addAttribute("orderPayAmount",ordersPayService.getOrderPayAmt(orders.getId()));
		model.addAttribute("orders",orders);
		model.addAttribute("serveAmount",serveAmount);
		model.addAttribute("productAmount",productAmount);
		
		
		return "orders/print";
	}
	/**
	 * 打印凭据小票
	 * @param model
	 * @param pageType
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/toPrintReceipt/{pageType}/{orderId}")
	public String toPrintReceipt(Model model, @PathVariable("pageType") Integer pageType,@PathVariable("orderId") Integer orderId,HttpServletRequest request) {
		Shops shops = (Shops) request.getSession().getAttribute(Shops.SHOPS_SESSION);
		model.addAttribute("shops", shops);
		model.addAttribute("pageType",pageType);
		BigDecimal serveAmount = new BigDecimal(0);
		BigDecimal productAmount = new BigDecimal(0);
		Orders orders = ordersService.queryById(orderId);
		orders.setOrdersServeList(ordersServeService.queryListByOrderId(orderId));
		orders.setOrdersProductList(ordersProductService.queryListByOrderId(orderId));
		orders.setOrdersPayList(ordersPayService.queryListByOrderId(orderId));
		for (int i = 0; i < orders.getOrdersServeList().size(); i++) {
			serveAmount = serveAmount.add(orders.getOrdersServeList().get(i).getPrice());
		}
		for (int i = 0; i < orders.getOrdersProductList().size(); i++) {
			productAmount = productAmount.add(orders.getOrdersProductList().get(i).getPrice().multiply
					(BigDecimal.valueOf(orders.getOrdersProductList().get(i).getQuantity())));
		}
		if (StringUtils.isNotBlank(orders.getCarNumber())) {
			Car car = carService.selectByCarNo(orders.getCarNumber());
			model.addAttribute("car",car);
		}
		if (orders.getCustId()!=null) {
			Custom custom = customService.selectByPrimaryKey(orders.getCustId());
			model.addAttribute("custom",custom);
		}
		model.addAttribute("orderPayAmount",ordersPayService.getOrderPayAmt(orders.getId()));
		model.addAttribute("orders",orders);
		model.addAttribute("serveAmount",serveAmount);
		model.addAttribute("productAmount",productAmount);
		model.addAttribute("userName",getUser(request).getUserRealname());
		model.addAttribute("now",new Date());
		
		return "orders/printReceipt";
	}
    /**
     * 消费记录列表
     * @param model
     * @param request
     * @return
     */
	@RequestMapping("/expenselist")
	public String expenselist(Model model, HttpServletRequest request) {
		return "orders/expenselist";
	}
	
	
}
