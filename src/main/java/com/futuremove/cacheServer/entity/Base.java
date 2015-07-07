package com.futuremove.cacheServer.entity;

import org.bson.Document;
import org.bson.types.ObjectId;


import javax.print.Doc;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qurj on 15/7/2.
 */
public class Base {
    public ObjectId _id;
    public Date createTime;
    public Date updateTime;






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
                    if(Base.class.isAssignableFrom(f.getType())
                            && document.get(f.getName()).getClass().equals(Document.class)) {
                        //如果属性是baseEntity , document 里面的内容是Document,一个子文档，那么....
                        Class entityClass = f.getType();
                        Object valueObj = entityClass.newInstance();
                        ((Base)valueObj).fromDocument((Document) document.get(f.getName()));
                        f.set(this,entityClass.cast(valueObj));
                    }else if(f.getType().equals(Integer.class) && document.get(f.getName()).getClass().equals(Double.class)){
                         Double valDouble =  (Double)document.get(f.getName());
                         f.set(this,valDouble.intValue());
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
                } else  if(Base.class.isAssignableFrom(f.getType()) && f.get(this)!=null) {
                   // System.out.println("could be assin");
                    if(f.get(this)==null)
                        System.out.println("its null");
                    else
                        System.out.println("its not null");
                    reDoc.append(f.getName(),((Base)f.get(this)).toDocument());
                } else if(f.get(this)!=null) {
                    reDoc.append(f.getName(),f.get(this));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reDoc;
    }

    public void clearProperties(){
        Field[] fs = this.getClass().getFields();
        try {
            for(Field f:fs) {
                f.setAccessible(true);
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    // do nothing
                } else {
                    f.set(this,null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
