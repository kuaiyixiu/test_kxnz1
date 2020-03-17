package com.kyx.basic.shops.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.vo.LableValue;

public interface ShopsService {
  /**
   * 查询所有的记录
   * 
   * @return
   */
  List<Shops> getAll();

  /**
   * 根据数据库名称查询数据库信息
   * 
   * @param dataBaseName
   * @return
   */
  List<Shops> getByDbName(String dataBaseName);

  /**
   * 根据门店码查询门店信息
   * 
   * @param shopKey
   * @return
   */
  Shops getByShopKey(String shopKey);

  /**
   * 根据id查询
   * 
   * @param shopId
   * @return
   */
  Shops getById(Integer shopId);

  /**
   * 按照dbName查询门店信息
   * 
   * @param jdbcUrl
   * @return
   */
  Map<Integer, String> getShopsByBdName(String dbName);

  /**
   * 组装dbname和门店信息
   * 
   * @return
   */
  Map<String, List<Shops>> getShops();

  List<Shops> getByWechatId(Integer id);

  Map<Integer, String> getMapByWechatId(Integer wechatId);

  /**
   * 查询门店信息
   * 
   * @param keyword
   * @return
   */
  GrdData queryShops(String keyword);

  /**
   * 更新门店状态
   * 
   * @param shops
   * @return
   */
  @Transactional
  RetInfo updateShops(Shops shops);

  /**
   * 添加门店
   * 
   * @param shops
   * @param user
   * @param sqlFilePath
   * @return
   */
  RetInfo addShop(Shops shops, User user, String sqlFilePath);

  /**
   * 更新门店信息 完善门店信息
   * 
   * @param shops
   * @param wechatConfig
   * @param request
   * @return
   */
  RetInfo saveEditShop(Shops shops, WechatConfig wechatConfig,String role,  HttpServletRequest request);

  /**
   * 门店新增20190816
   * 
   * @param shops
   * @param sqlFilePath
   * @return
   */
  // @Transactional(rollbackFor=Exception.class)
  RetInfo saveShopInfo(Shops shops, String sqlFilePath) throws Exception;

  GrdData queryShops(Shops shops, Pager pager);

  /**
   * 短信充值接口
   * 
   * @param shops
   * @return
   */
  RetInfo updateShopSmsCount(Shops shops);

  /**
   * 得到门店列表
   * 
   * @param map
   * @return
   */
  List<LableValue> getShops(Map<Integer, String> map);
  
 /**
   * 更新全局公众号配置
 * @param wechatConfig
 * @param request
 * @return
 */
RetInfo editWchat(WechatConfig wechatConfig, HttpServletRequest request);
}
