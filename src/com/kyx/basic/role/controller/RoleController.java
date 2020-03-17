package com.kyx.basic.role.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.resourcesrole.service.ResourcesRoleService;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.role.service.RoleService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;

/**
 * 角色管理
 * @author 严大灯
 * @data 2019-3-21 上午11:00:28
 * @Descripton
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController{
	@Resource
	private RoleService roleService;
	@Resource
	private ResourcesRoleService resourcesRoleService;
	@RequestMapping ("/roleinfo")
	public String showRoles(){
		//dangqian roleid
		
		return "role/roleinfo";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(Role role,Pager pager) {
    	GrdData result=roleService.getRolesInfo(role,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/addRole")
    public String addRole(){
    	return "role/addrole";
    }
    @RequestMapping("/editRole")
    public String editRole(Model model,Integer id){
    	Role sysRole=roleService.selectByPrimaryKey(id);
    	model.addAttribute("sysRole", sysRole);
    	return "role/addrole";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Role role) {
    	RetInfo retInfo=roleService.saveData(role);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 删除数据
     * @param role
     * @return
     */
    @RequestMapping("/delData")
    @ResponseBody
    public String delData(String ids,HttpServletRequest request) {
    	RetInfo retInfo=roleService.delData(ids,getShopId(request));
        return AppUtils.getReturnInfo(retInfo);
    }
    
    
    @RequestMapping("/setPermission")
    public String setPermission(Integer id,Model model){
    	model.addAttribute("roleId", id);
    	return "role/permission";
    }
    
    @RequestMapping("/rolePermission")
    @ResponseBody
    public String rolePermission(Integer id,Model model){
    	String jsonData=roleService.getMenuInfoByRoleId(id);
    	return jsonData;
    }
    
    
    @RequestMapping("/savePermission")
    @ResponseBody
    public String savePermission(Integer roleId,String resIds){
    	RetInfo retInfo=resourcesRoleService.savePermission(roleId,resIds);
    	return AppUtils.getReturnInfo(retInfo);
    }

}
