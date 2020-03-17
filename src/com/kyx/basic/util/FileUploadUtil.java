package com.kyx.basic.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 初始化文件上传路径
 * 
 * @author Administrator
 *
 */
@Component
public class FileUploadUtil {

  private static String path = "/www/image/";

  public static String getPath() {
    return path;
  }

  public void setPath(String path) {
    if (StringUtils.isBlank(path)) {
      return;
    } else {
      this.path = path;
    }
  }

}
