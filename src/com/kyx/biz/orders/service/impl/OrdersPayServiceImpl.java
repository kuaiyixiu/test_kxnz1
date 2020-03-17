package com.kyx.biz.orders.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustCouponMapper;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersPayMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersPay;
import com.kyx.biz.orders.service.OrdersPayService;
import com.kyx.biz.paytype.mapper.PayTypeMapper;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.wechatpublic.mapper.WechatCardRecordMapper;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;

@Service("ordersPayService")
public class OrdersPayServiceImpl implements OrdersPayService {

  @Resource
  private OrdersPayMapper ordersPayMapper;

  @Resource
  private PayTypeMapper payTypeMapper;

  @Resource
  private CustomMapper customMapper;

  @Resource
  private CustCouponMapper custCouponMapper;

  @Resource
  private OrdersMapper ordersMapper;

  @Resource
  private WechatCardRecordMapper wechatCardRecordMapper;



  @Override
  public RetInfo saveOrdersPayList(List<OrdersPay> ordersPays, Integer orderId) {
    BigDecimal balance = new BigDecimal("0");
    if (ordersPays != null && ordersPays.size() > 0) {
      for (OrdersPay ordersPay : ordersPays) {
        if (ordersPay.getPayId() == BasicContant.payTypeContant.BALANCE) {// 是余额就要校验余额
          balance = balance.add(ordersPay.getPayAmount());
        }
      }
    }

    if (balance.compareTo(BigDecimal.ZERO) == 1) {// 是余额就要校验余额
      Orders orders = ordersMapper.selectByPrimaryKey(orderId);
      if (orders.getCustId() == null) {
        return RetInfo.Error("非会员不可使用会员支付，请先绑定会员");
      }

      Custom custom = customMapper.selectByPrimaryKey(orders.getCustId());
      if (custom.getBalance().compareTo(balance) < 0) {
        return RetInfo.Error("会员卡余额不足");
      }


      if (balance.compareTo(BigDecimal.ZERO) == 1) { // 是余额
        if (orders.getCustId() != null) {
          Custom c = new Custom();
          c.setId(orders.getCustId());
          c.setSubBalance(balance);
          customMapper.updateByPrimaryKeySelective(c);
        }
      }
    }



    StringBuffer sb = new StringBuffer("");
    if (ordersPays != null && ordersPays.size() > 0) {
      for (OrdersPay ordersPay : ordersPays) {
        ordersPay.setOrderId(orderId);
        ordersPayMapper.insertSelective(ordersPay);
        sb.append(ordersPay.getId());
        sb.append(",");
      }
    }
    return RetInfo.Success(sb.toString());
  }

  @Override
  public OrdersPay queryById(HttpServletRequest request, Integer id, Integer shopId) {
    OrdersPay ordersPay = ordersPayMapper.selectByPrimaryKey(id);
    PayType payType = payTypeMapper.selectByPrimaryKey(ordersPay.getPayId());
    if (payType != null) {
      ordersPay.setPayName(payType.getPayName());
      if (ordersPay.getPayId().equals(BasicContant.payTypeContant.COUPON)) {
        String couponName = custCouponMapper.selectCouponNameById(ordersPay.getSourceId());
        ordersPay.setPayName(ordersPay.getPayName() + "-" + couponName);
      } else if (ordersPay.getPayId().equals(BasicContant.payTypeContant.CARDSET)) {
        WechatCardRecord cardRecord =
            wechatCardRecordMapper.selectByPrimaryKey(ordersPay.getSourceId());
        ordersPay.setPayName(ordersPay.getPayName() + "-" + cardRecord.getWechatCardName());
      }


    }
    return ordersPay;
  }

  @Override
  public List<OrdersPay> queryListByOrderId(Integer orderId) {
    List<OrdersPay> ordersPays = ordersPayMapper.queryListByOrderId(orderId);
    for (OrdersPay op : ordersPays) {
      if (op.getPayId() == BasicContant.payTypeContant.COUPON) {// 优惠券
        String couponName = custCouponMapper.selectCouponNameById(op.getSourceId());
        op.setPayName("代金券-" + couponName);
      } else if (op.getPayId().equals(BasicContant.payTypeContant.CARDSET)) {
        WechatCardRecord cardRecord = wechatCardRecordMapper.selectByPrimaryKey(op.getSourceId());
        op.setPayName("优惠券-" + cardRecord.getWechatCardName());
      }
    }
    return ordersPays;
  }

