package com.futuremove.cacheServer.test.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by qurj on 15/7/2.
 */
public class testNearCmd {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new ServerAddress("123.57.151.176", 27017));
        MongoDatabase database = mongoClient.getDatabase("test");
        //MongoCollection<Document> collection = database.getCollection("testNew");
       // database.runCommand(new Document().append());
        return;
    }
}
