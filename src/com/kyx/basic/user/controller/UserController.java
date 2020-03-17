package com.kyx.basic.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.role.service.RoleService;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;

/**
 * 用户管理
 * @author 严大灯
 * @data 2019-4-2 上午8:09:04
 * @Descripton
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private ShopsService shopsService;
	@RequestMapping ("/userinfo")
	public String showUser(){
		return "user/userinfo";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(User user,Pager pager,HttpSession session) {
    	//当前登录的门店只能看到门店下的员工
    	Shops shops=(Shops) session.getAttribute(Shops.SHOPS_SESSION);
    	user.setShopId(shops.getId());
    	user.setShops(shops);
    	GrdData result=userService.getUserInfo(user,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/addUser")
    public String addRole(Model model,HttpSession session){
    	String currentDBName=(String) session.getAttribute("currentDBName");
    	Dbs.setDbName(currentDBName);
    	List<Role> list=roleService.getAll();
    	model.addAttribute("roleList", list);
    	Shops shops=(Shops) session.getAttribute(Shops.SHOPS_SESSION);
    	//Dbs.setDbName(Dbs.getMainDbName());
    	//Shops shops=shopsService.getById(logininfo.getShopId());
    	model.addAttribute("shops", shops);
    	return "user/adduser";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	User user=userService.selectByPrimaryKey(id);
    	model.addAttribute("user", user);
    	String currentDBName=(String) session.getAttribute("currentDBName");
    	Dbs.setDbName(currentDBName);
    	List<Role> list=roleService.getAll();
    	model.addAttribute("roleList", list);
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	model.addAttribute("shops", shops);
    	return "user/adduser";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    @SystemControllerLog(module = "员工管理", description = "新增员工")
    public String saveData(User user) {
    	RetInfo retInfo=userService.saveData(user);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 删除数据
     * @param role
     * @return
     */
    @RequestMapping("/delData")
    @ResponseBody
    @SystemControllerLog(module = "员工管理", description = "删除员工")
    public String delData(String ids) {
    	RetInfo retInfo=userService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    @RequestMapping("/saveInfo")
    @ResponseBody
    @SystemControllerLog(module = "员工管理", description = "新增员工")
    public String saveInfo(User user) {
    	RetInfo retInfo=userService.saveData(user);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    @RequestMapping("/checkUserCount")
    @ResponseBody
    public String checkUserCount(HttpSession session) {
    	RetInfo retInfo=userService.checkUserCount(session);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 密码重置
     * @return
     */
	@RequestMapping ("/resetPwd")
	public String resetPwd(HttpServletRequest request){
		return "user/resetpwd";
	} 
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
    @RequestMapping("/resetPassWord")
    @ResponseBody
    public String resetPassWord(User user,HttpServletRequest request) {
    	user.setId(getUserId(request));
    	RetInfo retInfo=userService.resetPassWord(user);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 查询老板账户列表
     * @param user
     * @param pager
     * @param session
     * @return
     */
    @RequestMapping(value="/queryBossAccount",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String queryBossAccount(User user,Pager pager,HttpSession session) {
    	GrdData result=userService.queryBossAccount(user,pager);
        return JSON.toJSONString(result);
    }
    /**
     * 新增老板账户
     * @param request
     * @return
     */
	@RequestMapping ("/addBossAccount")
	public String addBossAccount(HttpServletRequest request,Integer id,Model model){
		if(id!=null){//修改
			User user=userService.selectByPrimaryKey(id);
			model.addAttribute("user", user);
		}
		return "shop/addbossaccount";
	} 
	/**
	 * 增加老板账户
	 * @param user
	 * @return
	 */
    @RequestMapping("/saveBossAccountInfo")
    @ResponseBody
    @SystemControllerLog(module = "门店管理", description = "新增老板账户")
    public String saveBossAccountInfo(User user) {
    	RetInfo retInfo=userService.saveBossAccountInfo(user);
        return AppUtils.getReturnInfo(retInfo);
    }

}
