package com.kyx.biz.customprice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.customprice.model.CustomPrice;
import com.kyx.biz.customprice.service.CustomPriceService;
import com.kyx.biz.product.service.ProductClassService;
import com.kyx.biz.serve.model.ServeClass;
import com.kyx.biz.serve.service.ServeClassService;

/**
 * 
 * @author 严大灯
 * @data 2019-5-11 上午9:41:02
 * @Descripton
 */
@Controller
@RequestMapping(value = "/customprice")
public class CustomPriceController {
	@Resource
	private CustomPriceService customPriceService;
	@Resource
	private CustomTypeService customTypeService;
	@Resource
	private ServeClassService serveClassService;
	@Resource
	private ProductClassService productClassService;
	@RequestMapping ("/infolist/{kind}")
	public String infoList(Model model,HttpSession session,@PathVariable("kind") Integer kind){
		model.addAttribute("kind", kind);
		List<CustomType> customTypes=customTypeService.getByShopId(AppUtils.getShopId(session));
		model.addAttribute("customTypes", customTypes);
		List classList;
		if(kind==1){//服务类型列表
			ServeClass serveClass=new ServeClass();
			serveClass.setShopId(AppUtils.getShopId(session));
			classList=serveClassService.getByServeClass(serveClass);
		}else{//产品类型列表
			classList=productClassService.getAll(session);
		}
		model.addAttribute("classList", classList);
		return "config/custpricelist";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(CustomPrice customPrice,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	customPrice.setShopId(shops.getId());
    	GrdData result=customPriceService.getInfo(customPrice,null);
        return JSON.toJSONString(result);
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveOneData")
    @ResponseBody
    public String saveOneData(CustomPrice customPrice,HttpSession session) {
    	RetInfo retInfo=customPriceService.saveData(customPrice);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 批量修改
     * @param model
     * @return
     */
	@RequestMapping ("/batchaddcustprice")
	public String batchAddCustPrice(Model model){

		return "config/batchaddcustprice";
	}
    /**
     * 批量存储
     * @param customPrice
     * @param session
     * @return
     */
    @RequestMapping("/saveBatchData")
    @ResponseBody
    public String saveBatchData(CustomPrice customPrice,HttpSession session) {
    	RetInfo retInfo=customPriceService.saveBatchData(customPrice);
        return AppUtils.getReturnInfo(retInfo);
    }
	
}
