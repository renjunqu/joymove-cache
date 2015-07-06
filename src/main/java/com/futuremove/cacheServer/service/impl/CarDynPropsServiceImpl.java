package com.futuremove.cacheServer.service.impl;

import com.futuremove.cacheServer.service.TestService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by qurj on 15/7/6.
 */
public class CarDynPropsServiceImpl extends BaseServiceImpl implements TestService {
    @Override
    public MongoCollection<Document> getCollection() {
        return null;
    }
}
