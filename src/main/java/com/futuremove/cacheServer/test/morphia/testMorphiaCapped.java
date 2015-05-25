package com.futuremove.cacheServer.test.morphia;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.test.Human;
import com.futuremove.cacheServer.test.morphia.entity.updateStatusData;
import com.futuremove.cacheServer.test.morphia.entity.event.Event;
import com.futuremove.cacheServer.test.morphia.entity.event.RegisterEvent;
import com.futuremove.cacheServer.test.morphia.entity.event.UpdateStatusEvent;
import com.mongodb.Bytes;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class testMorphiaCapped {

	 public static void main(String [] args){
			try {
				
				ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
				
				Datastore  datastore  = (Datastore)context.getBean("cappedDatastore");
				
				MongoClient mClient = (MongoClient)context.getBean("mongoClient");
				DB cappedDb = mClient.getDB("cappedEvent");
				DBCollection dbC = cappedDb.getCollection("test1");
				DBCursor cursor = dbC.find();
				cursor.addOption(Bytes.QUERYOPTION_TAILABLE).addOption(Bytes.QUERYOPTION_AWAITDATA);
				System.out.println("start to tail");
				while(cursor.hasNext()){
					DBObject obj = cursor.next();
					System.out.println("show next");
					System.out.println(obj);
				}
				
			
				
			} catch(Exception e){
				e.printStackTrace();
				
			}
		 
	 }
}
