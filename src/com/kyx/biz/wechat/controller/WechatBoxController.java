package com.kyx.biz.wechat.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.box.model.BoxOrder;
import com.kyx.biz.box.service.BoxService;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.serve.model.Serve;

@Controller
@RequestMapping(value = "/wechat/box")
public class WechatBoxController extends BaseController {

  @Resource
  private BoxService boxService;

  @Resource
  private CarService carService;

  /**
   * 开锁
   * 
   * @param model
   * @return
   */
  @PostMapping("/openLock")
  @ResponseBody
  public RetInfo openLock(String boxIdentifier, HttpSession session, HttpServletRequest request) {
    String dbName = (String) session.getAttribute(BasicContant.CURRENT_DB_NAME);

    return boxService.openLock(boxIdentifier, dbName, getShopId(request));
  }


  /**
   * 添加订单界面
   * 
   * @param model
   * @param lockNumber
   * @return
   */
  @RequestMapping("/addBoxOrderView")
  public String addBoxOrderView(Model model, Integer lockId, HttpServletRequest request) {
    model.addAttribute("lockId", lockId);
    Custom custom = getWechatCustom(request);
    model.addAttribute("custom", custom);

    List<Car> carList = carService.selectByCustomId(custom.getId());
    // 查询相关车辆信息，如果有自动填充
    if (!carList.isEmpty()) {
      Car car = carList.get(0);
      model.addAttribute("car", car);
    }

    Serve serve = new Serve();
    serve.setShopId(getShopId(request));
    model.addAttribute("serves", JSON.toJSONString(boxService.getBoxServes(serve)));

    return "box/addBoxOrderView";
  }

  @RequestMapping("/myBoxOrderView")
  public String myBoxOrderView(Model model, HttpServletRequest request) {

    return "box/myBoxOrderView";
  }

  /**
   * 下单
   * 
   * @param custom
   * @param request
   * @return
   */
  @PostMapping("/addBoxOrder")
  @ResponseBody
  public RetInfo addBoxOrder(BoxOrder boxOrder) {

    return boxService.addBoxOrder(boxOrder);
  }

  /**
   * 查询进行中的订单
   * 
   * @param request
   * @return
   */
  @PostMapping("/queryProcessingOrder")
  @ResponseBody
  public RetInfo addBoxOrder(HttpServletRequest request) {
    Custom custom = getWechatCustom(request);

    return boxService.queryProcessingOrder(custom.getId());
  }

  /**
   * 车主操作订单
   * 
   * @param orderId
   * @return
   */
  @PostMapping("/ownerOperateOrder")
  @ResponseBody
  public RetInfo ownerOperateOrder(Integer orderId, HttpServletRequest request) {
    Custom custom = getWechatCustom(request);
    return boxService.ownerOperateOrder(orderId, custom.getBalance());
  }

  /**
   * 校验余额是否充足
   * 
   * @param custom
   * @param request
   * @return
   */
  @PostMapping("/checkBalance")
  @ResponseBody
  public RetInfo checkBalance(BoxOrder boxOrder) {

    return boxService.checkBalance(boxOrder);
  }
}
