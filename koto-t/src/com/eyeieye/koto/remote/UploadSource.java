package com.eyeieye.koto.remote;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UploadSource
   implements Serializable
 {
   private static final long serialVersionUID = -6382565578676408526L;
   private byte[] body;
   private String filename;
   private Map<String, Serializable> appends;

   public byte[] getBody()
   {
     return this.body;
   }

   public void setBody(byte[] body) {
     this.body = body;
   }

   public Map<String, Serializable> getAppends() {
     return this.appends;
   }

   public UploadSource append(String key, Serializable value) {
     if (this.appends == null) {
       this.appends = new HashMap();
     }
     this.appends.put(key, value);
     return this;
   }

   public String getFilename() {
     return this.filename;
   }

   public void setFilename(String filename) {
     this.filename = filename;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.UploadSource
 * JD-Core Version:    0.6.2
 */