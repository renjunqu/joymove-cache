package com.futuremove.cacheServer.amqp.handler.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.futuremove.cacheServer.amqp.handler.EventHandler;
import com.futuremove.cacheServer.test.slf4j.testSlf4J;
import com.futuremove.cacheServer.utils.ConfigUtils;
import com.futuremove.cacheServer.utils.HttpPostUtils;

public class RegisterHandler  implements EventHandler {

	final static Logger logger = LoggerFactory.getLogger(RegisterHandler.class);
	@Override
	public boolean handleData(JSONObject json) {
		boolean error=true;
		try {
			
			    logger.info("register handler called !!");
			    
				String postUrl = ConfigUtils.getPropValues("registerAck.url");
				json.put("vinNum", json.get("vin"));
				HttpPostUtils.post(postUrl, json);
				logger.info("send data to joymove success");
				return false;
		} catch(Exception e){
			error = true;
			 logger.info("error to send data to joymove");
		}
		return error;
	}

}
