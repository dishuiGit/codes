package com.eyeieye.koto.dao.cache;

import com.eyeieye.koto.dao.cache.hotsopt.HotspotManager;
import com.eyeieye.koto.domain.StoredImage;
import java.util.concurrent.Executor;

public class ImageCacheCombination
   implements ImageCache
 {
   private BytesIdImgCache memoryCache;
   private BytesIdImgCache diskCache;
   private Executor executor;
   private HotspotManager hotspotManager;

   public void setMemoryCache(BytesIdImgCache memoryCache)
   {
     this.memoryCache = memoryCache;
   }

   public void setDiskCache(BytesIdImgCache diskCache) {
     this.diskCache = diskCache;
   }

   public void setExecutor(Executor executor) {
     this.executor = executor;
   }

   public void setHotspotManager(HotspotManager hotspotManager) {
     this.hotspotManager = hotspotManager;
   }

   public StoredImage get(String key)
   {
     IdKey id = new IdKey(key);
     StoredImage get = this.memoryCache.get(id);
     if (get == null) {
       get = this.diskCache.get(id);
     }
     if (get != null)
     {
       this.hotspotManager.hit(id, get);
     }
     return get;
   }

   public void put(final String key, final StoredImage image)
   {
     this.executor.execute(new Runnable()
     {
       public void run() {
         ImageCacheCombination.this.innerPut(key, image);
       }
     });
   }

   private void innerPut(String key, StoredImage image) {
     IdKey id = new IdKey(key);

     this.diskCache.put(id, image);

     this.hotspotManager.hit(id, image);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.ImageCacheCombination
 * JD-Core Version:    0.6.2
 */