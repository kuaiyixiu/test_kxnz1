package com.kyx.basic.util;

import java.util.List;

public abstract interface ConditionalFileFilter
{
  public abstract void addFileFilter(IOFileFilter paramIOFileFilter);

  public abstract List getFileFilters();

  public abstract boolean removeFileFilter(IOFileFilter paramIOFileFilter);

  public abstract void setFileFilters(List paramList);
}