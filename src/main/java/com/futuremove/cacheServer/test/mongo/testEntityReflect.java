package com.futuremove.cacheServer.test.mongo;

import com.futuremove.cacheServer.test.mongo.entity.baseEntity;
import com.futuremove.cacheServer.test.mongo.entity.pointEntity;
import com.futuremove.cacheServer.test.mongo.entity.testEntity;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;

/**
 * Created by qurj on 15/7/2.
 */
public class testEntityReflect {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("123.57.151.176", 27017));
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testNew");
        /*
        testEntity entity = new testEntity();
        entity.test1 = "qrj";
        entity.test2 = "i love u";
        entity.loc = new pointEntity();
        entity.loc.type = "Point";
        ArrayList<Double> coordinates = new ArrayList<Double>();
        entity.loc.coordinates = coordinates;
        coordinates.add(1.3);
        coordinates.add(3.2);
        collection.insertOne(entity.toDocument());
        */




        FindIterable<Document> qq = collection.find();
        qq.skip(10);
        qq.limit(10);
        for(Document d:qq) {
              testEntity e = new testEntity();
            e.fromDocument(d);
            System.out.println(e.toString());

        }



    }
}
