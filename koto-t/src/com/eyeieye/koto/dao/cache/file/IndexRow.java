package com.eyeieye.koto.dao.cache.file;

import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.domain.StoredImage;

public class IndexRow
 {
   private byte[] id;
   private byte type;
   private short block;
   private long positionLength;

   public IndexRow()
   {
   }

   public IndexRow(StoredImage image, short block, long position)
   {
     setFormat(image.getFormat());
     setBlock(block);
     setPositionLength(position, image.getLength());
   }

   public void setPositionLength(long position, int length) {
     this.positionLength = (position << 32);
     this.positionLength |= length;
   }

   public ImgType getImgType() {
     return ImgType.values()[this.type];
   }

   public void setFormat(String format) {
     this.type = ((byte)ImgType.valueOf(format).ordinal());
   }

   public long getEndPosition() {
     return getPosition() + getLength();
   }

   public byte[] getId() {
     return this.id;
   }

   public void setId(byte[] id) {
     this.id = id;
   }

   public byte getType() {
     return this.type;
   }

   public void setType(byte type) {
     this.type = type;
   }

   public int getLength() {
     return (int)this.positionLength;
   }

   public short getBlock() {
     return this.block;
   }

   public void setBlock(short block) {
     this.block = block;
   }

   public long getPosition() {
     return this.positionLength >>> 32;
   }

   public long getPositionLength() {
     return this.positionLength;
   }

   public void setPositionLength(long positionLength) {
     this.positionLength = positionLength;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.file.IndexRow
 * JD-Core Version:    0.6.2
 */