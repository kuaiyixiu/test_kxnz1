package com.kyx.basic.util;

import java.io.File;

public class TrueFileFilter
  implements IOFileFilter
{
  public static final IOFileFilter TRUE = new TrueFileFilter();

  public static final IOFileFilter INSTANCE = TRUE;

  public boolean accept(File file)
  {
    return true;
  }

  public boolean accept(File dir, String name)
  {
    return true;
  }
}