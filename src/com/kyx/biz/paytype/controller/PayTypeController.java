package com.kyx.biz.paytype.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.dailypay.model.DailyPayType;
import com.kyx.biz.orders.service.OrdersRoyaltyService;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.payRecord.service.PayRecordService;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.model.ServeClass;

@Controller
@RequestMapping(value = "/payType")
public class PayTypeController extends BaseController{

	@Resource
	private PayTypeService payTypeService;

	
	@RequestMapping("/payTypeList")
	public String payRecordList(Model model) {
		return "paytype/payTypeList";
	}

	@RequestMapping(value = "/getPayTypeList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getPayTypeList(HttpServletRequest request,PayType payType, Pager pager) {
		payType.setShopId(getShopId(request));
		GrdData result = payTypeService.getList(payType, pager);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 跳转到增加页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddPayType")
	public String toAddType(Model model, HttpServletRequest request) {
		return "paytype/updatePayType";
	}
	/**
	 * 校验支付类型名称不能重复
	 * @param id
	 * @param count
	 * @return
	 */
    @RequestMapping("/chkData")
    @ResponseBody
    public String chkDtData(PayType payType) {
    	RetInfo retInfo=payTypeService.chkName(payType.getPayName());
        return AppUtils.getReturnInfo(retInfo);
    }
	
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/savePayType")
    @ResponseBody
    public String savePayType(PayType payType,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	payType.setShopId(shops.getId());
    	RetInfo retInfo=payTypeService.saveData(payType);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 跳转到编辑页面
     * @param model
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateType")
	public String toUpdateType(Model model, HttpServletRequest request, Integer id) {
    	PayType payType =  payTypeService.queryById(id);
		model.addAttribute("payType",payType);
 		return "paytype/updatePayType";
	}
    
    @RequestMapping("/delPayType")
	@ResponseBody
	public String delType(Integer ids) {
		RetInfo retInfo = payTypeService.deleteType(ids);
		return AppUtils.getReturnInfo(retInfo);
	}
}
