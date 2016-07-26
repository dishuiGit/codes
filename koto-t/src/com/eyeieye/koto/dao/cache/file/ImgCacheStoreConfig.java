package com.eyeieye.koto.dao.cache.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ImgCacheStoreConfig
   implements InitializingBean, DisposableBean
 {
   public static final long UnsignedIntMax = 4294967295L;
   private long dataFileLength = 4294967295L;
   private int blocks;
   private String storePath;
   private String imgIndexNamePrefix = "index_";

   private String imgDataNamePrefix = "img_";

   private int flushIndexPeriod = 600000;
   private File storeDirectory;
   private File[] imgIndexs;
   private File[] imgBlocks;
   private FileChannel[] imgFileChannels;

   private void initVariable()
   {
     if (this.blocks < 1) {
       throw new IllegalArgumentException("blocks can't less than 1.");
     }
     if (this.blocks > 32767) {
       throw new IllegalArgumentException("blocks can't gretate than 32767");
     }

     if (StringUtils.isBlank(this.storePath)) {
       throw new IllegalArgumentException("store path must set.");
     }
     this.storeDirectory = new File(this.storePath);
     if (!this.storeDirectory.isDirectory()) {
       throw new IllegalArgumentException(this.storeDirectory + " must be a directory.");
     }

     if ((!this.storeDirectory.exists()) &&
       (!this.storeDirectory.mkdirs())) {
       throw new IllegalArgumentException(this.storeDirectory + " can't create.");
     }

     if (!this.storeDirectory.canRead()) {
       throw new IllegalArgumentException(this.storeDirectory + " must be readable.");
     }

     if (!this.storeDirectory.canWrite()) {
       throw new IllegalArgumentException(this.storeDirectory + " must be writeable.");
     }

     this.imgIndexs = new File[this.blocks];
     for (int i = 0; i < this.blocks; i++) {
       File dir = buildCacheDirectory(i);
       this.imgIndexs[i] = new File(dir, this.imgIndexNamePrefix + Integer.toString(i));
     }

     this.imgBlocks = new File[this.blocks];
     for (int i = 0; i < this.blocks; i++) {
       File dir = buildCacheDirectory(i);
       this.imgBlocks[i] = new File(dir, this.imgDataNamePrefix + Integer.toString(i));
     }
   }

   private File buildCacheDirectory(int i)
   {
     int name = i % 10;
     File dir = new File(this.storeDirectory, Integer.toString(name));
     if (!dir.exists()) {
       dir.mkdirs();
     }
     return dir;
   }

   private void initFiles()
     throws IOException
   {
     for (File indexFile : this.imgIndexs) {
       indexFile.createNewFile();
     }
     ByteBuffer zero = getZeroBuffer();
     for (File dataFile : this.imgBlocks) {
       createDataFileIfNotExists(dataFile, zero);
     }

     this.imgFileChannels = new FileChannel[this.blocks];
     for (int i = 0; i < this.blocks; i++) {
       File img = this.imgBlocks[i];
       RandomAccessFile raf = new RandomAccessFile(img, "r");
       this.imgFileChannels[i] = raf.getChannel();
     }
   }

   private void closeFiles() throws IOException {
     for (FileChannel fc : this.imgFileChannels)
       fc.close();
   }

   private void createDataFileIfNotExists(File dataFile, ByteBuffer zero)
     throws IOException
   {
     if (dataFile.createNewFile())
     {
       FileOutputStream fos = new FileOutputStream(dataFile);
       FileChannel channel = fos.getChannel();
       channel.position(this.dataFileLength);
       zero.flip();
       channel.write(zero);
       channel.close();
       fos.close();
     }
   }

   private ByteBuffer getZeroBuffer() {
     ByteBuffer bb = ByteBuffer.allocateDirect(1);
     bb.put((byte)0);
     return bb;
   }

   public void afterPropertiesSet() throws Exception
   {
     initVariable();
     initFiles();
   }

   public void destroy() throws Exception
   {
     closeFiles();
   }

   public void setStorePath(String storePath) {
     this.storePath = storePath;
   }

   public void setImgIndexNamePrefix(String imgIndexNamePrefix) {
     this.imgIndexNamePrefix = imgIndexNamePrefix;
   }

   public void setImgDataNamePrefix(String imgDataNamePrefix) {
     this.imgDataNamePrefix = imgDataNamePrefix;
   }

   public File getStoreDirectory() {
     return this.storeDirectory;
   }

   public File[] getImgIndexs() {
     return this.imgIndexs;
   }

   public File getImgIndex(int block) {
     return this.imgIndexs[block];
   }

   public File[] getImgBlocks() {
     return this.imgBlocks;
   }

   public File getImgBlock(int block) {
     return this.imgBlocks[block];
   }

   public void setBlocks(int blocks) {
     this.blocks = blocks;
   }

   public int getBlocks() {
     return this.blocks;
   }

   public long getDataFileLength() {
     return this.dataFileLength;
   }

   public void setDataFileLength(int dataFileLength) {
     if (dataFileLength <= 0) {
       throw new IllegalArgumentException("dataFileLength must greate than 0.");
     }

     this.dataFileLength = dataFileLength;
   }

   public int getFlushIndexPeriod() {
     return this.flushIndexPeriod;
   }

   public void setFlushIndexPeriod(int flushIndexPeriod) {
     this.flushIndexPeriod = flushIndexPeriod;
   }

   public FileChannel[] getImgFileChannels() {
     return this.imgFileChannels;
   }

   public FileChannel getImgFileChannel(short block) {
     return this.imgFileChannels[block];
   }

   public String toString()
   {
     StringBuilder sb = new StringBuilder("ImgCacheStoreConfig:");
     sb.append("storePath[").append(this.storePath);
     sb.append("] blocks[").append(this.blocks);
     sb.append("] dataFileLength[").append(this.dataFileLength);
     sb.append("] imgIndexNamePrefix[").append(this.imgIndexNamePrefix);
     sb.append("] imgDataNamePrefix[").append(this.imgDataNamePrefix);
     sb.append("] flushIndexPeriod[").append(this.flushIndexPeriod).append(']');
     return sb.toString();
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.file.ImgCacheStoreConfig
 * JD-Core Version:    0.6.2
 */