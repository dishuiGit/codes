package com.eyeieye.koto.dao.cache.hotsopt;

import com.eyeieye.koto.dao.cache.IdKey;
import com.eyeieye.koto.domain.StoredImage;

public abstract interface HotspotManager
{
  public abstract void hit(IdKey paramIdKey, StoredImage paramStoredImage);

  public abstract void arrange();
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.hotsopt.HotspotManager
 * JD-Core Version:    0.6.2
 */