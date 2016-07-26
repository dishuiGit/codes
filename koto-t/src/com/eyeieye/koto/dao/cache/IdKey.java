package com.eyeieye.koto.dao.cache;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class IdKey
 {
   private static final Charset IdCharset = Charset.forName("UTF-8");
   private byte[] bytes;
   private int hashCode = -1;

   public IdKey(byte[] bytes)
   {
     if (bytes == null) {
       throw new NullPointerException("bytes can't be null.");
     }
     this.bytes = bytes;
   }

   public IdKey(String idString)
   {
     if (idString == null) {
       throw new NullPointerException("id String can't be null.");
     }
     this.bytes = idString.getBytes(IdCharset);
   }

   public int hashCode()
   {
     if (this.hashCode == -1) {
       this.hashCode = Arrays.hashCode(this.bytes);
     }
     return this.hashCode;
   }

   public boolean equals(Object obj)
   {
     if (obj == null) {
       return false;
     }
     return Arrays.equals(this.bytes, ((IdKey)obj).bytes);
   }

   public String toString()
   {
     return getIdString();
   }

   public byte[] getBytes() {
     return this.bytes;
   }

   public String getIdString() {
     return new String(this.bytes, IdCharset);
   }

   public int getBytesLength() {
     return this.bytes.length;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.IdKey
 * JD-Core Version:    0.6.2
 */