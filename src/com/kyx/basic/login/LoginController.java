package com.kyx.basic.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.resource.service.ResourceService;
import com.kyx.basic.security.MySecurityMetadataSource;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.userloginlist.model.UserLoginList;
import com.kyx.basic.userloginlist.service.UserLoginListService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.Common;
import com.kyx.basic.util.RandomValidateCode;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.service.AppointService;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.remind.service.RemindService;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.service.WechatConfigService;

/**
 * 登录-主页面显示
 * 
 * @author 严大灯
 * @data 2019-3-19 上午10:44:08
 * @Descripton
 */
@Controller
public class LoginController extends BaseController {
	@Resource
	private AuthenticationManager myAuthenticationManager;
	@Resource
	private UserService userService;
	@Resource
	private UserLoginListService userLoginListService;
	@Resource
	private BCryptPasswordEncoder passwordEncoder;
	@Resource
	private ResourceService resourceService;
	@Resource
	private ShopsService shopsService;
	@Resource
	private ProductService productService;

	@Resource
	private RemindService remindService;

	@Resource
	private AppointService appointService;

	@Resource
	private CustomService customService;

	@Resource
	private MySecurityMetadataSource securityMetadataSource;
	@Resource
	private WechatConfigService wechatConfigService;
	@RequestMapping("/login")
	public String login(HttpSession session, Model model) {
		return "login";
	}

	@RequestMapping("/index")
	public String index(HttpSession session, Model model,
			HttpSession httpSession, Integer shopId) {
		User user = (User) httpSession.getAttribute(User.USER_SESSION);
		model.addAttribute("user", user);
		Shops shops = (Shops) httpSession.getAttribute(Shops.SHOPS_SESSION);
		model.addAttribute("shops", shops);
		// 查询当前门店的连锁店列表
		model.addAttribute("shops", shops);
		String dbName = shops.getDbName();
		List<Shops> shopList=new ArrayList<Shops>();
		//特殊处理系统管理员 要获取平台数据切换数据
		if(user.getRoleId()==1&&shops.getId()!=1){//管理员
			Dbs.setDbName(Dbs.getMainDbName());
			shopList.add(shopsService.getById(1));
		}
		shopList.addAll(shopsService.getShops().get(dbName));
		model.addAttribute("shopList", shopList);
		return "index";
	}

