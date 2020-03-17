package com.kyx.basic.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.userloginlist.model.UserLoginList;
import com.kyx.basic.userloginlist.service.UserLoginListService;
import com.kyx.basic.util.Common;


/**
 * 这个类主要是用户登录验证
 * @author 严大灯
 * @data 2019-3-28 下午5:45:13
 * @Descripton
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	/**
	 * 登录成功后跳转的地址
	 */
	private String successUrl = "/index.do";
	/**
	 * 登录失败后跳转的地址
	 */
	private String errorUrl = "";
	@Resource
	private UserService userService;
	@Resource
	private UserLoginListService userLoginListService;
	
	/**
	 * 自定义表单参数的name属性，默认是 j_username 和 j_password
	 * 定义登录成功和失败的跳转地址
	 * @author ydd
	 * @date 2019-3-28 下午5:45:13
	 */
	public void init() {
//		System.err.println(" ---------------  MyAuthenticationFilter init--------------- ");
		this.setUsernameParameter(USERNAME);
		this.setPasswordParameter(PASSWORD);
		// 验证成功，跳转的页面
//		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//		successHandler.setDefaultTargetUrl(successUrl);
//		this.setAuthenticationSuccessHandler(successHandler);

		// 验证失败，跳转的页面
//		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
//		failureHandler.setDefaultFailureUrl(errorUrl);
//		this.setAuthenticationFailureHandler(failureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
//		System.err.println(" ---------------  MyAuthenticationFilter attemptAuthentication--------------- ");
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: "+ request.getMethod());
		}

		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		if (Common.isEmpty(username) || Common.isEmpty(password)) {
			BadCredentialsException exception = new BadCredentialsException("用户名或密码不能为空！");// 在界面输出自定义的信息！！
			throw exception;
		}

		// 验证用户账号与密码是否正确
		User users = userService.querySingleUser(username);
		if (users == null || !users.getUserPassword().equals(password)) {
			BadCredentialsException exception = new BadCredentialsException("用户名或密码不匹配！");// 在界面输出自定义的信息！！
			throw exception;
		}
		// 当验证都通过后，把用户信息放在session里
		request.getSession().setAttribute("userSession", users);
		// 记录登录信息
//		UserLoginList userLoginList = new UserLoginList();
//		userLoginList.setUserId(users.getId());
//		userLoginList.setLoginIp(Common.toIpAddr(request));
//		userLoginListService.add(userLoginList);
		// 实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
   
}
