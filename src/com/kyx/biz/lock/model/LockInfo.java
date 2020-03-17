package com.kyx.biz.lock.model;

import java.io.Serializable;

public class LockInfo implements Serializable {
  private Integer id;

  private Integer lockNumber;

  private Integer boxId;

  private Integer status;

  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getLockNumber() {
    return lockNumber;
  }

  public void setLockNumber(Integer lockNumber) {
    this.lockNumber = lockNumber;
  }

  public Integer getBoxId() {
    return boxId;
  }

  public void setBoxId(Integer boxId) {
    this.boxId = boxId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  // 锁中文名
  private String lockName;

  // 箱子中文名
  private String boxName;

  // 状态中文名
  private String statusName;

  public String getLockName() {
    return lockName;
  }

  public void setLockName(String lockName) {
    this.lockName = lockName;
  }

  public String getBoxName() {
    return boxName;
  }

  public void setBoxName(String boxName) {
    this.boxName = boxName;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }


}
