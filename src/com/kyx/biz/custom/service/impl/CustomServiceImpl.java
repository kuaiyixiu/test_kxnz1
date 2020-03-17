package com.kyx.biz.custom.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.car.mapper.CarMapper;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customMeal.mapper.CustomMealMapper;
import com.kyx.biz.customMeal.model.CustomMeal;
import com.kyx.biz.customMealDt.mapper.CustomMealDtMapper;
import com.kyx.biz.customMealDt.model.CustomMealDt;
import com.kyx.biz.mealdt.mapper.MealDtMapper;
import com.kyx.biz.mealdt.model.MealDt;
import com.kyx.biz.mealdt.service.MealDtService;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.service.ServeService;
import com.kyx.biz.wechat.config.MainConfiguration;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.model.WechatCustomer;
import com.kyx.biz.wechat.service.WechatCustomerService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service("customService")
public class CustomServiceImpl implements CustomService {
  // 车牌号
  private static String CAR_NO = "carNo";

  @Resource
  private CustomMapper customMapper;

  @Resource
  private CarMapper carMapper;

  @Resource
  private CustomMealMapper customMealMapper;

  @Resource
  private CustomMealDtMapper customMealDtMapper;

  @Resource
  private PayRecordMapper payRecordMapper;

  @Resource
  private MealDtMapper mealDtMapper;

  @Resource
  private MealDtService mealDtService;

  @Resource
  private ProductService productService;

  @Resource
  private ServeService serveService;

  @Resource
  private OrdersMapper ordersMapper;

  @Resource
  private WechatCustomerService wechatCustomerService;

  @Resource
  private ShopsService shopsService;

  @Override
  public GrdData queryCustoms(Custom custom, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Custom> list = customMapper.queryCustoms(custom);
    return new GrdData(page.getTotal(), list);
  }

  @Override
  public Custom selectByPrimaryKey(Integer id) {
    Custom custom = customMapper.selectByPrimaryKey(id);
    String birthdayStr = AppUtils.date2String1(custom.getBirthday());
    custom.setBirthdayStr(birthdayStr);

    return custom;
  }

  @Override
  public RetInfo saveCustom(Custom custom) {
    String retCode = "success";
    custom = setCustomParam(custom);
    if (customMapper.insertSelective(custom) <= 0) {
      return RetInfo.Error("保存会员信息失败");
    }

    setCustomCarNo(custom);

    String retMsg = "保存会员信息成功";
    retMsg += "会员卡号：" + custom.getCardNo();
    // 保存车辆相关信息
    if (!StringUtils.isEmpty(custom.getCarDataList())) {
      String carMsg = saveCar(custom.getCarDataList(), custom.getId());
      retMsg += carMsg;
    }

    return new RetInfo(retCode, retMsg);
  }

  /**
   * 保存车辆信息
   * 
   * @param carList
   * @return
   */
  private String saveCar(String carList, int customId) {
    String carNumMsg = "";
    JSONArray jsonArray = JSONArray.parseArray(carList);
    for (Object json : jsonArray) {
      JSONObject jsonObject = (JSONObject) json;
      String carNo = String.valueOf(jsonObject.get(CAR_NO));
      // 如果存在于会员下则忽略并提示
      if (hasVIPCarDataByCarNo(carNo)) {
        carNumMsg += carNo + ",";
        continue;
      }

      saveCustomCar(carNo, customId);
    }

    if (!StringUtils.isEmpty(carNumMsg)) {
      carNumMsg = "，车牌号" + carNumMsg + "已存在";
    }

    return carNumMsg;
  }

  /**
   * 保存会员车辆信息
   * 
   * @param carNo
   * @param customId
   */
  private int saveCustomCar(String carNo, int customId) {
    int count = 0;
    Car car = setCarParam(carNo, customId);
    // 如果存在与库中，则更新绑定该会员信息
    if (hasCarDataByCarNo(carNo)) {
      count += carMapper.updateByCarNumber(car);
    } else {
      // 否则新增一条信息
      count += carMapper.insertSelective(setCarParam(carNo, customId));
    }

    return count;
  }

  /**
   * 按车牌号搜索是否已存在车辆
   * 
   * @param carNo
   * @return
   */
  private boolean hasCarDataByCarNo(String carNo) {
    Car car = carMapper.selectByCarNo(carNo);

    return car != null;
  }

