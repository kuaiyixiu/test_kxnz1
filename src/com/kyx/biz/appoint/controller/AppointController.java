package com.kyx.biz.appoint.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.service.AppointService;
import com.kyx.biz.base.controller.BaseController;

/**
 * 提醒控制层
 * 
 * @author daibin
 * @date 2019-4-10 上午10:04:24
 * 
 */
@Controller
@RequestMapping(value = "/appoint")
public class AppointController extends BaseController {

	@Resource
	private AppointService appointService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {

		return "appoint/appointInfo";
	}

	/**
	 * 查询客户预约
	 * 
	 * @param remind
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryCustomAppoints", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String queryCustomAppoints() {

		GrdData result = appointService.queryCustomAppoints();

		return JSON.toJSONString(result);
	}

	/**
	 * 完成预约
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/carryOutAppoint", produces = "text/plain; charset=utf-8")
	@ResponseBody
	@SystemControllerLog(module = "提醒", description = "完成预约提醒")
	public String carryOutAppoint(Integer id, HttpServletRequest request) {
		RetInfo retInfo = appointService.carryOutAppoint(id);

		return AppUtils.getReturnInfo(retInfo);
	}
}
