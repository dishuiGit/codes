package com.eyeieye.koto.dao.cache.memory;

import com.eyeieye.koto.dao.cache.CachedImgBlockSatistics;
import com.eyeieye.koto.dao.cache.CachedImgStatistics;
import com.eyeieye.koto.dao.cache.IdKey;
import com.eyeieye.koto.dao.cache.KeysAwareImageCache;
import com.eyeieye.koto.domain.StoredImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageCacheMemoryStoreImpl
   implements KeysAwareImageCache, CachedImgStatistics
 {
   private static final Log logger = LogFactory.getLog(ImageCacheMemoryStoreImpl.class);

   private final Map<IdKey, MemoryStoredImage> id2Image = new ConcurrentHashMap();

   private long maxMemory = 2147483647L;

   public StoredImage get(IdKey key)
   {
     MemoryStoredImage find = (MemoryStoredImage)this.id2Image.get(key);
     if (find == null) {
       return null;
     }
     return new MemoryStoredImage(find);
   }

   public boolean contains(IdKey key)
   {
     return this.id2Image.containsKey(key);
   }

   public void put(IdKey key, StoredImage image)
   {
     if (this.id2Image.containsKey(key)) {
       return;
     }
     MemoryStoredImage memoryImage = new MemoryStoredImage();
     memoryImage.setFormat(image.getFormat());
     try {
       memoryImage.setByteBuffer(createMemoryBuffer(image));
       this.id2Image.put(key, memoryImage);
     } catch (IOException e) {
       logger.error("error then create memory byteBuffer", e);
     }
   }

   private ByteBuffer createMemoryBuffer(StoredImage image) throws IOException {
     int len = image.getLength();
     ByteBuffer bb = ByteBuffer.allocateDirect(len);
     InputStream input = image.getBodyInputStream();
     ReadableByteChannel channel = Channels.newChannel(input);
     channel.read(bb);
     bb.flip();
     return bb;
   }

   public Set<IdKey> getKeys()
   {
     return new HashSet(this.id2Image.keySet());
   }

   public void remove(IdKey key)
   {
     this.id2Image.remove(key);
   }

   public void setMaxMemory(long maxMemory)
   {
     this.maxMemory = maxMemory;
   }

   public void setMaxMemoryMB(long maxMemoryMb) {
     this.maxMemory = (maxMemoryMb * 1024L * 1024L);
   }

   public List<CachedImgBlockSatistics> getBlockStatistics()
   {
     CachedImgBlockSatistics sat = new CachedImgBlockSatistics();
     sat.setCurrentStore(true);
     sat.setTotalSpace(this.maxMemory);
     int count = 0;
     long totalSize = 0L;
     for (MemoryStoredImage im : this.id2Image.values()) {
       count++;
       totalSize += im.getLength();
     }
     sat.setRowCount(count);
     sat.setUsedSpace(totalSize);
     sat.setFreeSpace(this.maxMemory - totalSize);
     return Collections.singletonList(sat);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.memory.ImageCacheMemoryStoreImpl
 * JD-Core Version:    0.6.2
 */