package com.futuremove.cacheServer.test.aop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.test.Chinese;
import com.futuremove.cacheServer.test.aop.cutter.*;
import com.futuremove.cacheServer.test.aop.*;
public class mainEntry {
	

	
	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		CutterTarget ct = (CutterTarget)context.getBean("cutterTarget");
		ct.test();
		
		
	}

}
