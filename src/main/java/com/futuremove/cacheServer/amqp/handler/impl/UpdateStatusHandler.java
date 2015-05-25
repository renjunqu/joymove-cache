package com.futuremove.cacheServer.amqp.handler.impl;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.futuremove.cacheServer.amqp.handler.EventHandler;
import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.service.CarService;
import com.futuremove.cacheServer.utils.ConfigUtils;

public class UpdateStatusHandler  implements EventHandler {

	final static Logger logger = LoggerFactory.getLogger(UpdateStatusHandler.class);
	
	private  CarService carService;
	
	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	

	public UpdateStatusHandler(CarService carService) {
		super();
		this.carService = carService;
	}
	

	public UpdateStatusHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean handleData(JSONObject json) {
		boolean error=true;
		try {
			
			logger.info("update car status  handler called !!");
			 
			Car car = new Car();
			car.setLatitude((Double)json.get("latitude"));
			car.setLongitude((Double)json.get("longitude"));
			car.setVinNum((String)json.get("vin"));
			logger.info("call carservice to update car loc info !!");
			carService.updateCarPosition(car);
			
			
		} catch(Exception e){
			error = true;
		}
		return error;
	}

}
