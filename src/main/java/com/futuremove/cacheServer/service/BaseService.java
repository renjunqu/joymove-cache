package com.futuremove.cacheServer.service;

import com.futuremove.cacheServer.entity.Base;
import com.mongodb.client.FindIterable;
import org.bson.Document;

/**
 * Created by qurj on 15/7/2.
 */
public interface BaseService {
    public void update(Base query,Base value);
    public FindIterable<Document> find(Base query);
    public long count(Base query);
    public void delete(Base query);
    public void insert(Base value);
}
