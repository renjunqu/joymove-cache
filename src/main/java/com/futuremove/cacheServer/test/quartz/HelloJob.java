package com.futuremove.cacheServer.test.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		//String name = arg0.getJobDetail().getJobDataMap().getString("name");
		//System.out.println("Job executing: "+name + " ****** Date: "+new Date());
		System.out.println("hi, i am hello world");
	}

	
}
