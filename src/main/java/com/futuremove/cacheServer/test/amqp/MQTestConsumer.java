package com.futuremove.cacheServer.test.amqp;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.futuremove.cacheServer.amqp.MQConsumer;

public class MQTestConsumer {
	
	public static void main(String[] args){
		
		try {
			MQConsumer  mq = new MQConsumer("wxtest","wxtestqueue","test", "123.56.106.52",5672);
			mq.startConsume();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
