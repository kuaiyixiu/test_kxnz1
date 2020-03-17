package com.kyx.basic.util;

import java.io.File;

public class FileFileFilter extends AbstractFileFilter
{
  public static final IOFileFilter FILE = new FileFileFilter();

  public boolean accept(File file)
  {
    return file.isFile();
  }
}