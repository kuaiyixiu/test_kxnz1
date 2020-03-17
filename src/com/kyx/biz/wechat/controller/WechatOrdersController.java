package com.kyx.biz.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustCouponService;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.recharge.service.RechargeService;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;

/**
 * 订单管理
 * 
 * @author 童亦刚
 * @Descripton
 */
@Controller
@RequestMapping(value = "/wechat/orders")
public class WechatOrdersController extends BaseController {

	@Resource
	private OrdersService ordersService;
	
	
	@Resource
	private CustCouponService custCouponService;
	
	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private WechatCardRecordService wechatCardRecordService;
	
	
	
	//pageType1：历史订单 2消费记录
	@RequestMapping("/orderList/{orderStatus}")
	public String orderList(Model model, @PathVariable("orderStatus") Integer orderStatus,Integer pageType ) {
		model.addAttribute("orderStatus",orderStatus);
		model.addAttribute("pageType",pageType);
		return "orders/orderlist";
	}
	
	@RequestMapping("/evaluateOrder/{orderId}")
	public String evaluateOrder(Model model, @PathVariable("orderId")Integer orderId ) {
		//Dbs.setDbName("kxnz001");
		Orders orders = ordersService.queryById(orderId);
		model.addAttribute("orders", orders);
		return "orders/evaluateorder";
	}
	
	//pageType1：历史订单 2消费记录
	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, Pager pager,Orders orders,Integer pageType) {
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		//	orders.setShopId(custom.getShopId());#0726 tygadd
		//Dbs.setDbName("kxnz001");
		orders.setCustId(custom.getId());
		GrdData result = ordersService.getWeChatOrdersList(orders, pager,pageType);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/addEvaluate")
	@ResponseBody
	public String addEvaluate(HttpServletRequest request,OrdersEvaluate ordersEvaluate) {
		//Dbs.setDbName("kxnz001");
		//ordersEvaluate.setCustId(1);
		ordersEvaluate.setCustId(getCustomId(request));
		ordersEvaluate.setShopId(getShopId(request));
		RetInfo retInfo = ordersService.saveEvaluate(ordersEvaluate);
		return AppUtils.getReturnInfo(retInfo);
	}
	

	@RequestMapping("/couponList")
	public String couponList(HttpServletRequest request,Model model ) {
		return "orders/couponlist";
	}
	
	@RequestMapping("/rechargeList")
	public String rechargeList(HttpServletRequest request,Model model ) {
		return "orders/rechargelist";
	}
	
	@RequestMapping(value = "/getCouponList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getCouponList(HttpServletRequest request, Pager pager,CustCoupon custCoupon) {
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		custCoupon.setCustId(custom.getId());
		GrdData result = custCouponService.getList(custCoupon, pager);
		return JSON.toJSONString(result);
	}
	@RequestMapping(value = "/getRechargeList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getRechargeList(HttpServletRequest request, Pager pager,Recharge recharge) {
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		recharge.setCardNo(custom.getCardNo());
		GrdData result = rechargeService.getList(recharge, pager);
		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 优惠卷
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cardRecordList")
	public String cardRecordList(HttpServletRequest request,Model model ) {
		return "orders/cardRecordlist";
	}
	
	@RequestMapping(value = "/getCardRecordList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getCardRecordList(HttpServletRequest request, WechatCardRecord wechatCardRecord, Pager pager) {
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		wechatCardRecord.setCustId(custom.getId());
		wechatCardRecord.setEffect(1);//有效
		GrdData result=wechatCardRecordService.getInfo(wechatCardRecord,pager);
		return JSON.toJSONString(result);
	}
	
}
