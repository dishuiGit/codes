package com.eyeieye.koto.remote.img;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoredImgResult
   implements Serializable
 {
   private static final long serialVersionUID = -1237848314828956929L;
   private int total;
   private List<StoredImg> imgs;

   public int getTotal()
   {
     return this.total;
   }

   public void setTotal(int total) {
     this.total = total;
   }

   public List<StoredImg> getImgs() {
     return this.imgs;
   }

   public void setImgs(List<StoredImg> imgs) {
     this.imgs = imgs;
   }

   public static final class StoredImg
     implements Serializable
   {
     private static final long serialVersionUID = -945986925240153464L;
     private String path;
     private Map<String, Serializable> attributes;

     public void addAttribute(String key, Serializable value)
     {
       if (this.attributes == null) {
         this.attributes = new HashMap();
       }
       this.attributes.put(key, value);
     }

     public Map<String, Serializable> getAttributes() {
       return this.attributes;
     }

     public void setAttributes(Map<String, Serializable> attributes) {
       this.attributes = attributes;
     }

     public String getPath() {
       return this.path;
     }

     public void setPath(String path) {
       this.path = path;
     }
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.remote.img.StoredImgResult
 * JD-Core Version:    0.6.2
 */