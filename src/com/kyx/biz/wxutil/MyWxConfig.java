package com.kyx.biz.wxutil;

import java.io.InputStream;

public class MyWxConfig extends WXPayConfig {

  public String getAppID() {

    return "wx3c957523d27e8cb0";
  }

  @Override
  public String getMchID() {

    return "1377594202";
  }

  @Override
  public String getKey() {

    return "7Ntl7m2GIKeLtBDkTa9Q4EK83qX46SGY";
  }


  public String getAppsecret() {

    return "1aaf204872fc276bfedf9c655150a9b5";
  }


  public String getToken() {

    return "LIANTU";
  }

  public String getEncodingaeskey() {

    return "pYDx1Th7wx7S8JSTm0Um2kkOuf1DnmJ8uWJxCpmg4A8";
  }

  @Override
  public InputStream getCertStream() {

    return null;
  }

  @Override
  public IWXPayDomain getWXPayDomain() {

    return null;
  }

  }
