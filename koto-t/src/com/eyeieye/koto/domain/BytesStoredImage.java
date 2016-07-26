package com.eyeieye.koto.domain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class BytesStoredImage
   implements StoredImage
 {
   private String format;
   private byte[] body;

   public String getFormat()
   {
     return this.format;
   }

   public InputStream getBodyInputStream()
   {
     return new ByteArrayInputStream(this.body);
   }

   public int getLength()
   {
     return this.body.length;
   }

   public void write(OutputStream out) throws IOException
   {
     out.write(this.body);
   }

   public Object getNettyChannelWrited()
   {
     ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(this.body);
     return channelBuffer;
   }

   public byte[] getBody() {
     return this.body;
   }

   public void setBody(byte[] body) {
     this.body = body;
   }

   public void setFormat(String format) {
     this.format = format;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.domain.BytesStoredImage
 * JD-Core Version:    0.6.2
 */