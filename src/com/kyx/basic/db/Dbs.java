package com.kyx.basic.db;

public class Dbs {
	private static final ThreadLocal<String> local = new ThreadLocal<String>();
	private static String mainDbName;

	public static String getDbName() {
		System.out.println("数据库使用："+local.get());
		return local.get();
	}

	public static void setDbName(String dbName) {
		local.set(dbName);
	}

	public static void clear() {
		local.remove();
	}

	public static String getMainDbName() {
		return mainDbName;
	}

	public static void setMainDbName(String mainDbName) {
		Dbs.mainDbName = mainDbName;
	}
	

	
	
	
}
