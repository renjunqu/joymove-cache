package com.futuremove.cacheServer.beans;

import org.springframework.beans.factory.config.AbstractFactoryBean;

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


public class QuartzSchedulerFactoryBean  extends AbstractFactoryBean<Scheduler> {

	@Override
	protected Scheduler createInstance() throws Exception {
		// TODO Auto-generated method stub
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//scheduler.start();
		return scheduler;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Scheduler.class;
	}

}
