package com.kyx.biz.app.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import com.kyx.basic.util.RetInfo;

public interface MobileShopsService {
  @Transactional
  RetInfo addOrder(Map map);

  RetInfo queryCustomMeal(Map map);

  RetInfo getServeList(Map map);

  RetInfo getProductList(Map map);

  /**
   * <pre>
   * 查询车辆列表 
   * shopId: 门店id
   * keyword: 搜索关键字
   * page: 当前页码
   * limit: 展示数量
   * </pre>
   * 
   * @param map
   * @return
   */
  RetInfo queryCarList(Map map);

  RetInfo getPaymentList(Map map);

  /**
   * 得到会员类型
   * 
   * @param map
   * @return
   */
  RetInfo getCustomTypeList(Map map);

  /**
   * <pre>
   * 添加会员
   * custName:客户名
   * custType:会员类型 (int)
   * cellphone:电话号码
   * birthdayStr:生日 2019-05-13
   * sex: 0:男 1:女 (int)
   * remark: 备注
   * carDataList:车辆列表 [{"carNo":"皖C98989"},{"carNo":"皖C98989"}]
   * </pre>
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo addCustom(Map map);

  /**
   * 查询车辆详情
   * 
   * @param map
   * @return
   */
  RetInfo queryCarDetail(Map map);

  /**
   * 得到车辆详情
   * 
   * @param map
   * @return
   */
  RetInfo queryCustomDetail(Map map);

  /**
   * 查询购买套餐列表
   * 
   * @param map
   * @return
   */
  RetInfo queryBuyMealList(Map map);

  /**
   * 查询订单详情按照id
   * 
   * @param map
   * @return
   */
  RetInfo queryOrderDetailById(Map map);

  /**
   * 查询订单列表
   * 
   * @param map
   * @return
   */
  RetInfo queryOrderList(Map map);

  /**
   * 查询提醒
   * 
   * @param map
   * @return
   */
  RetInfo queryReminds(Map map);

  /**
   * 查询邀约提醒
   * 
   * @param map
   * @return
   */
  RetInfo queryInvitationServer(Map map);

  /**
   * 车辆保险到期
   * 
   * @param map
   * @return
   */
  RetInfo queryCarInsure(Map map);

  /**
   * 查询车检到期
   * 
   * @param map
   * @return
   */
  RetInfo queryCarCheck(Map map);

  /**
   * 根据车牌号查询最近一次订单
   * 
   * @param map
   * @return
   */
  RetInfo queryLastOrdersByCarNumber(Map map);

  /**
   * 更新订单
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo submitOrders(Map map);

  /**
   * 实时增加服务列表
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo addOrdersServe(Map map);

  /**
   * 实时增加产品列表
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo addOrdersProduct(Map map) throws Exception;

  /**
   * 实时增加支付列表
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo addOrdersPay(Map map);

  /**
   * 实时更新服务
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo updateOrdersServe(Map map) throws Exception;

  /**
   * 实时更新产品
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo updateOrdersProduct(Map map) throws Exception;

  /**
   * 实时更新支付
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo updateOrdersPay(Map map) throws Exception;

  /**
   * 实时删除服务列表
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo delOrdersServe(Map map);

  /**
   * 实时删除产品列表
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo delOrdersProduct(Map map) throws Exception;

  /**
   * 实时删除支付
   * 
   * @param map
   * @return
   */
  @Transactional
  RetInfo delOrdersPay(Map map) throws Exception;

  /**
   * 提交套餐
   * 
   * @param map
   * @return
   */
  RetInfo submitMeal(Map map) throws Exception;

  /**
   * 购买会员套餐
   * 
   * @param map
   * @return
   */
  RetInfo saveCustomMeal(Map map);

  /**
   * 根据二维码查询产品信息
   * 
   * @param map
   * @return
   */
  RetInfo getProByCode(Map map);

  /**
   * 获取产品分类列表
   * 
   * @param map
   * @return
   */
  RetInfo getProductClass(Map map);

