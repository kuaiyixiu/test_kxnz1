package com.kyx.basic.car.controller;

import com.kyx.basic.car.model.*;
import com.kyx.basic.car.service.CarInfoService;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.util.RetInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/web/carVideo")
public class CarVideoController {
    @Resource
    private CarInfoService carInfoService;

    /**
     * 根据类别id, 获取品牌列表
     * @return
     */
    @RequestMapping("/category")
    @ResponseBody
    public RetInfo getCarCategory(){
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarCategory> list = carInfoService.queryCarCategoryList();
        RetInfo ret = new RetInfo(list != null && list.size() >= 0 , "获取车系类目");
        ret.setRetData(list);
        return ret;
    }


    /**
     * 根据类别id, 获取品牌列表
     * @param category 类别id
     * @return
     */
    @RequestMapping("/brands")
    @ResponseBody
    public RetInfo getBrands(Integer category){
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarBrand> list = carInfoService.queryCarBrandList(category);
        RetInfo ret = new RetInfo(list != null && list.size() >= 0 , "获取车辆品牌列表");
        ret.setRetData(list);
        return ret;
    }


    /**
     * 车系列表
     * @param brandId 品牌id
     * @return
     */
    @RequestMapping("/series")
    @ResponseBody
    public RetInfo getSeries(Integer brandId){
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarSeries> list = carInfoService.queryCarSeriesList(brandId);
        RetInfo ret = new RetInfo(list != null && list.size() >= 0 , "获取品牌车系列表");
        ret.setRetData(list);
        return ret;
    }


    /**
     * 视频类型
     * @return
     */
    @RequestMapping("/videoClass")
    @ResponseBody
    public RetInfo getVideoClass(){
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarVideoClass> list = carInfoService.queryCarVideoClass();
        RetInfo ret = new RetInfo(list != null && list.size() >= 0 , "获取视频类型列表");
        ret.setRetData(list);
        return ret;
    }

    /**
     * 视频列表页面
     * @param seriesId 车系id
     * @param modelId 车型id
     * @param name 页面标题
     * @return
     */
    @RequestMapping("/videoList")
    @ResponseBody
    public RetInfo getVideoList(Integer classId, Integer seriesId, Integer modelId){
        Dbs.setDbName(Dbs.getMainDbName());
        List<CarVideo> list = carInfoService.queryCarVideoBySeriesId(classId, seriesId, modelId);
        RetInfo ret = new RetInfo(list != null && list.size() >= 0 , "获取视频列表");
        ret.setRetData(list);
        return ret;
    }

    /**
     * 获取视频信息
     * @param videoId 视频id
     * @return
     */
    @RequestMapping("/video")
    @ResponseBody
    public RetInfo getVideo(Integer videoId){
        Dbs.setDbName(Dbs.getMainDbName());
        CarVideo video = carInfoService.queryCarVideoById(videoId);
        RetInfo ret = new RetInfo(video != null, "获取视频信息列表");
        ret.setRetData(video);
        return ret;
    }

    /**
     * 获取视频播放地址
     * @param videoId 视频id
     * @return
     */
    @RequestMapping("/videoUrl")
    @ResponseBody
    public String getVideoUrl(Integer videoId){
        Dbs.setDbName(Dbs.getMainDbName());
        CarVideo video = carInfoService.queryCarVideoById(videoId);
        if(video == null) return "";
        return video.getDirUrl();
    }
}
