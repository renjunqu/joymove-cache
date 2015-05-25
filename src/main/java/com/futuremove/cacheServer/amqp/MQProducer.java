package com.futuremove.cacheServer.amqp;



import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;



/**
 * message producer, serialize Object to bytes
 * @author wx
 *
 */
public class MQProducer extends MQEndPoint{

	public MQProducer(String exchangeName,String queueName,String routingKey,String ip, int port) throws IOException {
		super(exchangeName,queueName,routingKey,ip, port);
	}

	public void sendMessage(String jsonStr) throws IOException {
        channel.basicPublish(exchangeName,routingKey,null, jsonStr.getBytes());
    }

}
