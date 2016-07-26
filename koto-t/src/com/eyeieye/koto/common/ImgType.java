package com.eyeieye.koto.common;

public enum ImgType
 {
   jpeg("image/jpeg"), gif("image/gif"), png("image/png"), tiff("image/tiff"), bmp("image/bmp");

   private String mineType;

   private ImgType(String mineType)
   {
     this.mineType = mineType;
   }

   public String getMineType() {
     return this.mineType;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.common.ImgType
 * JD-Core Version:    0.6.2
 */