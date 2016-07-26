package com.eyeieye.koto.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface StoredImage
{
  public abstract String getFormat();

  public abstract int getLength();

  public abstract InputStream getBodyInputStream();

  public abstract void write(OutputStream paramOutputStream)
    throws IOException;

  public abstract Object getNettyChannelWrited();
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.domain.StoredImage
 * JD-Core Version:    0.6.2
 */