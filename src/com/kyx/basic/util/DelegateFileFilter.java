package com.kyx.basic.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class DelegateFileFilter extends AbstractFileFilter
{
  private FilenameFilter filenameFilter;
  private FileFilter fileFilter;

  public DelegateFileFilter(FilenameFilter filter)
  {
    if (filter == null) {
      throw new IllegalArgumentException("The FilenameFilter must not be null");
    }
    this.filenameFilter = filter;
  }

  public DelegateFileFilter(FileFilter filter)
  {
    if (filter == null) {
      throw new IllegalArgumentException("The FileFilter must not be null");
    }
    this.fileFilter = filter;
  }

  public boolean accept(File file)
  {
    if (this.fileFilter != null) {
      return this.fileFilter.accept(file);
    }
    return super.accept(file);
  }

  public boolean accept(File dir, String name)
  {
    if (this.filenameFilter != null) {
      return this.filenameFilter.accept(dir, name);
    }
    return super.accept(dir, name);
  }
}