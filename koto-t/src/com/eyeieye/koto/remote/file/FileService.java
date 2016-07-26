package com.eyeieye.koto.remote.file;

import com.eyeieye.koto.remote.UploadResult;
import com.eyeieye.koto.remote.UploadSource;

public abstract interface FileService
{
  public abstract UploadResult store(UploadSource paramUploadSource);

  public abstract void remove(String paramString);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.file.FileService
 * JD-Core Version:    0.6.2
 */