  /**
   * 该车牌号是否在vip客户下
   * 
   * @param carNo
   * @return
   */
  private boolean hasVIPCarDataByCarNo(String carNo) {
    Car car = carMapper.selectByCarNoVip(carNo);

    return car != null;
  }

  private Car setCarParam(String carNo, int customId) {
    Car car = new Car();
    car.setCarNumber(carNo);
    car.setCustomId(customId);
    car.setCarType(Car.VIP);

    return car;
  }

  @Override
  public RetInfo delCustomAndCar(String ids) {
    String[] idArr = ids.split(";");
    int count = 0;
    for (String id : idArr) {
      int customId = Integer.valueOf(id);
      count += customMapper.delCustomById(customId);
      // 车辆信息仅解绑，并不删除
      carMapper.updateByCustomId(customId);
    }

    if (count == idArr.length) {
      return RetInfo.Success("删除成功");
    }

    return RetInfo.Error("删除失败");
  }

  @Override
  public Custom selectCustomAndCar(Integer id) {
    Custom custom = this.selectByPrimaryKey(id);
    custom.setCarDataList(getCarList(id));

    return custom;
  }

  private String getCarList(Integer id) {
    Car car = new Car();
    car.setCustomId(id);
    List<Car> list = carMapper.selectCustomsCar(car);
    String carListStr = JSON.toJSONString(list);

    return carListStr;
  }

  @Override
  public RetInfo updateCustom(Custom custom) {
    customMapper.updateByPrimaryKeySelective(setCustomParam(custom));

    // 先解绑该会员下的车辆信息
    carMapper.updateByCustomId(custom.getId());
    if (StringUtils.isEmpty(custom.getCarDataList())) {
      return RetInfo.Success("更新成功");
    }

    // 添加关联车辆信息
    if (!addCar(custom)) {
      return RetInfo.Error("更新车辆信息失败");
    }
    return RetInfo.Success("更新成功");
  }

  /**
   * 绑定车辆信息
   * 
   * @param custom
   * @return
   */
  private boolean addCar(Custom custom) {
    String carList = custom.getCarDataList();
    Integer customId = custom.getId();
    JSONArray jsonArray = JSONArray.parseArray(carList);
    int count = 0;
    for (Object json : jsonArray) {
      JSONObject jsonObject = (JSONObject) json;
      String carNo = String.valueOf(jsonObject.get(CAR_NO));
      Car car = setCarParam(carNo, customId);
      // 如果存在则更新绑定信息
      if (hasCarDataByCarNo(carNo)) {
        count += carMapper.updateByCarNumber(car);
        continue;
      }

      count += carMapper.insertSelective(car);
    }

    int listSize = jsonArray.size();

    return count == listSize;
  }

  /**
   * 设置会员生日信息
   * 
   * @param custom
   * @return
   */
  private Custom setCustomParam(Custom custom) {
    String birthdayStr = custom.getBirthdayStr();
    if (StringUtils.isEmpty(birthdayStr)) {
      return custom;
    }

    Date birthday = AppUtils.string2Date1(custom.getBirthdayStr());
    custom.setBirthday(birthday);

    return custom;
  }

  @Override
  public RetInfo saveCustomMeal(CustomRequest customRequest) {
	  
	    //判断是否使用余额支付
	    if(4 == customRequest.getTypeId().intValue()) {
	    	
	    	//根据客户id查询客户信息
	    	Custom custom = customMapper.selectByPrimaryKey(customRequest.getCustId());
	    	if(custom != null) {
	    		
	    		BigDecimal balance = custom.getBalance();
	    		
	    		if(balance.compareTo(customRequest.getPrice()) == -1) {
	    			
	    			 return RetInfo.Error("可用余额不足");
	    		}else {
	    			//更新会员账户余额
	    			Custom ctom  =  new Custom();
	    			ctom.setId(customRequest.getCustId());
	    			BigDecimal money = balance.subtract(customRequest.getPrice());
	    			ctom.setBalance(money);
	    		    customMapper.updateByPrimaryKeySelective(ctom);
	    			//保存套餐信息
	    		    saveCustomForMeal(customRequest);
	    		}
	    		
	    	}
	    	
	    }else {
	    	//保存套餐信息
	       saveCustomForMeal(customRequest);
	    	 
	    }
	    return RetInfo.Success("添加成功");       
	    
  }

