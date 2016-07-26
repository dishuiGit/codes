package com.eyeieye.koto.dao.cache;

import java.text.DecimalFormat;
import java.util.Collection;

public class CachedImgBlockSatistics
 {
   private int id;
   private long rowCount;
   private long totalSpace;
   private long usedSpace;
   private long freeSpace;
   private boolean currentStore = false;
   private static final String mbFormat = "0.##";

   public CachedImgBlockSatistics()
   {
   }

   public CachedImgBlockSatistics(Collection<CachedImgBlockSatistics> sats)
   {
     for (CachedImgBlockSatistics sat : sats) {
       this.rowCount += sat.rowCount;
       this.totalSpace += sat.totalSpace;
       this.usedSpace += sat.usedSpace;
       this.freeSpace += sat.freeSpace;
     }
   }

   public int getId() {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   public long getRowCount() {
     return this.rowCount;
   }

   public void setRowCount(long rowCount) {
     this.rowCount = rowCount;
   }

   public long getTotalSpace() {
     return this.totalSpace;
   }

   public void setTotalSpace(long totalSpace) {
     this.totalSpace = totalSpace;
   }

   public long getUsedSpace() {
     return this.usedSpace;
   }

   public void setUsedSpace(long usedSpace) {
     this.usedSpace = usedSpace;
   }

   public long getFreeSpace() {
     return this.freeSpace;
   }

   public void setFreeSpace(long freeSpace) {
     this.freeSpace = freeSpace;
   }

   public boolean isCurrentStore() {
     return this.currentStore;
   }

   public void setCurrentStore(boolean currentStore) {
     this.currentStore = currentStore;
   }

   public String getTotalSpaceMb()
   {
     return new DecimalFormat("0.##").format(this.totalSpace / 1024.0D / 1024.0D);
   }

   public String getUsedSpaceMb()
   {
     return new DecimalFormat("0.##").format(this.usedSpace / 1024.0D / 1024.0D);
   }

   public String getFreeSpaceMb()
   {
     return new DecimalFormat("0.##").format(this.freeSpace / 1024.0D / 1024.0D);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.CachedImgBlockSatistics
 * JD-Core Version:    0.6.2
 */