package com.kyx.biz.wxutil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
/**
 * **********************************************
 * Copyright (c)  by anhui tonghui information technology Co., Ltd.
 * All right reserved.
 * Create Date: 2012-8-22
 * Create Author: doumingjun
 * File Name:  PropertiesUtil
 * Last version:  1.0
 * Function:读取.properties配置文件的内容至Map中。
 * Last Update Date:
 * Change Log:
 *************************************************
 */
public class PropertiesUtil {

	/**
	 * 读取.properties配置文件的内容至Map中
	 * @param propertiesFile
	 * @return
	 */
	public static Map<Object,Object> read(String propertiesFile) {
		ResourceBundle rb = ResourceBundle.getBundle(propertiesFile);
		Map<Object,Object> map = new HashMap<Object,Object>();
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
	 * 根据key读取value值
	 * @data 2014-03-04
	 * @author zhoujieyan
	 * @param key 关键字
	 * @return
	 */
	public String readValue(String key) {
		String fileName = "login.properties";
		String path = Thread.currentThread().getContextClassLoader().getResource(fileName).toString();
		String filePath = path.substring(5).replaceAll("%20"," ");
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			in.close();
			return props.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 *	读取properties属性配置文件中的参数值
	 * @Date 2015-8-12 下午5:21:41
	 * @Author wangqt@itonghui.org
	 * @param key
	 * @param type 0,表示读取login.properties 1,表示读取wxpay.properties,2  通联支付文件
	 * @return String
	 */
	public static String getPropertiesValue(String key) {
		String fileName = "wechat.properties";
		String path = Thread.currentThread().getContextClassLoader().getResource(fileName).toString();
		String filePath = path.substring(5).replaceAll("%20"," ");
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			in.close();
			return props.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
//		String token=PropertiesUtil.getPropertiesValue("token");
//		System.out.println(token);
//		Map map = PropertiesUtil.read("memcached");
//		Object a= (Object)map.get("pool.maxIdle");
	
	}

}

