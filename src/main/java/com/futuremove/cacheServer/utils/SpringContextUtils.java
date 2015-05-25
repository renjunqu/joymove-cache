package com.futuremove.cacheServer.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtils {
	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
}
