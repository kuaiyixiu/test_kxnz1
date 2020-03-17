package com.kyx.biz.box.model;

/**
 * 订单枚举
 * 
 * @author daibin
 * @date 2019-4-16 上午10:00:45
 * 
 */
public enum BoxOrderEnum {

  WAIT_ORDER("待接单", 0), CONDUCT_ORDER("施工中", 1), FINISH_ORDER("施工完成", 2), END_ORDER("订单完结", 3), REFUSE_ORDER(
      "师傅取消订单", -1), CUSTOM_REFUSE_ORDER("客户取消订单", -2);

  private String name;
  private int code;

  private BoxOrderEnum(String name, int code) {
    this.name = name;
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public static String getOrdersName(int code) {
    for (BoxOrderEnum order : BoxOrderEnum.values()) {
      if (order.getCode() == code) {
        return order.name;
      }
    }

    return "";
  }
}
