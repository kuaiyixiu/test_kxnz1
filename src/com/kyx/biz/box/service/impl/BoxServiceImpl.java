package com.kyx.biz.box.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.box.form.BoxResultJson;
import com.kyx.biz.box.mapper.BoxInfoMapper;
import com.kyx.biz.box.mapper.BoxOrderMapper;
import com.kyx.biz.box.model.BoxInfo;
import com.kyx.biz.box.model.BoxInfoExample;
import com.kyx.biz.box.model.BoxOrder;
import com.kyx.biz.box.model.BoxOrderEnum;
import com.kyx.biz.box.model.BoxOrderExample;
import com.kyx.biz.box.model.BoxOrderExample.Criteria;
import com.kyx.biz.box.service.BoxService;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.lock.mapper.LockCommandMapper;
import com.kyx.biz.lock.mapper.LockInfoMapper;
import com.kyx.biz.lock.model.LockCommand;
import com.kyx.biz.lock.model.LockCommandExample;
import com.kyx.biz.lock.model.LockInfo;
import com.kyx.biz.lock.model.LockInfoExample;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersPay;
import com.kyx.biz.orders.service.OrdersPayService;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.wechat.vo.TextValue;

@Service("boxService")
public class BoxServiceImpl implements BoxService {
  @Resource
  private LockInfoMapper lockInfoMapper;

  @Resource
  private BoxInfoMapper boxInfoMapper;

  @Resource
  private LockCommandMapper lockCommandMapper;

  @Resource
  private ServeMapper serveMapper;

  @Resource
  private BoxOrderMapper boxOrderMapper;

  @Resource
  private UserService userService;

  @Resource
  private ShopsService shopsService;

  @Resource
  private OrdersService ordersService;

  @Resource
  private OrdersPayService ordersPayService;
 
  @Resource
  private CustomMapper customMapper;
  
  @Resource
  private OrdersMapper ordersMapper;
  
  @Override
  public RetInfo openLock(String boxIdentifier, String dbName, Integer shopId) {

    return sendOpenLock(boxIdentifier, dbName, shopId);
  }

  /**
   * 发送开锁指令
   * 
   * @param boxIdentifier
   * @return
   */
  private RetInfo sendOpenLock(String boxIdentifier, String dbName, Integer shopId) {
    Dbs.setDbName(dbName);
    Integer boxId = queryBoxId(boxIdentifier, shopId);
    if (boxId == -1) {
      return RetInfo.Error("开锁失败");
    }

    // 显示的箱子编号
    LockInfo lockInfo = queryLockInfo(boxId);
    if (lockInfo == null) {
      return RetInfo.Error("该盒子钥匙已满");
    }

    // 切换为主库查询指令
    Dbs.setDbName(Dbs.getMainDbName());
    LockCommand lockCommand =
        queryCommand(lockInfo.getLockNumber(), BasicContant.BoxContant.OPEN_LOCK);
    if (lockCommand == null) {
      return RetInfo.Error("开锁失败");
    }

    boolean bool = sendCommand(boxIdentifier, lockCommand.getCommand().trim());

    String msg = lockCommand.getLockAddress() + "开箱";
    return new RetInfo(bool, msg, lockInfo.getId());
  }

  /**
   * 查询指令
   * 
   * @param lockNumber
   * @param type
   * @return
   */
  private LockCommand queryCommand(Integer lockNumber, Integer type) {
    LockCommandExample example = new LockCommandExample();
    example.createCriteria().andLockNumberEqualTo(lockNumber).andTypeEqualTo(type);

    List<LockCommand> list = lockCommandMapper.selectByExample(example);
    if (!list.isEmpty()) {
      return list.get(0);
    }

    return null;
  }

  /**
   * 查询锁id
   * 
   * @param boxId
   * @return
   */
  private LockInfo queryLockInfo(Integer boxId) {
    LockInfoExample example = new LockInfoExample();
    example.setOrderByClause("lock_number");
    example.createCriteria().andBoxIdEqualTo(boxId).andStatusEqualTo(BasicContant.BoxContant.IDLE);
    List<LockInfo> list = lockInfoMapper.selectByExample(example);

    if (!list.isEmpty()) {
      return list.get(0);
    }

    return null;
  }

