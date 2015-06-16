package com.futuremove.cacheServer.test;

import java.awt.*;

/**
 * Created by qurj on 15/5/25.
 */
public class testStr {

    public static void main(String []args){
        String test = "水电费123abc你";
        for(int i=0;i<test.length();i++) {
            long m = (long)test.charAt(i);
            if(m<128) {
                   // logger.trace("ascii");
                 } else {
               // logger.trace(" not ascii");
            }
           // logger.trace(test.charAt(i));

        }

    }
}
