package com.kyx.biz.box.service;

import java.math.BigDecimal;
import java.util.List;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.box.model.BoxInfo;
import com.kyx.biz.box.model.BoxOrder;
import com.kyx.biz.lock.model.LockInfo;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.wechat.vo.TextValue;

/**
 * 车辆信息
 * 
 * @author daibin
 * @date 2019-4-15 上午9:50:03
 * 
 */
public interface BoxService {
  /**
   * 开锁
   * 
   * @param ids
   * @return
   */
  RetInfo openLock(String boxIdentifier, String dbName, Integer shopId);

  /**
   * TODO(daibin):目前只提供夜间洗车服务
   * 
   * @param serve
   * @return
   */
  List<TextValue> getBoxServes(Serve serve);

  /**
   * 客户下单
   * 
   * @param boxOrder
   * @return
   */
  RetInfo addBoxOrder(BoxOrder boxOrder);

  /**
   * 查询盒子订单列表
   * 
   * @param boxOrder
   * @param pager
   * @return
   */
  GrdData getBoxOrderList(BoxOrder boxOrder, Pager pager);

  /**
   * <pre>
   * 师傅操作订单
   * status
   * 1: 接单
   * 2: 已完成
   * </pre>
   * 
   * @param boxOrder
   * @return
   */
  RetInfo workerOperateOrder(BoxOrder boxOrder);

  /**
   * 查询我的盒子订单
   * 
   * @param customId
   * @return
   */
  List<BoxOrder> getMyBoxOrderList(Integer customId, boolean isProcessing);

  /**
   * 师傅开箱
   * 
   * @param lockId
   * @return
   */
  RetInfo workerOpenLock(Integer lockId);

  /**
   * 车主操作订单
   * 
   * @param orderId
   * @return
   */
  RetInfo ownerOperateOrder(Integer orderId, BigDecimal balance);

  /**
   * 查询进行中的盒子订单
   * 
   * @param customId
   * @return
   */
  RetInfo queryProcessingOrder(Integer customId);

  /**
   * 查询所有箱子
   * 
   * @param customId
   * @return
   */
  List<LockInfo> queryAllLockList();

  /**
   * 添加盒子
   * 
   * @param boxInfo
   * @return
   */
  RetInfo addBox(BoxInfo boxInfo);
  
  /**
   * 客户下单
   * 
   * @param boxOrder
   * @return
   */
  RetInfo checkBalance(BoxOrder boxOrder);
}
