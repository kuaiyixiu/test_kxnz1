package com.kyx.biz.pay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kyx.basic.util.RetInfo;

/**
 * @author zl
 *
 */
public interface PayService {
  /**
   * 微信支付下单
   * 
   * @return
   */
  RetInfo unifiedorder(HttpServletRequest request, String openId);

  /**
   * 支付成功回调函数
   * 
   * @param request
   * @param openId
   * @return
   */
  String notify(HttpServletRequest request, HttpServletResponse response);
}
