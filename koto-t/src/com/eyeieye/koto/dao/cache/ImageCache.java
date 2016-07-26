package com.eyeieye.koto.dao.cache;

import com.eyeieye.koto.domain.StoredImage;

public abstract interface ImageCache
{
  public abstract StoredImage get(String paramString);

  public abstract void put(String paramString, StoredImage paramStoredImage);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.ImageCache
 * JD-Core Version:    0.6.2
 */