  @Override
  public RetInfo delOrdersPay(Integer ordersPayId) {
    if (ordersPayId == null) {
      return RetInfo.Error("订单支付主键为空");
    }
    OrdersPay op = ordersPayMapper.selectByPrimaryKey(ordersPayId);
    Orders orders = ordersMapper.selectByPrimaryKey(op.getOrderId());
    if (op.getPayId().equals(BasicContant.payTypeContant.COUPON)) { // 是代金券，要返还代金券金额
      CustCoupon updateCoupon = new CustCoupon();
      updateCoupon.setId(op.getSourceId());
      updateCoupon.setAddAvailAmount(op.getPayAmount());
      updateCoupon.setState(1);
      custCouponMapper.updateByPrimaryKeySelective(updateCoupon);
    } else if (op.getPayId().equals(BasicContant.payTypeContant.BALANCE)) { // 是余额，就要返还给用户
      if (orders.getCustId() != null) {
        Custom custom = new Custom();
        custom.setId(orders.getCustId());
        custom.setAddBalance(op.getPayAmount());
        customMapper.updateByPrimaryKeySelective(custom);
      }
    } else if (op.getPayId().equals(BasicContant.payTypeContant.CARDSET)) { // 是优惠券，就要修改优惠券状态
      wechatCardRecordMapper.unlockRecord(op.getSourceId());
    }
    ordersPayMapper.deleteByPrimaryKey(ordersPayId);
    if (orders.getCustId() != null) {
      int couponCount = custCouponMapper.selectCountByCustId(orders.getCustId());
      int cardRecordCount = wechatCardRecordMapper.selectCountByCustId(orders.getCustId());
      JSONObject jo = new JSONObject();
      jo.put("couponCount", couponCount);
      jo.put("cardRecordCount", cardRecordCount);
      return RetInfo.Success(jo.toString());
    } else {
      JSONObject jo = new JSONObject();
      jo.put("couponCount", 0);
      jo.put("cardRecordCount", 0);
      return RetInfo.Success(jo.toJSONString());
    }

    // return new RetInfo( ordersPayMapper.deleteByPrimaryKey(ordersPayId) > 0,"删除订单支付");
  }

  @Override
  public RetInfo updateOrdersPay(OrdersPay ordersPay) throws Exception {
    if (ordersPay.getId() == null) {
      return RetInfo.Error("订单支付主键为空");
    }
    OrdersPay oldPay = ordersPayMapper.selectByPrimaryKey(ordersPay.getId());
    if (ordersPay.getPayId().equals(BasicContant.payTypeContant.BALANCE)) {// 余额
      Orders orders = ordersMapper.selectByPrimaryKey(oldPay.getOrderId());
      if (orders.getCustId() == null) {
        return RetInfo.Error("非会员不可使用会员支付，请先绑定会员");
      }
      Custom custom = customMapper.selectByPrimaryKey(orders.getCustId());


      BigDecimal balance = ordersPay.getPayAmount().subtract(oldPay.getPayAmount());
      if (balance.compareTo(BigDecimal.ZERO) == 1) { // 更新的余额大于上次
        if (custom.getBalance().compareTo(balance) < 0) {
          return RetInfo.Error("会员卡余额不足");
        }
      }
      Custom c = new Custom();
      c.setId(orders.getCustId());
      c.setSubBalance(balance);
      customMapper.updateByPrimaryKeySelective(c); // 异动余额
    }


    return new RetInfo(ordersPayMapper.updateByPrimaryKeySelective(ordersPay) > 0, "修改订单支付");
  }

  @Override
  public BigDecimal getOrderPayAmt(Integer orderId) {
    BigDecimal amt = ordersPayMapper.getOrderPayAmt(orderId);
    return amt == null ? BigDecimal.ZERO : amt;
  }

  @Override
  public void saveOrdersPay(Integer orderId, OrdersPay ordersPay) {
    BigDecimal balance = new BigDecimal("0");

    if (ordersPay.getPayId() == BasicContant.payTypeContant.BALANCE) {// 是余额就要校验余额
      balance = balance.add(ordersPay.getPayAmount());
    }

    if (balance.compareTo(BigDecimal.ZERO) == 1) {// 是余额就要校验余额
      Orders orders = ordersMapper.selectByPrimaryKey(orderId);
      Custom custom = customMapper.selectByPrimaryKey(orders.getCustId());

      if (balance.compareTo(BigDecimal.ZERO) == 1) { // 是余额
        if (orders.getCustId() != null) {
          Custom c = new Custom();
          c.setId(orders.getCustId());
          c.setSubBalance(balance);
          customMapper.updateByPrimaryKeySelective(c);
        }
      }
    }


    ordersPay.setOrderId(orderId);
    ordersPayMapper.insertSelective(ordersPay);
  }

}
