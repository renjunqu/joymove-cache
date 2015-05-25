package com.futuremove.cacheServer.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ConfigUtils {
	public static String getPropValues(String propName){
		Properties prop = new Properties();
		String propFileName = "cacheServer.properties";
 
		InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(propFileName);
 
		if (inputStream != null) {
			try {
				prop.load(inputStream);
				return  prop.getProperty(propName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
			return null;
	}
	
	public static void main(String[] args){
		System.out.println(ConfigUtils.getPropValues("hello1"));
		
	}

}
