package com.futuremove.cacheServer.service.impl;

import com.futuremove.cacheServer.entity.Base;
import com.futuremove.cacheServer.service.BaseService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import javax.print.Doc;
import java.util.Date;

/**
 * Created by qurj on 15/7/2.
 */
public abstract class BaseServiceImpl implements BaseService {
    //获取到当前的collection
    public abstract  MongoCollection<Document> getCollection();

    public void update(Base query, Base value) {
        MongoCollection<Document> collection = this.getCollection();
        Document setDocument  = new Document();
        UpdateOptions options = new UpdateOptions();
        options.upsert(false);
        value.updateTime = new Date(System.currentTimeMillis());
        setDocument.append("$set",value.toDocument());
        collection.updateMany(query.toDocument(), setDocument, options);
    }

    public FindIterable<Document> find(Base query) {
        MongoCollection<Document> collection = this.getCollection();
        return collection.find(query.toDocument());
    }

    public void delete(Base query) {
        MongoCollection<Document> collection = this.getCollection();
        collection.deleteMany(query.toDocument());
    }

    public void insert(Base value) {
        MongoCollection<Document> collection = this.getCollection();
        value.createTime = new Date(System.currentTimeMillis());
        value.updateTime = new Date(System.currentTimeMillis());
        collection.insertOne(value.toDocument());
    }

    public long count(Base query) {
        MongoCollection<Document> collection = this.getCollection();
        return collection.count(query.toDocument());
    }
}
