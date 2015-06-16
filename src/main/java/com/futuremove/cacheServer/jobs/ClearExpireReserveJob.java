package com.futuremove.cacheServer.jobs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.futuremove.cacheServer.concurrent.CarOpLock;
import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.FileCopyUtils;

import com.futuremove.cacheServer.service.CarService;
import com.futuremove.cacheServer.test.Human;
import com.futuremove.cacheServer.entity.*;

public class ClearExpireReserveJob  implements Job {
	
	final static Logger logger = LoggerFactory.getLogger(ClearExpireReserveJob.class);
	
	/************  business proc  ***********************/
	
	
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		ReentrantLock optLock = null;
		try {
			Map args = arg0.getJobDetail().getJobDataMap();
			String carVinNum = (String)args.get("vinNum");
			String owner = (String)args.get("owner");
			optLock = CarOpLock.getCarLock(carVinNum);
			if (optLock.tryLock()) {
				ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
				Scheduler scheduler = (Scheduler) context.getBean("scheduler");
				JobKey jobKey = new JobKey(owner + carVinNum, "clearExpire");
				CarService carService = (CarService) context.getBean("carService");
				Car car = new Car();
				car.setVinNum(carVinNum);
				car.setOwner(owner);
				carService.clearExpireReserve(car);
				logger.info("clear expire timer running ......... for car: " + carVinNum + "......");
				//delete the job
				scheduler.deleteJob(jobKey);
				optLock.unlock();
			}
		} catch(Exception e){
			if(optLock!=null && optLock.getHoldCount()>0)
				optLock.unlock();
			logger.debug("exception happens when clear expire, ");
			logger.debug(e.toString());
			//logger.debug(e.printStackTrace());
			logger.debug("show exception over");
		}
		//logger.trace("Job executing: "+name + " ****** Date: "+new Date());
	}
}
