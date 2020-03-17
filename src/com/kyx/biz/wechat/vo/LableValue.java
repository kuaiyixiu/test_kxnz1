package com.kyx.biz.wechat.vo;

public class LableValue {
  private Integer lable;

  private String value;

  public Integer getLable() {
    return lable;
  }

  public void setLable(Integer lable) {
    this.lable = lable;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public LableValue(Integer lable, String value) {
    super();
    this.lable = lable;
    this.value = value;
  }


}
