package com.kyx.biz.customType.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;

/**
 * 提醒控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/customType")
public class CustomTypeController extends BaseController {

	@Resource
	private CustomTypeService customTypeService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("shopId", getShopId(request));

		return "customConfig/customType/customType";
	}

	/**
	 * 查询所有会员类型
	 * 
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryAllCustomTypes", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryAllCustomTypes(CustomType customType, Pager pager) {
		GrdData result = customTypeService.queryAllCustomTypes(customType,
				pager);

		return JSON.toJSONString(result);
	}

	/**
	 * 删除会员类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delData")
	@ResponseBody
	public String delData(Integer ids) {

		return AppUtils.getReturnInfo(customTypeService.delCustomType(ids));
	}

	/**
	 * 添加会员类型
	 * 
	 * @param customType
	 * @return
	 */
	@RequestMapping("/addCustomType")
	@ResponseBody
	public String addCustomType(CustomType customType,
			HttpServletRequest request) {
		customType.setShopId(getShopId(request));

		return AppUtils.getReturnInfo(customTypeService
				.addCustomType(customType));
	}

	/**
	 * 添加会员类型视图
	 * 
	 * @return
	 */
	@RequestMapping("/addCustomTypeView")
	public String addCustomTypeView() {

		return "customConfig/customType/addCustomType";
	}

	/**
	 * 编辑会员类型视图
	 * 
	 * @return
	 */
	@RequestMapping("/editCustomTypeView")
	public String editCustomTypeView(Integer id, Model model) {
		model.addAttribute("customType",
				customTypeService.queryCustomTypeById(id));

		return "customConfig/customType/editCustomType";
	}

	/**
	 * 编辑会员类型
	 * 
	 * @param customType
	 * @return
	 */
	@RequestMapping("/editCustomType")
	@ResponseBody
	public String editCustomType(CustomType customType) {
		RetInfo ret = customTypeService.updateCustomType(customType);

		return AppUtils.getReturnInfo(ret);
	}
}
