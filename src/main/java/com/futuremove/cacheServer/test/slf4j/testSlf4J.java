package com.futuremove.cacheServer.test.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class testSlf4J {

	public static void main(String[] args){
		
	 final Logger logger = LoggerFactory.getLogger(testSlf4J.class);
	  Integer t=1;
	  Integer oldT;

	   
	    
	   oldT = t;        
	   t = 9;

	   logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);

	   logger.info("Temperature has risen above 50 degrees.");  
	   
		
	}
}
