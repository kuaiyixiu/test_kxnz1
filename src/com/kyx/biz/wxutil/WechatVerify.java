package com.kyx.biz.wxutil;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;
import com.kyx.basic.util.PropertiesUtil;


public class WechatVerify {

  /**
   * 方法名：checkSignature</br> 详述：验证签名</br>
   * 
   * @param signature
   * @param timestamp
   * @param nonce
   * @return
   * @throws
   */
  public static boolean checkSignature(String signature, String timestamp, String nonce) {
    // 1.将token、timestamp、nonce三个参数进行字典序排序
    String token = PropertiesUtil.getPropertiesValue("token", 2);
    String[] arr = new String[] {token, timestamp, nonce};
    Arrays.sort(arr);
    // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      content.append(arr[i]);
    }
    MessageDigest md = null;
    String tmpStr = null;
    try {
      md = MessageDigest.getInstance("SHA-1");
      // 将三个参数字符串拼接成一个字符串进行sha1加密
      byte[] digest = md.digest(content.toString().getBytes());
      tmpStr = byteToStr(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    content = null;
    System.out.println("tmpStr:" + tmpStr);
    System.out.println("signature:" + signature);
    // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
    return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
  }

  private static String byteToStr(byte[] byteArray) {
    StringBuilder strDigest = new StringBuilder();
    for (int i = 0; i < byteArray.length; i++) {
      strDigest.append(byteToHexStr(byteArray[i]));
    }
    return strDigest.toString();
  }

  private static String byteToHexStr(byte mByte) {
    char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
    tempArr[1] = Digit[mByte & 0X0F];
    String s = new String(tempArr);
    return s;
  }

  public static String getWechatProperties(String key) {
    Properties properties = new Properties();
    // 使用ClassLoader加载properties配置文件生成对应的输入流
    InputStream in =
        Properties.class.getClassLoader().getResourceAsStream("config/wechat.properties");
    String value = "";
    // 使用properties对象加载输入流
    try {
      properties.load(in);
      value = properties.getProperty(key);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return value;
  }

}
