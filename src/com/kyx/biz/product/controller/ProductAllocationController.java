package com.kyx.biz.product.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.product.model.ProductAllocation;
import com.kyx.biz.product.service.ProductAllocationService;
/**
 * 库存调拨
 * @author 严大灯
 * @data 2019-5-9 下午2:11:39
 * @Descripton
 */
@Controller
@RequestMapping(value = "/productAllocation")
public class ProductAllocationController {
	@Resource
	private ProductAllocationService productAllocationService;
	@Resource
	private ShopsService shopsService;
	@Resource
	private PayTypeService payTypeService;
	/**
	 * 调拨历史页面
	 * @return
	 */
	@RequestMapping ("/history")
	public String infoList(Model model,HttpSession session){
		Shops shops = (Shops) session.getAttribute("shopsSession");
		model.addAttribute("shops", shops);
		String dbName = shops.getDbName();
		List<Shops> shopList=shopsService.getShops().get(dbName);
		model.addAttribute("shopList", shopList);
		return "stock/productallocationhistory";
	}
	/**
	 * 调拨历史数据
	 * @param productAllocation
	 * @param pager
	 * @param session
	 * @return
	 */
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(ProductAllocation productAllocation,Pager pager,HttpSession session) {
    	Integer shopId=AppUtils.getShopId(session);
    	if(productAllocation.getKind()==1)//调出
    		productAllocation.setShopId(shopId);
    	else //调入
    		productAllocation.setRelationShop(shopId);
    	GrdData result=productAllocationService.getInfo(productAllocation,pager,session);
        return JSON.toJSONString(result);
    }
    /**
     * 调拨付款
     * @return
     */
	@RequestMapping ("/addDBPay")
	public String addDBPay(Model model){
    	List<PayType> payTypes = payTypeService.getPayType(PayType.SHOPTYPE);
		//.getByShopId(getShopId(request));
        model.addAttribute("payTypes", payTypes);
		return "stock/adddbpayinfo";
	}
	/**
	 * 调拨单付款
	 * @param productAllocation
	 * @param session
	 * @return
	 */
    @RequestMapping("/savePayInfo")
    @ResponseBody
    public String savePayInfo(ProductAllocation productAllocation,HttpSession session) {
    	RetInfo retInfo=productAllocationService.savePayInfo(productAllocation);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 调拨单作废
     * @param repertory
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveDestoryData")
    @ResponseBody
    public String saveDestoryData(ProductAllocation productAllocation,HttpSession httpSession) {
    	RetInfo retInfo=productAllocationService.saveDestoryData(productAllocation,httpSession);
        return AppUtils.getReturnInfo(retInfo);
    }

}
