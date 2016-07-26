package com.eyeieye.koto.dao.store.impl;

import com.eyeieye.koto.dao.store.FilesAttributesResult;
import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.domain.StoredEntry;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;

public class MongoStoreServiceImpl
   implements StoreService
 {
   private static Log logger = LogFactory.getLog(MongoStoreServiceImpl.class);
   private GridFS gridFS;
   private DBCollection girdFsCollection;
   private static final String LengthMapFunctionPer = "function(){if(";
   private static final String LengthMapFunctionPost = ") { emit(0,this.length)}}";
   private static final String LengthReduceFunction = "function( key , vals ){var sum = 0;for(var i in vals) sum += vals[i];return sum; }";

   public void init()
   {
     this.girdFsCollection = this.gridFS.getDB().getCollection(this.gridFS.getBucketName() + ".files");
   }

   public String saveFile(byte[] body, String filename, Map<String, Serializable> appends)
   {
     GridFSFile file = this.gridFS.createFile(new ByteArrayInputStream(body), filename, true);

     if ((appends != null) && (!appends.isEmpty())) {
       for (Map.Entry entry : appends.entrySet()) {
         file.put((String)entry.getKey(), entry.getValue());
       }
     }
     file.save();
     ObjectId oid = (ObjectId)file.getId();
     return oid.toString();
   }

   public String saveFile(InputStream body, String filename, Map<String, Serializable> appends)
   {
     GridFSFile file = this.gridFS.createFile(body, filename, false);
     if ((appends != null) && (!appends.isEmpty())) {
       file.putAll(appends);
     }
     file.save();
     ObjectId oid = (ObjectId)file.getId();
     return oid.toString();
   }

   public StoredEntry getStoredEntry(String oid)
   {
     if (oid == null) {
       throw new NullPointerException("oid can't be null");
     }
     ObjectId id = new ObjectId(oid);
     GridFSDBFile file = this.gridFS.findOne(new BasicDBObject("_id", id));
     if (file == null) {
       return null;
     }
     return new GirdFsStore(file);
   }

   public StoredEntry findStoredEntry(String key, String value)
   {
     if (key == null) {
       throw new NullPointerException("key can't be null");
     }
     if (value == null) {
       throw new NullPointerException("value can't be null");
     }
     GridFSDBFile file = this.gridFS.findOne(new BasicDBObject(key, value));
     if (file == null) {
       return null;
     }
     return new GirdFsStore(file);
   }

   public StoredEntry findFile(Map<String, Object> query)
   {
     if (query == null) {
       throw new NullPointerException("query can't be null.");
     }
     if (query.isEmpty()) {
       throw new IllegalArgumentException("query can't be empty.");
     }
     BasicDBObject obj = new BasicDBObject();
     obj.putAll(query);
     GridFSDBFile file = this.gridFS.findOne(obj);
     if (file == null) {
       return null;
     }
     return new GirdFsStore(file);
   }

   public long sumImageLength(String condition)
   {
     if (condition == null) {
       throw new NullPointerException("condition can't be null.");
     }
     String map = "function(){if(" + condition + ") { emit(0,this.length)}}";
     if (logger.isDebugEnabled()) {
       logger.debug("sumImageLength map function:" + map);
     }
     MapReduceOutput out = null;
     try {
       out = this.girdFsCollection.mapReduce(map, "function( key , vals ){var sum = 0;for(var i in vals) sum += vals[i];return sum; }", null, null);

       Iterable results = out.results();
       Iterator ret = results.iterator();
       Number value;
       if (ret.hasNext()) {
         value = (Number)((DBObject)ret.next()).get("value");
         return value.longValue();
       }
       return 0L;
     }
     finally {
       if (out != null)
         out.drop();
     }
   }

   public Map<String, Object> findFileAttributes(Map<String, Object> query)
   {
     if (query == null) {
       throw new NullPointerException("query can't be null.");
     }
     if (query.isEmpty()) {
       throw new IllegalArgumentException("query can't be empty.");
     }
     BasicDBObject queryObj = new BasicDBObject();
     queryObj.putAll(query);
     return find(queryObj);
   }

   public Map<String, Object> findFileAttributesById(String id)
   {
     if (id == null) {
       throw new NullPointerException("id can't be null.");
     }
     BasicDBObject queryObj = new BasicDBObject();
     queryObj.append("_id", new ObjectId(id));
     return find(queryObj);
   }

   private Map<String, Object> find(DBObject query) {
     DBCursor cur = this.girdFsCollection.find(query);
     if (cur.hasNext()) {
       Map back = new HashMap();
       DBObject obj = cur.next();
       for (String att : obj.keySet()) {
         Object v = obj.get(att);
         if ((v instanceof ObjectId))
           back.put(att, ((ObjectId)v).toString());
         else {
           back.put(att, v);
         }
       }
       return back;
     }
     return Collections.emptyMap();
   }

   public void removeFile(String id)
   {
     if (id == null) {
       throw new NullPointerException("id can't be null.");
     }
     ObjectId oid = new ObjectId(id);
     this.gridFS.remove(oid);
   }

   public void removeFile(Map<String, Object> query)
   {
     if (query == null) {
       throw new NullPointerException("id can't be null.");
     }
     if (query.isEmpty()) {
       throw new IllegalArgumentException("query can't be empty.");
     }
     BasicDBObject queryObj = new BasicDBObject();
     queryObj.putAll(query);
     this.gridFS.remove(queryObj);
   }

   public FilesAttributesResult findFilesAttributes(Map<String, Object> query, int start, int end)
   {
     FilesAttributesResult result = new FilesAttributesResult();
     BasicDBObject sort = new BasicDBObject("uploadDate", Integer.valueOf(-1));
     BasicDBObject queryObj = new BasicDBObject();
     queryObj.putAll(query);

     DBCursor cur = this.girdFsCollection.find(queryObj).sort(sort).skip(start).limit(end - start);

     result.setTotal(cur.count());

     while (cur.hasNext()) {
       DBObject one = cur.next();
       Map map = new HashMap();
       for (String key : one.keySet()) {
         if (key.equals("_id"))
           map.put("id", one.get(key).toString());
         else {
           map.put(key, one.get(key));
         }
       }
       result.addMap(map);
     }
     return result;
   }

   public FilesAttributesResult findFilesAttributes(Map<String, Object> query)
   {
     return findFilesAttributes(query, 0, 100);
   }

   public GridFS getGridFS() {
     return this.gridFS;
   }

   public void setGridFS(GridFS gridFS) {
     this.gridFS = gridFS;
   }

   private static final class GirdFsStore
     implements StoredEntry
   {
     private GridFSDBFile f;

     public GirdFsStore(GridFSDBFile f)
     {
       this.f = f;
     }

     public Object getAppend(String key)
     {
       return this.f.get(key);
     }

     public void writeTo(OutputStream out) throws IOException
     {
       this.f.writeTo(out);
     }

     public int getLength()
     {
       return (int)this.f.getLength();
     }
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.store.impl.MongoStoreServiceImpl
 * JD-Core Version:    0.6.2
 */