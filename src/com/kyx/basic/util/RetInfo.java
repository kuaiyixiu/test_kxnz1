package com.kyx.basic.util;

import java.io.Serializable;

/**
 * 返回信息
 * 
 * @author 严大灯
 * @data 2019-3-19 上午9:16:58
 * @Descripton
 */
public class RetInfo implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public static final String ERROR = "error";
  public static final String SUCCESS = "success";

  public static final String errorMes = "失败";
  public static final String successMes = "成功";
  private String retCode;// 返回码
  private String retMsg;// 返回信息
  private String retDesc;// 返回明细
  private Object retData;// 返回的对象
  private Integer retCount;// 返回明细

  private boolean success;

  public RetInfo(String retCode, Object retData) {
    super();
    this.retCode = retCode;
    this.retData = retData;
  }

  public RetInfo(String retCode, String retMsg) {
    this.retCode = retCode;
    this.retMsg = retMsg;
  }

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetMsg() {
    return retMsg;
  }

  public void setRetMsg(String retMsg) {
    this.retMsg = retMsg;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  public static RetInfo Error(String message) {
    return new RetInfo(ERROR, message);
  }

  public static RetInfo Success(String message) {
    return new RetInfo(SUCCESS, message);
  }

  public Object getRetData() {
    return retData;
  }

  public void setRetData(Object retData) {
    this.retData = retData;
  }

  public RetInfo(boolean bool, String message) {
    String msg = bool ? successMes : errorMes;
    this.retMsg = message + msg;

    String boolStr = bool ? SUCCESS : ERROR;
    this.retCode = boolStr;
    this.success = bool;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public RetInfo() {
    super();
  }

  public Integer getRetCount() {
    return retCount;
  }

  public void setRetCount(Integer retCount) {
    this.retCount = retCount;
  }

  public static RetInfo SuccessData(String message, Object obj) {
    return new RetInfo(SUCCESS, obj);
  }

  public RetInfo(boolean bool, String message, Object retData) {
    String msg = bool ? successMes : errorMes;
    this.retMsg = message + msg;

    String boolStr = bool ? SUCCESS : ERROR;
    this.retCode = boolStr;
    this.success = bool;
    this.retData = retData;
  }

}
