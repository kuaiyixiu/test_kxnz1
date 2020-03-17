package com.kyx.biz.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.userloginlist.model.UserLoginList;
import com.kyx.basic.userloginlist.service.UserLoginListService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.Common;
import com.kyx.basic.util.RetInfo;
import com.kyx.basic.util.VerifyUtils;
import com.kyx.biz.app.service.MobileShopsService;
import com.kyx.biz.paytype.mapper.PayTypeMapper;
import com.kyx.biz.paytype.model.PayType;

/**
 * 
 * @author 严大灯
 * @data 2019-5-6 上午9:09:16
 * @Descripton
 */
@Controller
@RequestMapping(value = "/mobile/shop")
public class MobileShopsController {
  @Resource
  private UserService userService;
  @Resource
  private ShopsService shopsService;
  @Resource
  private PasswordEncoder passwordEncoder;
  @Resource
  private UserLoginListService userLoginListService;
  @Resource
  private MobileShopsService mobileShopsService;
  @Resource
  private PayTypeMapper payTypeMapper;

  @RequestMapping(value = "/test", method = RequestMethod.POST)
  @ResponseBody
  public String test(String params) throws Exception {
    System.out.println(params);
    if (VerifyUtils.verifySignature(params)) {
      return "验签通过";
    } else {
      return "验签失败";
    }
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(String params, HttpServletRequest request, HttpSession session) {
    Map paramMap = checkSignature(params);
    if (!(Boolean) paramMap.get("checkStatus"))
      return AppUtils.getReturnInfo(new RetInfo("error", "获取数据失败，没有权限！"));
    // 传入传入用户名，密码
    String username = (String) paramMap.get("userName");
    String password = (String) paramMap.get("passWord");

    // 验证用户账号与密码是否正确
    try {
      username = username.trim();
      if (Common.isEmpty(username) || Common.isEmpty(password)) {
        return AppUtils.getReturnInfo(new RetInfo("error", "用户名或密码不能为空！"));
      }
      // 根据用户名查询门店信息
      User user = userService.querySingleUser(username);
      if (user == null)
        return AppUtils.getReturnInfo(new RetInfo("error", "用户不存在！"));
      Shops shops = shopsService.getById(user.getShopId());
      List<User> userList = userService.getByShopId(shops.getId());
      setShopListSession(shops.getDbName(), request);
      // 先判断当前门店是否在运行
      if (!"0".equals(shops.getAccountStatus()) && !"1".equals(shops.getAccountStatus())) {// 门店已过期
        return AppUtils.getReturnInfo(new RetInfo("error", "门店已过期！"));
      }
      if (user == null || !passwordEncoder.matches(password, user.getUserPassword())) {
        return AppUtils.getReturnInfo(new RetInfo("error", "用户或密码不正确！"));
      }
      // 记录登录信息
      UserLoginList userLoginList = new UserLoginList();
      userLoginList.setUserId(user.getId());
      userLoginList.setLoginTime(new Date());
      userLoginList.setLoginIp(Common.toIpAddr(request));
      userLoginList.setShopId(shops.getId());
      userLoginListService.add(userLoginList);
      Map map = new HashMap();
      map.put("user", user);
      map.put("shops", shops);

      Dbs.setDbName(shops.getDbName());
      String amtStr = mobileShopsService.getFinishOrderAmt(shops.getId()); // 入账金额
      String[] amtArr = amtStr.split("-");
      map.put("yestdayAmt", amtArr[0]);
      map.put("todayAmt", amtArr[1]);

      PayType payType = new PayType();
      List<PayType> payTypeList = payTypeMapper.getPayTypeList(payType);
      map.put("payTypeList", payTypeList);

      RetInfo ret = new RetInfo("success", "登录成功！");
      ret.setRetDesc(JSONObject.toJSON(map).toString());
      ret.setRetData(userList);
      return AppUtils.getReturnInfo(ret);
    } catch (AuthenticationException e) {
      return AppUtils.getReturnInfo(new RetInfo("error", "登录异常，请联系管理员！"));
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

  /**
   * 新增订单
   * 
   * @param params
   * @param request
   * 
   */
  @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
  @ResponseBody
  public String addOrder(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.addOrder(checkSignature(params));

    return AppUtils.getReturnInfo(ret);
  }

  /**
   * 查询
   * 
   * @param customRequest
   * @return
   */
  @RequestMapping(value = "/queryCustomMeal", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCustomMeal(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCustomMeal(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 服务列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getServeList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getServeList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getServeList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 产品列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProductList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getProductList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getProductList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 车辆列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/queryCarList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCarList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCarList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 支付列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getPaymentList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getPaymentList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getPaymentList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 得到会员类型列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getCustomTypeList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getCustomTypeList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getCustomTypeList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 添加会员信息
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/addCustom", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String addCustom(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.addCustom(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 添加会员信息
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/updateCustom", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String updateCustom(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.updateCustom(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询车辆详情
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCarDetail", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCarDetail(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCarDetail(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 得到会员详情
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCustomDetail", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCustomDetail(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCustomDetail(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询客户可购买套餐列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryBuyMealList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryBuyMealList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryBuyMealList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 按照orderId查询订单详情
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryOrderDetailById", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryOrderDetailById(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryOrderDetailById(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询订单列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryOrderList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryOrderList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryOrderList(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询日常提醒
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryReminds", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryReminds(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryReminds(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询邀约提醒
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryInvitationServer", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryInvitationReminds(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryInvitationServer(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询车辆保险到期
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCarInsure", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCarInsure(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCarInsure(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询车辆车检到期
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCarCheck", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCarCheck(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCarCheck(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 根据车牌号查询最近一次订单
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryLastOrdersByCarNumber")
  @ResponseBody
  public String queryLastOrdersByCarNumber(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryLastOrdersByCarNumber(checkSignature(params));
    String foo = JSON.toJSONString(ret);
    System.out.println(JSON.toJSONString(ret));
    return JSON.toJSONString(ret);
  }

  /**
   * 更新订单
   * 
   * @param car
   * @return
   */
  @RequestMapping(value = "/submitOrders")
  @ResponseBody
  public String submitOrders(String params) {
    RetInfo ret = mobileShopsService.submitOrders(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 提交套餐
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/submitMeal")
  @ResponseBody
  public String submitMeal(String params) throws Exception {
    RetInfo ret = mobileShopsService.submitMeal(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时增加服务列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/addOrdersServe")
  @ResponseBody
  public String addOrdersServe(String params) {
    RetInfo ret = mobileShopsService.addOrdersServe(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时增加产品列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/addOrdersProduct")
  @ResponseBody
  public String addOrdersProduct(String params) throws Exception {
    RetInfo ret = mobileShopsService.addOrdersProduct(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时增加支付方式
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/addOrdersPay")
  @ResponseBody
  public String addOrdersPay(String params) {
    RetInfo ret = mobileShopsService.addOrdersPay(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时更新服务列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateOrdersServe")
  @ResponseBody
  public String updateOrdersServe(String params) throws Exception {
    RetInfo ret = mobileShopsService.updateOrdersServe(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时更新支付
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateOrdersPay")
  @ResponseBody
  public String updateOrdersPay(String params) throws Exception {
    RetInfo ret = mobileShopsService.updateOrdersPay(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时更新产品列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateOrdersProduct")
  @ResponseBody
  public String updateOrdersProduct(String params) throws Exception {
    RetInfo ret = mobileShopsService.updateOrdersProduct(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时删除服务列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delOrdersServe")
  @ResponseBody
  public String delOrdersServe(String params) {
    RetInfo ret = mobileShopsService.delOrdersServe(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时删除产品列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delOrdersProduct")
  @ResponseBody
  public String delOrdersProduct(String params) throws Exception {
    RetInfo ret = mobileShopsService.delOrdersProduct(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 实时删除支付
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delOrdersPay")
  @ResponseBody
  public String delOrdersPay(String params) throws Exception {
    RetInfo ret = mobileShopsService.delOrdersPay(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 购买会员套餐
   * 
   * @param params
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/saveCustomMeal")
  @ResponseBody
  public String saveCustomMeal(String params) {
    RetInfo ret = mobileShopsService.saveCustomMeal(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 根据二维码查询产品信息 传入参数：qrCode 二维码字符串 shopId 门店id
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProByCode")
  @ResponseBody
  public String getProByCode(String params) {
    RetInfo ret = mobileShopsService.getProByCode(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 获取产品分类列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProductClass")
  @ResponseBody
  public String getProductClass(String params) {
    RetInfo ret = mobileShopsService.getProductClass(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 保存或修改产品信息 修改产品二维码信息 productName classId price
   * 
   * @param params
   * @return
   */
  @RequestMapping("/saveProductData")
  @ResponseBody
  public String saveProductData(String params) {
    RetInfo retInfo = mobileShopsService.saveProductData(checkSignature(params));
    return AppUtils.getReturnInfo(retInfo);
  }

  /**
   * 盘点保存
   * 
   * @param params
   * @return
   */
  @RequestMapping("/savePDData")
  @ResponseBody
  public String savePDData(String params) {
    RetInfo retInfo = mobileShopsService.savePDData(checkSignature(params));
    return AppUtils.getReturnInfo(retInfo);
  }

  /**
   * 产品异动流水查询
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProductChange")
  @ResponseBody
  public String getProductChange(String params) {
    RetInfo ret = mobileShopsService.getProductChange(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 根据产品信息查询产品列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProduct")
  @ResponseBody
  public String getProduct(String params) {
    RetInfo ret = mobileShopsService.getProduct(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 新建采购单 或退货单 主表保存或者修改
   * 
   * @param params Repertory对象 kind 1入库单 2退货单
   * @return
   */
  @RequestMapping(value = "/saveRepertory")
  @ResponseBody
  public String saveRepertory(String params) {
    RetInfo ret = mobileShopsService.saveRepertory(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询供应商列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getSupply")
  @ResponseBody
  public String getSupply(String params) {
    RetInfo ret = mobileShopsService.getSupply(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 保存供应商
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/saveSupply")
  @ResponseBody
  public String saveSupply(String params) {
    RetInfo ret = mobileShopsService.saveSupply(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 保存采购明细或者退货明细
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/saveRepertoryDt")
  @ResponseBody
  public String saveRepertoryDt(String params) {
    RetInfo ret = mobileShopsService.saveRepertoryDt(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 删除采购明细或者退货明细
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delRepertoryDt")
  @ResponseBody
  public String delRepertoryDt(String params) {
    RetInfo ret = mobileShopsService.delRepertoryDt(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 保存采购或者退货付款方式
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/saveRepertoryPay")
  @ResponseBody
  public String saveRepertoryPay(String params) {
    RetInfo ret = mobileShopsService.saveRepertoryPay(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 删除采购或者退货付款方式
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delRepertoryPay")
  @ResponseBody
  public String delRepertoryPay(String params) {
    RetInfo ret = mobileShopsService.delRepertoryPay(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 获取入库单 出库单列表 明细
   * 
   * @param params
   * @return dtList 产品明细 payList 付款方式明细
   */
  @RequestMapping(value = "/getRepertoryDt")
  @ResponseBody
  public String getRepertoryDt(String params) {
    RetInfo ret = mobileShopsService.getRepertoryDt(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 获取库存列表 kind 1入库单 2退货单 repertoryStatus 0临单 2历史单3作废单
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getRepertory")
  @ResponseBody
  public String getRepertory(String params) {
    RetInfo ret = mobileShopsService.getRepertory(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 入库&挂账 必填字段 needPay hasPay payAll kind id
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateRepertory")
  @ResponseBody
  public String updateRepertory(String params) {
    RetInfo ret = mobileShopsService.updateRepertory(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 库存单据删除
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/delRepertory")
  @ResponseBody
  public String delRepertory(String params) {
    RetInfo ret = mobileShopsService.delRepertory(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 获取挂账列表 kind 2采购挂账 3退货挂账 enable 1
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getDebtRecord")
  @ResponseBody
  public String getDebtRecord(String params) {
    RetInfo ret = mobileShopsService.getDebtRecord(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 挂账还款 挂账id的对象
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateDebtRecord")
  @ResponseBody
  public String updateDebtRecord(String params) {
    RetInfo ret = mobileShopsService.updateDebtRecord(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 挂账批量还款
   * 
   * shopId 门店id ids 付款的挂账单id以;隔开 payId付款方式
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/updateBatchDebtRecord")
  @ResponseBody
  public String updateBatchDebtRecord(String params) {
    RetInfo ret = mobileShopsService.updateBatchDebtRecord(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 退货产品列表 available 1 拥有库存的产品 kind 1 shopId
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getReturnProduct")
  @ResponseBody
  public String getReturnProduct(String params) {
    RetInfo ret = mobileShopsService.getReturnProduct(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询客户预约
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCustomAppoints", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCustomAppoints(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCustomAppoints(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 添加提醒
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/addRemind", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String addRemind(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.addRemind(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 按照shopid查询用户
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryUserByShopId", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryUserByShopId(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryUserByShopId(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 获取库存盘点列表
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/getProductCheck")
  @ResponseBody
  public String getProductCheck(String params) {
    RetInfo ret = mobileShopsService.getProductCheck(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 保险到期列表（交强险，商业险）
   * 
   * @param params
   * @return
   */
  @RequestMapping(value = "/queryCarInsurance")
  @ResponseBody
  public String queryCarInsurance(String params) {
    RetInfo ret = mobileShopsService.queryCarInsurance(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 查询车辆保养到期
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCarMaintain", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCarMaintain(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCarMaintain(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 发送预约
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/sendAppoint", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String sendAppoint(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.sendAppoint(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  @RequestMapping(value = "/getRoyalty", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getByRoyaltyId(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getByRoyaltyId(checkSignature(params));
    return JSON.toJSONString(ret);
  }

  /**
   * 完成提醒
   * 
   * @param params
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/carryOutRemind", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String carryOutRemind(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.carryOutRemind(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 发送服务邀约
   * 
   * @param params
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/sendServer", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String sendServer(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.sendServer(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 保险邀约
   * 
   * @param params
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/sendCarInvitation", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String sendCarInvitation(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.sendCarInvitation(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 年检邀约
   * 
   * @param params
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/sendCarCheck", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String sendCarCheck(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.sendCarCheck(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 保养邀约
   * 
   * @param params
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/sendMaintain", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String sendMaintain(String params, HttpServletRequest request) throws Exception {
    RetInfo ret = mobileShopsService.sendMaintain(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 完成服务提醒
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/carryOutServer", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String carryOutServer(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.carryOutServer(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 完成预约
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/carryOutAppoint", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String carryOutAppoint(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.carryOutAppoint(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 完成车辆邀约
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/carryOutCar", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String carryOutCar(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.carryOutCar(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询客户列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryCustoms", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryCustoms(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryCustoms(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询全部代金券
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getAllCoupon", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getAllCoupon(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getAllCoupon(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询优惠券
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/chooseCardset", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String chooseCardset(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.chooseCardset(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 提交优惠券
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/submitCardset", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String submitCardset(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.submitCardset(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 查询全部代金券包
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getAllCouponPack", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getAllCouponPack(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getAllCouponPack(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 确认充值
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/saveRechargeData", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String saveRechargeData(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.saveRechargeData(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 提交代金券
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/submitCoupon", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String submitCoupon(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.submitCoupon(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 获取我发起的审批列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getAppInstanceList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getAppInstanceList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getAppInstanceList(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 获取待审批列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getAppCheckList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getAppCheckList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getAppCheckList(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * -
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getNodeList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getNodeList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getNodeList(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 获取审批历史列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getInstanceNodeList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getInstanceNodeList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getInstanceNodeList(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 获取workFlowInstance
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/selectWorkFlowInstanceById", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String selectWorkFlowInstanceById(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.selectWorkFlowInstanceById(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 提交审批
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/updateCheckNode", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String updateCheckNode(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.updateCheckNode(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 撤回审批
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/backInstance", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String backInstance(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.backInstance(checkSignature(params));

    return JSON.toJSONString(ret);
  }

  /**
   * 接单列表
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getBoxOrderList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getBoxOrderList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.getBoxOrderList(checkSignature(params));

    return JSON.toJSONString(ret);

  }

  /**
   * 师傅接单
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/workerAddOrder", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String workerAddOrder(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.workerAddOrder(checkSignature(params));

    return JSON.toJSONString(ret);

  }

  /**
   * 师傅开锁
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/workerOpenLock", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String workerOpenLock(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.workerOpenLock(checkSignature(params));

    return JSON.toJSONString(ret);

  }

  /**
   * 查询所有箱子
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/queryAllLockList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String queryAllLockList(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.queryAllLockList(checkSignature(params));

    return JSON.toJSONString(ret);

  }

  /**
   * 检查版本更新
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/taskCheckUpdate", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String taskCheckUpdate(String params, HttpServletRequest request) {
    RetInfo ret = mobileShopsService.taskCheckUpdate(checkSignature(params));

    return JSON.toJSONString(ret);

  }

  /**
   * 图片上传
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/file/upload", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String upload(String params, HttpServletRequest request) {

    RetInfo ret = mobileShopsService.uploadImage(checkSignature(params), request);

    return JSON.toJSONString(ret);
  }

  /**
   * 获取图片
   * 
   * @param params
   * @param request
   * @return
   */
  @RequestMapping(value = "/getImage", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public void getImage(String path, HttpServletResponse response) {

    mobileShopsService.getImage(path, response);
  }

  // 私有
  /**
   * 校验签名返回参数信息
   * 
   * @param params
   * @return
   * @throws Exception
   */
  private Map checkSignature(String params) {
    Map map = new HashMap();
    try {
      if (VerifyUtils.verifySignature(params)) {
        map = JSON.parseObject(params, new TypeReference<Map<String, String>>() {});
        map.put("checkStatus", true);
        String currentDBName = (String) map.get("currentDBName");
        if (StringUtils.isNotEmpty(currentDBName))
          Dbs.setDbName(currentDBName);
        else
          Dbs.setDbName(Dbs.getMainDbName());
      } else {
        map.put("checkStatus", false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      map.put("checkStatus", false);
    }
    return map;
  }
}
