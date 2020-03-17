package com.kyx.biz.userskills.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.serve.service.ServeClassService;
import com.kyx.biz.userskills.service.UserSkillsService;

/**
 * 
 * @author 严大灯
 * @data 2019-4-15 下午4:14:49
 * @Descripton
 */
@Controller
@RequestMapping(value = "/userskills")
public class UserSkillsController {
	@Resource
	private UserService userService;
	@Resource
	private ServeClassService serveClassService;
	@Resource
	private UserSkillsService userSkillsService; 
	@RequestMapping ("/infolist")
	public String infoList(Model model,HttpSession session,Integer userId){
		Shops shops=(Shops) session.getAttribute("shopsSession");
//		String currentDbName=Dbs.getDbName();
		Dbs.setDbName(Dbs.getMainDbName());
		List<User> list=userService.getByShop(shops);
		//List<User> list=userService.getByShopId(shops.getId());
		model.addAttribute("list", list);
//		if(userId==null&&list.size()>0)
//			userId=list.get(0).getId();
//		Dbs.setDbName(currentDbName);
//		List<Map> serveList=serveClassService.getAllInfo(userId);
//		model.addAttribute("serveList", serveList);
		return "user/setuserskills";
	}
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Integer userId,String ids,HttpSession session) {
    	RetInfo retInfo=userSkillsService.saveData(userId,ids,session);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    @RequestMapping("/getData")
    @ResponseBody
    public String getData(Integer userId,HttpSession session) {
		List<Map> serveList=serveClassService.getAllInfo(userId,session);
        return JSON.toJSONString(serveList);
    }
    
    /**
     * 删除数据
     * @param role
     * @return
     */
//    @RequestMapping("/delData")
//    @ResponseBody
//    public String delData(String ids) {
//    	RetInfo retInfo=serveClassService.delData(ids);
//        return AppUtils.getReturnInfo(retInfo);
//    }
    
    

}
