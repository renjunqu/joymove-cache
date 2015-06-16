package com.futuremove.cacheServer.test.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob {


	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		//String name = arg0.getJobDetail().getJobDataMap().getString("name");
		//logger.trace("Job executing: "+name + " ****** Date: "+new Date());
	//	logger.trace("hi, i am hello world");
	}

	
}
