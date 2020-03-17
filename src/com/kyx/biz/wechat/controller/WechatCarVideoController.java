package com.kyx.biz.wechat.controller;
import	java.util.HashMap;

import com.kyx.basic.car.model.CarVideo;
import com.kyx.basic.car.service.CarInfoService;
import com.kyx.basic.db.Dbs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 车型视频管理
 */
@Controller
@RequestMapping("wechat/carVideo/")
public class WechatCarVideoController {

    @Resource
    private CarInfoService carInfoService;

    /**
     * 视频类型页面
     * @param series
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("toVideoClass")
    public String toVideoClass(Integer series, Integer modelId, String name, Model model){
        model.addAttribute("series", series);
        model.addAttribute("model", modelId);
        model.addAttribute("title", name);
        model.addAttribute("videoClass", carInfoService.queryCarVideoClass());
        return "car/videoClass";
    }

    /**
     * 视频列表页面
     * @param seriesId 车系id
     * @param modelId 车型id
     * @param name 页面标题
     * @return
     */
    @RequestMapping("toVideoList")
    public String toVideoList(Integer classId, Integer seriesId, Integer modelId, String title, Model model){
        model.addAttribute("title", title);
        model.addAttribute("videoList", carInfoService.queryCarVideoBySeriesId(classId, seriesId, modelId));
        return "car/videoList";
    }
    /**
     * 视频播放页面
     * @param videoId 视频id
     * @return
     */
    @RequestMapping("toVideoPlayer")
    public String toVideoPlayer(Integer videoId, Model model){
        Dbs.setDbName(Dbs.getMainDbName());
        CarVideo video = carInfoService.queryCarVideoById(videoId);
        video.setBrowseNum(video.getBrowseNum() + 1);
        model.addAttribute("video", video);
        return "car/carVideoPlayer";
    }
    /**
     * 检测用户类型, 视频扣费等信息
     * @param videoId 视频id
     * @return
     */
    @RequestMapping("checkUser")
    @ResponseBody
    public Map<String, Object> checkUser(Integer videoId, Integer member){
        HashMap<String, Object> map = new HashMap<>();
        map.put("videoId", videoId);
        Dbs.setDbName(Dbs.getMainDbName());
        CarVideo video = carInfoService.queryCarVideoById(videoId);

        map.put("code", 200);
        map.put("url", "wechat/carVideo/toVideoPlayer.do?videoId="+videoId);
        return map;
    }
}
