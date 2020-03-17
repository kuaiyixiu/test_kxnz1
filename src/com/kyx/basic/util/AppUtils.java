package com.kyx.basic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechat.vo.WechartSendVo;

public class AppUtils {

  // 精确到天的时间格式
  private static String TIME_FORMAT_TO_DAY = "yyyy-MM-dd";

  // 精确到时分秒的时间格式
  public static String TIME_FORMAT_TO_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

  // 精确到时分秒的时间格式
  public static String TIME_FORMAT_TO_DATE_TIME2 = "yyyy-MM-dd HH:mm";

  // 会员id默认首字母
  private static String CUSTOM_DEFAULT_ID_LETTER = "L";

  // 门店id最大长度
  private static int SHOP_ID_MAX_LENGTH = 4;

  // 会员id最大长度
  private static int CUSTOM_ID_MAX_LENGTH = 6;

  // dbname最大长度
  private static int DB_NAME_MAX_LENGTH = 3;

  // 门店唯一标示最大长度
  private static int SHOP_KEY_MAX_LENGTH = 4;

  public static String getReturnInfo(RetInfo retInfo) {
    return JSONObject.toJSON(retInfo).toString();
  }

  public static Date string2Date1(String time) {

    return string2Date(time, TIME_FORMAT_TO_DAY);
  }

  /**
   * 字符串转时间
   * 
   * @param time
   * @return
   * @throws ParseException
   */
  private static Date string2Date(String time, String timeFormat) {
    Date date = null;

    SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
    try {
      date = sdf.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return date;
  }

  /**
   * 创建id
   * 
   * @return
   */
  public static String createId(Integer shopId, Integer id) {
    String shopID = transferId(String.valueOf(shopId), SHOP_ID_MAX_LENGTH);
    String ID = transferId(String.valueOf(id), CUSTOM_ID_MAX_LENGTH);

    return CUSTOM_DEFAULT_ID_LETTER + shopID + ID;
  }

  public static String date2String1(Date date) {
    if (date == null)
      return "";
    return date2String(date, TIME_FORMAT_TO_DAY);
  }

  /**
   * 时间转字符串
   * 
   * @param date
   * @param timeFormat
   * @return
   */
  public static String date2String(Date date, String timeFormat) {
    if (date == null) {
      return "";
    }

    SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
    String dateStr = sdf.format(date);

    return dateStr;
  }

  /**
   * <pre>
   * 得到当前时间前/后几个月时间 
   * monthNum 3:后三个月，-3:前三个月
   * </pre>
   * 
   * @param monthNum
   * @return
   */
  public static String getDayByMonth(int monthNum) {
    Calendar cal = Calendar.getInstance();
    cal.add(cal.MONTH, monthNum);
    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
    String date = dft.format(cal.getTime());

    return date;
  }

  // 使用
  public static String USING = "1";

  // 禁用
  public static String FORBID = "0";

  /**
   * <pre>
   * 得到当前时间前/后几年时间 
   * yearNum 3:后3年，-3:前三年
   * </pre>
   * 
   * @param yearNum
   * @return
   */
  public static String getDayByYear(int yearNum) {
    Calendar cal = Calendar.getInstance();
    cal.add(cal.YEAR, yearNum);
    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
    String date = dft.format(cal.getTime());

    return date;
  }

  public static String getOptUserName(HttpSession httpSession) {
    String name = "";
    if (httpSession != null) {
      User user = (User) httpSession.getAttribute("userSession");
      if (user != null)
        name = user.getUserRealname();
    }

    return name;
  }

  /**
   * id按照最大限制转换，如果小于则补0
   * 
   * @param id
   * @param maxLeng
   * @return
   */
  private static String transferId(String id, int maxLeng) {
    String fillId = "0";
    int idLengh = id.length();
    if (idLengh >= 4) {
      return id.substring(0, 4);
    }

    String resultId = "";
    for (int i = 0; i < maxLeng - idLengh; i++) {
      resultId += fillId;
    }

    return resultId + id;
  }

  /**
   * 创建dbname
   * 
   * @param id
   * @return
   */
  public static String createDbName(Integer id) {
    String isStr = String.valueOf(id);
    String dbName = transferId(isStr, DB_NAME_MAX_LENGTH);
    String dbNamePre = BasicContant.shopsContant.DB_NAME_PREFIX;

    return dbNamePre + dbName;
  }

  /**
   * 创建门店唯一标示
   * 
   * @param id
   * @return
   */
  public static String createShopKey(Integer id) {
    String isStr = String.valueOf(id);
    String shopKey = transferId(isStr, SHOP_KEY_MAX_LENGTH);

    return "1" + shopKey;
  }

  /**
   * 得到传入时间前/后几天
   * 
   * @param cycle
   * @return
   */
  public static Date getDayByCycle(Date time, int cycle) {
    if (time == null) {
      return time;
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.add(cal.DAY_OF_MONTH, cycle);

    return cal.getTime();
  }

  /**
   * 两时间差多少天
   * 
   * @param endTime
   * @param startTime
   * @return
   */
  public static int differentDaysByMillisecond(Date endTime, Date startTime) {
    int days = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 3600 * 24));
    return days;
  }

  public static Integer getShopId(HttpSession session) {
    Integer id = null;
    Shops shops = (Shops) session.getAttribute("shopsSession");
    if (shops != null)
      id = shops.getId();
    return id;
  }

  public static WechartSendVo getWechatConfig(WechatConfig wechatConfig) {
    WechartSendVo wechartSendVo = new WechartSendVo();
    wechartSendVo.setAppId(wechatConfig.getAppid());
    wechartSendVo.setAppSecret(wechatConfig.getAppsecret());
    wechartSendVo.setAesKey(wechatConfig.getEncodingaeskey());
    wechartSendVo.setToken(wechatConfig.getToken());
    wechartSendVo.setWechatId(wechatConfig.getId());
    return wechartSendVo;
  }

  /**
   * 得到零时零分零秒
   * 
   * @param time
   * @return
   */
  public static Date getZeroTime(Date time) {
    if (time == null) {
      return time;
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);

    return cal.getTime();
  }

  public static Date getEndTime(Date time) {
    if (time == null) {
      return time;
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.set(Calendar.HOUR, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);

    return cal.getTime();
  }

  public static Date dateAdd(Date time, int day) {
    if (time == null) {
      return time;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.add(Calendar.DAY_OF_MONTH, day);
    return cal.getTime();
  }

  public static Date MonthAdd(Date time, int month) {
    if (time == null) {
      return time;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.add(Calendar.MONTH, month);
    return cal.getTime();
  }

  public static Date getWechatExpiredTime(Date time) {
    if (time == null) {
      return time;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    cal.add(Calendar.HOUR, 1);
    cal.add(Calendar.MINUTE, 57);

    return cal.getTime();
  }

  public static boolean compareTime(Date time1, Date time2) {
    boolean bool = false;
    time1 = getWechatExpiredTime(time1);
    if (time1.getTime() > time2.getTime()) {
      bool = true;
    }

    return bool;
  }
}
