package com.kyx.biz.supply.controller;

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
import com.kyx.biz.supply.model.Supply;
import com.kyx.biz.supply.service.SupplyService;
/**
 * 
 * @author 严大灯
 * @data 2019-4-19 下午3:46:11
 * @Descripton
 */
@Controller
@RequestMapping(value = "/supply")
public class SupplyController {
	@Resource
	private SupplyService supplyService; 
    
	@RequestMapping ("/infolist")
	public String infoList(){
		return "stock/supplylist";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(Supply supply,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	supply.setShopId(shops.getId());
    	GrdData result=supplyService.getInfo(supply,pager);
        return JSON.toJSONString(result);
    }
    
    
    @RequestMapping("/addData")
    public String addRole(Model model,HttpSession session){
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	model.addAttribute("shopId", shops.getId());
    	return "stock/addsupply";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	model.addAttribute("shopId", shops.getId());
    	Supply supply=supplyService.getById(id);
    	model.addAttribute("supply", supply);
    	return "stock/addsupply";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Supply supply) {
    	RetInfo retInfo=supplyService.saveData(supply);
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
    	RetInfo retInfo=supplyService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
	
	 

}