  /**
   * 查询盒子id
   * 
   * @param boxIdentifier
   * @return
   */
  private Integer queryBoxId(String boxIdentifier, Integer shopId) {
    BoxInfoExample example = new BoxInfoExample();
    example.createCriteria().andIdentifierEqualTo(boxIdentifier).andShopIdEqualTo(shopId)
        .andEnabledEqualTo(BasicContant.BoxContant.USING);
    List<BoxInfo> list = boxInfoMapper.selectByExample(example);

    if (!list.isEmpty()) {
      return list.get(0).getId();
    }

    return -1;
  }

  /**
   * 发送指令
   * 
   * @param boxIdentifier
   * @param command
   * @return
   */
  private boolean sendCommand(String boxIdentifier, String command) {
    JSONObject json = new JSONObject();
    json.put("command", command);
    json.put("deviceId", boxIdentifier);

    // 创建Httpclient对象
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    String resultString = "";

    try {
      // 创建Http Post请求
      HttpPost httpPost = new HttpPost("http://118.25.176.213:9002/invokeCommand");
      // 创建请求内容
      StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);
      // 执行http请求
      response = httpClient.execute(httpPost);
      resultString = EntityUtils.toString(response.getEntity(), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        response.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    BoxResultJson boxResultJson = JSONObject.parseObject(resultString, BoxResultJson.class);

    if (StringUtils.equals(boxResultJson.getMsg(), BoxResultJson.SUCCESS_MSG)
        && StringUtils.equals(boxResultJson.getCode(), BoxResultJson.SUCCESS_CODE)) {
      return true;
    }

    return false;
  }

  @Override
  public List<TextValue> getBoxServes(Serve serve) {
    serve.setClassId(BasicContant.NIGHT_CLASS_ID);
    List<Serve> list = serveMapper.getServesByClassId(serve);

    return transferServeList(list);
  }

  private List<TextValue> transferServeList(List<Serve> list) {
    List<TextValue> resultList = Lists.newArrayList();
    for (Serve serve : list) {
      TextValue textValue =
          new TextValue(serve.getServeName(), String.valueOf(serve.getId()), String.valueOf(serve
              .getPrice()));
      resultList.add(textValue);
    }

    return resultList;
  }

  @Override
  public RetInfo addBoxOrder(BoxOrder boxOrder) {
    int lockId = boxOrder.getLockId();
    if (isUsingLock(lockId)) {
      return RetInfo.Error("该箱子已有订单!");
    }
    boxOrder.setCreateTime(new Date());
    boolean bool = boxOrderMapper.insertSelective(boxOrder) > 0;
    
    LockInfo lockInfo = new LockInfo();
    lockInfo.setId(lockId);
    lockInfo.setStatus(BasicContant.BoxContant.DISABLED);
    lockInfoMapper.updateByPrimaryKeySelective(lockInfo);

    return new RetInfo(bool, "下单");
  }

  private boolean isUsingLock(Integer id) {
    LockInfo lockInfo = lockInfoMapper.selectByPrimaryKey(id);
    if (lockInfo.getStatus() == BasicContant.BoxContant.DISABLED) {
      return true;
    }

    return false;
  }

  @Override
  public GrdData getBoxOrderList(BoxOrder boxOrder, Pager pager) {
    Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
    List<BoxOrder> list = boxOrderMapper.selectBoxOrder(boxOrder);
    transferOrderList(list);

    return new GrdData(page.getTotal(), list);
  }

  private void transferOrderList(List<BoxOrder> list) {
    Dbs.setDbName(Dbs.getMainDbName());
    for (BoxOrder boxOrder : list) {
      if (boxOrder.getWorkerId() != null) {
        User user = userService.selectByPrimaryKey(boxOrder.getWorkerId());
        boxOrder.setWorkerUser(user);
      }
      LockCommand lockCommand = lockCommandMapper.selectByLockNumber(boxOrder.getLockNumber());

      boxOrder.setLockName(lockCommand.getLockAddress());
    }
  }

  @Override
  public RetInfo workerOperateOrder(BoxOrder boxOrder) {
    // 如果是接单
    if (boxOrder.getStatus() == BoxOrderEnum.CONDUCT_ORDER.getCode() && isMaxOrder(boxOrder)) {
      return RetInfo.Error("接单已达上限");
    }
    
    // 更新订单状态
    boolean bool = updateOrder(boxOrder);

    updateLockInfo(boxOrder);

    return new RetInfo(bool, "订单更新");
  }

  private boolean isMaxOrder(BoxOrder boxOrder) {
    BoxOrderExample example = new BoxOrderExample();
    example.createCriteria().andStatusEqualTo(BoxOrderEnum.CONDUCT_ORDER.getCode())
        .andWorkerIdEqualTo(boxOrder.getWorkerId());
    List<BoxOrder> list = boxOrderMapper.selectByExample(example);
    // 如果到上限
    if (list.size() == BasicContant.IS_MAX_ORDER) {
      return true;
    }

    return false;
  }

  private void updateLockInfo(BoxOrder boxOrder) {
    // 锁禁止打开状态
    int status = BasicContant.BoxContant.DISABLED;
    // 如果是取消订单，恢复锁的状态
    if (boxOrder.getStatus() == BoxOrderEnum.REFUSE_ORDER.getCode()) {
      status = BasicContant.BoxContant.IDLE;
    }
    LockInfo lockInfo = new LockInfo();
    lockInfo.setId(boxOrder.getLockId());
    lockInfo.setStatus(status);

    lockInfoMapper.updateByPrimaryKeySelective(lockInfo);
  }

  private boolean updateOrder(BoxOrder boxOrder) {

    return boxOrderMapper.updateByPrimaryKeySelective(boxOrder) > 0;
  }

  /**
   * 师傅开锁
   * 
   * @param lockInfo
   * @return
   */
  private RetInfo workerOpenLock(LockInfo lockInfo) {
    int boxId = lockInfo.getBoxId();
    // 得到盒子标示符
    BoxInfo boxInfo = boxInfoMapper.selectByPrimaryKey(boxId);
    String boxIdentifier = boxInfo.getIdentifier();

    Dbs.setDbName(Dbs.getMainDbName());
    // 得到开箱子的指令
    LockCommand lockCommand =
        queryCommand(lockInfo.getLockNumber(), BasicContant.BoxContant.OPEN_LOCK);

    boolean bool = sendCommand(boxIdentifier, lockCommand.getCommand());

    String messgae = lockCommand.getLockAddress() + "开箱";
    return new RetInfo(bool, messgae, lockInfo.getId());
  }

  /**
   * 得到锁相关信息
   * 
   * @param lockId
   * @return
   */
  private LockInfo getLockInfoById(Integer lockId) {
    LockInfo lockInfo = lockInfoMapper.selectByPrimaryKey(lockId);

    return lockInfo;
  }

  private void transferMyBoxOrderList(List<BoxOrder> list) {
    Dbs.setDbName(Dbs.getMainDbName());
    for (BoxOrder boxOrder : list) {
      boxOrder.setStatusName(BoxOrderEnum.getOrdersName(boxOrder.getStatus()));
      boxOrder.setWorkerUser(getWorkerUser(boxOrder));
      boxOrder.setCreateTimeStr(AppUtils.date2String(boxOrder.getCreateTime(),
          AppUtils.TIME_FORMAT_TO_DATE_TIME2));
    }
  }

  private User getWorkerUser(BoxOrder boxOrder) {
    if (boxOrder.getWorkerId() == null) {
      return null;
    }

    return userService.selectByPrimaryKey(boxOrder.getWorkerId());
  }

  @Override
  public RetInfo workerOpenLock(Integer lockId) {
    LockInfo lockInfo = getLockInfoById(lockId);

    return workerOpenLock(lockInfo);
  }

  @Override
  public List<BoxOrder> getMyBoxOrderList(Integer customId, boolean isProcessing) {
    BoxOrderExample example = new BoxOrderExample();
    example.setOrderByClause("create_time desc");
    // 查询非完结的订单
    Criteria criteria = example.createCriteria().andCustomIdEqualTo(customId);
    if (isProcessing) {
      List<Integer> values =
          Lists.newArrayList(BoxOrderEnum.END_ORDER.getCode(),
              BoxOrderEnum.CUSTOM_REFUSE_ORDER.getCode());
      // 进行中，不查询完结和取消的订单
      criteria.andStatusNotIn(values);
    } else {
      // 历史订单，查询完结的订单
      criteria.andStatusEqualTo(BoxOrderEnum.END_ORDER.getCode());
    }

    List<BoxOrder> list = boxOrderMapper.selectByExample(example);

    transferMyBoxOrderList(list);
    return list;
  }

  @Override
  public RetInfo ownerOperateOrder(Integer boxOrderId, BigDecimal balance) {
    BoxOrder boxOrder = queryBoxOrderById(boxOrderId);
    Integer status = boxOrder.getStatus();

    // 取消订单
    Integer updateOrderStatus = BoxOrderEnum.CUSTOM_REFUSE_ORDER.getCode();
    // 完结
    if (status.intValue()== BoxOrderEnum.FINISH_ORDER.getCode()) {
      updateOrderStatus = BoxOrderEnum.END_ORDER.getCode();

      RetInfo retInfo = submitOrder(boxOrderId, boxOrder.getOrderId(), balance);
      if (retInfo.getRetCode().equals(RetInfo.ERROR)) {
        return retInfo;
      }
    }else if(status.intValue() == BoxOrderEnum.REFUSE_ORDER.getCode()) {
    	
    	updateOrderStatus = BoxOrderEnum.END_ORDER.getCode();
    	//作废该笔订单
    	 Orders o = new Orders();
         o.setId(boxOrder.getOrderId());
         o.setOrderStatus(0); // 作废
         int count = ordersMapper.updateByPrimaryKeySelective(o);
         if(count <= 0) {
        	 
        	  return new RetInfo(false, "操作失败");
         }
    }
    boolean bool = updateOrder(boxOrderId, updateOrderStatus);

    if (!bool) {
      return new RetInfo(bool, "操作失败");
    }

    // 开锁
    LockInfo lockInfo = getLockInfoById(boxOrder.getLockId());
    // 闲置该箱子
    lockInfo.setStatus(BasicContant.BoxContant.IDLE);
    lockInfoMapper.updateByPrimaryKeySelective(lockInfo);

    return workerOpenLock(lockInfo);
  }

  private boolean updateOrder(Integer orderId, Integer status) {
    BoxOrder boxOrder = new BoxOrder();
    boxOrder.setId(orderId);
    boxOrder.setStatus(status);

    return boxOrderMapper.updateByPrimaryKeySelective(boxOrder) > 0;
  }

  private BoxOrder queryBoxOrderById(Integer orderId) {

    return boxOrderMapper.selectByPrimaryKey(orderId);
  }

  @Override
  public RetInfo queryProcessingOrder(Integer customId) {
    List<BoxOrder> list = getMyBoxOrderList(customId, true);

    return RetInfo.SuccessData("操作成功", list);
  }

  @Override
  public List<LockInfo> queryAllLockList() {
    List<LockInfo> list = lockInfoMapper.selectByExample(null);
    transferLockList(list);

    return list;
  }

  private void transferLockList(List<LockInfo> list) {
    Map<Integer, String> boxMap = getAllBoxInfo();
    Map<Integer, String> lockMap = getAllLockName();

    for (LockInfo lockInfo : list) {
      String boxName = boxMap.get(lockInfo.getBoxId());
      lockInfo.setBoxName(boxName);

      String lockName = lockMap.get(lockInfo.getLockNumber());
      lockInfo.setLockName(lockName);

      String statusName = "闲置中";
      if (lockInfo.getStatus() == BasicContant.BoxContant.DISABLED) {
        statusName = "使用中";
      }
      lockInfo.setStatusName(statusName);
    }
  }

  /**
   * 得到所有盒子的名字
   * 
   * @return
   */
  private Map<Integer, String> getAllBoxInfo() {
    List<BoxInfo> list = boxInfoMapper.selectByExample(null);
    Map<Integer, String> map = Maps.newHashMap();

    for (BoxInfo boxInfo : list) {
      map.put(boxInfo.getId(), boxInfo.getRemark());
    }

    return map;
  }

  /**
   * 得到所有锁的名字
   * 
   * @return
   */
  private Map<Integer, String> getAllLockName() {
    Dbs.setDbName(Dbs.getMainDbName());
    List<LockCommand> list = lockCommandMapper.selectByExample(null);
    Map<Integer, String> map = Maps.newHashMap();

    for (LockCommand lockCommand : list) {
      map.put(lockCommand.getLockNumber(), lockCommand.getLockAddress());
    }

    return map;
  }

  @Override
  public RetInfo addBox(BoxInfo boxInfo) {
    Shops shop = shopsService.getById(boxInfo.getShopId());
    // 切到对应的库
    Dbs.setDbName(shop.getDbName());
    String listStr = boxInfo.getBoxInfoJsonList();
    List<BoxInfo> list = JSONObject.parseArray(listStr, BoxInfo.class);
    boolean bool = addBoxInfo(list);

    Dbs.setDbName(Dbs.getMainDbName());
    return new RetInfo(bool, "添加盒子");
  }

  /**
   * 添加盒子
   * 
   * @param list
   */
  private boolean addBoxInfo(List<BoxInfo> list) {
    boolean bool = true;
    for (BoxInfo boxInfo : list) {
      bool = boxInfoMapper.insertSelective(boxInfo) > 0;
      addLock(boxInfo);
    }

    return bool;
  }

  /**
   * 添加盒子对应的锁
   * 
   * @param boxInfo
   */
  private void addLock(BoxInfo boxInfo) {
    // 一个盒子里有12个锁
    int maxLockNumber = 12;
    for (int i = 1; i <= maxLockNumber; i++) {
      LockInfo lockInfo = new LockInfo();
      lockInfo.setLockNumber(i);
      lockInfo.setBoxId(boxInfo.getId());
      lockInfoMapper.insertSelective(lockInfo);
    }
  }

  private RetInfo submitOrder(Integer boxOrderId, Integer ordersId, BigDecimal balance) {
    // 待支付金额
    BigDecimal payAmount = getPayAmount(boxOrderId);
    // 待支付金额比账户余额大给提示
    if (payAmount != null && balance != null & payAmount.compareTo(balance) == 1) {
      return RetInfo.Error("账户余额不足，请联系门店充值");
    }

    // 生成支付信息
    OrdersPay ordersPay = getBoxOrdersPay(boxOrderId);
    ordersPayService.saveOrdersPay(ordersId, ordersPay);
    Orders order = ordersService.queryBoxOrderDetail(ordersId);
    order.setOrdersPayList(Lists.newArrayList(ordersPay));

    // 入账订单
    ordersService.submitOrder(order);

    return RetInfo.Success("成功");
  }

  private OrdersPay getBoxOrdersPay(Integer boxOrderId) {
    OrdersPay ordersPay = new OrdersPay();
    ordersPay.setAddTime(new Date());
    ordersPay.setCanEdit(1);
    ordersPay.setPayAmount(getPayAmount(boxOrderId));
    // 余額支付類型
    ordersPay.setPayId(4);
    ordersPay.setPayName("余额");

    return ordersPay;
  }

  private BigDecimal getPayAmount(Integer boxOrderId) {
    BoxOrder boxorder = boxOrderMapper.selectByPrimaryKey(boxOrderId);
    Serve serve = serveMapper.selectByPrimaryKey(boxorder.getServiceId());

    return serve.getPrice();
  }


  /**
   * 校验客户余额是否充足
   * 
   * @param boxOrder
   */
   @Override
   public RetInfo checkBalance(BoxOrder boxOrder) {
	   //判断客户余额是否充足
	    // 待支付金额
	    Serve serve = serveMapper.selectByPrimaryKey(boxOrder.getServiceId());
	    BigDecimal payAmount = serve.getPrice();
	    //查询客户的余额
		Custom custom = customMapper.selectByPrimaryKey(boxOrder.getCustomId());
		if(custom != null) {
		 // 待支付金额比账户余额大给提示
		 if (payAmount != null && custom.getBalance() != null & payAmount.compareTo(custom.getBalance()) == 1) {
			
			 return RetInfo.Error("您的余额不足请联系门店充值，否则无法取出钥匙，是否继续下单");
		 }
		}
		 return RetInfo.Success("成功");
	}
}
