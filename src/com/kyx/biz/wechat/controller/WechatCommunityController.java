package com.kyx.biz.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.car.model.CarBrand;
import com.kyx.basic.car.model.CarCategory;
import com.kyx.basic.car.model.CarModel;
import com.kyx.basic.car.service.CarInfoService;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.sysparam.model.SysBasicInfo;
import com.kyx.basic.sysparam.service.SysBasicInfoService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.userloginlist.model.UserLoginList;
import com.kyx.basic.userloginlist.service.UserLoginListService;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.Common;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechat.service.WechatCommunityService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/wechat/community")
public class WechatCommunityController {
    @Resource
    private UserService userService;
    @Resource
    private WechatCommunityService wechatCommunityService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private UserLoginListService userLoginListService;
    @Resource
    private CarInfoService carInfoService;
    @Resource
    private SysBasicInfoService sysBasicInfoService;

    /**
     * 注册用户
     *
     * @return
     */
    @RequestMapping("/regIndex")
    public String regIndex(HttpSession session, Model model) {
        if (session.getAttribute(BasicContant.MASTERWORKER_SESSION) != null) {
            return "redirect:/wechat/community.do";
        }
        Object attribute = session.getAttribute(BasicContant.WXMPUSER_SESSION);
        if (attribute != null) {
            WxMpUser wxMpUser = (WxMpUser) attribute;
            model.addAttribute("userPhoto", wxMpUser.getHeadImgUrl());
            model.addAttribute("userRealName", wxMpUser.getNickname());
            model.addAttribute("userSex", wxMpUser.getSex() == 1 ? 1 : 0);
            model.addAttribute("userAddress", (wxMpUser.getProvince() == null ? "" : wxMpUser.getProvince()) +
                    (wxMpUser.getCity() == null ? "" : wxMpUser.getCity()));
        }
        return "community/regIndex";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(module = "微信社区员工管理", description = "注册员工")
    public RetInfo register(String userJson, HttpSession session) {
        if (StringUtils.isBlank(userJson)) {
            return new RetInfo(false, "注册信息错误, 请重新填写!");
        }
        User user = JSON.parseObject(userJson, User.class);
        Dbs.setDbName(Dbs.getMainDbName());
        RetInfo retInfo = userService.wechatRegisterUser(user);
        if (RetInfo.SUCCESS.equals(retInfo.getRetCode())) {
            session.setAttribute(BasicContant.MASTERWORKER_SESSION, retInfo.getRetData());
            wechatCommunityService.addWechatCommunity(session, user.getUserPhone());
        }
        return retInfo;
    }


    /**
     * 用户登录验证
     *
     * @param account  登录用户名
     * @param password 密码
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RetInfo login(String account, String password, HttpSession session, HttpServletRequest request) {
        if (Common.isEmpty(account) || Common.isEmpty(password)) {
            return new RetInfo(false, "用户名或密码不能为空!");
        } else {
            Dbs.setDbName(Dbs.getMainDbName());
            User user = userService.queryExistUserName(account);
            if (user == null || !passwordEncoder.matches(password, user.getUserPassword())) {
                return new RetInfo("error", "用户或密码不正确！");
            } else if (!"1".equals(user.getEnable())) {
                return new RetInfo(false, "当前用户不可用, 请联系管理员!");
            } else {
                // 记录登录信息
                UserLoginList userLoginList = new UserLoginList();
                userLoginList.setUserId(user.getId());
                userLoginList.setLoginTime(new Date());
                userLoginList.setLoginIp(Common.toIpAddr(request));
                userLoginList.setShopId(user.getShopId());
                userLoginListService.add(userLoginList);
                session.setAttribute(BasicContant.MASTERWORKER_SESSION, user);
                wechatCommunityService.addWechatCommunity(session, user.getUserName());
                return new RetInfo(true, "登陆成功!");
            }
        }

    }

    /**
     * 忘记密码
     *
     * @return
     */
    @RequestMapping("/pwdIndex")
    public String pwdIndex() {
        return "community/pwdIndex";
    }

    @RequestMapping("/page/{pageName}.do")
    public String page(@PathVariable("pageName") String pageName) {
        return "community/" + pageName;
    }

    @RequestMapping("/category")
    @ResponseBody
    public List<CarCategory> category() {
        Dbs.setDbName(Dbs.getMainDbName());
        return carInfoService.queryCarCategoryList();
    }

    /**
     * 车型选择也
     *
     * @param model
     * @return
     */
    @RequestMapping("/page/vehicle")
    public String indexs(Model model, HttpSession session) {
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarCategory> categories = carInfoService.queryCarCategoryList();
        model.addAttribute("categorys", categories);
        return "community/vehicle";
    }

    /**
     * 根据类别id, 获取品牌列表
     *
     * @param category 类别id
     * @return
     */
    @RequestMapping("/brands")
    @ResponseBody
    public List<CarBrand> getBrands(Integer category) {
        Dbs.setDbName(Dbs.getMainDbName());
        return carInfoService.queryCarBrandList(category);
    }

    /**
     * 轮播图片
     *
     * @param category 类别id
     * @return
     */
    @RequestMapping("/banners")
    @ResponseBody
    public List<SysBasicInfo> banners() {
        Dbs.setDbName(Dbs.getMainDbName());
        return sysBasicInfoService.queryBasicInfosByFlag(SysBasicInfo.BANNERS);
    }

    /**
     * 轮播图片
     *
     * @param category 类别id
     * @return
     */
    @RequestMapping("/car/search")
    @ResponseBody
    public List<CarModel> carSearch(String search, Pager pager) {
        Dbs.setDbName(Dbs.getMainDbName());
        return carInfoService.searchCarModelList(search, pager);
    }


}
