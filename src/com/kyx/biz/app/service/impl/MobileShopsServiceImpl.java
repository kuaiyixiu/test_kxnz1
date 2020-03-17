package com.kyx.biz.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.FileTool;
import com.kyx.basic.util.FileUploadUtil;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.PropertiesUtil;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.app.AppVersion;
import com.kyx.biz.app.service.MobileShopsService;
import com.kyx.biz.appoint.service.AppointService;
import com.kyx.biz.box.model.BoxOrder;
import com.kyx.biz.box.service.BoxService;
import com.kyx.biz.car.mapper.CarMapper;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.coupon.mapper.CouponMapper;
import com.kyx.biz.coupon.mapper.CouponPackMapper;
import com.kyx.biz.coupon.model.Coupon;
import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.service.CouponService;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustCouponService;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customMeal.model.CustomMeal;
import com.kyx.biz.customType.mapper.CustomTypeMapper;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.debt.mapper.DebtRecordMapper;
import com.kyx.biz.debt.model.DebtRecord;
import com.kyx.biz.debt.service.DebtRecordService;
import com.kyx.biz.lock.model.LockInfo;
import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.meal.service.MealService;
import com.kyx.biz.mealdt.service.MealDtService;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersPayMapper;
import com.kyx.biz.orders.mapper.OrdersServeMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEnum;
import com.kyx.biz.orders.model.OrdersPay;
import com.kyx.biz.orders.model.OrdersProduct;
import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.orders.service.OrdersPayService;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.orders.service.OrdersServeService;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.paytype.mapper.PayTypeMapper;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.product.service.ProductChangeService;
import com.kyx.biz.product.service.ProductClassService;
import com.kyx.biz.product.service.ProductRepertoryService;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.recharge.service.impl.RechargeServiceImpl;
import com.kyx.biz.remind.model.Invitation;
import com.kyx.biz.remind.model.Remind;
import com.kyx.biz.remind.service.RemindService;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.repertory.model.RepertoryDt;
import com.kyx.biz.repertory.model.RepertoryPay;
import com.kyx.biz.repertory.service.RepertoryDtService;
import com.kyx.biz.repertory.service.RepertoryPayService;
import com.kyx.biz.repertory.service.RepertoryService;
import com.kyx.biz.repertorycheck.model.RepertoryCheck;
import com.kyx.biz.repertorycheck.service.RepertoryCheckService;
import com.kyx.biz.royalty.model.Royalty;
import com.kyx.biz.royalty.service.RoyaltyService;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.supply.model.Supply;
import com.kyx.biz.supply.service.SupplyService;
import com.kyx.biz.wechat.service.WechatSendService;
import com.kyx.biz.wechat.vo.TemplateDataVo;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;
import com.kyx.biz.workflow.model.WorkflowCheckUser;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowInstanceNode;
import com.kyx.biz.workflow.model.WorkflowNode;
import com.kyx.biz.workflow.service.WorkflowInstanceService;
import com.kyx.biz.workflow.service.WorkflowTemplateService;
import net.coobird.thumbnailator.Thumbnails;

@Service("mobileShopsService")
public class MobileShopsServiceImpl implements MobileShopsService {
	
  private Logger logger = LoggerFactory.getLogger(MobileShopsServiceImpl.class);
  @Resource
  private DebtRecordMapper debtRecordMapper;

  @Resource
  private UserService userService;

  @Resource
  private PayRecordMapper payRecordMapper;

  @Resource
  private OrdersPayMapper ordersPayMapper;

  @Resource
  private OrdersPayService ordersPayService;

  @Resource
  private OrdersServeMapper ordersServeMapper;

  @Resource
  private CarService carService;

  @Resource
  private CustomService customService;

  @Resource
  private OrdersService ordersService;

  @Resource
  private CustomTypeMapper customTypeMapper;

  @Resource
  private ServeMapper serveMapper;

  @Resource
  private ProductMapper productMapper;

  @Resource
  private CarMapper carMapper;

  @Resource
  private PayTypeMapper payTypeMapper;

  @Resource
  private CustomTypeService customTypeService;

  @Resource
  private MealService mealService;

  @Resource
  private PayTypeService payTypeService;

  @Resource
  private OrdersProductService ordersProductService;

  @Resource
  private OrdersServeService ordersServeService;

  @Resource
  private RemindService remindService;

  @Resource
  private OrdersMapper ordersMapper;

  @Resource
  private MealDtService mealDtService;

  @Resource
  private ProductService productService;
  @Resource
  private ProductClassService productClassService;
  @Resource
  private RepertoryCheckService repertoryCheckService;
  @Resource
  private ProductChangeService productChangeService;
  @Resource
  private SupplyService supplyService;
  @Resource
  private RepertoryService repertoryService;
  @Resource
  private RepertoryDtService repertoryDtService;
  @Resource
  private RepertoryPayService repertoryPayService;
  @Resource
  private DebtRecordService debtRecordService;
  @Resource
  private ProductRepertoryService productRepertoryService;

  @Resource
  private WechatSendService wechatSendService;

  @Resource
  private AppointService appointService;

  @Resource
  private RoyaltyService royaltyService;
  @Resource
  private CouponPackMapper couponPackMapper;
  @Resource
  private CouponMapper couponMapper;
  @Resource
  private RechargeServiceImpl rechargeServiceImpl;
  @Resource
  private CustCouponService custCouponService;
  @Resource
  private CouponService couponService;
  @Resource
  private WorkflowInstanceService workflowInstanceService;
  @Resource
  private WechatCardRecordService wechatCardRecordService;
  @Resource
  private WorkflowTemplateService workflowTemplateService;
  @Resource
  private BoxService boxService;

  // 未提醒
  private static final Integer NOT_REMINDED = 0;

  @Override
  public RetInfo addOrder(Map paramMap) {
    if (!(Boolean) paramMap.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    // carNumber= URLDecoder.decode(carNumber, "utf-8");
    // Shops shops = (Shops)
    // request.getSession().getAttribute("shopsSession");
    Orders orders = new Orders();
    Map map = new HashMap();
    Integer orderType = null;
    judgeCarNumber(orderType, orders, paramMap, map);

    orders.setOrderStatus(1); // 编辑

    ordersService.saveOrders(orders);
    map.put("orders", orders);// 订单信息

    RetInfo ret = new RetInfo("success", "获取成功！");
    // ret.setRetDesc(JSONObject.toJSON(map).toString());
    ret.setRetData(map);
    return ret;
  }

  /**
   * 设置会员类型中文名
   * 
   * @param custom
   * @return
   */
  private Custom setCustomType(Custom custom) {
    CustomType customType = customTypeMapper.selectByPrimaryKey(custom.getCustType());
    custom.setCustomTypeName(customType.getTypeName());

    return custom;
  }

  /**
   * 查询套餐
   * 
   * @param custom
   * @return
   */
  @Override
  public RetInfo queryCustomMeal(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    CustomRequest customRequest = new CustomRequest();

    String custId = String.valueOf(map.get("custId"));
    customRequest.setCustId(Integer.valueOf(custId));
    List<CustomMeal> list = customService.queryCustomMeals(customRequest);

    Map resultMap = new HashMap();
    resultMap.put("mealList", list);
    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());
    return ret;
  }

  /**
   * 服务列表
   * 
   * @param serve
   * @return
   */
  @Override
  public RetInfo getServeList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    Serve serve = JSON.parseObject((String) map.get("serve"), Serve.class);
    serve.setShopId(shopId);
    Map resultMap = new HashMap();
    resultMap.put("serveList", serveMapper.getServeList(serve));
    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  /**
   * 产品列表
   * 
   * @param product
   * @return
   */
  @Override
  public RetInfo getProductList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    Product product = JSON.parseObject((String) map.get("product"), Product.class);
    product.setShopId(shopId);
    Map resultMap = new HashMap();
    resultMap.put("productList", productMapper.getProductList(product));
    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryCarList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    String keyword = (String) map.get("keyword");

