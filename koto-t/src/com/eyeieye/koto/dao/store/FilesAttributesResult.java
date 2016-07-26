package com.eyeieye.koto.dao.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilesAttributesResult
 {
   private int total;
   private List<Map<String, Object>> fileAttributes;

   public void addMap(Map<String, Object> map)
   {
     if (this.fileAttributes == null) {
       this.fileAttributes = new ArrayList();
     }
     this.fileAttributes.add(map);
   }

   public int getTotal() {
     return this.total;
   }

   public void setTotal(int total) {
     this.total = total;
   }

   public List<Map<String, Object>> getFileAttributes() {
     return this.fileAttributes;
   }

   public void setFileAttributes(List<Map<String, Object>> fileAttributes) {
     this.fileAttributes = fileAttributes;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.store.FilesAttributesResult
 * JD-Core Version:    0.6.2
 */