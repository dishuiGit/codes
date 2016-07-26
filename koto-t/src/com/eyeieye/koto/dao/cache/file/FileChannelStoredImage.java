package com.eyeieye.koto.dao.cache.file;

import com.eyeieye.koto.domain.StoredImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import org.jboss.netty.channel.DefaultFileRegion;

public class FileChannelStoredImage
   implements StoredImage
 {
   private String format;
   private FileChannel fileChannel;
   private long position;
   private int length;

   public String getFormat()
   {
     return this.format;
   }

   public int getLength()
   {
     return this.length;
   }

   private byte[] getBodyBytes() {
     byte[] body = new byte[this.length];
     try {
       MappedByteBuffer buffer = this.fileChannel.map(FileChannel.MapMode.READ_ONLY, this.position, this.length);

       buffer.get(body);
       return body;
     } catch (IOException e) {
       throw new RuntimeException(e);
     }
   }

   public InputStream getBodyInputStream()
   {
     return new ByteArrayInputStream(getBodyBytes());
   }

   public void write(OutputStream out) throws IOException
   {
     out.write(getBodyBytes());
   }

   public Object getNettyChannelWrited()
   {
     return new DefaultFileRegion(this.fileChannel, this.position, this.length);
   }

   public FileChannel getFileChannel() {
     return this.fileChannel;
   }

   public void setFileChannel(FileChannel fileChannel) {
     this.fileChannel = fileChannel;
   }

   public long getPosition() {
     return this.position;
   }

   public void setPosition(long position) {
     this.position = position;
   }

   public void setFormat(String format) {
     this.format = format;
   }

   public void setLength(int length) {
     this.length = length;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.file.FileChannelStoredImage
 * JD-Core Version:    0.6.2
 */