package com.kyx.biz.wechat.controller;

import java.io.PrintWriter;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kyx.biz.wechat.service.WechatCallBackService;
import com.kyx.biz.wxutil.MessageFormat;
import com.kyx.biz.wxutil.WechatVerify;


/**
 * 微信公共账号回调接口
 * 
 * @author 严大灯
 * @data 2019-5-13 下午1:58:35
 * @Descripton
 */
@Controller
@RequestMapping("/wechat/callback")
public class WechatCallBackController {
  @Resource
  private WechatCallBackService wechatCallBackService;

  /**
   * <h4>功能：[微信验证 ] 配置公众号服务器调用</h4> <h4></h4>
   * 
   * @param request
   * @param response
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/subscribeNotice", method = RequestMethod.GET)
  public String getMessageValidate(HttpServletRequest request, HttpServletResponse response,
      String signature, String echostr, String timestamp, String nonce) {
    System.out.println("subscribeNotice start");
    if (WechatVerify.checkSignature(signature, timestamp, nonce)) {
      System.out.println("================验签失败=====================");
      return echostr;
    } else {
      System.out.println("================验签失败=====================");
    }
    System.out.println("subscribeNotice end");
    return "";
  }

  /**
   * 关注/取消关注 用户请求openid调用
   * 
   * @param request
   * @param response
   * @param signature
   * @param echostr
   * @param timestamp
   * @param nonce
   * @throws Exception
   */
  @RequestMapping(value = "/subscribeNotice", method = RequestMethod.POST)
  public void subscribeNotice(HttpServletRequest request, HttpServletResponse response,
      String signature, String echostr, String timestamp, String nonce) throws Exception {
    System.out.println("subscribeNotice start");
    System.out.println("signature:" + signature);
    System.out.println("timestamp:" + timestamp);
    // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
    if (WechatVerify.checkSignature(signature, timestamp, nonce)) {
      Map<String, String> map = MessageFormat.xmlToMap(request);
      // 测试查看返回的消息
      for (String key : map.keySet()) {
        System.out.println("key= " + key + " and value= " + map.get(key));
      }
      String msg = wechatCallBackService.subscribeNotice(map);
      response.setContentType("text/xml,charset=utf-8");
      PrintWriter printWriter = response.getWriter();
      printWriter.println(msg);
      printWriter.close();

      System.out.println("subscribeNotice end");
    } else {
      System.out.println("================验签失败=====================");
    }
  }

}
