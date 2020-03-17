package com.kyx.biz.serve.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.serve.model.ServeClass;
import com.kyx.biz.serve.service.ServeClassService;

/**
 * 服务分类
 * @author 严大灯
 * @data 2019-4-12 下午4:29:40
 * @Descripton
 */
@Controller
@RequestMapping(value = "/serveclass")
public class ServeClassController {
	@Resource
	private ServeClassService serveClassService;
	@RequestMapping ("/infolist")
	public String infoList(){
		return "config/serverclass";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(ServeClass serveClass,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	serveClass.setShopId(shops.getId());
    	GrdData result=serveClassService.getInfo(serveClass,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/addData")
    public String addRole(Model model,HttpSession session){
    	return "config/addserverclass";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	ServeClass sc=serveClassService.getById(id);
    	model.addAttribute("serveClass", sc);
    	return "config/addserverclass";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(ServeClass serveClass,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	serveClass.setShopId(shops.getId());
    	RetInfo retInfo=serveClassService.saveData(serveClass);
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
    	RetInfo retInfo=serveClassService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    

}
