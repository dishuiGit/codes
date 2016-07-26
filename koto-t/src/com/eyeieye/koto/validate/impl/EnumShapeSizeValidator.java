package com.eyeieye.koto.validate.impl;

import com.eyeieye.koto.validate.ShapeSizeValidator;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class EnumShapeSizeValidator
   implements ShapeSizeValidator
 {
   private Set<Integer> allowed;

   public void setAllowdSize(String sizes)
   {
     String[] numbers = StringUtils.split(sizes, ',');
     this.allowed = new HashSet(numbers.length);
     for (String number : numbers)
       this.allowed.add(Integer.valueOf(Integer.parseInt(number)));
   }

   public boolean isAllowed(Integer width, Integer height)
   {
     if (height == null) {
       return this.allowed.contains(width);
     }
     return (this.allowed.contains(width)) && (this.allowed.contains(height));
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.validate.impl.EnumShapeSizeValidator
 * JD-Core Version:    0.6.2
 */