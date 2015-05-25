package com.futuremove.cacheServer.test.quartz;

import java.util.Date;
import java.util.Scanner;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;



public class testQuartz {
	
	public static void main(String [] args){
		
		
			
		// scheduler if not start, it could be use to insert job、delete job, not run.
		// so, the true 'sceduler node', just used to add job or delete job
		// and the running node, used to run the job
		try {
			System.out.println("now, lest's start");
			 // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // and start it off
           // scheduler.start();
            System.out.println("first show the instance id :");
            System.out.println(scheduler.getSchedulerInstanceId());
            System.out.println("first show the instance name :");
            System.out.println(scheduler.getSchedulerName());
            
            int createNum = 0;
            int delNum = 0;
   
			Scanner input = new Scanner(System.in);
			while(true) {
				  int    x = input.nextInt();
					switch(x) {
						case 1:
							System.out.println("i input");
							 JobDataMap dataMap = new JobDataMap();
							 dataMap.put("name", "job1"+createNum);
							 JobDetail job1 = newJob(HelloJob.class)
					            		.withIdentity("job1"+ createNum,"group1")
					            		.setJobData(dataMap)
					            		.build();
					            
					            Trigger trigger1 = newTrigger()
					            		.withIdentity("trigger1"+ createNum,"group1")
					            		.startNow()
					            		.withSchedule(simpleSchedule()
					            				.withIntervalInSeconds(3)
					            				.repeatForever()
					            				)
					            		.build();
					            createNum++;
					            
							scheduler.scheduleJob(job1,trigger1);
							Thread.sleep(2000);
						break;
						case 2:
							System.out.println("2 input");
							JobKey key = new JobKey("job1"+(delNum++), "group1");
							System.out.println("to be deleted : "+ key);
							scheduler.deleteJob(key);
							break;
						default:
							 System.out.println("OK");
							break;
					}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
