package com.kyx.basic.sysparam.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.sysparam.model.SysParam;
import com.kyx.basic.sysparam.service.SysParamService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.orders.model.OrdersServe;

/**
 * 系统参数配置
 * 
 * 
 */
@Controller
@RequestMapping(value = "/sysParam")
public class SysParamController extends BaseController {

	@Resource
	private SysParamService sysParamService;

	@RequestMapping("/paramlist")
	public String paramlist(Model model, HttpServletRequest request) {
		return "sysparam/paramlist";
	}
	@RequestMapping("/getParamlist")
	@ResponseBody
	public String getParamlist(Model model, HttpServletRequest request) {
		List<SysParam>  sysParams = sysParamService.getListByShopId(getShopId(request));
		return JSON.toJSONString(sysParams);
	}
	

	@RequestMapping(value = "/saveParm", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String saveParm(HttpServletRequest request,@RequestBody List<SysParam> paramList) {
		RetInfo retInfo =sysParamService.saveParam(paramList, getUserId(request), getShopId(request));
		return AppUtils.getReturnInfo(retInfo);
	}

}
