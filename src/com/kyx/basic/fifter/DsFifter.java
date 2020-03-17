package com.kyx.basic.fifter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kyx.basic.db.Dbs;
/**
 * 数据库管理过滤器
 * @author 严大灯
 * @data 2019-4-3 上午9:53:22
 * @Descripton
 */
public class DsFifter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  HttpServletRequest req = (HttpServletRequest) request;
		  String requestUrl=req.getServletPath();
		  if(requestUrl.indexOf("?")>-1){
			requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
		  }
		  String[] urlPathArr=requestUrl.split("/");
		  if(requestUrl.indexOf("login") > -1 ||(urlPathArr.length>2&&("user".equals(urlPathArr[1])))){//主张套信息
			  Dbs.setDbName(Dbs.getMainDbName());  
		  }else{//非主张套信息
			  HttpSession session=req.getSession(true);
			  if(session.getAttribute("currentDBName")!=null){
				  String currentDBName=(String) session.getAttribute("currentDBName");
				  Dbs.setDbName(currentDBName);
			  }else{//没有指定当前数据库 默认设定为主库
				  Dbs.setDbName(Dbs.getMainDbName());  
				  session.setAttribute("currentDBName", Dbs.getMainDbName());
			  }  
		  }
		 //更改数据库信息
		 
		 chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
