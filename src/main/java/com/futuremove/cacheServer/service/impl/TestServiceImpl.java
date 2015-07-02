package com.futuremove.cacheServer.service.impl;

import com.futuremove.cacheServer.beans.MongoClientFactoryBean;
import com.futuremove.cacheServer.entity.Base;
import com.futuremove.cacheServer.entity.Test;
import com.futuremove.cacheServer.service.TestService;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qurj on 15/7/2.
 */

@Component("TestService")
public class TestServiceImpl  extends BaseServiceImpl implements TestService {

    @Autowired
    MongoClient mongoClient;

    @Override
    public MongoCollection<Document> getCollection() {
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testNew2");
        return collection;
    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test.xml");
        TestService service  = (TestService)context.getBean("TestService");
        Test test = new Test();
        test.hehe = "sdfdsfsdf";
        Test test2=new Test();
        test2.hehe = "12345";
        FindIterable<Document> docIter = service.find(test);
        for(Document doc:docIter) {
            Test t = new Test();
            t.fromDocument(doc);
            System.out.println(t.toString());
        }



    }
}
