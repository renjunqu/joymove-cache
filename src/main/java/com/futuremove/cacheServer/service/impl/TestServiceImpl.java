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
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static void main(String[] args) throws  Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test.xml");
        TestService service  = (TestService)context.getBean("TestService");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Test t = new Test();
        t.int_haha = 3;
        t.double_haha = 9.35;
        t.date_haha = dateFormat.parse("2015-01-01 10:20:02");
        t.qqq = new Binary("sdfsdf".getBytes());
        service.delete(t);

     /*
        FindIterable<Document> rets = service.find(t);

        for(Document doc:rets) {
            Test test = new Test();
            test.fromDocument(doc);
            System.out.println(test);
        }
     */

    }
}
