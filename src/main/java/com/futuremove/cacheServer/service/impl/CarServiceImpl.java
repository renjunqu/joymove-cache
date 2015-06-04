package com.futuremove.cacheServer.service.impl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import com.futuremove.cacheServer.utils.ConfigUtils;
import com.futuremove.cacheServer.utils.HttpPostUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.futuremove.cacheServer.dao.CarDao;
import com.futuremove.cacheServer.dao.DynamicMatDao;
import com.futuremove.cacheServer.dao.StaticMatDao;
import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMatCarInfo;
import com.futuremove.cacheServer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 *   NCar means new car, used the old for test
 * 
 * */

@Service("carService")
public class CarServiceImpl  implements CarService {
	
	final static Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

	@Autowired
	private CarDao carDao;
	@Autowired
	private DynamicMatDao dynamicMatDao;
	@Autowired
	private StaticMatDao staticMatDao;

	public CarServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	/************ business method  ******************/

	public void updateCarPosition(Car car) {

		logger.info("inside  updateCarPosition !!");
			try {
					//get the old data  car
					Car prevCar =  carDao.get(car.getVinNum());
					carDao.updateLocInfo(car);
					//update loc mat info
					if(prevCar==null) {
						//no such car
						logger.info("update status for a not found car");
						return ;
					} else if(prevCar.getState()== Car.state_busy) {
						logger.info("update a  status for a busy car");
						logger.info("first clear car info from static matrix");
						staticMatDao.removeCar(prevCar);
						logger.info("then update data into dynamic matrix");
						dynamicMatDao.updateCurrCarInfo(prevCar,car);
						return;
					} else if (prevCar.getState()==Car.state_free) {
						logger.info("update a  status for a free car");
						logger.info("first clear car info from dynamic matrix");
						dynamicMatDao.removeCar(prevCar);
						logger.info("then update data into static matrix");
						staticMatDao.updateCarInfo(prevCar,car);
						return;
					} else {
						//other state
						logger.info("update a  status for a Not Ready ~~~~~ car");
						dynamicMatDao.removeCar(prevCar);
						staticMatDao.removeCar(prevCar);
					}
			} catch(Exception e){
				logger.error(e.toString());
				return ;
			}

	}

	public void save(Car car) {
		// TODO Auto-generated method stub
		carDao.save(car);
		
	}
	
	public Car getByVinNum(String vinNum){
		return carDao.get(vinNum);
	}



	public void clearExpireReserve(Car car) {
		logger.info("inside  clearExpireReserve !!");
		carDao.clearExpireReserve(car);
	}
	
	public Car getByOwnerAndNotState(Car car) {
		// TODO Auto-generated method stub
		return carDao.getByOwnerAndNotState(car);
	}
	
	//set car state to pending, when car's state as free

	public void updateCarStateFree(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService  : update State to Pending start");
	    carDao.changeCarState(Car.state_free,car);
		logger.debug("carService : update State to Pending over ");	
	}
	
	
	
	//set car state to pending, when car's state as free

	public void updateCarStateWaitSendCode(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService  : update State to Pending start");
		carDao.changeCarState(Car.state_wait_sendcode,car);
		logger.debug("carService : update State to Pending over ");	
	}

	public void updateCarStateWaitClearCode(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService  : update State to Pending start");
		carDao.changeCarState(Car.state_wait_clearcode,car);
		logger.debug("carService : update State to Pending over ");
	}

	
	
	
	//set car state to pending, when car's state as free

	public void updateCarStateReservePending(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService  : update State to Pending start");
		car.setState(Car.state_free);
		carDao.changeCarState(Car.state_reserve_pending,car);
		logger.debug("carService : update State to Pending over ");
	}
		
	//set car state to reserved, when car's state as pending

	public void updateCarStateReserved(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService : update State to reserved start");
		car.setState(Car.state_reserve_pending);
		carDao.changeCarState(Car.state_reserved,car);
		logger.debug("carService : update State to reserved over ");	
	}
		
	//set car state to reserved, when car's state as pending

	public void updateCarStateBusy(Car car) {
		// TODO Auto-generated method stub
		logger.debug("carService : update State to Busy start");
		carDao.changeCarState(Car.state_busy,car);
	 	logger.debug("carService : update State to Busy over ");
	}

	public void updateCarStateWaitPowerOn(Car car){
		logger.debug("carService : update State to Busy start");
		carDao.changeCarState(Car.state_wait_poweron, car);
		logger.debug("carService : update State to Busy over ");
	}


	public void updateCarStateWaitPowerOff(Car car){
		logger.debug("carService : update State to Busy start");
		carDao.changeCarState(Car.state_wait_poweroff, car);
		logger.debug("carService : update State to Busy over ");
	}


