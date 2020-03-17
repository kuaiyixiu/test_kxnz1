package com.kyx.basic.resource.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.resource.service.ResourceService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;

/**
 * 菜单控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/menu")
public class ResourceController {
	@Resource
	private ResourceService resourceService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "menu/menuInfo";
	}

	/**
	 * 查询菜单
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "/queryMenu", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryMenu(com.kyx.basic.resource.model.Resource resource,
			Pager pager) {
		GrdData result = resourceService.queryMenu(resource, pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 添加菜单视图
	 * 
	 * @return
	 */
	@RequestMapping("/addMenuView")
	public String addMenuView(Model model, String parentId) {
		model.addAttribute("parentId", parentId);
		return "menu/addMenu";
	}

	/**
	 * 保存菜单
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/saveMenu")
	@ResponseBody
	public String saveMenu(com.kyx.basic.resource.model.Resource resource) {
		RetInfo retInfo = resourceService.saveMenu(resource);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 编辑菜单
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping("/editMenu")
	@ResponseBody
	public String editMenu(com.kyx.basic.resource.model.Resource resource) {
		RetInfo retInfo = resourceService.editMenu(resource);

		return AppUtils.getReturnInfo(retInfo);
	}

	/**
	 * 菜单详情
	 * 
	 * @param model
	 * @param resource
	 * @return
	 */
	@RequestMapping("/menuDetailView")
	public String menuDetailView(Model model, Integer id, String event) {
		model.addAttribute("menu", resourceService.selectByPrimaryKey(id));
		model.addAttribute("event", event);

		return "menu/menuDetail";
	}

	/**
	 * 删除数据
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/delData")
	@ResponseBody
	public String delData(String ids) {
		RetInfo retInfo = resourceService.delData(ids);

		return AppUtils.getReturnInfo(retInfo);
	}

}
