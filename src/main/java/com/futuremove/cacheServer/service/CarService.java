package com.futuremove.cacheServer.service;

import java.util.List;
import java.util.Map;

import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.utils.ConfigUtils;
import com.futuremove.cacheServer.utils.HttpPostUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public interface CarService {

	void updateCarPosition(Car car);
	void save(Car car);
	public Car getByVinNum(String vinNum);
	void clearExpireReserve(Car car);
	Car getByOwnerAndNotState(Car car);
	void updateCarStateReservePending(Car car);
	void updateCarStateReserved(Car car);
	void updateCarStateBusy(Car car);
	List<Car> getFreeCarByScope(Map<String, Object> likeCondition);
	List<Car> getBusyCarByScope(Map<String, Object> likeCondition);
	void updateCarStateFree(Car car);
	void updateCarStateWaitSendCode(Car car);
	void updateCarStateWaitPowerOn(Car car);
	void updateCarStateWaitLock(Car car);
	void updateCarStateWaitClearCode(Car car);
	void updateCarStateWaitPowerOff(Car car);
	boolean sendAuthCode(String vin);
	boolean sendLock(String vin) ;
	boolean sendPowerOff(String vin) ;
	boolean sendClearCode(String vin);
	boolean sendPowerOn(String vin) ;

}