  /**
   * 设置会员套餐参数
   * 
   * @param customRequest
   * @return
   */
  private CustomMeal setCustomMealParam(CustomRequest customRequest) {
    CustomMeal customMeal = new CustomMeal();
    customMeal.setCreateDate(new Date());
    customMeal.setCustId(customRequest.getCustId());
    customMeal.setEndDate(customRequest.getEndDate());
    customMeal.setMealId(customRequest.getMealId());
    customMeal.setPrice(customRequest.getPrice());

    return customMeal;
  }

  /**
   * 保存会员套餐明细
   * 
   * @param mealId
   * @param dtId
   * @return
   */
  private void saveCustomMealDt(Integer mealId, String dtId, Integer quantity) {
    CustomMealDt customMealDt = new CustomMealDt();
    customMealDt.setMealDtId(Integer.valueOf(dtId));
    customMealDt.setCustomMealId(mealId);
    customMealDt.setUsed(CustomMealDt.NO_USE);
    customMealDt.setQuantity(quantity);

    customMealDtMapper.insertSelective(customMealDt);
  }

  /**
   * 保存支付记录
   * 
   * @param customRequest
   * @param id
   * @return
   */
  private boolean savePayRecord(CustomRequest customRequest) {
    PayRecord payRecord = new PayRecord();
    payRecord.setTypeId(customRequest.getTypeId());
    payRecord.setAmount(customRequest.getPrice());
    payRecord.setKind(PayRecord.KEY_MEAL);
    payRecord.setSourceId(customRequest.getMealId());
    payRecord.setShopId(customRequest.getShopId());

    return payRecordMapper.insertSelective(payRecord) > 0;
  }

  @Override
  public GrdData queryCustomMeal(CustomRequest customRequest) {
    List<CustomMeal> customMeals = queryCustomMeals(customRequest);

    return new GrdData(Long.valueOf(customMeals.size()), customMeals);
  }

  /**
   * 查询客户套餐信息
   * 
   * @param customRequest
   * @return
   */
  @Override
  public List<CustomMeal> queryCustomMeals(CustomRequest customRequest) {
    CustomMeal record = new CustomMeal();
    record.setShopId(customRequest.getShopId());
    record.setCustId(customRequest.getCustId());
    List<CustomMeal> list = customMealMapper.selectCustomMeal(record);

    return setCustomMealDt(list);
  }

  /**
   * 设置客户套餐明细信息
   * 
   * @param meals
   * @return
   */
  private List<CustomMeal> setCustomMealDt(List<CustomMeal> meals) {
    List<CustomMeal> resultList = new ArrayList<>();
    for (CustomMeal meal : meals) {
      Integer id = meal.getId();
      List<MealDt> mealDts = mealDtMapper.selectCustomMealDts(id);
      // 如果没有订单明细表示该套餐已消耗完，则不展示
      if (mealDts.isEmpty()) {
        continue;
      }
      meal.setCustomMealDts(mealDtService.transferMealDts(mealDts));
      resultList.add(meal);
    }

    return resultList;
  }

  @Override
  public RetInfo delCustomMeal(Integer id) {
    boolean mealDelOk = customMealMapper.deleteByPrimaryKey(id) > 0;
    boolean mealDtDelOk = customMealDtMapper.deleteByCustomMealId(id) > 0;

    if (mealDelOk && mealDtDelOk) {
      return RetInfo.Success("删除套餐成功");
    }

    return RetInfo.Error("删除套餐失败");
  }

  @Override
  public RetInfo delCustomMealDt(Integer id, String pId) {
    boolean DelMealDtOK = customMealDtMapper.deleteByPrimaryKey(id) > 0;
    // 如果pid不为empty 则表示该明细为最后一条，一并删除关联的客户套餐表
    if (!pId.equals("empty")) {
      boolean mealDelOk = customMealMapper.deleteByPrimaryKey(Integer.valueOf(pId)) > 0;
    }
    if (DelMealDtOK) {
      return RetInfo.Success("删除成功");
    }

    return RetInfo.Error("删除失败");
  }

