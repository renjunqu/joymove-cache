package com.futuremove.cacheServer.entity;

import java.util.ArrayList;

/**
 * Created by qurj on 15/7/6.
 */
public class CarLocation  extends Base {
    public final String type="Point";
    public final ArrayList<Double> coordinates = new ArrayList<Double>();

    public CarLocation(){
        coordinates.add(0.0);
        coordinates.add(0.0);
    }
}
