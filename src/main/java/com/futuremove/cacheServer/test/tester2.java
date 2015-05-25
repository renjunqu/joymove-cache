package com.futuremove.cacheServer.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class tester2 {
	public  static  void main(String []args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		Food h = null;
		h = (Food)context.getBean("Food");
		System.setProperty("ttt", "qrj");
		System.out.println(h.getName());		
	}

}
