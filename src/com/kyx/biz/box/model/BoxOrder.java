package com.kyx.biz.box.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.kyx.basic.user.model.User;

public class BoxOrder implements Serializable {
  private Integer id;

  private Integer customId;

  private Integer orderId;

  private Integer serviceId;

  private String customImgName;

  private String customRemark;

  private String workerAddressImgUrl;

  private String workerImgUrl;

  private String workerRemark;

  private Integer workerId;

  private String customPhone;

  private String carNumber;

  private String carBrand;

  private Date createTime;

  private Date acceptTime;

  private Date workEndTime;

  private Date endTime;

  private Integer lockId;

  private Integer status;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCustomId() {
    return customId;
  }

  public void setCustomId(Integer customId) {
    this.customId = customId;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public String getCustomImgName() {
    return customImgName;
  }

  public void setCustomImgName(String customImgName) {
    this.customImgName = customImgName == null ? null : customImgName.trim();
  }

  public String getCustomRemark() {
    return customRemark;
  }

  public void setCustomRemark(String customRemark) {
    this.customRemark = customRemark == null ? null : customRemark.trim();
  }

  public String getWorkerAddressImgUrl() {
    return workerAddressImgUrl;
  }

  public void setWorkerAddressImgUrl(String workerAddressImgUrl) {
    this.workerAddressImgUrl = workerAddressImgUrl == null ? null : workerAddressImgUrl.trim();
  }

  public String getWorkerImgUrl() {
    return workerImgUrl;
  }

  public void setWorkerImgUrl(String workerImgUrl) {
    this.workerImgUrl = workerImgUrl == null ? null : workerImgUrl.trim();
  }

  public String getWorkerRemark() {
    return workerRemark;
  }

  public void setWorkerRemark(String workerRemark) {
    this.workerRemark = workerRemark == null ? null : workerRemark.trim();
  }

  public Integer getWorkerId() {
    return workerId;
  }

  public void setWorkerId(Integer workerId) {
    this.workerId = workerId;
  }

  public String getCustomPhone() {
    return customPhone;
  }

  public void setCustomPhone(String customPhone) {
    this.customPhone = customPhone == null ? null : customPhone.trim();
  }

  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber == null ? null : carNumber.trim();
  }

  public String getCarBrand() {
    return carBrand;
  }

  public void setCarBrand(String carBrand) {
    this.carBrand = carBrand == null ? null : carBrand.trim();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getAcceptTime() {
    return acceptTime;
  }

  public void setAcceptTime(Date acceptTime) {
    this.acceptTime = acceptTime;
  }

  public Date getWorkEndTime() {
    return workEndTime;
  }

  public void setWorkEndTime(Date workEndTime) {
    this.workEndTime = workEndTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Integer getLockId() {
    return lockId;
  }

  public void setLockId(Integer lockId) {
    this.lockId = lockId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  private String customName;

  private String serviceName;

  private BigDecimal price;

  private User workerUser;

  // 状态名
  private String statusName;

  // 创建时间
  private String createTimeStr;

  public String getCustomName() {
    return customName;
  }

  public void setCustomName(String customName) {
    this.customName = customName;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public User getWorkerUser() {
    return workerUser;
  }

  public void setWorkerUser(User workerUser) {
    this.workerUser = workerUser;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getCreateTimeStr() {
    return createTimeStr;
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  private String lockName;

  public String getLockName() {
    return lockName;
  }

  public void setLockName(String lockName) {
    this.lockName = lockName;
  }

  private Integer lockNumber;

  public Integer getLockNumber() {
    return lockNumber;
  }

  public void setLockNumber(Integer lockNumber) {
    this.lockNumber = lockNumber;
  }

}
