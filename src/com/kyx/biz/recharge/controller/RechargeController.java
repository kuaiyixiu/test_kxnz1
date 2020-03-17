package com.kyx.biz.recharge.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.service.CustCouponService;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.recharge.service.RechargeService;

/**
 * 充值记录 
 * @author 严大灯
 * @data 2019-7-17 下午2:43:27
 * @Descripton
 */
@Controller
@RequestMapping(value = "/recharge")
public class RechargeController extends BaseController {
	
	@Resource
	private RechargeService rechargeService;
	@Resource
	private CustCouponService custCouponService;
    /**
     * 充值记录列表
     * @param model
     * @param request
     * @return
     */
	@RequestMapping("/rechargelist")
	public String otherShopIndex(Model model, HttpServletRequest request) {
		return "custom/rechargelist";
	}

	/**
	 * 会员跨店查询
	 * 
	 * @param orders
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public String getList(Recharge recharge, Pager pager) {
		GrdData result = rechargeService.getList(recharge, pager);
		return JSON.toJSONString(result);
	}
	/**
	 * 充值赠送明细
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/couponDetail")
	public String couponDetail(Model model, HttpServletRequest request,Integer id) {
		List<CustCoupon> dtList=custCouponService.getRechargeId(1,id);
		model.addAttribute("dtList", dtList);
		return "custom/rechargecoupon";
	}
}