  @Override
  public GrdData queryExpireMealDts(CustomRequest customRequest, Pager pager) {
    List<CustomMealDt> list =
        customMealDtMapper.selectExpireCustomMeals(customRequest.getDateRangeStartTime(),
            customRequest.getDateRangeEndTime());

    return new GrdData(Long.valueOf(list.size()), tansferMealDt(list));
  }

  /**
   * 列表转换
   * 
   * @param list
   * @return
   */
  private List<CustomMealDt> tansferMealDt(List<CustomMealDt> list) {
    for (CustomMealDt customMealDt : list) {
      int type = customMealDt.getType();
      String mealName = "";
      if (type == MealDt.PRODUCT) {
        Product product = productService.getById(customMealDt.getItemId());
        mealName = product.getProductName();
      } else {
        Serve serve = serveService.getById(customMealDt.getItemId());
        mealName = serve.getServeName();
      }

      customMealDt.setMealDtName(mealName);
    }

    return list;
  }

  /**
   * 设置会员卡号id
   * 
   * @param custom
   */
  private void setCustomCarNo(Custom custom) {
    custom.setCardNo(AppUtils.createId(custom.getShopId(), custom.getId()));
    customMapper.updateByPrimaryKeySelective(custom);
  }

  @Override
  public GrdData queryOtherShop(Orders orders, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Orders> list = ordersMapper.selectCustomOrders(orders);

    return new GrdData(page.getTotal(), list);
  }

  @Override
  public List<CustomMeal> queryCustomMealList(CustomRequest customRequest, Integer shopId) {
    CustomMeal record = new CustomMeal();
    // record.setShopId(customRequest.getShopId());
    record.setCustId(customRequest.getCustId());
    List<CustomMeal> list = customMealMapper.selectCustomMeal(record);

    Iterator<CustomMeal> it = list.iterator();
    while (it.hasNext()) {
      CustomMeal meal = it.next();
      List<MealDt> mealDts = mealDtMapper.selectCustomMealDts(meal.getId());

      Iterator<MealDt> mealDtIt = mealDts.iterator();
      while (mealDtIt.hasNext()) {
        MealDt mealDt = mealDtIt.next();
        if (meal.getShopId() != shopId) { // 跨店使用套餐
          if (mealDt.getType() == 1) { // 产品
            Product mealProduct = productService.getById(mealDt.getItemId());
            Product shopProduct = productService.selectByName(mealProduct.getProductName(), shopId);
            if (shopProduct == null) {
              mealDtIt.remove();
            }
            // else if (customRequest.getItemId()!= null && customRequest.getItemType() == 1 &&
            // !customRequest.getItemId().equals(shopProduct.getId())){
            // mealDtIt.remove();
            // }
            else {
              mealDt.setItemId(shopProduct.getId()); // 替换成当前门店的产品ID
            }

          } else if (mealDt.getType() == 2) { // 服务
            Serve mealServe = serveService.getById(mealDt.getItemId()); // 其他店的服务
            Serve shopServe = serveService.selectByName(mealServe.getServeName(), shopId);
            if (shopServe == null) {
              mealDtIt.remove();
            }
            // else if (customRequest.getItemId()!= null && customRequest.getItemType() == 2 &&
            // !customRequest.getItemId().equals(shopServe.getId())){
            // mealDtIt.remove();
            // }
            else {
              mealDt.setItemId(shopServe.getId()); // 替换成当前门店的服务ID
            }
          }
        } else {
          // if (customRequest.getItemId()!= null && (customRequest.getItemType() !=
          // mealDt.getType() || !customRequest.getItemId().equals(mealDt.getItemId()))){
          // mealDtIt.remove();
          // }
        }
      }

      if (ObjectUtils.isEmpty(mealDts)) {
        it.remove();
      } else {
        List<MealDt> dtList = mealDtService.transferMealDts(mealDts);
        meal.setProductMealDts(new ArrayList<MealDt>());
        meal.setServeMealDt(new ArrayList<MealDt>());
        for (MealDt dt : dtList) {
          if (dt.getType() == 1) {
            meal.getProductMealDts().add(dt);
          } else {
            meal.getServeMealDt().add(dt);
          }
        }
      }
    }
    return list;
  }

