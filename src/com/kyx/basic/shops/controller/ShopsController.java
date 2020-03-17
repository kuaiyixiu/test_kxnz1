package com.kyx.basic.shops.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.role.mapper.RoleMapper;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.box.model.BoxInfo;
import com.kyx.biz.box.service.BoxService;
import com.kyx.biz.wechat.mapper.WechatConfigMapper;
import com.kyx.biz.wechat.model.WechatConfig;

/**
 * 门店控制层
 * 
 * @author daibin
 * @date 2019-5-23 上午9:54:14
 * 
 */
@Controller
@RequestMapping(value = "/shop")
@PropertySource("classpath:jdbc.properties")
public class ShopsController extends BaseController {

  @Resource
  private ShopsService shopsService;

  @Resource
  private BoxService boxService;

  @Value("${add_shop_sql_file_path}")
  private String file_path;

  @Resource
  private RoleMapper roleMapper;
  
  @Resource
  private  WechatConfigMapper wechatConfigMapper ;
  /**
   * 首页
   * 
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model, HttpServletRequest request) {
    User user = getUser(request);
    model.addAttribute("user", user);
    model.addAttribute("shopId", getShopId(request));

    return "shop/accountInfo";
  }

  /**
   * 查询客户
   * 
   * @param shops
   * @param pager
   * @return
   */
  @RequestMapping(value = "/queryShops", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryShops(Shops shops, Pager pager) {
    GrdData data = shopsService.queryShops(shops, pager);
    return JSON.toJSONString(data);
  }

  /**
   * 更新门店
   * 
   * @param shops
   * @return
   */
  @RequestMapping(value = "/updateShops", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String updateShops(Shops shops) {
    Dbs.setDbName(Dbs.getMainDbName());
    RetInfo ret = shopsService.updateShops(shops);

    return AppUtils.getReturnInfo(ret);
  }

  /**
   * 编辑门店
   * 
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/editShopView")
  public String editShopView(Model model, Integer id) {
    model.addAttribute("shop", shopsService.getById(id));

    return "shop/editShop";
  }

  /**
   * 添加门店界面
   * 
   * @return
   */
  @RequestMapping("/addShopView")
  public String addShopView(Model model, Integer id) {
    if (id != null)
      model.addAttribute("shops", shopsService.getById(id));
    return "shop/addShop";
  }

  /**
   * 添加门店
   * 
   * @param shops
   * @return
   */
  // @RequestMapping("/addShop")
  // @ResponseBody
  // public String addShop(Shops shops, User user, HttpServletRequest request) {
  // String sqlFilePath = request.getServletContext().getRealPath(file_path);
  // RetInfo ret = shopsService.addShop(shops, user, sqlFilePath);
  //
  // return AppUtils.getReturnInfo(ret);
  // }

  /**
   * 门店信息完善
   * 
   * @param request
   * @param model
   * @return
   */
  @RequestMapping("/toEditShop")
  public String toEditShop(HttpServletRequest request, Model model) {
    Shops shops = getShops(request);
    WechatConfig config = getWechatConfig(request);
    model.addAttribute("shops", shops);
    if (config != null)
      model.addAttribute("config", config);
    //判断登录的角色是否是系统管理员
	User user = (User) request.getSession().getAttribute(User.USER_SESSION);
    Role role = roleMapper.selectByPrimaryKey(user.getRoleId());
    if(role != null && "ROLE_ADMIN".equalsIgnoreCase(role.getRoleKey())) {
       //系统管理员登录
    	
    	model.addAttribute("role","admin");
    }
    return "shop/toeditshop";
  }

  /**
   * 保存门店完善的信息
   * 
   * @param shops
   * @param wechatConfig
   * @param request
   * @return
   */
  @RequestMapping("/saveEditShop")
  @ResponseBody
  public String saveEditShop(Shops shops, WechatConfig wechatConfig, String role , HttpServletRequest request) {

    RetInfo ret = shopsService.saveEditShop(shops, wechatConfig,role, request);

    return AppUtils.getReturnInfo(ret);
  }

  /**
   * 账户列表
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping("/bossInfo")
  public String bossInfo(Model model, HttpServletRequest request) {
    return "shop/bossInfo";
  }

  /**
   * 门店信息列表
   * 
   * @param model
   * @param request
   * @param id
   * @return
   */
  @RequestMapping("/shopInfo")
  public String shopInfo(Model model, HttpServletRequest request, Integer id) {
    model.addAttribute("bossId", id);// 老板账户id
    return "shop/shopInfo";
  }

  /**
   * 新增门店信息
   * 
   * @param shops
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/saveShopInfo")
  @ResponseBody
  public String saveShopInfo(Shops shops, HttpServletRequest request) throws Exception {
    String sqlFilePath = request.getServletContext().getRealPath(file_path);
    RetInfo ret = shopsService.saveShopInfo(shops, sqlFilePath);
    return AppUtils.getReturnInfo(ret);
  }

  /**
   * 增加短信数量视图
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping("/addSmsCount")
  public String addSmsCount(Model model, HttpServletRequest request) {
    return "shop/addSmsCount";
  }

  /**
   * 更新短信数量
   * 
   * @param shops
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/updateShopSmsCount")
  @ResponseBody
  public String updateShopSmsCount(Shops shops, HttpServletRequest request) throws Exception {
    shops.setUserRealname(getUser(request).getUserRealname());
    RetInfo ret = shopsService.updateShopSmsCount(shops);
    return AppUtils.getReturnInfo(ret);
  }

  @RequestMapping("/addBoxView")
  public String addBoxView(Model model, Integer id) {
    model.addAttribute("id", id);
    return "shop/addBox";
  }

  /**
   * 添加盒子
   * 
   * @param boxInfo
   * @return
   */
  @RequestMapping("/addBox")
  @ResponseBody
  public String addBox(BoxInfo boxInfo) {
    RetInfo ret = boxService.addBox(boxInfo);

    return AppUtils.getReturnInfo(ret);
  }
  
  /**
         *  查询全局公众号配置
   * 
   * @param boxInfo
   * @return
   */
  @RequestMapping("/wchatcfig")
  public String wchatcfig(Model model, HttpServletRequest request) {
      //查询全局微信公众号配置
	   WechatConfig config = wechatConfigMapper.selectByPrimaryKey(1);
	   model.addAttribute("config", config);
	  return "shop/editWchat";
  }
  
  /**
   * 保存全局公众号配置
   * 
   * @param shops
   * @param wechatConfig
   * @param request
   * @return
   */
  @RequestMapping("/editWechat")
  @ResponseBody
  public String editWechat(WechatConfig wechatConfig,HttpServletRequest request) {
    System.out.println("--------公众号全局修改开始 ------------------------");
    RetInfo ret = shopsService.editWchat(wechatConfig, request);

    return AppUtils.getReturnInfo(ret);
  }
  
}
