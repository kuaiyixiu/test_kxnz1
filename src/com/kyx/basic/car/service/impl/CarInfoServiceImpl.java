package com.kyx.basic.car.service.impl;
import	java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.car.mapper.*;
import com.kyx.basic.car.model.*;
import com.kyx.basic.car.service.CarInfoService;
import com.kyx.basic.car.util.CarModelUtils;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

@Service("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {
    @Resource
    CarBrandMapper brandMapper;
    @Resource
    CarSeriesMapper seriesMapper;
    @Resource
    CarModelMapper modelMapper;
    @Resource
    CarCategoryMapper categoryMapper;

    @Resource
    CarVideoClassMapper carVideoClassMapper;
    @Resource
    CarVideoMapper carVideoMapper;

    /**
     * 下载车型信息
     * @param letter
     */
    @Override
    public void downloadCarInfo(String letter) {
        try{
            List<CarBrand> carBrands= getBrands(letter);
            FileWriter writer;
            try {
                System.out.println("保存数据开始");
                String str = JSON.toJSON(carBrands).toString();
                String basePath = CarModelUtils.getCarBasePath();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                String ldtStr = LocalDateTime.now().format(dtf);
                writer = new FileWriter(basePath + "carData-" + ldtStr + "-" + letter + ".json");
                writer.write(str);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("保存数据结束");
            System.out.println("统计: " + letter);
            System.out.println("品牌数:" + carBrands.size());
            int carSeriesCount = 0;
            int carModelsCount = 0;
            for (CarBrand brand : carBrands) {
                List<CarSeries> series = brand.getCarSeries();
                carSeriesCount += series.size();
                for (CarSeries carSeries : series) {
                    carModelsCount += carSeries.getCarModels().size();
                }
            }
            System.out.println("系列数:" + carSeriesCount);
            System.out.println("型号数:" + carModelsCount);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询类目
     * @return
     */
    @Override
    public List<CarCategory> queryCarCategoryList() {
        return categoryMapper.queryAll();
    }
    /**
     * 根据类目查询品牌
     * @param category
     * @return
     */
    @Override
    public List<CarBrand> queryCarBrandList(int category) {
        return brandMapper.queryByCategory(category);
    }

    /**
     * 根据品牌查询车系
     * @param brandId
     * @return
     */
    @Override
    public List<CarSeries> queryCarSeriesList(int brandId) {
        return seriesMapper.queryByBrand(brandId);
    }

    /**
     * 根据品牌和车系查询车型
     * @param brandId
     * @param seriesId
     * @return
     */
    @Override
    public List<CarModel> queryCarModelList(int seriesId) {
        return modelMapper.queryByBrandSeries(seriesId, null);
    }

    /**
     * 根据品牌和车系查询车型年代款
     * @param brandId
     * @param seriesId
     * @return
     */
    @Override
    public List<String> queryCarModelYears(int seriesId) {
        return modelMapper.queryCarModelYears(seriesId);
    }

    /**
     * 根据车系和年代, 查询车型列表数据
     * @param series 车系id
     * @param year 年代
     * @return
     */
    @Override
    public List<CarModel> queryCarModelList(Integer series, String year) {
        return modelMapper.queryByBrandSeries(series, year);
    }

    /**
     * 根据首字母, 获取汽车品牌信息
     * @param letter 首字母
     * @return
     */
    public List<CarBrand> getBrands(String letter) throws IOException {
        Document doc = Jsoup.connect("https://www.autohome.com.cn/grade/carhtml/" + letter + ".html").timeout(10000).get();
        Element body = doc.body();

        // 品牌列表
        Elements dl = body.select("dl");
        System.out.println(letter + " 开头的品牌有: "+dl.size());
        List<CarBrand> carBrands = new ArrayList<CarBrand>();
        // 遍历品牌
        for (int i = 0; i < dl.size(); i++) {
            CarBrand brand = new CarBrand();
            brand.setFirstChar(letter);

            Element element = dl.get(i);
            Elements select = element.select("dt div a");  // 品牌名称
            System.out.println(select.text());                      // 一级品牌名
            brand.setBrandName(select.text());

            Elements select1 = element.select("dt a img"); // 品牌logo
            System.out.println(select1.attr("src"));    // 一级品牌LOG
            brand.setLogoUrl(select1.attr("src"));

            CarModelUtils.downloadPicture(brand.getLogoUrl(), brand.getBrandName());
            brand.setLogoUrl("logo/" + brand.getBrandName() + ".png");


            /*******插入品牌*******/
            Dbs.setDbName(Dbs.getMainDbName());
            brandMapper.insertSelective(brand);

            // 车系列表
            Elements select2 = element.select("dd ul li h4");
            List<CarSeries> series = new ArrayList<>();
            for (int j = 0; j < select2.size(); j++) {
                CarSeries serie = new CarSeries();
                serie.setBrandId(brand.getId());

                Element element2 = select2.get(j);
                serie.setSeriesName(element2.text());
                /*****插入系列*****/
                Dbs.setDbName(Dbs.getMainDbName());
                seriesMapper.insertSelective(serie);

                getModels(serie, element2.select("a").attr("href"));
                series.add(serie);
            }

            brand.setCarSeries(series);
            carBrands.add(brand);
        }
        return carBrands;
    }

    /**
     * 根据车型信息地址, 爬取车型数据
     * @param series 车系
     * @param args 车型信息地址
     * @return
     * @throws IOException
     */
    private List<CarModel> getModels(CarSeries series, String args) throws IOException {
        List<CarModel> carModels = new ArrayList<>();
        System.out.println(args);
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.connect("https:" + args).timeout(999999).get();
        Element body = doc.body();
        // 加判断 判断新页面布局,旧页面布局
        Elements selectwra = body.select("div[class=wrapper] div[class=container]");
        if ("".equals(selectwra.text())) { //旧布局
            Elements select1 = body.select("div[class=name] a");
            for (Element ele : select1) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String href1 = "https://www.autohome.com.cn/" + ele.attr("href");
                System.out.println("旧布局  "+href1);
                Document href = Jsoup.connect(href1).timeout(999999).get();
                Element body1 = href.body();
                Elements select2 = body1.select("ul[class=baseinfo-list] li a");
                if(select2 != null && select2.size() > 0){
                    for (Element element : select2) {
                        if(element.text() != null && element.text().contains("更多参数")){
                            carModels.add(getModelInfo(series, substringHref(element.attr("href"))));
                        }
                    }
                }
            }
        }else{ //            新布局
            // 检索dl
            Elements select = body.select("div[class=spec-wrap active] dl dd");
            System.out.println(select.size());
            for (int i = 0; i < select.size(); i++) {
                Element element = select.get(i);
                Elements infoLinks = element.select("div[class=spec-info] div[class=info-link] a");
                if (infoLinks.size() > 0){
                    Element  a = infoLinks.get(infoLinks.size() - 1);
                    if("配置".equals(a.text())) {
                        carModels.add(getModelInfo(series, substringHref(a.attr("href"))));
                    }
                }
            }
            //   下载历史版本     获取参数一
            String href = body.select("div[class=athm-title__name athm-title__name--blue] a").attr("href");
            String s = href.split("/")[1];
            Elements select1 = body.select("ul[class=dropdown-con] li");
            for (Element element : select1) {
                String a = element.select("a").attr("data-yearid");
                String s1 = "https://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + s + "&y=" + a;
                System.out.println(s1);
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Document doc1 = Jsoup.connect("https://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + s + "&y=" + a)
                        .timeout(999999).get();
                JSONObject jsonObject = JSON.parseObject(doc1.body().text());
                List<JSONObject> group = (List<JSONObject>)jsonObject.get("Spec");
                if (group != null) {
                    for (int i = 0; i < group.size(); i++) {
                        Integer id = group.get(i).getInteger("Id");
                        carModels.add(getModelInfo(series, "/spec/"+id+".html"));
                    }
                }
            }
        }
        return carModels;
    }

    /**
     * 截取车型连接地址, 获取车型id的访问地址;
     * 解决通过配置按钮进入车型详情页时,地址变更的问题
     * @param href
     * @return
     */
    public String substringHref(String href){
        if(StringUtils.isBlank(href)){
            return null;
        }else {
            try {
                href = href.substring(href.indexOf("/spec/"), href.lastIndexOf("/config"));
                return href + ".html#pvareaid=3454569";
            }catch (Exception e) {
                return null;
            }
        }
    }
    /**
     * 根据车型连接, 车型数据
     * @param configHref
     * @return
     */
    private CarModel getModelInfo(CarSeries series, String configHref){
        String url = "https://car.autohome.com.cn/config" + configHref;
        System.out.println(url);
        if (StringUtils.isBlank(configHref)) {
            return null;
        }

        try{
            // 解决页面延迟加载信息数据的问题, 使用script解析css数据
            Connection.Response response = Jsoup.connect(url).validateTLSCertificates(false).ignoreContentType(true).ignoreHttpErrors(true).execute();
            Document document = response.parse();
            Elements scripts = document.select("script:containsData(insertRule)");

            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
            Map<String, String> cssKeyValue = new HashMap<>();
            //System.out.println("------------css数据------------");
            List<FormElement> forms = scripts.forms();
            for (FormElement element : forms) {
                String script = CarModelUtils.SCRIPT_PRE + element.html();
                try {
                    engine.eval(script);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                String css = (String) engine.get("rules");
                for (String str : css.split("\\|")) {
                    Matcher cssMatcher = CarModelUtils.CSS_PATTERN.matcher(str);
                    if (cssMatcher.find()) {
                        cssKeyValue.put("<span class='" + cssMatcher.group(1) + "'></span>", cssMatcher.group(2));
                    }
                }
            }
            Elements contents = document.select("script:containsData(keyLink)");
            String content = contents.html();
            //System.out.println("------------用css混淆的配置数据------------");
            //System.out.println(content);
            //把混淆数据里的样式用上面解析的样式给替代 (keyLink, config, option, bag, color, innerColor)
            for(Map.Entry<String, String> entry : cssKeyValue.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }
            if (StringUtils.isBlank(content)) {
                return null;
            }
            // 此处只获取config中车型配置信息, 获取截取的字符串
            String substring = content.substring(content.indexOf("var config = "), content.indexOf("var option = "));
            String json = substring.substring(substring.indexOf("{"), substring.lastIndexOf(";"));
            CarModel carModel = CarModelUtils.getCarModel(json);
            // 获取车型名称和年代
            Element first = document.select("div[class=subnav-title] div[class=subnav-title-name] a h1").first();
            if(first != null && first.text().length() > 0){
                carModel.setYear(first.text().split(" ")[0]);
            }
            if(carModel != null){
                carModel.setSeriesId(series.getId());
                carModel.setBrandId(series.getBrandId());
                Dbs.setDbName(Dbs.getMainDbName());
                modelMapper.insertSelective(carModel);
                series.addCarModel(carModel);
            }
            return carModel;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询视频类型列表
     * @return
     */
    @Override
    public List<CarVideoClass> queryCarVideoClass() {
        Dbs.setDbName(Dbs.getMainDbName());
        return carVideoClassMapper.queryAll();
    }

    /**
     * 按视频类型,车系和车型查询数据
     * @param classId 类型id
     * @param seriesId 车系id
     * @param modelId 车型id
     */
    @Override
    public List<CarVideo> queryCarVideoBySeriesId(Integer classId, Integer seriesId, Integer modelId) {
        Dbs.setDbName(Dbs.getMainDbName());
        return carVideoMapper.queryCarVideoBySeriesId(classId, seriesId, modelId);
    }

    /**
     * 根据id获取视频信息
     * @param videoId
     * @return
     */
    @Override
    public CarVideo queryCarVideoById(Integer videoId) {
        Dbs.setDbName(Dbs.getMainDbName());
        return carVideoMapper.queryCarVideoById(videoId);
    }

    @Override
    public List<CarModel> searchCarModelList(String search, Pager pager) {
        if (pager == null) {
            pager = new Pager();
        }
        PageHelper.startPage(pager.getPage(), pager.getLimit());
        List<CarModel> list = modelMapper.searchCarModelList(search);
        return list;
    }
}
