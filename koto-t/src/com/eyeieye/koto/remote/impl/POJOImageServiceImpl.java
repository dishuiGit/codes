package com.eyeieye.koto.remote.impl;

import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.dao.store.FilesAttributesResult;
import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.remote.UploadResult;
import com.eyeieye.koto.remote.UploadSource;
import com.eyeieye.koto.remote.img.ImageService;
import com.eyeieye.koto.remote.img.StoredImgResult;
import com.eyeieye.koto.remote.img.StoredImgResult.StoredImg;
import com.eyeieye.koto.service.ImageThumbService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

 @Service("remoteImageService")
public class POJOImageServiceImpl
   implements ImageService
 {
   private static Log logger = LogFactory.getLog(POJOImageServiceImpl.class);

   @Autowired
   @Qualifier("imgStoreService")
   private StoreService imgStoreService;

   @Autowired
   private ImageThumbService imageThumbService;

   public UploadResult store(UploadSource imgFile) { if (imgFile == null) {
       throw new NullPointerException("imgFileStoreSource can't be null.");
     }
     UploadResult result = new UploadResult();
     String imgFormat;
     try {
       imgFormat = this.imageThumbService.preHanleAndGetFormat(imgFile);
     } catch (IOException e1) {
       logger.error("error in read image", e1);
       result.setErrorInfo("图片类型错误");
       return result;
     }
     if (imgFormat == null) {
       result.setErrorInfo("图片类型无法识别");
       return result;
     }
     if (ImgType.valueOf(imgFormat) == null) {
       result.setErrorInfo("图片类型目前不支持:" + imgFormat);
       return result;
     }

     String id = saveOriginally(imgFile, imgFormat);

     result.setNewId(id);
     result.setPath("img/" + id + ".img");

     if (logger.isDebugEnabled()) {
       logger.debug("save image with id:" + id);
     }
     return result; }

   private String getImgType(ByteArrayInputStream bais) throws IOException
   {
     ImageInputStream iis = null;
     try {
       iis = ImageIO.createImageInputStream(bais);
       Iterator ir = ImageIO.getImageReaders(iis);
       String format;
       while (ir.hasNext()) {
         format = ((ImageReader)ir.next()).getFormatName();
         if (format != null) {
           return format.trim().toLowerCase();
         }
       }
       return null;
     } finally {
       if (iis != null)
         try {
           iis.close();
         }
         catch (IOException ignore) {
         }
     }
   }

   private String saveOriginally(UploadSource source, String imgFormat) {
     Map appends = new HashMap();
     if (source.getAppends() != null) {
       appends.putAll(source.getAppends());
     }
     appends.put("i_tp", imgFormat);
     String orignalId = this.imgStoreService.saveFile(source.getBody(), source.getFilename(), appends);

     return orignalId;
   }

   public void removeById(String id) {
     if (id == null) {
       throw new NullPointerException("image id can't be null.");
     }
     if (logger.isDebugEnabled()) {
       logger.debug("remove id:" + id);
     }
     this.imgStoreService.removeFile(id);
   }

   public void removeByPath(String path) {
     if (path == null) {
       throw new NullPointerException("image url can't be null.");
     }
     if (logger.isDebugEnabled()) {
       logger.debug("remove path:" + path);
     }
     path = path.trim();

     if (!path.endsWith(".img")) {
       throw new IllegalArgumentException("path not end with[.img].");
     }

     if (!path.startsWith("img/")) {
       throw new IllegalArgumentException("path not start with[img/].");
     }

     String id = getIdFromPath(path);
     if (id == null) {
       throw new IllegalArgumentException("unknow path:[" + path + "].");
     }
     this.imgStoreService.removeFile(id);
   }

   private String getIdFromPath(String path)
   {
     int urlSeparate = path.indexOf(95);
     if (urlSeparate != -1) {
       return null;
     }

     return path.substring("img/".length(), path.length() - ".img".length());
   }

   public long sumImageLength(String condition)
   {
     if (condition == null) {
       throw new NullPointerException("condition can't be null.");
     }
     return this.imgStoreService.sumImageLength(condition);
   }

   public StoredImgResult findStoredImages(Map conditions, int start, int end)
   {
       if(conditions == null)
           conditions = new HashMap();
       FilesAttributesResult result = imgStoreService.findFilesAttributes(conditions, start, end);
       StoredImgResult back = new StoredImgResult();
       back.setTotal(result.getTotal());
       List as = result.getFileAttributes();
       if(as != null && !as.isEmpty())
       {
           List imgs = new ArrayList(as.size());
           back.setImgs(imgs);
           com.eyeieye.koto.remote.img.StoredImgResult.StoredImg sf;
label0:
           for(Iterator i$ = as.iterator(); i$.hasNext(); imgs.add(sf))
           {
               Map one = (Map)i$.next();
               sf = new com.eyeieye.koto.remote.img.StoredImgResult.StoredImg();
               String id = one.get("id").toString();
               sf.setPath((new StringBuilder()).append("img/").append(id).append(".img").toString());
               Iterator i$1 = one.entrySet().iterator();
               do
               {
                   if(!i$1.hasNext())
                       continue label0;
                   java.util.Map.Entry attr = (java.util.Map.Entry)i$1.next();
                   if(attr.getValue() instanceof Serializable)
                       sf.addAttribute((String)attr.getKey(), (Serializable)attr.getValue());
               } while(true);
           }

       }
       return back;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.impl.POJOImageServiceImpl
 * JD-Core Version:    0.6.2
 */