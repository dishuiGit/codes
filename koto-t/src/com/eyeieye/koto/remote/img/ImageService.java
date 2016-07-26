package com.eyeieye.koto.remote.img;

import com.eyeieye.koto.remote.UploadResult;
import com.eyeieye.koto.remote.UploadSource;
import java.util.Map;

public abstract interface ImageService
{
  public abstract UploadResult store(UploadSource paramUploadSource);

  public abstract void removeById(String paramString);

  public abstract void removeByPath(String paramString);

  public abstract long sumImageLength(String paramString);

  public abstract StoredImgResult findStoredImages(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.img.ImageService
 * JD-Core Version:    0.6.2
 */