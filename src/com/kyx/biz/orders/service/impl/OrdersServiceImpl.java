package com.kyx.biz.orders.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customMealDt.mapper.CustomMealDtMapper;
import com.kyx.biz.customMealDt.model.CustomMealDt;
import com.kyx.biz.dailypay.mapper.DailyPayRecordMapper;
import com.kyx.biz.dailypay.model.DailyPayRecord;
import com.kyx.biz.debt.mapper.DebtRecordMapper;
import com.kyx.biz.debt.model.DebtRecord;
import com.kyx.biz.orders.mapper.OrdersEvaluateMapper;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersPayMapper;
import com.kyx.biz.orders.mapper.OrdersProductMapper;
import com.kyx.biz.orders.mapper.OrdersRoyaltyMapper;
import com.kyx.biz.orders.mapper.OrdersServeMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEnum;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.orders.model.OrdersPay;
import com.kyx.biz.orders.model.OrdersProduct;
import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.orders.service.OrdersPayService;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.orders.service.OrdersServeService;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.payRecord.mapper.MealPayRecordMapper;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.MealPayRecord;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.payRecord.vo.ProfitVo;
import com.kyx.biz.product.mapper.ProductConsumeMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductConsume;
import com.kyx.biz.product.service.ProductRepertoryService;
import com.kyx.biz.repertory.service.RepertoryService;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.wechatpublic.mapper.WechatCardRecordMapper;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

  @Resource
  private OrdersMapper ordersMapper;

  @Resource
  private OrdersServeService ordersServeService;

  @Resource
  private OrdersProductService ordersProductService;

  @Resource
  private OrdersPayService ordersPayService;

  @Resource
  private OrdersServeMapper ordersServeMapper;

  @Resource
  private OrdersProductMapper ordersProductMapper;

  @Resource
  private PayRecordMapper payRecordMapper;

  @Resource
  private OrdersPayMapper ordersPayMapper;

  @Resource
  private DebtRecordMapper debtRecordMapper;

  @Resource
  private ProductMapper productMapper;

  @Resource
  private OrdersRoyaltyMapper ordersRoyaltyMapper;

  @Resource
  private MealPayRecordMapper mealPayRecordMapper;

  @Resource
  private CustomMealDtMapper customMealDtMapper;

  @Resource
  private ServeMapper serveMapper;

  @Resource
  private OrdersEvaluateMapper ordersEvaluateMapper;

  @Resource
  private ProductConsumeMapper productConsumeMapper;

  @Resource
  private RepertoryService repertoryService;

  @Resource
  private DailyPayRecordMapper dailyPayRecordMapper;

  @Resource
  private ProductRepertoryService productRepertoryService;

  @Resource
  private CustomService customService;

  @Resource
  private CustomMapper customMapper;

  @Resource
  private WechatCardRecordMapper wechatCardRecordMapper;



  // pageType 1订单列表 2挂账订单 3入账订单4消费记录
  @Override
  public GrdData getOrdersList(Orders orders, Integer pageType, Pager pager) {
    if (pageType == 4)
      orders.setNotNullCustId(1); // 消费记录 只查有会员卡的订单

    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Orders> list = ordersMapper.getOrdersList(orders);
    for (Orders order : list) {
      if (pageType == 2 || pageType == 4) { // 挂账订单/消费记录，要查出挂账金额
        BigDecimal amt = ordersPayService.getOrderPayAmt(order.getId());
        order.setDebtAmt(order.getOrderAmt().subtract(amt));
      }
      if (order.getOrderType() == 2) {// 会员开单
        if (order.getCustId() != null) {
          Custom custom = customService.selectByPrimaryKey(order.getCustId());
          order.setCustom(custom);
          // order.setCustName(custom.getCustName());
        }
      }
    }

    return new GrdData(page.getTotal(), list);
  }

  @Override
  public RetInfo saveOrders(Orders orders) {
    orders.setOrderNo(createOrderNo());
    ordersMapper.insertSelective(orders);
    return new RetInfo(RetInfo.SUCCESS, orders.getId() + "");
  }

  @Override
  public RetInfo updateOrders(HttpServletRequest request, Orders orders, Integer operateType)
      throws Exception {
    String msg = validateOrder(orders, operateType);
    if (StringUtils.isNotBlank(msg)) {
      return RetInfo.Error(msg);
    }

    if (orders.getId() == null) {
      return RetInfo.Error("订单编号不能为空");
    }
    Orders oldOrders = ordersMapper.selectByPrimaryKey(orders.getId());

    BigDecimal orderAmt = BigDecimal.ZERO;
    List<OrdersServe> ordersServeList = ordersServeService.queryListByOrderId(orders.getId());
    if (operateType == 3 || operateType == 4) {
      if (!ObjectUtils.isEmpty(ordersServeList)) {
        for (OrdersServe os : ordersServeList) {
          if (operateType == 4) {
            orderAmt = orderAmt.add(os.getPrice());
          }
          if (operateType == 3) { // 3施工中
            os.setServeStatus(2); // 施工中
            ordersServeMapper.updateByPrimaryKeySelective(os);
          } else if (operateType == 4) { // 4施工完成
            os.setServeStatus(3); // 施工完成
            os.setDoneTime(new Date());
            ordersServeMapper.updateByPrimaryKeySelective(os);
          }
        }
      }
    }

    if (operateType == 2) { // 2待施工（已提交）
      orders.setOrderStatus(2);
      orders.setSubmitTime(new Date());
    } else if (operateType == 3) { // 3施工中
      orders.setOrderStatus(3);
      orders.setDoingTime(new Date());
    } else if (operateType == 4) { // 4施工完成
      orders.setOrderStatus(4);
      orders.setDoneTime(new Date());
    } else if (operateType == 5 || operateType == 6) {// 5入账 6挂账
      orders.setOrderStatus(operateType);
      orders.setFinishTime(new Date());
    } else if (operateType == 7) { // 7解除入账/解除挂账
      if (oldOrders.getOrderStatus() == 5)
        orders.setOrderStatus(7); // 反入账
      else if (oldOrders.getOrderStatus() == 6)
        orders.setOrderStatus(8);// 8反挂账
    }

    int count = ordersMapper.updateByPrimaryKeySelective(orders);

    if (operateType == 5 || operateType == 6) {// 5入账 6挂账
      BigDecimal newPayAmt = new BigDecimal("0"); // 本次新还的金额
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      BigDecimal payedAmt = new BigDecimal("0");
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        Integer score = 0;// 1元算100积分
        for (OrdersPay op : ordersPayList) {
          if (op.getCanEdit() == 1) { // 可编辑，说明是新增的
            PayRecord payRecord = new PayRecord();
            payRecord.setTypeId(op.getPayId());
            payRecord.setAmount(op.getPayAmount());
            payRecord.setRemark(op.getRemark());
            if (oldOrders.getOrderStatus() == 5 || oldOrders.getOrderStatus() == 6) { // 挂账，记账的时候还款
              payRecord.setKind(4);// 4客户挂账还款（收入）
            } else {
              payRecord.setKind(1); // 1客户订单支付
            }
            payRecord.setSourceId(op.getOrderId());
            payRecord.setShopId(op.getShopId());
            payRecordMapper.insertSelective(payRecord);
            op.setCanEdit(0); // 结算后不能编辑
            ordersPayMapper.updateByPrimaryKeySelective(op);
            newPayAmt = newPayAmt.add(op.getPayAmount());

            // 增加买家积分
            if (op.getPayId() == BasicContant.payTypeContant.WECHAT
                || op.getPayId() == BasicContant.payTypeContant.ALIPAY
                || op.getPayId() == BasicContant.payTypeContant.CASH
                || op.getPayId() == BasicContant.payTypeContant.BALANCE) {

              score += op.getPayAmount().multiply(new BigDecimal("100")).intValue();
            }

            // 处理优惠券相关信息(更新状态和关联订单信息)
            if (op.getPayId().equals(BasicContant.payTypeContant.CARDSET)) {
              User user = (User) request.getSession().getAttribute(User.USER_SESSION);
              WechatCardRecord cardRecord = new WechatCardRecord();
              cardRecord.setId(op.getSourceId());
              cardRecord.setState(1);
              cardRecord.setUseDate(new Date());
              cardRecord.setUseCar(oldOrders.getCarNumber());
              cardRecord.setSlipNo(oldOrders.getOrderNo());
              cardRecord.setSlipAmount(oldOrders.getPayAmount());
              cardRecord.setOptUserName(user.getUserRealname());
              wechatCardRecordMapper.updateByPrimaryKeySelective(cardRecord);
            }


          }
          payedAmt = payedAmt.add(op.getPayAmount());
        }

        if (score > 0) { // 更新客户积分
          if (oldOrders.getCustId() != null) {
            Custom custom = new Custom();
            custom.setId(oldOrders.getCustId());
            custom.setAddScore(score);
            customMapper.updateByPrimaryKeySelective(custom);
          }
        }
      }

      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord == null) { // 之前没有挂账记录
        if (oldOrders.getPayAmount().compareTo(payedAmt) == 1) { // 订单支付金额// >实际支付金额 // 生成挂账表
          debtRecord = new DebtRecord();
          debtRecord.setKind(1); // 1客户订单挂账
          debtRecord.setSourceId(orders.getId());
          debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setReturnAmount(BigDecimal.ZERO);
          debtRecord.setLeftAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setEnable(1);
          debtRecordMapper.insertSelective(debtRecord);
        }
      } else {
        debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));// 剩余欠款
        debtRecord.setReturnAmount(debtRecord.getReturnAmount().add(newPayAmt)); // 当前还款+本次还款
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }
    }

    if (operateType == 7) { // 7解除入账/解除挂账
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        Integer score = 0;// 1元算100积分
        for (OrdersPay op : ordersPayList) {
          PayRecord payRecord = new PayRecord();
          payRecord.setTypeId(op.getPayId());
          payRecord.setAmount(op.getPayAmount());
          payRecord.setRemark(op.getRemark());
          payRecord.setKind(8); // 8订单反入账或反挂账（支出）
          payRecord.setSourceId(op.getOrderId());
          payRecord.setShopId(op.getShopId());
          payRecordMapper.insertSelective(payRecord);
          op.setCanEdit(1); // 解除挂账/入账后 可以编辑了
          ordersPayMapper.updateByPrimaryKeySelective(op);

          // 增加买家积分
          if (op.getPayId() == BasicContant.payTypeContant.WECHAT
              || op.getPayId() == BasicContant.payTypeContant.ALIPAY
              || op.getPayId() == BasicContant.payTypeContant.CASH
              || op.getPayId() == BasicContant.payTypeContant.BALANCE) {

            score += op.getPayAmount().multiply(new BigDecimal("100")).intValue();
          }

        }

        if (score > 0) { // 更新客户积分
          if (oldOrders.getCustId() != null) {
            Custom custom = new Custom();
            custom.setId(oldOrders.getCustId());
            custom.setSubScore(score);
            customMapper.updateByPrimaryKeySelective(custom);
          }
        }
      }
      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord != null) {
        debtRecord.setEnable(0);
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }

    }

    return RetInfo.Success("保存成功");
  }


  /**
   * 校验订单
   * 
   * @param orders
   * @return
   */
  private String validateOrder(Orders orders, Integer operateType) {
    // if(operateType == 2){ //2待施工（已提交）
    // List<OrdersProduct> ordersProductList =
    // ordersProductService.queryListByOrderId(request, orders.getId());
    // if (!ObjectUtils.isEmpty(ordersProductList)){
    // Map<Integer, Integer> useMap = new HashMap<Integer, Integer>();
    // for(OrdersProduct op : ordersProductList){
    // if (useMap.get(op.getProductId()) == null)
    // useMap.put(op.getProductId(), 0);
    // useMap.put(op.getProductId(),useMap.get(op.getProductId())+op.getQuantity());
    // }
    //
    // for (Map.Entry<Integer, Integer> entry : useMap.entrySet()) {
    // Product product = productMapper.selectByPrimaryKey(entry.getKey());
    // if (product.getQuantity() < entry.getValue()){ //校验产品数量
    // return "产品数量不足!";
    // }
    // }
    // }
    // }
    return "";
  }

  /**
   * 创建订单编号
   * 
   * @return
   */
  private String createOrderNo() {
    return DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS");
  }

  @Override
  public Orders queryById(Integer id) {
    return ordersMapper.selectByPrimaryKey(id);
  }


  @Override
  public RetInfo deleteOrders(Integer id) throws Exception {
    if (id == null) {
      return RetInfo.Success("订单信息不能为空");
    }
    Orders orders = ordersMapper.selectByPrimaryKey(id);
    if (orders.getOrderStatus() == 1 || orders.getOrderStatus() == 7
        || orders.getOrderStatus() == 8) { // 只有订单状态是1 7 8才能删除

      List<OrdersPay> ordersPays = ordersPayMapper.queryListByOrderId(id);
      for (OrdersPay op : ordersPays) {
        ordersPayService.delOrdersPay(op.getId());
      }
      // ordersPayMapper.deleteByOrderId(id); 需要遍历删除，因为需要还原代金券的金额

      ordersRoyaltyMapper.deleteByOrderIdKind(id, null);

      List<OrdersProduct> productList = ordersProductMapper.queryListByOrderId(id);
      for (OrdersProduct op : productList) {
        productRepertoryService.retrunProduct(op.getId(), -1);
        OrdersProduct ordersProduct = ordersProductMapper.selectByPrimaryKey(op.getId());
        if (ordersProduct.getMealPayRecordId() != null) { // 删除客户套餐
          MealPayRecord mRecord =
              mealPayRecordMapper.selectByPrimaryKey(ordersProduct.getMealPayRecordId());
          // 异动客户套餐数量
          CustomMealDt cMealDt = new CustomMealDt();
          cMealDt.setId(mRecord.getCustMealDtId());
          cMealDt.setAddQuantity(mRecord.getQuantity());
          customMealDtMapper.updateByPrimaryKeySelective(cMealDt);
          // 删除套餐使用记录表
          mealPayRecordMapper.deleteByPrimaryKey(ordersProduct.getMealPayRecordId());
        }
      }

      Orders o = new Orders();
      o.setId(id);
      o.setOrderStatus(0); // 作废
      int count = ordersMapper.updateByPrimaryKeySelective(o);
      if (count > 0) {
        return RetInfo.Success("订单作废成功");
      } else {
        return RetInfo.Error("订单作废失败");
      }
    } else {
      return RetInfo.Error("已提交不可作废");
    }
  }

  @Override
  public RetInfo submitMeal(String chooseMealInfo, Integer customId, Integer orderId, Integer shopId)
      throws Exception {
    Orders dbOrders = ordersMapper.selectByPrimaryKey(orderId);
    List<OrdersProduct> ordersProducts = new ArrayList<OrdersProduct>();
    List<OrdersServe> ordersServes = new ArrayList<OrdersServe>();
    if (!StringUtils.isEmpty(chooseMealInfo) && customId != null) {
      String[] mealInfos = chooseMealInfo.split(",");
      // 校验
      Map<Integer, Integer> useMap = new HashMap<Integer, Integer>();
      for (String mealInfo : mealInfos) {
        String[] meal = mealInfo.split("#");
        Integer dtId = Integer.valueOf(meal[0]);
        Integer itemId = Integer.valueOf(meal[1]);
        Integer quantity = Integer.valueOf(meal[2]);

        CustomMealDt customMealDt = customMealDtMapper.selectCustMealById(dtId, customId);
        if (customMealDt != null) {
          if (customMealDt.getQuantity() < quantity) { // 校验可用数量
            return RetInfo.Error("客户可用套餐数量不足");
          }
        } else {
          return RetInfo.Error("客户套餐信息有误");
        }

        if (customMealDt.getType() == 1) {// 产品
          if (useMap.get(itemId) == null)
            useMap.put(itemId, 0);
          useMap.put(itemId, useMap.get(itemId) + quantity);
        }
      }

      for (Map.Entry<Integer, Integer> entry : useMap.entrySet()) {
        Product product = productMapper.selectByPrimaryKey(entry.getKey());
        if (product.getQuantity() < entry.getValue()) { // 校验产品数量
          return RetInfo.Error(product.getProductName() + "库存数量不足");
        }
      }

      for (String mealInfo : mealInfos) {
        String[] meal = mealInfo.split("#");
        Integer dtId = Integer.valueOf(meal[0]);
        Integer itemId = Integer.valueOf(meal[1]);
        Integer quantity = Integer.valueOf(meal[2]);
        CustomMealDt customMealDt = customMealDtMapper.selectCustMealById(dtId, customId);

        if (customMealDt != null) {
          if (customMealDt.getQuantity() >= quantity) { // 校验可用数量
            if (customMealDt.getType() == 1) { // 产品
              MealPayRecord mealPayRecord = new MealPayRecord();
              mealPayRecord.setCustId(customId);
              mealPayRecord.setCustMealDtId(dtId);
              mealPayRecord.setQuantity(quantity);
              mealPayRecord.setOrderId(orderId);
              mealPayRecord.setShopId(shopId);
              mealPayRecordMapper.insertSelective(mealPayRecord);
              // 更新客户套餐表里剩余数量
              CustomMealDt updateDt = new CustomMealDt();
              updateDt.setId(dtId);
              updateDt.setSubQuantity(quantity);
              customMealDtMapper.updateByPrimaryKeySelective(updateDt);

              Product product = productMapper.selectByPrimaryKey(itemId);
              // 创建订单细表
              OrdersProduct ordersProduct = new OrdersProduct();
              ordersProduct.setPrice(BigDecimal.ZERO);
              ordersProduct.setProductId(itemId);
              ordersProduct.setProductName(product.getProductName());
              ordersProduct.setShopId(product.getShopId());
              ordersProduct.setQuantity(quantity);
              ordersProduct.setProductRoyal(BigDecimal.ZERO);
              ordersProduct.setMealPayRecordId(mealPayRecord.getId());
              ordersProducts.add(ordersProduct);

              // 异动产品信息
            } else { // 2服务
              Serve serve = serveMapper.selectByPrimaryKey(itemId);
              // 创建订单细表 如果是服务，那么每项服务就是一个明细
              for (int i = 0; i < quantity; i++) {
                MealPayRecord mealPayRecord = new MealPayRecord();
                mealPayRecord.setCustId(customId);
                mealPayRecord.setCustMealDtId(dtId);
                mealPayRecord.setQuantity(1);
                mealPayRecord.setOrderId(orderId);
                mealPayRecord.setShopId(shopId);
                mealPayRecordMapper.insertSelective(mealPayRecord);
                // 更新客户套餐表里剩余数量
                CustomMealDt updateDt = new CustomMealDt();
                updateDt.setId(dtId);
                updateDt.setSubQuantity(1);
                customMealDtMapper.updateByPrimaryKeySelective(updateDt);

                OrdersServe ordersServe = new OrdersServe();
                ordersServe.setPrice(BigDecimal.ZERO);
                ordersServe.setServeId(itemId);
                ordersServe.setServeName(serve.getServeName());
                ordersServe.setShopId(serve.getShopId());
                ordersServe.setQuantity(1);
                if (dbOrders.getOrderStatus() == 4) {// 施工完成
                  ordersServe.setServeStatus(3);
                } else {
                  ordersServe.setServeStatus(1);
                }
                ordersServe.setServeRoyal(BigDecimal.ZERO);
                ordersServe.setSellRoyal(BigDecimal.ZERO);
                ordersServe.setMealPayRecordId(mealPayRecord.getId());
                ordersServes.add(ordersServe);
              }

            }
          }
        }
      }
    }
    Orders orders = new Orders();
    orders.setOrdersServeList(ordersServes);
    orders.setOrdersProductList(ordersProducts);
    RetInfo ret = new RetInfo(RetInfo.SUCCESS, orders);
    return ret;
  }

  // pageType1：历史订单 2消费记录
  @Override
  public GrdData getWeChatOrdersList(Orders orders, Pager pager, Integer pageType) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Orders> list = ordersMapper.getOrdersList(orders);
    Iterator<Orders> orderIterator = list.iterator();
    while (orderIterator.hasNext()) {
      Orders od = orderIterator.next();
      od.setOrderStatusName(OrdersEnum.getOrdersName(od.getOrderStatus()));
      if (orders.getOrderStatus() == 456) {
        od.setOrdersPayList(ordersPayService.queryListByOrderId(od.getId()));
        if (pageType == 2) {
          if (ObjectUtils.isEmpty(od.getOrdersPayList())) { // 消费记录列表，如果没有支付信息，那么就不现实
            orderIterator.remove();
          }
        }
      } else {
        od.setOrdersPayList(new ArrayList<OrdersPay>());
      }
      od.setOrdersProductList(ordersProductService.queryListByOrderId(od.getId()));
      od.setOrdersServeList(ordersServeService.queryListByOrderId(od.getId()));



    }
    return new GrdData(Long.valueOf(list.size()), list);
  }

  @Override
  public List<Orders> queryOrders(Orders orders, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Orders> list = ordersMapper.selectOrdersList(orders);

    for (Orders od : list) {
      od.setOrderStatusName(OrdersEnum.getOrdersName(od.getOrderStatus()));
      od.setOrdersProductList(ordersProductService.queryListByOrderId(od.getId()));
      od.setOrdersServeList(ordersServeService.queryListByOrderId(od.getId()));
    }

    return list;
  }


  @Override
  public Orders queryLastOrdersByCarNumber(String carNumber) {
    return ordersMapper.selectByCarNumber(carNumber);
  }


  @Override
  public RetInfo saveEvaluate(OrdersEvaluate ordersEvaluate) {
    String msg = checkEvaluate(ordersEvaluate);
    if (StringUtils.isNotBlank(msg)) {
      return RetInfo.Error(msg);
    }
    ordersEvaluate.setAddTime(new Date());
    Orders orders = new Orders();
    orders.setId(ordersEvaluate.getOrderId());
    orders.setEvaluateTime(ordersEvaluate.getAddTime());
    ordersMapper.updateByPrimaryKeySelective(orders);

    return new RetInfo(ordersEvaluateMapper.insertSelective(ordersEvaluate) > 0, "新增评价");
  }

  private String checkEvaluate(OrdersEvaluate ordersEvaluate) {
    if (ordersEvaluate.getCustId() == null) {
      return "客户编号不能为空";
    } else if (ordersEvaluate.getOrderId() == null) {
      return "订单编号不能为空";
    } else if (ordersEvaluate.getStars() == null) {
      return "评价等级不能为空";
    }
    return "";
  }

  @Override
  public ProfitVo getOrderRevenue(Date dateRangeStartTime, Date dateRangeEndTime) {
    PayRecord record = new PayRecord();
    record.setDateRangeStartTime(dateRangeStartTime);
    record.setDateRangeEndTime(dateRangeEndTime);
    record.setInKind(3); // 3:(1,3,4)
    List<PayRecord> payRecords = payRecordMapper.getList(record);
    BigDecimal amt = BigDecimal.ZERO;
    for (PayRecord r : payRecords) {
      amt = amt.add(r.getAmount());
    }

    ProfitVo profitVo = new ProfitVo("订单", "营收");
    profitVo.setAmount(amt);
    return profitVo;
  }

  @Override
  public ProfitVo getOrderCost(Date dateRangeStartTime, Date dateRangeEndTime) {
    ProductConsume productConsume = new ProductConsume();
    productConsume.setDateRangeStartTime(dateRangeStartTime);
    productConsume.setDateRangeEndTime(dateRangeEndTime);
    productConsume.setKind(1); // 订单消耗
    List<ProductConsume> productConsumes = productConsumeMapper.getPriceList(productConsume);
    BigDecimal amt = BigDecimal.ZERO;
    for (ProductConsume pc : productConsumes) {
      amt = amt.add(pc.getPrice().multiply(new BigDecimal(pc.getQuantity())));
    }

    ProfitVo profitVo = new ProfitVo("订单", "成本");
    profitVo.setAmount(amt);
    return profitVo;
  }

  @Override
  public List<ProfitVo> getProFitList(Date dateRangeStartTime, Date dateRangeEndTime) {
    List<ProfitVo> list = new ArrayList<ProfitVo>();
    // 订单利润
    ProfitVo revenue = getOrderRevenue(dateRangeStartTime, dateRangeEndTime);
    ProfitVo cost = getOrderCost(dateRangeStartTime, dateRangeEndTime);
    ProfitVo profit = new ProfitVo("订单", "毛利"); // 毛利
    profit.setAmount(revenue.getAmount().subtract(cost.getAmount()));

    list.add(revenue);
    list.add(cost);
    list.add(profit);

    // 日常支出
    DailyPayRecord dailyPayRecord = new DailyPayRecord();
    dailyPayRecord.setDateRangeStartPeriod(dateRangeStartTime);
    dailyPayRecord.setDateRangeEndPeriod(dateRangeEndTime);
    List<DailyPayRecord> dailyPayRecords = dailyPayRecordMapper.getList(dailyPayRecord);
    BigDecimal amt1 = BigDecimal.ZERO;
    BigDecimal amt2 = BigDecimal.ZERO;
    BigDecimal amt3 = BigDecimal.ZERO;
    for (DailyPayRecord record : dailyPayRecords) {
      if (record.getKind() == 1) { // 收入
        amt1 = amt1.add(record.getAmount());
      } else {
        if (DateUtils.isSameDay(record.getStartSharePeriod(), record.getEndSharePeriod())) {
          amt2 = amt2.add(record.getAmount());// 一次性支出
        } else {
          amt3 = amt3.add(record.getAmount()); // 分摊支出
        }
      }
    }

    ProfitVo p1 = new ProfitVo("日常收支", "收入"); // 收入
    p1.setAmount(amt1);
    ProfitVo p2 = new ProfitVo("日常收支", "一次性支出"); // 一次性支出
    p2.setAmount(amt2);
    ProfitVo p3 = new ProfitVo("日常收支", "分摊支出"); // 分摊支出
    p3.setAmount(amt3);

    list.add(p1);
    list.add(p2);
    list.add(p3);

    // 采购退货
    ProfitVo back = repertoryService.getOrderReturn(dateRangeStartTime, dateRangeEndTime);
    list.add(back);

    BigDecimal pureProfit = BigDecimal.ZERO;
    pureProfit =
        profit.getAmount().add(p1.getAmount()).subtract(p2.getAmount()).subtract(p3.getAmount())
            .add(back.getAmount());

    // 净利润
    ProfitVo pureFit = new ProfitVo("净利润", "净利润");
    pureFit.setAmount(pureProfit);

    list.add(pureFit);

    return list;
  }

  @Override
  public BigDecimal getFinishOrderAmt(Integer shopId, Date dateRangeStartTime, Date dateRangeEndTime) {
    if (dateRangeStartTime == null || dateRangeEndTime == null)
      return BigDecimal.ZERO;
    BigDecimal amt = ordersMapper.getFinishOrderAmt(shopId, dateRangeStartTime, dateRangeEndTime);
    return amt == null ? BigDecimal.ZERO : amt;
  }

  @Override
  public RetInfo submitMeal(String chooseMealInfo, Integer customId, Integer orderId,
      Integer shopId, Integer dtId, Integer itemType) throws Exception {
    RetInfo retInfo = submitMeal(chooseMealInfo, customId, orderId, shopId);
    if (StringUtils.isNotBlank(chooseMealInfo) && dtId != null && itemType != null) {
      if (retInfo.getRetCode().equals(RetInfo.SUCCESS)) {
        if (itemType == 1) {
          ordersProductService.delOrdersProduct(dtId);
        } else {
          ordersServeService.delOrdersServe(dtId);
        }
      }
    }
    return retInfo;
  }

  @Override
  public Orders queryBoxOrderDetail(Integer ordersId) {
    Orders orders = ordersMapper.selectByPrimaryKey(ordersId);
    orders.setOrdersServeList(ordersServeService.queryListByOrderId(orders.getId()));

    return orders;
  }

  @Override
  public void submitOrder(Orders orders) {
    Orders oldOrders = ordersMapper.selectByPrimaryKey(orders.getId());
    // 盒子入账
    Integer operateType = 5;
    BigDecimal orderAmt = BigDecimal.ZERO;
    List<OrdersServe> ordersServeList = ordersServeService.queryListByOrderId(orders.getId());
    if (operateType == 3 || operateType == 4) {
      if (!ObjectUtils.isEmpty(ordersServeList)) {
        for (OrdersServe os : ordersServeList) {
          if (operateType == 4) {
            orderAmt = orderAmt.add(os.getPrice());
          }
          if (operateType == 3) { // 3施工中
            os.setServeStatus(2); // 施工中
            ordersServeMapper.updateByPrimaryKeySelective(os);
          } else if (operateType == 4) { // 4施工完成
            os.setServeStatus(3); // 施工完成
            os.setDoneTime(new Date());
            ordersServeMapper.updateByPrimaryKeySelective(os);
          }
        }
      }
    }
    orders.setOrderStatus(4);
    orders.setDoneTime(new Date());

    int count = ordersMapper.updateByPrimaryKeySelective(orders);

    if (operateType == 5 || operateType == 6) {// 5入账 6挂账
      BigDecimal newPayAmt = new BigDecimal("0"); // 本次新还的金额
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      BigDecimal payedAmt = new BigDecimal("0");
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        for (OrdersPay op : ordersPayList) {
          if (op.getCanEdit() == 1) { // 可编辑，说明是新增的
            PayRecord payRecord = new PayRecord();
            payRecord.setTypeId(op.getPayId());
            payRecord.setAmount(op.getPayAmount());
            payRecord.setRemark(op.getRemark());
            if (oldOrders.getOrderStatus() == 5 || oldOrders.getOrderStatus() == 6) { // 挂账，记账的时候还款
              payRecord.setKind(4);// 4客户挂账还款（收入）
            } else {
              payRecord.setKind(1); // 1客户订单支付
            }
            payRecord.setSourceId(op.getOrderId());
            payRecord.setShopId(op.getShopId());
            payRecordMapper.insertSelective(payRecord);
            op.setCanEdit(0); // 结算后不能编辑
            ordersPayMapper.updateByPrimaryKeySelective(op);
            newPayAmt = newPayAmt.add(op.getPayAmount());
          }
          payedAmt = payedAmt.add(op.getPayAmount());
        }
      }

      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord == null) { // 之前没有挂账记录
        if (oldOrders.getPayAmount().compareTo(payedAmt) == 1) { // 订单支付金额
          // >实际支付金额
          // 生成挂账表
          debtRecord = new DebtRecord();
          debtRecord.setKind(1); // 1客户订单挂账
          debtRecord.setSourceId(orders.getId());
          debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setReturnAmount(BigDecimal.ZERO);
          debtRecord.setLeftAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setEnable(1);
          debtRecordMapper.insertSelective(debtRecord);
        }
      } else {
        debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));// 剩余欠款
        debtRecord.setReturnAmount(debtRecord.getReturnAmount().add(newPayAmt)); // 当前还款+本次还款
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }
    }

    if (operateType == 7) { // 7解除入账/解除挂账
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        for (OrdersPay op : ordersPayList) {
          PayRecord payRecord = new PayRecord();
          payRecord.setTypeId(op.getPayId());
          payRecord.setAmount(op.getPayAmount());
          payRecord.setRemark(op.getRemark());
          payRecord.setKind(8); // 8订单反入账或反挂账（支出）
          payRecord.setSourceId(op.getOrderId());
          payRecord.setShopId(op.getShopId());
          payRecordMapper.insertSelective(payRecord);
          op.setCanEdit(1); // 解除挂账/入账后 可以编辑了
          ordersPayMapper.updateByPrimaryKeySelective(op);
        }
      }
      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord != null) {
        debtRecord.setEnable(0);
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }


    }
  }

}
