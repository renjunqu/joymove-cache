package com.futuremove.cacheServer.concurrent;

import com.futuremove.cacheServer.entity.Car;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jessie on 2015/6/2.
 */

public class UserOptLock {

    public static HashMap<String,ReentrantLock> UserOptLockMap = new HashMap<String, ReentrantLock>();

    public static ReentrantLock lockOfLock = new ReentrantLock();

    public static  ReentrantLock getUserLock(String mobileNo){
        if(mobileNo==null || mobileNo.length()==0)
            return null;
        ReentrantLock lock = UserOptLockMap.get(mobileNo);
        if(lock==null) {
            lockOfLock.lock();
            ReentrantLock lock_test = UserOptLockMap.get(mobileNo);
            if(lock_test==null) {
                ReentrantLock newLock = new ReentrantLock();
                UserOptLockMap.put(mobileNo,newLock);
            }
            lockOfLock.unlock();
        }
        lock = UserOptLockMap.get(mobileNo);
        return lock;
    }
}