  @Override
  public RetInfo addCustom(Custom custom) {
    String retCode = "success";
    custom = setCustomParam(custom);
    if (customMapper.insertSelective(custom) <= 0) {
      return RetInfo.Error("保存会员信息失败");
    }

    setCustomCarNo(custom);

    // String retMsg = "添加会员成功,";
    String retMsg = "会员卡号：" + custom.getCardNo();
    // 保存车辆相关信息
    if (!StringUtils.isEmpty(custom.getCarDataList())) {
      String carMsg = addCar(custom.getCarDataList(), custom.getId());
      retMsg += carMsg;
    }

    RetInfo ret = new RetInfo(retCode, retMsg);
    ret.setRetData(custom.getCardNo());
    return new RetInfo(retCode, retMsg);
  }

  private String addCar(String carList, int customId) {
    String carNumMsg = "";
    String[] carLists = carList.split(",");
    for (int i = 0; i < carLists.length; i++) {
      String carNo = carLists[i];
      // 如果存在于会员下则忽略并提示
      if (hasVIPCarDataByCarNo(carNo)) {
        carNumMsg += carNo + ",";
        continue;
      }

      saveCustomCar(carNo, customId);
    }

    if (!StringUtils.isEmpty(carNumMsg)) {
      carNumMsg = "，车牌号" + carNumMsg + "已存在";
    }

    return carNumMsg;
  }

  @Override
  public Custom getByCarNo(String cardNo) {

    return customMapper.selectByCardNo(cardNo);
  }

  @Override
  public List<CustomMeal> queryWeChartCustomMeals(CustomRequest customRequest) {
    CustomMeal record = new CustomMeal();
    // record.setShopId(customRequest.getShopId()); #0726
    record.setCustId(customRequest.getCustId());
    List<CustomMeal> list = customMealMapper.selectCustomMeal(record);

    return setCustomMealDts(list);
  }

  private List<CustomMeal> setCustomMealDts(List<CustomMeal> meals) {
    for (CustomMeal meal : meals) {
      Integer id = meal.getId();
      List<MealDt> mealDts = mealDtMapper.selectCustomMealDts(id);
      transferMealDts(mealDts, meal);
    }

    return meals;
  }

  /**
   * 区分是产品还是服务套餐
   * 
   * @param list
   * @param meal
   * @return
   */
  private List<MealDt> transferMealDts(List<MealDt> list, CustomMeal meal) {
    List<MealDt> productMealDts = new ArrayList<>();
    List<MealDt> serveMealDt = new ArrayList<>();

    for (MealDt mealDt : list) {
      BigDecimal price;
      int type = mealDt.getType();
      if (type == MealDt.PRODUCT) {
        Product product = productService.getById(mealDt.getItemId());
        mealDt.setMealDtName(product.getProductName());
        price = product.getPrice();
        productMealDts.add(mealDt);
      } else {
        Serve serve = serveService.getById(mealDt.getItemId());
        mealDt.setMealDtName(serve.getServeName());
        price = serve.getPrice();
        serveMealDt.add(mealDt);
      }
    }
    meal.setProductMealDts(productMealDts);
    meal.setServeMealDt(serveMealDt);
    meal.setCreateDateStr(AppUtils.date2String1(meal.getCreateDate()));
    meal.setEndDateStr(AppUtils.date2String1(meal.getEndDate()));

    return list;
  }

  @Override
  public RetInfo editCustom(Custom custom) {
    customMapper.updateByPrimaryKeySelective(setCustomParam(custom));

    // 先解绑该会员下的车辆信息
    carMapper.updateByCustomId(custom.getId());
    if (StringUtils.isEmpty(custom.getCarDataList())) {
      return RetInfo.Success("更新成功");
    }

    // 添加关联车辆信息
    if (!bindCar(custom)) {
      return RetInfo.Error("更新车辆信息失败");
    }
    return RetInfo.Success("更新成功");
  }

  /**
   * 绑定车辆信息
   * 
   * @param custom
   * @return
   */
  private boolean bindCar(Custom custom) {
    String carStr = custom.getCarDataList();
    Integer customId = custom.getId();
    String[] carList = carStr.split(",");

    int count = 0;
    for (int i = 0; i < carList.length; i++) {
      String carNo = carList[i];
      Car car = setCarParam(carNo, customId);
      // 如果存在则更新绑定信息
      if (hasCarDataByCarNo(carNo)) {
        count += carMapper.updateByCarNumber(car);
        continue;
      }

      count += carMapper.insertSelective(car);
    }

    return count == carList.length;
  }

