package com.kyx.basic.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import com.alibaba.druid.util.StringUtils;


/**
 * @author liguoping@itonghui.org 
 */
public class CommonUtils {
	
	public static String parseHexStringFromBytes(final byte[] text) {
		StringBuffer buff = new StringBuffer(0);
		for (int i = 0; i < text.length; i++) {
			byte _byte = text[i];
			byte _bytel = (byte) (_byte & 0x000f);
			byte _byteh = (byte) (_byte & 0x00f0);
			getHexString(buff, (byte) ((_byteh >> 4) & 0x000f));
			getHexString(buff, _bytel);
		}
		return buff.toString();
	}
	
	private static void getHexString(final StringBuffer buffer, final byte value) {
		if (value - 9 > 0) {
			int index = value - 9;
			switch (index) {
			case 1:
				buffer.append("A");
				break;
			case 2:
				buffer.append("B");
				break;
			case 3:
				buffer.append("C");
				break;
			case 4:
				buffer.append("D");
				break;
			case 5:
				buffer.append("E");
				break;
			case 6:
				buffer.append("F");
				break;
			default:
				break;
			}
		} else {
			buffer.append(String.valueOf(value));
		}
	}
	
	public static byte[] parseBytesByHexString(final String hexString) {
		if (hexString == null || hexString.length() == 0
				|| hexString.equals("")) {
			return new byte[0];
		}
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException(
					"hexString length must be an even number!");
		}
		StringBuffer sb = new StringBuffer(hexString);
		StringBuffer sb1 = new StringBuffer(2);
		int n = hexString.length() / 2;
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			if (sb1.length() > 1) {
				sb1.deleteCharAt(0);
				sb1.deleteCharAt(0);
			}
			sb1.append(sb.charAt(0));
			sb1.append(sb.charAt(1));
			sb.deleteCharAt(0);
			sb.deleteCharAt(0);
			bytes[i] = (byte) Integer.parseInt(sb1.toString(), 16);
		}
		return bytes;
	}
	
	public static Integer convert2int(Object o){
		if(o==null) return 0;
		else return (Integer)o;
	}
	
	 public static String xssEncode(String s){  
	        if (s == null || s.isEmpty()){  
	            return s;  
	        }

	        String result = stripXSS(s);
	        /*
	        if (null != result){
//	        	result =  HtmlUtils.htmlEscape(result);
	            result = escape(result);  
	        }*/
	        return result;
	    }
	 
	 /** 
	     * 将容易引起xss漏洞的半角字符直接替换成全角字符 
	     * @param s 
	     * @return 
	     */  
	    public static String escape(String s){
	        StringBuilder sb = new StringBuilder(s.length() + 16);
	        for (int i = 0; i < s.length(); i++){
	            char c = s.charAt(i);  
	            switch (c){
	            case '>':
	                sb.append('＞');// 全角大于号 
	                break;  
	            case '<':  
	                sb.append('＜');// 全角小于号  
	                break;  
	            case '\'':  
	                sb.append('‘');// 全角单引号
	                break;  
//	            case '\"':  
//	                sb.append('“');// 全角双引号  
//	                break;  
//	            case '\\':  
//	                sb.append('＼');// 全角斜线  
//	                break;  
	            case '%':  
	            sb.append('％'); // 全角冒号  
	            break;  
	            default:  
	                sb.append(c);  
	                break;  
	            }  
	  
	        }  
	        return sb.toString();  
	    }
	    
	    public static String stripXSS(String value){  
	        if (!StringUtils.isEmpty(value)){  
	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to  
	            // avoid encoded attacks.  
	            // value = ESAPI.encoder().canonicalize(value);  
	            // Avoid null characters  
	            value = value.replaceAll("", "");  
	            // Avoid anything between script tags  
	            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            
	            scriptPattern = Pattern.compile("alert((.*?))", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid anything in a src='...' type of expression  
	            /*scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll(""); */ 
	            // Remove any lonesome </script> tag  
	            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Remove any lonesome <script ...> tag  
	            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid eval(...) expressions  
	            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid expression(...) expressions  
	            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid javascript:... expressions  
	            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid vbscript:... expressions  
	            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Avoid onload= expressions  
	            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	              
	            scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	              
	            scriptPattern = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	            // Remove any lonesome <script ...> tag  
	            scriptPattern = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
	            value = scriptPattern.matcher(value).replaceAll("");  
	        }
	            return value;
	        }
		 
	        public static void  main(String args []) {
	            try {
	            	 Map targetDataSource = new HashMap<>();
	                //forName(String className) 返回与带有给定字符串名的类或接口相关联的 Class 对象。
					Class.forName("com.mysql.jdbc.Driver");
					//试图建立到给定数据库 URL 的连接。(本地)
		            Connection connection=DriverManager.getConnection("jdbc:mysql://192.168.1.251:3306/ltplat?useUnicode=true&characterEncoding=utf8","root","123456");
		            // Connection connection=DriverManager.getConnection("jdbc:mysql://118.31.227.138:3306/ltplat?useUnicode=true&characterEncoding=utf8","root","Kyx123!@#qwe+");
					PreparedStatement ps=connection.prepareStatement("select * from shops_info where account_status = '0' or account_status = '1'");
					ResultSet rs=ps.executeQuery();
					List<Map> list=getShopsList(rs);
					for(Map map:list){
						if(targetDataSource.containsKey((String)map.get("db_name"))) {
							
							continue;
						}
							
						//判断是不是主库 （排除主库）
						if("ltplat".equals((String)map.get("db_name"))) {
							
							continue ;
						}
						targetDataSource.put((String)map.get("db_name"),(String)map.get("jdbc_url"));
				        //同步数据库表
						//dynamicDataSourceTable(map);
						//同步数据库表的字段
						dynamicDataSourceField(map);
							
					} 
	            }catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         
	        	
	        }

	        public static List<Map> getShopsList(ResultSet rs) throws Exception {
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
	        
	        public  static void dynamicDataSourceTable (Map map) throws Exception {
	        	         Connection connection = null; 
	        	try {
	        		    String  table  = "test";
	             	    String   cretTableSql ="create table test(id int,name varchar(50),age int)";
	        	    	  //加载数据驱动
						Class.forName((String)map.get("jdbc_driverclassname"));
						 // 声明数据库的url
						String url = (String)map.get("jdbc_url");
						 // 数据库用户名
			            String username =  (String)map.get("jdbc_username"); 
			            // 数据库密码
			            String password = (String)map.get("jdbc_password"); 
			             connection = DriverManager.getConnection(url, username, password);
			            DatabaseMetaData metaData = connection.getMetaData();
			            //判断是否进行表同步
			            if(!org.apache.commons.lang3.StringUtils.isBlank(table)) {
			            	 //判断表是否存在 不存在则添加
				            ResultSet tables = metaData.getTables(null, null, table, null);
				            if(tables.next()) {
				            	
				            	System.out.println((String)map.get("db_name") + "----------"+table+" table  exist");
				            	
				            }else {
				            		//创建表 执行sql
				            		  Statement st = connection.createStatement();
			            			  int row = st.executeUpdate(cretTableSql);
			            			  st.close();
				            }
			            	
			            }else {
			            	
			            	System.out.println("请输入表名");
			            }
					} catch (Exception e) {
						// 同步失败
						e.printStackTrace();
					}finally {
						
						if(connection!=null) {
							
							connection.close();
						}
					}
	        	
	        }

  public static void dynamicDataSourceField(Map map) throws Exception {
    Connection connection = null;
    try {
      String table = "box_order";
      String field = "worker_img_url";
      String addFieldSql = "alter table  box_order modify column worker_img_url varchar(255)";
      // 加载数据驱动
      Class.forName((String) map.get("jdbc_driverclassname"));
      // 声明数据库的url
      String url = (String) map.get("jdbc_url");
      // 数据库用户名
      String username = (String) map.get("jdbc_username");
      // 数据库密码
      String password = (String) map.get("jdbc_password");
      connection = DriverManager.getConnection(url, username, password);
      DatabaseMetaData metaData = connection.getMetaData();
      // 判断是否进行表同步
      if (!org.apache.commons.lang3.StringUtils.isBlank(table)) {
        // 判断表是否存在 不存在则添加
        ResultSet tables = metaData.getTables(null, null, table, null);
        if (tables.next()) {
          // 判断是否是同步表中字段
          if (!org.apache.commons.lang3.StringUtils.isBlank(field)) {
            // 查询数据库中的字段是否存在
            ResultSet fields = metaData.getColumns(null, null, table, field);
            if (!fields.next()) {
              // 创建表中字段
              Statement st = connection.createStatement();
              // 执行sql
              st.executeUpdate(addFieldSql);
              st.close();
            } else {
              // 修改表中字段
              Statement st = connection.createStatement();
              // 执行sql
              st.executeUpdate(addFieldSql);
              st.close();
              System.out.println((String) map.get("db_name") + "--------" + field + "field  exist");
            }

          } else {
            System.out.println("请输入需要同步的列名");
          }
        } else {

          System.out
              .println((String) map.get("db_name") + "----------" + table + " table not exist");
        }

      } else {

        System.out.println("请输入表名");

      }
    } catch (Exception e) {
      // 同步失败
      e.printStackTrace();
    } finally {

      if (connection != null) {

        connection.close();
      }
    }

  }
}
