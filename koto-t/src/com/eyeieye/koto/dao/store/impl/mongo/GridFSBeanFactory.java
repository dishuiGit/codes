package com.eyeieye.koto.dao.store.impl.mongo;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class GridFSBeanFactory
   implements FactoryBean<GridFS>, InitializingBean
 {
   private GridFS gridFS;
   private DB mongoDB;
   private String bucket;

   @Autowired
   public void setMongoDB(DB mongoDB)
   {
     this.mongoDB = mongoDB;
   }

   public void setBucket(String bucket) {
     this.bucket = bucket;
   }

   public void afterPropertiesSet() throws Exception
   {
     this.gridFS = new GridFS(this.mongoDB, this.bucket);
   }

   public GridFS getObject() throws Exception
   {
     return this.gridFS;
   }

   public Class<?> getObjectType()
   {
     return GridFS.class;
   }

   public boolean isSingleton()
   {
     return true;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.store.impl.mongo.GridFSBeanFactory
 * JD-Core Version:    0.6.2
 */