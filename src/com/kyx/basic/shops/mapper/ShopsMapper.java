package com.kyx.basic.shops.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.model.ShopsExample;

public interface ShopsMapper {
  long countByExample(ShopsExample example);

  int deleteByExample(ShopsExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(Shops record);

  int insertSelective(Shops record);

  List<Shops> selectByExample(ShopsExample example);

  Shops selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") Shops record, @Param("example") ShopsExample example);

  int updateByExample(@Param("record") Shops record, @Param("example") ShopsExample example);

  int updateByPrimaryKeySelective(Shops record);

  int updateByPrimaryKey(Shops record);

  List<Shops> getAll();

  List<Shops> getByDbName(String dataBaseName);

  Shops getByShopKey(String shopKey);

  List<Shops> selectShopsByBdName(@Param("dbName") String dbName);

  List<Shops> getByWechatId(Integer wechatId);

  List<Shops> selectShops(@Param("keyword") String keyword);

  /**
   * 根据老板账套查询是否拥有门店
   * 
   * @param bossId
   * @return
   */
  int selectCountByBossId(@Param("bossId") Integer bossId);

  List<Shops> getShopsInfo(Shops shops);

  /**
   * 更新短信数量
   * 
   * @param shops
   * @return
   */
  int updateShopSmsCount(Shops shops);
  

  /**
        *  更新门店微信公众号配置
   * 
   * @param shops
   * @return
   */
  int updateShopByBossId(Shops shops);
}