	public void updateCarStateWaitLock(Car car){
		//logger.debug("carService : update State to Busy start");
		carDao.changeCarState(Car.state_wait_lock, car);
		//logger.debug("carService : update State to Busy over ");
	}


	public List<Car> getFreeCarByScope(Map<String, Object> likeCondition) {
		// TODO Auto-generated method stub
		//search in free matrix
		Long userX = StaticMatDao.getXIndex(Double.valueOf(likeCondition.get("userPositionX").toString()));
		Long userY = StaticMatDao.getYIndex(Double.valueOf(likeCondition.get("userPositionY").toString()));
		Long margin = Double.valueOf(likeCondition.get("scope").toString()).longValue();
		margin = margin/50 + 20L;
		if(margin<=1) {
			margin = new Long(2);
		} else if(margin >=100000L) {
			margin = 100000L;
		}
		Long minX = userX - margin,maxX=userX+margin;
		Long minY = userY - margin,maxY=userY+margin;
		//first get the car vin num
		List<String> carVinNums = staticMatDao.getCarVinNumByScope(minX,maxX,minY,maxY);
		// then get the car
		List<Car> retCars = new ArrayList<Car>();
		for(String vinNum:carVinNums) {
			Car car = carDao.get(vinNum);
			if(car.getState()==Car.state_free)
				retCars.add(car);
		}
		return retCars;
	}

	public List<Car> getBusyCarByScope(Map<String, Object> likeCondition) {
		// TODO Auto-generated method stub
		//search in free matrix
		Long userX = StaticMatDao.getXIndex(Double.valueOf(likeCondition.get("userPositionX").toString()));
		Long userY = StaticMatDao.getYIndex(Double.valueOf(likeCondition.get("userPositionY").toString()));
		Long margin = Double.valueOf(likeCondition.get("scope").toString()).longValue();
		margin = margin/50 + 20L;
		if(margin<=1) {
			margin = new Long(2);
		} else if(margin >=100000L) {
			margin = 100000L;
		}
		Long minX = userX - margin,maxX=userX+margin;
		Long minY = userY - margin,maxY=userY+margin;
		//first get the car vin num
		List<DynamicMatCarInfo> infos = dynamicMatDao.getCarVinNumByScope(minX,maxX,minY,maxY);
		// then get the car
		List<Car> retCars = new ArrayList<Car>();
		for(DynamicMatCarInfo info:infos) {
			Car car = carDao.get(info.getVinNum());
			if(car.getState()==Car.state_busy)
				retCars.add(car);
		}
		return retCars;
	}


	public boolean sendLock(String vin){
		try {
				String timeStr = String.valueOf(System.currentTimeMillis());
				String data = "time="+timeStr+"&vin="+vin;
				String url = ConfigUtils.getPropValues("cloudmove.lock");
				String result = HttpPostUtils.post(url, data);

				JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
				int opResult = Integer.parseInt(cmObj.get("result").toString());
				if(opResult==1)
					return true;
				else
					return false;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;


	}

	public boolean sendPowerOff(String vin) {
		try {
				String timeStr = String.valueOf(System.currentTimeMillis());
				String data = "time="+timeStr+"&vin="+vin;
				String url = ConfigUtils.getPropValues("cloudmove.poweroff");
				String result = HttpPostUtils.post(url, data);

				JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
				int opResult = Integer.parseInt(cmObj.get("result").toString());
				if(opResult==1)
					return true;
				else
					return false;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}

	public boolean sendClearCode(String vin){
		try {
				String timeStr = String.valueOf(System.currentTimeMillis());
				String data = "time="+timeStr+"&vin="+vin;
				String url = ConfigUtils.getPropValues("cloudmove.clearAuth");
				String result = HttpPostUtils.post(url, data);

				JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
				int opResult = Integer.parseInt(cmObj.get("result").toString());
				if(opResult==1)
					return true;
				else
					return false;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean sendPowerOn(String vin) {
		try {
			String timeStr = String.valueOf(System.currentTimeMillis());
			String data = "time="+timeStr+"&vin="+vin;
			String url = ConfigUtils.getPropValues("cloudmove.poweron");
			String result = HttpPostUtils.post(url, data);
			JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
			int opResult = Integer.parseInt(cmObj.get("result").toString());
			if(opResult==1)
				return true;
			else
				return false;
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean sendAuthCode(String vin) {
		try {
			String postUrl=ConfigUtils.getPropValues("cloudmove.sendAuth");
			String timeStr = String.valueOf(System.currentTimeMillis());
			String postData = "time=" + timeStr + "&vin=" + vin + "&auth=abcdef";
			String result = HttpPostUtils.post(postUrl, postData);
			JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
			int opResult = Integer.parseInt(cmObj.get("result").toString());
			if(opResult==1) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

		
	

}
