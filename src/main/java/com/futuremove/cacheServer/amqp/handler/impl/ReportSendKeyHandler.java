package com.futuremove.cacheServer.amqp.handler.impl;

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
import com.futuremove.cacheServer.utils.ConfigUtils;
import com.futuremove.cacheServer.utils.HttpPostUtils;

public class ReportSendKeyHandler  implements EventHandler {

	final static Logger logger = LoggerFactory.getLogger(ReportSendKeyHandler.class);
	
	@Override
	public boolean handleData(JSONObject json) {
		boolean error=true;
		try {
				
				logger.info("report send key   handler called !!");
		 		Long result = (Long)json.get("result");
		 		if(result > 0 ) {
		 			logger.info("send key ok  !!");
		 			// the key send ok 
		 			String postUrl = ConfigUtils.getPropValues("sendKeyAck.url");
		 			json.put("vinNum", json.get("vin"));
		 			HttpPostUtils.post(postUrl, json);
		 			return false;
		 		} else {
		 			logger.info("send key failed  !!");
		 		}
		} catch(Exception e){
			error = true;
			logger.info("send key exception: "+e.toString());
		}
		return error;
	}

}
