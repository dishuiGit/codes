package com.eyeieye.koto.dao.cache;

import com.eyeieye.koto.domain.StoredImage;

public abstract interface BytesIdImgCache
{
  public abstract StoredImage get(IdKey paramIdKey);

  public abstract boolean contains(IdKey paramIdKey);

  public abstract void put(IdKey paramIdKey, StoredImage paramStoredImage);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.BytesIdImgCache
 * JD-Core Version:    0.6.2
 */