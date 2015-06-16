package com.futuremove.cacheServer.test.aop.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.test.Human;

public class test {
	
	public void test1() throws Exception{
	//	logger.trace("try to throw exception");
		throw new Exception("sdfdsf");
	}
	
	public static void main(String []args){
		
		try {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		test t = null;
		t = (test)context.getBean("test");
	//	logger.trace(t);
		t.test1();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
