package com.kyx.basic.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.kyx.basic.util.BasicContant;

/**
 * 数据源管理类
 * 
 * @author 严大灯
 * @data 2019-4-1 上午8:27:34
 * @Descripton
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	// 默认数据源，也就是主库
	protected DataSource masterDataSource;
	// 保存动态创建的数据源
	private static final Map targetDataSource = new HashMap<>();

	@Override
	protected DataSource determineTargetDataSource() {
		return (DataSource) determineCurrentLookupKey();

	}
	/**
     * 返回数据源名称
     */
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		String dataBaseName = Dbs.getDbName();
		if (dataBaseName == null) {
			// 默认的数据源名字
			dataBaseName = Dbs.getMainDbName();
		}
		return selectDataSource(dataBaseName);
				//(DataSource) targetDataSource.get(dataBaseName);
	}

	/*
	 * public void setTargetDataSource(Map targetDataSource) {
	 * this.targetDataSource = targetDataSource;
	 * super.setTargetDataSources(this.targetDataSource); }
	 */

	/*
	 * public Map getTargetDataSource() { return this.targetDataSource; }
	 */

	public void addTargetDataSource(String key, BasicDataSource dataSource) {
		this.targetDataSource.put(key, dataSource);
		// setTargetDataSources(this.targetDataSource);
	}

	/**
	 * 该方法为同步方法，防止并发创建两个相同的数据库 使用双检锁的方式，防止并发
	 * 
	 * @param dbType
	 * @return
	 */
	private synchronized DataSource selectDataSource(String dataBaseName) {
		// 再次从数据库中获取，双检锁
		DataSource obj = (DataSource) this.targetDataSource.get(dataBaseName);
		if (null != obj) {
			return obj;
		}
		// 为空则创建数据库
		BasicDataSource dataSource = this.getDataSource(dataBaseName);
		if (null != dataSource) {
			// 将新创建的数据库保存到map中
			this.setDataSource(dataBaseName, dataSource);
			return dataSource;
		} else {
			System.out.println("============门店数据库"+dataBaseName+"建立连接失败");
			// throw new SystemException("创建数据源失败！");
			return null;
		}
	}

	/**
	 * 查询对应数据库的信息
	 * 
	 * @param dbtype
	 * @return
	 */
	private BasicDataSource getDataSource(String dataBaseName) {
		BasicDataSource dataSource = null;
		String oriType = Dbs.getDbName();
//		 先切换回主库
		Dbs.setDbName(Dbs.getMainDbName());
//		 查询所需信息
		try {
			Connection connection=masterDataSource.getConnection();
			PreparedStatement ps=connection.prepareStatement("select * from shops_info where db_name ='"+dataBaseName+"'");
			ResultSet rs=ps.executeQuery();
			List<Map> list=getShopsList(rs);
			if(list.size()<1)
				System.out.println("============查询门店数据库"+dataBaseName+"失败");
			Map map=list.get(0);
//			 切换回目标库
			Dbs.setDbName(oriType);
			dataSource = createDataSource((String)map.get("jdbc_driverclassname"),(String)map.get("jdbc_url"), (String)map.get("jdbc_username"),(String)map.get("jdbc_password"));
			connection.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return dataSource;
	}

	// 创建数据源
	private BasicDataSource createDataSource(String driverClassName,
			String url, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setTestWhileIdle(true);

		return dataSource;
	}

	public void setDataSource(String type, BasicDataSource dataSource) {
		this.addTargetDataSource(type, dataSource);
		Dbs.setDbName(type);
	}

	/*
	 * @Override public void setTargetDataSources(Map targetDataSources) { //
	 * TODO Auto-generated method stub
	 * super.setTargetDataSources(targetDataSources); //
	 * 重点：通知container容器数据源发生了变化 afterPropertiesSet(); }
	 */

	/**
	 * 动态创建数据源 该方法重写为空，因为AbstractRoutingDataSource类中会通过此方法将，
	 * targetDataSources变量中保存的数据源交给resolvedDefaultDataSource变量
	 * 在本方案中动态创建的数据源保存在了本类的targetDataSource变量中
	 * 。如果不重写该方法为空，会因为targetDataSources变量为空报错
	 * 如果仍然想要使用AbstractRoutingDataSource类中的变量保存数据源
	 * ，则需要在每次数据源变更时，调用此方法来为resolvedDefaultDataSource变量更新
	 */
	@Override
	public void afterPropertiesSet() {
		System.out.println("=================开始动态创建数据源================");
		try {
			Connection connection=masterDataSource.getConnection();
			PreparedStatement ps=connection.prepareStatement("select * from shops_info where account_status = '0' or account_status = '1'");
			ResultSet rs=ps.executeQuery();
			List<Map> list=getShopsList(rs);
			int i=0;
			for(Map map:list){
				if(targetDataSource.containsKey((String)map.get("db_name")))
					continue;
				this.addTargetDataSource((String)map.get("db_name"),
				createDataSource((String)map.get("jdbc_driverclassname"),
						(String)map.get("jdbc_url"), (String)map.get("jdbc_username"),
						 (String)map.get("jdbc_password")));
				i++;
			}
			System.out.println("=================初始化数据源个数:"+i+"个================");
			connection.close();	
			//设定初始化数据源
			Dbs.setMainDbName(BasicContant.PLAT_DBNAME);
			Dbs.setDbName(Dbs.getMainDbName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("=================结束动态创建数据源================");
	}



	private List<Map> getShopsList(ResultSet rs) throws Exception {
		List<Map> list=new ArrayList<Map>();
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while (rs.next()) {
			Map rowData = new HashMap();// 声明Map
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));// 获取键名及值
			}
			list.add(rowData);
		}
		return list;
	}

	public DataSource getMasterDataSource() {
		return masterDataSource;
	}

	public void setMasterDataSource(DataSource masterDataSource) {
		this.masterDataSource = masterDataSource;
	}

}
