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
import com.futuremove.cacheServer.utils.HttpPostUtils;

public class ReportSendCodeHandler implements EventHandler {
	

	private  CarService carService;
	
	
	
	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	

	final static Logger logger = LoggerFactory.getLogger(ReportSendCodeHandler.class);
	
	
	
	
	public ReportSendCodeHandler(CarService carService) {
		super();
		this.carService = carService;
	}
	
	

	public ReportSendCodeHandler() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/********* busi proc *******/

	@Override
	public boolean handleData(JSONObject json) {
		boolean error=true;
		try {
			
			logger.info("get send code ok report ");
			logger.info("now to call handler !!");
			    
			String postUrl = ConfigUtils.getPropValues("sendCodeAck.url");
			json.put("vinNum", json.get("vin"));
			HttpPostUtils.post(postUrl, json);
			logger.info("send data to joymove success");
			return false;
				
			/*
			Car car = new Car();
			Car prevCar = carService.getByVinNum((String)json.get("vin"));
			if(prevCar!=null && prevCar.getState()==Car.state_wait_code) {
				
				car.setVinNum((String)json.get("vin"));
				car.setState(Car.state_busy);
				carService.updateCarState(car);
				logger.info("update the car state to busy");
			} else {
				logger.info("car not in code_wait state, so do nothing");
			}
			*/
		} catch(Exception e){
			error = true;
		}
		return error;
	}

}
