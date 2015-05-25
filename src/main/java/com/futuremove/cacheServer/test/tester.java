package com.futuremove.cacheServer.test;
import java.io.InputStream;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;


public class tester {
	public static void main(String []args){
		
		try {
		
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
			Human h = null;
			h = (Human)context.getBean("Chinese");
			h.eat();
		} catch(Exception e){
			e.printStackTrace();
			
		}
	}

}
