package com.futuremove.cacheServer.test.amqp;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.futuremove.cacheServer.amqp.MQProducer;
import com.futuremove.cacheServer.test.amqp.*;


public class MQTestProducer {
	public static void main(String[] args){
			
			try {
				MQProducer mq = new MQProducer("wxtest","wxtestqueue","test", "123.56.106.52",5672);
				JSONObject t = new JSONObject();
				t.put("ttt","haha");
				System.out.println("start send");
				mq.sendMessage(t.toJSONString());
				System.out.println("SendOver");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	

}
