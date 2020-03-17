package com.kyx.basic.util;

import java.io.File;

public class FalseFileFilter
  implements IOFileFilter
{
  public static final IOFileFilter FALSE = new FalseFileFilter();

  public static final IOFileFilter INSTANCE = FALSE;

  public boolean accept(File file)
  {
    return false;
  }

  public boolean accept(File dir, String name)
  {
    return false;
  }
}