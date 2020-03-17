package com.kyx.basic.sms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.sms.model.SmsRecharge;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.sms.service.SmsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
/**
 * 短信管理
 * @author 严大灯
 * @data 2019-9-4 下午1:29:11
 * @Descripton
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {
	@Resource
	private SmsService smsService; 
	/**
	 * 跳转公共页面
	 * @param model
	 * @param request
	 * @param index
	 * @return
	 */
	@RequestMapping("/{index}")
	public String index(Model model, HttpServletRequest request,@PathVariable("index") String index) {
		return "sms/"+index;
	}
	/**
	 * 短信充值记录列表
	 * @param smsRecharge
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryRechargeList")
	@ResponseBody
	public String queryRechargeList(SmsRecharge smsRecharge,Pager pager,HttpServletRequest request) throws Exception {
		GrdData grdData=smsService.getRechargePage(smsRecharge,pager);
		return JSON.toJSONString(grdData);
	}
	/**
	 * 短信消费记录
	 * @param smsSend
	 * @param pager
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySendList")
	@ResponseBody
	public String querySendList(SmsSend smsSend,Pager pager,HttpServletRequest request) throws Exception {
		GrdData grdData=smsService.getSendPage(smsSend,pager);
		return JSON.toJSONString(grdData);
	}
}
