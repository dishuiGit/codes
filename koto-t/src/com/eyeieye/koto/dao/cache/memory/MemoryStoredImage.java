package com.eyeieye.koto.dao.cache.memory;

import com.eyeieye.koto.domain.StoredImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import org.jboss.netty.buffer.ChannelBuffers;

public class MemoryStoredImage
   implements StoredImage
 {
   private String format;
   private ByteBuffer byteBuffer;

   public MemoryStoredImage()
   {
   }

   public MemoryStoredImage(MemoryStoredImage source)
   {
     this.format = source.format;
     this.byteBuffer = source.byteBuffer.duplicate();
   }

   public String getFormat()
   {
     return this.format;
   }

   public int getLength()
   {
     return this.byteBuffer.capacity();
   }

   public InputStream getBodyInputStream()
   {
     this.byteBuffer.rewind();
     byte[] bs = new byte[this.byteBuffer.capacity()];
     this.byteBuffer.get(bs);
     return new ByteArrayInputStream(bs);
   }

   public void write(OutputStream out) throws IOException
   {
     this.byteBuffer.rewind();
     WritableByteChannel channel = Channels.newChannel(out);
     channel.write(this.byteBuffer);
   }

   public Object getNettyChannelWrited()
   {
     this.byteBuffer.rewind();
     return ChannelBuffers.wrappedBuffer(this.byteBuffer);
   }

   public ByteBuffer getByteBuffer() {
     return this.byteBuffer;
   }

   public void setByteBuffer(ByteBuffer byteBuffer) {
     this.byteBuffer = byteBuffer;
   }

   public void setFormat(String format) {
     this.format = format;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.cache.memory.MemoryStoredImage
 * JD-Core Version:    0.6.2
 */