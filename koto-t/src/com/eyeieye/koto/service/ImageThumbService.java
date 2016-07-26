package com.eyeieye.koto.service;

import com.eyeieye.koto.domain.StoredImage;
import com.eyeieye.koto.remote.UploadSource;
import java.io.IOException;
import org.im4java.core.IM4JavaException;

public abstract interface ImageThumbService
{
  public abstract StoredImage getImageThumb(StoredImage paramStoredImage, Integer paramInteger1, Integer paramInteger2)
    throws IOException;

  public abstract String preHanleAndGetFormat(UploadSource paramUploadSource)
    throws IOException;

  public abstract StoredImage cutImage(StoredImage paramStoredImage, Integer paramInteger1, Integer paramInteger2, String paramString)
    throws InterruptedException, IM4JavaException;
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.service.ImageThumbService
 * JD-Core Version:    0.6.2
 */