  /**
   * 保存产品信息
   * 
   * @param map
   * @return
   */
  RetInfo saveProductData(Map map);

  /**
   * 盘点存档
   * 
   * @param map
   * @return
   */
  RetInfo savePDData(Map map);

  /**
   * 查询库存流水 productId
   * 
   * @param map
   * @return
   */
  RetInfo getProductChange(Map map);

  /**
   * 查询产品列表
   * 
   * @param map
   * @return
   */
  RetInfo getProduct(Map map);

  /**
   * 查询供应商列表
   * 
   * @param map
   * @return
   */
  RetInfo getSupply(Map map);

  /**
   * 新建采购单 或退货单 主表保存或者修改
   * 
   * @param map
   * @return
   */
  RetInfo saveRepertory(Map map);

  /**
   * 保存供应商
   * 
   * @param map
   * @return
   */
  RetInfo saveSupply(Map map);

  /**
   * 保存库存明细
   * 
   * @param map
   * @return
   */
  RetInfo saveRepertoryDt(Map map);

  /**
   * 删除库存明细
   * 
   * @param map
   * @return
   */
  RetInfo delRepertoryDt(Map map);

  /**
   * 保存库存付款方式
   * 
   * @param map
   * @return
   */
  RetInfo saveRepertoryPay(Map map);

  /**
   * 删除库存付款方式
   * 
   * @param map
   * @return
   */
  RetInfo delRepertoryPay(Map map);

  /**
   * 获取库存列表
   * 
   * @param checkSignature
   * @return
   */
  RetInfo getRepertory(Map map);

  /**
   * 获取库存单据明细
   * 
   * @param map
   * @return
   */
  RetInfo getRepertoryDt(Map map);

  /**
   * 入库挂账
   * 
   * @param map
   * @return
   */
  RetInfo updateRepertory(Map map);

  /**
   * 删除库存单
   * 
   * @param map
   * @return
   */
  RetInfo delRepertory(Map map);

  /**
   * 获取挂账列表
   * 
   * @param map
   * @return
   */
  RetInfo getDebtRecord(Map map);

  /**
   * 挂账还款
   * 
   * @param map
   * @return
   */
  RetInfo updateDebtRecord(Map map);

  /**
   * 挂账批量还款
   * 
   * @param map
   * @return
   */
  RetInfo updateBatchDebtRecord(Map map);

  /**
   * 退货产品列表
   * 
   * @param map
   * @return
   */
  RetInfo getReturnProduct(Map map);

  /**
   * 客户预约列表
   * 
   * @param map
   * @return
   */
  RetInfo queryCustomAppoints(Map map);

  /**
   * 添加提醒
   * 
   * @param map
   * @return
   */
  RetInfo addRemind(Map map);

  /**
   * 按照shopid查询用户
   * 
   * @param map
   * @return
   */
  RetInfo queryUserByShopId(Map map);

  /**
   * 获取库存盘点列表
   * 
   * @param map
   * @return
   */
  RetInfo getProductCheck(Map map);

  /**
   * 查询保险到期
   * 
   * @param map
   * @return
   */
  RetInfo queryCarInsurance(Map map);

  /**
   * 查询车辆保养到期
   * 
   * @param map
   * @return
   */
  RetInfo queryCarMaintain(Map map);

  /**
   * 推送预约
   * 
   * @param map
   * @return
   */
  RetInfo sendAppoint(Map map) throws Exception;

  /**
   * 获取入账订单金额 （Integer shopId,Date dateRangeStartTime,Date dateRangeEndTime）
   * 
   * @param map
   * @return
   */
  String getFinishOrderAmt(Integer shopId);

  /**
   * 查询产品提成
   * 
   * @param map
   * @return
   */
  RetInfo getByRoyaltyId(Map map);

  /**
   * 完成提醒
   * 
   * @param map
   * @return
   */
  RetInfo carryOutRemind(Map map);

