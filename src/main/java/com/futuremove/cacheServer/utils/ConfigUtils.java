package com.futuremove.cacheServer.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ConfigUtils {
	static Properties prop = null;
	static {
		InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream("cacheServer.properties");
			try {
				prop = new Properties();
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public static String getPropValues(String propName){

		return prop.getProperty(propName);
	}
	
	public static void main(String[] args){
		System.out.println(ConfigUtils.getPropValues("hello1"));
		
	}

}
