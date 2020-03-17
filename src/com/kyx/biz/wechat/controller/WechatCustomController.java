package com.kyx.biz.wechat.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customMeal.model.CustomMeal;
import com.kyx.biz.wechat.service.WechatCustomerService;
import com.kyx.biz.wechat.vo.LableValue;

@Controller
@RequestMapping(value = "/wechat/custom")
public class WechatCustomController extends BaseController {
  @Resource
  private CustomService customService;

  @Resource
  private WechatCustomerService wechatCustomerService;
  @Resource
  private ShopsService shopsService;

  /**
   * 注册
   * 
   * @param model
   * @return
   */
  @RequestMapping("/regIndex")
  public String index(Model model, HttpServletRequest request) {
    Map<Integer, String> map =
        (Map<Integer, String>) request.getSession().getAttribute(Shops.SHOP_LIST_SESSION);
    List<LableValue> list = shopsService.getShops(map);

    model.addAttribute("shops", JSON.toJSONString(list));

    return "custom/regCustom";
  }

  /**
   * 添加客户
   * 
   * @param custom
   * @param request
   * @return
   */
  @RequestMapping(value = "/saveCustom", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String saveCustom(Custom custom, HttpServletRequest request) {
    custom = setParam(custom, request);
    Dbs.setDbName(Dbs.getMainDbName());
    Shops shops = shopsService.getById(custom.getShopId());
    Dbs.setDbName(shops.getDbName());
    custom.setSex(0);
    RetInfo retInfo = customService.addCustom(custom);
    Dbs.setDbName(Dbs.getMainDbName());
    wechatCustomerService.addWechatCustomer(custom);
    request.getSession().setAttribute(BasicContant.CUSTOMER_SESSION, custom);
    Dbs.setDbName(shops.getDbName());
    request.getSession().setAttribute("currentDBName", shops.getDbName());
    return AppUtils.getReturnInfo(retInfo);
  }

  /**
   * 查询客户套餐
   * 
   * @param customRequest
   * @param request
   * @return
   */
  @RequestMapping("/mealIndex")
  public String queryCustomMeal(CustomRequest customRequest, HttpServletRequest request, Model model) {
    // customRequest.setShopId(getShopId(request));
    List<CustomMeal> meals = customService.queryWeChartCustomMeals(customRequest);
    model.addAttribute("meals", JSON.toJSONString(meals));

    return "custom/mealInfo";
  }

  private Custom setParam(Custom custom, HttpServletRequest request) {
    custom.setAppid(getAppId(request));
    custom.setOpenId(getOpenId(request));
    // custom.setShopId(getShopId(request));
    custom.setCustType(1);

    return custom;
  }


  /**
   * 以下为pc方便调试地址
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping("/regIndexs")
  public String regIndexs(Model model, HttpServletRequest request) {
    List<LableValue> list = Lists.newArrayList();
    LableValue l1 = new LableValue(1, "上海");
    LableValue l2 = new LableValue(2, "南京");
    list.add(l1);
    list.add(l2);
    model.addAttribute("shops", JSON.toJSONString(list));
    return "custom/regCustom";
  }
}
