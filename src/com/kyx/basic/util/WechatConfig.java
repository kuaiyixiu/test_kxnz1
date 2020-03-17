package com.kyx.basic.util;

public class WechatConfig {

  // 公众号用于调用微信JS接口的临时票据
  private String jsapiTicket;

  // 生成签名的随机串
  private String noncestr;

  // 生成签名的时间戳
  private String timestamp;

  private String url;

  // 签名
  private String signature;

  // 微信appid
  private String appId;

  private String accessToken;


  public String getJsapiTicket() {
    return jsapiTicket;
  }

  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }

  public String getNoncestr() {
    return noncestr;
  }

  public void setNoncestr(String noncestr) {
    this.noncestr = noncestr;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public WechatConfig(String jsapiTicket, String noncestr, String timestamp, String url,
      String signature, String appId) {
    super();
    this.jsapiTicket = jsapiTicket;
    this.noncestr = noncestr;
    this.timestamp = timestamp;
    this.url = url;
    this.signature = signature;
    this.appId = appId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


}