    String pagesStr = (String) map.get("page");
    Integer pages = Integer.valueOf(pagesStr);

    String limitStr = (String) map.get("limit");
    Integer limit = Integer.valueOf(limitStr);

    Map resultMap = new HashMap();
    Pager pager = new Pager();
    pager.setPage(pages);
    pager.setLimit(limit);
    List<Car> list = carService.selectCarInfo(keyword, shopId, pager);
    resultMap.put("carList", list);

    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  /**
   * 支付列表
   * 
   * @param custom
   * @return
   */
  @Override
  public RetInfo getPaymentList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    // String shopIdStr = (String) map.get("shopId");
    // Integer shopId = Integer.valueOf(shopIdStr);

    PayType payType = new PayType();
    // payType.setShopId(shopId);
    Map resultMap = new HashMap();
    resultMap.put("payTypeList", payTypeMapper.getPayTypeList(payType));
    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo getCustomTypeList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);

    Map resultMap = new HashMap();
    // TODO(daibin):是否要按照shopid 门店来查询
    resultMap.put("customTypeList", customTypeService.selectCustomTypes(null));
    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  // 增加会员
  @Override
  public RetInfo addCustom(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = customService.addCustom(setCustomParam(map));

    return ret;
  }

  // 更新会员信息
  @Override
  public RetInfo updateCustom(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Custom custom = JSON.parseObject((String) map.get("custom"), Custom.class);
    RetInfo ret = customService.updateCustom(custom);

    return ret;
  }

  @Override
  public RetInfo queryCarDetail(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Map resultMap = new HashMap();
    // 车辆详情
    String idStr = (String) map.get("id");
    Integer id = Integer.valueOf(idStr);
    Car carDetail = carService.selectCarById(id);
    transferCarDetail(carDetail, map);

    resultMap.put("carDetail", carDetail);

    // 保养记录
    Pager pager = getPager(map);
    GrdData carMaintainGrdData = carService.queryCarMaintain(id, pager);
    List<Object> carMaintainList = carMaintainGrdData.getData();
    resultMap.put("carMaintainList", carMaintainList);

    // 消费记录
    GrdData ordersGrd = carService.queryConsumeOrders(setOrdersParam(map), pager);
    List<Object> ordersList = ordersGrd.getData();
    resultMap.put("ordersList", ordersList);

    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryCustomDetail(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String idStr = (String) map.get("id");
    Integer id = Integer.valueOf(idStr);
    // 会员详情
    Map resultMap = new HashMap<>();
    Custom customDetail = customService.selectByPrimaryKey(id);
    resultMap.put("customDetail", customDetail);

    // 车辆信息
    List<Car> carList = carService.selectByCustomId(id);
    resultMap.put("carList", carList);

    // 代金券详情
    List<CustCoupon> couponList = custCouponService.selectByCustId(id);
    resultMap.put("couponList", couponList);

    // 购买套餐
    GrdData mealGrd = customService.queryCustomMeal(getCustomRequest(map));
    List<Object> meals = mealGrd.getData();
    resultMap.put("meals", meals);

    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryBuyMealList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Map resultMap = new HashMap<>();
    // 套餐列表
    List<Meal> mealList = mealService.queryAppBuyMeals(setMealParam());
    resultMap.put("mealList", mealList);

    // 支付列表
    String idStr = (String) map.get("shopId");
    List<PayType> payTypeList = payTypeService.getPayType(PayType.CUSTOMTYPE);
    resultMap.put("payTypeList", payTypeList);

    // 默认到期时间,如选择套餐则按照套餐内的到期时间来算 endTime
    resultMap.put("nextYear", AppUtils.getDayByYear(1));

    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryOrderDetailById(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Map resultMap = new HashMap<>();

    String orderIdStr = (String) map.get("orderId");
    Integer orderId = Integer.valueOf(orderIdStr);
    GrdData productResult = ordersProductService.queryProductsByOrderId(orderId);
    List<Object> products = productResult.getData();
    resultMap.put("products", products);

    GrdData serversResult = ordersServeService.queryServesByOrderId(orderId);
    List<Object> servers = serversResult.getData();
    resultMap.put("servers", servers);

    RetInfo ret = new RetInfo("success", "获取成功！");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryOrderList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Map resultMap = new HashMap<>();
    List<Orders> orderList = ordersService.queryOrders(getOrdersParam(map), getPager(map));
    resultMap.put("orderList", orderList);

    RetInfo ret = RetInfo.Success("获取成功");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryReminds(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    GrdData remindsGrd = remindService.queryOldReminds(setRemind(map), getPager(map));

    List<Object> remindList = remindsGrd.getData();

    RetInfo ret = RetInfo.Success("获取成功");
    ret.setRetData(remindList);

    return ret;
  }

  @Override
  public RetInfo queryInvitationServer(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Remind remind = setRemindParam(map);

    GrdData serverGrd = remindService.queryInvitationServer(remind);
    List<Object> serverList = serverGrd.getData();

    RetInfo ret = RetInfo.Success("获取成功");
    ret.setRetData(serverList);
    return ret;
  }

  @Override
  public RetInfo queryCarInsure(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Map resultMap = new HashMap<>();
    Pager pager = getPager(map);
    GrdData carGrd = carService.selectCarByInsure(null, pager);
    List<Object> carInsureList = carGrd.getData();
    resultMap.put("carInsureList", carInsureList);

    RetInfo ret = RetInfo.Success("获取成功");
    ret.setRetDesc(JSONObject.toJSON(resultMap).toString());

    return ret;
  }

  @Override
  public RetInfo queryLastOrdersByCarNumber(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Map resultMap = new HashMap<>();
    Orders orders = new Orders();
    if (StringUtils.isNotEmpty((CharSequence) map.get("orderId"))) {
      orders = ordersService.queryById(Integer.valueOf((String) map.get("orderId")));
    } else {
      orders = ordersService.queryLastOrdersByCarNumber(String.valueOf(map.get("carNumber")));
      // resultMap.put("orderStatus", orders.getOrderStatus());
      if (orders == null || orders.getOrderStatus() >= OrdersEnum.FINISH_WORK.getType()
          || map.get("kind").equals("1")) {
        Map addMap = new HashMap<>();
        addMap.put("kind", map.get("kind"));
        addMap.put("carNumber", map.get("carNumber"));
        addMap.put("shopId", map.get("shopId"));
        addMap.put("currentDBName", map.get("currentDBName"));
        addMap.put("checkStatus", map.get("checkStatus"));
        return this.addOrder(addMap);
      }
    }

    orders.setOrdersServeList(ordersServeService.queryListByOrderId(orders.getId()));
    orders.setOrdersProductList(ordersProductService.queryListByOrderId(orders.getId()));
    orders.setOrdersPayList(ordersPayService.queryListByOrderId(orders.getId()));

    Integer orderType = null;
    judgeCarNumber(orderType, orders, map, resultMap);

    Dbs.setDbName(Dbs.getMainDbName());
    Map<Integer, String> userMap =
        userService.getUserMap(Integer.valueOf((String) map.get("shopId")));
    for (OrdersServe os : orders.getOrdersServeList()) {
      if (os.getServeUser() != null) {
        os.setServeUserName(userMap.get(os.getServeUser()));
      }
      if (os.getSellUser() != null) {
        os.setSellUserName(userMap.get(os.getSellUser()));
      }
    }

    for (OrdersProduct op : orders.getOrdersProductList()) {
      if (op.getProductUser() != null) {
        op.setProductUserName(userMap.get(op.getProductUser()));
      }
    }

    resultMap.put("orders", orders);// 订单信息
    RetInfo ret = RetInfo.Success("获取成功");
    // ret.setRetDesc(JSONObject.toJSON(resultMap).toString());
    ret.setRetData(resultMap);

    return ret;
  }

  @Override
  public RetInfo submitMeal(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    // chooseMealInfo += dtId +"#"+itemId+"#"+quantity+",";
    String chooseMealInfo = (String) map.get("chooseMealInfo");
    String customIdStr = (String) map.get("customId");
    String orderIdStr = (String) map.get("orderId");
    String shopIdStr = (String) map.get("shopId");

    RetInfo ret =
        ordersService.submitMeal(chooseMealInfo, Integer.valueOf(customIdStr),
            Integer.valueOf(orderIdStr), Integer.valueOf(shopIdStr));
    Orders orders = (Orders) ret.getRetData();
    List<OrdersServe> serves = orders.getOrdersServeList();
    List<OrdersProduct> products = orders.getOrdersProductList();
    if (!ObjectUtils.isEmpty(serves)) {
      ordersServeService.saveOrdersServeList(serves, Integer.valueOf(orderIdStr));
    }

    if (!ObjectUtils.isEmpty(products)) {
      ordersProductService.saveOrdersProductList(products, Integer.valueOf(orderIdStr));
    }

    return ret;
  }

  @Override
  public RetInfo addOrdersServe(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String orderIdStr = (String) map.get("orderId");
    Integer orderId = Integer.valueOf(orderIdStr);
    List<OrdersServe> orderServiceList =
        JSON.parseObject((String) map.get("orderServiceList"),
            new TypeReference<List<OrdersServe>>() {});

    // 针对APP端，可能传入的服务数量>1，所以需要把orderServiceList拆分数量为1的list #tyg2019.0628
    List<OrdersServe> transList = new ArrayList<OrdersServe>();
    if (!ObjectUtils.isEmpty(orderServiceList)) {
      for (OrdersServe os : orderServiceList) {
        Integer quantity = os.getQuantity() == null ? 1 : os.getQuantity();
        for (int i = 0; i < quantity; i++) {
          os.setQuantity(1);
          transList.add(os);
        }
      }
    }

    RetInfo ret = ordersServeService.saveOrdersServeList(transList, orderId);
    // RetInfo ret = RetInfo.Success("添加成功");
    return ret;
  }

  @Override
  public RetInfo addOrdersProduct(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String orderIdStr = (String) map.get("orderId");
    Integer orderId = Integer.valueOf(orderIdStr);
    List<OrdersProduct> orderProductList =
        JSON.parseObject((String) map.get("orderProductList"),
            new TypeReference<List<OrdersProduct>>() {});
    RetInfo ret = ordersProductService.saveOrdersProductList(orderProductList, orderId);
    // RetInfo ret = RetInfo.Success("添加成功");
    return ret;
  }

  @Override
  public RetInfo addOrdersPay(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String orderIdStr = (String) map.get("orderId");
    Integer orderId = Integer.valueOf(orderIdStr);
    OrdersPay ordersPay = JSON.parseObject((String) map.get("ordersPay"), OrdersPay.class);
    List<OrdersPay> ordersPays = new ArrayList<OrdersPay>();
    ordersPays.add(ordersPay);
    RetInfo ret = ordersPayService.saveOrdersPayList(ordersPays, orderId);
    // RetInfo ret = RetInfo.Success("添加成功");
    return ret;
  }

  @Override
  public RetInfo updateOrdersServe(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    OrdersServe ordersServe = JSON.parseObject((String) map.get("ordersServe"), OrdersServe.class);
    RetInfo ret = ordersServeService.updateOrdersServe(ordersServe);
    return ret;
  }

  @Override
  public RetInfo updateOrdersProduct(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    OrdersProduct ordersProduct =
        JSON.parseObject((String) map.get("ordersProduct"), OrdersProduct.class);
    RetInfo ret = ordersProductService.updateOrdersProduct(ordersProduct);
    return ret;
  }

  @Override
  public RetInfo updateOrdersPay(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    OrdersPay ordersPay = JSON.parseObject((String) map.get("ordersPay"), OrdersPay.class);
    RetInfo ret = ordersPayService.updateOrdersPay(ordersPay);
    return ret;
  }

  @Override
  public RetInfo delOrdersServe(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = ordersServeService.delOrdersServe(Integer.valueOf((String) map.get("id")));
    return ret;
  }

  @Override
  public RetInfo delOrdersProduct(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = ordersProductService.delOrdersProduct(Integer.valueOf((String) map.get("id")));
    return ret;
  }

  @Override
  public RetInfo delOrdersPay(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = ordersPayService.delOrdersPay(Integer.valueOf((String) map.get("id")));
    return ret;
  }

  @Override
  public RetInfo submitOrders(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Map resultMap = new HashMap<>();
    Orders orders = JSON.parseObject((String) map.get("orders"), Orders.class);
    Integer operateType = Integer.valueOf((String) map.get("operateType"));
    if (orders.getId() == null) {
      return RetInfo.Error("订单编号不能为空");
    }
    Orders oldOrders = ordersMapper.selectByPrimaryKey(orders.getId());

    BigDecimal orderAmt = BigDecimal.ZERO;
    List<OrdersServe> ordersServeList = ordersServeService.queryListByOrderId(orders.getId());
    if (operateType == 3 || operateType == 4) {
      if (!ObjectUtils.isEmpty(ordersServeList)) {
        for (OrdersServe os : ordersServeList) {
          if (operateType == 4) {
            orderAmt = orderAmt.add(os.getPrice());
          }
          if (operateType == 3) { // 3施工中
            os.setServeStatus(2); // 施工中
            ordersServeMapper.updateByPrimaryKeySelective(os);
          } else if (operateType == 4) { // 4施工完成
            os.setServeStatus(3); // 施工完成
            os.setDoneTime(new Date());
            ordersServeMapper.updateByPrimaryKeySelective(os);
          }
        }
      }
    }
    // String optUserName=AppUtils.getOptUserName(request.getSession());
    // if (operateType == 2 ||operateType == 4){
    // List<OrdersProduct> ordersProductList =
    // ordersProductService.queryListByOrderId(request, orders.getId());
    // if (!ObjectUtils.isEmpty(ordersProductList)){
    // // List<ProductChange> productChanges=new ArrayList<ProductChange>();
    // for(OrdersProduct op : ordersProductList){
    // if (operateType == 2){ //异动产品数量
    //
    // // productRepertoryService.useProduct(op.getProductId(),
    // op.getQuantity(), oldOrders);
    // //更新产品异动记录
    // // Product pro = productMapper.selectByPrimaryKey(op.getProductId());
    // // ProductChange productChange=new ProductChange();
    // // productChange.setBeforeCount(pro.getQuantity());
    // // productChange.setCount(op.getQuantity());
    // // productChange.setAfterCount(pro.getQuantity()-op.getQuantity());
    // // productChange.setChangeReason(oldOrders.getCarNumber());
    // // productChange.setChangeTime(new Date());
    // // productChange.setOptUser(optUserName);
    // // productChange.setProductId(op.getProductId());
    // // productChange.setRelationId( orders.getId());
    // // productChange.setType(3);
    // // productChanges.add(productChange);
    // //
    // // Product p = new Product();
    // // p.setId(op.getProductId());
    // // p.setSubQuantity(op.getQuantity());
    // // productMapper.updateByPrimaryKeySelective(p);
    // }
    //
    // if (operateType == 4){
    // orderAmt = orderAmt.add(op.getPrice());
    // }
    // }
    //
    // // if (!ObjectUtils.isEmpty(productChanges))
    // // productChangeMapper.insertBatches(productChanges);
    // }
    // }
    //
    // if (operateType == 4){//4施工完成
    // orders.setOrderAmt(orderAmt);
    // orders.setPayAmount(orderAmt);
    // }

    if (operateType == 2) { // 2待施工（已提交）
      orders.setOrderStatus(2);
      orders.setSubmitTime(new Date());
    } else if (operateType == 3) { // 3施工中
      orders.setOrderStatus(3);
      orders.setDoingTime(new Date());
    } else if (operateType == 4) { // 4施工完成
      orders.setOrderStatus(4);
      orders.setDoneTime(new Date());
    } else if (operateType == 5 || operateType == 6) {// 5入账 6挂账
      orders.setOrderStatus(operateType);
      orders.setFinishTime(new Date());
    } else if (operateType == 7) { // 7解除入账/解除挂账
      if (oldOrders.getOrderStatus() == 5)
        orders.setOrderStatus(7); // 反入账
      else if (oldOrders.getOrderStatus() == 6)
        orders.setOrderStatus(8);// 8反挂账
    }

    int count = ordersMapper.updateByPrimaryKeySelective(orders);

    if (operateType == 5 || operateType == 6) {// 5入账 6挂账
      BigDecimal newPayAmt = new BigDecimal("0"); // 本次新还的金额
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      BigDecimal payedAmt = new BigDecimal("0");
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        for (OrdersPay op : ordersPayList) {
          if (op.getCanEdit() == 1) { // 可编辑，说明是新增的
            PayRecord payRecord = new PayRecord();
            payRecord.setTypeId(op.getPayId());
            payRecord.setAmount(op.getPayAmount());
            payRecord.setRemark(op.getRemark());
            if (oldOrders.getOrderStatus() == 5 || oldOrders.getOrderStatus() == 6) { // 挂账，记账的时候还款
              payRecord.setKind(4);// 4客户挂账还款（收入）
            } else {
              payRecord.setKind(1); // 1客户订单支付
            }
            payRecord.setSourceId(op.getOrderId());
            payRecord.setShopId(op.getShopId());
            payRecordMapper.insertSelective(payRecord);
            op.setCanEdit(0); // 结算后不能编辑
            ordersPayMapper.updateByPrimaryKeySelective(op);
            newPayAmt = newPayAmt.add(op.getPayAmount());
          }
          payedAmt = payedAmt.add(op.getPayAmount());
        }
      }

      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord == null) { // 之前没有挂账记录
        if (oldOrders.getPayAmount().compareTo(payedAmt) == 1) { // 订单支付金额
          // >实际支付金额
          // 生成挂账表
          debtRecord = new DebtRecord();
          debtRecord.setKind(1); // 1客户订单挂账
          debtRecord.setSourceId(orders.getId());
          debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setReturnAmount(BigDecimal.ZERO);
          debtRecord.setLeftAmount(oldOrders.getPayAmount().subtract(payedAmt));
          debtRecord.setEnable(1);
          debtRecordMapper.insertSelective(debtRecord);
        }
      } else {
        debtRecord.setDebtAmount(oldOrders.getPayAmount().subtract(payedAmt));// 剩余欠款
        debtRecord.setReturnAmount(debtRecord.getReturnAmount().add(newPayAmt)); // 当前还款+本次还款
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }
    }

    if (operateType == 7) { // 7解除入账/解除挂账
      List<OrdersPay> ordersPayList = ordersPayService.queryListByOrderId(orders.getId());
      if (!ObjectUtils.isEmpty(ordersPayList)) {
        for (OrdersPay op : ordersPayList) {
          PayRecord payRecord = new PayRecord();
          payRecord.setTypeId(op.getPayId());
          payRecord.setAmount(op.getPayAmount());
          payRecord.setRemark(op.getRemark());
          payRecord.setKind(8); // 8订单反入账或反挂账（支出）
          payRecord.setSourceId(op.getOrderId());
          payRecord.setShopId(op.getShopId());
          payRecordMapper.insertSelective(payRecord);
          op.setCanEdit(1); // 解除挂账/入账后 可以编辑了
          ordersPayMapper.updateByPrimaryKeySelective(op);
        }
      }
      DebtRecord debtRecord = debtRecordMapper.selectBySourceIdAndKind(orders.getId(), 1);
      if (debtRecord != null) {
        debtRecord.setEnable(0);
        debtRecordMapper.updateByPrimaryKeySelective(debtRecord);
      }

      // productRepertoryService.returnProduct(oldOrders);

      /*
       * List<OrdersProduct> ordersProductList = ordersProductService.queryListByOrderId(request,
       * orders.getId()); if (!ObjectUtils.isEmpty(ordersProductList)){ List<ProductChange>
       * productChanges=new ArrayList<ProductChange>(); for(OrdersProduct op : ordersProductList){
       * //更新产品异动记录 Product pro = productMapper.selectByPrimaryKey(op.getProductId()); ProductChange
       * productChange=new ProductChange(); productChange.setBeforeCount(pro.getQuantity());
       * productChange.setCount(op.getQuantity());
       * productChange.setAfterCount(pro.getQuantity()-op.getQuantity());
       * productChange.setChangeReason(oldOrders.getCarNumber()); productChange.setChangeTime(new
       * Date()); productChange.setOptUser(optUserName);
       * productChange.setProductId(op.getProductId()); productChange.setRelationId(
       * orders.getId()); productChange.setType(3); productChanges.add(productChange);
       * 
       * Product p = new Product(); p.setId(op.getProductId()); p.setAddQuantity(op.getQuantity());
       * productMapper.updateByPrimaryKeySelective(p); }
       * 
       * if (!ObjectUtils.isEmpty(productChanges))
       * productChangeMapper.insertBatches(productChanges); }
       */

    }

    RetInfo ret = RetInfo.Success("操作成功");
    ret.setRetDesc("操作成功");

    return ret;
  }

  @Override
  public RetInfo saveCustomMeal(Map map) {
    CustomRequest customRequest = setSaveCustom(map);
    RetInfo retInfo = customService.saveCustomMeal(customRequest);

    return retInfo;
  }

  /**
   * 
   * @param map
   * @return
   */
  private CustomRequest setSaveCustom(Map map) {
    CustomRequest request = new CustomRequest();
    String mealStr = (String) map.get("mealId");
    request.setMealId(Integer.valueOf(mealStr));

    String price = (String) map.get("price");
    BigDecimal priceBig = new BigDecimal(price);
    request.setPrice(priceBig);

    String typeIdStr = (String) map.get("typeId");
    request.setTypeId(Integer.valueOf(typeIdStr));

    String endDateStr = (String) map.get("endDate");
    Date endDate = AppUtils.string2Date1(endDateStr);
    request.setEndDate(endDate);

    String custIdStr = (String) map.get("custId");
    request.setCustId(Integer.valueOf(custIdStr));

    // [{"id":"1","quantity":"2"},{"id":"2","quantity":"10"}]
    String mealDts = (String) map.get("mealDts");
    request.setMealDts(mealDts);

    return request;
  }

  // 私有
  private void judgeCarNumber(Integer orderType, Orders orders, Map map, Map resultMap) {
    // 传入开单类型，车牌号
    String kindStr = (String) map.get("kind");
    Integer kind = Integer.valueOf(kindStr);
    String carNumber = (String) map.get("carNumber");
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    if (kind == 1) { // 临牌开单
      orderType = 0;// 临牌开单
    } else { // 车牌开单
      Car car = carService.selectByCarNo(carNumber);
      if (car == null) { // 不存在车辆信息，就新增
        car = new Car();
        car.setCarType(Car.DEFAULT);
        car.setCarNumber(carNumber);
        carService.saveCar(car);
        orderType = 1;// 非会员开单
        resultMap.put("car", car);// 车辆信息
      } else {
        if (car.getCustomId() == null) {
          orderType = 1;// 非2会员开单
        } else {
          Custom custom = customService.selectByPrimaryKey(car.getCustomId());
          orders.setCustId(car.getCustomId()); // 设置订单的会员卡信息
          orderType = 2;// 2会员开单
          custom = setCustomType(custom);
          resultMap.put("custom", custom);// 会员信息
        }
      }
      orders.setCarNumber(carNumber);// 设置订单的车辆编号
    }
    orders.setShopId(shopId);
    orders.setOrderType(orderType); // 订单类型
  }

  private Car getCheckCar(Map map) {
    Car car = new Car();

    if (!ObjectUtils.isEmpty(map.get("keyword"))) {
      String keyword = (String) map.get("keyword");
      car.setKeyword(keyword);
    }

    return car;
  }

  private Remind setRemindParam(Map map) {
    Remind remind = new Remind();
    remind.setStatus(BasicContant.UN_USED);
    String shopId = (String) map.get("shopId");
    remind.setShopId(Integer.valueOf(shopId));

    return remind;
  }

  private Remind setRemind(Map map) {
    Remind remind = new Remind();
    String oldDateStr = (String) map.get("oldDate");
    remind.setOldDate(AppUtils.string2Date1(oldDateStr));

    remind.setRemindType(Remind.NORMAL_USING);
    remind.setRemindStatus(NOT_REMINDED);

    return remind;
  }

  private Orders getOrdersParam(Map map) {
    Orders orders = new Orders();
    if (!ObjectUtils.isEmpty(map.get("carNumber"))) {
      String carNumber = (String) map.get("carNumber");
      orders.setCarNumber(carNumber);
    }

    if (!ObjectUtils.isEmpty(map.get("orderStatus"))) {
      String orderStr = (String) map.get("orderStatus");
      Integer orderStatus = Integer.valueOf(orderStr);
      orders.setOrderStatus(orderStatus);
    }

    return orders;
  }

  private Meal setMealParam() {
    Meal meal = new Meal();
    meal.setStatus(AppUtils.USING);

    return meal;
  }

  private Custom setCustomParam(Map map) {
    Custom custom = new Custom();
    String custName = (String) map.get("custName");
    custom.setCustName(custName);

    String custType = (String) map.get("custType");
    custom.setCustType(Integer.valueOf(custType));

    String cellphone = (String) map.get("cellphone");
    custom.setCellphone(cellphone);

    String birthdayStr = (String) map.get("birthdayStr");
    if (!StringUtils.isEmpty(birthdayStr)) {
      custom.setBirthday(AppUtils.string2Date1(birthdayStr));
    }

    String sex = (String) map.get("sex");
    custom.setSex(Integer.valueOf(sex));

    String remark = (String) map.get("remark");
    if (!StringUtils.isEmpty(remark)) {
      custom.setRemark(remark);
    }

    String carDataList = (String) map.get("carDataList");
    if (!StringUtils.isEmpty(carDataList)) {
      custom.setCarDataList(carDataList);
    }

    String shopId = (String) map.get("shopId");
    custom.setShopId(Integer.valueOf(shopId));

    return custom;
  }

  private Orders setOrdersParam(Map map) {
    Orders orders = new Orders();

    String shopIdStr = (String) map.get("shopId");
    orders.setShopId(Integer.valueOf(shopIdStr));

    String carNumber = (String) map.get("carNumber");
    orders.setCarNumber(carNumber);

    return orders;
  }

  private Pager getPager(Map map) {
    String pagesStr = (String) map.get("page");
    Integer pages = Integer.valueOf(pagesStr);

    String limitStr = (String) map.get("limit");
    Integer limit = Integer.valueOf(limitStr);

    Pager pager = new Pager();
    pager.setPage(pages);
    pager.setLimit(limit);
    return pager;
  }

  private CustomRequest getCustomRequest(Map map) {
    CustomRequest request = new CustomRequest();
    String id = (String) map.get("id");
    request.setCustId(Integer.valueOf(id));

    return request;
  }

  /**
   * 转换car信息设置会员开卡类型
   * 
   * @param car
   * @return
   */
  private void transferCarDetail(Car car, Map map) {
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    CustomType customType = new CustomType();
    customType.setShopId(shopId);
    List<CustomType> list = customTypeService.selectCustomTypes(customType);

    CustomType type = customTypeService.selectByPrimaryKey(car.getCustType());

    car.setCustTypeName(type.getTypeName());
  }

  @Override
  public RetInfo getProByCode(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("获取成功");
    Product product = JSON.parseObject((String) map.get("product"), Product.class);
    List<Product> list = productService.getProductList(product);
    if (list.size() > 0) {
      product = list.get(0);
      ret.setRetData(product);
    } else {
      ret.setRetData(new Product());
    }

    return ret;
  }

  @Override
  public RetInfo getProductClass(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    ProductClass productClass =
        JSON.parseObject((String) map.get("productClass"), ProductClass.class);
    GrdData grdData = productClassService.getInfo(productClass, pager);
    List<Object> classList = grdData.getData();
    if (classList == null)
      classList = new ArrayList<Object>();
    ret.setRetData(classList);
    return ret;
  }

  @Override
  public RetInfo saveProductData(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Product product = JSON.parseObject((String) map.get("product"), Product.class);
    ret = productService.saveData(product);
    return ret;
  }

  @Override
  public RetInfo savePDData(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    RepertoryCheck repertoryCheck =
        JSON.parseObject((String) map.get("repertoryCheck"), RepertoryCheck.class);
    ret = repertoryCheckService.savePDData(repertoryCheck);
    return ret;
  }

  @Override
  public RetInfo getProductChange(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    ProductChange productChange =
        JSON.parseObject((String) map.get("productChange"), ProductChange.class);
    GrdData grdData = productChangeService.getInfo(productChange, pager);
    List<Object> classList = grdData.getData();
    if (classList == null)
      classList = new ArrayList<Object>();
    ret.setRetData(classList);
    return ret;
  }

  @Override
  public RetInfo getProduct(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    Product product = JSON.parseObject((String) map.get("product"), Product.class);
    GrdData grdData = productService.getInfo(product, pager);
    List<Object> classList = grdData.getData();
    if (classList == null)
      classList = new ArrayList<Object>();
    ret.setRetData(classList);
    return ret;
  }

  @Override
  public RetInfo getSupply(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    // Pager pager = getPager(map);
    Supply supply = JSON.parseObject((String) map.get("supply"), Supply.class);
    List<Supply> list = supplyService.getInfo(supply);
    ret.setRetData(list);
    return ret;
  }

  @Override
  public RetInfo saveRepertory(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Repertory repertory = JSON.parseObject((String) map.get("repertory"), Repertory.class);
    ret = repertoryService.save(repertory);
    return ret;
  }

  @Override
  public RetInfo saveSupply(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Supply supply = JSON.parseObject((String) map.get("supply"), Supply.class);
    ret = supplyService.saveData(supply);
    return ret;
  }

  @Override
  public RetInfo saveRepertoryDt(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    List<RepertoryDt> dtList =
        JSONArray.parseArray((String) map.get("repertoryDtList"), RepertoryDt.class);
    // RepertoryDt
    // repertoryDt=JSON.parseObject((String)map.get("repertoryDt"),
    // RepertoryDt.class);
    ret = repertoryDtService.saveBatch(dtList);
    return ret;
  }

  @Override
  public RetInfo delRepertoryDt(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    RepertoryDt repertoryDt = JSON.parseObject((String) map.get("repertoryDt"), RepertoryDt.class);
    ret = repertoryDtService.delData(repertoryDt.getId());
    return ret;
  }

  @Override
  public RetInfo saveRepertoryPay(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    RepertoryPay repertoryPay =
        JSON.parseObject((String) map.get("repertoryPay"), RepertoryPay.class);
    ret = repertoryPayService.saveData(repertoryPay);
    return ret;
  }

  @Override
  public RetInfo delRepertoryPay(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    RepertoryPay repertoryPay =
        JSON.parseObject((String) map.get("repertoryPay"), RepertoryPay.class);
    ret = repertoryPayService.delData(repertoryPay.getId());
    return ret;
  }

  @Override
  public RetInfo getRepertory(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Repertory repertory = JSON.parseObject((String) map.get("repertory"), Repertory.class);
    Pager pager = getPager(map);
    GrdData grdData = repertoryService.getInfo(repertory, pager);
    List<Object> repertoryList = grdData.getData();
    if (repertoryList == null)
      repertoryList = new ArrayList<Object>();
    ret.setRetData(repertoryList);
    return ret;
  }

  @Override
  public RetInfo getRepertoryDt(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Repertory repertory = JSON.parseObject((String) map.get("repertory"), Repertory.class);
    RepertoryDt dt = new RepertoryDt();
    dt.setRepertory(repertory.getId());
    List<RepertoryDt> repertoryDtList = repertoryDtService.getList(dt);
    RepertoryPay repertoryPay = new RepertoryPay();
    repertoryPay.setRepertoryId(repertory.getId());
    List<Object> payList = repertoryPayService.getInfo(repertoryPay, null).getData();
    Map resultMap = new HashMap();
    resultMap.put("dtList", repertoryDtList);
    resultMap.put("payList", payList);
    ret.setRetData(resultMap);
    return ret;
  }

  @Override
  public RetInfo updateRepertory(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Repertory repertory = JSON.parseObject((String) map.get("repertory"), Repertory.class);
    ret = repertoryService.saveRepertoryin(repertory);
    return ret;
  }

  @Override
  public RetInfo delRepertory(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    String id = (String) map.get("id");
    ret = repertoryService.delData(id);
    return ret;
  }

  @Override
  public RetInfo getDebtRecord(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    DebtRecord debtRecord = JSON.parseObject((String) map.get("debtRecord"), DebtRecord.class);
    BigDecimal amount = debtRecordService.getSumDebtAmount(debtRecord);
    Pager pager = getPager(map);
    GrdData grdData = debtRecordService.getInfo(debtRecord, pager);
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    Map retMap = new HashMap();
    retMap.put("list", list);
    retMap.put("amount", amount);
    ret.setRetData(retMap);
    return ret;
  }

  @Override
  public RetInfo updateDebtRecord(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    DebtRecord debtRecord = JSON.parseObject((String) map.get("debtRecord"), DebtRecord.class);
    ret = debtRecordService.saveReturnDebt(debtRecord);
    return ret;
  }

  @Override
  public RetInfo updateBatchDebtRecord(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    String shopId = (String) map.get("shopId");
    String ids = (String) map.get("ids");
    String payId = (String) map.get("payId");
    ret = debtRecordService.saveReturnDebt(Integer.valueOf(payId), ids, Integer.valueOf(shopId));
    return ret;
  }

  @Override
  public RetInfo getReturnProduct(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    ProductRepertory productRepertory =
        JSON.parseObject((String) map.get("productRepertory"), ProductRepertory.class);
    GrdData grdData = productRepertoryService.getInfo(productRepertory, pager);
    List<Object> repertoryList = grdData.getData();
    if (repertoryList == null)
      repertoryList = new ArrayList<Object>();
    ret.setRetData(repertoryList);
    return ret;
  }

  @Override
  public RetInfo queryCustomAppoints(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    RetInfo ret = RetInfo.Success("请求成功");

    GrdData result = appointService.queryCustomAppoints();
    List<Object> repertoryList = result.getData();
    if (repertoryList == null)
      repertoryList = new ArrayList<Object>();
    ret.setRetData(repertoryList);

    return ret;
  }

  private Integer getShopId(Map map) {
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);

    return shopId;
  }

  @Override
  public RetInfo addRemind(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Remind remind = JSON.parseObject((String) map.get("remind"), Remind.class);
    RetInfo retInfo = remindService.addRemind(remind);

    return retInfo;
  }

  @Override
  public RetInfo queryUserByShopId(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    RetInfo retInfo = RetInfo.Success("查询成功");
    Integer shopId = getShopId(map);
    List<User> shopList = userService.getByShopId(shopId);
    retInfo.setRetData(shopList);

    return retInfo;
  }

  @Override
  public RetInfo getProductCheck(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    RepertoryCheck repertoryCheck =
        JSON.parseObject((String) map.get("repertoryCheck"), RepertoryCheck.class);
    GrdData grdData = repertoryCheckService.getAppInfo(repertoryCheck, pager);
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    ret.setRetData(list);
    return ret;
  }

  @Override
  public RetInfo queryCarInsurance(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Remind remind = JSON.parseObject((String) map.get("remind"), Remind.class);
    List<Invitation> list = remindService.queryInvitationCarList(remind);
    List<Invitation> lists = transferInsuranceList(list);

    ret.setRetData(lists);
    return ret;
  }

  /**
   * 交强险或者商业险
   * 
   * @param list
   * @return
   */
  private List<Invitation> transferInsuranceList(List<Invitation> list) {
    List<Invitation> lists = new ArrayList<>();
    for (Invitation invitation : list) {
      if (invitation.getIsCommercialDate() || invitation.getIsCompulsoryDate()) {
        lists.add(invitation);
      }
    }

    return lists;
  }

  @Override
  public RetInfo queryCarCheck(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Remind remind = JSON.parseObject((String) map.get("remind"), Remind.class);
    List<Invitation> list = remindService.queryInvitationCarList(remind);
    List<Invitation> lists = transferCheckList(list);

    ret.setRetData(lists);
    return ret;
  }

  private List<Invitation> transferCheckList(List<Invitation> list) {
    List<Invitation> lists = new ArrayList<>();
    for (Invitation invitation : list) {
      if (invitation.getIsCheckDate()) {
        lists.add(invitation);
      }
    }

    return lists;
  }

  @Override
  public RetInfo queryCarMaintain(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Remind remind = JSON.parseObject((String) map.get("remind"), Remind.class);
    List<Invitation> list = remindService.queryInvitationCarList(remind);
    List<Invitation> lists = transferMaintainList(list);

    ret.setRetData(lists);
    return ret;
  }

  private List<Invitation> transferMaintainList(List<Invitation> list) {
    List<Invitation> lists = new ArrayList<>();
    for (Invitation invitation : list) {
      if (invitation.getIsMaintainDate()) {
        lists.add(invitation);
      }
    }

    return lists;
  }

  @Override
  public RetInfo sendAppoint(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    TemplateDataVo tplDataVo = getTemplateDataVo(map);
    String cardNo = (String) map.get("cardNo");
    Integer shopId = getShopId(map);
    RetInfo ret = wechatSendService.sendAppointInfo(tplDataVo, cardNo, shopId);

    return ret;
  }

  @Override
  public String getFinishOrderAmt(Integer shopId) {
    // Integer shopId = Integer.valueOf((String) map.get("shopId"));
    // 昨日
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date dateRangeStartTime = cal.getTime();

    cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    Date dateRangeEndTime = cal.getTime();
    BigDecimal yestDayOrderAmt =
        ordersService.getFinishOrderAmt(shopId, dateRangeStartTime, dateRangeEndTime);

    // 今日
    cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    dateRangeStartTime = cal.getTime();

    cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    dateRangeEndTime = cal.getTime();
    BigDecimal toDayOrderAmt =
        ordersService.getFinishOrderAmt(shopId, dateRangeStartTime, dateRangeEndTime);

    return yestDayOrderAmt.toString() + "-" + toDayOrderAmt.toString();
  }

  @Override
  public RetInfo getByRoyaltyId(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Royalty royalty = JSON.parseObject((String) map.get("royalty"), Royalty.class);
    if (StringUtils.isBlank(royalty.getKind()) || royalty.getRoyaltyId() == null) {
      return new RetInfo("error", "提成查询条件有误！");
    }
    List<Royalty> list = royaltyService.getList(royalty);

    if ("12".equals(royalty.getKind())) {
      if (ObjectUtils.isEmpty(list)) {
        Royalty r = new Royalty();
        r.setKind("1");
        r.setRoyaltyCount(BigDecimal.ZERO);
        list.add(r);

        r = new Royalty();
        r.setKind("2");
        r.setRoyaltyCount(BigDecimal.ZERO);
        list.add(r);
      } else if (list.size() == 1) {
        Royalty r = list.get(0);
        if ("1".equals(r.getKind())) {
          Royalty r1 = new Royalty();
          r1.setKind("2");
          r1.setRoyaltyCount(BigDecimal.ZERO);
          list.add(r1);
        } else {
          Royalty r1 = new Royalty();
          r1.setKind("1");
          r1.setRoyaltyCount(BigDecimal.ZERO);
          list.add(r1);
        }
      }
    } else {
      if (ObjectUtils.isEmpty(list)) {
        Royalty r = new Royalty();
        r.setKind("3");
        r.setRoyaltyCount(BigDecimal.ZERO);
        list.add(r);
      }

    }
    ret.setRetData(list);
    return ret;
  }

  private TemplateDataVo getTemplateDataVo(Map map) {
    TemplateDataVo tplDataVo =
        JSON.parseObject((String) map.get("templateDataVo"), TemplateDataVo.class);
    // new TemplateDataVo();
    // String first = (String) map.get("first");
    // tplDataVo.setFirst(first);
    //
    // String keyword1 = (String) map.get("keyword1");
    // tplDataVo.setKeyword1(keyword1);
    //
    // String keyword2 = (String) map.get("keyword2");
    // tplDataVo.setKeyword2(keyword2);
    //
    // String keyword3 = (String) map.get("keyword3");
    // tplDataVo.setKeyword3(keyword3);
    //
    // String keyword4 = (String) map.get("keyword4");
    // tplDataVo.setKeyword4(keyword4);
    //
    // String keyword5 = (String) map.get("keyword5");
    // tplDataVo.setKeyword5(keyword5);
    //
    // String remark = (String) map.get("remark");
    // tplDataVo.setRemark(remark);

    return tplDataVo;
  }

  @Override
  public RetInfo carryOutRemind(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    String idStr = (String) map.get("id");
    Integer id = Integer.valueOf(idStr);
    RetInfo retInfo = remindService.carryOutRemind(id);

    return retInfo;
  }

  @Override
  public RetInfo sendServer(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    TemplateDataVo tplDataVo = getTemplateDataVo(map);
    String cardNo = (String) map.get("cardNo");
    Integer shopId = getShopId(map);
    RetInfo ret = wechatSendService.sendServer(tplDataVo, shopId, cardNo);

    return ret;
  }

  @Override
  public RetInfo sendCarInvitation(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Invitation invitation = JSON.parseObject((String) map.get("invitation"), Invitation.class);
    invitation.setIsCheckDate(false);
    invitation.setIsMaintainDate(false);
    Integer shopId = getShopId(map);
    RetInfo ret = wechatSendService.sendCarInvitation(invitation, shopId);

    return ret;
  }

  @Override
  public RetInfo sendCarCheck(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Invitation invitation = JSON.parseObject((String) map.get("invitation"), Invitation.class);
    Integer shopId = getShopId(map);
    invitation.setShopId(shopId);
    RetInfo ret = wechatSendService.sendCarCheck(invitation, shopId);

    return ret;
  }

  @Override
  public RetInfo sendMaintain(Map map) throws Exception {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Invitation invitation = JSON.parseObject((String) map.get("invitation"), Invitation.class);
    Integer shopId = getShopId(map);
    invitation.setShopId(shopId);
    RetInfo ret = wechatSendService.sendMaintain(invitation, shopId);

    return ret;
  }

  @Override
  public RetInfo carryOutServer(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Integer id = getIntegerByStr(map, "id");
    RetInfo retInfo = remindService.carryOutServer(id);

    return retInfo;
  }

  private Integer getIntegerByStr(Map map, String name) {
    String str = (String) map.get(name);
    Integer integer = Integer.valueOf(str);

    return integer;
  }

  @Override
  public RetInfo carryOutAppoint(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    Integer id = getIntegerByStr(map, "id");
    RetInfo retInfo = appointService.carryOutAppoint(id);

    return retInfo;
  }

  @Override
  public RetInfo carryOutCar(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    Invitation invitation = JSON.parseObject((String) map.get("invitation"), Invitation.class);
    RetInfo retInfo = remindService.carryOutCar(invitation);

    return retInfo;
  }

  @Override
  public RetInfo queryCustoms(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    Custom custom = JSON.parseObject((String) map.get("custom"), Custom.class);
    GrdData grdData = customService.queryCustoms(custom, pager);
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    ret.setRetData(list);

    return ret;
  }

  @Override
  public RetInfo getAllCoupon(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Coupon coupon = new Coupon();
    coupon.setStatue(1);
    List<Coupon> list = couponMapper.getList(coupon);
    ret.setRetData(list);
    return ret;
  }

  @Override
  public RetInfo getAllCouponPack(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    CouponPack couponPack = new CouponPack();
    List<CouponPack> list = couponPackMapper.getList(couponPack);
    if (list == null)
      list = new ArrayList<CouponPack>();
    ret.setRetData(list);

    return ret;
  }

  @Override
  public RetInfo saveRechargeData(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Recharge recharge = JSON.parseObject((String) map.get("recharge"), Recharge.class);
    ret = rechargeServiceImpl.saveRechargeData(recharge);
    return ret;
  }

  @Override
  public RetInfo submitCoupon(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    CustCoupon custCoupon = JSON.parseObject((String) map.get("custCoupon"), CustCoupon.class);

    ret =
        couponService.submitCoupon(String.valueOf(custCoupon.getCouponId()), custCoupon
            .getSubAvailAmount().toString(), custCoupon.getCustId(), custCoupon.getSourceId());
    return ret;
  }

  @Override
  public RetInfo getAppInstanceList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    WorkflowInstance workflowInstance =
        JSON.parseObject((String) map.get("workflowInstance"), WorkflowInstance.class);
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    GrdData grdData = workflowInstanceService.getInstanceList(workflowInstance, pager, shopId);
    Integer count = grdData.getCount();
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    ret.setRetData(list);
    ret.setRetCount(count);
    return ret;
  }

  @Override
  public RetInfo getAppCheckList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    WorkflowInstanceNode workflowInstanceNode =
        JSON.parseObject((String) map.get("workflowInstanceNode"), WorkflowInstanceNode.class);
    GrdData grdData = workflowInstanceService.getCheckList(workflowInstanceNode, pager);
    Integer count = grdData.getCount();
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    ret.setRetData(list);
    ret.setRetCount(count);
    return ret;
  }

  @Override
  public RetInfo chooseCardset(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Custom custom = JSON.parseObject((String) map.get("custom"), Custom.class);
    List<WechatCardRecord> wechatCardRecords =
        wechatCardRecordService.selectByCustId(custom.getId());
    ret.setRetData(wechatCardRecords);
    return ret;
  }

  @Override
  public RetInfo submitCardset(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    WechatCardRecord wechatCardRecord =
        JSON.parseObject((String) map.get("wechatCardRecord"), WechatCardRecord.class);

    ret =
        wechatCardRecordService.submitCardset(wechatCardRecord.getCardsetId().toString(),
            wechatCardRecord.getCustId(), wechatCardRecord.getOrderId());
    return ret;
  }

  @Override
  public RetInfo getNodeList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    WorkflowNode workflowNode =
        JSON.parseObject((String) map.get("workflowNode"), WorkflowNode.class);
    List<WorkflowNode> list =
        workflowTemplateService.getListByTemplateId(workflowNode.getTemplateId());

    ret.setRetData(list);
    return ret;
  }

  @Override
  public RetInfo getInstanceNodeList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = new Pager();
    pager.setPage(1);
    pager.setLimit(20);
    WorkflowInstanceNode workflowInstanceNode =
        JSON.parseObject((String) map.get("workflowInstanceNode"), WorkflowInstanceNode.class);
    GrdData grdData = workflowInstanceService.getInstanceNodeList(workflowInstanceNode, pager);
    Integer count = grdData.getCount();
    List<Object> list = grdData.getData();
    if (list == null)
      list = new ArrayList<Object>();
    ret.setRetData(list);
    ret.setRetCount(count);
    return ret;
  }

  @Override
  public RetInfo selectWorkFlowInstanceById(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    String shopIdStr = (String) map.get("shopId");
    Integer shopId = Integer.valueOf(shopIdStr);
    WorkflowInstanceNode workflowInstanceNode =
        JSON.parseObject((String) map.get("workflowInstanceNode"), WorkflowInstanceNode.class);
    WorkflowInstance workflowInstance =
        workflowInstanceService.selectById(workflowInstanceNode.getInstanceId(), shopId);

    ret.setRetData(workflowInstance);

    return ret;
  }

  @Override
  public RetInfo updateCheckNode(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    WorkflowCheckUser workflowCheckUser =
        JSON.parseObject((String) map.get("workflowCheckUser"), WorkflowCheckUser.class);
    RetInfo ret = workflowInstanceService.updateCheckNode(workflowCheckUser);

    return ret;
  }

  @Override
  public RetInfo backInstance(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");

    WorkflowInstance workflowInstance =
        JSON.parseObject((String) map.get("workflowInstance"), WorkflowInstance.class);
    RetInfo ret = workflowInstanceService.backInstance(workflowInstance.getId());

    return ret;
  }

  @Override
  public RetInfo getBoxOrderList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");
    Pager pager = getPager(map);
    BoxOrder boxOrder = JSON.parseObject((String) map.get("boxOrder"), BoxOrder.class);
    GrdData grdData = boxService.getBoxOrderList(boxOrder, pager);

    ret.setRetData(grdData.getData());
    ret.setRetCount(grdData.getCount());
    return ret;
  }

  @Override
  public RetInfo workerAddOrder(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");

    BoxOrder boxOrder = JSON.parseObject((String) map.get("boxOrder"), BoxOrder.class);
    return boxService.workerOperateOrder(boxOrder);
  }

  @Override
  public RetInfo workerOpenLock(Map map) {
    // TODO Auto-generated method stub
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");

    BoxOrder boxOrder = JSON.parseObject((String) map.get("boxOrder"), BoxOrder.class);
    return boxService.workerOpenLock(boxOrder.getLockId());
  }

  @Override
  public RetInfo queryAllLockList(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");

    List<LockInfo> boxOrders = boxService.queryAllLockList();
    ret.setRetData(boxOrders);
    return ret;
  }

  @Override
  public RetInfo taskCheckUpdate(Map map) {
    if (!(Boolean) map.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo ret = RetInfo.Success("请求成功");

    AppVersion appVersion = new AppVersion();
    appVersion.setDownloadUrl(PropertiesUtil.getPropertiesValue("downloadUrl", 3));
    appVersion.setAppVersion(PropertiesUtil.getPropertiesValue("appVersion", 3));
    appVersion.setVersionCode(PropertiesUtil.getPropertiesValue("versionCode", 3));
    appVersion.setVersionDesc(PropertiesUtil.getPropertiesValue("versionDesc", 3));

    ret.setRetData(appVersion);
    return ret;
  }


  /**
   *  
   */
  @Override
  public RetInfo uploadImage(Map paramMap, HttpServletRequest request) {
    if (!(Boolean) paramMap.get("checkStatus"))
      return new RetInfo("error", "获取数据失败，没有权限！");
    RetInfo result = RetInfo.Error("图片上传失败");
      try {
      // 获取图片的流文件
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      // 获得文件
      MultipartFile file = multipartRequest.getFile("file");
      if (null != file) {
        // 校验文件是否是图片
        Map<String, Object> checkResMap = checkImageFile(file);
        if ("0".equals(checkResMap.get("respCode"))) {

          return RetInfo.Error(checkResMap.get("msg").toString());

        }
        // 获取图片的真是类型
        String type = getPicType(file.getInputStream());
        if (StringUtils.isBlank(type)) {

          return RetInfo.Error("图片格式错误，请选择其它图片");
        }
        // 获取图片路径
        String path = FileUploadUtil.getPath();
        // 对图片进行重新命名
        String fileName = String.format("%s.%s", UUID.randomUUID().toString().toLowerCase(), type);
        // 将图片以日期的方式创建
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd/");

        String filePath = path + date;
        // 上传图片
        FileTool.uploadFiles(file.getBytes(), filePath, fileName);
        // 对上传的图片进行压缩
        // 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量 scale(比例)
        Thumbnails.of(filePath + fileName).scale(1f).outputQuality(0.25f)
            .toFile(filePath + fileName);
        RetInfo ret = RetInfo.Success("图片上传成功");
        logger.info("图片上传路径：" + filePath + fileName);
        ret.setRetData(date + fileName);
        return ret;
      }
    } catch (Exception e) {

      e.printStackTrace();
    }

    return result;
  }

  /**
   * 校验上传的文件是否是图片
   * 
   * @param file
   * @return
   */
  public Map<String, Object> checkImageFile(MultipartFile file) {
    Map<String, Object> map = new HashMap<String, Object>();
    // 根据图片内容判断是否为图片文件
    InputStream inputStream = null;
    try {
      inputStream = file.getInputStream();
      BufferedImage bi = ImageIO.read(inputStream);
      if (bi == null) {
            
        map.put("respCode", "0");
        map.put("msg", "该文件不是图片");
        return map;
      }
    } catch (IOException e) {

      map.put("respCode", "0");
      map.put("msg", "图片上传失败");
      logger.error("图片上传失败", e);

    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          map.put("respCode", "0");
          map.put("msg", "图片上传失败");
          logger.error("图片上传失败", e);
        }
      }
    }
    return map;
      }

  public String getPicType(InputStream inputStream) {
    // 读取文件的前几个字节来判断图片格式
    byte[] b = new byte[4];
    try {
      inputStream.read(b, 0, b.length);
      String type = bytesToHexString(b).toUpperCase();
      if (type.contains("FFD8FF")) {
        return "jpg";
      } else if (type.contains("89504E47")) {
        return "png";
      } else if (type.contains("47494638")) {
        return "gif";
      } else if (type.contains("424D")) {
        return "bmp";
      } else if (type.contains("FFD8FFD9")) {
        return "jpeg";
      }
    } catch (IOException e) {

    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          logger.error("图片上传失败", e);
        }
      }
    }
    return null;
    }

  private static String bytesToHexString(byte[] src) {
    StringBuilder builder = new StringBuilder();
    if (src == null || src.length <= 0) {
      return null;
    }
    String hv;
    for (int i = 0; i < src.length; i++) {
      // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
      hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
      if (hv.length() < 2) {
        builder.append(0);
      }
      builder.append(hv);
    }
    return builder.toString();
  }

  /**
   * 获取图片地址
   */
  @Override
  public void getImage(String path, HttpServletResponse response) {
    // 获取服务器图片
    String filePath = FileUploadUtil.getPath() + path;
    File imageFile = new File(filePath);
    if (imageFile.exists()) {
      FileInputStream fis = null;
      OutputStream os = null;
      try {
        fis = new FileInputStream(imageFile);
        os = response.getOutputStream();
        int count = 0;
        byte[] buffer = new byte[1024 * 8];
        while ((count = fis.read(buffer)) != -1) {
          os.write(buffer, 0, count);
          os.flush();
        }

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          fis.close();
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }

  }
  
}
