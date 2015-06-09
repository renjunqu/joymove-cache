package com.futuremove.cacheServer.xml;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by jessie on 2015/6/9.
 */
public class Entity {
    /*记住,java的String里面保持的都是Unicode*/
    public String name;
    public String content;
    public HashMap<String,String> attributes;
    public HashMap<String,Entity> children;
    //知道自己是第几级
    public  int level;

    public String toString(){
        String prefix = StringUtils.repeat("\t\t",this.level);
        StringBuffer sb = new StringBuffer();
        sb.append(prefix + "{\n");
        sb.append(prefix+"content:"+this.content+"\n");
        sb.append(prefix+"level:"+this.level+"\n");
        if(this.attributes!=null) {
            sb.append(prefix + "attributes : {\n");
            for(Entry<String, String> entry : this.attributes.entrySet()){
                 sb.append(prefix+" "+entry.getKey()+":"+entry.getValue()+" \n");
            }
            sb.append(prefix + "}\n");
        }
        if(this.children!=null) {
            sb.append(prefix + "childrens : {\n");
            for(Entry<String, Entity> entry : this.children.entrySet()){
                sb.append(prefix+" "+entry.getKey()+":"+entry.getValue().toString());
            }
            sb.append(prefix + "}\n");
        }
        sb.append(prefix+"}\n");
        return sb.toString();
    }
    //以dfs的方式查找第一个名字匹配的子元素
    public Entity dfsFind(String targetName){
        if(this.children!=null) {
            for(Entry<String, Entity> entry : this.children.entrySet()){
              if(entry.getKey().equals(targetName))
                  return entry.getValue();
                else {
                  Entity childResult = entry.getValue().dfsFind(targetName);
                  if(childResult!=null)
                      return childResult;
              } // else
            } // for Entry
        } // if children != null
        return null;


    }
}
