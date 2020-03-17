package com.kyx.biz.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.product.service.ProductRepertoryService;
/**
 * 
 * @author 严大灯
 * @data 2019-4-29 下午2:50:03
 * @Descripton
 */
@Controller
@RequestMapping(value = "/quick")
public class QuickController {
	@Resource
	private ProductRepertoryService productRepertoryService;
	
    
	@RequestMapping ("/qkproduct")
	public String infoList(){
		return "quick/qkproduct";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(ProductRepertory productRepertory,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	productRepertory.setShopId(shops.getId());
    	productRepertory.setAvailable(1);
    	productRepertory.setKind(1);
    	GrdData result=productRepertoryService.getInfo(productRepertory,pager);
        return JSON.toJSONString(result);
    }
	 

}
