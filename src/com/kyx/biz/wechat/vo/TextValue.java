package com.kyx.biz.wechat.vo;

public class TextValue {

  private String text;

  private String value;

  private String price;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public TextValue(String text, String value, String price) {
    super();
    this.text = text;
    this.value = value;
    this.price = price;
  }


}
