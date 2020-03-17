package com.kyx.basic.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5
{
//  private static Logger LOGGER = LoggerFactory.getLogger(MD5.class);

  private static ThreadLocal threadLocal = new ThreadLocal() {
    protected synchronized Object initialValue() {
      MessageDigest messagedigest = null;
      try {
        messagedigest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
   //     MD5.LOGGER.error("初始化失败，MessageDigest不支持MD5Util", e);
      }
      return messagedigest;
    }
  };

  public static MessageDigest getMessageDigest()
  {
    return (MessageDigest)threadLocal.get();
  }

  public static String digest(String s, String charset) throws UnsupportedEncodingException {
    getMessageDigest().update(s.getBytes(charset));
    return HexUtil.bytes2Hexstr(getMessageDigest().digest());
  }
}