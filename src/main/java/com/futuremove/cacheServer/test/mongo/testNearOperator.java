package com.futuremove.cacheServer.test.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;


/**
 * Created by qurj on 15/7/2.
 */
public class testNearOperator {
    public static void main(String[] args)  throws  Exception {
        MongoClient mongoClient = new MongoClient(new ServerAddress("123.57.151.176", 27017));
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testGeo");
        Document locFilter = new Document();
        Document centerPointer = new Document();
        centerPointer.append("type", "Point");
        ArrayList<Double> ar = new ArrayList<Double>();
        ar.add(1.0);
        ar.add(2.0);
        centerPointer.append("coordinates", ar);
        locFilter.append("$near", (new Document())
                        .append("$geometry", centerPointer)
                        .append("$minDistance", 10)
                        .append("$maxDistance", 3500000000L)
        );
        Document queryFilter = new Document().append("loc",locFilter);
        FindIterable<Document> iterable =  collection.find(queryFilter);
        for (Document document : iterable) {
            System.out.println(document.toJson());
        }





    }

}
