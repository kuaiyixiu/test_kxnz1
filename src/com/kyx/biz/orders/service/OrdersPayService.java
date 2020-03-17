package com.kyx.biz.orders.service;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.model.OrdersPay;

public interface OrdersPayService {

  /**
   * 保存订单支付方式
   * 
   * @param request
   * @param ordersPays
   * @param orderId
   * @return
   */
  @Transactional
  RetInfo saveOrdersPayList(List<OrdersPay> ordersPays, Integer orderId);


  @Transactional
  RetInfo delOrdersPay(Integer ordersPayId);


  @Transactional
  RetInfo updateOrdersPay(OrdersPay ordersPay) throws Exception;


  /**
   * 根据ID查订单支付方式
   * 
   * @param request
   * @param id
   * @param shopId
   * @return
   */
  OrdersPay queryById(HttpServletRequest request, Integer id, Integer shopId);

  /**
   * 根据订单ID查支付列表
   * 
   * @param request
   * @param orderId
   * @return
   */
  List<OrdersPay> queryListByOrderId(Integer orderId);

  /**
   * 获取订单支付金额
   * 
   * @param orderId
   * @return
   */
  BigDecimal getOrderPayAmt(Integer orderId);

  void saveOrdersPay(Integer orderId, OrdersPay ordersPay);
}
