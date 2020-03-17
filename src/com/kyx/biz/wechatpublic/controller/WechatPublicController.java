package com.kyx.biz.wechatpublic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyx.biz.base.controller.BaseController;

/**
 * 微信公众号管理父类
 * @author 严大灯
 * @data 2019-9-5 上午11:18:06
 * @Descripton
 */
public class WechatPublicController extends BaseController {
	/**
	 * 公众页面跳转
	 * @param model
	 * @param request
	 * @param index
	 * @return
	 */
	@RequestMapping("/{index}")
	public String index(Model model, HttpServletRequest request,@PathVariable("index") String index) {
		return "wechatpublic/"+index;
	}
}
