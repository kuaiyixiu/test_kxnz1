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

import com.kyx.basic.util.BasicContant;
/**
 * 微信端过滤器 主要处理session失效问题
 * @author 严大灯
 * @data 2019-5-17 下午2:53:34
 * @Descripton
 */
public class WechatFifter implements Filter{

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
		if (
				urlPathArr.length > 2 &&
						("wechat".equals(urlPathArr[1]) && !"callback".equals(urlPathArr[2]) && !"error.do".equals(urlPathArr[2])
								&& !"bindcustinfo.do".equals(urlPathArr[2]) && !"serviceappointment.do".equals(urlPathArr[2])
								&& !"isHasCustom.do".equals(urlPathArr[2])
						)
						&& (urlPathArr.length > 3 && !"regIndex.do".equals(urlPathArr[3]) &&
						!"pwdIndex.do".equals(urlPathArr[3]) && !"register.do".equals(urlPathArr[3])
						&& !"login.do".equals(urlPathArr[3]))
		) {
			HttpSession session = req.getSession(true);
			if (("community".equals(urlPathArr[2]) && session.getAttribute(BasicContant.MASTERWORKER_SESSION) == null)
					|| (!"community".equals(urlPathArr[2]) && session.getAttribute(BasicContant.CUSTOMER_SESSION) == null)) {
				request.getRequestDispatcher("/wechat/error.do").forward(request, response);
				return;
			}
		}
		 chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
		
	}

	@Override
	public void destroy() {
		
		
	}

}
