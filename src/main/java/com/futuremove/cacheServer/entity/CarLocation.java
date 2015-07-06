package com.futuremove.cacheServer.entity;

import java.util.ArrayList;

/**
 * Created by qurj on 15/7/6.
 */
public class CarLocation  extends Base {
    public final String type="Point";
    public ArrayList<Double> coordinates;
}
