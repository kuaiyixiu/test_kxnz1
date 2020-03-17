package com.kyx.basic.shops.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.shops.mapper.ShopsMapper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.sms.mapper.SmsRechargeMapper;
import com.kyx.basic.sms.model.SmsRecharge;
import com.kyx.basic.user.mapper.UserMapper;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.Common;
import com.kyx.basic.util.DataBaseUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.supply.mapper.SupplyMapper;
import com.kyx.biz.supply.model.Supply;
import com.kyx.biz.wechat.mapper.WechatConfigMapper;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.vo.LableValue;

@Service("shopsService")
public class ShopsServiceImpl implements ShopsService {
  @Resource
  private ShopsMapper shopsMapper;

  @Resource
  private DataBaseUtils dataBaseUtils;

  @Value("${jdbc_url_ip}")
  private String jdbcUrl;

  @Resource
  private UserMapper userMapper;

  @Value("${jdbc_driverClassName}")
  private String jdbcDriver;
  @Resource
  private SupplyMapper supplyMapper;
  @Resource
  private WechatConfigMapper wechatConfigMapper;
  @Resource
  private SmsRechargeMapper smsRechargeMapper;

  @Override
  public List<Shops> getAll() {

    return shopsMapper.getAll();
  }

  @Override
  public List<Shops> getByDbName(String dataBaseName) {

    return shopsMapper.getByDbName(dataBaseName);
  }

  @Override
  public Shops getByShopKey(String shopKey) {

    return shopsMapper.getByShopKey(shopKey);
  }

  @Override
  public Shops getById(Integer shopId) {
    return shopsMapper.selectByPrimaryKey(shopId);
  }

  @Override
  public Map<Integer, String> getShopsByBdName(String dbName) {
    List<Shops> list = shopsMapper.selectShopsByBdName(dbName);
    Map<Integer, String> resultMap = new HashMap<>();
    for (Shops shops : list) {
      resultMap.put(shops.getId(), shops.getShopName());
    }

    return resultMap;
  }

  @Override
  public Map<String, List<Shops>> getShops() {
    if (BasicContant.shopMap != null)
      return BasicContant.shopMap;
    String db = Dbs.getDbName();
    Dbs.setDbName(Dbs.getMainDbName());
    List<Shops> list = shopsMapper.getAll();
    Map<String, List<Shops>> map = new HashMap<String, List<Shops>>();
    for (Shops shops : list) {
      String dbName = shops.getDbName();
      if (map.get(dbName) == null) {
        List<Shops> li = new ArrayList<Shops>();
        map.put(dbName, li);
      }
      map.get(dbName).add(shops);
    }
    BasicContant.shopMap = map;
    Dbs.setDbName(db);
    return map;
  }

  @Override
  public List<Shops> getByWechatId(Integer wechatId) {

    return shopsMapper.getByWechatId(wechatId);
  }

  @Override
  public Map<Integer, String> getMapByWechatId(Integer wechatId) {
    List<Shops> list = shopsMapper.getByWechatId(wechatId);
    Map<Integer, String> resultMap = new HashMap<>();
    for (Shops shops : list) {
      resultMap.put(shops.getId(), shops.getShopName());
    }

    return resultMap;
  }

  @Override
  public GrdData queryShops(String keyword) {
    Dbs.setDbName(Dbs.getMainDbName());
    List<Shops> shopList = shopsMapper.selectShops(keyword);

    return new GrdData(Long.valueOf(shopList.size()), shopList);
  }

  @Override
  public RetInfo updateShops(Shops shops) {
    boolean bool = shopsMapper.updateByPrimaryKeySelective(shops) > 0;
    BasicContant.shopMap = null;
    getShops();
    return new RetInfo(bool, "更新门店");
  }

  @Override
  public RetInfo addShop(Shops shops, User user, String sqlFilePath) {
    boolean addBool = true;
    // 表示有选择已有门店
    if (shops.getId() != -1) {
      addBool = addPresence(shops);
    } else {
      addBool = addNewShop(shops, user, sqlFilePath);
    }
    BasicContant.shopMap = null;
    getShops();
    return new RetInfo(addBool, "添加门店");
  }

  /**
   * 添加已存在boss门店
   * 
   * @param shops
   * @return
   */
  private boolean addPresence(Shops shops) {
    Shops presenceShop = shopsMapper.selectByPrimaryKey(shops.getId());
    // 用界面输入的门店名,地址，联系方式
    presenceShop.setId(null);
    presenceShop.setShopName(shops.getShopName());
    presenceShop.setShopAddress(shops.getShopAddress());
    presenceShop.setShopTel(shops.getShopTel());
    presenceShop.setUserCount(BasicContant.SHOP_USER_COUNT);
    presenceShop.setShopKey("0");// 不是一级门店
    int count = shopsMapper.insertSelective(presenceShop);
    initData(presenceShop);
    return count > 0;
  }

