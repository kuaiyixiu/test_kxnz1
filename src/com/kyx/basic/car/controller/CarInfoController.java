package com.kyx.basic.car.controller;
import	java.util.ArrayList;

import com.kyx.basic.car.service.CarInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 网页端汽车车型信息
 *
 */
@Controller
@RequestMapping(value = "/carInfo")
public class CarInfoController {
    @Resource
    private CarInfoService carInfoService;

    /**
     * 下载车型信息页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        List<String> letters = new ArrayList<String> ();
        for(int i=0; i<26 ; i++){
            letters.add(String.valueOf(((char)(65 + i))));
        }
        model.addAttribute("letters", letters);
        return "car/info";
    }

    /**
     * 下载车型数据
     * @param all 是否下载全部车型
     * @param params 需要下载车型数组字符串
     * @return
     */
    @RequestMapping("download")
    @ResponseBody
    public Map<String, Object> download(boolean all, String params){
        System.out.println("all = "+ all + ", params = "+ params);
        Map<String, Object> result = new HashMap<String, Object>();

        final List<String> letters = new ArrayList<String>();
        if(all){
            for(int i=0; i<26; i++){
                letters.add(String.valueOf((char)(65+i)));
            }
        }else{
            if(StringUtils.isEmpty(params)) {
                result.put("code", 500);
                result.put("msg", "未选择首字母, 无法下载!");
                return result;
            }
            letters.addAll(Arrays.asList(params.split(",")));
        }

        // 创建线程池, 按首字母下载相应车型信息
        ExecutorService service = Executors.newFixedThreadPool(letters.size());
        for (final String letter : letters) {
            service.submit(new Runnable() {
                public void run() {
                    carInfoService.downloadCarInfo(letter);
                }
            });
        }
        service.shutdown();

        result.put("code", 200);
        result.put("msg", "开始下载!");
        return result;
    }
}
