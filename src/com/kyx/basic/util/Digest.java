package com.kyx.basic.util;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Digest
{
//  private static Logger LOGGER = LoggerFactory.getLogger(Digest.class);

  public static String digest(String str)
  {
    String md5 = null;
    try {
  //    LOGGER.debug("生成的字符串为:{}", str);
      md5 = MD5.digest(str, "UTF-8");
  //    LOGGER.debug("生成的Md5摘要为:{}", md5);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return md5;
  }

  public static String digest(Map<String, String> map, String[] keys)
  {
    TreeMap treeMap = treeMap(map, keys);
    return digest(mapToString(treeMap));
  }

  public static TreeMap<String, String> treeMap(Map<String, String> map, String[] keys)
  {
    Comparator stringComparator = new StringComparator();

    TreeMap treeMap = new TreeMap(stringComparator);
    treeMap.putAll(map);

    for (String key : keys) {
      treeMap.remove(key);
    }
    return treeMap;
  }

  public static String mapToString(Map<String, String> map)
  {
    StringBuilder result = new StringBuilder();
    for (Map.Entry entry : map.entrySet()) {
      String value = entry.getValue() == null ? "" : ((String)entry.getValue()).trim();
      result.append((String)entry.getKey()).append("=").append(value).append("&");
    }
    if (result.length() > 0) {
      result.deleteCharAt(result.length() - 1);
    }
    return result.toString().trim();
  }
}