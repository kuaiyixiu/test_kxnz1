package com.kyx.biz.coupon.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.coupon.model.Coupon;
import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.model.CouponPackDt;
import com.kyx.biz.coupon.service.CouponPackDtService;
import com.kyx.biz.coupon.service.CouponPackService;
import com.kyx.biz.coupon.service.CouponService;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.service.CustCouponService;
import com.kyx.biz.customMeal.model.CustomMeal;

@Controller
@RequestMapping(value = "/coupon")
public class CouponController extends BaseController{

	@Resource
	private CouponService couponService;
	@Resource
	private CouponPackService couponPackService;
	
	
	@Resource
	private CouponPackDtService couponPackDtService;
	
	@Resource
	private CustCouponService custCouponService;
	
	
	@RequestMapping("/couponlist")
	public String couponlist(Model model) {
		return "coupon/couponlist";
	}

	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, Coupon coupon, Pager pager) {
	//	coupon.setShopId(getShopId(request));
		GrdData result = couponService.getList(coupon, pager);
		return JSON.toJSONString(result);
	}
	/**
	 * 获取所有代金券
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCouponInfo")
	@ResponseBody
	public String getCouponInfo(HttpServletRequest request) {
		RetInfo ret=couponService.getAll();
		return AppUtils.getReturnInfo(ret);
	}
	
	/**
	 * 获取所有代金券包
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCouponPackList")
	@ResponseBody
	public String getCouponPackList(HttpServletRequest request) {
		RetInfo ret=couponPackService.getAll();
		return AppUtils.getReturnInfo(ret);
	}
	
	 @RequestMapping("/addCoupon")
     public String addCoupon(){
    	return "coupon/addcoupon";
     }
	 
	 
	 @RequestMapping("/updateCoupon")
	 public String updateCoupon(Model model,Integer id){
		Coupon coupon = couponService.selectByPrimaryKey(id);
	    model.addAttribute("coupon", coupon);
		return "coupon/addcoupon";
	 }
	 
	 
	 
	@RequestMapping("/saveCoupon")
	@ResponseBody
	public String saveCoupon(Coupon coupon) {
		if (coupon.getId() != null) {
			RetInfo retInfo = couponService.updateCoupon(coupon);
			return AppUtils.getReturnInfo(retInfo);
		} else {
			RetInfo retInfo = couponService.saveCoupon(coupon);
			return AppUtils.getReturnInfo(retInfo);
		}
	}
	
	@RequestMapping("/delCoupon")
    @ResponseBody
    public String delCoupon(Integer ids,HttpServletRequest request) {
		RetInfo retInfo= couponService.delCoupon(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
	
	
	//代金卷包

	@RequestMapping("/couponpacklist")
	public String couponpackList(Model model) {
		return "coupon/couponpacklist";
	}

	@RequestMapping(value = "/getPackList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getPackList(HttpServletRequest request, CouponPack couponPack, Pager pager) {
	//	coupon.setShopId(getShopId(request));
		GrdData result = couponPackService.getList(couponPack, pager);
		return JSON.toJSONString(result);
	}
	
	
	 @RequestMapping("/addCouponPack")
     public String addCouponPack(){
    	return "coupon/addcouponpack";
     }
	 
	 
	 @RequestMapping("/updateCouponPack")
	 public String updateCouponPack(Model model,Integer id){
		CouponPack couponPack = couponPackService.selectByPrimaryKey(id);
	    model.addAttribute("couponPack", couponPack);
		return "coupon/addcouponpack";
	 }
	 
	 
	 
	@RequestMapping("/saveCouponPack")
	@ResponseBody
	public String saveCouponPack(CouponPack couponPack) {
		if (couponPack.getId() != null) {
			RetInfo retInfo = couponPackService.updateCouponPack(couponPack);
			return AppUtils.getReturnInfo(retInfo);
		} else {
			RetInfo retInfo = couponPackService.saveCouponPack(couponPack);
			return AppUtils.getReturnInfo(retInfo);
		}
	}
	
	@RequestMapping("/delCouponPack")
    @ResponseBody
    public String delCouponPack(Integer ids,HttpServletRequest request) {
		RetInfo retInfo= couponPackService.delCouponPack(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
	
	@RequestMapping("/couponpackdtlist")
	public String couponpackdtlist(Model model,Integer packId) {
		 model.addAttribute("packId", packId);
		return "coupon/couponpackdtlist";
	}
	
	@RequestMapping(value = "/getListByPackId", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getListByPackId(HttpServletRequest request,Integer packId, Pager pager) {
		GrdData result = couponPackDtService.getListByPackId(packId,pager);
		return JSON.toJSONString(result);
	}
	
	
	
	 @RequestMapping("/addCouponPackDt/{packId}")
    public String addCouponPackDt(@PathVariable("packId") Integer packId,Model model){
		 Coupon coupon = new Coupon();
		 coupon.setStatue(1);
		List<Coupon> couponList  = couponService.getListNoLimit(coupon);
		 model.addAttribute("couponList", couponList);
		 model.addAttribute("packId", packId);
		 return "coupon/addcouponpackdt";
    }
	 
	 
		@RequestMapping("/saveCouponPackDt")
		@ResponseBody
		public String saveCouponPackDt(CouponPackDt couponPackDt) {
			RetInfo retInfo = couponPackDtService.saveCouponPackDt(couponPackDt);
			return AppUtils.getReturnInfo(retInfo);
		}
		
		
		@RequestMapping("/delCouponPackDt")
	    @ResponseBody
	    public String delCouponPackDt(Integer ids,HttpServletRequest request) {
			RetInfo retInfo= couponPackDtService.delCouponPackDt(ids);
	        return AppUtils.getReturnInfo(retInfo);
	    }
		
		
		 @RequestMapping(value="/chooseCoupon/{customId}/{orderId}")
		 public String chooseCoupon(HttpServletRequest request,Model model,@PathVariable("customId") Integer customId,@PathVariable("orderId") Integer orderId) {
			 List<CustCoupon> custCoupons  = custCouponService.selectByCustId(customId);
			 model.addAttribute("custCoupons", custCoupons);
			 model.addAttribute("customId", customId);
			 model.addAttribute("orderId", orderId);
			 model.addAttribute("payId", BasicContant.payTypeContant.COUPON);
			 model.addAttribute("shopId", getShopId(request));
			 return "orders/chooseCoupon";
		 }
		 
		 
		 
		 /**
		  * 提交选择的代金券
		 * @throws Exception 
		  */
		 @RequestMapping(value="/submitCoupon")
		 @ResponseBody
		 public String submitCoupon(HttpServletRequest request,String couponIds, String amounts, Integer customId,Integer orderId) throws Exception {
			return  AppUtils.getReturnInfo(couponService.submitCoupon(couponIds, amounts, customId, orderId));
		 }
		 
}