  /**
   * 初始化门店数据 更新门店数据库信息
   * 
   * @param presenceShop
   */
  private void initData(Shops presenceShop) {
    String pdbName = Dbs.getDbName();
    Dbs.setDbName(presenceShop.getDbName());
    // 补充供应商自带的数据
    Supply supply = new Supply();
    supply.setSupplyName("零星采购");
    supply.setShopId(presenceShop.getId());
    supply.setCandel(0);
    supplyMapper.insertSelective(supply);
    Dbs.setDbName(pdbName);

  }

  private boolean addNewShop(Shops shops, User user, String sqlFilePath) {
    boolean bool = true;
    Shops addShops = setNewShop(shops);
    bool = shopsMapper.insertSelective(addShops) > 0;
    if (!bool) {
      return false;
    }

    bool = isSetBossAndJdbcMsgOk(addShops, user);
    if (!bool) {
      return false;
    }

    try {
      dataBaseUtils.createDatabase(addShops, sqlFilePath);
      initData(addShops);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return bool;
  }

  /**
   * 选择已存在门店时，设置存在jdbc相关设置
   * 
   * @param shops
   * @return
   */
  private Shops setNewShop(Shops shops) {
    shops.setId(null);
    shops.setJdbcUsername(BasicContant.shopsContant.JDBC_USERNAME);
    shops.setJdbcPassword(BasicContant.shopsContant.JDBC_PASSWORD);
    shops.setAccountStatus(BasicContant.shopsContant.RUNNING);
    return shops;
  }

  /**
   * 门店设置boss信息和jdbc
   * 
   * @param shops
   * @return
   */
  private boolean isSetBossAndJdbcMsgOk(Shops shops, User user) {
    Integer shopId = shops.getId();
    user = setUserParam(shopId, user);
    boolean addBossBool = isAddBossOk(user);
    if (!addBossBool) {
      return false;
    }

    Integer userId = user.getId();
    Shops record = setUpdateShopsParam(shops, shopId, userId);

    return isUpdateShopOk(record);
  }

  private Shops setUpdateShopsParam(Shops shops, Integer shopId, Integer userId) {
    // 库名
    String dbName = AppUtils.createDbName(shops.getId());
    shops.setDbName(dbName);

    // jdbc:mysql://192.168.1.251:3306/kxnz?useUnicode=true&characterEncoding=utf8
    String url =
        BasicContant.shopsContant.JDBC_URL_PREFIX + jdbcUrl + "/" + dbName
            + BasicContant.shopsContant.JDBC_URL_SUFFIX;
    shops.setJdbcUrl(url);

    // shopkey 标注是否是一级门店 门店绑定老板账户
    // String shopKey = AppUtils.createShopKey(shopId);
    String shopKey = "1";
    shops.setShopKey(shopKey);

    shops.setJdbcDriverclassname(jdbcDriver);
    shops.setBossId(userId);

    return shops;
  }

  /**
   * 更新门店
   * 
   * @param shops
   * @return
   */
  private boolean isUpdateShopOk(Shops shops) {

    return shopsMapper.updateByPrimaryKeySelective(shops) > 0;
  }

  /**
   * 添加boss信息
   * 
   * @param shopId
   * @return
   */
  private boolean isAddBossOk(User user) {

    return userMapper.insertSelective(user) > 0;
  }

  private User setUserParam(Integer shopId, User user) {
    Date nowDate = new Date();
    user.setId(null);
    user.setEntryDate(nowDate);
    user.setUserName(user.getUserPhone());
    user.setUserPassword(Common.getInitPwd());
    user.setRegTime(nowDate);
    user.setStatus(BasicContant.userContant.USING_STATUS);
    user.setRoleId(BasicContant.userContant.BOSS_ROLE_ID);
    user.setEnable(BasicContant.userContant.ENABLE);
    user.setPost(BasicContant.userContant.POST);
    user.setShopId(shopId);
    user.setBossAccount(BasicContant.userContant.BOSS_ACCOUNT);

    return user;
  }

  @Override
  public RetInfo saveEditShop(Shops shops, WechatConfig wechatConfig ,String role, HttpServletRequest request) {
      Dbs.setDbName(Dbs.getMainDbName());

      RetInfo ret = RetInfo.Error("保存失败");

      HttpSession session = request.getSession();
      Shops shop = (Shops) session.getAttribute(Shops.SHOPS_SESSION);

      if ("admin".equalsIgnoreCase(role)) {
          WechatConfig config =
                  (WechatConfig) session.getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
          WechatConfig dbWeChat = null;
          if (wechatConfig != null) { // 当页面公总号信息不为空是，查询AppId
              wechatConfig.setAppid(wechatConfig.getAppid().trim());
              dbWeChat = wechatConfigMapper.getByAppId(wechatConfig.getAppid());
          }

          // 更新公总号信息
          if (config == null) { // 缓存为空
              return ret;
          } else {
              if (config.getId() == 1) { // 默认为全局，此处不可更改，只能新增。
                  if (dbWeChat != null && dbWeChat.getId() != 1) { // 数据库已存在当前Appid, 提示已使用
                      return RetInfo.Error("开发者ID已经存在");
                  } else if (dbWeChat == null) { // 新增公总号, 且更新所有门店公总号id
                      if (wechatConfigMapper.insertSelective(wechatConfig) < 1) {
                          return ret;
                      }
                      config.setId(wechatConfig.getId());
                      config.setWechatName(wechatConfig.getWechatName());
                      config.setToken(wechatConfig.getToken());
                      config.setAppid(wechatConfig.getAppid());
                      config.setAppsecret(wechatConfig.getAppsecret());
                      config.setEncodingaeskey(wechatConfig.getEncodingaeskey());

                      shop.setWechatId(wechatConfig.getId());
                      changeShop(shops, shop);
                      // 更新该boss下的所有门店的公众号配置信息改变
                      shopsMapper.updateShopByBossId(shop);
                  }
                  // dbWeChat.getId() == 1 表示为全局的, 不修改
              } else {
                  if (dbWeChat != null && !dbWeChat.getId().equals(config.getId())) {
                      return RetInfo.Error("开发者ID已经存在");
                  } else { // 更新公总号信息
                      wechatConfig.setId(config.getId());
                      if (wechatConfigMapper.updateByPrimaryKeySelective(wechatConfig) < 1) {
                          return ret;
                      }
                      config.setWechatName(wechatConfig.getWechatName());
                      config.setToken(wechatConfig.getToken());
                      config.setAppid(wechatConfig.getAppid());
                      config.setAppsecret(wechatConfig.getAppsecret());
                      config.setEncodingaeskey(wechatConfig.getEncodingaeskey());
                  }
              }
          }
          session.setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, config);
      }

      // 更新完公总号, 更新门店信息
      changeShop(shops, shop);
      RetInfo info = updateShops(shop);
      if (!info.isSuccess())
          return ret;

      // 更新session中的值
      session.setAttribute(Shops.SHOPS_SESSION, shop);
      return RetInfo.Success("保存成功");


//
//    if ((config != null && 1 == config.getId()) || config == null ) {// 公众号配置信息不存在 新增进去
//      count = wechatConfigMapper.insertSelective(wechatConfig);
//      if (count < 1)
//        return ret;
//      shop.setWechatId(wechatConfig.getId());
//      changeShop(shops, shop);
//      //更新该boss下的所有门店的公众号配置信息改变
//      shopsMapper.updateShopByBossId(shop);
//      //更新门店的信息
//      RetInfo info = updateShops(shop);
//      if (!info.isSuccess())
//        return ret;
//      //更新session中的值
//      request.getSession().setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, wechatConfig);
//      request.getSession().setAttribute(Shops.SHOPS_SESSION, shop);
//    } else {
//      config.setWechatName(wechatConfig.getWechatName());
//      config.setAccessToken(wechatConfig.getAccessToken());
//      config.setAppid(wechatConfig.getAppid());
//      config.setAppsecret(wechatConfig.getAppsecret());
//      config.setEncodingaeskey(wechatConfig.getEncodingaeskey());
//      count = wechatConfigMapper.updateByPrimaryKeySelective(config);
//      if (count < 1)
//        return ret;
//      changeShop(shops, shop);
//      RetInfo info = updateShops(shop);
//      if (!info.isSuccess())
//        return ret;
//      //更新session中的值
//      request.getSession().setAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION, config);
//      request.getSession().setAttribute(Shops.SHOPS_SESSION, shop);
//    }
//
//    return RetInfo.Success("保存成功");
  }

