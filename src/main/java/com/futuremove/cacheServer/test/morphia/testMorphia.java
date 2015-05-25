package com.futuremove.cacheServer.test.morphia;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.test.morphia.entity.event.Event;
import com.futuremove.cacheServer.test.morphia.entity.event.RegisterEvent;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class testMorphia {
	
	public static void main(String [] args){
		try {
			
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
			
			Datastore  datastore  = (Datastore)context.getBean("datastore");
			
			RegisterEvent event = new RegisterEvent();
			event.setEventId("123");
			event.setRegisterTime(new Date(System.currentTimeMillis()));
			event.setType("register");
			event.setVinNum("sdfdsf");
			datastore.save(event);
			event.setEventId("qrj");
			
			
			
			
			
			datastore.save(event);
			
			/*
			Query t = datastore.createQuery(Event.class).field("eventId").equal("123");
			List<Event> es = t.asList();
			System.out.println("sdfsdf");
			System.out.println(es.get(0).getEventId());
			*/
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
