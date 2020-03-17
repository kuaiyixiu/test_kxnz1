package com.kyx.biz.product.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.product.service.ProductClassService;
/**
 * 
 * @author 严大灯
 * @data 2019-4-18 下午3:40:47
 * @Descripton
 */
@Controller
@RequestMapping(value = "/productclass")
public class ProductClassController {
	@Resource
	private ProductClassService productClassService;
	@RequestMapping ("/infolist")
	public String infoList(){
		return "config/productclass";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(ProductClass productClass,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	productClass.setShopId(shops.getId());
    	GrdData result=productClassService.getInfo(productClass,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/addData")
    public String addRole(Model model,HttpSession session){
    	return "config/addproductclass";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	ProductClass productClass=productClassService.getById(id);
    	model.addAttribute("productClass", productClass);
    	return "config/addproductclass";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(ProductClass productClass,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	productClass.setShopId(shops.getId());
    	RetInfo retInfo=productClassService.saveData(productClass);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 删除数据
     * @param role
     * @return
     */
    @RequestMapping("/delData")
    @ResponseBody
    public String delData(String ids) {
    	RetInfo retInfo=productClassService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    

}
