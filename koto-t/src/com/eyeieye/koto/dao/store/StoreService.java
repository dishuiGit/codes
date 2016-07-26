package com.eyeieye.koto.dao.store;

import com.eyeieye.koto.domain.StoredEntry;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

public abstract interface StoreService
{
  public abstract String saveFile(byte[] paramArrayOfByte, String paramString, Map<String, Serializable> paramMap);

  public abstract String saveFile(InputStream paramInputStream, String paramString, Map<String, Serializable> paramMap);

  public abstract StoredEntry getStoredEntry(String paramString);

  public abstract StoredEntry findStoredEntry(String paramString1, String paramString2);

  public abstract StoredEntry findFile(Map<String, Object> paramMap);

  public abstract long sumImageLength(String paramString);

  public abstract Map<String, Object> findFileAttributes(Map<String, Object> paramMap);

  public abstract Map<String, Object> findFileAttributesById(String paramString);

  public abstract void removeFile(String paramString);

  public abstract void removeFile(Map<String, Object> paramMap);

  public abstract FilesAttributesResult findFilesAttributes(Map<String, Object> paramMap, int paramInt1, int paramInt2);

  public abstract FilesAttributesResult findFilesAttributes(Map<String, Object> paramMap);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.store.StoreService
 * JD-Core Version:    0.6.2
 */