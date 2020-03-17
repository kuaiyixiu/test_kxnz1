package com.kyx.basic.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * ********************************************** Copyright (c) by anhui tonghui information
 * technology Co., Ltd. All right reserved. Create Date: 2012-8-22 Create Author: doumingjun File
 * Name: PropertiesUtil Last version: 1.0 Function:读取.properties配置文件的内容至Map中。 Last Update Date:
 * Change Log:
 ************************************************* 
 */
public class PropertiesUtil {

  /**
   * 读取.properties配置文件的内容至Map中
   * 
   * @param propertiesFile
   * @return
   */
  public static Map<Object, Object> read(String propertiesFile) {
    ResourceBundle rb = ResourceBundle.getBundle(propertiesFile);
    Map<Object, Object> map = new HashMap<Object, Object>();
    @SuppressWarnings("rawtypes")
    Enumeration enu = rb.getKeys();
    while (enu.hasMoreElements()) {
      Object obj = enu.nextElement();
      Object objv = rb.getObject(obj.toString());
      map.put(obj, objv);
    }
    return map;
  }


  /**
   * 读取properties属性配置文件中的参数值
   * 
   * @Date 2015-8-12 下午5:21:41
   * @Author wangqt@itonghui.org
   * @param key
   * @param type 0,表示读取login.properties 1,app加密配置2微信公总号配置
   * @return String
   */
  public static String getPropertiesValue(String key, int type) {
    String fileName = "";
    if (type == 1) {
      fileName = "cert.properties";
    } else if (type == 2) {
      fileName = "wechat.properties";
    } else if (type == 3) {
      fileName = "version.properties";
    }
    Properties props = new Properties();
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
      props.load(in);
      in.close();
      return props.getProperty(key);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(getPropertiesValue("PRIVATE_KEY", 1));
    // Map map = PropertiesUtil.read("memcached");
    // Object a= (Object)map.get("pool.maxIdle");

  }

}