  private void changeShop(Shops shops, Shops shop) {
    shop.setShopName(shops.getShopName());
    shop.setShopAddress(shops.getShopAddress());
    shop.setShopTel(shops.getShopTel());
    shop.setShopPhone(shops.getShopPhone());
    shop.setFaTitle(shops.getFaTitle());
    shop.setFax(shops.getFax());
  }

  @Override
  public RetInfo saveShopInfo(Shops shops, String sqlFilePath) throws Exception {
    // 查询该boss账户下是否存在门店 如果存在门店说明以后账套 如果不存在需要创建账套
    User boss = userMapper.selectByPrimaryKey(shops.getBossId());
    Boolean bool = false;
    if (shops.getId() != null) {// 修改
      int count = shopsMapper.updateByPrimaryKeySelective(shops);
      if (count < 1)
        return RetInfo.Error("门店修改失败");
    } else {
      if (boss.getShopId() != null) {// 账套已存在
        shops.setShopKey("0");
        Shops topShops = shopsMapper.selectByPrimaryKey(boss.getShopId());
        // 库名
        shops.setDbName(topShops.getDbName());
        shops.setJdbcUrl(topShops.getJdbcUrl());
        shops.setJdbcDriverclassname(topShops.getJdbcDriverclassname());
        shops.setWechatId(topShops.getWechatId());
      } else {
        shops.setShopKey("1");
      }
      bool = setNewSaveShop(shops);
      if (!bool)
        return RetInfo.Error("门店添加失败");
      if (boss.getShopId() == null) {// 账套不存在
        dataBaseUtils.createDatabase(shops, sqlFilePath);
        initData(shops);
      }

    }
    BasicContant.shopMap = null;
    getShops();
    return RetInfo.Success("保存成功");
  }

