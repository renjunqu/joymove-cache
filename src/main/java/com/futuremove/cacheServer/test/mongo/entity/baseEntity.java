package com.futuremove.cacheServer.test.mongo.entity;

import org.bson.Document;
import org.bson.types.ObjectId;


import javax.print.Doc;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qurj on 15/7/2.
 */
public class baseEntity {
    public ObjectId _id;
    public String test2;





    public String toString(){
        Document thisDoc = this.toDocument();
        return thisDoc.toJson();
    }

    public void fromDocument(Document document){
        Field[] fs = this.getClass().getFields();
        try {
            for(Field f : fs) {
                f.setAccessible(true);
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    // do nothing
                } else if(document.get(f.getName())!=null) {
                    if(baseEntity.class.isAssignableFrom(f.getType())
                            && document.get(f.getName()).getClass().equals(Document.class)) {
                       //如果属性是baseEntity , document 里面的内容是Document,一个子文档，那么....
                        Class entityClass = f.getType();
                        Object valueObj = entityClass.newInstance();
                        ((baseEntity)valueObj).fromDocument((Document) document.get(f.getName()));
                        f.set(this,entityClass.cast(valueObj));
                    } else {
                        f.set(this,document.get(f.getName()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Document  toDocument(){
        Document reDoc = new Document();
        Field[] fs = this.getClass().getFields();
        try {
            for(Field f:fs) {
                f.setAccessible(true);
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    // do nothing
                } else  if(baseEntity.class.isAssignableFrom(f.getType())) {
                         System.out.println("could be assin");
                        reDoc.append(f.getName(),((baseEntity)f.get(this)).toDocument());
                } else if(f.get(this)!=null) {
                        reDoc.append(f.getName(),f.get(this));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reDoc;
    }

}
