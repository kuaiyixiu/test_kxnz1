package com.kyx.basic.shops.model;

import java.io.Serializable;
import java.util.Date;

public class Shops implements Serializable {
  private Integer id;

  private String shopName;

  private String shopAddress;

  private String shopTel;

  private String accountStatus;

  private String shopPhone;

  private String faTitle;

  private String fax;

  private Date accountEnd;

  private Integer bossId;

  private String jdbcDriverclassname;

  private String jdbcUrl;

  private String jdbcUsername;

  private String jdbcPassword;

  private String shopKey;

  private String dbName;

  private Integer userCount;

  private Integer wechatId;

  private Integer creatUser;

  private Integer managerUser;

  private Integer smsAmount;

  private Integer openNightServeFlag;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName == null ? null : shopName.trim();
  }

  public String getShopAddress() {
    return shopAddress;
  }

  public void setShopAddress(String shopAddress) {
    this.shopAddress = shopAddress == null ? null : shopAddress.trim();
  }

  public String getShopTel() {
    return shopTel;
  }

  public void setShopTel(String shopTel) {
    this.shopTel = shopTel == null ? null : shopTel.trim();
  }

  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus == null ? null : accountStatus.trim();
  }

  public String getShopPhone() {
    return shopPhone;
  }

  public void setShopPhone(String shopPhone) {
    this.shopPhone = shopPhone == null ? null : shopPhone.trim();
  }

  public String getFaTitle() {
    return faTitle;
  }

  public void setFaTitle(String faTitle) {
    this.faTitle = faTitle == null ? null : faTitle.trim();
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax == null ? null : fax.trim();
  }

  public Date getAccountEnd() {
    return accountEnd;
  }

  public void setAccountEnd(Date accountEnd) {
    this.accountEnd = accountEnd;
  }

  public Integer getBossId() {
    return bossId;
  }

  public void setBossId(Integer bossId) {
    this.bossId = bossId;
  }

  public String getJdbcDriverclassname() {
    return jdbcDriverclassname;
  }

  public void setJdbcDriverclassname(String jdbcDriverclassname) {
    this.jdbcDriverclassname = jdbcDriverclassname == null ? null : jdbcDriverclassname.trim();
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl == null ? null : jdbcUrl.trim();
  }

  public String getJdbcUsername() {
    return jdbcUsername;
  }

  public void setJdbcUsername(String jdbcUsername) {
    this.jdbcUsername = jdbcUsername == null ? null : jdbcUsername.trim();
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public void setJdbcPassword(String jdbcPassword) {
    this.jdbcPassword = jdbcPassword == null ? null : jdbcPassword.trim();
  }

  public String getShopKey() {
    return shopKey;
  }

  public void setShopKey(String shopKey) {
    this.shopKey = shopKey == null ? null : shopKey.trim();
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName == null ? null : dbName.trim();
  }

  public Integer getUserCount() {
    return userCount;
  }

  public void setUserCount(Integer userCount) {
    this.userCount = userCount;
  }

  public Integer getWechatId() {
    return wechatId;
  }

  public void setWechatId(Integer wechatId) {
    this.wechatId = wechatId;
  }

  public Integer getCreatUser() {
    return creatUser;
  }

  public void setCreatUser(Integer creatUser) {
    this.creatUser = creatUser;
  }

  public Integer getManagerUser() {
    return managerUser;
  }

  public void setManagerUser(Integer managerUser) {
    this.managerUser = managerUser;
  }

  public Integer getSmsAmount() {
    return smsAmount;
  }

  public void setSmsAmount(Integer smsAmount) {
    this.smsAmount = smsAmount;
  }

  public Integer getOpenNightServeFlag() {
    return openNightServeFlag;
  }

  public void setOpenNightServeFlag(Integer openNightServeFlag) {
    this.openNightServeFlag = openNightServeFlag;
  }

  // 缓存门店列表session
  public static final String SHOP_LIST_SESSION = "shopListSession";

  // 当前门店session
  public static final String SHOPS_SESSION = "shopsSession";


  private Integer addQuantity;

  // 用户名
  private String userRealname;

  // 用户手机号
  private String userPhone;

  // 盒子的唯一标识码
  private String identifier;

  // 盒子的备注
  private String boxRemark;

  public Integer getAddQuantity() {
    return addQuantity;
  }

  public void setAddQuantity(Integer addQuantity) {
    this.addQuantity = addQuantity;
  }

  public String getUserRealname() {
    return userRealname;
  }

  public void setUserRealname(String userRealname) {
    this.userRealname = userRealname;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getBoxRemark() {
    return boxRemark;
  }

  public void setBoxRemark(String boxRemark) {
    this.boxRemark = boxRemark;
  }


}
