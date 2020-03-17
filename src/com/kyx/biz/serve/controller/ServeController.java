package com.kyx.biz.serve.controller;

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
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.model.ServeClass;
import com.kyx.biz.serve.service.ServeClassService;
import com.kyx.biz.serve.service.ServeService;

/**
 * 服务管理
 * @author 童亦刚
 * @Descripton
 */
@Controller
@RequestMapping(value = "/serve")
public class ServeController extends BaseController{
		
	@Resource
	private ServeService serveService;
	@Resource
	private ServeClassService serveClassService;
	
	
	
	@RequestMapping(value="/getServeList")
    @ResponseBody
    public String getServeList(HttpServletRequest request,Serve serve) {
		serve.setShopId(getShopId(request));
        return JSON.toJSONString(serveService.getServeList(serve));
    }
	
	/**
	 *获取会员产品价格 
	 */
	@RequestMapping(value="/getServeCustomPriceList")
	@ResponseBody
	public String getServeCustomPriceList(HttpServletRequest request,Serve serve) {
		serve.setShopId(getShopId(request));
		return JSON.toJSONString(serveService.getServeCustomPrice(serve));
	}
	
	
	@RequestMapping ("/infolist")
	public String infoList(){
		return "config/servelist";
	}
	
    @RequestMapping(value="/getList")
    @ResponseBody
    public String getList(Serve serve,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	serve.setShopId(shops.getId());
    	GrdData result=serveService.getServePage(serve, pager);
        return JSON.toJSONString(result);
    }
    
    @RequestMapping(value="/getServeListByIds")
    @ResponseBody
    public String getServeListByIds(String ids){
    	return JSON.toJSONString(serveService.getServeListByIds(ids));
    }
    
    
    
    @RequestMapping("/addData")
    public String addRole(Model model,HttpSession session){
    	List<ServeClass> list=serveClassService.getAll();
    	model.addAttribute("serveClassList", list);
    	return "config/editserve";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	List<ServeClass> list=serveClassService.getAll();
    	model.addAttribute("serveClassList", list);
    	Serve serve=serveService.getById(id);
    	model.addAttribute("serve", serve);
    	return "config/editserve";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Serve serve,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	serve.setShopId(shops.getId());
    	RetInfo retInfo=serveService.saveData(serve);
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
    	RetInfo retInfo=serveService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    
	
	
	
	
}
