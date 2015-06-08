package com.futuremove.cacheServer.concurrent;

import com.futuremove.cacheServer.entity.Car;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jessie on 2015/6/2.
 */

public class CarOpLock {

    public static HashMap<String,ReentrantLock> carOptLockMap = new HashMap<String, ReentrantLock>();

    public static ReentrantLock lockOfLock = new ReentrantLock();

    public static  ReentrantLock getCarLock(String vinNum){

        ReentrantLock lock = carOptLockMap.get(vinNum);
        if(lock==null) {
            lockOfLock.lock();
                ReentrantLock lock_test = carOptLockMap.get(vinNum);
                if(lock_test==null) {
                    ReentrantLock newLock = new ReentrantLock();
                    carOptLockMap.put(vinNum,newLock);
                }
            lockOfLock.unlock();
        }
        lock = carOptLockMap.get(vinNum);
        return lock;
    }
}
