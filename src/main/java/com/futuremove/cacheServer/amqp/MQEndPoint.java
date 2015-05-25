package com.futuremove.cacheServer.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * represent a connection with queue
 * @author qrj
 *
 */
public abstract class MQEndPoint {

	protected Channel channel;
	protected Connection connection;
	protected String queueName;
	protected String exchangeName;
	protected String routingKey;
	
	public final Logger logger = LoggerFactory.getLogger(MQEndPoint.class);

	public MQEndPoint(String exchangeName,String queueName,String routingKey,String ip, int port) throws IOException{

		this.exchangeName = exchangeName;
		this.queueName    = queueName;
		this.routingKey   = routingKey;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ip);
		factory.setPort(port);
		factory.setNetworkRecoveryInterval(3000);
		factory.setAutomaticRecoveryEnabled(true);
		factory.setTopologyRecoveryEnabled(true);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, "direct", true);
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

	}

	/***
	 * close channel and connection
	 * @throws IOException
	 */
	 public void close() throws IOException{
		 this.channel.close();
		 this.connection.close();
	 }
}