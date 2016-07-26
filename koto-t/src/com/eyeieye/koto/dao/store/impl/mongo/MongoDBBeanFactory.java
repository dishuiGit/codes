package com.eyeieye.koto.dao.store.impl.mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class MongoDBBeanFactory
   implements FactoryBean<DB>, InitializingBean, DisposableBean
 {
   private DB mongoDB;
   private List<ServerAddress> address;
   private String dbName;
   private int optionsConnectionsPerHost = 20;

   private int writeConcern = 0;

   private int writeConcernTimeout = 0;

   private boolean slaveOk = false;

   public void setMongoUrls(String mongoUrls)
     throws Exception
   {
     if (StringUtils.isBlank(mongoUrls)) {
       throw new IllegalArgumentException("mongo urls can't be null or empty.");
     }

     String[] urls = StringUtils.split(mongoUrls, ',');
     this.address = new ArrayList(urls.length);
     for (String url : urls) {
       ServerAddress sa = new ServerAddress(url);
       this.address.add(sa);
     }
   }

   public void setDbName(String db)
   {
     this.dbName = db;
   }

   public void setOptionsConnectionsPerHost(int optionsConnectionsPerHost) {
     this.optionsConnectionsPerHost = optionsConnectionsPerHost;
   }

   public void afterPropertiesSet() throws Exception
   {
     MongoOptions options = new MongoOptions();
     options.connectionsPerHost = this.optionsConnectionsPerHost;
     Mongo mongo = new Mongo(this.address, options);
     if (isSlaveOk())
       mongo.setReadPreference(ReadPreference.secondaryPreferred());
     else {
       mongo.setReadPreference(ReadPreference.primaryPreferred());
     }

     WriteConcern w = new WriteConcern(this.writeConcern, this.writeConcernTimeout);

     mongo.setWriteConcern(w);
     this.mongoDB = mongo.getDB(this.dbName);
   }

   public void destroy() throws Exception
   {
     this.mongoDB.getMongo().close();
   }

   public DB getObject() throws Exception
   {
     return this.mongoDB;
   }

   public Class<?> getObjectType()
   {
     return DB.class;
   }

   public boolean isSingleton()
   {
     return true;
   }

   public int getWriteConcern() {
     return this.writeConcern;
   }

   public void setWriteConcern(int writeConcern) {
     this.writeConcern = writeConcern;
   }

   public int getWriteConcernTimeout() {
     return this.writeConcernTimeout;
   }

   public void setWriteConcernTimeout(int writeConcernTimeout) {
     this.writeConcernTimeout = writeConcernTimeout;
   }

   public boolean isSlaveOk() {
     return this.slaveOk;
   }

   public void setSlaveOk(boolean slaveOk) {
     this.slaveOk = slaveOk;
   }

   public String getDbName() {
     return this.dbName;
   }

   public int getOptionsConnectionsPerHost() {
     return this.optionsConnectionsPerHost;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.dao.store.impl.mongo.MongoDBBeanFactory
 * JD-Core Version:    0.6.2
 */