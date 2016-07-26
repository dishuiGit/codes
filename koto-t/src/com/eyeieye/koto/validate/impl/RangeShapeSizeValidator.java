package com.eyeieye.koto.validate.impl;

import com.eyeieye.koto.validate.ShapeSizeValidator;

public class RangeShapeSizeValidator
   implements ShapeSizeValidator
 {
   private int widthMin = 10;

   private int widthMax = 1200;

   private int heightMin = 10;

   private int heightMax = 4000;

   public boolean isAllowed(Integer width, Integer height)
   {
     if (height == null) {
       return (width.intValue() >= this.widthMin) && (width.intValue() <= this.widthMax);
     }
     return (width.intValue() >= this.widthMin) && (width.intValue() <= this.widthMax) && (height.intValue() >= this.heightMin) && (height.intValue() <= this.heightMax);
   }

   public int getWidthMin()
   {
     return this.widthMin;
   }

   public void setWidthMin(int widthMin) {
     this.widthMin = widthMin;
   }

   public int getWidthMax() {
     return this.widthMax;
   }

   public void setWidthMax(int widthMax) {
     this.widthMax = widthMax;
   }

   public int getHeightMin() {
     return this.heightMin;
   }

   public void setHeightMin(int heightMin) {
     this.heightMin = heightMin;
   }

   public int getHeightMax() {
     return this.heightMax;
   }

   public void setHeightMax(int heightMax) {
     this.heightMax = heightMax;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.validate.impl.RangeShapeSizeValidator
 * JD-Core Version:    0.6.2
 */