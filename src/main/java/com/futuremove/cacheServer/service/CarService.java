package com.futuremove.cacheServer.service;

import java.util.List;
import java.util.Map;

import com.futuremove.cacheServer.entity.Car;

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
	void updateCarStateWaitCode(Car car);
	void updateCarStatePowerOn(Car car);
	void updateCarStateWaitLock(Car car);

	boolean sendLock(String vin) throws Exception;
	boolean sendPowerOff(String vin) throws Exception;
	boolean sendClearCode(String vin) throws Exception;

}
