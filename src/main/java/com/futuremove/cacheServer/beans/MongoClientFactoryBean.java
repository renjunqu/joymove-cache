package com.futuremove.cacheServer.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoClientFactoryBean  extends AbstractFactoryBean {
	   // 表示服务器列表(主从复制或者分片)的字符串数组
   private String[] serverStrings;
   // mongoDB配置对象
   
   private boolean readSecondary = false;
   // 设定写策略(出错时是否抛异常)，默认采用SAFE模式(需要抛异常)
   private WriteConcern writeConcern = WriteConcern.SAFE;
   
   
 
   @Override
   public Class<?> getObjectType() {
       return MongoClient.class;
   }
 
   @Override
   protected Mongo createInstance() throws Exception {
	   MongoClient mongo = initMongo();
       
       // 设定主从分离
       if (readSecondary) {
           mongo.setReadPreference(ReadPreference.secondaryPreferred());
       }
 
       // 设定写策略
       mongo.setWriteConcern(writeConcern);
       return mongo;
   }
   
   /**
    * 初始化mongo实例
    * @return
    * @throws Exception
    */
   private MongoClient initMongo() throws Exception {

       // 根据条件创建Mongo实例
	   MongoClientOptions options = new MongoClientOptions.Builder()
	   .connectTimeout(600000)
	   .heartbeatConnectTimeout(2000)
	   .heartbeatFrequency(10) //check every 10ms, for recovery when server restart
       .build();
	   
	   MongoClient mongo = null;
       List<ServerAddress> serverList = getServerList();
 
       if (serverList.size() == 0) {
           mongo = new MongoClient();
       }else if(serverList.size() == 1){
          
               mongo = new MongoClient(serverList.get(0),options);
           
       }else{
          
               mongo = new MongoClient(serverList,options);
           
       }
       return mongo;
   }
   
   
   /**
    * 根据服务器字符串列表，解析出服务器对象列表
    * <p>
    * 
    * @Title: getServerList
    *         </p>
    * 
    * @return
    * @throws Exception
    */
   private List<ServerAddress> getServerList() throws Exception {
       List<ServerAddress> serverList = new ArrayList<ServerAddress>();
       try {
           for (String serverString : serverStrings) {
               String[] temp = serverString.split(":");
               String host = temp[0];
               if (temp.length > 2) {
                   throw new IllegalArgumentException(
                           "Invalid server address string: " + serverString);
               }
               if (temp.length == 2) {
                   serverList.add(new ServerAddress(host, Integer
                           .parseInt(temp[1])));
               } else {
                   serverList.add(new ServerAddress(host));
               }
           }
           return serverList;
       } catch (Exception e) {
           throw new Exception(
                   "Error while converting serverString to ServerAddressList",
                   e);
       }
   }
   
   /* ------------------- setters --------------------- */

public String[] getServerStrings() {
	return serverStrings;
}

public void setServerStrings(String[] serverStrings) {
	this.serverStrings = serverStrings;
}

public boolean isReadSecondary() {
	return readSecondary;
}

public void setReadSecondary(boolean readSecondary) {
	this.readSecondary = readSecondary;
}

public WriteConcern getWriteConcern() {
	return writeConcern;
}

public void setWriteConcern(WriteConcern writeConcern) {
	this.writeConcern = writeConcern;
}




}
