package com.kyx.biz.wechat.controller;

import com.kyx.basic.car.model.CarBrand;
import com.kyx.basic.car.model.CarCategory;
import com.kyx.basic.car.service.CarInfoService;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.BasicContant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 微信端显示车型信息
 */
@Controller
@RequestMapping(value = "/wechat/carModel")
public class WechatCarModelController {
    @Resource
    private CarInfoService carInfoService;
    @Resource
    private UserService userService;

    /**
     * 车型选择也
     *
     * @param model
     * @return
     */
    @RequestMapping("/indexs")
    public String indexs(Model model, HttpSession session) {
        Dbs.setDbName(Dbs.getMainDbName());
        String userName = "18715171217";
        User user = userService.querySingleUser(userName);
        session.setAttribute(BasicContant.MASTERWORKER_SESSION, user);

        List<CarCategory> categories = carInfoService.queryCarCategoryList();
        model.addAttribute("categorys", categories);
        return "car/carList";
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
     * 车系列表页
     *
     * @param brandId 品牌id
     * @param name    页面标题名称
     * @param model
     * @return
     */
    @RequestMapping("/series/{brandId}")
    public String toSeriesPage(@PathVariable Integer brandId, String name, Model model) {
        model.addAttribute("name", name);
        Dbs.setDbName(Dbs.getMainDbName());
        model.addAttribute("series", carInfoService.queryCarSeriesList(brandId));
        return "car/carSeries";
    }

    /**
     * 车型年代选择页面
     *
     * @param series 车系id
     * @param name   页面标题名称
     * @param model
     * @return
     */
    @RequestMapping("/model/year/{series}")
    public String toModelYear(@PathVariable Integer series, String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("series", series);
        Dbs.setDbName(Dbs.getMainDbName());
        model.addAttribute("years", carInfoService.queryCarModelYears(series));
        return "car/carModelYear";
    }

    /**
     * 车型选择页面
     *
     * @param year   年代
     * @param series 车系
     * @param name   页面标题名称
     * @param model
     * @return
     */
    @RequestMapping("/model/{year}")
    public String toModelYear(@PathVariable String year, Integer series, String name, Model model) {
        model.addAttribute("name", name);
        Dbs.setDbName(Dbs.getMainDbName());
        model.addAttribute("models", carInfoService.queryCarModelList(series, year));
        return "car/carModel";
    }
}
