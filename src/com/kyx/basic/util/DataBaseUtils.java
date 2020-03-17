package com.kyx.basic.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kyx.basic.shops.model.Shops;

@Component
public class DataBaseUtils {

	@Value("${jdbc_url}")
	private String jdbcUrl;

	@Value("${jdbc_username}")
	private String jdbcUsername;

	@Value("${jdbc_password}")
	private String jdbcPassword;

	/**
	 * 创建数据库
	 * 
	 * @param shops
	 * @throws SQLException
	 */
	public void createDatabase(Shops shops, String sqlFilePath) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = DriverManager.getConnection(jdbcUrl,
				jdbcUsername, jdbcPassword);
		Statement stat = connection.createStatement();

		String dbName = shops.getDbName();
		String createSql = "create database " + dbName;
		stat.executeUpdate(createSql);

		createTable(sqlFilePath, shops);

		stat.close();
		connection.close();
	}

	/**
	 * 创建表
	 * 
	 * @param sqlFile
	 * @param shops
	 * @throws SQLException
	 */
	private void createTable(String sqlFile, Shops shops) throws SQLException {
		Statement stmt = null;
		List<String> sqlList = new ArrayList<String>();
		Connection conn = getConnection(shops);

		try {
			// 按行来读取sql
			sqlList = loadSql(sqlFile);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (String sql : sqlList) {
				System.out.println(sql);
				stmt.addBatch(sql);
			}
			int[] rows = stmt.executeBatch();
			System.out.println("Row count:" + Arrays.toString(rows));
			conn.commit();
			System.out.println("数据更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			stmt.close();
			conn.close();
		}

	}

	private List<String> loadSql(String sqlFile) throws Exception {
		List<String> sqlList = new ArrayList<String>();
		InputStream sqlFileIn = new FileInputStream(sqlFile);
		StringBuffer sqlSb = new StringBuffer();
		byte[] buff = new byte[sqlFileIn.available()];
		int byteRead = 0;
		while ((byteRead = sqlFileIn.read(buff)) != -1) {
			sqlSb.append(new String(buff, "UTF-8"));
		}

		String sqlArr[] = sqlSb.toString().split("(;\\s*\\rr\\n)|(;\\s*\\n)");

		for (int i = 0; i < sqlArr.length; i++) {
			// String sql = sqlArr[i].replaceAll("--.*", "").trim();
			String sql = sqlArr[i].trim();
			if (StringUtils.isNotEmpty(sql)) {
				sqlList.add(sql);
			}
		}

		return sqlList;
	}

	private Connection getConnection(Shops shops) {
		Connection conn = null;
		String driver = shops.getJdbcDriverclassname();
		String url = shops.getJdbcUrl();
		String userName = shops.getJdbcUsername();
		String pwd = shops.getJdbcPassword();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, pwd);
			if (!conn.isClosed()) {
				System.out.println("数据库连接成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
}
