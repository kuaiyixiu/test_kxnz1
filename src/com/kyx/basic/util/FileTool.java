package com.kyx.basic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileTool {
	  /**
	   * 图片上传
	   * @param file
	   * @param filePath
	   * @param fileName
	   * @throws Exception
	   */
	  public static void uploadFiles(byte[] file, String filePath, String fileName) throws Exception {
	    File targetFile = new File(filePath);
	    if (!targetFile.exists()) {
	      targetFile.mkdirs();
	    }
	    FileOutputStream out = new FileOutputStream(filePath + fileName);
	    out.write(file);
	    out.flush();
	    out.close();
	  }
	  /**
	   * 创建新的文件名
	   * @param fileName
	   * @return
	   */
	  public static String renameToUUID(String fileName) {
	    return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	  }
}
