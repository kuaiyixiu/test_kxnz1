package com.kyx.basic.util;

import java.io.File;

public class SizeFileFilter extends AbstractFileFilter
{
  private long size;
  private boolean acceptLarger;

  public SizeFileFilter(long size)
  {
    this(size, true);
  }

  public SizeFileFilter(long size, boolean acceptLarger)
  {
    if (size < 0L) {
      throw new IllegalArgumentException("The size must be non-negative");
    }
    this.size = size;
    this.acceptLarger = acceptLarger;
  }

  public boolean accept(File file)
  {
    boolean smaller = file.length() < this.size;
    return this.acceptLarger ? false : !smaller ? true : smaller;
  }
}