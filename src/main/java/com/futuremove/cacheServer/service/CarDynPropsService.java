package com.futuremove.cacheServer.service;

import com.futuremove.cacheServer.entity.CarDynProps;
import com.mongodb.client.FindIterable;
import org.bson.Document;

/**
 * Created by qurj on 15/7/6.
 */
public interface CarDynPropsService extends  BaseService  {

    FindIterable<Document> getNearByNeededCar(Long maxDistance,CarDynProps centerFilter);
    Long countNearByNeededCar(Long maxDistance,CarDynProps centerFilter);
    FindIterable<Document> getByOwnerAndNotState(CarDynProps carDynProps);
    boolean sendAuthCode(String vin);
    boolean sendLock(String vin) ;
    boolean sendPowerOff(String vin) ;
    boolean sendClearCode(String vin);
    boolean sendPowerOn(String vin) ;

}
