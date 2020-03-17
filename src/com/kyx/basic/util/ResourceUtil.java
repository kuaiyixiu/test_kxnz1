package com.kyx.basic.util;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResourceUtil {
	private static Properties sysConfig = new Properties();
	private static Properties urlConfig = new Properties();
	
	private static Map<String, Properties> configs = new HashMap<String, Properties>();
	private static String CLASSPATH = ResourceUtil.class.getResource("/").getPath();
	
	static {
		try {
			System.out.println("context:" + getContextRealPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getResource(Properties config, String file) throws Exception {
		FileReader fr = new FileReader(CLASSPATH + file);
		config.load(fr);
	}
	
	public static String getSystemCfg(String key) {
		return sysConfig.getProperty(key, null);
	}
	
	public static String getUrlCfg(String key) {
		return urlConfig.getProperty(key, null);
	}
	
	public static Properties getProperties(String file) {
		try {
			Properties _sysConfig;
			if(!file.endsWith(".properties")) {
				file += ".properties";
			}
			_sysConfig = configs.get(file);
			if(_sysConfig == null) {
				_sysConfig = new Properties();
				getResource(_sysConfig, file);
				configs.put(file, _sysConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return configs.get(file);
	}
	
	public static Properties getUrlCfgProperties() {
		return urlConfig;
	}
	
	public static Properties getDBMapCfgProperties() {
		return getProperties("dbmap-config.properties");
	}
	
	/**
	 * 从指定配置文件中获取指定key的
	 * @param file 可以是全名，也可以是不带后缀名的
	 * @param key
	 * @return
	 */
	public static String getConfig(String file, String key) {
		Properties _sysConfig;
		try {
			if(!file.endsWith(".properties")) {
				file += ".properties";
			}
			
			_sysConfig = configs.get(file);
			if(_sysConfig == null) {
				_sysConfig = new Properties();
				getResource(_sysConfig, file);
				configs.put(file, _sysConfig);
			}
			
			return _sysConfig.getProperty(key, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getConfigNoCache(String file, String key) {
		String CLASSPATH1 = CLASSPATH.substring(0, CLASSPATH.length() - 8);
		Properties _sysConfig =  new Properties();
		try {
			if(!file.endsWith(".properties")) {
				file += ".properties";
			}
			FileReader fr = new FileReader(CLASSPATH1 + file);
			_sysConfig.load(fr);
			
			return _sysConfig.getProperty(key, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getContextRealPath() {
		//return CLASSPATH.substring(0, CLASSPATH.indexOf("/WEB-INF"));
		return "";
	}
}
