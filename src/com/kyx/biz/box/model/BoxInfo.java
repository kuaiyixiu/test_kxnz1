package com.kyx.biz.box.model;

import java.io.Serializable;

public class BoxInfo implements Serializable {
  private Integer id;

  private String boxName;

  private String identifier;

  private String remark;

  private Integer address;

  private Integer shopId;

  private Integer enabled;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBoxName() {
    return boxName;
  }

  public void setBoxName(String boxName) {
    this.boxName = boxName == null ? null : boxName.trim();
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier == null ? null : identifier.trim();
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark == null ? null : remark.trim();
  }

  public Integer getAddress() {
    return address;
  }

  public void setAddress(Integer address) {
    this.address = address;
  }

  public Integer getShopId() {
    return shopId;
  }

  public void setShopId(Integer shopId) {
    this.shopId = shopId;
  }

  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer enabled) {
    this.enabled = enabled;
  }

  // boxinfo的list
  private String boxInfoJsonList;

  public String getBoxInfoJsonList() {
    return boxInfoJsonList;
  }

  public void setBoxInfoJsonList(String boxInfoJsonList) {
    this.boxInfoJsonList = boxInfoJsonList;
  }

}
