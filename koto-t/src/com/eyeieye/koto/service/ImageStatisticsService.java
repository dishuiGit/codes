package com.eyeieye.koto.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public abstract interface ImageStatisticsService
 {
   public abstract void hitInCache(int paramInt);

   public abstract void hitInStore(int paramInt);

   public abstract void record();

   public abstract List<ImgReport> getRecentStatistics();

   public static class ImgReport
   {
     private Date date;
     private long cacheHitCount;
     private long cacheHitSum;
     private long storeHitCount;
     private long storeHitSum;

     public long getHitTotalCount()
     {
       return this.cacheHitCount + this.storeHitCount;
     }

     public double getCacheHitRate()
     {
       long totalCount = getHitTotalCount();
       if (totalCount == 0L) {
         return 0.0D;
       }
       return new BigDecimal(this.cacheHitCount * 100L).divide(new BigDecimal(totalCount), 4, 6).doubleValue();
     }

     public long getHitTotalSum()
     {
       return this.cacheHitSum + this.storeHitSum;
     }

     public double getCacheHitSumRate()
     {
       long totalCount = getHitTotalSum();
       if (totalCount == 0L) {
         return 0.0D;
       }
       return new BigDecimal(this.cacheHitSum * 100L).divide(new BigDecimal(totalCount), 4, 6).doubleValue();
     }

     public Date getDate()
     {
       return this.date;
     }

     public void setDate(Date date) {
       this.date = date;
     }

     public long getCacheHitCount() {
       return this.cacheHitCount;
     }

     public void setCacheHitCount(long cacheHitCount) {
       this.cacheHitCount = cacheHitCount;
     }

     public long getCacheHitSum() {
       return this.cacheHitSum;
     }

     public void setCacheHitSum(long cacheHitSum) {
       this.cacheHitSum = cacheHitSum;
     }

     public long getStoreHitCount() {
       return this.storeHitCount;
     }

     public void setStoreHitCount(long storeHitCount) {
       this.storeHitCount = storeHitCount;
     }

     public long getStoreHitSum() {
       return this.storeHitSum;
     }

     public void setStoreHitSum(long storeHitSum) {
       this.storeHitSum = storeHitSum;
     }
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.service.ImageStatisticsService
 * JD-Core Version:    0.6.2
 */