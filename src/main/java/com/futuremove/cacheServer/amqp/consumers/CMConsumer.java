package com.futuremove.cacheServer.amqp.consumers;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.amqp.MQEndPoint;
import com.futuremove.cacheServer.amqp.handler.EventHandler;
import com.futuremove.cacheServer.amqp.handler.impl.*;
import com.futuremove.cacheServer.test.slf4j.testSlf4J;
import com.futuremove.cacheServer.utils.SpringContextUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class CMConsumer implements Consumer{
	
	public final Logger logger = LoggerFactory.getLogger(CMConsumer.class);
	 
	
	private Hashtable<String,EventHandler> handlersMap = new Hashtable<String,EventHandler>();
	
	public CMConsumer() {
		
	
		logger.warn("MQConsumer init  !!!");
		ApplicationContext context = SpringContextUtils.context;
		handlersMap.put("1", (RegisterHandler)context.getBean("registerHandler"));
		handlersMap.put("2", (ReportSendKeyHandler)context.getBean("reportSendKeyHandler"));
		handlersMap.put("3", (UpdateStatusHandler)context.getBean("updateStatusHandler"));
		handlersMap.put("4", (ReportSendCodeHandler)context.getBean("reportSendCodeHandler"));
		handlersMap.put("5", (ReportClearCodeHandler)context.getBean("reportClearCodeHandler"));
		logger.warn("init amqp consumer over");
		
	}

	public void handleCancel(String arg0) throws IOException {}

	public void handleCancelOk(String arg0) {}

	/** Called when consumer is registered. */
	public void handleConsumeOk(String arg0) {}

	/** Called when new message is available.*/
	public void handleDelivery(String arg0, Envelope arg1,
			BasicProperties arg2, byte[] arg3) {
		try {
		    String res = new String(arg3);
			//System.out.println("data is "+res);
			
			
			logger.warn(res);
			
			JSONParser parser=new JSONParser();
			 
				 Map json = (Map)parser.parse(res);
				 logger.warn("show the parse result");
				 logger.warn(json.toString());
				 EventHandler eventHandler = handlersMap.get(String.valueOf(json.get("type")));
				 logger.warn("the to called handler is "+eventHandler.toString());
				 JSONObject json_data = (JSONObject)json.get("data");
				 eventHandler.handleData(json_data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}

	public void handleRecoverOk(String arg0) {
		System.out.println("recover ok");
	}

	public void handleShutdownSignal(String arg0, ShutdownSignalException arg1) throws  ShutdownSignalException  {
		logger.warn("now the channel shutdown !!!"+arg0);
		logger.warn("reason "+arg1.getReason());
		
	}
}
