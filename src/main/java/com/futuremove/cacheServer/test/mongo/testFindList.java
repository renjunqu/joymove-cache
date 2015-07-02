package com.futuremove.cacheServer.test.mongo;

import com.futuremove.cacheServer.test.mongo.entity.pointEntity;
import com.futuremove.cacheServer.test.mongo.entity.testEntity;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qurj on 15/7/2.
 */
public class testFindList {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("123.57.151.176", 27017));
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testNew");

        /*
        List<Document> entityList = new ArrayList<Document>();
        int count = 0;
        while(count <100 ) {
            count ++;
            testEntity entity = new testEntity();
            entity.test1 = "qrj"+ count;
            entity.test2 = "i love u" + count;
            entity.loc = new pointEntity();
            entity.loc.type = "Point";
            ArrayList<Double> coordinates = new ArrayList<Double>();
            entity.loc.coordinates = coordinates;
            coordinates.add(Math.random()*50);
            coordinates.add(Math.random()*20);
            entityList.add(entity.toDocument());

        }

        collection.insertMany(entityList);
        */




    }


}
