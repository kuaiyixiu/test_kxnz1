package com.kyx.basic.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;

public class WechatConfigUtils {

  public static String getAccessToken() {
    String access_token = "";
    String grant_type = "client_credential";// 获取access_token填写client_credential
    String AppId = BasicContant.WECHAT_APPID;// 第三方用户唯一凭证
    String secret = BasicContant.WECHAT_APPSECRET;// 第三方用户唯一凭证密钥，即appsecret
    // 这个url链接地址和参数皆不能变
    String url =
        "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + AppId
            + "&secret=" + secret;

    try {
      URL urlGet = new URL(url);
      HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
      http.setRequestMethod("GET"); // 必须是get方式请求
      http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      http.setDoOutput(true);
      http.setDoInput(true);
      System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
      System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
      http.connect();
      InputStream is = http.getInputStream();
      int size = is.available();
      byte[] jsonBytes = new byte[size];
      is.read(jsonBytes);
      String message = new String(jsonBytes, "UTF-8");
      JSONObject json = JSONObject.parseObject(message);
      System.out.println("getAccessToken json:" + json);
      access_token = json.getString("access_token");
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }



    return access_token;
  }

  public static void saveAccessToken(String value) {
    Preferences preferences = Preferences.userRoot();
    preferences.put("accessToken", value);

  }

  public static String getTicket(String access_token) {
    String ticket = null;
    String url =
        "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token
            + "&type=jsapi";// 这个url链接和参数不能变
    try {
      URL urlGet = new URL(url);
      HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
      http.setRequestMethod("GET"); // 必须是get方式请求
      http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      http.setDoOutput(true);
      http.setDoInput(true);
      System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
      System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
      http.connect();
      InputStream is = http.getInputStream();
      int size = is.available();
      byte[] jsonBytes = new byte[size];
      is.read(jsonBytes);
      String message = new String(jsonBytes, "UTF-8");
      JSONObject json = JSONObject.parseObject(message);
      System.out.println("getTicket JSON:" + json);
      ticket = json.getString("ticket");
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ticket;
  }

  public static String getWechatTickes() {
    Preferences preferences = Preferences.systemRoot();
    String jsapi_ticketStr = preferences.get("jsapi_ticket", "");
    String times = preferences.get("jsapi_ticket_time", "");

    System.out.println("Preferences_jsapi_ticket:" + jsapi_ticketStr);
    System.out.println("Preferences_jsapi_ticket_time:" + times);

    // 缓存中有jspi_ticket,并且缓存时间+1小时57分钟大于当前时间
    if (StringUtils.isNotEmpty(jsapi_ticketStr) && StringUtils.isNotEmpty(times)
        && AppUtils.compareTime(new Date(Long.valueOf(times)), new Date())) {

      System.out.println("Preferences_jsapi_ticket:" + jsapi_ticketStr);
      return jsapi_ticketStr;
    }

    String access_token = getAccessToken();
    String jsapi_ticket = getTicket(access_token);

    preferences.put("jsapi_ticket", jsapi_ticket);
    long time = new Date().getTime();
    String timeStr = String.valueOf(time);
    preferences.put("jsapi_ticket_time", timeStr);
    return jsapi_ticket;
  }

  public static WechatConfig getWechatChonfig() {
    String jsapi_ticket = getWechatTickes();
    System.out.println("jsapi_ticket getWechatConfig" + jsapi_ticket);

    String url = "http://liantu.powerbass.net/liantu/wechat/index.do";

    String nonce_str = create_nonce_str();
    String timestamp = create_timestamp();
    String string1;
    String signature = "";

    // 注意这里参数名必须全部小写，且必须有序
    string1 =
        "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp
            + "&url=" + url;
    System.out.println(string1);

    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(string1.getBytes("UTF-8"));
      signature = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    WechatConfig wechatConfig =
        new WechatConfig(jsapi_ticket, nonce_str, timestamp, url, signature,
            BasicContant.WECHAT_APPID);
    /*
     * ret.put("url", url); ret.put("jsapi_ticket", jsapi_ticket); ret.put("nonceStr", nonce_str);
     * ret.put("timestamp", timestamp); ret.put("signature", signature);
     */

    return wechatConfig;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  private static String create_nonce_str() {
    return UUID.randomUUID().toString();
  }

  private static String create_timestamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }

}
