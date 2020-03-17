package com.kyx.basic.log.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.log.model.Log;
import com.kyx.basic.log.service.LogService;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.base.controller.BaseController;

@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController{

	@Resource
	private LogService logService;
	
	
	@RequestMapping("/logList")
	public String logList(Model model) {
		return "log/logList";
	}

	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, Log log, Pager pager) {
		log.setShopId(getShopId(request));
		GrdData result = logService.getList(log, pager);
		return JSON.toJSONString(result);
	}
	
}