  /**
   * 服务邀约
   * 
   * @param map
   * @return
   */
  RetInfo sendServer(Map map) throws Exception;

  /**
   * 保险邀约
   * 
   * @param map
   * @return
   * @throws Exception
   */
  RetInfo sendCarInvitation(Map map) throws Exception;

  /**
   * 年检邀约
   * 
   * @param map
   * @return
   * @throws Exception
   */
  RetInfo sendCarCheck(Map map) throws Exception;

  /**
   * 保养邀约
   * 
   * @param map
   * @return
   * @throws Exception
   */
  RetInfo sendMaintain(Map map) throws Exception;

  /**
   * 完成服务
   * 
   * @param map
   * @return
   */
  RetInfo carryOutServer(Map map);

  /**
   * 完成预约
   * 
   * @param map
   * @return
   */
  RetInfo carryOutAppoint(Map map);

  /**
   * 完成车辆邀约
   * 
   * @param map
   * @return
   */
  RetInfo carryOutCar(Map map);

  /**
   * 查询客户列表
   * 
   * @param map
   * @return
   */
  RetInfo queryCustoms(Map map);

  /**
   * 查询所有优惠券
   * 
   * @param map
   * @return
   */
  RetInfo getAllCoupon(Map map);

  /**
   * 查询所有优惠券包
   * 
   * @param map
   * @return
   */
  RetInfo getAllCouponPack(Map map);

  /**
   * 确认充值
   * 
   * @param map
   * @return
   */
  RetInfo saveRechargeData(Map map);

  /**
   * 提交代金券
   * 
   * @param map
   * @return
   */
  RetInfo submitCoupon(Map map);

  /**
   * 获取我发起的审批列表
   * 
   * @param map
   * @return
   */
  RetInfo getAppInstanceList(Map map);

  /**
   * 获取待审批列表
   * 
   * @param map
   * @return
   */
  RetInfo getAppCheckList(Map map);

  /**
   * 查询优惠券
   * 
   * @param map
   * @return
   */
  RetInfo chooseCardset(Map map);

  /**
   * 提交优惠券
   * 
   * @param map
   * @return
   */
  RetInfo submitCardset(Map map);

  /**
   * 获取模板节点列表
   * 
   * @param map
   * @return
   */
  RetInfo getNodeList(Map map);

  /**
   * 获取审批历史列表
   * 
   * @param params
   * @param request
   * @return
   */

  RetInfo getInstanceNodeList(Map map);

  /**
   * 获取工作流实例
   * 
   * @param params
   * @param request
   * @return
   */
  RetInfo selectWorkFlowInstanceById(Map map);

  /**
   * 提交审批
   * 
   * @param params
   * @param request
   * @return
   */
  RetInfo updateCheckNode(Map map);

  /**
   * 撤回审批
   * 
   * @param params
   * @param request
   * @return
   */
  RetInfo backInstance(Map map);

  /**
   * 接单列表
   * 
   * @param params
   * @param request
   * @return
   */
  RetInfo getBoxOrderList(Map map);

  /**
   * 师傅接单
   * 
   * @param boxOrder
   * @return
   */
  RetInfo workerAddOrder(Map map);

  /**
   * 师傅开锁
   * 
   * @param boxOrder
   * @return
   */
  RetInfo workerOpenLock(Map map);

  /**
   * 查询所有箱子
   * 
   * @param boxOrder
   * @return
   */
  RetInfo queryAllLockList(Map map);

  /**
   * 更新客户信息
   * 
   * @param map
   * @return
   */
  RetInfo updateCustom(Map map);

  /**
   * 检查版本更新
   * 
   * @param map
   * @return
   */
  RetInfo taskCheckUpdate(Map map);
  
  /**
   * 上传施工图片
   * 
   * @param map
   * @return
   */
  RetInfo uploadImage(Map map, HttpServletRequest request);

  /**
   * 
   * 
   * @param map
   * @return
   * @return
   */
  void getImage(String path, HttpServletResponse response);

}
