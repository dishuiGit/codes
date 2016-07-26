package com.eyeieye.koto.domain;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface StoredEntry
{
  public abstract Object getAppend(String paramString);

  public abstract void writeTo(OutputStream paramOutputStream)
    throws IOException;

  public abstract int getLength();
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.domain.StoredEntry
 * JD-Core Version:    0.6.2
 */