	@ResponseBody
	@RequestMapping("/changeShop")
	public String changeShop(HttpSession session, Model model,
			HttpSession httpSession, Integer shopId,HttpServletRequest request) {
		RetInfo ret = RetInfo.Error("没有权限");
		User user = (User) httpSession.getAttribute(User.USER_SESSION);
		model.addAttribute("user", user);
		if (shopId != null && user.getBossAccount() == 1) {// 更换门店
			Dbs.setDbName(Dbs.getMainDbName());
			Shops shops = shopsService.getById(shopId);
			model.addAttribute("shops", shops);
			httpSession.setAttribute(Shops.SHOPS_SESSION, shops);
			WechatConfig wechatConfig=wechatConfigService.getById(shops.getWechatId());
			request.getSession().setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, wechatConfig);
			// 查询当前门店的连锁店列表
			model.addAttribute("shops", shops);
			String dbName = shops.getDbName();
			List<Shops> shopList = shopsService.getShops().get(dbName);
			model.addAttribute("shopList", shopList);
			setShopListSession(dbName,request);
			ret = RetInfo.Success("");
		}
		return JSON.toJSONString(ret);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(String username, String password,
			HttpServletRequest request, HttpSession session, String shopId,
			String isAdminFlag) {
		// 验证用户账号与密码是否正确
		try {
			username = username.trim();
			if (!request.getMethod().equals("POST")) {
				return AppUtils
						.getReturnInfo(new RetInfo("error", "支持POST方法提交"));
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				return AppUtils.getReturnInfo(new RetInfo("error",
						"用户名或密码不能为空！"));
			}
			// 使用主张套
			System.out.println(Dbs.getDbName());
			// 根据用户名查询门店信息
			User user = userService.querySingleUser(username);
			if (user == null)
				return AppUtils.getReturnInfo(new RetInfo("error", "用户不存在！"));
			Shops shops = shopsService.getById(user.getShopId());
			setShopListSession(shops.getDbName(), request);
			// 先判断当前门店是否在运行
			if (!"0".equals(shops.getAccountStatus())
					&& !"1".equals(shops.getAccountStatus())) {// 门店已过期
				return AppUtils.getReturnInfo(new RetInfo("error", "门店已过期！"));
			}
			if (user == null
					|| !passwordEncoder.matches(password,
							user.getUserPassword())) {
				return AppUtils
						.getReturnInfo(new RetInfo("error", "用户或密码不正确！"));
			}
			WechatConfig wechatConfig=wechatConfigService.getById(shops.getWechatId());
			// 记录登录信息
			UserLoginList userLoginList = new UserLoginList();
			userLoginList.setUserId(user.getId());
			userLoginList.setLoginTime(new Date());
			userLoginList.setLoginIp(Common.toIpAddr(request));
			userLoginList.setShopId(shops.getId());
			userLoginListService.add(userLoginList);
			// 校验码先去掉
			// if (!checkValidateCode(request)) {
			// return AppUtils.getReturnInfo(new RetInfo("error", "验证码错误！"));
			// }

			Authentication authentication = myAuthenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							username, password));
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			securityContext.setAuthentication(authentication);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			// 当验证都通过后，把用户信息放在session里
			request.getSession().setAttribute(User.USER_SESSION, user);
			request.getSession().setAttribute(Shops.SHOPS_SESSION, shops);
			request.getSession().setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, wechatConfig);
			// 更换数据库
			Dbs.setDbName(shops.getDbName());
			session.setAttribute("currentDBName", shops.getDbName());

			return AppUtils.getReturnInfo(new RetInfo("success", "登录成功！"));
		} catch (AuthenticationException e) {
			return AppUtils.getReturnInfo(new RetInfo("error", "登录异常，请联系管理员！"));
			// e.printStackTrace();
		}
	}

	/**
	 * 验证码判断
	 * 
	 * @param request
	 * @return
	 */
	protected boolean checkValidateCode(HttpServletRequest request) {
		Object object = request.getSession().getAttribute(
				RandomValidateCode.RANDOMCODEKEY);
		if (object != null) {
			String result_verifyCode = object.toString();
			String user_verifyCode = request.getParameter("verity");// 获取用户输入验证码
			if (null == user_verifyCode
					|| !result_verifyCode.equalsIgnoreCase(user_verifyCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 登录页面生成验证码
	 */
	@RequestMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getMenuJson")
	public String getMenuJson(HttpSession session, Model model)
			throws Exception {
		// 查询出该用户所有菜单
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return AppUtils.getReturnInfo(new RetInfo("error", "登录异常！"));
		}
		List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		if (list.size() == 0) {
			return AppUtils.getReturnInfo(new RetInfo("error", "登录异常！"));
		}
		// 查询一级菜单
		// 菜单全部放到主账套统一管理 查询要先设置主张套 然后切换到当前登录的账套
		// 平台员工有权限进入其它门店 拥有所有门店菜单列表
		// Dbs.setDbName(Dbs.getMainDbName());
		User user=(User) session.getAttribute(User.USER_SESSION);
		Boolean isAdmin=user.getRoleId()==1;
		List<com.kyx.basic.resource.model.Resource> menuList = resourceService.getMenu(isAdmin,list, 0);
		List<com.kyx.basic.resource.model.Resource> allList = resourceService.getMenu(isAdmin,list, null);
		List resultList = new ArrayList();
		for (com.kyx.basic.resource.model.Resource pmenu : menuList) {
			Map map = new HashMap();
			map.put("id", pmenu.getId());
			map.put("resKey", pmenu.getResKey());
			map.put("path", pmenu.getResUrl());
			map.put("title", pmenu.getName());
			map.put("icon", pmenu.getIcon());
			map.put("itemed", pmenu.getItemed());
			map.put("children", null);
			List<Map> childTmpList = getChildList(allList, pmenu.getId());
			if (childTmpList.size() > 0)
				map.put("children", childTmpList);
			resultList.add(map);
		}
		String menuJson = JSONArray.toJSONString(resultList).toString();
		return menuJson;
	}

	private List<Map> getChildList(
			List<com.kyx.basic.resource.model.Resource> childList,
			Integer menuId) {
		List<Map> list = new ArrayList<Map>();
		for (com.kyx.basic.resource.model.Resource menu : childList) {
			if (menu.getParentId().intValue() == menuId.intValue()) {
				Map map = new HashMap();
				map.put("id", menu.getId());
				map.put("resKey", menu.getResKey());
				map.put("title", menu.getName());
				map.put("icon", menu.getIcon());
				map.put("path", menu.getResUrl());
				map.put("itemed", menu.getItemed());
				map.put("children", null);
				List<Map> childTmpList = getChildList(childList, menu.getId());
				if (childTmpList.size() > 0)
					map.put("children", childTmpList);
				list.add(map);
			}
		}
		return list;
	}

	// 后台主页面
	@RequestMapping("/welcome")
	public String main(HttpSession session, Model model,
			HttpServletRequest httpServletRequest) {
		String mainDBName = Dbs.getMainDbName();
		String currentDBName = Dbs.getDbName();
		if (currentDBName.equals(mainDBName)) {// 平台主页面
			return "platwelcome";
		} else {
			Integer shopId = getShopId(httpServletRequest);
			// 查询库存报警
			Integer count = productService.getArertCount(shopId);
			model.addAttribute("productArertCount", count);

			// 日常提醒
			model.addAttribute("remindCount",
					remindService.queryOldRemindsCount());

			// 邀约
			model.addAttribute("invitationCount",
					remindService.queryInvitationCount(shopId));

			// 预约
			model.addAttribute("appointCount",
					appointService.queryCustomAppointsCount());

			// 过期
			model.addAttribute("expireCount",
					customService.queryExpireMealDtsCount());

			return "welcome";
		}
	}

	/**
	 * 设置门店列表session
	 * 
	 * @param jdbcUrl
	 * @param request
	 */
	private void setShopListSession(String dbName, HttpServletRequest request) {
		Map<Integer, String> map = shopsService.getShopsByBdName(dbName);
		request.getSession().setAttribute(Shops.SHOP_LIST_SESSION, map);
	}

	@ResponseBody
	@RequestMapping("/changeShopByAdmin")
	public String changeShopByAdmin(HttpSession session, Model model,
			HttpSession httpSession, Integer shopId,HttpServletRequest request) {
		RetInfo ret = RetInfo.Error("没有权限");
		User user = (User) httpSession.getAttribute(User.USER_SESSION);
		model.addAttribute("user", user);
		if (shopId != null) {// 更换门店
			Dbs.setDbName(Dbs.getMainDbName());
			Shops shops = shopsService.getById(shopId);
			model.addAttribute("shops", shops);
			httpSession.setAttribute(Shops.SHOPS_SESSION, shops);
			WechatConfig wechatConfig=wechatConfigService.getById(shops.getWechatId());
			request.getSession().setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, wechatConfig);
			// 查询当前门店的连锁店列表
			model.addAttribute("shops", shops);
			String dbName = shops.getDbName();
			List<Shops> shopList = shopsService.getShops().get(dbName);
			model.addAttribute("shopList", shopList);
			setShopListSession(dbName,request);
			ret = RetInfo.Success("");
			String shopName = shops.getDbName();
			Dbs.setDbName(shopName);
			session.setAttribute("currentDBName", shopName);
			securityMetadataSource.loadResource();
		}
		return JSON.toJSONString(ret);
	}
}
