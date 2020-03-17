package com.kyx.biz.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.pay.service.PayService;

@Controller
@RequestMapping(value = "/wechat/pay")
public class WechatPayController extends BaseController {

  @Autowired
  private PayService payService;

	
	@RequestMapping(value="/orders", method = RequestMethod.POST)
	@ResponseBody
  public RetInfo orders(HttpServletRequest request, String openId) {

    return payService.unifiedorder(request, openId);

  }

  @RequestMapping("/notify")
  public String callBack(HttpServletRequest request, HttpServletResponse response) {
    
    return payService.notify(request, response);

}
}
