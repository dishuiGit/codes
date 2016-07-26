package com.eyeieye.koto.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class FixsizeByteArrayOutputStream extends OutputStream
 {
   protected byte[] buf;
   protected int count;

   public FixsizeByteArrayOutputStream(int size)
   {
     if (size < 0) {
       throw new IllegalArgumentException("Negative initial size: " + size);
     }
     this.buf = new byte[size];
   }

   public void write(int b)
   {
     int newcount = this.count + 1;
     if (newcount > this.buf.length) {
       this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, newcount));
     }
     this.buf[this.count] = ((byte)b);
     this.count = newcount;
   }

   public void write(byte[] b, int off, int len)
   {
     if ((off < 0) || (off > b.length) || (len < 0) || (off + len > b.length) || (off + len < 0))
     {
       throw new IndexOutOfBoundsException();
     }if (len == 0) {
       return;
     }
     int newcount = this.count + len;
     if (newcount > this.buf.length) {
       this.buf = Arrays.copyOf(this.buf, Math.max(this.buf.length << 1, newcount));
     }
     System.arraycopy(b, off, this.buf, this.count, len);
     this.count = newcount;
   }

   public void writeTo(OutputStream out)
     throws IOException
   {
     out.write(this.buf, 0, this.count);
   }

   public void reset()
   {
     this.count = 0;
   }

   public byte[] toByteArray()
   {
     return this.buf;
   }

   public int size()
   {
     return this.count;
   }

   public String toString()
   {
     return new String(this.buf, 0, this.count);
   }

   public String toString(String charsetName)
     throws UnsupportedEncodingException
   {
     return new String(this.buf, 0, this.count, charsetName);
   }

   @Deprecated
   public String toString(int hibyte)
   {
     return new String(this.buf, hibyte, 0, this.count);
   }

   public void close()
     throws IOException
   {
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.common.FixsizeByteArrayOutputStream
 * JD-Core Version:    0.6.2
 */