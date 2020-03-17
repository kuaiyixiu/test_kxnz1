package com.kyx.biz.pay.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.pay.service.PayService;
import com.kyx.biz.wxutil.HttpRequest;
import com.kyx.biz.wxutil.MyWxConfig;
import com.kyx.biz.wxutil.WXPayUtil;

/**
 * @author zl
 *
 */
@Service("payService")
public class PayServiceImpl implements PayService {
  @Autowired
  private MyWxConfig myWxConfig;
  @Override
  public RetInfo unifiedorder(HttpServletRequest request, String openId) {
    try {
      // 拼接统一下单地址参数
      Map<String, String> paraMap = new HashMap<String, String>();
      // 获取请求ip地址
      String ip = request.getHeader("x-forwarded-for");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
      if (ip.indexOf(",") != -1) {
        String[] ips = ip.split(",");
        ip = ips[0].trim();
      }
      // 获取session中的公众号配置(之后可改成读取配置文件)
      paraMap.put("appid", myWxConfig.getAppID());
      paraMap.put("body", "vip会员充值");
      paraMap.put("mch_id", myWxConfig.getMchID());
      paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
      paraMap.put("openid", openId);
      paraMap.put("out_trade_no", WXPayUtil.getUniqueOrder());// 订单号
      paraMap.put("spbill_create_ip", ip);
      paraMap.put("total_fee", "1");
      paraMap.put("trade_type", "JSAPI");
      paraMap.put("notify_url", "http://lay.easy.echosite.cn/liantu/wechat/pay/notify.do");// 此路径是微信服务器调用支付结果通知路径随意写
      String sign = WXPayUtil.generateSignature(paraMap, myWxConfig.getKey());
      paraMap.put("sign", sign);
      String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式

      // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
      String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

      String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);// 发送post请求"统一下单接口"返回预支付id:prepay_id

      // 以下内容是返回前端页面的json数据
      if (xmlStr.indexOf("SUCCESS") != -1) {
        Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
        String prepay_id = (String) map.get("prepay_id");
        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", myWxConfig.getAppID());
        payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        payMap.put("signType", "MD5");
        payMap.put("package", "prepay_id=" + prepay_id);
        String paySign = WXPayUtil.generateSignature(payMap, myWxConfig.getKey());
        payMap.put("paySign", paySign);
        payMap.put("packagse", "prepay_id=" + prepay_id);
        // 创建订单
        return new RetInfo(true, "下单成功", payMap);
      } else {

        return new RetInfo(false, "下单失败");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 微信支付成功回调函数
   */
  @Override
  public String notify(HttpServletRequest request, HttpServletResponse response) {
    // System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
    InputStream is = null;
    try {
      is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
      String xml = WXPayUtil.inputStream2String(is, "UTF-8");
      Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map

      if (notifyMap.get("return_code").equals("SUCCESS")) {
        if (notifyMap.get("result_code").equals("SUCCESS")) {
          String ordersSn = notifyMap.get("out_trade_no");// 商户订单号
          String amountpaid = notifyMap.get("total_fee");// 实际支付的订单金额:单位 分
          BigDecimal amountPay =
              (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);// 将分转换成元-实际支付金额:元
          // String openid = notifyMap.get("openid"); //如果有需要可以获取
          // String trade_type = notifyMap.get("trade_type");

          /*
           * 以下是自己的业务处理------仅做参考 更新order对应字段/已支付金额/状态码
           */
          // Orders order = ordersService.selectOrdersBySn(ordersSn);
          // if(order != null) {
          // order.setLastmodifieddate(new Date());
          // order.setVersion(order.getVersion().add(BigDecimal.ONE));
          // order.setAmountpaid(amountPay);//已支付金额
          // order.setStatus(2L);//修改订单状态为待发货
          // int num = ordersService.updateOrders(order);//更新order
          //
          // String amount = amountPay.setScale(0,
          // BigDecimal.ROUND_FLOOR).toString();//实际支付金额向下取整-123.23--123
          // /*
          // * 更新用户经验值
          // */
          // Member member = accountService.findObjectById(order.getMemberId());
          // accountService.updateMemberByGrowth(amount, member);
          //
          // /*
          // * 添加用户积分数及添加积分记录表记录
          // */
          // pointService.updateMemberPointAndLog(amount, member, "购买商品,订单号为:"+ordersSn);
          //
          // }
          //
        }
      }

      // 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
      response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (is != null) {

          is.close();
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    return null;
  }

}