  @Override
  public boolean isHasCustom(Custom custom, HttpServletRequest request) {
    Custom resultCustom = getByCarNo(custom.getCardNo().trim().toUpperCase());

    if (!ObjectUtils.isEmpty(resultCustom) && custom.getShopId().intValue() == resultCustom.getShopId().intValue()) {
      resultCustom.setAppid(custom.getAppid());
      resultCustom.setOpenId(custom.getOpenId());
      setLoginSession(request, resultCustom);
      // 更新客户信息的openid和wechatname
      resultCustom.setWechatOpenId(custom.getOpenId());
      WechatConfig wechatConfig =
          (WechatConfig) request.getSession().getAttribute(BasicContant.WECHAT_CONFIG_SESSION);
      MainConfiguration mainConfig =
          new MainConfiguration(wechatConfig.getAppid(), wechatConfig.getAppsecret(),
              wechatConfig.getToken(), wechatConfig.getEncodingaeskey());
      WxMpService wxMpService = mainConfig.wxMpService();
      WxMpUser wxMpUser = null;
      try {
        wxMpUser = wxMpService.getUserService().userInfo(custom.getOpenId());
      } catch (WxErrorException e) {
        e.printStackTrace();
      }
      resultCustom.setWechatName(wxMpUser.getNickname());
      customMapper.updateByPrimaryKeySelective(resultCustom);
      return true;
    }

    return false;
  }

  /**
   * 设置登陆session
   * 
   * @param request
   */
  private void setLoginSession(HttpServletRequest request, Custom resultCustom) {
    Dbs.setDbName(Dbs.getMainDbName());
    wechatCustomerService.addWechatCustomer(resultCustom);

    HttpSession session = request.getSession();

    // 微信用户信息
    WechatCustomer wechatCustomer =
        wechatCustomerService
            .getByAppidAndOpenId(resultCustom.getAppid(), resultCustom.getOpenId());
    session.setAttribute(BasicContant.WECHAT_CUSTOMER_SESSION, wechatCustomer);

    // 数据库链接名
    String dbName = shopsService.getById(resultCustom.getShopId()).getDbName();
    Dbs.setDbName(dbName);
    session.setAttribute("currentDBName", dbName);

    // 用户信息
    session.setAttribute(BasicContant.CUSTOMER_SESSION, resultCustom);

  }

  @Override
  public int queryExpireMealDtsCount() {
    // 和过期模块一样，默认查询一个月内的，以免有歧义
    Date dateRangeStartTime = new Date();
    String dateRangeEndTimeStr = AppUtils.getDayByMonth(1);
    Date dateRangeEndTime = AppUtils.string2Date1(dateRangeEndTimeStr);

    int count =
        customMealDtMapper.selectExpireCustomMealsCount(dateRangeStartTime, dateRangeEndTime);

    return count;
  }

  @Override
  public String selectCardNoById(Integer id) {
    if (id != null)
      return customMapper.selectCardNoById(id);
    else
      return "";
  }
  
  public void saveCustomForMeal(CustomRequest customRequest) {
	    // 添加套餐
	    CustomMeal record = setCustomMealParam(customRequest);
	    customMealMapper.insertSelective(record);

	    JSONArray jsonArray = JSONArray.parseArray(customRequest.getMealDts());
	    for (Object json : jsonArray) {
	      JSONObject jsonObject = (JSONObject) json;
	      String id = String.valueOf(jsonObject.get("id"));
	      String quantityStr = String.valueOf(jsonObject.get("quantity"));
	      Integer quantity = Integer.valueOf(quantityStr);

	      saveCustomMealDt(record.getId(), id, quantity);
	    }

	    // 保存支付记录
	    savePayRecord(customRequest);
  }

  /**
   * 根据姓名卡号手机号和车牌号查询会员信息
   */
  @Override
  public GrdData queryCustomAndCar(Custom custom, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Custom> list = customMapper.queryCustomAndCar(custom);
    return new GrdData(page.getTotal(), list);
  }
  
}
