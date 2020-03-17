package com.kyx.basic.car.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.car.model.CarModel;
import com.kyx.basic.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class CarModelUtils {
    /**
     * 汽车品牌图片保存路径, 相对 FileUploadUtil.getPath()
     */
    private static final String CAR_PATH = "car/";

    public static final String CAR_LOGO_PATH = "logo/";
    /**
     * 用于解析汽车之家script
     */
    public final static String SCRIPT_PRE = "var rules = '';var document = {};document.createElement = function() {return {sheet: {insertRule: " +
            "function(rule, i) {if (rules.length == 0) {rules = rule;} else {rules = rules + '|' + rule;}}}}};document.getElementsByTagName = " +
            "function() {};document.querySelectorAll = function() {return {};};document.head = {};document.head.appendChild = function() " +
            "{};var window = {};window.decodeURIComponent = decodeURIComponent;window.location = {};window.location.href = 'car.m.autohome.com.cn';";

    /**
     * 查询css样式正则
     */
    public final static Pattern CSS_PATTERN = Pattern.compile("\\.(.*)::before.*content:\"(.*)\".*");

    /**
     * 根据config的json字符串, 获取车型配置信息
     * @param resultJson
     * @return
     */
    public static List<CarParamtypeitems> getCarParameters(String resultJson){
        JSONObject json = JSONObject.parseObject(resultJson);
        JSONObject result = json.getJSONObject("result");
        if(result == null){
            return null;
        }else{
            String paramtypeitems = result.getJSONArray("paramtypeitems").toJSONString();
            List<CarParamtypeitems> object = JSON.parseArray(paramtypeitems, CarParamtypeitems.class);
            /*for (CarParamtypeitems p : object) {
                System.out.println(p.getName() + "  "+p.getParamitems());
            }*/
            return object;
        }
    }

    /**
     * 根据config的json字符串, 获取车型数据
     * @param resultJson
     * @return
     */
    public static CarModel getCarModel(String resultJson){
        return getCarModel(getCarParameters(resultJson));
    }
    /**
     * 根据config的json字符串, 获取车型数据
     * @param params 车型详情数据每一个类目数据集合
     * @return
     */
    public static CarModel getCarModel(List<CarParamtypeitems> params){
        if(params == null) return null;
        CarModel carModel = new CarModel();
        for (CarParamtypeitems param : params) {
            List<CarParamitems> paramitems = param.getParamitems();
            if(param != null){
                if("基本参数".equals(param.getName())){
                    carModel.setModelName(getCarModelValue(paramitems, "1_135")); // 车型名称
                    carModel.setEngine(getCarModelValue(paramitems, "1_22")); // 发动机
                }else if("电动机".equals(param.getName())){
                    carModel.setEngineModel(getCarModelValue(paramitems, "1_94")); //电动机型号
                }else if("发动机".equals(param.getName())){
                    carModel.setEngineModel(getCarModelValue(paramitems, "1_117")); //发动机型号
                    carModel.setDisplacement(getCarModelValue(paramitems, "1_161")); //排量(L)
                }else if("变速箱".equals(param.getName())){
                    carModel.setGearsNum(parseInt(getCarModelValue(paramitems, "1_163"))); //挡位个数
                    carModel.setGearbox(getCarModelValue(paramitems, "1_62")); // 变速箱类型
                    carModel.setGear(getCarModelValue(paramitems, "1_68")); // 简称
                }
            }
        }
        return carModel;
    }

    /**
     * @param paramitems
     * @param pnid
     * @return
     */
    private static String getCarModelValue(List<CarParamitems> paramitems, String pnid){
        if(paramitems == null || paramitems.isEmpty() || StringUtils.isBlank(pnid)) return null;
        for (CarParamitems item : paramitems) {
            if(pnid.equals(item.getPnid())){
                List<CarValueitems> valueitems = item.getValueitems();
                if(valueitems == null || valueitems.isEmpty()) return null;
                return valueitems.get(0).getValue();
            }
        }
        return "电动";
    }

    private static Integer parseInt(String value){
        try {
            return Integer.parseInt(value);
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 车型信息保存位置
     * @return
     * @throws IOException
     */
    public static String getCarBasePath(){
        return getCarBasePath("");
    }

    public static String getCarBasePath(String chilepath){
        String path = FileUploadUtil.getPath() + CAR_PATH + chilepath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    /**
     * 链接url下载图片
     * @param urlList 图片地址
     * @param fileName 图片名称
     */
    public static String downloadPicture(String urlList, String fileName) {
        String path = null;
        try {
            // 数据库保存图片地址
            path = CAR_PATH + CAR_LOGO_PATH + fileName + ".png";

            String filePath = getCarBasePath(CAR_LOGO_PATH) + path;
            System.out.println("图片地址: "+filePath);
            URL url = new URL("http:" + urlList);

            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    public static void main(String[] args){
        String result ="{\"message\":\"成功\",\"result\":{\"paramtypeitems\":[{\"name\":\"基本参数\",\"paramitems\":[{\"id\":0,\"name\":\"车型名称\",\"pnid\":\"1_135\",\"valueitems\":[{\"specid\":20844,\"value\":\"Huayra 2016款 Dinastia 特别版\"}]},{\"id\":0,\"name\":\"厂商指导价(元)\",\"pnid\":\"1_45\",\"valueitems\":[{\"specid\":20844,\"value\":\"2900.00万\"}]},{\"id\":52,\"name\":\"厂商\",\"pnid\":\"1_183\",\"valueitems\":[{\"specid\":20844,\"value\":\"帕加尼\"}]},{\"id\":53,\"name\":\"级别\",\"pnid\":\"1_66\",\"valueitems\":[{\"specid\":20844,\"value\":\"跑车\"}]},{\"id\":1149,\"name\":\"能源类型\",\"pnid\":\"1_-1\",\"valueitems\":[{\"specid\":20844,\"value\":\"汽油\"}]},{\"id\":1311,\"name\":\"环保标准\",\"pnid\":\"1_181\",\"valueitems\":[{\"specid\":20844,\"value\":\"欧V\"}]},{\"id\":0,\"name\":\"上市时间\",\"pnid\":\"1_70\",\"valueitems\":[{\"specid\":20844,\"value\":\"2015.11\"}]},{\"id\":1185,\"name\":\"最大功率(kW)\",\"pnid\":\"1_3\",\"valueitems\":[{\"specid\":20844,\"value\":\"551.5\"}]},{\"id\":1186,\"name\":\"最大扭矩(N·m)\",\"pnid\":\"1_177\",\"valueitems\":[{\"specid\":20844,\"value\":\"1000\"}]},{\"id\":1150,\"name\":\"发动机\",\"pnid\":\"1_22\",\"valueitems\":[{\"specid\":20844,\"value\":\"6.0T 750马力 V12\"}]},{\"id\":1245,\"name\":\"变速箱\",\"pnid\":\"1_11\",\"valueitems\":[{\"specid\":20844,\"value\":\"7挡序列\"}]},{\"id\":1148,\"name\":\"长*宽*高(mm)\",\"pnid\":\"1_107\",\"valueitems\":[{\"specid\":20844,\"value\":\"4605*2036*1169\"}]},{\"id\":1147,\"name\":\"车身结构\",\"pnid\":\"1_140\",\"valueitems\":[{\"specid\":20844,\"value\":\"2门2座硬顶跑车\"}]},{\"id\":1246,\"name\":\"最高车速(km/h)\",\"pnid\":\"1_170\",\"valueitems\":[{\"specid\":20844,\"value\":\"370\"}]},{\"id\":1250,\"name\":\"官方0-100km/h加速(s)\",\"pnid\":\"1_193\",\"valueitems\":[{\"specid\":20844,\"value\":\"3.2\"}]},{\"id\":1252,\"name\":\"实测0-100km/h加速(s)\",\"pnid\":\"1_80\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1253,\"name\":\"实测100-0km/h制动(m)\",\"pnid\":\"1_33\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1251,\"name\":\"工信部综合油耗(L/100km)\",\"pnid\":\"1_92\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1254,\"name\":\"实测油耗(L/100km)\",\"pnid\":\"1_212\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1255,\"name\":\"整车质保\",\"pnid\":\"1_129\",\"valueitems\":[{\"specid\":20844,\"value\":\"待查\"}]}]},{\"name\":\"车身\",\"paramitems\":[{\"id\":5886,\"name\":\"长度(mm)\",\"pnid\":\"1_151\",\"valueitems\":[{\"specid\":20844,\"value\":\"4605\"}]},{\"id\":5887,\"name\":\"宽度(mm)\",\"pnid\":\"1_105\",\"valueitems\":[{\"specid\":20844,\"value\":\"2036\"}]},{\"id\":5888,\"name\":\"高度(mm)\",\"pnid\":\"1_168\",\"valueitems\":[{\"specid\":20844,\"value\":\"1169\"}]},{\"id\":1169,\"name\":\"轴距(mm)\",\"pnid\":\"1_38\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1170,\"name\":\"前轮距(mm)\",\"pnid\":\"1_46\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":0,\"name\":\"后轮距(mm)\",\"pnid\":\"1_78\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1268,\"name\":\"最小离地间隙(mm)\",\"pnid\":\"1_42\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1147,\"name\":\"车身结构\",\"pnid\":\"1_140\",\"valueitems\":[{\"specid\":20844,\"value\":\"硬顶跑车\"}]},{\"id\":1172,\"name\":\"车门数(个)\",\"pnid\":\"1_167\",\"valueitems\":[{\"specid\":20844,\"value\":\"2\"}]},{\"id\":1173,\"name\":\"座位数(个)\",\"pnid\":\"1_59\",\"valueitems\":[{\"specid\":20844,\"value\":\"2\"}]},{\"id\":1174,\"name\":\"油箱容积(L)\",\"pnid\":\"1_75\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1175,\"name\":\"行李厢容积(L)\",\"pnid\":\"1_199\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":0,\"name\":\"整备质量(kg)\",\"pnid\":\"1_82\",\"valueitems\":[{\"specid\":20844,\"value\":\"1350\"}]}]},{\"name\":\"发动机\",\"paramitems\":[{\"id\":0,\"name\":\"发动机型号\",\"pnid\":\"1_117\",\"valueitems\":[{\"specid\":20844,\"value\":\"M158\"}]},{\"id\":1182,\"name\":\"排量(mL)\",\"pnid\":\"1_39\",\"valueitems\":[{\"specid\":20844,\"value\":\"5980\"}]},{\"id\":0,\"name\":\"排量(L)\",\"pnid\":\"1_161\",\"valueitems\":[{\"specid\":20844,\"value\":\"6.0\"}]},{\"id\":1183,\"name\":\"进气形式\",\"pnid\":\"1_35\",\"valueitems\":[{\"specid\":20844,\"value\":\"双涡轮增压\"}]},{\"id\":1184,\"name\":\"气缸排列形式\",\"pnid\":\"1_169\",\"valueitems\":[{\"specid\":20844,\"value\":\"V\"}]},{\"id\":1191,\"name\":\"气缸数(个)\",\"pnid\":\"1_108\",\"valueitems\":[{\"specid\":20844,\"value\":\"12\"}]},{\"id\":1192,\"name\":\"每缸气门数(个)\",\"pnid\":\"1_146\",\"valueitems\":[{\"specid\":20844,\"value\":\"3\"}]},{\"id\":1193,\"name\":\"压缩比\",\"pnid\":\"1_223\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1194,\"name\":\"配气机构\",\"pnid\":\"1_91\",\"valueitems\":[{\"specid\":20844,\"value\":\"SOHC\"}]},{\"id\":1195,\"name\":\"缸径(mm)\",\"pnid\":\"1_118\",\"valueitems\":[{\"specid\":20844,\"value\":\"82.6\"}]},{\"id\":1196,\"name\":\"行程(mm)\",\"pnid\":\"1_60\",\"valueitems\":[{\"specid\":20844,\"value\":\"93\"}]},{\"id\":1294,\"name\":\"最大马力(Ps)\",\"pnid\":\"1_131\",\"valueitems\":[{\"specid\":20844,\"value\":\"750\"}]},{\"id\":1185,\"name\":\"最大功率(kW)\",\"pnid\":\"1_3\",\"valueitems\":[{\"specid\":20844,\"value\":\"551.5\"}]},{\"id\":1278,\"name\":\"最大功率转速(rpm)\",\"pnid\":\"1_204\",\"valueitems\":[{\"specid\":20844,\"value\":\"5800\"}]},{\"id\":1186,\"name\":\"最大扭矩(N·m)\",\"pnid\":\"1_177\",\"valueitems\":[{\"specid\":20844,\"value\":\"1000\"}]},{\"id\":1279,\"name\":\"最大扭矩转速(rpm)\",\"pnid\":\"1_53\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":0,\"name\":\"发动机特有技术\",\"pnid\":\"1_32\",\"valueitems\":[{\"specid\":20844,\"value\":\"-\"}]},{\"id\":1280,\"name\":\"燃料形式\",\"pnid\":\"1_41\",\"valueitems\":[{\"specid\":20844,\"value\":\"汽油\"}]},{\"id\":1187,\"name\":\"燃油标号\",\"pnid\":\"1_188\",\"valueitems\":[{\"specid\":20844,\"value\":\"95号\"}]},{\"id\":1188,\"name\":\"供油方式\",\"pnid\":\"1_76\",\"valueitems\":[{\"specid\":20844,\"value\":\"直喷\"}]},{\"id\":1189,\"name\":\"缸盖材料\",\"pnid\":\"1_123\",\"valueitems\":[{\"specid\":20844,\"value\":\"铝合金\"}]},{\"id\":1190,\"name\":\"缸体材料\",\"pnid\":\"1_21\",\"valueitems\":[{\"specid\":20844,\"value\":\"铝合金\"}]},{\"id\":1311,\"name\":\"环保标准\",\"pnid\":\"1_181\",\"valueitems\":[{\"specid\":20844,\"value\":\"欧V\"}]}]},{\"name\":\"变速箱\",\"paramitems\":[{\"id\":1295,\"name\":\"挡位个数\",\"pnid\":\"1_163\",\"valueitems\":[{\"specid\":20844,\"value\":\"7\"}]},{\"id\":1230,\"name\":\"变速箱类型\",\"pnid\":\"1_62\",\"valueitems\":[{\"specid\":20844,\"value\":\"序列变速箱\"}]},{\"id\":0,\"name\":\"简称\",\"pnid\":\"1_68\",\"valueitems\":[{\"specid\":20844,\"value\":\"7挡序列\"}]}]},{\"name\":\"底盘转向\",\"paramitems\":[{\"id\":1231,\"name\":\"驱动方式\",\"pnid\":\"1_106\",\"valueitems\":[{\"specid\":20844,\"value\":\"中置后驱\"}]},{\"id\":1226,\"name\":\"前悬架类型\",\"pnid\":\"1_178\",\"valueitems\":[{\"specid\":20844,\"value\":\"双叉臂式独立悬架\"}]},{\"id\":1227,\"name\":\"后悬架类型\",\"pnid\":\"1_154\",\"valueitems\":[{\"specid\":20844,\"value\":\"双叉臂式独立悬架\"}]},{\"id\":1225,\"name\":\"助力类型\",\"pnid\":\"1_69\",\"valueitems\":[{\"specid\":20844,\"value\":\"电动助力\"}]},{\"id\":75,\"name\":\"车体结构\",\"pnid\":\"1_130\",\"valueitems\":[{\"specid\":20844,\"value\":\"承载式\"}]}]},{\"name\":\"车轮制动\",\"paramitems\":[{\"id\":1219,\"name\":\"前制动器类型\",\"pnid\":\"1_121\",\"valueitems\":[{\"specid\":20844,\"value\":\"陶瓷通风盘式\"}]},{\"id\":1220,\"name\":\"后制动器类型\",\"pnid\":\"1_95\",\"valueitems\":[{\"specid\":20844,\"value\":\"陶瓷通风盘式\"}]},{\"id\":1200,\"name\":\"驻车制动类型\",\"pnid\":\"1_81\",\"valueitems\":[{\"specid\":20844,\"value\":\"手刹\"}]},{\"id\":1222,\"name\":\"前轮胎规格\",\"pnid\":\"1_85\",\"valueitems\":[{\"specid\":20844,\"value\":\"255/35 R19\"}]},{\"id\":1223,\"name\":\"后轮胎规格\",\"pnid\":\"1_98\",\"valueitems\":[{\"specid\":20844,\"value\":\"335/30 R20\"}]},{\"id\":1224,\"name\":\"备胎规格\",\"pnid\":\"1_112\",\"valueitems\":[{\"specid\":20844,\"value\":\"无\"}]}]}],\"speclist\":[{\"showstate\":-1,\"specid\":20844,\"specstate\":20}],\"yearid\":0},\"returncode\":\"0\",\"taskid\":\"617df5dc-269c-42ad-b372-d668e3f37079\",\"time\":\"2020-02-06 14:52:39\"}";
        getCarParameters(result);

    }


}
