package com.kyx.basic.fifter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 防止注入
 * @author 严大灯
 * @data 2019-3-26 下午4:50:10
 * @Descripton
 */
public class XssFilter implements Filter {
	FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		 this.filterConfig = null;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

}
