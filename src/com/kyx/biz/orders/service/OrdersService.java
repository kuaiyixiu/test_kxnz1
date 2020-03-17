package com.kyx.biz.orders.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.payRecord.vo.ProfitVo;

public interface OrdersService {

  /**
   * 分页获取订单列表
   * 
   * @param orders
   * @param pager
   * @param pageType 1订单列表 2挂账订单 3入账订单
   * @return
   */
  GrdData getOrdersList(Orders orders, Integer pageType, Pager pager);

  /**
   * 保存订单
   * 
   * @param request
   * @param orders
   * @return
   */
  @Transactional
  RetInfo saveOrders(Orders orders);

  /**
   * 提交订单
   * 
   * @param request
   * @param orders 1编辑，2待施工（已提交），3施工中， 4施工完成 5入账 6挂账 7反入账 8反挂账
   * @return
   * @throws Exception
   */
  @Transactional(rollbackFor = Exception.class)
  RetInfo updateOrders(HttpServletRequest request, Orders orders, Integer operateType)
      throws Exception;

  /**
   * 删除订单
   * 
   * @param request
   * @param orders
   * @return
   * @throws Exception
   */
  @Transactional(rollbackFor = Exception.class)
  RetInfo deleteOrders(Integer id) throws Exception;

  /**
   * 根据订单ID获取单个订单信息
   * 
   * @param request
   * @param id
   * @return
   */
  Orders queryById(Integer id);

  /**
   * 提交选择的用户套餐
   * 
   * @throws Exception
   */
  @Transactional(rollbackFor = Exception.class)
  RetInfo submitMeal(String chooseMealInfo, Integer customId, Integer orderId, Integer shopId)
      throws Exception;


  @Transactional(rollbackFor = Exception.class)
  RetInfo submitMeal(String chooseMealInfo, Integer customId, Integer orderId, Integer shopId,
      Integer dtId, Integer itemType) throws Exception;

  /**
   * 微信分页获取订单列表
   * 
   * @param orders
   * @param pager
   * @return
   */
  GrdData getWeChatOrdersList(Orders orders, Pager pager, Integer pageType);

  /**
   * 提供给手机端的订单那列表
   * 
   * @param orders
   * @param pager
   * @return
   */
  List<Orders> queryOrders(Orders orders, Pager pager);

  /**
   * 根据车牌查询获取最近一次的订单信息
   * 
   * @param request
   * @param id
   * @return
   */
  Orders queryLastOrdersByCarNumber(String carNumber);


  @Transactional
  RetInfo saveEvaluate(OrdersEvaluate ordersEvaluate);

  /**
   * 获取时间区间（订单完结时间）内 订单的营收
   * 
   * @param dateRangeStartTime
   * @param dateRangeEndTime
   * @return
   */
  ProfitVo getOrderRevenue(Date dateRangeStartTime, Date dateRangeEndTime);

  /**
   * 获取时间区间内订单的成本
   * 
   * @param dateRangeStartTime
   * @param dateRangeEndTime
   * @return
   */
  ProfitVo getOrderCost(Date dateRangeStartTime, Date dateRangeEndTime);

  /**
   * 获取利润合计
   * 
   * @param dateRangeStartTime
   * @param dateRangeEndTime
   * @return
   */
  List<ProfitVo> getProFitList(Date dateRangeStartTime, Date dateRangeEndTime);

  /**
   * 获取入账订单金额
   * 
   * @param dateRangeStartTime
   * @param dateRangeEndTime
   * @return
   */
  BigDecimal getFinishOrderAmt(Integer shopId, Date dateRangeStartTime, Date dateRangeEndTime);

  /**
   * 查询盒子订单详情
   * 
   * @param ordersId
   * @return
   */
  Orders queryBoxOrderDetail(Integer ordersId);

  void submitOrder(Orders orders);
}
