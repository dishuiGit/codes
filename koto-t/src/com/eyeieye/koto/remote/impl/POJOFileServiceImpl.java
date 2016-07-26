package com.eyeieye.koto.remote.impl;

import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.remote.UploadResult;
import com.eyeieye.koto.remote.UploadSource;
import com.eyeieye.koto.remote.file.FileService;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

 @Service("remoteFileService")
public class POJOFileServiceImpl
   implements FileService
 {
   private static Log logger = LogFactory.getLog(POJOFileServiceImpl.class);

   @Autowired
   @Qualifier("fileStoreService")
   private StoreService fileStoreService;

   public UploadResult store(UploadSource source) { if (source == null) {
       throw new NullPointerException("FileStoreSource can't be null.");
     }
     if (source.getBody() == null) {
       throw new IllegalArgumentException("FileStoreSource's body can't be null.");
     }

     String filename = StringUtils.trim(source.getFilename());
     if (StringUtils.isBlank(filename)) {
       throw new IllegalArgumentException("FileStoreSource's filename can't be null.");
     }

     Map appends = new HashMap();
     if (source.getAppends() != null) {
       appends.putAll(source.getAppends());
     }
     appends.put("i_fn", filename);
     String id = this.fileStoreService.saveFile(source.getBody(), source.getFilename(), appends);

     String nameExtension = FilenameUtils.getExtension(filename);
     String path = "fs/" + id;
     if (StringUtils.isNotBlank(nameExtension)) {
       path = path + '.' + nameExtension;
     }
     UploadResult back = new UploadResult();
     back.setSuccess(true);
     back.setPath(path);
     return back;
   }

   public void remove(String path)
   {
     if (path == null) {
       throw new NullPointerException("image url can't be null.");
     }
     path = path.trim();
     String id = getId(path);
     if (logger.isDebugEnabled()) {
       logger.debug("remove file in path:" + path + " which id is:" + id);
     }
     this.fileStoreService.removeFile(id);
   }

   private String getId(String path) {
     int lastDot = path.lastIndexOf('.');
     if (lastDot == -1) {
       lastDot = path.length();
     }
     int lastSeq = path.lastIndexOf('/');
     if (lastSeq == -1)
       lastSeq = 0;
     else {
       lastSeq++;
     }
     return path.substring(lastSeq, lastDot);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.impl.POJOFileServiceImpl
 * JD-Core Version:    0.6.2
 */