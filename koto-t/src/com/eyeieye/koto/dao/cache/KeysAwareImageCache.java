package com.eyeieye.koto.dao.cache;

import java.util.Set;

public abstract interface KeysAwareImageCache extends BytesIdImgCache
{
  public abstract Set<IdKey> getKeys();

  public abstract void remove(IdKey paramIdKey);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.KeysAwareImageCache
 * JD-Core Version:    0.6.2
 */