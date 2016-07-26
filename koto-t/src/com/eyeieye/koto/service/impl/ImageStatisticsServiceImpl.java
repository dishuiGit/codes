package com.eyeieye.koto.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eyeieye.koto.service.ImageStatisticsService;

public class ImageStatisticsServiceImpl
   implements ImageStatisticsService
 {
   private static final Log logger = LogFactory.getLog(ImageStatisticsServiceImpl.class);

   private ImageStatistics cacheHit = new ImageStatistics();

   private ImageStatistics storeHit = new ImageStatistics();

   private int statisticsAmount = 120;

   private Queue<ImageStatisticsService.ImgReport> recentStatistics = new LinkedList();
   private ScheduledExecutorService scheduledExecutor;
   private static final long OneHourMilliseconds = 3600000L;

   public void hitInCache(int length)
   {
     this.cacheHit.addHit(length);
   }

   public void hitInStore(int length)
   {
     this.storeHit.addHit(length);
   }

   public void setStatisticsDay(int day)
   {
     this.statisticsAmount = (day * 24);
   }

   public List<ImageStatisticsService.ImgReport> getRecentStatistics()
   {
     List back = new ArrayList(this.recentStatistics);
     Collections.reverse(back);
     return back;
   }

   public void record()
   {
     ImageStatisticsService.ImgReport report = new ImageStatisticsService.ImgReport();
     report.setDate(new Date());

     report.setCacheHitCount(this.cacheHit.getAndCleanCount());
     report.setCacheHitSum(this.cacheHit.getAndCleanSum());
     report.setStoreHitCount(this.storeHit.getAndCleanCount());
     report.setStoreHitSum(this.storeHit.getAndCleanSum());
     this.recentStatistics.add(report);
     if (this.recentStatistics.size() > this.statisticsAmount)
       this.recentStatistics.poll();
   }

   public void init()
   {
     this.scheduledExecutor = Executors.newScheduledThreadPool(1);
     this.scheduledExecutor.scheduleAtFixedRate(new Runnable()
     {
       public void run() {
         try {
           ImageStatisticsServiceImpl.this.record();
         } catch (Exception e) {
           ImageStatisticsServiceImpl.logger.error("error then record.", e);
         }
       }
     }
     , getFristTaskTime(), 3600000L, TimeUnit.MILLISECONDS);
   }

   private long getFristTaskTime()
   {
     Calendar now = GregorianCalendar.getInstance();
     int hour = now.get(11);
     now.set(11, hour + 1);
     now.set(12, 0);
     now.set(13, 0);
     now.set(14, 0);
     return now.getTimeInMillis() - System.currentTimeMillis();
   }

   public void destroy() throws Exception {
     this.scheduledExecutor.shutdown();
   }

   private class ImageStatistics
   {
     private AtomicLong count = new AtomicLong(0L);

     private AtomicLong sum = new AtomicLong(0L);

     private ImageStatistics() {  }
     private void addHit(int length) { this.count.incrementAndGet();
       this.sum.addAndGet(length); }

     private long getAndCleanCount()
     {
       return this.count.getAndSet(0L);
     }

     private long getAndCleanSum() {
       return this.sum.getAndSet(0L);
     }
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.service.impl.ImageStatisticsServiceImpl
 * JD-Core Version:    0.6.2
 */