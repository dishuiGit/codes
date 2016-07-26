package com.eyeieye.koto.remote;

import java.io.Serializable;

public class UploadResult
   implements Serializable
 {
   private static final long serialVersionUID = -8227277408478690545L;
   private boolean success = true;
   private String errorInfo;
   private String path;
   private String newId;

   public void setErrorInfo(String errorInfo)
   {
     this.errorInfo = errorInfo;
     if (errorInfo != null)
       setSuccess(false);
   }

   public void setPath(String path)
   {
     this.path = path;
   }

   public boolean isSuccess() {
     return this.success;
   }

   public void setSuccess(boolean success) {
     this.success = success;
   }

   public String getErrorInfo() {
     return this.errorInfo;
   }

   public String getPath() {
     return this.path;
   }

   public String getNewId() {
     return this.newId;
   }

   public void setNewId(String newId) {
     this.newId = newId;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.UploadResult
 * JD-Core Version:    0.6.2
 */