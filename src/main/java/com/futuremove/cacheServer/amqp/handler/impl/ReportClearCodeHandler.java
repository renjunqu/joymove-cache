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

public class ReportClearCodeHandler  implements EventHandler {

	final static Logger logger = LoggerFactory.getLogger(ReportClearCodeHandler.class);
	
	@Override
	public boolean handleData(JSONObject json) {
		boolean error=false;
		logger.info("clear code ok");
		return error;
	}

}