  private Boolean setNewSaveShop(Shops shops) {
    shops.setJdbcUsername(BasicContant.shopsContant.JDBC_USERNAME);
    shops.setJdbcPassword(BasicContant.shopsContant.JDBC_PASSWORD);
    shops.setAccountStatus(BasicContant.shopsContant.RUNNING);
    // 先存档 获取id
    int count = shopsMapper.insertSelective(shops);
    if (count < 1)
      return false;
    // 判断是否是一级门店
    if ("1".equals(shops.getShopKey())) {// 更新用户表
      User user = new User();
      user.setId(shops.getBossId());
      user.setShopId(shops.getId());
      userMapper.updateByPrimaryKeySelective(user);
    }
    if (StringUtils.isEmpty(shops.getDbName())) {
      // 库名
      String dbName = AppUtils.createDbName(shops.getId());
      shops.setDbName(dbName);

      // jdbc:mysql://192.168.1.251:3306/kxnz?useUnicode=true&characterEncoding=utf8
      String url =
          BasicContant.shopsContant.JDBC_URL_PREFIX + jdbcUrl + "/" + dbName
              + BasicContant.shopsContant.JDBC_URL_SUFFIX;
      shops.setJdbcUrl(url);
      shops.setJdbcDriverclassname(jdbcDriver);
      count = shopsMapper.updateByPrimaryKeySelective(shops);
      if (count < 1)
        return false;
    }
    return true;
  }

  @Override
  public GrdData queryShops(Shops shops, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<Shops> list = shopsMapper.getShopsInfo(shops);
    return new GrdData(page.getTotal(), list);
  }

  @Override
  public RetInfo updateShopSmsCount(Shops shops) {
    RetInfo info = RetInfo.Error("充值失败");
    Integer beforeCount = shopsMapper.selectByPrimaryKey(shops.getId()).getSmsAmount();
    int count = shopsMapper.updateShopSmsCount(shops);
    if (count > 0) {// 短信充值成功
      SmsRecharge smsRecharge = new SmsRecharge();
      smsRecharge.setAmount(shops.getAddQuantity());
      smsRecharge.setCreateTime(new Date());
      smsRecharge.setShopId(shops.getId());
      smsRecharge.setOptUser(shops.getUserRealname());
      smsRecharge.setBeforeCount(beforeCount);
      smsRecharge.setAfterCount(beforeCount + shops.getAddQuantity());
      count = smsRechargeMapper.insertSelective(smsRecharge);
      if (count > 0) {
        info = RetInfo.Success("充值成功");
      }
    }
    return info;
  }

  @Override
  public List<LableValue> getShops(Map<Integer, String> map) {
    List<LableValue> list = Lists.newArrayList();
    if (map.isEmpty()) {
      return list;
    }

    for (Integer key : map.keySet()) {
      LableValue lableValue = new LableValue(key, map.get(key));
      list.add(lableValue);
    }

    return list;
  }

	/**
	 *  更新全局的公众号配置信息
	 */
	@Override
	public RetInfo editWchat(WechatConfig wechatConfig, HttpServletRequest request) {
		
		 //根据appid查询数据库是否已经存在
		  WechatConfig wechat = null;
		  if(wechatConfig != null) {
	 		 
		   wechat = wechatConfigMapper.getByAppId(wechatConfig.getAppid());
		 
		  }
		  if((wechat == null) ||(wechat != null &&  1 == wechat.getId()) ) {
			  //更新公众号配置信息
			  int updateByPrimaryKey = wechatConfigMapper.updateByPrimaryKeySelective(wechatConfig);
			  if(updateByPrimaryKey < 1) {
				  
				  return  RetInfo.Error("更新失败");
			  }
		  }else {
			  
			  return  RetInfo.Error("开发者ID已经存在");
			  
		  }
		  return RetInfo.Success("更新成功");
	}
}
